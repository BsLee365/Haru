import cv2
import numpy as np
import tensorflow.keras as keras
from deepface import DeepFace


# Create your views here.


model1 = keras.models.load_model('face/data/face_model.h5')  # 학습된 모델 load
model1.summary()

def getFaceStress(img):  # 표정을 분석하여 스트레스 수치를 계산하는 함수
    img = np.frombuffer(img.read(), np.uint8)
    img = cv2.imdecode(img, cv2.IMREAD_COLOR)
    img = getFace(img)
    img = resizeImg(img)

    result = predict_emote(img)

    return result


def getFace(img):  # 사진에서 얼굴을 찾아 추출하는 함수

    # SSD 모델을 이용하여 안면 추출
    result = DeepFace.extract_faces(img, detector_backend='ssd')[0]

    # 검출된 안면 좌표 가져오기
    facial_area = result['facial_area']
    print("Facial Area:", facial_area)

    # 검출된 안면 좌표를 정수로 변환
    x, y, w, h = map(int, (facial_area['x'], facial_area['y'], facial_area['w'], facial_area['h']))

    # 이미지에서 안면 부분 자르기
    face_img = img[y:y + h, x:x + w]

    return face_img


def resizeImg(img):  # 입력 받은 이미지를 학습된 모델에 맞게 전처리
    image = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY).astype('float32')
    image = np.array(cv2.resize(image, (48, 48)))
    image = np.expand_dims(image, axis=0)
    image /= 255.0

    return image


def predict_emote(img):

    label_names = ['기쁨', '당황', '분노', '불안', '상처', '슬픔', '중립']  # 모델의 label_list

    predictions = model1.predict(img)[0]
    print(predictions)
    result = [{'label': label_names[i], 'confidence': round(predictions[i] * 100)} for i in range(len(label_names))]  # 각 라벨과 확률을 dictionary로 만들어 리스트로 반환

    return result
