<template>
  <!-- 추천 리스트 - 내장소(추천리스트), 피드생성/수정(추천리스트 모달창) -->
  <div>
<<<<<<< HEAD

    <!--
      recList: {}, // 추천받은 리스트
      wish: {}, // 위시 리스트 (찜 목록)
      allPlace: {}, // 각 장소별 detail 정보
      -->
<!--    {{ this.AllRecList.length }}-->

    <!-- 추천받은 날짜-->
    <p class="reclist-date-text" v-if="AllRecList.length > 0">
      {{ sendSelectedDate }}
    </p>
    <p class="reclist-date-text" v-if="AllRecList.length == 0">
      추천 받은 장소가 없어요!
    </p>
    <!--    추천 받으러 가기 유도-->
    <div v-for="(item, idx) in AllRecList" :key="idx" class="rlist-day-area">
<!--      {{ Object.keys(item) }} ===> place", "recList", "wishList-->
      <div @mouseleave="FeedBtnOff">
        <div class="rlist-card-area" @mouseleave="FeedBtnOff">
          <div
              class="rlist-content-area"
              :id="'contentArea' + idx"
              @mouseover="FeedBtnOn(idx)"
          >
            <div class="all-info">
              <div class="rlist-img-area">
                <a :href="'https://map.naver.com/p/search/' + item.place.place_name" target="_blank"
                ><img class="rec-detail-img" :src="item.place.place_img" alt=""
=======
    <div
      v-for="(rlist, idx) in RecommendList"
      :key="idx"
      class="rlist-day-area"
    >
      <p>{{ rlist.rdate }}</p>
      <div v-for="(item, i) in rlist.recList" :key="i" @mouseleave="FeedBtnOff">
        <div class="rlist-card-area">
          <div
            class="rlist-content-area"
            :id="'contentArea' + idx + '-' + i"
            @mouseover="FeedBtnOn(idx + '-' + i)"
          >
            <div class="all-info">
              <div class="rlist-img-area">
                <a :href="item.link"
                  ><img class="rec-detail-img" :src="item.img" alt=""
>>>>>>> yj_base
                /></a>
              </div>
              <div class="content">
                <div class="stname-address">
                  <!-- 가게 명 -->
<<<<<<< HEAD
                  <a :href="'https://map.naver.com/p/search/' + item.place.place_name" target="_blank"
                  ><h5 class="stName">{{ item.place.place_name }}</h5></a
                  >
                  <!-- 가게 주소 -->
                  <a :href="'https://map.naver.com/p/search/' + item.place.place_name" target="_blank"
                  ><p class="stAddress">{{ item.place.place_address }}</p></a
                  >
                </div>
                <!-- 별점 -->
                <p class="rlist-score-area">
                  <span class="rlist-score">★ {{ item.place.place_score / 10 }}</span>
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
>>>>>>> yj_base
                </p>
              </div>
            </div>

<<<<<<< HEAD
            <!-- 찜 - 하트 버튼 (isBtnHeartNone) -->
            <div
                class="rlist-heart"
                :class="{ recBtnDisplayNone: isBtnHeartNone === true }"
            >
              <img class="cursor-p"
                   src="@/img/Feed/heart.png"
                   id="heart"
                   @click="toggleWish(item)"
                   v-if="isInWishList(item)"
              />

              <img class="cursor-p"
                   src="@/img/Feed/heart_blank.png"
                   id="heart"
                   @click="toggleWish(item)"
                   v-else
              />
=======
            <!-- 하트 버튼 -->
            <div
              class="rlist-heart"
              :class="{ recBtnDisplayNone: isBtnHeartNone === true }"
            >
              <img class="cursor-p" src="@/img/Feed/heart.png" id="heart" />
>>>>>>> yj_base
            </div>

            <!-- 라디오 버튼 -> 추천리스트 선택 -->
            <div
<<<<<<< HEAD
                class="rlist-checkbox"
                :class="{ recBtnDisplayNone: isBtnHeartNone === false }"
            >
              <input
                  type="radio"
                  class="recCheckbox"
                  :id="'recCheckbox' + idx"
                  name="recRadio"
                  @click="sendRecList(item)"
              />
              <label :for="'recCheckbox' + idx"></label>
=======
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
>>>>>>> yj_base
            </div>
          </div>

          <transition name="fade">
            <div
<<<<<<< HEAD
                v-if="myNum === idx"
                @mouseover="FeedBtnOn(idx)"
                @mouseleave="FeedBtnOff()"
                @click="gotoWriteFeed"
                class="upload-btn-area cursor-p"
                :id="'uploadBtn' + idx"
                :class="{
                recBtnDisplayNone: isBtnHeartNone === true,
                feedBtnon: myNum === idx,
=======
              v-if="myNum === idx + '-' + i"
              @mouseover="FeedBtnOn(idx + '-' + i)"
              @mouseleave="FeedBtnOff()"
              @click="gotoWriteFeed"
              class="upload-btn-area cursor-p"
              :id="'uploadBtn' + idx + '-' + i"
              :class="{
                recBtnDisplayNone: isBtnHeartNone === true,
                feedBtnon: myNum === idx + '-' + i,
>>>>>>> yj_base
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
<<<<<<< HEAD
import {onMounted, ref} from "vue";
import axios from "axios";
import {jwtDecode} from "jwt-decode";

=======
>>>>>>> yj_base
export default {
  data() {
    return {
      myNum: "",
<<<<<<< HEAD
      // recList: {}, // 추천받은 리스트
      // wish: {}, // 위시 리스트 (찜 목록)
      // allPlace: {}, // 각 장소별 detail 정보
    };
  },
  props: {
    AllRecList: Object, // 추천 리스트 목록
    isBtnHeartNone: Boolean, // 하트 버튼이 눌리는 화면인지 추천 리스트 선택 버튼이 나와야되는 화면인지
    sendSelectedDate: String, // 추천 받은 날짜
=======
    };
  },
  props: {
    RecommendList: Object, // 추천 리스트 목록
    isBtnHeartNone: Boolean, //
>>>>>>> yj_base
  },
  methods: {
    FeedBtnOn(num) {
      this.myNum = num;
    },
    FeedBtnOff() {
<<<<<<< HEAD
      this.myNum = -1;
=======
      this.myNum = 0;
>>>>>>> yj_base
    },
    gotoWriteFeed() {
      this.$router.push("/insertFeed");
    },
<<<<<<< HEAD
    // 선택한 항목 부모에게 전달 --------------------------------------------------------
    sendRecList(recItem) {
      this.$emit("send-rec-List", recItem);
    },

    // 찜 목록에 있는 장소인지 확인 --------------------------------------------------------
    isInWishList(item) {
      if (item.wishList != null) {
        return true;
      }
      return false;
    },

    // 위시 리스트 담고 빼기 --------------------------------------------------------
    toggleWish(item) {
      // console.log(item);
      if (item.wishList == null) {
      //   찜 목록에 없는 상태 -> 등록 필요
      //   place", "recList", "wishList
        console.log('!!!! ' + item.recList);

        // console.log(this.data.id);
        // console.log(item.place);
        axios.post(`http://${process.env.VUE_APP_BACK_END_URL}/wishList/addWishPlace`, {
          userid: this.data.id,
          place: item.place
        })
            .then((res) => {
              // var JSON.parse(item.wishList).push(res.data));
              // console.log(res.data);

              this.$emit('update-all-rec-list', res.data, item);
            })
            .catch(err => {
              console.log('등록 에러 ' + err);
            })
      } else {
      //   찜 목록에서 제거 필요
      //   console.log('!!!! ' + item.wishList.wish_list_num);
      //   console.log(JSON.stringify(item.wishList));
        axios.post(`http://${process.env.VUE_APP_BACK_END_URL}/wishList/deleteWishPlace`,{
            wishlistnum: item.wishList.wish_list_num
        })
            .then(() => {
              console.log('삭제됨');
              this.$emit('update-all-rec-list');
            })
            .catch(err => {
              console.log('삭제 에러 ' + err);
            })
      }
    }
  },
  setup() {
    const isLoggedIn = ref(false);
    const data = ref([]);

    const getToken = () => {
      const token = localStorage.getItem("jwtToken");
      isLoggedIn.value = token ? true : false;
    };

    const logout = () => {
      axios
          .get(`http://${process.env.VUE_APP_BACK_END_URL}/api/auth/logout`)
          .then((res) => {
            if (res.data == "Logout") {
              localStorage.removeItem("jwtToken");
              window.location.href = "/login";
            }
          });
    };

    const decodeToken = (token) => {
      if (token == null) return false;
      const decoded = jwtDecode(token);
      data.value = decoded; // Use data.value to set the value of the ref
      return decoded;
    };

    onMounted(() => {
      getToken();
      const token = localStorage.getItem("jwtToken");
      decodeToken(token);
    });

    return { logout, data }; // Return data in the setup function
=======
    // 선택한 항목 부모에게 전달
    sendRecList(recItem) {
      this.$emit("send-rec-List", recItem);
    },
>>>>>>> yj_base
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
