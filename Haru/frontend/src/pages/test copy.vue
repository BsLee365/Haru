<template>
  <div>
    <form action="">
      <input type="file" name="img" id="img" @change="selectFile" />
      <input type="text" v-model="textData" placeholder="일기를 입력하세요" />
      <!-- 텍스트 입력란 추가 -->
      <button type="button" name="send" id="send" @click="sendImg">전송</button>
    </form>
    <h1>{{ label }}</h1>
    <h1>{{ confidence }}</h1>
    <h1>{{ face_score }}</h1>
    <h1>{{ diary_score }}</h1>
    <h1>{{ total_score }}</h1>
    <h1>{{ date }}</h1>
  </div>
</template>
<script>
import axios from "axios";

export default {
  name: "test",
  data() {
    return {
      total_score: "", // 스트레스 총합
      face_score: "", // 얼굴 스트레스
      diary_score: "", // 일기 스트레스
      label: "", // 얼굴 라벨
      confidence: "", // 얼굴 라벨 정확도
      date: "", // 분석일자
      formData: new FormData(),
      textData: "", // 텍스트 데이터를 위한 데이터 바인딩
    };
  },
  methods: {
    selectFile(event) {
      this.formData.append("img", event.target.files[0]);
    },
    sendImg() {
      this.total_score = "이미지 처리중";
      this.face_score = "";
      this.diary_score = "";
      this.label = "";
      this.confidence = "";
      this.date = "";

      // 텍스트 데이터를 FormData에 추가
      this.formData.append("text", this.textData);

      axios
        .post("http://192.168.0.227:8000/calculate/getStress1", this.formData, {
          headers: {
            "Content-Type": "multipart/form-data",
          },
        })
        .then((res) => {
          this.total_score = "일기 처리중";
          console.log("Received data from getStress1:", res.data); // 이미지 처리 결과 확인
          if (res.data === "Face not found") {
            // 얼굴 못 찾았을 경우
            this.total_score = "얼굴을 찾을 수 없습니다.";
          } else {
            this.label = "분석된 감정 : " + res.data["label"];
            this.confidence = "정확도 : " + res.data["confidence"];
            this.face_score = "얼굴 스트레스 : " + res.data["face_score"];
            this.formData.append("face_score", res.data["face_score"]);
            this.formData.append("label", res.data["label"]);
            this.formData.append("confidence", res.data["confidence"]);
            axios
              .post(
                "http://192.168.0.227:8000/calculate/getStress2", // 이미지 처리 끝 일기 처리 시작
                this.formData,
                {
                  headers: {
                    "Content-Type": "multipart/form-data",
                  },
                }
              )
              .then((res) => {
                // 일기 처리 끝
                console.log("Received data from getStress2:", res.data); // 일기 처리 결과 확인
                this.diary_score = "일기 스트레스 : " + res.data["diary_score"];
                this.total_score = "스트레스 총합 : " + res.data["total_score"];
                this.date = "분석된 날짜 : " + res.data["date"];
              });
          }
        });
    },
  },
};
</script>
