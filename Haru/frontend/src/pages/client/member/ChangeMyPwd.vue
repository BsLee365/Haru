<template>
  <!-- 내 정보 수정 화면 이동 전 비밀번호 검사 화면 -->
  <div class="container1">
    <div class="userConfirm-box">
      <!-- title -->
      <div class="main-title-center">
        <h2>비밀번호 재설정</h2>
      </div>
      <form>
        <div class="pw-change-input-area">
          <label for="password" class="password-label">재설정할 비밀번호</label>
          <input type="password" id="password" />
          <p id="userComfirm-pwd-msg"></p>
        </div>

        <div class="pw-change-input-area">
          <label for="password" class="password-label">비밀번호 확인</label>
          <input type="password" id="password-check" />
          <p id="userComfirm-pwdcheck-msg"></p>
        </div>
        <button
          class="big-ctlbtn update-btn"
          type="button"
          id="userConfirm-btn"
          @click="resetPwd"
        >
          비밀번호 재설정
        </button>
      </form>
    </div>
  </div>
</template>
<script>
import axios from "axios";

export default {
  name: "UserConfirmation",
  data() {
    return {
      formData: new FormData(),
      isLoggedIn: false,
    };
  },
  created() {
    // 페이지가 로드될 때 초기 이미지 설정
    this.bgImage();
    this.checkTokenUserId();
  },
  methods: {
    // 해당 화면 Background 이미지 설정
    bgImage() {
      var newImage = "type1";
      this.$emit("bgImage", newImage);
    },
    // localStorage에 userId가 있는지 확인
    checkTokenUserId() {
      if (localStorage.getItem("userId") === null) {
        this.$router.push("/login");
      }
    },

    // 회원정보 수정 화면으로 이동
    userConfirmBtn() {
      this.$router.push("/updateMyInfo");
    },
    // 비밀번호 재설정
    resetPwd() {
      const pwd = document.getElementById("password").value;
      const pwdCheck = document.getElementById("password-check").value;

      if (pwd === "" || pwd.length < 8) {
        document.getElementById("userComfirm-pwd-msg").innerHTML =
          "비밀번호는 영문 대문자와 숫자, 특수문자를 포함한 8자리 이상이어야 합니다.";
        document.getElementById("userComfirm-pwd-msg").style.display = "block";
        document.getElementById("password").focus();
        document.getElementById("userComfirm-pwd-msg").style.color = "red";
        return false;
      } else {
        document.getElementById("userComfirm-pwd-msg").style.display = "none";
      }
      if (pwdCheck !== pwd) {
        document.getElementById("userComfirm-pwdcheck-msg").innerHTML =
          "비밀번호가 일치하지 않습니다.";
        document.getElementById("userComfirm-pwdcheck-msg").style.display =
          "block";
        document.getElementById("userComfirm-pwdcheck-msg").style.color = "red";
        return false;
      } else {
        document.getElementById("userComfirm-pwdcheck-msg").style.display =
          "none";
      }

      // FormData에 값 초기화
      this.formData = new FormData();

      const userId = localStorage.getItem("userId");

      // FormData에 값 추가
      this.formData.append("id", userId);
      this.formData.append("pwd", pwd);

      // 비밀번호 재설정 API 호출
      axios
        .post(
          `http://${process.env.VUE_APP_BACK_END_URL}/api/auth/resetPwd`,
          this.formData,
          {
            headers: {
              "Content-Type": "application/json",
              Accept: "application/json",
            },
          }
        )
        .then((res) => {
          console.log(res);
          if (res.data == 1) {
            alert("비밀번호가 변경되었습니다.");
            localStorage.removeItem("userId");
            this.$router.push("/login");
          } else {
            alert("비밀번호 변경에 실패하였습니다.");
          }
        });
    },
  },
};
</script>
<style scoped>
@import url("@/css/client/member/userConfirmation.css");
.pw-change-input-area {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  height: 80px;
  width: 100%;
}

.pw-change-input-area:first-child {
  margin-bottom: 30px;
}
</style>
