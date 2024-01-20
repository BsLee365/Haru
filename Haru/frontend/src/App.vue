<template>
  <div
    :class="{
      'bg-type1': bgType === 'type1',
      'bg-type2': bgType === 'type2',
      'bg-type3': bgType === 'type3',
      'bg-type4': bgType === 'type4',
      'bg-type5': bgType === 'type5',
    }"
  >
    <Header class="custom-header" />
    <RouterView @bgImage="updateBgImage" @is-main="mainPage" />
    <Footer v-if="!isMain" />
  </div>
</template>

<script>
import Header from "@/components/client/layout/Header.vue";
import Footer from "@/components/client/layout/Footer.vue";

export default {
  name: "App",
  data() {
    return {
      bgType: "",
      isMain: false,
    };
  },
  components: {
    Header,
    Footer,
  },
  methods: {
    updateBgImage(bg) {
      this.bgType = bg;
    },
    mainPage(is) {
      this.isMain = is;
    },
  },
};
</script>

<style>
/* 공통 css 적용 */
@import url("@/css/client/common/common.css");
/* 공통 font */
@import url("@/css/client/common/font.css");
/* 화면 별 background-image 설정 */
@import url("@/css/client/common/bgImage.css");
</style>
