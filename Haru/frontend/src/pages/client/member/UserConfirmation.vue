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
          @click="confirmPwd"
        >
          개인 정보 수정
        </button>
      </form>
    </div>
  </div>
</template>
<script>
import axios from "axios";
import { ref, onMounted } from "vue";
import { jwtDecode } from "jwt-decode";

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
  methods: {
    bgImage() {
      var newImage = "type1";
      this.$emit("bgImage", newImage);
    },

    userConfirmBtn() {
      this.$router.push("/updateMyInfo");
    },
    confirmPwd(event) {
      event.preventDefault();
      const pwd = document.getElementById("password").value;

      const data = {
        userId: this.data.id,
        password: pwd,
      };

      axios
        .post(
          `http://${process.env.VUE_APP_BACK_END_URL}/member/userConfirm`,
          data,
          {
            headers: {
              Authorization: `Bearer ${this.AccessToken}`,
              "Content-Type": "application/json",
            },
          }
        )
        .then((res) => {
          console.log(res);
          if (res.data === 1) {
            this.$router.push("/updateMyInfo");
          } else {
            alert("비밀번호가 일치하지 않습니다.");
          }
        });
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
