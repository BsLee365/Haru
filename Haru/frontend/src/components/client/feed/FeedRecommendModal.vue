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
            <div v-if="!recItemInfo" class="feed-rec-noplace">
              장소를 선택해주세요!
            </div>

            <div v-if="recItemInfo">
              <div class="rlist-card-area">
                <div class="rlist-content-area">
                  <div class="all-info">
                    <div class="rlist-img-area">
                      <a :href="recItemInfo.link"
                        ><img
                          class="rec-detail-img"
                          :src="recItemInfo.img"
                          alt=""
                      /></a>
                    </div>
                    <div class="content">
                      <div class="stname-address">
                        <!-- 가게 명 -->
                        <h5 class="stName">{{ recItemInfo.storeName }}</h5>
                        <!-- 가게 주소 -->
                        <p class="stAddress">{{ recItemInfo.stAddress }}</p>
                      </div>
                      <!-- 해시태그 -->
                      <p class="rlist-rec-hash-area">
                        <span
                          class="rlist-rec-hash cursor-p"
                          v-for="(hash, hidx) in recItemInfo.hashtag"
                          :key="hidx"
                          >#{{ hash }}</span
                        >
                      </p>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>

        <!-- 이전 추천 리스트 전체 영역 -->
        <div>
          <p class="feed-rec-area-title">이전 추천 리스트</p>
          <RecList
            :RecommendList="RecommendList"
            :isBtnHeartNone="isBtnHeartNone"
            class="feed-rec-recList"
            @send-rec-List="getRecList"
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
      recItemInfo: "",
    };
  },
  methods: {
    getRecList(recItem) {
      console.log(recItem);
      this.recItemInfo = recItem;
    },
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
.rlist-card-area {
  padding-left: 30px;
}
</style>
