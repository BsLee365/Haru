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
                  <input
                    type="file"
                    id="upload-btn"
                    @change="fileChanged($event)"
                  />
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
                  <h2 id="diary-date-h2">{{ today }}</h2>
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
        </div>
        <div>
          <!-- <button @click="loadingOpen()">로딩 임시버튼</button> -->
        </div>
      </div>
      <!-- 카드 영역 끝 3-->
    </div>
    <!-- 모달 영역-------------------->
    <WarnFaceModal v-if="modal_Check" @hideModal="modal_click" />
    <!-- 로딩화면 1 -->
    <div class="loading" v-show="activeLoading">
      <div class="loading-box">
        <h1 v-if="loadingParam === 1">
          얼굴 분석중입니다...
          <span class="emoticon">얼굴 분석중입니다...</span>
        </h1>
        <h1 v-else-if="loadingParam === 2">
          일기 분석중입니다...{{ currentEmoji }}
        </h1>
        <h1 v-else-if="loadingParam === 3">
          거의 다 됐어요! <span class="emoticon">{{ currentEmoji }}</span>
        </h1>
      </div>
    </div>
  </div>
  <!-- 카드 전체 영역 끝 -->
  <!-- 최종 결과화면 들어올 자리 -->
  <!-- 최종 결과 화면은 페이지 이동 -->
</template>

<script>
import WarnFaceModal from "@/components/client/stress/WarnFaceModal.vue";
import { onMounted, ref } from "vue";
import axios from "axios";
import { jwtDecode } from "jwt-decode";
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
      today: null,
      // 전송할 폼 정보들
      formData: new FormData(),

      // 파일
      file: null,

      //이모티콘
      emoticon: ["📖", "✏️", "💌", "🖍️", "📒", "📔", "✒️"],
      currentEmot: 0,

      //로딩
      activeLoading: false,
      // 로딩 3은 장소 추천시 보여줌.
      loadingParam: 1,

      //일기
      dirayTitle: null,
      diaryContent: null,

      // 스트레스 결과를 위한 데이터들
      diaryFigure: 0,
      faceFigure: 0,
      stressScore: 0, // 최종 스트레스 수치
      stressCdate: null,

      // 현재 클라이언트 주소
      ipAddress: window.location.host.split(":")[0],
    };
  },
  created() {
    this.bgImage();

    var today = new Date();
    var year = today.getFullYear();
    var month = ("0" + (today.getMonth() + 1)).slice(-2);
    var day = ("0" + today.getDate()).slice(-2);
    this.today = year + "년 " + month + "월 " + day + "일";
  },
  methods: {
    bgImage() {
      var newImage = "type2";
      this.$emit("bgImage", newImage);
    },
    //분석하기 버튼
    submitCard() {
      // 일기 제목, 내용
      this.dirayTitle = document.getElementById("diary-title").value;
      this.diaryContent = document.getElementById("diary-content").value;

      const cards = document.querySelectorAll(".reg-box");

      // 제출하기 전 유효성 검사
      if (
        this.fileName === "끌어서 사진 올려놓기!" ||
        this.fileName === "" ||
        this.file === null
      ) {
        alert("사진을 등록해주세요😅");
        //페이지 이동
        cards.forEach((card) => {
          card.style.transform = `translateX(-0%)`;
        });
        // 현재 페이지 초기화
        this.currentIndex = 0;
        // 진행바 스텝 초기화
        this.activeStep = 1;

        return false;
      }
      if (this.dirayTitle === "" || this.diaryContent === "") {
        alert("제목 또는 내용을 입력해주세요😅");

        return false;
      }

      this.formData.append("text", this.diaryContent);
      // 일기 날짜
      this.formData.append("date", this.today);
      // 얼굴 이미지
      this.formData.append("faceImage", this.file);
      // 멤버 아이디
      this.formData.append("memberId", this.data.id);
      // 기분 척도
      this.formData.append("mood", this.changeMood);
      //분석하기 버튼 클릭시 로딩화면 들어갈 자리

      // 로딩창 활성화
      this.activeLoading = true;
      // 로딩 멘트 1
      this.loadingParam = 1;
      // axios를 통해 장고모델에 전달 (출력 결과는 모두 console.log로 찍음.)
      axios
        .post(`http://${process.env.VUE_APP_DJANGO_CROSS_URL}/calculate/getStress1`, this.formData, {
          headers: {
            "Content-Type": "multipart/form-data",
          },
        })
        .then((res) => {
          if (res.data === "Face not found") {
            // 얼굴 못찾았을 경우
            this.activeLoading = false;
            this.modal_Check = true;
            //페이지 이동
            cards.forEach((card) => {
              card.style.transform = `translateX(-0%)`;
            });
            // 현재 페이지 초기화
            this.currentIndex = 0;
            // 진행바 스텝 초기화
            this.activeStep = 1;

            //제출하지 않음.
            return false;
          } else {
            // 로딩 멘트 2
            this.loadingParam = 2;
            console.log("getStress1로부터 받은 데이터: ", res.data);

            // 얼굴 감정
            console.log(res.data["label"]);
            // 얼굴 감정 정확도
            console.log(res.data["confidence"]);
            // 얼굴 점수
            this.faceFigure = res.data["face_score"];
            console.log(res.data["face_score"]);

            // 폼 데이터 추가
            this.formData.append("face_score", res.data["face_score"]);
            this.formData.append("label", res.data["label"]);
            this.formData.append("confidence", res.data["confidence"]);

            axios
              .post(
                `http://${process.env.VUE_APP_DJANGO_CROSS_URL}/calculate/getStress2`, // 이미지 처리 끝 일기 처리 시작
                this.formData,
                {
                  headers: {
                    "Content-Type": "multipart/form-data",
                  },
                }
              )
              .then((res) => {
                console.log("getStress2로부터 받은 데이터: ", res.data); // 일기 처리 결과 확인
                //일기 점수
                this.diaryFigure = res.data["diary_score"];
                // 스트레스 총합
                this.stressScore = res.data["total_score"];
                // 스트레스 측정일자
                this.stressCdate = res.data["date"];

                //일기 저장
                axios
                  .post(
                    `http://${this.ipAddress}/Haru/diary/saveDiary`,
                    {
                      user_id: this.data.id,
                      diary_title: this.dirayTitle,
                      diary_context: this.diaryContent,
                    },
                    {
                      headers: {
                        "Content-Type": "application/json",
                      },
                    }
                  )
                  .then((res) => {
                    console.log(res);
                    console.log("성공!");
                    console.log(this.data.id);

                    // 스트레스 측정 저장
                    axios
                      .post(`http://${this.ipAddress}/Haru/stress/saveStress`, {
                        diaryfigure: this.diaryFigure,
                        facefigure: this.faceFigure,
                        stressscore: this.stressScore,
                        stresscdate: null,
                        userid: this.data.id,
                      })
                      .then(() => {
                        console.log("스트레스 측정 저장 완료");
                      });

                    // 장소추천 알고리즘 axios
                    axios
                      .post(
                        `http://${this.ipAddress}/Haru/stress/recommend`,
                        {
                          userid: this.data.id,
                          stresssocre: this.stressScore,
                        },
                        {
                          headers: {
                            "Content-Type": "application/json",
                          },
                        }
                      )
                      .then((recommend) => {
                        console.log("성공!");
                        // 장소 들어갈 곳
                        console.log("추천 받은 장소");
                        console.log(recommend);

                        // 로컬에 저장
                        localStorage.setItem(
                          "recommendPlace",
                          JSON.stringify(recommend)
                        );
                        localStorage.setItem(
                          "stressScore",
                          this.stressScore * 10
                        );

                        this.$router.push("/Total_stress");
                      });
                  });
                //최종 스트레스로 이동.
              });
          }
        });
    },
    dragover(event) {
      event.preventDefault();
      this.isDrag = true;
    },
    //이미지 파일 드래그앤 드롭
    dropInputTag(event) {
      // 유사 배열을 배열로 변환
      this.file = Array.from(event.dataTransfer.files, (v) => v)[0];
      this.fileName = this.file.name;
      //파일 유효성 검사
      const correctForm = /(.*?)\.(jpg|jpeg|gif|bmp|png)$/;
      if (!this.fileName.match(correctForm)) {
        this.fileName = "끌어서 사진 올려놓기!";
        this.file = null;
        alert("이미지 파일만 업로드 가능합니다.");
        this.isDrag = false;
        return false;
      }
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
      this.file = event.target.files[0];
      this.fileName = this.file.name;

      //파일 유효성 검사
      const correctForm = /(.*?)\.(jpg|jpeg|gif|bmp|png)$/;
      if (!this.fileName.match(correctForm)) {
        this.fileName = "끌어서 사진 올려놓기!";
        this.file = null;
        alert("이미지 파일만 업로드 가능합니다.");
        this.isDrag = false;
        return false;
      }
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

    // 이모티콘 변경 메소드
    changeEmoji() {
      setTimeout(() => {
        this.currentEmot = (this.currentEmot + 1) % this.emoticon.length;
      }, 500);
    },
  },
  // 로그인 토큰
  setup() {
    const isLoggedIn = ref(false); // Use ref to create reactive isLoggedIn
    const data = ref([]); // Use ref to create reactive data

    const getToken = () => {
      const token = localStorage.getItem("jwtToken");
      isLoggedIn.value = token ? true : false;
    };

    const logout = () => {
      axios
        .get(`http://${process.env.VUE_APP_BACK_END_URL}/api/auth/logout`)
        .then((res) => {
          if (res.data === "Logout") {
            localStorage.removeItem("jwtToken");
            window.location.href = "/login";
          }
        });
    };

    const decodeToken = (token) => {
      console.log(token);
      if (token == null) return false;
      const decoded = jwtDecode(token);
      console.log(JSON.stringify(decoded));
      data.value = decoded; // Use data.value to set the value of the ref
      return decoded;
    };

    onMounted(() => {
      getToken();
      const token = localStorage.getItem("jwtToken");
      decodeToken(token);
    });

    return { logout, data }; // Return data in the setup function
  },
  computed: {
    // 로딩화면 이모티콘 변경
    currentEmoji() {
      return this.emoticon[this.currentEmot];
    },
  },
  mounted() {
    // 1초 주기로 이모티콘 변경
    setInterval(this.changeEmoji, 1000);
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
.emoticon {
  font-size: 2.5rem;
}
</style>
