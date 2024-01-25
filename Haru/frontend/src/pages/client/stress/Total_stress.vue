<template>
  <div class="stress-container">
    <!--  진행바 영역 시작-->
    <div class="pro-bar">
      <div class="pro-bar-box">
        <div class="row">
          <div class="col">
            <ul id="progress-bar" class="progressbar">
              <li class="active">
                <span class="span-bold">얼굴 등록하기</span>
              </li>
              <li class="active">
                <span class="span-bold">척도 등록하기</span>
              </li>
              <li class="active"><span class="span-bold">일기쓰기</span></li>
              <li class="active"><span class="span-bold">완료!</span></li>
            </ul>
          </div>
        </div>
      </div>
    </div>
    <!--  진행바 영역 끝-->
    <div class="result-card-container">
      <div class="result-card-box">
        <div class="stress-area">
          <div class="result-comment">
            이범석님의 스트레스 수치는
            <span class="badge rounded-pill bg-danger custom-bedge" v-show="stressRate <= 60">정상</span>
            <span class="badge rounded-pill bg-danger custom-bedge" v-show="61 <= stressRate && stressRate <= 80">주의</span>
            <span class="badge rounded-pill bg-danger custom-bedge" v-show="81 <= stressRate ">심각</span>
            입니다.
          </div>
          <!-- 스트레스 수치 바 시작 -->
          <div class="rating-bar-container">
            <div class="percentage-box">
              <ul class="rating-percent">
                <li class="rating-number"></li>
                <li class="rating-number">
                  <span class="percentage">10%</span>
                </li>
                <li class="rating-number">
                  <span class="percentage">20%</span>
                </li>
                <li class="rating-number">
                  <span class="percentage">30%</span>
                  <span class="fourchette stress-nomal">정상</span>
                </li>
                <li class="rating-number">
                  <span class="percentage">40%</span>
                </li>
                <li class="rating-number">
                  <span class="percentage">50%</span>
                </li>
                <li class="rating-number">
                  <span class="percentage">60%</span>
                  <span class="fourchette stress-warning">주의</span>
                </li>
                <li class="rating-number">
                  <span class="percentage">70%</span>
                </li>
                <li class="rating-number">
                  <span class="percentage">80%</span>
                  <span class="fourchette stress-danger">심각</span>
                </li>
                <li class="rating-number">
                  <span class="percentage">90%</span>
                </li>
                <li class="rating-number"></li>
              </ul>
            </div>
            <div class="rating-bar">
              <div class="rating-bar-gauge" :style="{ width: gaugeWidth }">
                <span
                  class="percentage-number"
                  v-if="20 <= parseInt(gaugeWidth)"
                  >스트레스 수치 {{ stressRate }}%</span
                >
              </div>
            </div>
          </div>
        </div>
        <!-- 스트레스 수치 바 끝 -->
        <!-- 장소추천 영역 시작 -->
        <div class="recommend-area">
          <div class="recommend-area-box">
            <div class="recommend-comment">
              <span
                >이범석님의 스트레스 수치로 ‘하루의 여울’이 추천한
                장소에요.</span
              >
            </div>
            <div class="recommend-button">
              <button
                id="scroll-left-button"
                class="swiper-btn"
                data-v-35d05e88=""
                @click="scrollLeft"
              >
                &lt;
              </button>
              <button
                id="scroll-right-button"
                class="swiper-btn"
                data-v-35d05e88=""
                @click="scrollRight"
              >
                &gt;
              </button>
            </div>
          </div>
          <!-- 장소보여주기 -->
          <div class="recommend-place">
            <!--장소 카드 시작-->
            <div class="place-card" v-for="(item, index) in recommendPlace.data" :key="index">
              <div class="'food-img">
                <!-- 하트(찜) 하기 -->
<!--                <img-->
<!--                  class="heart-img"-->
<!--                  src="@/img/Total_stress/img/image 47.png"-->
<!--                />-->
                  <img class="heart-img cursor-p"
                       :src="isInWishList(item) ? existImage : noImage"
                       @click="toggleWish(item)"
                  />

                <!-- 장소 이미지 없는 경우 -->
                <img
                    v-show="item.place_img === null"
                    src="@/img/Total_stress/img/no_img.png"
                    alt="no_image"
                    class="place-card"
                />
                <!-- 장소 이미지 있는 경우 -->
                <img
                  v-show="item.place_img != null"
                  :src="item.place_img"
                  alt="no_image"
                  class="place-card"
                />
              </div>
              <div class="food-desc">
                <div class="food-desc-box">
                  <div class="food-title">
                    <h4>{{item.place_name}}</h4>
                  </div>
                  <div class="hash-tag">
                    <span class="review-score">★ {{item.place_score/10}}점</span>
                  </div>
                  <div class="food-detail">
                    <span class="food-address">{{item.place_address}}</span>
                  </div>
                </div>
              </div>

            </div>
          <!--  장소 카드 끝-->
          </div>
          <!-- 장소보여주기 끝 -->
        </div>
        <div class="step-btn">
          <button
            @click="toMyPlaceDiary"
            class="big-ctlbtn cancle-btn"
            id="prev-button"
          >
            이전 추천 리스트
          </button>
          <button @click="toReport" class="big-ctlbtn select-btn" id="next-button">
            나의 스트레스 보고서
          </button>
          <a
              href="/"
            class="big-ctlbtn insert-btn"
            id="main-button"
          >
            메인으로 이동
          </a>
        </div>
        <!-- 장소추천 영역 끝 -->
      </div>
    </div>
  </div>
</template>
<script>
import axios from "axios";
import {onMounted, ref} from "vue";
import {jwtDecode} from "jwt-decode";

export default {
  name: "total_stress",
  data() {
    return {
      gaugeWidth: "0%", // 초기 게이지
      ratNumber: [10, 20, 30, 40, 50, 60, 70, 80, 90],
      stressRate: 0,

      // 장소
      recommendPlace : null,

      // 스트레스 수치
      stressScore : null,

      // 찜 목록
      selectedWishList : [],

      // 이미지
      noImage : require("@/img/Total_stress/img/image 47.png"),
      existImage: require("@/img/Total_stress/img/total_stress_heart.png"),

    };
  },
  created() {
    // background-image 설정
    this.bgImage();

    this.recommendPlace = JSON.parse(localStorage.getItem("recommendPlace"));
    this.stressScore = localStorage.getItem("stressScore");
  },
  methods: {
    // background-image 설정
    bgImage() {
      var newImage = "type2";
      this.$emit("bgImage", newImage);
    },
    scrollLeft() {
      var slider = document.querySelector(".recommend-place");
      slider.scrollTo({
        left: 0,
        behavior: "smooth",
      });
    },
    scrollRight() {
      const slider = document.querySelector(".recommend-place");
      slider.scrollTo({
        left: slider.scrollWidth,
        behavior: "smooth",
      });
    },
    toMyPlaceDiary() {
      this.$router.push("/MyPlaceDiary");
    },
    toReport() {
      this.$router.push("/Emotional_report")
    },
    // 찜 기능 토글
    toggleWish(item) {
      // console.log('찜기능에 넣을 place : ' + item.place_num);

      if (this.selectedWishList.indexOf(item.place_num) < 0) {
        this.selectedWishList.push(item.place_num);
        // console.log('추가함 ! : ' + this.selectedWishList);

        axios.post(`http://${process.env.VUE_APP_BACK_END_URL}/wishList/addWishPlace`, {
          userid: this.data.id,
          place: item
        })
            .then(() => {
              // console.log(res.data);
            })
            .catch(err => {
              console.log('등록 에러 ' + err);
            })

      } else {
        this.selectedWishList.splice(this.selectedWishList.indexOf(item.place_num), 1);
        // console.log('잘라냄 ! : ' + this.selectedWishList);

        axios.post(`http://${process.env.VUE_APP_BACK_END_URL}/wishList/deleteWishByPlaceNum`,{
          place_num: item.place_num
        })
            .then(() => {
              console.log('삭제됨');
              // this.$emit('update-all-rec-list');
            })
            .catch(err => {
              console.log('삭제 에러 ' + err);
            })

      }
    },

    // 화면에 띄우기 용
    isInWishList(item) {
      if (this.selectedWishList.indexOf(item.place_num) < 0) {
        return false;
      }
      return true;
    }
  },
  mounted() {
    let currentWidth = 0;
    const targetWidth = this.stressScore; // 목표 값
    this.stressRate = targetWidth;

    const animationDuration = 5000;
    const interval = 25;

    const step = (targetWidth - currentWidth) / (animationDuration / interval);

    const animate = () => {
      if (currentWidth < targetWidth) {
        currentWidth += step+0.2;
        this.gaugeWidth = currentWidth + "%";
        this.stressRate = Math.round(currentWidth);
        requestAnimationFrame(animate);
      } else {
        this.gaugeWidth =targetWidth + "%"; // 애니메이션이 완료되면 정확한 값으로 설정
        this.stressRate = Number(targetWidth).toFixed(2);
      }
    };
    animate();

    // 로컬 스토리지 삭제
    // localStorage.removeItem("recommendPlace");
    // localStorage.removeItem("stressScore");

  },
  setup() {
    const isLoggedIn = ref(false);
    const data = ref([]);

    const getToken = () => {
      const token = localStorage.getItem("jwtToken");
      isLoggedIn.value = token ? true : false;
    };

    const logout = () => {
      axios
          .get(`http://${process.env.VUE_APP_BACK_END_URL}/api/auth/logout`)
          .then((res) => {
            if (res.data == "Logout") {
              localStorage.removeItem("jwtToken");
              window.location.href = "/login";
            }
          });
    };

    const decodeToken = (token) => {
      if (token == null) return false;
      const decoded = jwtDecode(token);
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
};
</script>

<style scoped>
@import url("@/css/client/stress/total_stress.css");
#main-button {
  text-align: center;
  line-height: 50px;
}
</style>
