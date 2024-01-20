<template>
  <div class="container1">
    <FeedRecommend
      v-if="modal_Check"
      @close-modal="closeModal"
      :RecommendList="RecommendList"
    />
    <form>
      <div class="bg">
        <div>
          <h1 class="page-upload-title">피드 작성하기</h1>
        </div>

        <!-- 사진 올리기 Area -->
        <div class="part-area">
          <p class="part-title">사진 올리기</p>
          <div class="upload-image-area">
            <div v-for="(item, idx) in 5" :key="idx">
              <label :for="'file' + (idx + 1)">
                <div class="file" :class="{ upfile: idx === 0 }">
                  <img v-if="idx === 0" src="@/img/Feed/camera.png" alt="" />
                </div>
              </label>
              <input
                v-if="idx === 0"
                type="file"
                :id="'file' + (idx + 1)"
                accept="image/*"
                hidden
              />
            </div>
          </div>
        </div>

        <div class="part-area">
          <p class="part-title">피드 작성</p>
          <textarea
            id="contents"
            cols="68"
            rows="7"
            placeholder="피드 내용을 작성하세요."
          ></textarea>
        </div>

        <div class="part-area">
          <div>
            <p class="part-title">
              추천 해시태그<br />
              <span style="color: #a7a7a7"
                >피드를 작성하면 해시태그를 추천해드려요!</span
              >
            </p>
          </div>
          <div class="hashtag-area">
            <span
              @click="toggleActive(hidx)"
              v-for="(hash, hidx) in hashtag"
              :key="hidx"
              :class="getTagClasses(hidx)"
            >
              #{{ hash }}
            </span>
          </div>
        </div>

        <div class="part-area">
          <div>
            <p class="part-title">
              해시태그 직접 입력<br />
              <span style="color: #a7a7a7"
                >추가하고 싶은 해시태그를 직접 입력해보세요!</span
              >
            </p>
          </div>
          <div class="hashtag-area-two">
            <div
              class="write-down-hash-area"
              v-for="(whIdx, wHash) in writeHashtag"
              :key="whIdx"
            >
              <span class="hash-icon">#</span>
              <input
                type="text"
                class="hashtag writeHash"
                placeholder="직접 입력하기"
                :key="wHash"
                :ref="'writeHashtagInput' + whIdx"
              />
            </div>
            <div class="add-hashtag-area">
              <button
                @click.prevent="addHashTag"
                class="add-hashtag-btn big-ctlbtn insert-btn"
              >
                추가
              </button>
            </div>
          </div>
        </div>

        <!-- 추천 장소 선택 -->
        <div class="part-area">
          <button
            type="button"
            class="part-title recommend-modal-open-btn else-btn"
            @click="openModal"
          >
            추천 장소 선택
          </button>
        </div>

        <div class="updateFeed-btn-area">
          <button id="uploadFeed">피드 올리기</button>
        </div>
      </div>
    </form>
  </div>
</template>
<script>
import FeedRecommend from "@/components/client/feed/FeedRecommendModal.vue";

export default {
  name: "InsertFeed",
  data() {
    return {
      isLoggedIn: false,
      AccessToken: "",
      uid: "abc",
      hashtag: [
        "고기",
        "돼지고기",
        "목살",
        "삼겹살",
        "소고기",
        "무한리필",
        "생고기",
      ],
      writeHashtag: [""], // 직접 입력 해시태그
      activeTags: [], // 활성화된 해시태그
      RecommendList: [
        {
          rdate: "9월 23일",
          recList: [
            {
              storeName: "신논현역 딸부자네 불백",
              stAddress: "서울시 강남구 꼬마빌딩 1층",
              img: require("@/img/Feed/bul.png"),
              link: "#",
              hashtag: ["푸드", "맛집", "고기", "돼지고기", "갈매기살"],
              heartOnOff: "on",
            },
            {
              storeName: "신논현역 버거킹",
              stAddress: "서울시 강남구 꼬마빌딩 2층",
              img: require("@/img/Feed/bul.png"),
              link: "#",
              hashtag: ["패스트푸드", "맛집", "와퍼"],
              heartOnOff: "off",
            },
            {
              storeName: "신논현역 버거킹",
              stAddress: "서울시 강남구 꼬마빌딩 2층",
              img: require("@/img/Feed/bul.png"),
              link: "#",
              hashtag: ["패스트푸드", "맛집", "와퍼"],
              heartOnOff: "off",
            },
          ],
        },
        {
          rdate: "9월 22일",
          recList: [
            {
              storeName: "신논현역 와플대학",
              stAddress: "서울시 강남구 꼬마빌딩 3층",
              img: require("@/img/Feed/bul.png"),
              link: "#",
              hashtag: [
                "카페",
                "맛집",
                "와플",
                "애플시나몬",
                "레몬에이드",
                "레몬에이드",
                "레몬에이드",
              ],
              heartOnOff: "on",
            },
            {
              storeName: "신논현역 딸부자네 불백",
              stAddress: "서울시 강남구 꼬마빌딩 1층",
              img: require("@/img/Feed/bul.png"),
              link: "#",
              hashtag: [
                "푸드",
                "맛집",
                "고기",
                "돼지고기",
                "갈매기살",
                "갈매기살",
              ],
              heartOnOff: "on",
            },
          ],
        },
        {
          rdate: "9월 21일",
          recList: [
            {
              storeName: "신논현역 버거킹",
              stAddress: "서울시 강남구 꼬마빌딩 2층",
              img: require("@/img/Feed/bul.png"),
              hashtag: ["패스트푸드", "맛집", "와퍼"],
              heartOnOff: "off",
            },
          ],
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
    toggleActive(index) {
      const indexOfTag = this.activeTags.indexOf(index);

      if (indexOfTag === -1) {
        this.activeTags.push(index);
      } else {
        this.activeTags.splice(indexOfTag, 1);
      }
    },
    getTagClasses(index) {
      return {
        hashtag: true,
        "cursor-p": true,
        "hash-active": this.activeTags.includes(index),
      };
    },
    // 해시태그 직접 입력
    addHashTag() {},

    openModal() {
      this.modal_Check = true;
    },

    closeModal() {
      this.modal_Check = false;
    },
  },
  components: { FeedRecommend },
};
</script>
<style scoped>
@import url("@/css/client/feed/uploadFeed.css");
.big-ctlbtn {
  width: 75px;
  height: 42px;
  font-size: 1rem;
  font-weight: 500;
}
</style>
