<template>
  <div class="faceReg-container">
    <div class="warn-modal-box">
      <!-- v-show="modal_Check" -->
      <div
        class="warn-modal-wrap"
        id="modal-wrap"
        @click="this.$emit('hideModal')"
      >
        <div class="modal-container1" @click.stop="">
          <div class="warn-modal-btn">
            <button
              id="warn_modal_close"
              @click="this.$emit('hideModal')"
            ></button>
          </div>
          <div class="warn-info">
            <div class="warn-info-box">
              <div class="warn-info-box-1">
                <img src="@/img/FaceRegistration/warn_img.png" width="70" />
                <span class="warn-title">얼굴을 찾지 못했어요....</span>
              </div>
            </div>
            <div class="info-cards-container">
              <div class="info-cards-1 card">
                <img
                  src="@/img/FaceRegistration/warn_info_img/image 79.png"
                  width="60"
                />
                <img
                  src="@/img/FaceRegistration/warn_info_img/image 75.png"
                  width="100"
                  height="130"
                />
                <span class="info-coment">스티커나 필터를 사용한 경우</span>
              </div>
              <div class="info-cards-2 card">
                <img
                  src="@/img/FaceRegistration/warn_info_img/image 79.png"
                  width="60"
                />
                <img
                  src="@/img/FaceRegistration/warn_info_img/image 76.png"
                  width="100"
                  height="130"
                />
                <span class="info-coment">옆 모습만 나온 경우</span>
              </div>
              <div class="info-cards-3 bcard">
                <img
                  src="@/img/FaceRegistration/warn_info_img/image 79.png"
                  width="60"
                />
                <img
                  src="@/img/FaceRegistration/warn_info_img/image 77.png"
                  width="100"
                  height="130"
                />
                <span class="info-coment" style="color: white"
                  >사진이 어둡게 나온 경우</span
                >
              </div>
              <div class="info-cards-4 card">
                <img
                  src="@/img/FaceRegistration/warn_info_img/image 79.png"
                  width="60"
                />
                <img
                  src="@/img/FaceRegistration/warn_info_img/image 78.png"
                  width="100"
                  height="130"
                />
                <span class="info-coment">얼굴이 작게 나온 경우</span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  name: "warnFaceModal",
  data() {
    return {};
  },
  props: {
    modal_Check: Boolean,
  },
  methods: {
    // hideModal() {
    //   this.$emit("modal_click");
    // },
  },
};
</script>
