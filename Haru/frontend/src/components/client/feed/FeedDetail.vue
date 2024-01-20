<template>
	<div>
		<!-- --------------------- modal Start ------------------------->
		<div class="feed-modal-wrap" id="modal-wrap" @click="$emit('close-modal')">
			<div class="feed-modal-container feed-detail" @click.stop>
				<div class="feed-detail-modal-btn">
					<!-- X 버튼 -->
					<button id="common-modal-close" @click="$emit('close-modal')"></button>
				</div>
				<div class="feed-card-content-area">
					<div class="feed-card-header">
						<div class="left-header">
							<!-- 프로필 사진, 닉네임 -->
							<div class="feed-profile-area">
								<div class="feed-profile-img">
									<a href="#"><img :src="card.profileImage" alt="프로필 이미지" /></a>
								</div>
								<a href="#"
									><span class="feed-nickname">{{ card.nickname }}</span></a
								>
							</div>

							<!-- 큰 카테고리 -->
							<div class="feed-main-category">{{ card.mainCategory }}</div>
						</div>
						<h3 class="rec-title">여기를 추천받았어요!</h3>
					</div>
				</div>

				<div class="feed-modal">
					<div class="modal-contents">
						<!-- 이미지 Area -->
						<div class="feed-card-body-image">
							<div id="myCarousel" class="carousel slide" data-bs-interval="false">
								<!-- 인디케이터 -->
								<div class="carousel-indicators" v-if="card.images.length > 1">
									<button v-for="(indi, midx) in card.images" :key="midx" type="button" data-bs-target="#myCarousel" :data-bs-slide-to="midx" :class="{ active: midx === 0 }"></button>
								</div>

								<!-- 이미지 -->
								<div class="carousel-inner carousel-inner-m">
									<div v-for="(image, imgIdx) in card.images" :key="imgIdx" class="carousel-item" :class="{ active: imgIdx === 0 }">
										<img :src="image" class="d-block w-100" alt="피드 이미지" />
									</div>
								</div>

								<!-- 이전, 다음 버튼 -->
								<button class="carousel-control-prev" type="button" data-bs-target="#myCarousel" data-bs-slide="prev" v-if="card.images.length > 1">
									<span class="carousel-control-prev-icon" aria-hidden="true"></span>
									<span class="visually-hidden">Previous</span>
								</button>
								<button class="carousel-control-next" type="button" data-bs-target="#myCarousel" data-bs-slide="next" v-if="card.images.length > 1">
									<span class="carousel-control-next-icon" aria-hidden="true"></span>
									<span class="visually-hidden">Next</span>
								</button>
							</div>
						</div>

						<!-- 본문 contents -->
						<!-- 이미지 여러개면 margin-top 조금 올리기 -->
						<div class="feed-card-content">
							<p>{{ card.content }}</p>
						</div>

						<!-- 좋아요, 댓글 버튼 -->
						<div class="card-btn">
							<div>
								<div class="heart" :key="likeload">
									<img class="cursor-p" src="@/img/Feed/heart.png" id="heart" @click="sendLike" />
									<span>{{ card.likes }}</span>
								</div>
								<div class="comment">
									<img src="@/img/Feed/comment.png" id="comment" />
									<span :key="commentload">{{ card.comments }}</span>
								</div>
							</div>

							<p class="rDate">{{ card.rDate }}</p>
						</div>
					</div>

					<!-- 우측 댓글 영역 -->
					<div class="modal-comment-area">
						<!-- 추천 받은 장소 -->
						<div class="feed-recommend-area">
							<div class="rec-detail-img-area">
								<a href="{{recommend.url}}"><img class="rec-detail-img" :src="recommend.img" alt="" /></a>
							</div>
							<div class="rec-detail">
								<a href="{{recommend.url}}">
									<p class="rec-detail-title">{{ recommend.name }}</p>
									<p>{{ recommend.address }}</p>
								</a>
								<p class="rec-hash-area">
									{{ recommend.hashtag / 10 }}
									<!-- <span class="rec-hash cursor-p" v-for="(hash, hidx) in recommend.hashtag" :key="hidx">#{{ hash }}</span> -->
								</p>
							</div>
						</div>

						<!-- 댓글 창 -->
						<div class="modal-comment-bg" :key="commentload">
							<div class="comment-area1">
								<div class="modal-comment" v-for="(com, i) in comments" :key="i">
									<div class="m-profile">
										<a href="#"><img class="m-profile-img" :src="com.profileImage" /></a>
										<a href="#"
											><span class="m-nickname">{{ com.nickname }}</span></a
										>
										<span class="m-cDate">{{ com.cDate }}</span>
									</div>

									<div class="m-comment-area">
										<div class="m-comment">
											<p>{{ com.comment }}</p>
										</div>
										<!-- <img class="cursor-p" src="@/img/Feed/report.png" /> -->
									</div>
								</div>
							</div>

							<div class="comment-area2">
								<div id="comment-form">
									<textarea id="commentText" cols="37" rows="2"></textarea>
									<button class="send-comment" @click="sendComment">전송</button>
								</div>
							</div>
						</div>

						<!-- 수정하기, 삭제하기 버튼 => 본인 게시글일 경우 -->
						<div class="card-ctrl-btn-area">
							<button class="card-ctrl-btn update">수정</button>
							<button class="card-ctrl-btn delete">삭제</button>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!------------------------ modal End ------------------------->
	</div>
</template>

<script>
import axios from "axios";
export default {
	name: "feedDetail",
	props: ["cardList", "userData", "index"],
	data() {
		return {
			commentload: 0,
			likeload: 0,
			comments: [],
			recommend: {},
			formData: new FormData(),
			card: {},
		};
	},
	methods: {
		getComments() {
			const card = this.cardList[this.index];
			this.card = card;
			this.comments = [];
			this.recommend = {};
			console.log(card);
			for (const comment in card.feedComments) {
				const date = new Date(card.feedComments[comment].feed_cdate);

				const year = date.getFullYear().toString().slice(2);
				const month = (date.getMonth() + 1).toString().padStart(2, "0");
				const day = date.getDate().toString().padStart(2, "0");
				const hours = date.getHours().toString().padStart(2, "0");
				const minutes = date.getMinutes().toString().padStart(2, "0");

				const formattedDateString = `${year}-${month}-${day} ${hours}시${minutes}분`;

				const commentMap = {
					// profileImage: require("@/img/Feed/" + card.feedComments[comment].member.profile_img),
					profileImage: require("@/img/Feed/no_profile.png"),
					uid: card.feedComments[comment].user_id.user_id,
					nickname: card.feedComments[comment].user_id.nickname,
					cDate: formattedDateString,
					comment: card.feedComments[comment].feed_comment_content,
				};
				this.comments.push(commentMap);
			}
			this.recommend = {
				name: card.recommend.place_name,
				img: card.recommend.place_img,
				hashtag: card.recommend.place_score,
				url: card.recommend.place_link,
				address: card.recommend.place_address,
			};
		},
		async sendComment() {
			this.formData = new FormData();
			this.formData.append("feedNum", this.card.feedNum);
			this.formData.append("feedCommentContent", document.getElementById("commentText").value);
			this.formData.append("userId", this.userData.id);
			console.log(this.formData);
			axios.post(`http://${process.env.VUE_APP_BACK_END_URL}/addFeedComment`, this.formData).then((res) => {
				console.log("addFeedComment");
				this.$emit("getFeedList");
				console.log("갱신된 댓글", res);
				this.comments = [];
				for (const comment of res.data) {
					const date = new Date(comment.feed_cdate);

					const year = date.getFullYear().toString().slice(2);
					const month = (date.getMonth() + 1).toString().padStart(2, "0");
					const day = date.getDate().toString().padStart(2, "0");
					const hours = date.getHours().toString().padStart(2, "0");
					const minutes = date.getMinutes().toString().padStart(2, "0");

					const formattedDateString = `${year}-${month}-${day} ${hours}시${minutes}분`;

					const commentMap = {
						// profileImage: require("@/img/Feed/" + card.feedComments[comment].member.profile_img),
						profileImage: require("@/img/Feed/no_profile.png"),
						uid: comment.user_id.user_id,
						nickname: comment.user_id.nickname,
						cDate: formattedDateString,
						comment: comment.feed_comment_content,
					};
					this.comments.push(commentMap);
					this.card.comments = res.data.length;
				}
				console.log("댓글 추가");
				document.getElementById("commentText").value = "";
				this.commentload += 1;
			});
		},
		async sendLike() {
			this.formData = new FormData();
			this.formData.append("feedNum", this.card.feedNum);
			this.formData.append("userId", this.userData.id);
			await axios.post(`http://${process.env.VUE_APP_BACK_END_URL}/modifyFeedLike`, this.formData).then((res) => {
				console.log("modifyFeedLike");
				this.$emit("getFeedList");
				console.log("before", this.card.likes);
				console.log(res);
				this.card.likes = res.data;
				console.log("after", this.card.likes);
				console.log("좋아요 갱신");
				this.likeload += 1;
			});
		},
	},
	created() {
		this.getComments();
	},
};
</script>

<style scoped>
@import "@/css/client/feed/feedDetail.css";
</style>
