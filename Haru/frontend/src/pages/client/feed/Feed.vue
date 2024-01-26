<template>
  <div class="container1">
    <FeedDetail
      v-if="modal_Check"
      @close-modal="closeModal"
      @openModal="openModal"
      :cardOrigin="card"
      :userData="userData"
      @getFeedList="getFeedList"
      :index="index"
      ref="feedDetail"
    />
    <div class="feed-card-area" id="card-area">
      <!-- 해시태그 검색 -->
      <form v-if="!this.$route.query.nickname" action="" id="search-form">
        <div class="search-area">
          <input
            v-on:keydown.enter.prevent="searchFeed"
            class="hashtag-search-input"
            type="text"
            placeholder="내용으로 피드를 검색하세요"
          />
          <button type="button" class="hashtag-search-btn" @click="searchFeed">
            <img src="@/img/Feed/search_btn.png" alt="" />
          </button>
        </div>
      </form>

      <!-- 피드 Start -->
      <FeedList
        :cardList="cardList"
        :listCnt="listCnt"
        :allCardList="allCardList"
        :searchKeyword="searchKeyword"
        :selectedNickname="selectedNickname"
        @open-modal="openModal"
        @getMyFeedList="getMyFeedList"
        @getFeedList="getFeedList"
        @getNextFeedList="getNextFeedList"
        :searchFeed="searchFeed"
        ref="feedList"
      />
    </div>
  </div>
</template>
<script>
import FeedList from "@/components/client/feed/FeedList.vue";
import FeedDetail from "@/components/client/feed/FeedDetail.vue";
import axios from "axios";
import { ref, onMounted } from "vue";
import { jwtDecode } from "jwt-decode";
export default {
  name: "feedMain",
  data() {
    return {
      cardList: [
        // {
        // profileImage: require("@/img/Feed/no_profile.png"),
        // uid: "abc",
        // profileLink: "#",
        // nickname: "닉네임",
        // mainCategory: "액티비티",
        // images: [require("@/img/Feed/feed1.png"), require("@/img/Feed/feed3.jpg"), require("@/img/Feed/feed2.jpg")],
        // content: "오늘 정말 좋았던거 같은데... 뭐 별거 없었다... 하지만 야경은 진짜 멋있었다",
        // likes: 22,
        // rDate: "2023-12-27",
        // comments: 5,
        // },
      ],
      allCardList: [],
      searchKeyword: "",
      listCnt: 6,
      card: {},
      modal_Check: false,
      userData: null,
      index: 0,
      imagePath: null,
      selectedNickname: "",
    };
  },
  async created() {
    // 페이지가 로드될 때 초기 이미지 설정
    this.bgImage();
    window.addEventListener("scroll", this.handleScroll, true);
    this.userData = this.data;
    
  },
  methods: {
    // 해당 화면 Background 이미지 설정
    bgImage() {
      var newImage = "type4";
      this.$emit("bgImage", newImage);
    },
    async getFeedList() {
      var formData = new FormData();
      formData.append("userId", this.data.id);
      this.allCardList = [];
      this.searchKeyword = "";
      await axios
        .post(`http://${process.env.VUE_APP_BACK_END_URL}/feedList`, formData)
        .then((res) => {
          const data = res.data;
          var index = 0;
          for (const feed in data.feed) {
            const feedNum = data.feed[feed].feed_num;
            const images = function (data) {
              const images = [];
              for (const img of data.feedImg) {
                if (img.feed_num.feed_num === feedNum) {
                  // E:/git/final/Haru/Haru/src/main/resources/static/img/Feed/
                  // E:/900_팀 프로젝트/최종 프로젝트/Haru/Haru/src/main/resources/static/img/Feed/
                  images.push(
                    require(`${process.env.VUE_APP_IMG_BASE_URL}/Haru/src/main/resources/static/img/Feed/` +
                      img.feed_img)
                  );
                }
              }
              return images;
            };
            const comments = [];
            for (const comment of data.feedComment) {
              if (comment.feed_num.feed_num === feedNum) {
                comments.push(comment);
              }
            }
            const hashTag = [];
            for (const tag of data.feedHashTag) {
              if (tag.feed_num.feed_num === feedNum) {
                hashTag.push("#" + tag.feed_hash_tag);
              }
            }
            var myFeedLike = false;
            for (const myLike of data.myFeedLikes) {
              if (myLike.feed_num.feed_num === feedNum) {
                myFeedLike = true;
              }
            }
            const cardList = {
              profileImage: require(`${process.env.VUE_APP_IMG_BASE_URL}/Haru/src/main/resources/static/img/profileImg/` + data.feed[index].member.profile_img),
              uid: data.feed[index].member.user_id,
              profileLink: data.feed[index].member.profile_link,
              nickname: data.feed[index].member.nickname,
              mainCategory:
                data.feed[index].place_num.sub_category.main_category
                  .main_category,
              images: images(data),
              content: data.feed[index].feed_content,
              likes: data.feedLike[index],
              rDate: data.feed[index].feed_cdate,
              comments: comments.length,
              feedComments: comments,
              recommend: data.feed[index].place_num,
              feedNum: data.feed[index].feed_num,
              hashTag: hashTag,
              myFeedLike: myFeedLike,
            };
            this.allCardList.push(cardList);
            this.allCardList.sort((a, b) => {
              const dateA = new Date(a.rDate);
              const dateB = new Date(b.rDate);

              return dateB - dateA;
            });
            index++;
          }
          for (let i = 0; i < this.allCardList.length; i++) {
            this.allCardList[i].rDate = this.getTimeString(
              this.allCardList[i].rDate
            );
          }
          this.cardList = [];
          this.cardList = this.allCardList.slice(0, this.listCnt);
          console.log(data);
        });
    },
    async getMyFeedList(nickname) {
      var formData = new FormData();
      formData.append("userId", this.data.id);
      console.log(nickname);
      this.allCardList = [];
      this.searchKeyword = "";
      formData.append("nickname", nickname);
      await axios
        .post(`http://${process.env.VUE_APP_BACK_END_URL}/myFeedList`, formData)
        .then((res) => {
          const data = res.data;
          var index = 0;
          for (const feed in data.feed) {
            const feedNum = data.feed[feed].feed_num;
            const images = function (data) {
              const images = [];
              for (const img of data.feedImg) {
                if (img.feed_num.feed_num === feedNum) {
                  // E:/git/final/Haru/Haru/src/main/resources/static/img/Feed/
                  // E:/900_팀 프로젝트/최종 프로젝트/Haru/Haru/src/main/resources/static/img/Feed/
                  images.push(
                    require(`${process.env.VUE_APP_IMG_BASE_URL}/Haru/src/main/resources/static/img/Feed/` +
                      img.feed_img)
                  );
                }
              }
              return images;
            };
            const comments = [];
            for (const comment of data.feedComment) {
              if (comment.feed_num.feed_num === feedNum) {
                comments.push(comment);
              }
            }
            const hashTag = [];
            for (const tag of data.feedHashTag) {
              if (tag.feed_num.feed_num === feedNum) {
                hashTag.push("#" + tag.feed_hash_tag);
              }
            }
            var myFeedLike = false;
            for (const myLike of data.myFeedLikes) {
              if (myLike.feed_num.feed_num === feedNum) {
                myFeedLike = true;
              }
            }
            const cardList = {
              profileImage: require(`${process.env.VUE_APP_IMG_BASE_URL}/Haru/src/main/resources/static/img/profileImg/` + data.feed[index].member.profile_img),
              uid: data.feed[index].member.user_id,
              profileLink: data.feed[index].member.profile_link,
              nickname: data.feed[index].member.nickname,
              mainCategory:
                data.feed[index].place_num.sub_category.main_category
                  .main_category,
              images: images(data),
              content: data.feed[index].feed_content,
              likes: data.feedLike[index],
              rDate: data.feed[index].feed_cdate,
              comments: comments.length,
              feedComments: comments,
              recommend: data.feed[index].place_num,
              feedNum: data.feed[index].feed_num,
              hashTag: hashTag,
              myFeedLike: myFeedLike,
            };
            this.allCardList.push(cardList);
            this.allCardList.sort((a, b) => {
              const dateA = new Date(a.rDate);
              const dateB = new Date(b.rDate);

              return dateB - dateA;
            });
            index++;
          }
          for (let i = 0; i < this.allCardList.length; i++) {
            this.allCardList[i].rDate = this.getTimeString(
              this.allCardList[i].rDate
            );
          }
          this.cardList = [];
          this.cardList = this.allCardList.slice(0, this.listCnt);
          console.log(this.cardList);
          console.log("data", data);
          this.searchKeyword = "";
        });
      this.searchKeyword = "";
      this.selectedNickname = nickname;
    },
    searchFeed() {
      var formData = new FormData();
      formData.append("userId", this.data.id);
      this.allCardList = [];
      const search = document.querySelector(".hashtag-search-input").value;
      this.searchKeyword = search;
      if (search === "") {
        this.getFeedList();
      } else {
        axios
          .post(`http://${process.env.VUE_APP_BACK_END_URL}/feedList`, formData)
          .then((res) => {
            const data = res.data;
            var index = 0;
            for (const feed in data.feed) {
              const feedNum = data.feed[feed].feed_num;
              const images = function (data) {
                const images = [];
                for (const img of data.feedImg) {
                  if (img.feed_num.feed_num === feedNum) {
                    // E:/900_팀 프로젝트/최종 프로젝트/Haru/Haru/src/main/resources/static/img/Feed/  
                    images.push(
                      require(`${process.env.VUE_APP_IMG_BASE_URL}/Haru/src/main/resources/static/img/Feed/` +
                        img.feed_img)
                    );
                  }
                }
                return images;
              };
              const comments = [];
              for (const comment of data.feedComment) {
                if (comment.feed_num.feed_num === feedNum) {
                  comments.push(comment);
                }
              }
              const hashTag = [];
              for (const tag of data.feedHashTag) {
                if (tag.feed_num.feed_num === feedNum) {
                  hashTag.push("#" + tag.feed_hash_tag);
                }
              }
              var myFeedLike = false;
              for (const myLike of data.myFeedLikes) {
                if (myLike.feed_num.feed_num === feedNum) {
                  myFeedLike = true;
                }
              }
              const cardList = {
                profileImage: require(`${process.env.VUE_APP_IMG_BASE_URL}/Haru/src/main/resources/static/img/profileImg/` + data.feed[index].member.profile_img),
                uid: data.feed[index].member.user_id,
                profileLink: data.feed[index].member.profile_link,
                nickname: data.feed[index].member.nickname,
                mainCategory:
                  data.feed[index].place_num.sub_category.main_category
                    .main_category,
                images: images(data),
                content: data.feed[index].feed_content,
                likes: data.feedLike[index],
                rDate: data.feed[index].feed_cdate,
                comments: comments.length,
                feedComments: comments,
                recommend: data.feed[index].place_num,
                feedNum: data.feed[index].feed_num,
                hashTag: hashTag,
                myFeedLike: myFeedLike,
              };
              if (cardList.content.includes(search)) {
                this.allCardList.push(cardList);
              }
              this.allCardList.sort((a, b) => {
                const dateA = new Date(a.rDate);
                const dateB = new Date(b.rDate);

                return dateB - dateA;
              });

              index++;
            }
            for (let i = 0; i < this.allCardList.length; i++) {
              this.allCardList[i].rDate = this.getTimeString(
                this.allCardList[i].rDate
              );
            }
            this.cardList = [];
            this.cardList = this.allCardList.slice(0, this.listCnt);
            console.log(data);
          });
        this.selectedNickname = null;
      }
    },
    openModal(card, idx) {
      console.log("card", card, "idx", idx);
      this.modal_Check = !this.modal_Check;
      this.userData = this.data;
      this.index = idx;
      this.card = card;
    },
    closeModal() {
      this.modal_Check = false;
    },
    getNextFeedList() {
      this.listCnt += 3;
      this.cardList = this.allCardList.slice(0, this.listCnt);
    },
    handleScroll() {
      if (
        window.innerHeight + window.scrollY + 400 >=
        document.body.offsetHeight
      ) {
        this.getNextFeedList();
      }
    },
    getTimeString(isoTimeString) {
      const currentTime = new Date();
      const targetTime = new Date(isoTimeString);
      const timeDifference = currentTime - targetTime;

      const minutes = Math.floor(timeDifference / (1000 * 60));

      if (minutes < 1) {
        return "방금 전";
      } else if (minutes < 60) {
        return `${minutes}분 전`;
      } else if (minutes < 24 * 60) {
        const hours = Math.floor(minutes / 60);
        return `${hours}시간 전`;
      } else {
        const options = {
          year: "numeric",
          month: "2-digit",
          day: "2-digit",
          hour: "2-digit",
          minute: "2-digit",
        };
        return targetTime.toLocaleString("ko-KR", options);
      }
    },
  },
  setup() {
    const isLoggedIn = ref(false); // Use ref to create reactive isLoggedIn
    const data = ref([]); // Use ref to create reactive data

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
  async mounted() {
    // 페이지 로드하면서 리스트 불러오기
    if (this.$route.query.nickname) {
      // await this.$refs.feedList.getMyFeedList(this.$route.query.nickname);
      await this.getMyFeedList(this.$route.query.nickname);
      this.selectedNickname = this.$route.query.nickname;
      if (this.$route.query.feedNum) {
        console.log("allCardList", this.allCardList);
        for (const card of this.allCardList) {
          console.log("card", card);
          if (card.feedNum == this.$route.query.feedNum) {
            console.log("찾았다", card);
            this.card = card;
          }
        }
        this.openModal(this.card, this.allCardList.indexOf(this.card));
      }
    } else {
      await this.getFeedList();
    }
    const feedList = this.$refs.feedList;
    console.log("feedList", feedList);
    feedList.myNickname = this.$route.query.nickname;
  },
  components: {
    FeedDetail,
    FeedList,
  },
};
</script>
<style scoped>
@import "@/css/client/feed/feed.css";
</style>
