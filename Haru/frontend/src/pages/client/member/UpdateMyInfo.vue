<template>
  <div class="container1">
    <div>
      <DeleteMyInfoModal
        :deleteMyInfoModal="deleteMyInfoModal"
        @modalClose="toggleDeleteMyInfoModal"
        :userId="userId"
      />
    </div>
    <div>
      <form
        method="POST"
        autocomplete="off"
        @submit.prevent="onSubmit"
        class="myInfo-form"
      >
        <!-- 타이틀 -->
        <div class="main-title-center">
          <h2>내 정보 수정</h2>
        </div>

        <!-- 아이디 -->
        <div class="info-input-container">
          <!-- label -->
          <div class="label-area">
            <label for="userId" class="mustInput">아이디</label>
          </div>
          <div class="input-area">
            <input
              class="input-text readonly-input"
              type="text"
              id="userId"
              readonly
              :value="userId"
            />
            <input type="hidden" id="userId-v" :value="userId" />
          </div>
        </div>

        <!-- 닉네임 확인 -->
        <div class="info-input-container">
          <div class="label-area">
            <label for="nickname" class="mustInput">닉네임</label>
          </div>
          <div class="input-area">
            <input
              class="input-text"
              type="text"
              name="nickname"
              id="nickname"
              placeholder="한글, 영문 대소문자, 숫자만 사용 가능"
              :value="nickname"
            />
            <button
              class="input-in-btn"
              id="nickname-check"
              @click="nicknameCheck"
            >
              중복확인
            </button>
            <div class="error-msg-area">
              <p style="display: none" id="nicknameCheck-msg" class="msg"></p>
            </div>
          </div>
        </div>

        <!-- 이름 -->
        <div class="info-input-container">
          <div class="label-area">
            <label for="name" class="mustInput">이름</label>
          </div>
          <div class="input-area">
            <input
              class="input-text"
              type="text"
              name="name"
              id="name"
              placeholder="이름 입력"
              :value="name"
            />
          </div>
          <div class="error-msg-area">
            <p style="display: none" id="nameCheck-msg" class="msg"></p>
          </div>
        </div>

        <!-- 이메일 -->
        <div class="info-input-container">
          <div class="label-area">
            <label for="email" class="mustInput">이메일</label>
          </div>
          <div class="input-area">
            <input
              class="input-text"
              type="email"
              @keydown.enter.prevent="handleEnter"
              id="email"
              placeholder="이메일 입력"
              :value="email"
            />
            <button class="input-in-btn" id="email-ckeck" @click="emailCheck">
              중복확인
            </button>
            <div class="error-msg-area">
              <p style="display: none" id="emailCheck-msg" class="msg"></p>
            </div>
          </div>
        </div>

        <!-- 이메일 인증 번호 -->
        <div class="info-input-container">
          <div class="label-area">
            <label for="code" class="mustInput">인증번호</label>
          </div>
          <div class="input-area">
            <input
              class="input-text"
              type="text"
              @keydown.enter.prevent="handleEnter"
              id="code"
              placeholder="인증번호 입력"
            />
          </div>
          <div class="error-msg-area">
            <p style="display: none" id="Code-msg" class="msg"></p>
          </div>
        </div>

        <div class="myinfo-btn-area">
          <input
            type="button"
            id="backbtn"
            @click="backbtn"
            value="뒤로가기"
            class="big-ctlbtn cancle-btn"
          />
          <input
            type="button"
            id="update-my-info"
            @click="submit"
            value="정보수정"
            class="big-ctlbtn select-btn"
          />

          <input
            type="button"
            id="delete-my-info"
            @click="toggleDeleteMyInfoModal"
            value="탈퇴하기"
            class="big-ctlbtn delete-btn"
          />
        </div>
      </form>
    </div>
  </div>
</template>
<script>
import axios from "axios";
import DeleteMyInfoModal from "@/components/client/member/DeleteUserModal.vue";
import { ref, onMounted } from "vue";
import { jwtDecode } from "jwt-decode";

export default {
  name: "UpdateMyInfo",
  data() {
    return {
      deleteMyInfoModal: false,
      isLoggedIn: false,
      AccessToken: "",
      formData: new FormData(),
    };
  },
  components: {
    DeleteMyInfoModal,
  },
  created() {
    this.bgImage();
  },
  setup() {
    const isLoggedIn = ref(false);
    const data = ref([]);
    const userId = ref("");
    const nickname = ref("");
    const email = ref("");
    const name = ref("");

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
    // 토큰 디코딩
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
      getUserData();
    });
    // 유저 정보 가져오기
    const getUserData = () => {
      const userIdData = {
        id: data.value.id,
      };

      axios
        .post(
          `http://${process.env.VUE_APP_BACK_END_URL}/member/userData`,
          userIdData,
          {
            headers: {
              "Content-Type": "application/json",
              Accept: "application/json",
            },
          }
        )
        .then((res) => {
          console.log(res);

          userId.value = res.data.id;
          nickname.value = res.data.nickname;
          email.value = res.data.email;
          name.value = res.data.name;
        });
    };

    return { logout, data, getUserData, userId, nickname, email, name }; // Return data in the setup function
  },
  methods: {
    bgImage() {
      var newImage = "type1";
      this.$emit("bgImage", newImage);
    },
    // 토큰 가져오기
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
    // 닉네임 중복확인
    nicknameCheck() {
      const nickname = document.getElementById("nickname").value;

      if (nickname === "") {
        document.getElementById("nicknameCheck-msg").innerHTML =
          "닉네임은 필수 입력 사항입니다.";
        document.getElementById("nicknameCheck-msg").style.display = "block";
        return false;
      } else {
        document.getElementById("nicknameCheck-msg").style.display = "none";
      }
      if (nickname >= 20) {
        document.getElementById("nicknameCheck-msg").innerHTML =
          "닉네임은 20자리 이하로 입력해주세요.";
        document.getElementById("nicknameCheck-msg").style.display = "block";
        return false;
      } else {
        document.getElementById("nicknameCheck-msg").style.display = "none";
      }
      // 닉네임 중복확인 API 호출
      axios
        .get(
          `http://${process.env.VUE_APP_BACK_END_URL}/api/auth/${nickname}/nicknameCheck`
        )
        .then((res) => {
          if (res.data == 0) {
            document.getElementById("nicknameCheck-msg").innerHTML =
              "사용 가능한 닉네임입니다.";
            document.getElementById("nicknameCheck-msg").style.display =
              "block";
            document.getElementById("nicknameCheck-msg").style.color = "green";
            this.nicknameCheckBoolean = true;
          } else {
            document.getElementById("nicknameCheck-msg").innerHTML =
              "이미 사용중인 닉네임입니다.";
            document.getElementById("nicknameCheck-msg").style.display =
              "block";
          }
        })
        .catch((error) => {
          console.error("API 호출 에러:", error);
          return false;
        });
    },
    // 이메일 중복확인 및 인증번호 발송
    emailCheck() {
      const email = document.getElementById("email").value;
      axios
        .post(
          `http://${process.env.VUE_APP_BACK_END_URL}/api/auth/emailCheck`,
          {
            email: email,
          }
        )
        .then((res) => {
          if (res.data == 1) {
            alert("인증 번호가 발송되었습니다.");
            document.getElementById("emailCheck-msg").style.display = "none";
          } else {
            document.getElementById("emailCheck-msg").innerHTML =
              "이미 사용중인 이메일입니다.";
            document.getElementById("emailCheck-msg").style.display = "block";
          }
        })
        .catch((error) => {
          alert("인증 번호 발송에 오류가 발생했습니다.");
          console.error("API 호출 에러", error);
          return false;
        });
    },

    handleEnter(event) {
      event.preventDefault();
    },

    // 내 정보 수정
    submit() {
      // FormData에 값 초기화
      this.formData.delete("nickname", "");
      this.formData.delete("email", "");
      this.formData.delete("name", "");

      // 값 가져오기
      const userId = document.getElementById("userId-v").value;
      const nickname = document.getElementById("nickname").value;
      const email = document.getElementById("email").value;
      const name = document.getElementById("name").value;

      // FormData에 값 추가
      this.formData.append("id", userId);
      this.formData.append("nickname", nickname);
      this.formData.append("email", email);
      this.formData.append("name", name);

      // 정규식
      var nicknameRegex = /^[a-zA-Z0-9ㄱ-ㅎ가-힣]+$/;
      var emailRegex = /^[\w-.]+@([\w-]+\.)+[\w-]{2,5}$/;

      // 값 유효성 검사
      if (nickname === "") {
        document.getElementById("nicknameCheck-msg").innerHTML =
          "닉네임은 필수 입력 사항입니다.";
        document.getElementById("nicknameCheck-msg").style.display = "block";
      } else {
        document.getElementById("nicknameCheck-msg").style.display = "none";
      }
      if (name === "") {
        document.getElementById("nameCheck-msg").innerHTML =
          "이름은 필수 입력 사항입니다.";
        document.getElementById("nameCheck-msg").style.display = "block";
      } else {
        document.getElementById("nameCheck-msg").style.display = "none";
      }
      if (email === "") {
        document.getElementById("emailCheck-msg").innerHTML =
          "이메일은 필수 입력 사항입니다.";
        document.getElementById("emailCheck-msg").style.display = "block";
      } else {
        document.getElementById("emailCheck-msg").style.display = "none";
      }

      if (userId === "" || email === "" || name === "" || nickname === "") {
        return false;
      }

      if (nicknameRegex.test(nickname) === false) {
        alert("닉네임은 한글, 영문 대소문자, 숫자만 사용 가능합니다.");
        document.getElementById("nickname").focus();
      } else if (emailRegex.test(email) === false) {
        alert("이메일 형식이 올바르지 않습니다.");
        document.getElementById("email").focus();
      } else if (this.idCheckBoolean === false) {
        document.getElementById("idCheck-msg").innerText =
          "아이디 중복확인을 해주세요.";
        document.getElementById("idCheck-msg").style.display = "block";
        document.getElementById("userId").focus();
      } else if (this.idCheckBoolean === true) {
        document.getElementById("idCheck-msg").style.display = "none";
      } else if (this.nicknameCheckBoolean === false) {
        document.getElementById("nickname").focus();
      } else if (this.nicknameCheckBoolean === true) {
        document.getElementById("nicknameCheck-msg").style.display = "none";
      }
      // 인증코드 확인 메서드 호출
      axios
        .post(
          `http://${process.env.VUE_APP_BACK_END_URL}/api/auth/emailCheck/certification`,
          {
            email: email,
            code: document.getElementById("code").value,
          }
        )
        .then((res) => {
          console.log(res);
          if (res.data == 1) {
            document.getElementById("Code-msg").innerText =
              "인증번호가 일치합니다.";
            document.getElementById("Code-msg").style.color = "green";
            document.getElementById("Code-msg").style.display = "block";
            // 내 정보 수정 API 호출
            axios
              .post(
                `http://${process.env.VUE_APP_BACK_END_URL}/api/auth/updateMyInfo`,
                this.formData,
                {
                  headers: {
                    "Content-Type": "application/json",
                  },
                }
              )
              .then((res) => {
                alert(res);
                if (res.data == 1) {
                  this.$router.push("/");
                }
              })
              .catch((error) => {
                console.error("API 호출 에러:", error);
                return false;
              });
          } else {
            document.getElementById("Code-msg").innerText =
              "인증번호가 일치하지 않습니다.";
            document.getElementById("Code-msg").style.display = "block";
          }
        });
    },
    // 뒤로가기
    backbtn() {
      this.$router.push("/Mypage");
    },
    toggleDeleteMyInfoModal() {
      this.deleteMyInfoModal = !this.deleteMyInfoModal;
    },
  },
};
</script>

<style></style>

<style scoped>
@import url("@/css/client/member/updateMyInfo.css");
</style>
