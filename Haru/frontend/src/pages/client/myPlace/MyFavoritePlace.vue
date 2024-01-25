<template>
  <div class="container1">
    <!-- 장소보여주기 -->
    <div>
      <div class="main-title">
        <h2>{{ data.nickname }} 님이 찜한 장소</h2>
      </div>
      <div class="myFavorite-place">
        <div
          class="myFavorite-place-card"
          v-for="(item, idx) in this.placeData"
          :key="idx"
        >
<!--          {{item}}-->
          <div class="food-img">
            <img class="heart-img cursor-p"
                 :src="existImage"
                 @click="toggleWish(item[0], item[3])"
            />

            <a :href="'https://map.naver.com/p/smart-around/place/' + getPlaceLink(item[2])">
              <img v-if="item[1] != null" :src="item[1]" alt="" class="place-card" />
              <img v-else src="@/img/Total_stress/img/no-image.jpg" class="place-card" alt=""/>
            </a>

          </div>
          <div class="food-desc">
            <div class="food-desc-box">
              <div class="food-title">
                <a :href="'https://map.naver.com/p/smart-around/place/' + getPlaceLink(item[2])" target="_blank">
                  <h4>{{ item[3] }}</h4>
                </a>
              </div>
              <div class="hash-tag">
                <!-- .toFixed(1) : 소수점 자리수 표시 format -->
                <span class="review-score">★ {{ (item[4] / 10).toFixed(1) }}</span>
              </div>
              <div class="food-detail">
                <a :href="'https://map.naver.com/p/smart-around/place/' + getPlaceLink(item[2])">
                  <span class="food-address">주소: {{ item[5] }}</span>
                </a>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import { ref, onMounted } from "vue";
import { jwtDecode } from "jwt-decode";

export default {
  data() {
    return {
      isLoggedIn: false,
      AccessToken: "",
      // 하트 이미지
      existImage: require("@/img/Total_stress/img/total_stress_heart.png"),
    };
  },
  created() {
    this.bgImage();
    this.getToken();
  },
  setup() {
    const isLoggedIn = ref(false);
    const data = ref([]);
    const myFaboritePlace = ref({});
    const placeData = ref([]);
    // 토큰 가져오기
    const getToken = () => {
      const token = localStorage.getItem("jwtToken");
      isLoggedIn.value = token ? true : false;
    };
    // 로그아웃 메서드
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
    //  토큰 디코딩
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
      getData();
    });

    // 찜한 장소 가져오기
    const getData = () => {
      const token = localStorage.getItem("jwtToken");
      axios
        .post(
          `http://${process.env.VUE_APP_BACK_END_URL}/wishList/getMyFavoritePlace`,
          data.value,
          {
            headers: {
              Authorization: `Bearer ${token}`,
              "Content-Type": "application/json",
            },
          }
        )
        .then((res) => {
          // 요청 성공 시 처리 로직
          myFaboritePlace.value = res.data.place;
          console.log(myFaboritePlace)
          res.data[0].forEach((element) => {
            placeData.value.push(element);
          });
        })
        .catch((error) => {
          // 요청 실패 시 처리 로직
          console.error(error);
        });
    };

    return { logout, data, getData, placeData };
  },

  methods: {
    bgImage() {
      var newImage = "type5";
      this.$emit("bgImage", newImage);
    },
    //  토큰 가져오기
    getToken() {
      this.AccessToken = localStorage.getItem("jwtToken");
      console.log(this.AccessToken);
      if (this.AccessToken != null) {
        this.isLoggedIn = true;
      } else {
        this.isLoggedIn = false;
        this.$router.push("/login");
      }
    },
    // 찜 기능 토글
    toggleWish(item, place_name) {
      // console.log('placeData!! : ' + this.placeData);

      if (confirm(`[${place_name}] 장소의 찜을 해제할까요?`))
      axios.post(`http://${process.env.VUE_APP_BACK_END_URL}/wishList/deleteWishByPlaceNum`,{
        place_num: item
      })
          .then(() => {
            console.log('삭제됨');
            // this.$emit('update-all-rec-list');
            this.placeData=[];
            this.getData();
          })
          .catch(err => {
            console.log('삭제 에러 ' + err);
          })
    },
    // 링크 주소 변경
    getPlaceLink(link) {
      var mylink = link.split("/");
      if ( mylink[mylink.length-1] === '?entry=ple' ) {
        return  mylink[mylink.length-2]
      } else {
        return mylink[mylink.length-1]
      }
    }
  },
};
</script>
<style scoped>
/* 전체 영역 */
.myFavorite-place {
  width: 1480px;
  display: flex;
  flex-wrap: wrap;
  row-gap: 40px;
  -moz-column-gap: 20px;
  column-gap: 20px;
  margin: 0 auto;
}

/* 카드 하나 */
.myFavorite-place-card {
  width: 280px;
  height: 280px;
  border-radius: 10px;
  transition: box-shadow 0.2s ease-out 0.05s, scale 0.2s;
}
.myFavorite-place-card:hover {
  box-shadow: 0 0 8px 0px rgba(0, 0, 0, 0.3);
  scale: 1.05;
}

/* 제목 영역 */
.main-title {
  width: 1480px;
  margin: 0 auto 30px;
}
</style>
