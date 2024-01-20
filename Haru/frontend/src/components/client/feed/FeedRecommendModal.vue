<template>
  <!-- 피드 생성/수정 - 추천 장소 선택 모달창 -->
  <!-- --------------------- modal Start ------------------------->
  <div class="feed-modal-wrap" id="modal-wrap" @click="$emit('close-modal')">
    <!-- 모달창 영역 -->
    <div class="feed-modal-container feed-rec-modal" @click.stop>
      <!-- x 버튼 -->
      <div class="feed-rec-modal-btn">
        <button id="common-modal-close" @click="$emit('close-modal')"></button>
      </div>

      <!-------------------------- 컨텐츠 시작 -------------------------->
      <div class="feed-rec-area">
        <!-- 제목 -->
        <div data-v-f00aebca="">
          <h1 data-v-f00aebca="" class="page-upload-title">추천 장소 선택</h1>
        </div>
        <!-- 선택한 장소 전체 영역 -->
        <div class="feed-rec-selected-area">
          <p class="feed-rec-area-title">선택한 장소</p>
          <div class="contents">
            <div class="feed-rec-noplace">장소를 선택해주세요!</div>
          </div>
        </div>

        <!-- 이전 추천 리스트 전체 영역 -->
        <div>
          <p class="feed-rec-area-title">이전 추천 리스트</p>
          <RecList
            :RecommendList="RecommendList"
            :isBtnHeartNone="isBtnHeartNone"
            class="feed-rec-recList"
          />
        </div>

        <!-- 버튼 영역 -->
        <div class="feed-rec-btn-area">
          <button @click="$emit('close-modal')" class="big-ctlbtn cancle-btn">
            취소하기
          </button>
          <button class="big-ctlbtn insert-btn">선택하기</button>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import RecList from "@/components/client/myPlace/RecommendList.vue";

export default {
  name: "feedRecommendList",
  data() {
    return {
      isBtnHeartNone: true,
    };
  },
  components: {
    RecList,
  },
  props: {
    RecommendList: Array,
  },
};
</script>
<style scoped>
@import "@/css/client/feed/uploadFeed.css";
@import "@/css/client/feed/feedRecommend.css";
</style>
