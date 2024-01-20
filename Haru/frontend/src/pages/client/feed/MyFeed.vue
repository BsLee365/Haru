<template>
  <div class="container1">
    <FeedDetail v-if="modal_Check" @close-modal="closeModal" />
    <div class="feed-card-area" id="card-area">
      <div class="page-title-area">
        <h1 class="page-upload-title">{{ cardList[0].nickname }} 님의 피드</h1>
      </div>

      <!-- 피드 Start -->
      <FeedList :cardList="cardList" @open-modal="openModal" />
    </div>
  </div>
</template>
<script>
import FeedList from "@/components/client/feed/FeedList.vue";
import FeedDetail from "@/components/client/feed/FeedDetail.vue";

export default {
  name: "MyFeed",
  data() {
    return {
      isLoggedIn: false,
      AccessToken: "",
      cardList: [
        {
          profileImage: require("@/img/Feed/no_profile.png"),
          uid: "abc",
          profileLink: "#",
          nickname: "닉네임",
          mainCategory: "액티비티",
          images: [
            require("@/img/Feed/feed1.png"),
            require("@/img/Feed/feed3.jpg"),
            require("@/img/Feed/feed2.jpg"),
          ],
          content:
            "오늘 정말 좋았던거 같은데... 뭐 별거 없었다... 하지만 야경은 진짜 멋있었다",
          likes: 22,
          rDate: "2023-12-31",
          comments: 5,
        },
        {
          profileImage: require("@/img/Feed/no_profile.png"),
          uid: "abcd",
          profileLink: "#",
          nickname: "닉네임",
          mainCategory: "액티비티",
          images: [require("@/img/Feed/feed3.jpg")],
          content:
            "오늘 정말 좋았던거 같은데... 뭐 별거 없었다... 하지만 야경은 진짜 멋있었다 오늘 정말 좋았던거 같은데... 뭐 별거 없었다... 하지만 야경은 진짜 멋있었다 꼭 또가고 싶어졌다",
          likes: 22,
          rDate: "2023-12-30",
          comments: 5,
        },
        {
          profileImage: require("@/img/Feed/no_profile.png"),
          uid: "abcd",
          profileLink: "#",
          nickname: "닉네임",
          mainCategory: "액티비티",
          images: [
            require("@/img/Feed/feed3.jpg"),
            require("@/img/Feed/feed2.jpg"),
          ],
          content:
            "오늘 정말 좋았던거 같은데... 뭐 별거 없었다... 하지만 야경은 진짜 멋있었다",
          likes: 22,
          rDate: "2023-12-29",
          comments: 5,
        },
        {
          profileImage: require("@/img/Feed/no_profile.png"),
          uid: "abc",
          profileLink: "#",
          nickname: "닉네임",
          mainCategory: "액티비티",
          images: [
            require("@/img/Feed/feed1.png"),
            require("@/img/Feed/feed3.jpg"),
            require("@/img/Feed/feed2.jpg"),
          ],
          content:
            "오늘 정말 좋았던거 같은데... 뭐 별거 없었다... 하지만 야경은 진짜 멋있었다",
          likes: 22,
          rDate: "2023-12-28",
          comments: 5,
        },
        {
          profileImage: require("@/img/Feed/no_profile.png"),
          uid: "abc",
          profileLink: "#",
          nickname: "닉네임",
          mainCategory: "액티비티",
          images: [
            require("@/img/Feed/feed1.png"),
            require("@/img/Feed/feed3.jpg"),
            require("@/img/Feed/feed2.jpg"),
          ],
          content:
            "오늘 정말 좋았던거 같은데... 뭐 별거 없었다... 하지만 야경은 진짜 멋있었다",
          likes: 22,
          rDate: "2023-12-27",
          comments: 5,
        },
      ],
      modal_Check: false,
    };
  },
  created() {
    this.bgImage();
    this.getToken();
  },
  methods: {
    bgImage() {
      var newImage = "type4";
      this.$emit("bgImage", newImage);
    },
    openModal() {
      this.modal_Check = !this.modal_Check;
    },
    closeModal() {
      this.modal_Check = false;
    },
    getToken() {
      this.AccessToken = localStorage.getItem("jwtToken");
      console.log(this.AccessToken);
      if (this.AccessToken != null) {
        this.isLoggedIn = true;
      } else {
        this.isLoggedIn = false;
        this.$router.push("/login");
      }
    },
  },
  components: {
    FeedDetail,
    FeedList,
  },
};
</script>
<style scoped>
@import "@/css/client/feed/feed.css";
.page-upload-title {
  margin-left: 43px;
}
</style>
