import time

import numpy as np
from django.http import JsonResponse
from django.views.decorators.csrf import csrf_exempt

import face.views
import text.views


# Create your views here.
@csrf_exempt
def getStress1(request):
    img = request.FILES['faceImage']
    print(img)
    try:
        face_dict = face.views.getFaceStress(img)
    except:
        return JsonResponse("Face not found", safe=False)  # 입력받은 이미지에서 얼굴을 찾지 못할 시 Face not Found 반환
    print(face_dict)
    sorted_item = sorted(face_dict, key=lambda x: x['confidence'], reverse=True)
    max_item = sorted_item[0]
    second_item = None
    face_stress = 0
    if max_item['label'] in ['기쁨']:  # 반환받은 감정과 정확도를 이용하여 각 감정별로 가중치를 다르게 두어 스트레스 수치 계산
        face_stress = max_item['confidence']
    elif max_item['label'] in ['당황', '중립']:
        base_stress = max_item['confidence'] * 0.6
        second_item = sorted_item[1]

        if second_item['label'] in ['기쁨']:
            face_stress = base_stress + second_item['confidence']
        elif second_item['label'] in ['불안']:
            face_stress = base_stress + ((100 - max_item['confidence'] - second_item['confidence']) * 0.5)
        elif second_item['label'] in ['슬픔', '상처']:
            face_stress = base_stress + ((100 - max_item['confidence'] - second_item['confidence']) * 0.3)
        elif second_item['label'] in ['분노']:
            face_stress = base_stress + ((100 - max_item['confidence'] - second_item['confidence']) * 0.1)
        else:
            third_item = sorted_item[2]
            base_stress = base_stress + second_item['confidence'] * 0.6
            if third_item['label'] in ['기쁨']:
                face_stress = base_stress + third_item['confidence']
            elif third_item['label'] in ['불안']:
                face_stress = base_stress + ((100 - max_item['confidence'] - second_item['confidence'] - third_item['confidence']) * 0.5)
            elif third_item['label'] in ['슬픔', '상처']:
                face_stress = base_stress + ((100 - max_item['confidence'] - second_item['confidence'] - third_item['confidence']) * 0.3)
            elif third_item['label'] in ['분노']:
                face_stress = base_stress + ((100 - max_item['confidence'] - second_item['confidence'] - third_item['confidence']) * 0.1)
            second_item = third_item
    elif max_item['label'] in ['불안']:
        face_stress = (150 - max_item['confidence']) * 0.5
    elif max_item['label'] in ['슬픔', '상처']:
        face_stress = (130 - max_item['confidence']) * 0.5
    elif max_item['label'] in ['분노']:
        face_stress = (100 - max_item['confidence']) * 0.5
    if max_item['label'] not in ['당황', '중립']:
        label = max_item['label']
        confidence = max_item['confidence']
        print(f'{label} {confidence}%')
    else:
        label = f'{max_item["label"]}[{second_item["label"]}]'
        confidence = max_item['confidence']
        print(f'{label} {confidence}[{second_item["confidence"]}]')

    with open('face/img/' + img.name, 'wb') as f:  # 입력받은 이미지를 저장
        for chunk in img.chunks():
            f.write(chunk)

    ditca = {"face_score": 100 - face_stress, "label": label, "confidence": confidence}  # 표정 분석에 대한 정보를 dictionary로 반환

    return JsonResponse(ditca, safe=False)


@csrf_exempt
def getStress2(request):  # 일기를 분석하여 일기 스트레스 수치 및 스트레스 총합 계산
    today = time.strftime('%Y-%m-%d', time.localtime(time.time()))
    time.sleep(3)
    face_stress = float(request.POST['face_score'])
    print(face_stress)
    diary_text = request.POST['text']  # 텍스트 데이터 받기
    print(diary_text)
    slider_stress = int(request.POST['mood']) * 20
    userId = request.POST['memberId']


        # predict_load_data 함수를 사용하여 감정 분석을 수행하고, 결과를 diary_stress_results에 저장합니다.
    diary_stress_results = text.views.predict_load_data(diary_text)
        # 결과값이 0과 1 사이의 점수로 나오므로, 이를 100점 만점으로 변환하여 스트레스 점수로 사용합니다.
    diary_stress = round((1-diary_stress_results) * 100)  # 스트레스 점수 계산



    print(diary_stress)
    diary_stress = int(diary_stress)
    print(face_stress, diary_stress, slider_stress)
    total_stress = round((face_stress * 0.3 + diary_stress * 0.6 + slider_stress * 0.1) / 10, 2)
    print(total_stress)

    dicta = {'total_score': total_stress, 'label': request.POST['label'], 'confidence': request.POST['confidence'], 'date': today, 'diary_score': diary_stress}

    return JsonResponse(dicta, safe=False)
