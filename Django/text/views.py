import pickle
import random
import re
import pandas as pd
from konlpy.tag import Okt
from keras.preprocessing.sequence import pad_sequences
from keras.models import load_model
from kiwipiepy import Kiwi
from django.http import JsonResponse
from django.views.decorators.csrf import csrf_exempt

max_len = 60
stopwords = ['도', '는', '다', '의', '가', '내', '것', '이', '은', '한', '에', '하', '고', '을', '를', '인', '듯', '과', '와', '네', '들', '듯', '지', '임', '게', '만', '게임', '겜', '되', '음', '면']  # 여기 수정됨
df = pd.read_csv('text/data/stopwords.csv')
df2 = pd.read_csv('text/data/stopword.csv')  # 불용어 처리를 위한 데이터 로딩
stopwords.extend(df['0'].values.tolist())
stopwords.extend(df2['0'].values.tolist())  # 불용어 데이터 통합

with open('text/textmodel/tokenizer.pickle', 'rb') as handle:
    tokenizer = pickle.load(handle)

model_path = 'text/textmodel/finaltextmodel1234567.h5'  # 텍스트 감정분석 모델의 경로 지정
model = load_model(model_path)  # 텍스트 감정분석 모델 로드

okt = Okt()


def preprocess_text(sentence):  # 텍스트 감정 분석을 위한 전처리 및 토큰화
    sentence = re.sub(r'[^ㄱ-ㅎㅏ-ㅣ가-힣 ]', '', sentence)  # 텍스트에서 한글을 제외한 문자를 정규표현식을 이용하여 삭제
    sentence = okt.morphs(sentence)  # okt를 이용하여 문장을 형태소 단위로 분류
    sentence = [word for word in sentence if not word in stopwords]  # 분류된 리스트에서 불용어를 제외
    encoded = tokenizer.texts_to_sequences([sentence])  # 리스트를 토큰화
    pad_new = pad_sequences(encoded, maxlen=max_len)  # 토큰화된 리스트를 패딩 처리하여 길이를 맞춤
    return pad_new


def predict_load_data(new_sentence):  # 텍스트의 감정 분석을 하여 긍정과 부정 비율을 판단하여 반환
    new_sentence = preprocess_text(new_sentence)  # 감정분석을 위하여 텍스트를 전처리
    text_results = float(model.predict(new_sentence))  # 모델을 통해 텍스트 감정 분석하여 결과 반환
    print(f"감정 분석 결과 (0 부정 1긍정) : {text_results:.2f}\n")
    return text_results


@csrf_exempt
def feedKeyword(request):  # FeedInsert에서 입력 받은 문장에서 해시태그를 추출하기 위한 함수
    feedsentence = request.GET['contents']  # vue에서 get 요청으로 입력받은 contents(피드 내용)을 feedsentence로 저장
    sentence = re.sub(r'[^ㄱ-ㅎㅏ-ㅣ가-힣 ]', '', feedsentence) # 텍스트에서 한글을 제외한 문자를 정규표현식을 이용하여 삭제
    nounkeyword = getNoun(sentence)  # getNoun함수를 이용하여 텍스트에 있는 단어를 명사화
    nounsentence = ' '.join(nounkeyword)  # 명사화 된 단어를 한 문장으로 합침
    keyword = okt.morphs(nounsentence)  # okt를 사용하여 문장을 형태소 단위로 분류
    print(keyword)
    filteredkeyword = [word for word in keyword if word not in stopwords]  # 분류된 리스트에서 불용어를 제외

    filteredkeyword = list(set(filteredkeyword))  # 리스트에서 중복을 제거
    filteredkeyword = [word for word in filteredkeyword if len(word) > 1]  # 리스트에서 한글자인 단어를 제거
    random.shuffle(filteredkeyword)  # 리스트를 랜덤하게 섞기
    randomkeyword = filteredkeyword[:10]  # 리스트에서 10개의 단어를 추출하여 저장
    print(filteredkeyword)
    print(randomkeyword)

    return JsonResponse(randomkeyword, safe=False)  # 해시태그 추천 리스트 반환


def getNoun(sentence):  # 문장을 입력받아 단어를 명사화 하여 리스트로 반환하는 함수
    kiwi = Kiwi()
    kiwi.analyze(sentence)  # Kiwi를 이용하여 문장을 분석

    results = []
    result = kiwi.analyze(sentence)  # 분석된 결과를 result에 저장
    for token, pos, _, _ in result[0][0]:  # result에서 명사만을 추출하여 results에 반환
        if len(token) != 1 and pos.startswith('N') or pos.startswith('SL'):
            results.append(token)
    return results
