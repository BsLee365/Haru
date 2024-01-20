<template>
  <!-- 추천 리스트 - 내장소(추천리스트), 피드생성/수정(추천리스트 모달창) -->
  <div>
<<<<<<< HEAD
    <!-- 추천받은 날짜-->
    <p class="reclist-date-text" v-if="RecPlace.length > 0">{{ sendSelectedDate }}</p>
    <p class="reclist-date-text" v-if="RecPlace.length == 0">추천 받은 장소가 없어요!</p>
    <div
      v-for="(item, idx) in RecPlace"
      :key="idx"
      class="rlist-day-area"
    >
      <div @mouseleave="FeedBtnOff">
=======
    <div
      v-for="(rlist, idx) in RecommendList"
      :key="idx"
      class="rlist-day-area"
    >
      <p>{{ rlist.rdate }}</p>
      <div v-for="(item, i) in rlist.recList" :key="i" @mouseleave="FeedBtnOff">
>>>>>>> junghyoun
        <div class="rlist-card-area">
          <div
            class="rlist-content-area"
            :id="'contentArea' + idx + '-' + i"
            @mouseover="FeedBtnOn(idx + '-' + i)"
          >
            <div class="all-info">
              <div class="rlist-img-area">
<<<<<<< HEAD
                <a :href="item.place_link" target="_blank"
                  ><img class="rec-detail-img" :src="item.place_img" alt=""
=======
                <a :href="item.link"
                  ><img class="rec-detail-img" :src="item.img" alt=""
>>>>>>> junghyoun
                /></a>
              </div>
              <div class="content">
                <div class="stname-address">
                  <!-- 가게 명 -->
<<<<<<< HEAD
                  <a :href="item.place_link" target="_blank"
                  ><h5 class="stName">{{ item.place_name }}</h5></a>
                  <!-- 가게 주소 -->
                  <a :href="item.place_link" target="_blank"
                  ><p class="stAddress">{{ item.place_address }}</p></a>
                </div>
                <!-- 별점 -->
                <p class="rlist-score-area">
                  <span class="rlist-score">★ {{ item.place_score / 10 }}</span>
=======
                  <h5 class="stName">{{ item.storeName }}</h5>
                  <!-- 가게 주소 -->
                  <p class="stAddress">{{ item.stAddress }}</p>
                </div>
                <!-- 해시태그 -->
                <p class="rlist-rec-hash-area">
                  <span
                    class="rlist-rec-hash cursor-p"
                    v-for="(hash, hidx) in item.hashtag"
                    :key="hidx"
                    >#{{ hash }}</span
                  >
>>>>>>> junghyoun
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

<<<<<<< HEAD
            <!-- 라디오 버튼 -> 추천리스트 선택 -->
=======
            <!-- 체크박스 -> 추천리스트 선택 -->
>>>>>>> junghyoun
            <div
              class="rlist-checkbox"
              :class="{ recBtnDisplayNone: isBtnHeartNone === false }"
            >
              <input
<<<<<<< HEAD
                type="radio"
                class="recCheckbox"
                :id="'recCheckbox' + idx + '-' + i"
                name="recRadio"
                @click="sendRecList(item)"
=======
                type="checkbox"
                class="recCheckbox"
                :id="'recCheckbox' + idx + '-' + i"
>>>>>>> junghyoun
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
<<<<<<< HEAD
    RecPlace: Object, // 추천 리스트 목록
    isBtnHeartNone: Boolean, //
    sendSelectedDate: String, // 추천 받은 날짜
=======
    RecommendList: Object, // 추천 리스트 목록
    isBtnHeartNone: Boolean, //
>>>>>>> junghyoun
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
<<<<<<< HEAD
    // 선택한 항목 부모에게 전달
    sendRecList(recItem) {
      this.$emit("send-rec-List", recItem);
    },
=======
>>>>>>> junghyoun
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
