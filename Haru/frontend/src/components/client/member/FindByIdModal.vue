<template>
  <!-- 아이디 찾기 모달창 -->
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
        <h2>아이디 찾기</h2>
      </div>

      <div class="findModalContent">
        <form autocomplete="off">
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
              <button
                class="input-in-btn"
                id="email-ckeck"
                @click.prevent="submit"
              >
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

          <!-- 찾은 아이디 -->
          <div class="info-input-container">
            <div class="label-area">
              <label for="emailCheck" id="findMyIdLabel">찾은 아이디</label>
            </div>
            <div class="input-area">
              <input
                  class="input-text"
                  type="text"
                  id="findMyId"
                  readonly
              />
            </div>
            <div class="error-msg-area">
              <p style="display: none" id="Code-msg" class="msg"></p>
            </div>
          </div>

          <div class="btn-area">
            <button class="big-ctlbtn insert-btn" @click="findIdToggleModal">
              아이디 찾기
            </button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  name: "FindByIdModal",
  data() {
    return {};
  },
  props: {
    modalOpen: Boolean,
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
#findMyIdLabel, #findMyId {
  color: #f39f7e;
}
</style>
