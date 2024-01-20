<template>
  <!-- 추천 리스트 - 내장소(추천리스트), 피드생성/수정(추천리스트 모달창) -->
  <div>
    <!-- 추천받은 날짜-->
    <p class="reclist-date-text" v-if="RecPlace.length > 0">{{ sendSelectedDate }}</p>
    <p class="reclist-date-text" v-if="RecPlace.length == 0">추천 받은 장소가 없어요!</p>
    <div
      v-for="(item, idx) in RecPlace"
      :key="idx"
      class="rlist-day-area"
    >
      <div @mouseleave="FeedBtnOff">
        <div class="rlist-card-area">
          <div
            class="rlist-content-area"
            :id="'contentArea' + idx + '-' + i"
            @mouseover="FeedBtnOn(idx + '-' + i)"
          >
            <div class="all-info">
              <div class="rlist-img-area">
                <a :href="item.place_link" target="_blank"
                  ><img class="rec-detail-img" :src="item.place_img" alt=""
                /></a>
              </div>
              <div class="content">
                <div class="stname-address">
                  <!-- 가게 명 -->
                  <a :href="item.place_link" target="_blank"
                  ><h5 class="stName">{{ item.place_name }}</h5></a>
                  <!-- 가게 주소 -->
                  <a :href="item.place_link" target="_blank"
                  ><p class="stAddress">{{ item.place_address }}</p></a>
                </div>
                <!-- 별점 -->
                <p class="rlist-score-area">
                  <span class="rlist-score">★ {{ item.place_score / 10 }}</span>
                </p>
              </div>
            </div>

            <!-- 하트 버튼 -->
            <div
              class="rlist-heart"
              :class="{ recBtnDisplayNone: isBtnHeartNone === true }"
            >
              <img class="cursor-p" src="@/img/Feed/heart.png" id="heart" />
            </div>

            <!-- 라디오 버튼 -> 추천리스트 선택 -->
            <div
              class="rlist-checkbox"
              :class="{ recBtnDisplayNone: isBtnHeartNone === false }"
            >
              <input
                type="radio"
                class="recCheckbox"
                :id="'recCheckbox' + idx + '-' + i"
                name="recRadio"
                @click="sendRecList(item)"
              />
              <label :for="'recCheckbox' + idx + '-' + i"></label>
            </div>
          </div>

          <transition name="fade">
            <div
              v-if="myNum === idx + '-' + i"
              @mouseover="FeedBtnOn(idx + '-' + i)"
              @mouseleave="FeedBtnOff()"
              @click="gotoWriteFeed"
              class="upload-btn-area cursor-p"
              :id="'uploadBtn' + idx + '-' + i"
              :class="{
                recBtnDisplayNone: isBtnHeartNone === true,
                feedBtnon: myNum === idx + '-' + i,
              }"
            >
              <span>피드 올리기</span>
            </div>
          </transition>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  data() {
    return {
      myNum: "",
    };
  },
  props: {
    RecPlace: Object, // 추천 리스트 목록
    isBtnHeartNone: Boolean, //
    sendSelectedDate: String, // 추천 받은 날짜
  },
  methods: {
    FeedBtnOn(num) {
      this.myNum = num;
    },
    FeedBtnOff() {
      this.myNum = 0;
    },
    gotoWriteFeed() {
      this.$router.push("/insertFeed");
    },
    // 선택한 항목 부모에게 전달
    sendRecList(recItem) {
      this.$emit("send-rec-List", recItem);
    },
  },
};
</script>
<style scoped>
@import "@/css/client/myPlace/recommendList.css";

.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.2s;
}
.fade-enter,
.fade-leave-to {
  opacity: 0;
}
</style>
