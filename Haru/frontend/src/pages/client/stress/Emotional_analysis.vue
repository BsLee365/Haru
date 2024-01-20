<template>
  <div class="stress-container">
    <!-- 진행바 영역 시작 -->
    <div class="pro-bar">
      <div class="pro-bar-box">
        <div class="row">
          <div class="col">
            <ul id="progress-bar" class="progressbar">
              <li
                v-for="(item, index) in steps"
                :key="index"
                :class="{ active: index < activeStep }"
              >
                <span
                  :class="{
                    'span-bold': index + 1 <= activeStep,
                    'span-thin': index !== activeStep,
                  }"
                  >{{ item }}</span
                >
              </li>
            </ul>
          </div>
        </div>
      </div>
    </div>
    <!-- 진행바 영역 끝 -->

    <!-- 카드 전체 영역 -->
    <div class="reg-container">
      <!-- 카드 영역 1 -->
      <div class="reg-box">
        <div class="reg-box-card">
          <!-- 설명 영역 -->
          <div class="reg-info">
            <div class="reg-info-box">
              <img
                src="@/img/FaceRegistration/image3-unscreen.gif"
                width="200"
              />
              <div class="reg-desc">
                <div class="reg-title">
                  <span class="desc-title">지금의 나를 남겨 주세요!</span>
                </div>
                <div class="reg-tip">
                  <p>Tip: 얼굴은 정면이 보여야</p>
                  <p>인식 확률이 올라갑니다.</p>
                </div>
              </div>
            </div>
          </div>
          <!-- 설명 영역 끝 -->
          <!-- ------------------------------------------------------------------------ -->
          <!-- 입력 영역 -->
          <div class="stress-input-area">
            <div
              class="input-pic-box upload-grid"
              :class="{ 'input-pic-box-dragndrop': isDrag }"
            >
              <div class="upload-area-container">
                <div class="upload-area-box1">
                  <img src="@/img/FaceRegistration/camera.png" width="150" />
                </div>
                <div class="upload-area-box2">
                  <span id="face-pic-name">{{ fileName }}</span>
                </div>
                <div class="upload-area-box3">
                  <label
                    for="upload-btn"
                    id="upload-btn-label"
                    @drop.prevent="dropInputTag($event)"
                    @dragover="dragover"
                    @dragleave="isDrag = false"
                    >얼굴 등록하기</label
                  >
                  <input type="file" id="upload-btn" @change="fileChanged" />
                </div>
              </div>
            </div>
            <!--업로드 시킬 영역-->
            <div
              class="input-pic-box img-upload-area"
              @drop.prevent="dropInputTag($event)"
              @dragover="dragover"
              @dragleave="isDrag = false"
            ></div>
          </div>
          <!-- 입력영역 끝 -->
        </div>
        <div class="next-button">
          <button class="big-ctlbtn select-btn" id="preStep" @click="slideCard">
            다음단계
          </button>
        </div>
      </div>
      <!-- 카드 영역 끝 1-->

      <!-- 카드 영역 2 -->
      <div class="reg-box">
        <div class="reg-box-card">
          <!-- 설명 영역 -->
          <div class="reg-info">
            <div class="reg-info-box">
              <img
                src="@/img/FaceRegistration/darily--unscreen.gif"
                width="300"
              />
              <div class="reg-desc">
                <div class="reg-title">
                  <span class="desc-title">지금의 나를 기록해 주세요!</span>
                </div>
                <div class="reg-tip">
                  <p>Tip: 자신의 기분을 자세하게</p>
                  <p>기록 할수록 정확도가 올라가요!</p>
                </div>
              </div>
            </div>
          </div>
          <!-- 설명 영역 끝 -->
          <!-- 입력 영역 -->
          <div class="stress-input-area">
            <div class="input-area-box">
              <div class="mood-scale">
                <div class="mood-scale-1">
                  나의 기분은 지금 &nbsp;
                  <span
                    class="badge rounded-pill"
                    :class="moodColor"
                    id="mood"
                    >{{ moodText }}</span
                  >이에요.
                </div>
                <div class="mood-scale-2">
                  <input
                    type="range"
                    min="1"
                    max="5"
                    value="3"
                    step="1"
                    id="myRange"
                    @change="updateMood"
                    v-model="changeMood"
                    :class="'facebgicon' + changeMood"
                  />
                </div>
              </div>
            </div>
          </div>
          <!-- 입력영역 끝 -->
        </div>
        <!-- 다음단계 버튼 필요시 활성화 -->
        <div class="next-button">
          <button class="big-ctlbtn cancle-btn" @click="returnCard">
            이전단계
          </button>
          <button class="big-ctlbtn select-btn" @click="slideCard">
            일기쓰기
          </button>
        </div>
      </div>
      <!-- 카드 영역 끝 2 -->

      <!-- 카드 영역 3 -->
      <div class="reg-box">
        <div class="reg-box-card">
          <!-- 설명 영역 -->
          <div class="reg-info">
            <div class="reg-info-box">
              <img
                src="@/img/FaceRegistration/darily--unscreen.gif"
                width="300"
              />
              <div class="reg-desc">
                <div class="reg-title">
                  <span class="desc-title">지금의 나를 기록해 주세요!</span>
                </div>
                <div class="reg-tip">
                  <p>Tip: 자신의 기분을 자세하게</p>
                  <p>기록 할수록 정확도가 올라가요!</p>
                </div>
              </div>
            </div>
          </div>
          <!-- 설명 영역 끝 -->
          <!-- 입력 영역 -->
          <div class="stress-input-area">
            <div class="input-area-box">
              <!-- 일기 시작 -->
              <div class="input-diary">
                <div class="diary-date-area">
                  <h2 id="diary-date-h2">2024년 01월 07일</h2>
                </div>
                <div class="diary-title-box">
                  <input
                    type="text"
                    name="diary-title"
                    id="diary-title"
                    placeholder="제목"
                    maxlength="30"
                  />
                </div>
                <div class="diary-content-box">
                  <textarea
                    name="diary-content"
                    id="diary-content"
                    placeholder="어떤 일이 있었나요?"
                    maxlength="1000"
                  ></textarea>
                </div>
              </div>
              <!-- 일기 끝 -->
            </div>
          </div>
          <!-- 입력영역 끝 -->
        </div>
        <!-- 다음단계 버튼 필요시 활성화 -->
        <div class="next-button">
          <button class="big-ctlbtn cancle-btn" @click="returnCard">
            이전단계
          </button>
          <button class="big-ctlbtn select-btn" @click="submitCard">
            분석하기
          </button>
          <button class="big-ctlbtn else-btn" @click="modal_click">
            모달 임시버튼
          </button>
        </div>
        <div>
          <!-- <button @click="loadingOpen()">로딩 임시버튼</button> -->
        </div>

        <!-- 로딩화면 1 -->
        <div class="loading" style="display: none">
          <div class="container-3">
            <div class="wave" id="wave1"></div>
            <div class="wave" id="wave2"></div>
            <div class="wave" id="wave3"></div>
            <div class="wave" id="wave4"></div>
            <div class="wave" id="wave5"></div>
            <div class="wave" id="wave6"></div>
            <div class="wave" id="wave7"></div>
            <div class="wave" id="wave8"></div>
            <div class="object">
              <div class="boat"></div>
              <div class="flag"></div>
            </div>
          </div>
          <h3 id="analyzing">분석중입니다.</h3>
          <h3 id="almost-end">거의 다 왔어요!</h3>
        </div>
      </div>
      <!-- 카드 영역 끝 3-->
    </div>
    <!-- 모달 영역-------------------->
    <WarnFaceModal v-if="modal_Check" @hideModal="modal_click" />
  </div>
  <!-- 카드 전체 영역 끝 -->
  <!-- 최종 결과화면 들어올 자리 -->
  <!-- 최종 결과 화면은 페이지 이동 -->
</template>

<script>
import WarnFaceModal from "@/components/client/stress/WarnFaceModal.vue";
export default {
  name: "EmotionalAnalysis",
  data() {
    return {
      modal_Check: false,
      fileName: "끌어서 사진 올려놓기!",
      currentIndex: 0,
      changeMood: 3,
      moodText: "보통",
      moodColor: "mood-normal",
      isDrag: false,
      //진행바
      steps: ["얼굴 등록하기", "척도 등록하기", "일기 쓰기", "완료!"],
      activeStep: 1,
      // 전송할 폼 정보들
      formData: new FormData(),
    };
  },
  created() {
    this.bgImage();
  },
  methods: {
    bgImage() {
      var newImage = "type2";
      this.$emit("bgImage", newImage);
    },
    //분석하기 버튼
    submitCard() {
      this.$router.push("/Total_stress");

      // 기분 척도
      // this.formData.append("mood", this.changeMood);
      // 일기
      // this.formData.append(
      //   "title",
      //   document.getElementById("diary-title").value
      // );
      // 일기 내용
      // this.formData.append(
      //   "content",
      //   document.getElementById("diary-content").value
      // );
      // 일기 날짜
      // this.formData.append(
      //   "date",
      //   document.getElementById("diary-date-h1").innerText
      // );
      // for (let value of this.formData.values()) {
      //   console.log(value);
      // }
      // 분석하기 버튼 클릭시 로딩화면 들어갈 자리
    },
    dragover(event) {
      event.preventDefault();
      this.isDrag = true;
    },
    //이미지 파일 드래그앤 드롭
    dropInputTag(event) {
      // 유사 배열을 배열로 변환
      let file = Array.from(event.dataTransfer.files, (v) => v)[0];
      this.fileName = file.name;
      // 사진 파일을 formData에 추가
      this.formData.append("faceImage", file);
      event.preventDefault();
      this.isDrag = false;
    },
    // 진행바 다음 단계로 이동
    next() {
      if (this.activeStep < this.steps.length - 1) {
        this.activeStep++;
      }
    },
    // 진행바 이전 단계로 이동
    back() {
      if (this.activeStep > 0) {
        this.activeStep--;
      }
    },
    modal_click() {
      this.modal_Check = !this.modal_Check;
    },
    fileChanged(event) {
      this.fileName = event.target.files[0].name;
    },
    slideCard() {
      const cards = document.querySelectorAll(".reg-box");
      if (this.currentIndex < cards.length - 1) {
        this.currentIndex++; // 다음 카드로 이동
        this.updateCardsPosition();
        this.next();
      }
    },
    returnCard() {
      if (this.currentIndex > 0) {
        this.currentIndex--; // 이전 카드로 이동
        this.updateCardsPosition();
        this.back();
      }
    },
    updateCardsPosition() {
      const cards = document.querySelectorAll(".reg-box");
      cards.forEach((card) => {
        card.style.transform = `translateX(-${100 * this.currentIndex}%)`;
      });
    },
    updateMood() {
      if (this.changeMood === "1") {
        this.moodText = "매우 나쁨";
        this.moodColor = "mood-bad";
      } else if (this.changeMood === "2") {
        this.moodText = "나쁨";
        this.moodColor = "mood-little-bad";
      } else if (this.changeMood === "3") {
        this.moodText = "보통";
        this.moodColor = "mood-normal";
      } else if (this.changeMood === "4") {
        this.moodText = "좋음";
        this.moodColor = "mood-good";
      } else if (this.changeMood === "5") {
        this.moodText = "매우 좋음";
        this.moodColor = "mood-very-good";
      }
    },
  },
  components: {
    WarnFaceModal,
  },
};
</script>

<style scoped>
@import url("@/css/client/stress/emotional_analysis.css");

.big-ctlbtn {
  margin: 0 10px;
}
#diary-title {
  width: 100%;
}
#diary-content {
  width: 100%;
  height: 400px;
  padding: 30px;
  background-size: cover;
  line-height: 27px;
}
.input-diary {
  align-items: normal;
}
/* 스크롤바 제거 */
::-webkit-scrollbar {
  display: none;
}
</style>
