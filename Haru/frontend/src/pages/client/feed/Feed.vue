<template>
  <div class="container1">
    <FeedDetail v-if="modal_Check" @close-modal="closeModal" />
    <div class="feed-card-area" id="card-area">
      <!-- 해시태그 검색 -->
      <form action="" id="search-form">
        <div class="search-area">
          <input
            class="hashtag-search-input"
            type="text"
            placeholder="# 해시태그로 피드를 검색하세요"
          />
          <button class="hashtag-search-btn">
            <img src="@/img/Feed/search_btn.png" alt="" />
          </button>
        </div>
      </form>

      <!-- 피드 Start -->
      <FeedList :cardList="cardList" @open-modal="openModal" />
    </div>
  </div>
</template>
<script>
import FeedList from "@/components/client/feed/FeedList.vue";
import FeedDetail from "@/components/client/feed/FeedDetail.vue";

export default {
  name: "feedMain",
  data() {
    return {
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
    // 페이지가 로드될 때 초기 이미지 설정
    this.bgImage();
  },
  methods: {
    // 해당 화면 Background 이미지 설정
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
