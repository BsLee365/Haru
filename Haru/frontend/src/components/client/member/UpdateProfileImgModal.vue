<template>
  <!-- 프로필 사진 변경 모달창 -->
  <div v-if="mStatus" class="FindMyAccount-modal" @click="$emit('modalClose')">
    <div class="findMyAccount-modal-content" @click.stop>
      <div class="feed-detail-modal-btn">
        <!-- X 버튼 -->
        <button id="common-modal-close" @click="$emit('modalClose')"></button>
      </div>

      <div class="findMyAccount-title-area">
        <h2>프로필 사진 수정</h2>
      </div>

      <div class="findModalContent">
        <form autocomplete="off" class="up-profile-modal-form">
          <div id="up-profile-area">
            <div class="up-profileImgBg cursor-p" @click="openModal">
              <img src="@/img/FaceRegistration/camera.png" alt="" />
              <p>프로필 수정</p>
            </div>
            <img src="@/assets/bgImage/type3.png" class="profileImg" />
          </div>

          <div class="deleteMyInfo-btn-area">
            <button
              class="big-ctlbtn cancle-btn"
              id="deleteMyInfo-cancel"
              @click="$emit('modalClose')"
            >
              뒤로가기
            </button>
            <button class="big-ctlbtn update-btn" id="deleteMyInfo-submit">
              수정하기
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
    mStatus: Boolean,
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
.deleteMyInfo-btn-area {
  width: 93%;
}
</style>
