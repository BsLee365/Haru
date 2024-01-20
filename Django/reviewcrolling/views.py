import math
import datetime
import time
from django.http import HttpResponse


import numpy as np
import pandas as pd
from selenium import webdriver
from selenium.common.exceptions import TimeoutException
from selenium.webdriver.edge.service import Service
from selenium.webdriver.common.by import By
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.support.ui import WebDriverWait
from keras.models import load_model
from keras.utils import custom_object_scope
from tensorflow_addons.optimizers import RectifiedAdam
from tqdm import tqdm
from transformers import BertTokenizer
from transformers import TFBertModel
import cx_Oracle
from sqlalchemy import create_engine
import pandas as pd

from text.views import model, predict_load_data

import numpy as np
import pandas as pd
from tensorflow.keras.models import load_model
from tensorflow.keras.utils import custom_object_scope
from tensorflow_addons.optimizers import RectifiedAdam
from tqdm import tqdm
from transformers import BertTokenizer, TFBertModel

tokenizer = BertTokenizer.from_pretrained('bert-base-multilingual-cased')


# 모델 파일 경로
model_path = 'text/textmodel/finalsentense.h5'



# custom_objects 매개변수를 사용하여 레이어를 등록
with custom_object_scope({'TFBertModel': TFBertModel, 'RectifiedAdam': RectifiedAdam}):
    model = load_model(model_path)


model.summary()
def convert_data(data_df):
    global tokenizer

    SEQ_LEN = 128 #SEQ_LEN : 버트에 들어갈 인풋의 길이
    #토큰 인풋, 마스크 인풋, 세그먼트 인풋, 타겟
    tokens, masks, segments, targets = [], [], [], []

    for i in tqdm(range(len(data_df))):
        # token : 문장을 토큰화함
        token = tokenizer.encode(data_df[DATA_COLUMN][i], max_length=SEQ_LEN, truncation=True, padding='max_length')

        # 마스크는 토큰화한 문장에서 패딩이 아닌 부분은 1, 패딩인 부분은 0으로 통일
        # num_zeros를 구해서 패딩 처리 하기 위해서 연산
        num_zeros = token.count(0)
        # SEQ_LEN-num_zeros : 실제 문장의 길이를 연산
        mask = [1]*(SEQ_LEN-num_zeros) + [0]*num_zeros
        #print(mask)
        # 문장의 전후관계를 구분해주는 세그먼트는 문장이 1개밖에 없으므로 모두 0
        segment = [0]*SEQ_LEN

        # 버트 인풋으로 들어가는 token, mask, segment를 tokens, segments에 각각 저장
        tokens.append(token)
        masks.append(mask)
        segments.append(segment)

        # 정답(긍정 : 1 부정 0)을 targets 변수에 저장해 줌
        targets.append(data_df[LABEL_COLUMN][i])

    # tokens, masks, segments, 정답 변수 targets를 numpy array로 지정
    tokens = np.array(tokens)
    masks = np.array(masks)
    segments = np.array(segments)
    targets = np.array(targets)

    return [tokens, masks, segments], targets

# 위에 정의한 convert_data 함수를 불러오는 함수를 정의
def load_data(pandas_dataframe):
    data_df = pandas_dataframe
    data_df[DATA_COLUMN] = data_df[DATA_COLUMN].astype(str)
    data_df[LABEL_COLUMN] = data_df[LABEL_COLUMN].astype(int)
    data_x, data_y = convert_data(data_df)
    return data_x, data_y

SEQ_LEN = 128
BATCH_SIZE = 20
# 긍부정 문장을 포함하고 있는 칼럼
DATA_COLUMN = "document"
# 긍정인지 부정인지를 (1=긍정,0=부정) 포함하고 있는 칼럼
LABEL_COLUMN = "label"



def predict_convert_data(data_df):
    global tokenizer
    tokens, masks, segments = [], [], []

    for i in tqdm(range(len(data_df))):

        token = tokenizer.encode(data_df[DATA_COLUMN][i], max_length=SEQ_LEN, truncation=True, padding='max_length')
        num_zeros = token.count(0)
        mask = [1]*(SEQ_LEN-num_zeros) + [0]*num_zeros
        segment = [0]*SEQ_LEN

        tokens.append(token)
        segments.append(segment)
        masks.append(mask)

    tokens = np.array(tokens)
    masks = np.array(masks)
    segments = np.array(segments)
    return [tokens, masks, segments]

# 위에 정의한 convert_data 함수를 불러오는 함수를 정의
def predict_load_data(pandas_dataframe):
    data_df = pandas_dataframe
    data_df[DATA_COLUMN] = data_df[DATA_COLUMN].astype(str)
    data_x = predict_convert_data(data_df)
    return data_x



def sentence_convert_data(data):
    global tokenizer
    tokens, masks, segments = [], [], []
    token = tokenizer.encode(data, max_length=SEQ_LEN, truncation=True, padding='max_length')

    num_zeros = token.count(0)
    mask = [1]*(SEQ_LEN-num_zeros) + [0]*num_zeros
    segment = [0]*SEQ_LEN

    tokens.append(token)
    segments.append(segment)
    masks.append(mask)

    tokens = np.array(tokens)
    masks = np.array(masks)
    segments = np.array(segments)
    return [tokens, masks, segments]



def text_data(data):
    # 데이터 전처리
    data_df = pd.DataFrame([{'document': data}])
    data_x = predict_load_data(data_df)

    # 데이터 프레임의 감정 분석 수행
    predictions = model.predict(data_x)

    # 결과 계산
    text_results = [min(max(round(float(pred), 2), 0), 1) for pred in predictions.flat]

    print(text_results)
    return text_results
def review_crawl(request):
    df3 = pd.read_csv('reviewcrolling/data/place_final.csv')
    service = Service(executable_path='reviewcrolling/data/msedgedriver.exe')
    driver = webdriver.Edge(service=service)

    df2 = pd.DataFrame()
    df2 = pd.read_csv('reviewcrolling/data/review_score.csv')
    df2_length = len(df2)
    df3 = df3.iloc[df2_length:]

    link = df3['review_url']
    location = df3['location']
    name = df3['name']
    wait = WebDriverWait(driver, 5)
    for i, (l, loc, n) in tqdm(enumerate(zip(link, location, name)), total=len(link)):
        reviewlist = []
        url = '{}'.format(l)
        driver.get(url)
        time.sleep(2)

        body = driver.find_element(By.TAG_NAME, 'body')

        try:

            element = WebDriverWait(driver, 10).until(
                EC.presence_of_element_located((By.CLASS_NAME, 'place_section_count'))
            )
            section_count_text = element.text
        except TimeoutException:
            # 10초 동안 요소가 나타나지 않으면 페이지 새로고침
            # print("요소를 찾을 수 없어 페이지를 새로고침합니다.")
            driver.refresh()
            time.sleep(5)

        try:
            element = WebDriverWait(driver, 10).until(
                EC.presence_of_element_located((By.CLASS_NAME, 'place_section_count'))
            )
            section_count_text = element.text
        except TimeoutException:
            # print("요소를 다시 찾을 수 없습니다.")
            continue

        section_count_text = section_count_text.replace(',', '')

        print(section_count_text)
        section_count = float(section_count_text)

        if section_count > 2000:
            section_count = 2000
        result = section_count / 10
        value = result
        rounded_value = math.ceil(value)
        print(rounded_value)
        time.sleep(3)

        body = driver.find_element(By.TAG_NAME, 'body')

        for i in range(rounded_value):
            body.send_keys(Keys.PAGE_DOWN)
            try:
                element = wait.until(EC.presence_of_element_located((By.CLASS_NAME, "E4qxG")))
                clickable_element = driver.find_element(By.CLASS_NAME, 'E4qxG')
                clickable_element.click()
            except Exception as e:
                # print("클릭할 요소를 찾지 못했습니다:", e)
                pass
        try:
            element = wait.until(EC.presence_of_element_located((By.CLASS_NAME, "rvCSr")))
            plus_elements = driver.find_elements(By.CLASS_NAME, 'rvCSr')
            for element in plus_elements:
                try:
                    element.click()
                except Exception as e:
                    break
                    # print("클릭 중 오류 발생:", e)
        except:
            pass

        try:
            element = wait.until(EC.presence_of_element_located((By.CLASS_NAME, "zPfVt")))
            elements = driver.find_elements(By.CLASS_NAME, 'zPfVt')
            for element in elements:
                try:
                    reviewlist.append(element.text)
                except Exception as e:
                    print("뭔 에러냐 이거")
                    pass
        except:
            average_sentiment = 0

        df = pd.DataFrame(reviewlist)
        df['링크'] = link

        data = {'document': df.iloc[:, 0]}
        data_df = pd.DataFrame(data)
        try:
            data_x = predict_load_data(data_df)
            predictions = model.predict(data_x)

            predict_values = [prediction[0] for prediction in predictions]
            average_sentiment = np.mean(predict_values)
        except:
            average_sentiment = 0

        temp_df = pd.DataFrame({'name': n, 'review_url': l, 'score': average_sentiment}, index=[0])
        df2 = pd.concat([df2, temp_df], ignore_index=True)
        print(df2)
        # 여기부터는 내부 반복문 및 로직이 들어갑니다...
        # 나머지 코드는 이전과 동일하게 유지됩니다.
        df2.to_csv('reviewcrolling/data/review_score.csv', index=False)

    # 데이터 저장 및 HTTP 응답 반환
    df2.to_csv('review_score.csv', index=False)
    engine = create_engine('oracle+cx_oracle://haru:haru0115@116.122.37.100:1521/xe')
    df2.to_sql('review', con=engine, if_exists='append', index=False)

    return HttpResponse("리뷰 크롤링 성공!!")