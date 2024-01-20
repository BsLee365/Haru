<template>
  <div class="container-header">
    <!-- 로고, 메인 GNB -->
    <header>
      <div class="left-side-area">
        <div class="logo">
          <a href="/"><img src="@/assets/logo/logo_검정.png" /></a>
        </div>
        <div class="dropdown">
          <button class="dropbtn dropbtn-1">나의 스트레스</button>
          <div class="dropdown-content dropdown-1">
            <a href="/Emotional_analysis">나의 스트레스 분석하기</a>
            <a href="/Emotional_report">나의 스트레스 종합 보고서</a>
          </div>
        </div>
        <div class="dropdown">
          <button class="dropbtn">내 장소</button>
          <div class="dropdown-content">
            <a href="MyPlaceDiary">추천 리스트/일기</a>
            <a href="/MyFavoritePlace">찜한 장소</a>
          </div>
        </div>
        <div class="dropdown">
          <button class="dropbtn">피드</button>
          <div class="dropdown-content">
            <a href="/feed">피드</a>
            <a href="/MyFeed">내 피드</a>
            <a href="insertFeed">피드 작성하기</a>
          </div>
        </div>
        <div class="dropdown">
          <button class="dropbtn">마이페이지</button>
          <div class="dropdown-content">
            <a href="/MyPage">마이페이지</a>
            <a href="/SelectHobby">취미 및 관심사</a>
            <a href="/userConfirmation">회원정보 수정</a>
          </div>
        </div>
        <div class="dropdown">
          <button class="dropbtn">Q&A</button>
          <div class="dropdown-content">
            <a href="/QnA">Q&A 게시판</a>
            <a href="/WriteQnA">Q&A 등록</a>
          </div>
        </div>
      </div>
      <div class="login-ul-box">
        <ul class="login-ul">
          <div v-if="!isLoggedIn">
            <li><a href="/login">로그인</a></li>
            <li><a href="/signup">회원가입</a></li>
          </div>
          <div v-else>
            <li>
              <span id="userNickname-box"
                >{{ data.nickname }}님 환영합니다.</span
              >
              <button @click="logout">로그아웃</button>
            </li>
          </div>
        </ul>
      </div>
    </header>
  </div>
</template>
<script>
import axios from "axios";
import { ref, onMounted } from "vue";
import { jwtDecode } from "jwt-decode";

export default {
  name: "Header",
  data() {
    return {};
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

    return { isLoggedIn, logout, data }; // Return data in the setup function
  },
};
</script>
<style scoped>
.container-header {
  margin: 0px;
  padding: 20px 228px;
  z-index: 10;
}

header {
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.left-side-area {
  display: flex;
}

.logo {
  z-index: 3;
  margin-right: 60px;
}

img {
  width: 130px;
}

/* Dropdown Button */
.dropbtn {
  padding: 16px;
  border: none;
  cursor: pointer;
  display: flex;
  position: relative;
  width: 160px;
  justify-content: center;
  background-color: transparent;
  font-size: 20px;
  /* color: white; */
  border-top-left-radius: 10px;
  border-top-right-radius: 10px;
}

.dropbtn:hover {
  color: white;
}

.dropbtn-1 {
  width: 210px;
}

.dropdown {
  display: inline-block;
  z-index: 1;
}

.dropdown-content {
  display: none;
  position: absolute;
  background-color: black rgba(0, 0, 0, 0.5);
  min-width: 160px;
  z-index: 1;
}

.dropdown-1 {
  min-width: 210px;
}

.dropdown-content a {
  color: white;
  padding: 12px 16px;
  text-decoration: none;
  display: block;
  text-align: center;
  background-color: rgba(36, 36, 36, 0.6);
}

.dropdown-content a:last-child {
  border-bottom-right-radius: 10px;
  border-bottom-left-radius: 10px;
}

.dropdown-content a:hover {
  background-color: #adadad;
}

.dropdown:hover .dropdown-content {
  display: block;
}

.dropdown:hover .dropbtn {
  background-color: rgba(36, 36, 36, 0.6);
}

/* 로그인 회원가입 */
.login-ul-box {
  display: inline-flex;
}

.login-ul-box > .login-ul div {
  display: flex;
}

.login-ul-box > .login-ul > div > li {
  margin-left: 20px;
}

.login-ul-box > .login-ul > div > li > a {
  font-size: 20px;
  color: black;
  white-space: nowrap;
}

.login-ul-box ul li a:hover {
  color: #fba883;
}

button {
  font-size: 20px;
  color: black;
  white-space: nowrap;
  border: none;
  background: none;
}

#userNickname-box {
  font-size: 20px;
  color: black;
  white-space: nowrap;
  margin-right: 10px;
}
</style>
