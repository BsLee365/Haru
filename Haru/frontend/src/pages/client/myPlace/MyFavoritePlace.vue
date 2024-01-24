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
          <div class="'food-img">
            <img class="heart-img" src="@/img/Total_stress/img/image 47.png" />
            <img :src="item" alt="" class="place-card" />
          </div>
          <div class="food-desc">
            <div class="food-desc-box">
              <div class="food-title">
                <h4>{{ item[2] }}</h4>
              </div>
              <div class="hash-tag">
                <span class="review-score">★ {{ item[3] }}</span>
              </div>
              <div class="food-detail">
                <span class="food-address">주소: {{ item[4] }}</span>
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
      getData();
    });
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
          res.data[0].forEach((element) => {
            placeData.value.push(element);
          });
        })
        .catch((error) => {
          // 요청 실패 시 처리 로직
          console.error(error);
        });
    };

    return { logout, data, getData, placeData }; // Return data in the setup function
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
