<template>
  <div class="container1">
    <!-- 이용 약관 모달이랑 구분하기 위한 div -->
    <div>
      <form
        method="POST"
        autocomplete="off"
        @submit.prevent="onSubmit"
        class="myInfo-form"
      >
        <!-- 타이틀 -->
        <div class="main-title-center">
          <h2>회원가입</h2>
        </div>

        <!-- 입력 항목 input -->
        <!-- 아이디 -->
        <div class="info-input-container">
          <!-- label -->
          <div class="label-area">
            <label for="userId" class="mustInput">아이디</label>
          </div>
          <div class="input-area">
            <!-- input, button -->
            <input
              class="input-text"
              type="text"
              @keydown.enter.prevent="handleEnter"
              id="userId"
              placeholder="아이디 입력"
              v-model="userId"
            />
            <button class="input-in-btn" id="id-check" @click="idCheck">
              중복확인
            </button>
          </div>
          <div class="error-msg-area">
            <!-- 안내문구 -->
            <p style="display: none" id="idCheck-msg" class="msg"></p>
          </div>
        </div>

        <!-- 비밀번호 -->
        <div class="info-input-container">
          <div class="label-area">
            <label for="pwd" class="mustInput">비밀번호</label>
          </div>
          <div class="input-area">
            <input
              class="input-text"
              type="password"
              name="pwd"
              id="pwd"
              placeholder="영문 대문자와 숫자, 특수문자를 포함한 8자리 이상"
              v-model="pwd"
            />
          </div>
          <div class="error-msg-area">
            <p style="display: none" id="pwd-msg" class="msg"></p>
          </div>
        </div>

        <!-- 비밀번호 확인 -->
        <div class="info-input-container">
          <div class="label-area">
            <label for="pwd_check" class="mustInput">비밀번호 확인</label>
          </div>
          <div class="input-area">
            <input
              class="input-text"
              type="password"
              name="pwd_chk"
              id="pwd_chk"
              placeholder="비밀번호 확인"
              v-model="pwd_chk"
            />
            <div class="error-msg-area">
              <p style="display: none" id="pwdCheck-msg" class="msg"></p>
            </div>
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
              v-model="nickname"
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
            />
            <button class="input-in-btn" id="email-ckeck" @click="emailCheck">
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

        <!-- 프로필 사진 -->
        <div class="info-input-container">
          <div class="label-area">
            <label for="profile">프로필 사진</label>
          </div>
          <div class="input-area">
            <div
              class="profile-box"
              @drop.prevent="dropInputTag($event)"
              @dragover="dragover"
              @dragleave="isDrag = false"
            >
              <div class="profile-contents">
                <div v-for="file in files" :key="file.name">
                  <img
                    v-if="file.preview"
                    :src="file.preview"
                    id="preview-img"
                  />
                </div>
                <!-- 01/11 (목) 영중, 리수 머지 : files에 값이 없을땐 기본이미지가 나오게 해야됨 ------------------------------------------------->
                <img :src="files.preview" id="preview-img" />
                <p>{{ fileName }}</p>
                <label
                  @drop.prevent="dropInputTag($event)"
                  @dragover="dragover"
                  @dragleave="isDrag = false"
                  for="profile"
                  id="labelForProfile"
                  class="cursor-p"
                  >파일 업로드</label
                >
              </div>
            </div>
            <input
              type="file"
              name="profile"
              id="profile"
              @change="handleFileChange"
              multiple
              accept="image/*"
              ref="fileRef"
            />
            <!-- </div> -->
          </div>
          <div class="error-msg-area">
            <p style="display: none" id="Code-msg" class="msg"></p>
          </div>
        </div>

        <!-------------- 이용약관 -------------->
        <div class="termsOfUse-container">
          <div class="label-area">
            <label>이용약관 및 개인정보처리방침</label>
          </div>
          <div class="error-msg-area">
            <p style="display: none" id="termsOfUse-msg" class="msg"></p>
          </div>

          <div class="termsOfUse-box-area">
            <div class="termsOfUse-box input-text">
              <div>
                <input type="checkbox" name="termsOfUse" id="termsOfUse" />
                <label for="termsOfUse">[필수]이용약관에 동의</label>
              </div>
              <button @click="termsOfUseModal">></button>
            </div>

            <div class="termsOfUse-box input-text">
              <div>
                <input
                  type="checkbox"
                  name="privacyPolicy"
                  id="privacyPolicy"
                />
                <label for="privacyPolicy"
                  >[필수]개인정보 처리 방침에 동의</label
                >
              </div>
              <button @click="privacyPolicyModal">></button>
            </div>
          </div>
        </div>

        <div class="myinfo-btn-area">
          <button class="big-ctlbtn cancle-btn" id="back-btn" @click="back">
            뒤로가기
          </button>
          <button class="big-ctlbtn select-btn" id="submit-btn" @click="submit">
            가입하기
          </button>
        </div>
      </form>
    </div>
    <!-- 이용약관 모달 -->
    <termsOfUseModal
      :termsOfUseModalOpen="termsOfUseModalOpen"
      @closeModal="closeTermsOfUseModal"
    />
    <!-- 개인정보처리방침 모달 -->
    <privacyPolicyModal
      :modalOpen="modalOpen"
      @closeModal="closePrivacyPolicyModal"
    />
  </div>
</template>
<script>
import axios from "axios";
import termsOfUseModal from "@/components/client/member/TermsOfUseModal.vue";
import privacyPolicyModal from "@/components/client/member/PrivacyPolicyModal.vue";

export default {
  name: "SignUp",
  data() {
    return {
      userId: "",
      pwd: "",
      pwd_chk: "",
      nickname: "",
      email: "",
      name: "",
      profile: "",
      termsOfUseModalOpen: false,
      modalOpen: false,
      fileName: "끌어서 사진 올리기",
      formData: new FormData(),
      files: [],
      idCheckBoolean: false,
      nicknameCheckBoolean: false,
    };
  },
  components: {
    privacyPolicyModal,
    termsOfUseModal,
  },
  mounted() {},
  created() {
    this.bgImage();
  },
  watch: {
    userId() {
      var idRegex = /^[a-zA-Z0-9_]+$/;
      if (idRegex.test(this.userId) === false) {
        document.getElementById("idCheck-msg").innerText =
          "아이디는 영문 대소문자와 숫자, _만 사용 가능합니다.";
        document.getElementById("idCheck-msg").style.display = "block";
        document.getElementById("userId").focus();
        this.idCheckBoolean = false; // 아이디 형식이 맞지 않을 때 idCheckBoolean를 false로 설정
      } else {
        document.getElementById("idCheck-msg").style.display = "none";
        this.idCheckBoolean = true; // 아이디 형식이 맞을 때 idCheckBoolean를 true로 설정
      }
    },
    pwd() {
      var pwdRegex = /^(?=.*[A-Z])(?=.*[!@#$%^&*])(?=.*[a-zA-Z]).{8,}$/;
      if (pwdRegex.test(this.pwd) === false && this.pwd !== "") {
        document.getElementById("pwd-msg").innerText =
          "비밀번호는 영문 대문자와 숫자, 특수문자를 포함한 8자리 이상이어야 합니다.";
        document.getElementById("pwd-msg").style.display = "block";
        document.getElementById("pwd").focus();
      } else {
        document.getElementById("pwd-msg").style.display = "none";
      }
    },
    pwd_chk() {
      if (this.pwd_chk !== this.pwd && this.pwd_chk !== "") {
        document.getElementById("pwdCheck-msg").innerText =
          "비밀번호가 일치하지 않습니다.";
        document.getElementById("pwdCheck-msg").style.display = "block";
      } else {
        document.getElementById("pwdCheck-msg").style.display = "none";
      }
    },
    nickname() {
      this.nicknameCheckBoolean = false;
      var nicknameRegex = /^[a-zA-Z0-9ㄱ-ㅎ가-힣]+$/;

      if (this.nickname.length > 20) {
        document.getElementById("nicknameCheck-msg").innerText =
          "닉네임은 20자리 이하로 입력해주세요.";
        document.getElementById("nicknameCheck-msg").style.display = "block";
        document.getElementById("nickname").focus();
      } else if (
        nicknameRegex.test(this.nickname) === false &&
        this.nickname !== ""
      ) {
        document.getElementById("nicknameCheck-msg").innerText =
          "닉네임은 한글, 영문 대소문자, 숫자만 사용 가능합니다.";
        document.getElementById("nicknameCheck-msg").style.display = "block";
        document.getElementById("nickname").focus();
      } else {
        document.getElementById("nicknameCheck-msg").style.display = "none";
      }
    },
  },
  methods: {
    bgImage() {
      var newImage = "type1";
      this.$emit("bgImage", newImage);
    },
    back() {
      this.$router.push("/login");
    },
    termsOfUseModal() {
      this.termsOfUseModalOpen = true;
    },
    closeTermsOfUseModal() {
      this.termsOfUseModalOpen = false;
    },
    privacyPolicyModal() {
      this.modalOpen = true;
    },
    closePrivacyPolicyModal() {
      this.modalOpen = false;
    },
    fileChanged(event) {
      this.fileName = event.target.files[0].name;
    },
    dragover(event) {
      event.preventDefault();
      this.isDrag = true;
    },
    handleFileChange(event) {
      if (event.dataTransfer) {
        this.dropInputTag(event);
        console.log("test1");
      } else {
        this.selectFile(event);
        console.log("test2");
      }
    },

    //이미지 파일 드래그앤 드롭
    dropInputTag(event) {
      this.clearFiles();
      let files = Array.from(event.dataTransfer.files);

      if (files.length > 0) {
        const file1 = files[0];
        this.fileName = file1.name;

        const preview = URL.createObjectURL(file1);
        this.files.push({
          name: file1.name,
          preview: preview,
        });

        this.formData.append("faceImage", file1);
        this.formData.append("files", file1);

        console.log(this.formData.get("faceImage"));
      }

      event.preventDefault();
      this.isDrag = false;
    },
    clearFiles() {
      this.files = [];
      this.fileName = "";
      this.formData.delete("files");
      this.formData.delete("faceImage");
    },
    selectFile(event) {
      this.clearFiles(); // 기존 파일 정보 초기화

      const selectedFiles = event.target.files;

      for (let i = 0; i < selectedFiles.length; i++) {
        const file = selectedFiles[i];

        // 파일 미리보기 설정
        const preview = URL.createObjectURL(file);

        this.files.push({
          name: file.name,
          preview: preview,
        });

        this.fileName = file.name;

        this.formData.append("faceImage", file);
        this.formData.append("files", file);

        console.log(this.formData.get("faceImage"));
        console.log(this.formData.get("files"));
      }
    },

    async idCheck() {
      var idRegex = /^[a-zA-Z0-9_]+$/;
      const userId = document.getElementById("userId").value;
      if (userId === "") {
        document.getElementById("idCheck-msg").innerText =
          "아이디는 필수 입력 사항입니다.";
        document.getElementById("idCheck-msg").style.display = "block";
        document.getElementById("userId").focus();
        return false;
      } else if (!idRegex.test(userId)) {
        document.getElementById("idCheck-msg").innerText =
          "아이디는 영문 대소문자와 숫자, _만 사용 가능합니다.";
        document.getElementById("idCheck-msg").style.display = "block";
        document.getElementById("userId").focus();
        return false;
      }
      axios
        .get(
          `http://${process.env.VUE_APP_BACK_END_URL}/api/auth/${userId}/userIdCheck`
        )
        .then((res) => {
          console.log(res);
          if (res.data) {
            document.getElementById("idCheck-msg").innerText =
              "이미 사용중인 아이디입니다.";
            document.getElementById("idCheck-msg").style.display = "block";
            document.getElementById("userId").focus();
            return false;
          } else {
            document.getElementById("idCheck-msg").innerText =
              "사용 가능한 아이디입니다.";
            document.getElementById("idCheck-msg").style.display = "block";
            document.getElementById("idCheck-msg").style.color = "green";
            this.idCheckBoolean = true;
          }
        })
        .catch((error) => {
          console.error("API 호출 에러:", error);
          return false;
        });
    },

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
    submit() {
      const termsOfUse = document.getElementById("termsOfUse");
      const termsOfUseMsg = document.getElementById("termsOfUse-msg");
      const privacyPolicy = document.getElementById("privacyPolicy");

      this.formData.delete("userId", "");
      this.formData.delete("pwd", "");
      this.formData.delete("nickname", "");
      this.formData.delete("email", "");
      this.formData.delete("name", "");

      const userId = document.getElementById("userId").value;
      const pwd = document.getElementById("pwd").value;
      const pwd_chk = document.getElementById("pwd_chk").value;
      const nickname = document.getElementById("nickname").value;
      const email = document.getElementById("email").value;
      const name = document.getElementById("name").value;

      this.formData.append("userId", userId);
      this.formData.append("pwd", pwd);
      this.formData.append("nickname", nickname);
      this.formData.append("email", email);
      this.formData.append("name", name);
      var idRegex = /^[a-zA-Z0-9_]+$/;
      var pwdRegex = /^(?=.*[A-Z])(?=.*[!@#$%^&*])(?=.*[a-zA-Z]).{8,}$/;
      var nicknameRegex = /^[a-zA-Z0-9ㄱ-ㅎ가-힣]+$/;
      var emailRegex = /^[\w-.]+@([\w-]+\.)+[\w-]{2,5}$/;

      if (userId === "") {
        document.getElementById("idCheck-msg").innerText =
          "아이디는 필수 입력 사항입니다.";
        document.getElementById("idCheck-msg").style.display = "block";
        document.getElementById("userId").focus();
      } else {
        document.getElementById("idCheck-msg").style.display = "none";
      }
      if (idRegex.test(userId) === false) {
        document.getElementById("idCheck-msg").innerText =
          "아이디는 영문 대소문자와 숫자, _만 사용 가능합니다.";
        document.getElementById("idCheck-msg").style.display = "block";
        document.getElementById("userId").focus();
      } else {
        document.getElementById("idCheck-msg").style.display = "none";
      }
      if (userId.length < 5) {
        document.getElementById("idCheck-msg").innerText =
          "아이디는 5자리 이상이어야 합니다.";
        document.getElementById("idCheck-msg").style.display = "block";
        document.getElementById("userId").focus();
      } else {
        document.getElementById("idCheck-msg").style.display = "none";
      }

      if (pwd === "" || pwd.length < 8) {
        document.getElementById("pwd-msg").innerHTML =
          "비밀번호는 영문 대문자와 숫자, 특수문자를 포함한 8자리 이상이어야 합니다.";
        document.getElementById("pwd-msg").style.display = "block";
        document.getElementById("pwd").focus();
      } else {
        document.getElementById("pwd-msg").style.display = "none";
      }
      if (pwd_chk !== pwd || pwd_chk === "") {
        document.getElementById("pwdCheck-msg").innerHTML =
          "비밀번호가 일치하지 않습니다.";
        document.getElementById("pwdCheck-msg").style.display = "block";
      } else {
        document.getElementById("pwdCheck-msg").style.display = "none";
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
      if (!termsOfUse.checked || !privacyPolicy.checked) {
        termsOfUseMsg.innerHTML =
          "이용약관 및 개인정보처리방침은 필수 체크 사항입니다.";
        termsOfUseMsg.style.display = "block";
      } else {
        termsOfUseMsg.style.display = "none";
      }

      if (
        userId === "" ||
        pwd === "" ||
        pwd_chk === "" ||
        email === "" ||
        name === "" ||
        nickname === ""
      ) {
        return false;
      }

      if (idRegex.test(userId) === false) {
        document.getElementById("userId").focus();
        document.getElementById("idCheck-msg").innerText =
          "아이디는 영문 대소문자와 숫자, _만 사용 가능합니다.";
        document.getElementById("idCheck-msg").style.display = "block";
      } else if (pwdRegex.test(pwd) == false) {
        document.getElementById("pwdCheck-msg").innerHTML =
          "비밀번호는 영문 대문자와 숫자, 특수문자를 포함한 8자리 이상이어야 합니다.";
        document.getElementById("pwdCheck-msg").style.display = "block";
        document.getElementById("pwd").focus();
      } else if (pwd !== pwd_chk) {
        document.getElementById("pwdCheck-msg").innerHTML =
          "비밀번호가 일치하지 않습니다.";
        document.getElementById("pwdCheck-msg").style.display = "block";
        document.getElementById("pwd_chk").focus();
      } else if (nicknameRegex.test(nickname) === false) {
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
          console.log(this.formData.get("files"));
          if (res.data == 1) {
            document.getElementById("Code-msg").innerText =
              "인증번호가 일치합니다.";
            document.getElementById("Code-msg").style.color = "green";
            document.getElementById("Code-msg").style.display = "block";
            axios
              .post(
                `http://${process.env.VUE_APP_BACK_END_URL}/api/auth/signup`,
                this.formData,
                {
                  headers: {
                    "Content-Type": "multipart/form-data",
                  },
                }
              )
              .then((res) => {
                alert(res);
                if (res.status == 200) {
                  alert("회원가입이 완료되었습니다.");
                  this.$router.push("/Login");
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
    previewImage(event) {
      const reader = new FileReader();
      const image = event.target.files[0];
      reader.readAsDataURL(image);
      reader.onload = (event) => {
        this.profile = event.target.result;
      };
    },
  },
};
</script>

<style scoped>
@import url("@/css/client/member/myInfoUploadForm.css");
</style>
