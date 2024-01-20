<template>
  <div class="container1">
    <!-- 장소보여주기 -->
    <div>
      <div class="main-title">
        <h2>닉네임 님이 찜한 장소</h2>
      </div>
      <div class="myFavorite-place">
        <div
          class="myFavorite-place-card"
          v-for="(item, idx) in myFaboritePlace"
          :key="idx"
        >
          <div class="'food-img">
            <img class="heart-img" src="@/img/Total_stress/img/image 47.png" />
            <img :src="item.image" alt="" class="place-card" />
          </div>
          <div class="food-desc">
            <div class="food-desc-box">
              <div class="food-title">
                <h4>{{ item.stname }}</h4>
              </div>
              <div class="hash-tag">
                <span class="review-score">★ {{ item.score }}</span>
              </div>
              <div class="food-detail">
                <span class="food-address">주소: {{ item.address }}</span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      isLoggedIn: false,
      AccessToken: "",
      myFaboritePlace: [
        {
          image: require("@/img/Total_stress/food/buger.jpg"),
          stname: "버거킹",
          score: 4.32,
          address:
            "주소: 부산광역시 강서구 녹산산단382로14번가길 10~29번지(송정동)",
        },
        {
          image: require("@/img/Total_stress/food/buger.jpg"),
          stname: "버거킹",
          score: "별점 없음",
          address:
            "주소: 부산광역시 강서구 녹산산단382로14번가길 10~29번지(송정동)",
        },
        {
          image: require("@/img/Total_stress/food/buger.jpg"),
          stname: "버거킹",
          score: 4.32,
          address:
            "주소: 부산광역시 강서구 녹산산단382로14번가길 10~29번지(송정동)",
        },
        {
          image: require("@/img/Total_stress/food/buger.jpg"),
          stname: "버거킹",
          score: "별점 없음",
          address:
            "주소: 부산광역시 강서구 녹산산단382로14번가길 10~29번지(송정동)",
        },
        {
          image: require("@/img/Total_stress/food/buger.jpg"),
          stname: "버거킹",
          score: 4.32,
          address:
            "주소: 부산광역시 강서구 녹산산단382로14번가길 10~29번지(송정동)",
        },
        {
          image: require("@/img/Total_stress/food/buger.jpg"),
          stname: "버거킹",
          score: 4.32,
          address:
            "주소: 부산광역시 강서구 녹산산단382로14번가길 10~29번지(송정동)",
        },
        {
          image: require("@/img/Total_stress/food/buger.jpg"),
          stname: "버거킹",
          score: 4.32,
          address:
            "주소: 부산광역시 강서구 녹산산단382로14번가길 10~29번지(송정동)",
        },
        {
          image: require("@/img/Total_stress/food/buger.jpg"),
          stname: "버거킹",
          score: 4.32,
          address:
            "주소: 부산광역시 강서구 녹산산단382로14번가길 10~29번지(송정동)",
        },
        {
          image: require("@/img/Total_stress/food/buger.jpg"),
          stname: "버거킹",
          score: 4.32,
          address:
            "주소: 부산광역시 강서구 녹산산단382로14번가길 10~29번지(송정동)",
        },
      ],
    };
  },
  created() {
    this.bgImage();
    this.getToken();
  },
  methods: {
    bgImage() {
      var newImage = "type5";
      this.$emit("bgImage", newImage);
    },
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
