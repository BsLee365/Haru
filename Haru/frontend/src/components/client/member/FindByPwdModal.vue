<template>
  <!-- 비밀번호 찾기 모달창 -->
  <div
    class="FindMyAccount-modal"
    v-if="modalOpen"
    @click="$emit('modalClose')"
  >
    <div class="findMyAccount-modal-content" @click.stop>
      <div class="feed-detail-modal-btn">
        <!-- X 버튼 -->
        <button id="common-modal-close" @click="$emit('modalClose')"></button>
      </div>

      <div class="findMyAccount-title-area">
        <h2>비밀번호 재설정</h2>
      </div>

      <div class="findModalContent">
        <form autocomplete="off">
          <!-- 아이디 -->
          <div class="info-input-container">
            <div class="label-area">
              <label for="id">아이디</label>
            </div>
            <div class="input-area">
              <input
                class="input-text"
                type="text"
                name="id"
                id="id"
                placeholder="아이디 입력"
              />
            </div>
          </div>

          <!-- 이름 -->
          <div class="info-input-container">
            <div class="label-area">
              <label for="name">이름</label>
            </div>
            <div class="input-area">
              <input
                class="input-text"
                type="text"
                name="name"
                id="name"
                placeholder="이름 입력"
              />
            </div>
          </div>

          <!-- 이메일 -->
          <div class="info-input-container">
            <div class="label-area">
              <label for="email">이메일</label>
            </div>
            <div class="input-area">
              <input
                class="input-text"
                type="email"
                id="email"
                placeholder="이메일 입력"
              />
              <button class="input-in-btn" id="email-ckeck" @click="findByPwd">
                인증
              </button>
              <div class="error-msg-area">
                <p style="display: none" id="emailCheck-msg" class="msg"></p>
              </div>
            </div>
          </div>

          <!-- 이메일 인증 번호 -->
          <div class="info-input-container">
            <div class="label-area">
              <label for="emailCheck">인증번호</label>
            </div>
            <div class="input-area">
              <input
                class="input-text"
                type="text"
                id="emailCheck"
                placeholder="인증번호 입력"
              />
            </div>
            <div class="error-msg-area">
              <p style="display: none" id="Code-msg" class="msg"></p>
            </div>
          </div>

          <div class="btn-area">
            <button class="big-ctlbtn insert-btn" @click="submit">
              비밀번호 재설정
            </button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>
<script>
import axios from "axios";

export default {
  name: "FindByIdModal",
  data() {
    return {};
  },
  props: {
    modalOpen: Boolean,
  },
  methods: {
    findByPwd(event) {
      event.preventDefault();
      if (document.getElementById("id").value == "") {
        alert("아이디를 입력해주세요.");
        event.preventDefault();
        event.stopPropagation();
        return;
      } else if (document.getElementById("name").value == "") {
        alert("이름을 입력해주세요.");
        event.preventDefault();
        event.stopPropagation();
        return;
      } else if (document.getElementById("email").value == "") {
        alert("이메일을 입력해주세요.");
        event.preventDefault();
        event.stopPropagation();
        return;
      }

      this.formData = new FormData();

      const name = document.getElementById("name").value;
      const email = document.getElementById("email").value;
      const userId = document.getElementById("id").value;

      this.formData.append("userId", userId);
      this.formData.append("username", name);
      this.formData.append("email", email);

      axios
        .post(
          `http://${process.env.VUE_APP_BACK_END_URL}/api/auth/findByPwd`,
          this.formData,
          {
            headers: {
              "Content-Type": "application/json", // 요청의 미디어 타입
              Accept: "application/json", // 서버에서 지원하는 미디어 타입
            },
          }
        )
        .then((res) => {
          console.log(res);
          if (res.data == 1) {
            alert("이메일로 아이디를 전송하였습니다.");
          } else {
            alert("이메일을 다시 확인해주세요.");
          }
        });
    },
    submit(event) {
      event.preventDefault();
      if (document.getElementById("id").value == "") {
        document.getElementById("id").focus();
        event.preventDefault();
        event.stopPropagation();
        alert("아이디를 입력해주세요.");
        return;
      } else if (document.getElementById("name").value == "") {
        document.getElementById("name").focus();
        event.preventDefault();
        event.stopPropagation();
        alert("이름을 입력해주세요.");
        return;
      } else if (document.getElementById("email").value == "") {
        document.getElementById("email").focus();
        event.preventDefault();
        event.stopPropagation();
        alert("이메일을 입력해주세요.");
        return;
      } else if (document.getElementById("emailCheck").value == "") {
        document.getElementById("emailCheck").focus();
        event.preventDefault();
        event.stopPropagation();
        alert("인증번호를 입력해주세요.");
        return;
      }

      const code = document.getElementById("emailCheck").value;
      this.formData.append("code", code);
      axios
        .post(
          `http://${process.env.VUE_APP_BACK_END_URL}/api/auth/findById/certification`,
          this.formData,
          {
            headers: {
              "Content-Type": "application/json",
              Accept: "application/json",
            },
          }
        )
        .then((res) => {
          console.log("res" + res);
          if (res.data != 0) {
            alert("인증되었습니다.");
            localStorage.setItem("userId", res.data);
            this.$router.push("/ChangeMyPwd");
          } else {
            alert("인증번호를 다시 확인해주세요.");
          }
        });
    },
  },
};
</script>

<style scoped>
.info-input-container {
  padding-bottom: 30px;
}
.btn-area {
  width: fit-content;
  margin: 0 auto;
}
</style>
