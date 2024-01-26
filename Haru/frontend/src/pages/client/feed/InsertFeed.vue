<template>
	<div class="container1">
		<FeedRecommend v-if="modal_Check" @close-modal="closeModal" @get-rec-list="getRecList" :RecommendList="MyRecommendList" :selectedRecPlace="selectedRecPlace" />
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
									<img @click="removeImg(idx)" v-if="idx > 0 && previewImageUrls[idx]" :src="previewImageUrls[idx]" alt="" style="object-fit: cover; width: 100%; height: 100%; border-radius: 15px" />
								</div>
							</label>
							<input @change="addImage" v-if="idx === 0" type="file" :id="'file' + (idx + 1)" accept="image/*" hidden />
						</div>
					</div>
				</div>

				<div class="part-area">
					<p class="part-title">피드 작성</p>
					<textarea id="contents" cols="68" rows="7" placeholder="피드 내용을 작성하세요." @change="getHashTag"></textarea>
				</div>

				<div class="part-area">
					<div>
						<p class="part-title">
							추천 해시태그<br />
							<span style="color: #a7a7a7">피드를 작성하면 해시태그를 추천해드려요!</span>
						</p>
					</div>
					<div class="hashtag-area">
						<span @click="toggleActive(hidx)" v-for="(hash, hidx) in hashtag" :key="hidx" :class="getTagClasses(hidx)"> #{{ hash }} </span>
					</div>
				</div>

				<div class="part-area">
					<div>
						<p class="part-title">
							해시태그 직접 입력<br />
							<span style="color: #a7a7a7">추가하고 싶은 해시태그를 직접 입력해보세요!</span>
						</p>
					</div>
					<div class="hashtag-area-two">
						<div class="write-down-hash-area" v-for="(wHash, whIdx) in writeHashtag" :key="whIdx">
							<span class="hash-icon">#</span>
							<input type="text" class="hashtag writeHash" placeholder="직접 입력" :value="wHash" :ref="'writeHashtagInput' + whIdx" @change="addHashTag" />
							<img id="deleteIcon" src="@\img\Feed\X.png" @click="removeBox(whIdx)" />
						</div>
						<div class="add-hashtag-area">
							<button type="button" @click="addHashTagBox" class="add-hashtag-btn big-ctlbtn insert-btn">추가</button>
						</div>
					</div>
				</div>

				<!-- 추천 장소 선택 -->
				<div class="part-area">
					<button type="button" class="part-title recommend-modal-open-btn else-btn" @click="openModal">추천 장소 선택</button>
				</div>

				<div class="updateFeed-btn-area">
					<button type="button" id="uploadFeed" @click="uploadFeed">피드 올리기</button>
				</div>
			</div>
		</form>
	</div>
</template>
<script>
import FeedRecommend from "@/components/client/feed/FeedRecommendModal.vue";
import moment from "moment";
import axios from "axios";
import { ref, onMounted } from "vue";
import { jwtDecode } from "jwt-decode";
export default {
	name: "InsertFeed",
	data() {
		return {
			imageList: [],
			previewImageUrls: [],
			isLoggedIn: false,
			AccessToken: "",
			uid: "abc",
			hashtag: [],
			writeHashtag: [""], // 직접 입력 해시태그
			activeTags: [], // 활성화된 해시태그
			modal_Check: false,
      MyRecommendList: [], // 추천 리스트
      selectedRecPlace: {}, // 모달에서 선택된 항목 저장하는 곳
		};
	},
	created() {
		this.bgImage();
		this.getToken();
	},
	methods: {
    getMyRecommendList() {
      // 시작 날짜, 끝 날짜 설정
      var endRecDate = moment().add(1,"days").format("YYYY-MM-DD");
      var startRecDate = moment().subtract(1,"months").format("YYYY-MM-DD");

      // console.log(`시작~ 끝 날짜 !! : ${this.data.id} / ${startRecDate}, ${endRecDate}`);

      axios
          .post(`http://${process.env.VUE_APP_BACK_END_URL}/recommend/getMyRecList`, {
            userid: this.data.id,
            startdate: startRecDate,
            enddate: endRecDate,
          })
          .then((res) => {
            // console.log(res.data); // place, place_recommend_list, wish_list 가져옴
            this.MyRecommendList = res.data;
            // console.log(this.MyRecommendList);
          })
          .catch((error) => {
            console.error("error! " + error);
          });
    },
		bgImage() {
			var newImage = "type4";
			this.$emit("bgImage", newImage);
		},
		getToken() {
			this.AccessToken = localStorage.getItem("jwtToken");
			// console.log(this.AccessToken);
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
				if (this.writeHashtag.length + this.activeTags.length < 5) {
					this.activeTags.push(index);
				} else {
					alert("해시태그는 5개 이하로 입력 가능합니다.");
				}
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
		addHashTag() {
			// 기존에 입력 값 배열에 넣기
			for (var i = 0; i < this.writeHashtag.length; i++) {
				var refName = "writeHashtagInput" + i;
				this.writeHashtag[i] = this.$refs[refName][0].value;
			}

			// 배열의 길이가 5개 이하면 입력하는 항목란 추가
			if (this.writeHashtag.length + this.activeTags.length < 5) {
				this.writeHashtag.length = this.writeHashtag.length + 1;
			} else {
				alert("해시태그는 5개 이하로 입력 가능합니다.");
			}
			console.log(this.writeHashtag);
		},
		addHashTagBox() {
			if (this.writeHashtag.length + this.activeTags.length < 5) {
				this.writeHashtag.length = this.writeHashtag.length + 1;
			} else if (this.$refs["writeHashtagInput" + 4]) {
				alert("해시태그는 5개 이하로 입력 가능합니다.");
			} else {
				alert("해시태그는 5개 이하로 입력 가능합니다.");
			}
		},
		removeBox(whIdx) {
			this.writeHashtag.splice(whIdx, 1);
		},

    getRecList(item) {
      this.selectedRecPlace = item;
      console.log('insert까지 넘어온 값: ' + this.selectedRecPlace);
    },

		openModal() {
			this.modal_Check = true;
		},

		closeModal() {
			this.modal_Check = false;
		},
		getHashTag() {
			axios
				.get(`http://${process.env.VUE_APP_DJANGO_CROSS_URL}/text/feedKeyword`, {
					// 경로 직접 작성해야 오류가 안남... 0124 AI 머지
					// axios
					// 	.get(`http://192.168.0.215:8000/text/feedKeyword`, {
					params: {
						contents: document.getElementById("contents").value,
					},
				})
				.then((res) => {
					this.activeTags = [];
					console.log(res.data);
					this.hashtag = res.data;
				});
		},
		uploadFeed() {
			var formData = new FormData();
			for (const index in this.activeTags) {
				formData.append("hashTag", this.hashtag[this.activeTags[index]]);
			}
			for (const [index, tag] of this.writeHashtag.entries()) {
				console.log(tag);

				// 마지막 요소를 제외하고 싶다면
				if (index < this.writeHashtag.length - 1) {
					formData.append("hashTag", tag);
				}
			}
			console.log(this.writeHashtag);
			for (const image of this.imageList) {
				formData.append("file", image);
			}
			formData.append("contents", document.getElementById("contents").value);
			formData.append("userId", this.data.id);
			formData.append("placeNum", this.selectedRecPlace.place_num);
			console.log("해시태그", formData.getAll("hashTag"));
			console.log("이미지", formData.getAll("file"));
			console.log("내용", formData.getAll("contents"));
			console.log("아이디", formData.getAll("userId"));
			console.log("장소넘버", formData.getAll("placeNum"));
			axios
				.post(`http://${process.env.VUE_APP_BACK_END_URL}/uploadFeed`, formData, {
					headers: {
						"Content-Type": "multipart/form-data",
					},
				})
				.then(() => {
					alert("피드가 등록되었습니다.");
				})
				.then(() => {
					this.$router.push("/feed");
				});
		},
		addImage(e) {
			const file = e.target.files[0];
			this.imageList.push(file);
			console.log("이미지 리스트", this.imageList);
			console.log(file);

			this.previewImageUrls = ["default"];
			for (let i = 0; i < Math.min(this.imageList.length, 5); i++) {
				const file = this.imageList[i];

				if (file && file.type.startsWith("image/")) {
					const reader = new FileReader();

					reader.onload = () => {
						this.previewImageUrls.push(reader.result);
					};
					reader.readAsDataURL(file);
				}
			}
			console.log("미리보기 리스트", this.previewImageUrls);
		},
		removeImg(idx) {
			this.imageList.splice(idx - 1, 1);
			console.log("이미지 리스트", this.imageList);
			this.previewImageUrls.splice(idx, 1);
			console.log("미리보기 리스트", this.previewImageUrls);
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
			axios.get(`http://${process.env.VUE_APP_BACK_END_URL}/api/auth/logout`).then((res) => {
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
  mounted() {
    this.getMyRecommendList();
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
	margin-top: 5px;
}
</style>
