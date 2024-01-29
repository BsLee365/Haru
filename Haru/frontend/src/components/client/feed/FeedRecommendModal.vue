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
                      <a :href="'https://map.naver.com/p/smart-around/place/' + getPlaceLink(recItemInfo.place_link)" target="_blank">
                        <img
                            v-show="recItemInfo.place_img === null"
                            src="@/img/Total_stress/img/no-image.jpg"
                            alt="no_image"
                            class="rec-detail-img"
                        />
                        <!-- 장소 이미지 있는 경우 -->
                        <img
                            v-show="recItemInfo.place_img != null"
                            :src="recItemInfo.place_img"
                            alt="no_image"
                            class="rec-detail-img"
                        />
                      </a>
                    </div>
                    <div class="content">
                      <div class="stname-address">
                        <!-- 가게 명 -->
                        <h5 class="stName">{{ recItemInfo.place_name }}</h5>
                        <!-- 가게 주소 -->
                        <p class="stAddress">{{ recItemInfo.place_address }}</p>
                      </div>
                      <!-- 별점 -->
                      <p class="rlist-score-area">
                        <span class="rlist-score"
                        >★ {{ (recItemInfo.place_score / 10).toFixed(1) }}</span
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
          <p class="feed-rec-area-title">이전 추천 리스트</p><span style="padding-bottom:10px; padding-left: 20px; display: inline-block;">최근 한달 동안 추천 받은 리스트입니다.</span>
          <RecList
              :AllRecList="RecommendList"
              :isBtnHeartNone="isBtnHeartNone"
              :selectedRecPlace="selectedRecPlace"
              class="feed-rec-recList"
              @send-rec-List="getRecList"
          />
        </div>

        <!-- 버튼 영역 -->
        <div class="feed-rec-btn-area">
          <button @click="$emit('close-modal')" class="big-ctlbtn cancle-btn">
            취소하기
          </button>
          <button class="big-ctlbtn insert-btn" @click="selectMyRecItem(recItemInfo)">선택하기</button>
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
    // 링크 주소 변경
    getPlaceLink(link) {
      var mylink = link.split("/");
      if ( mylink[mylink.length-1] === '?entry=ple' ) {
        return  mylink[mylink.length-2]
      } else {
        return mylink[mylink.length-1]
      }
    },
    getRecList(recItem) {
      // console.log('선택한 거 : ' + recItem);
      this.recItemInfo = recItem;
    },
    selectMyRecItem(item) {
      console.log(item)
      if (this.recItemInfo === "") {
        alert("추천 장소가 선택되지 않았어요!")
      } else {
        // insert 화면으로 값 전달
        this.$emit('get-rec-list', item)
        this.$emit('close-modal')
      }
    },
  },
  components: {
    RecList,
  },
  created() {
    // console.log('this.RecommendList!!!! ' + this.RecommendList);
  },
  mounted() {
    // console.log(this.selectedRecPlace.place_num == null);
    if (this.selectedRecPlace.place_num == null) {
      console.log('선택돼서 넘어온거 없음')
    } else {
      console.log('선택돼서 넘어온거 있음')
      this.recItemInfo = this.selectedRecPlace;
    }
  },
  props: {
    RecommendList: Array,
    selectedRecPlace: Array,
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
