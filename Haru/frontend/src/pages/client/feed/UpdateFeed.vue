<template>
  <div id="app">
    <div class="container1">
      <FeedRecommend
        v-if="modal_Check"
        @close-modal="closeModal"
        :RecommendList="RecommendList"
      />
      <form>
        <div class="bg">
          <div>
            <h1 class="page-upload-title">피드 수정하기</h1>
          </div>

          <!-- 사진 올리기 Area -->
          <div class="part-area">
            <p class="part-title">사진</p>
            <div class="upload-image-area">
              <div v-for="(item, idx) in 5" :key="idx">
                <label :for="'file' + (idx + 1)">
                  <div class="file" :class="{ upfile: idx === 0 }">
                    <img v-if="idx === 0" src="@/img/Feed/camera.png" alt="" />
                    <img @click="removeImg(idx)" v-if="idx > 0 && previewImageUrls[idx]" :src="previewImageUrls[idx]" alt="" style="object-fit: cover; width: 100%; height: 100%; border-radius: 15px;"/>
                  </div>
                </label>
                <input
                  @change="addImage"
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
            <p class="part-title">피드</p>
            <textarea
              @change="getHashTag()"
              id="contents"
              cols="68"
              rows="7"
              placeholder="피드 내용을 작성하세요."
              v-model="myFeed.contents"
            ></textarea>
          </div>

          <div class="part-area">
            <p class="part-title">기존 해시태그</p>
            <div class="hashtag-area">
              <span
                @click="toggleActive(oidx, 0)"
                v-for="(originHash, oidx) in myFeed.originHashtag"
                :key="oidx"
                :class="getTagClasses(oidx, 0)"
              >
                #{{ originHash }}
              </span>
            </div>
          </div>

          <div class="part-area">
            <div>
              <p class="part-title">
                추천 해시태그<br />
                <span style="color: #a7a7a7"
                  >사진을 업로드하면 해시태그를 추천해드려요!</span
                >
              </p>
            </div>
            <div class="hashtag-area">
              <span
                @click="toggleActive(hidx, 1)"
                v-for="(hash, hidx) in hashtag"
                :key="hidx"
                :class="getTagClasses(hidx, 1)"
              >
                #{{ hash }}
              </span>
            </div>
            <div class="hashtag-area-two">
              <div
                class="write-down-hash-area"
                v-for="(wHash, whIdx) in writeHashtag"
                :key="whIdx"
              >
                <span class="hash-icon">#</span>
                <input
                  type="text"
                  class="hashtag writeHash"
                  placeholder="직접 입력"
                  :value="wHash"
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
            <button type="button" id="uploadFeed" @click="updateFeed">피드 수정하기</button>
          </div>
        </div>
      </form>
    </div>
  </div>
</template>
<script>
import FeedRecommend from "@/components/client/feed/FeedRecommendModal.vue";
import axios from "axios";
import { ref, onMounted } from "vue";
import { jwtDecode } from "jwt-decode";

export default {
  name: "UpdateFeed",
  data() {
    return {
      myFeed: {
        uid: "abc",
        originHashtag: [],
        upimage: ["feed1.png", "feed2.jpg", "feed2.jpg"],
        contents:
          "고기 무한리필집 가고싶어서 집 근처에 뭐있나 봤는데 없길래 사진이나 찍자해서 스트레스 분석을 했거든요. 근데 고기집을 추천해주더라구요. 가서 먹었는데 진짜 걍 맛있어서 우왕굿",
      },
      hashtag: [
        // "삼겹살",
        // "소고기",
        // "무한리필",
        // "생고기",
        // "소고기",
        // "무한리필",
        // "생고기",
      ],
      originImage: [],
      originImagePreview: [],
      imageList: [],
      imageNameList: [],
      previewImageUrls: [],
      activeTags: [[], []],
      writeHashtag: [""],
      modal_Check: false,
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
    };
  },
  created() {
    this.previewImageUrls = ['default'];
    const feedData = history.state.feedData;
    var formData = new FormData();
    formData.append("feedNum", feedData.feed_num);
    axios.post(`http://${process.env.VUE_APP_BACK_END_URL}/getFeedImgAndHashTag`, formData).then((res) => {
        console.log(res.data);
        for (var i = 0; i < res.data.feedImg.length; i++) {
          this.imageList.push(res.data.feedImg[i].feed_img);
          this.originImage.push(res.data.feedImg[i].feed_img);
          this.previewImageUrls.push(require("@/img/Feed/" + res.data.feedImg[i].feed_img));
          this.originImagePreview.push(require("@/img/Feed/" + res.data.feedImg[i].feed_img));
          this.imageNameList.push(res.data.feedImg[i].feed_img);
        }
        for (var j = 0; j < res.data.feedHashTag.length; j++) {
          this.myFeed.originHashtag.push(res.data.feedHashTag[j].feed_hash_tag);
          this.activeTags[0].push(j);
        }
      });
    console.log('피드 데이터 : ', feedData);
    this.myFeed.uid = feedData.member.user_id;
    this.myFeed.contents = feedData.feed_content;
    
    axios.get(`http://${process.env.VUE_APP_DJANGO_CROSS_URL}/text/feedKeyword`, {
        params: {
          contents: this.myFeed.contents,
        },
      }).then((res) => {
        console.log(res.data);
        this.hashtag = res.data;
        this.hashtag = this.hashtag.filter(item => !this.myFeed.originHashtag.includes(item));
      });

    this.upimage = feedData.feedImg;
    
    this.bgImage();
  },
  methods: {
    bgImage() {
      var newImage = "type4";
      this.$emit("bgImage", newImage);
    },
    toggleActive(index, num) {
      const indexOfTag = this.activeTags[num].indexOf(index);

      if (indexOfTag === -1) {
        this.activeTags[num].push(index);
      } else {
        this.activeTags[num].splice(indexOfTag, 1);
      }
      console.log(this.activeTags)
      console.log(this.myFeed.originHashtag)
      console.log(this.hashtag)
    },

    getTagClasses(index, num) {
      return {
        hashtag: true,
        "cursor-p": true,
        "hash-active": this.activeTags[num].includes(index),
      };
    },

    openModal() {
      this.modal_Check = !this.modal_Check;
    },

    closeModal() {
      this.modal_Check = false;
    },
    getHashTag() {
      axios.get(`http://${process.env.VUE_APP_DJANGO_CROSS_URL}/text/feedKeyword`, {
        params: {
          contents: document.getElementById("contents").value,
        },
      }).then((res) => {
        console.log(res.data);
        this.hashtag = res.data;
        this.hashtag = this.hashtag.filter(item => !this.myFeed.originHashtag.includes(item));
      });
    },
    // 해시태그 직접 입력
    addHashTag() {
      // 기존에 입력 값 배열에 넣기
      for (var i = 0; i < this.writeHashtag.length; i++) {
        var refName = "writeHashtagInput" + i;
        this.writeHashtag[i] = this.$refs[refName][0].value;
      }

      // 배열의 길이가 5개 이하면 입력하는 항목란 추가
      if (this.writeHashtag.length < 5) {
        this.writeHashtag.length = this.writeHashtag.length + 1;

      } else {
        alert("해시태그는 5개 이하로 입력 가능합니다.");
      }
    },
    addImage(e) {
      const file = e.target.files[0];
      this.imageList.push(file);
      this.imageNameList.push(file.name);
      console.log("이미지 리스트", this.imageList);
      console.log(file);

      this.previewImageUrls = ["default"];
      if (this.originImage.length !== 0) {
        for (const img of this.originImagePreview) {
          this.previewImageUrls.push(img);
        }
      }
      for (let i = 0; i < Math.min(this.imageList.length, 5); i++) {
        const file = this.imageList[i];

        if (file && file.type && file.type.startsWith('image/')) {
          const reader = new FileReader();

          reader.onload = () => {
            this.previewImageUrls.push(reader.result);
          };
          reader.readAsDataURL(file);
        }
      }
      console.log("이미지 이름 리스트", this.imageNameList)
      console.log("미리보기 리스트", this.previewImageUrls)
      console.log("기존 이미지 리스트", this.originImage);
      console.log("기존 이미지 미리보기 리스트", this.originImagePreview)
      
    },
    removeImg(idx) {
      if (idx - 1 < this.originImage.length) {
        this.originImage.splice(idx - 1, 1);
        this.originImagePreview.splice(idx - 1, 1);
      }
      this.imageList.splice(idx - 1, 1);
      console.log("이미지 리스트", this.imageList);
      this.imageNameList.splice(idx - 1, 1);
      console.log("이미지 이름 리스트", this.imageNameList)
      this.previewImageUrls.splice(idx, 1);
      console.log("미리보기 리스트", this.previewImageUrls);
      console.log("기존 이미지 리스트", this.originImage);
      console.log("기존 이미지 미리보기 리스트", this.originImagePreview)
    },
    updateFeed() {
      var formData = new FormData();
      for (const index in this.activeTags[0]) {
        formData.append("hashTag", this.myFeed.originHashtag[this.activeTags[0][index]]);
      }
      for (const index in this.activeTags[1]) {
        formData.append("hashTag", this.hashtag[this.activeTags[1][index]]);
      }
      for (const [index, tag] of this.writeHashtag.entries()) {
        console.log(tag);

        // 마지막 요소를 제외하고 싶다면
        if (index < this.writeHashtag.length - 1) {
          formData.append("hashTag", tag);
        }
      }
      console.log(this.writeHashtag)
      for (const [index, image] of this.imageList.entries()) {
        if (typeof image !== "string") {
          formData.append("file", image);
          formData.append("imageName", this.imageNameList[index]);
        }
      }
      formData.append("feedNum", history.state.feedData.feed_num);
      formData.append("contents", document.getElementById("contents").value);
      formData.append("userId", this.data.id);
      formData.append("placeNum", history.state.feedData.place_num.place_num);
      console.log("해시태그", formData.getAll("hashTag"));
      console.log("이미지", formData.getAll("file"));
      console.log("이미지 이름", formData.getAll("imageName"));
      console.log("내용", formData.getAll("contents"));
      console.log("아이디", formData.getAll("userId"));
      console.log("장소넘버", formData.getAll("placeNum"));
      axios
        .post(
          `http://${process.env.VUE_APP_BACK_END_URL}/updateFeed`,
          formData,
          {
            headers: {
              "Content-Type": "multipart/form-data",
            },
          }
        )
        .then(() => {
          alert("피드가 수정되었습니다.");
        }).then(() => {
          this.$router.push("/feed");
        })
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
  components: { FeedRecommend },
};
</script>
<style scoped>
@import url("@/css/client/feed/uploadFeed.css");
.hashtag-area {
  margin-top: -10px;
}

.btn-area {
  width: 660px;
}

/* 전송 버튼 */
#uploadFeed {
  background-color: #ffdbb0;
}

#uploadFeed:hover {
  background-color: #ffa83f;
}
.big-ctlbtn {
  width: 75px;
  height: 42px;
  font-size: 1rem;
  font-weight: 500;
  margin-top: 5px;
}
</style>
