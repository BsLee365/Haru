import pickle
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
df2 = pd.read_csv('text/data/stopword.csv')
stopwords.extend(df['0'].values.tolist())
stopwords.extend(df2['0'].values.tolist())

with open('text/textmodel/tokenizer.pickle', 'rb') as handle:
    tokenizer = pickle.load(handle)

model_path = 'text/textmodel/finaltextmodel1234567.h5'
model = load_model(model_path)

okt = Okt()


def preprocess_text(sentence):
    sentence = re.sub(r'[^ㄱ-ㅎㅏ-ㅣ가-힣 ]', '', sentence)
    sentence = okt.morphs(sentence)
    sentence = [word for word in sentence if not word in stopwords]
    encoded = tokenizer.texts_to_sequences([sentence])
    pad_new = pad_sequences(encoded, maxlen=max_len)
    return pad_new


def predict_load_data(new_sentence):
    new_sentence = preprocess_text(new_sentence)
    text_results = float(model.predict(new_sentence))
    print(f"감정 분석 결과 (0 부정 1긍정) : {text_results:.2f}\n")
    return text_results


@csrf_exempt
def feedKeyword(request):
    feedsentence = request.POST['feedSentence']
    sentence = re.sub(r'[^ㄱ-ㅎㅏ-ㅣ가-힣 ]', '', feedsentence)
    nounkeyword = getNoun(sentence)
    nounsentence = ' '.join(nounkeyword)
    keyword = okt.morphs(nounsentence)
    print(stopwords)
    print(keyword)
    filteredkeyword = [word for word in keyword if word not in stopwords]

    filteredkeyword = list(set(filteredkeyword))
    print(filteredkeyword)

    return JsonResponse(filteredkeyword, safe=False)


def getNoun(sentence):
    kiwi = Kiwi()
    kiwi.analyze(sentence)

    results = []
    result = kiwi.analyze(sentence)
    for token, pos, _, _ in result[0][0]:
        if len(token) != 1 and pos.startswith('N') or pos.startswith('SL'):
            results.append(token)
    return results
