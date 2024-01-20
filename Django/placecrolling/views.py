from selenium import webdriver
from selenium.common import NoSuchElementException
from selenium.webdriver.edge.service import Service
from selenium.webdriver.common.by import By
import time
from selenium.webdriver.common.keys import Keys
from tqdm.auto import tqdm
import pandas as pd
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.support.ui import WebDriverWait


def placeCrawl(request):
    getLinkList()
    df1 = pd.read_csv('placecrolling/test/linklist_강남.csv')
    df2 = pd.read_csv('placecrolling/test/linklist_명동.csv')
    df3 = pd.read_csv('placecrolling/test/linklist_성수.csv')
    df4 = pd.read_csv('placecrolling/test/linklist_홍대.csv')

    df = pd.concat([df1, df2, df3, df4], ignore_index=True)

    filteringLinkList(df)


def getLinkList():

    service = Service(executable_path='reviewcrolling/data/msedgedriver.exe')
    driver = webdriver.Edge(service=service)
    wait = WebDriverWait(driver, 5)

    location = ['강남', '명동', '성수', '홍대']
    # activities = ['해수욕장']
    activities = ['공원', '배드민턴장', '클라이밍', '요가', '헬스', '볼링장', '수영장', '농구장', '야구장', '자전거', '골프', '테니스', '전시', '영화관', \
                  '공연장', '사진', '보드게임', '공예', '댄스', '노래방', '방탈출', 'pc방', '도서관', '스터디', '외국어학원']

    for location in tqdm(location):
        list = []
        for activity in tqdm(activities):
            url = 'https://m.place.naver.com/place/list?query={} {}'.format(location, activity)
            driver.get(url)
            req = driver.page_source
            try:
                element = wait.until(EC.presence_of_element_located((By.CLASS_NAME, "eDFz9")))
                list_div = driver.find_element(By.CLASS_NAME, 'eDFz9')
            except NoSuchElementException:
                print(f'{location}[{activity}] 없다')
                continue
            element = wait.until(EC.presence_of_element_located((By.TAG_NAME, "body")))
            body = driver.find_element(By.TAG_NAME, 'body')
            list_div.click()
            for i in range(25):
                body.send_keys(Keys.PAGE_DOWN)
                if i % 25 == 0:
                    time.sleep(1)
            element = wait.until(EC.presence_of_element_located((By.CLASS_NAME, "VLTHu")))
            lists = driver.find_elements(By.CLASS_NAME, 'VLTHu')
            print(lists[0])
            for i in tqdm(lists):
                dicta = {}
                element = wait.until(EC.presence_of_element_located((By.CLASS_NAME, "P7gyV")))
                if 'tivan' not in i.find_element(By.CLASS_NAME, 'P7gyV').get_attribute('href'):
                    element = wait.until(EC.presence_of_element_located((By.CLASS_NAME, "YwYLL")))
                    dicta['name'] = i.find_element(By.CLASS_NAME, 'YwYLL').text
                    dicta['category_b'] = activity
                    element = wait.until(EC.presence_of_element_located((By.CLASS_NAME, "YzBgS")))
                    dicta['category_s'] = i.find_element(By.CLASS_NAME, 'YzBgS').text
                    dicta['location'] = location
                    element = wait.until(EC.presence_of_element_located((By.CLASS_NAME, "P7gyV")))
                    dicta['link'] = i.find_element(By.CLASS_NAME, 'P7gyV').get_attribute('href')
                    # print('이름 :', dicta['name'])
                    # print('카테고리 :', dicta['category_s'])
                    # print('링크 :', dicta['link'])

                    list.append(dicta)
            df = pd.DataFrame(list)
            df.to_csv(f'placecrolling/test/linklist_{location}.csv', index=False)


def filteringLinkList(df):
    ndf = df
    ndf = ndf.drop(ndf[(ndf['category_b'] == '공원') & ~(ndf['category_s'].isin(['근린공원', '부속시설', '도시,테마공원', '자연공원', '공원', '테마공원']))].index)
    ndf2 = ndf.drop(ndf[(ndf['category_b'] == '배드민턴장') & ~(ndf['category_s'].isin(['부속시설', '부속건물', '배드민턴장']))].index)
    ndf3 = ndf2.drop(ndf[(ndf['category_b'] == '클라이밍') & ~(ndf['category_s'].isin(['암벽등반', '스포츠시설', '레저,스포츠', '등산,등반']))].index)
    ndf4 = ndf3.drop(ndf[(ndf['category_b'] == '볼링장') & ~(ndf['category_s'].isin(['볼링장']))].index)
    ndf5 = ndf4.drop(ndf[(ndf['category_b'] == '수영장') & ~(ndf['category_s'].isin(['수영장', '부속건물', '스포츠센터', '스포츠시설', '수영']))].index)
    ndf6 = ndf5.drop(ndf[(ndf['category_b'] == '농구장') & ~(ndf['category_s'].isin(['농구장', '부속건물', '부속시설', '스포츠시설', '구민체육센터']))].index)
    ndf7 = ndf6.drop(ndf[(ndf['category_b'] == '야구장') & ~(ndf['category_s'].isin(['스크린야구장', '야구장', '야구연습장']))].index)
    ndf8 = ndf7.drop(ndf[(ndf['category_b'] == '자전거') & ~(ndf['category_s'].isin(['따릉이']))].index)
    ndf9 = ndf8.drop(ndf[(ndf['category_b'] == '골프') & ~(ndf['category_s'].isin(['스크린골프장', '실내골프연습장', '골프연습장', '실외골프연습장']))].index)
    ndf10 = ndf9.drop(ndf[(ndf['category_b'] == '테니스') & ~(ndf['category_s'].isin(['스포츠,오락', '부속건물', '부속시설', '테니스', '스쿼시장', '스포츠시설']))].index)
    ndf11 = ndf10.drop(ndf[(ndf['category_b'] == '전시') & ~(ndf['category_s'].isin(['자동차', '박람회,전시회', '전시관', '과학관']))].index)
    ndf12 = ndf11.drop(ndf[(ndf['category_b'] == '영화관') & ~(ndf['category_s'].isin(['영화관', '복합문화공간']))].index)
    ndf13 = ndf12.drop(ndf[(ndf['category_b'] == '공연장') & ~(ndf['category_s'].isin(['공연장', '공연,연극시설', '문화,예술회관', '복합문화공간']))].index)
    ndf14 = ndf13.drop(ndf[(ndf['category_b'] == '사진') & ~(ndf['category_s'].isin(['사진,스튜디오', '셀프,대여스튜디오']))].index)
    ndf15 = ndf14.drop(ndf[(ndf['category_b'] == '보드게임') & ~(ndf['category_s'].isin(['보드카페']))].index)
    ndf16 = ndf15.drop(ndf[(ndf['category_b'] == '공예') & ~(ndf['category_s'].isin(['미술,공예품', '액자,표구', '도자기', '뜨개', '공방', '전통공예', '공예', '수예,자수', '금속공예', '퀼트', '리본공예', '비즈공예', '유리공예']))].index)
    ndf17 = ndf16.drop(ndf[(ndf['category_b'] == '댄스') & ~(ndf['category_s'].isin(['댄스스포츠', '댄스학원', '무용,댄스', '무용,발레']))].index)
    ndf18 = ndf17.drop(ndf[(ndf['category_b'] == '노래방') & ~(ndf['category_s'].isin(['노래방']))].index)
    ndf19 = ndf18.drop(ndf[(ndf['category_b'] == '방탈출') & ~(ndf['category_s'].isin(['방탈출카페']))].index)
    ndf20 = ndf19.drop(ndf[(ndf['category_b'] == 'pc방') & ~(ndf['category_s'].isin(['PC방']))].index)
    ndf21 = ndf20.drop(ndf[(ndf['category_b'] == '도서관') & ~(ndf['category_s'].isin(['도서관', '군구립도서관', '어린이도서관', '전문도서관']))].index)
    ndf22 = ndf21.drop(ndf[(ndf['category_b'] == '스터디') & ~(ndf['category_s'].isin(['장소대여', '스터디카페']))].index)
    ndf23 = ndf22.drop(ndf[(ndf['category_b'] == '헬스') & ~(ndf['category_s'].isin(['헬스장', '스포츠시설']))].index)
    ndf24 = ndf23.drop(ndf[(ndf['category_b'] == '요가') & ~(ndf['category_s'].isin(['요가원', '요가', '필라테스', '요가,명상']))].index)

    ndf24.to_csv('placecrolling/test/linklist_seoul.csv', index=False)
