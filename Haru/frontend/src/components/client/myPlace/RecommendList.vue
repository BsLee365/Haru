<template>
  <!-- 추천 리스트 - 내장소(추천리스트), 피드생성/수정(추천리스트 모달창) -->
  <div>

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
                <a :href="'https://map.naver.com/p/smart-around/place/' + getPlaceLink(item.place.place_link)" target="_blank">
<!--                  <img class="rec-detail-img" :src="item.place.place_img" alt=""/>-->
                  <!-- 장소 이미지 없는 경우 -->
                  <img
                      v-show="item.place.place_img === null"
                      src="@/img/Total_stress/img/no-image.jpg"
                      alt="no_image"
                      class="rec-detail-img"
                  />
                  <!-- 장소 이미지 있는 경우 -->
                  <img
                      v-show="item.place.place_img != null"
                      :src="item.place.place_img"
                      alt="no_image"
                      class="rec-detail-img"
                  />

                </a>
              </div>
              <div class="content">
                <div class="stname-address">
                  <!-- 가게 명 -->
                  <a :href="'https://map.naver.com/p/smart-around/place/' + getPlaceLink(item.place.place_link)" target="_blank">
                    <h5 class="stName">{{ item.place.place_name }}</h5>
                  </a>
                  <!-- 가게 주소 -->
                  <a :href="'https://map.naver.com/p/smart-around/place/' + getPlaceLink(item.place.place_link)" target="_blank">
                    <p class="stAddress">{{ item.place.place_address }}</p>
                  </a>
                </div>
                <!-- 별점 -->
                <p class="rlist-score-area">
                  <span class="rlist-score">★ {{ (item.place.place_score / 10).toFixed(1) }}</span>
                </p>
              </div>
            </div>

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
            </div>

            <!-- 라디오 버튼 -> 추천리스트 선택 -->
            <div
                class="rlist-checkbox"
                :class="{ recBtnDisplayNone: isBtnHeartNone === false }"
            >
<!--              v-model="checkedNum"-->
              <input
                  type="radio"
                  class="recCheckbox"
                  :id="'recCheckbox' + idx"
                  :checked="isChecked(item.place.place_num)"
                  name="recRadio"
                  @click="sendRecList(item.place)"
              />
              <label :for="'recCheckbox' + idx"></label>
            </div>
          </div>

          <transition name="fade">
            <div
                v-if="myNum === idx"
                @mouseover="FeedBtnOn(idx)"
                @mouseleave="FeedBtnOff()"
                @click="gotoWriteFeed"
                class="upload-btn-area cursor-p"
                :id="'uploadBtn' + idx"
                :class="{
                recBtnDisplayNone: isBtnHeartNone === true,
                feedBtnon: myNum === idx,
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
import {onMounted, ref} from "vue";
import axios from "axios";
import {jwtDecode} from "jwt-decode";

export default {
  data() {
    return {
      myNum: "",
      // recList: {}, // 추천받은 리스트
      // wish: {}, // 위시 리스트 (찜 목록)
      // allPlace: {}, // 각 장소별 detail 정보
    };
  },
  props: {
    AllRecList: Object, // 추천 리스트 목록
    isBtnHeartNone: Boolean, // 하트 버튼이 눌리는 화면인지 추천 리스트 선택 버튼이 나와야되는 화면인지
    sendSelectedDate: String, // 추천 받은 날짜
    selectedRecPlace: Object, // 모달창 - 선택된 장소 정보
    // checkedNum: "", // 모달창 장소 선택 어떤거 됐는지
  },
  mounted() {
    this.isChecked();
  },
  methods: {
    isChecked(pnum) {
      // console.log(this.selectedRecPlace.place_num + ' / ' + pnum)
      return this.selectedRecPlace.place_num == pnum;
    },
    FeedBtnOn(num) {
      this.myNum = num;
    },
    FeedBtnOff() {
      this.myNum = -1;
    },
    gotoWriteFeed() {
      this.$router.push("/insertFeed");
    },
    // 선택한 항목 부모에게 전달 --------------------------------------------------------
    sendRecList(recItem) {
      // item.place 전달
      this.$emit("send-rec-List", recItem);
    },

    // 찜 목록에 있는 장소인지 확인 --------------------------------------------------------
    isInWishList(item) {
      if (item.wishList != null) {
        return true;
      }
      return false;
    },

    // 링크 주소 변경
    getPlaceLink(link) {
      var mylink = link.split("/");
      if ( mylink[mylink.length-1] === '?entry=ple' ) {
        return  mylink[mylink.length-2]
      } else {
        return mylink[mylink.length-1]
      }
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
