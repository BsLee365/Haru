<template>
  <!-- 내 정보 수정 화면 이동 전 비밀번호 검사 화면 -->
  <div class="container1">
    <div class="userConfirm-box">
      <!-- title -->
      <div class="main-title-center">
        <h2>개인 정보 확인</h2>
      </div>
      <form>
        <div class="pw-confirm-input-area">
          <label for="password" class="password-label">비밀번호</label>
          <input type="password" id="password" />
          <p id="userComfirm-pwd-msg"></p>
        </div>
        <button
          class="big-ctlbtn update-btn"
          type="submit"
          id="userConfirm-btn"
          @click="userConfirmBtn"
        >
          개인 정보 수정
        </button>
      </form>
    </div>
  </div>
</template>
<script>
export default {
  name: "UserConfirmation",
  data() {
    return {
      AccessToken: "",
      isLoggedIn: false,
    };
  },
  created() {
    // 페이지가 로드될 때 초기 이미지 설정
    this.bgImage();
    this.getToken();
  },
  methods: {
    // 해당 화면 Background 이미지 설정
    bgImage() {
      var newImage = "type1";
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
    userConfirmBtn() {
      this.$router.push("/updateMyInfo");
    },
  },
};
</script>
<style scoped>
@import url("@/css/client/member/userConfirmation.css");
.container1 {
  display: flex;
  align-items: center;
}
</style>
