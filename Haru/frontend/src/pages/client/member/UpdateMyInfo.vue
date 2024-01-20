<template>
  <div class="container1">
    <div>
      <DeleteMyInfoModal
        :deleteMyInfoModal="deleteMyInfoModal"
        @modalClose="toggleDeleteMyInfoModal"
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
              value="rhdudwnd82"
            />
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
              value="개구쟁이"
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
              value="고영중"
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
              value="rhdudwnd82@naver.com"
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
            @click="updateMyInfo"
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

export default {
  name: "UpdateMyInfo",
  data() {
    return {
      userId: "",
      nickname: "",
      email: "",
      name: "",
      deleteMyInfoModal: false,
      isLoggedIn: false,
      AccessToken: "",
    };
  },
  components: {
    DeleteMyInfoModal,
  },
  created() {
    this.bgImage();
    this.getToken();
  },
  methods: {
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
    nicknameCheck() {
      const nickname = document.getElementById("nickname").value;
      if (nickname === "") {
        document.getElementById("nicknameCheck-msg").innerHTML =
          "닉네임은 필수 입력 사항입니다.";
        document.getElementById("nicknameCheck-msg").style.display = "block";
        return false;
      }

      axios
        .post(`http://${process.env.BACK_END_URL}/Haru/nicknameCheck`, {
          nickname: nickname,
        })
        .then((res) => {
          if (res.data) {
            alert("사용 가능한 닉네임입니다.");
          } else {
            alert("이미 사용중인 닉네임입니다.");
          }
        })
        .catch((error) => {
          console.error("API 호출 에러:", error);
          return false;
        });
    },

    async emailCheck() {
      const email = document.getElementById("email").value;
      console.log(process.env);
      axios
        .post(`${process.env.VUE_APP_BACK_END_URL}/emailCheck`, {
          email: email,
        })
        .then((res) => {
          if (res == 1) {
            alert("인증 번호가 발송되었습니다.");
          } else {
            alert("이미 등록된 이메일입니다.");
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
    submit() {
      const userId = document.getElementById("userId").value;
      const nickname = document.getElementById("nickname").value;
      const email = document.getElementById("email").value;
      const name = document.getElementById("name").value;

      var idRegex = /^[a-zA-Z0-9_]+$/;
      var nicknameRegex = /^[a-zA-Z0-9ㄱ-ㅎ가-힣]+$/;
      var emailRegex = /^[\w-.]+@([\w-]+\.)+[\w-]{2,5}$/;

      if (userId === "") {
        document.getElementById("idCheck-msg").innerText =
          "아이디는 필수 입력 사항입니다.";
        document.getElementById("idCheck-msg").style.display = "block";
      }
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

      if (idRegex.test(userId) === false) {
        document.getElementById("userId").focus();
        document.getElementById("idCheck-msg").innerText =
          "아이디는 영문 대소문자와 숫자, _만 사용 가능합니다.";
        document.getElementById("idCheck-msg").style.display = "block";
      } else if (nicknameRegex.test(nickname) === false) {
        alert("닉네임은 한글, 영문 대소문자, 숫자만 사용 가능합니다.");
        document.getElementById("nickname").focus();
      } else if (emailRegex.test(email) === false) {
        alert("이메일 형식이 올바르지 않습니다.");
        document.getElementById("email").focus();
      } else {
        axios
          .post("http://localhost:8090/api/user/signup", {
            userId: userId,
            nickname: nickname,
            email: email,
            name: name,
          })
          .then((res) => {
            if (res.data === 1) {
              alert("회원정보가 변경되어습니다.");
              this.$router.push("/login");
            } else {
              alert("회원정보 변경에 실패했습니다.");
              return false;
            }
          })
          .catch((error) => {
            console.error("API 호출 에러:", error);
            return false;
          });
      }
    },
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
