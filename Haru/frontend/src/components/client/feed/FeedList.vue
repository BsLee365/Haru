<template>
	<!-- 피드 리스트 화면 - 카드 하나  -->
	<div>
		<div v-if="this.$route.query.nickname || selectedNickname" class="page-title-area">
			<h1 class="page-upload-title">{{ myNickname }}님의 피드</h1>
		</div>
		<div class="feed-card" v-for="(i, idx) in cardList" :key="idx">
			<div class="feed-card-header">
				<!-- 프로필 사진, 닉네임 -->
				<div class="feed-profile-area">
					<div class="feed-profile-img">
						<a href="#" @click="getMyFeedList(i.nickname)"><img :src="i.profileImage" /></a>
					</div>
					<a href="#" @click="getMyFeedList(i.nickname)"
						><span class="feed-nickname">{{ i.nickname }}</span></a
					>
				</div>

				<!-- 큰 카테고리 -->
				<div class="feed-main-category">
					{{ i.mainCategory }}
				</div>
			</div>

			<div class="feed-card-body">
				<!-- 이미지 Area -->
				<div class="card-body-image">
					<!-- 본인 게시글인 경우 수정 dim 처리 -->
					<div class="feed-update-area" v-if="i.uid === 'abc'">
						<a href="/UpdateFeed">
							<img class="cursor-p" src="@/img/Feed/update.png" alt="" />
							<p class="cursor-p">수정하기</p>
						</a>
					</div>

					<div :id="'carousel' + idx" class="carousel slide" data-bs-interval="false">
						<!-- 인디케이터 -->
						<div class="carousel-indicators" v-if="i.images.length > 1">
							<button v-for="(indi, indiIdx) in i.images" :key="indiIdx" type="button" :data-bs-target="'#carousel' + idx" :data-bs-slide-to="indiIdx" :class="{ active: indiIdx === 0 }"></button>
						</div>

						<!-- 이미지 -->
						<div class="carousel-inner">
							<div v-for="(image, imgIdx) in i.images" :key="imgIdx" class="carousel-item" :class="{ active: imgIdx === 0 }">
								<img :src="image" class="d-block w-100" />
							</div>
						</div>

						<!-- 이전, 다음 버튼 -->
						<button class="carousel-control-prev" type="button" :data-bs-target="'#carousel' + idx" data-bs-slide="prev" v-if="i.images.length > 1">
							<span class="carousel-control-prev-icon" aria-hidden="true"></span>
							<span class="visually-hidden">Previous</span>
						</button>
						<button class="carousel-control-next" type="button" :data-bs-target="'#carousel' + idx" data-bs-slide="next" v-if="i.images.length > 1">
							<span class="carousel-control-next-icon" aria-hidden="true"></span>
							<span class="visually-hidden">Next</span>
						</button>
					</div>
				</div>
				<!-- 본문 contents -->
				<!-- 이미지 여러개면 margin-top 조금 올리기 -->
				<div class="feed-card-content">
					<p>
						{{ i.content.slice(0, 90) }}
						<span v-if="i.content.length > 100" class="more cursor-p" id="more" @click="$emit('open-modal', i, idx)"> ... 더보기 </span>
					</p>
					<br />
					<p id="hashTag">{{ i.hashTag.join(" ") }}</p>
				</div>
				<!-- 좋아요, 댓글 버튼 -->
				<div class="card-btn">
					<div>
						<div class="heart">
							<img v-if="!i.myFeedLike" class="cursor-p" src="@/img/Feed/heart.png" id="heart" @click="sendLikeInFeed(i.feedNum, i.uid)" />
							<img v-if="i.myFeedLike" class="cursor-p" src="@/img/Feed/heart.png" id="heart" @click="sendLikeInFeed(i.feedNum, i.uid)" style="filter: opacity(0.4) drop-shadow(0 0 0 red)" />
							<span>{{ i.likes }}</span>
						</div>
						<div class="comment">
							<img class="cursor-p" src="@/img/Feed/comment.png" id="comment" @click="$emit('open-modal', i, idx)" />
							<span @click="$emit('open-modal', i, idx)" class="cursor-p">{{ i.comments }}</span>
						</div>
					</div>
					<p class="rDate">{{ i.rDate }}</p>
				</div>
			</div>
		</div>
	</div>
</template>

<script>
import axios from "axios";
import { ref, onMounted } from "vue";
import { jwtDecode } from "jwt-decode";

export default {
	name: "FeedList",
	data() {
		return {
			modal_Check: false,
			myNickname: "",
			hashTag: "",
		};
	},
	props: ["cardList", "listCnt", "allCardList", "userData", "searchKeyword", "searchFeed", "selectedNickname"],
	methods: {
		getMyFeedList(nickname) {
			this.$emit("getMyFeedList", nickname);
			this.myNickname = nickname;
		},
		sendLikeInFeed(feedNum, feedUserId) { // 피드 리스트에서 좋아요 클릭 메소드
			console.log("uid : ", this.data.id, "feedNum : ", feedNum, "feedUserId : ", feedUserId);
			this.formData = new FormData();
			this.formData.append("feedNum", feedNum);
			this.formData.append("userId", this.data.id);
			this.formData.append("feedUserId", feedUserId);
			axios.post(`http://${process.env.VUE_APP_BACK_END_URL}/modifyFeedLike`, this.formData).then((res) => { // 해당 피드에 좋아요가 있으면 삭제 없으면 추가
				console.log("modifyFeedLike");
				if (this.searchKeyword) { // 검색어가 있으면 검색어로 검색된 리스트로 갱신
					this.searchFeed();
				} else if (this.$route.query.nickname) { // 쿼리에 닉네임이 있으면 해당 유저의 피드 리스트로 갱신
					this.getMyFeedList(this.$route.query.nickname);
				} else { // 아무것도 없으면 전체 피드 리스트로 갱신
					this.$emit("getFeedList");
				}

				console.log(res);
				console.log("좋아요 갱신");
				this.likeload += 1;
			});
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
		this.myNickname = this.selectedNickname;
		console.log("id", this.data.id);
	},
};
</script>
<style scoped>
.page-upload-title {
	margin-left: 43px;
}
#hashTag {
	color: #928e8e;
}
</style>
