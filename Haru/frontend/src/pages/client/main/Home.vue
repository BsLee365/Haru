<template>
  <video muted autoplay loop class="home">
    <source src="@/assets/bgImage/main_bg.mp4" type="video/mp4" />
  </video>
  <div class="screen-1">
    <h1>지친 현대인의 삶을 위한, 하루의 여울</h1>
    <h4>하루의 여울이 당신을 위해 안락한 곳으로 안내합니다.</h4>
  </div>
</template>

<script>
export default {
  name: "HelloWorld",
  created() {
    this.bgImage();
    this.$emit("is-main", true);
  },
  methods: {
    bgImage() {
      var newImage = "";
      this.$emit("bgImage", newImage);
    },
  },
};
</script>

<style scoped>
@import url("@/css/client/main/home.css");
</style>
