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
							<br />
							<p id="hashTag">{{ card.hashTag.join(" ") }}</p>
						</div>

						<!-- 좋아요,  댓글 버튼 -->
						<div class="card-btn">
							<div>
								<div class="heart">
									<img class="cursor-p" src="@/img/Feed/heart.png" id="heart" @click="sendLike" :style="heartStyle" />
									<span>{{ card.likes }}</span>
								</div>
								<div class="comment">
									<img src="@/img/Feed/comment.png" id="comment" />
									<span>{{ card.comments }}</span>
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
                <a :href="'https://map.naver.com/p/smart-around/place/' + getPlaceLink(recommend.url)" target="_blank">
                  <img class="rec-detail-img" :src="recommend.img" alt="" />
                </a>
							</div>
							<div class="rec-detail">
                <a :href="'https://map.naver.com/p/smart-around/place/' + getPlaceLink(recommend.url)" target="_blank">
									<p class="rec-detail-title">{{ recommend.name }}</p>
									<p>{{ recommend.address }}</p>
								</a>
								<p class="rec-hash-area">
                  <span class="rlist-score">★ {{ (recommend.hashtag / 10).toFixed(1) }}</span>
								</p>
							</div>
						</div>

						<!-- 댓글 창 -->
						<div class="modal-comment-bg">
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
									<textarea id="commentText" cols="35" rows="2"></textarea>
									<button class="send-comment" @click="sendComment">전송</button>
								</div>
							</div>
						</div>

						<!-- 수정하기, 삭제하기 버튼 => 본인 게시글일 경우 -->
						<div class="card-ctrl-btn-area" v-if="this.userData.id == card.uid">
							<button class="card-ctrl-btn update" @click="feedUpdate">수정</button>
							<button class="card-ctrl-btn delete" @click="feedDelete">삭제</button>
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
	props: ["cardOrigin", "userData", "index"],
	data() {
		return {
			heartStyle: {},
			comments: [],
			recommend: {},
			formData: new FormData(),
			card: {},
			cardLike: false,
		};
	},
	methods: {
		getComments() {
			const card = this.card;
			console.log(card);
			this.comments = [];
			this.recommend = {};
			card.feedComments.sort((a, b) => new Date(b.feed_cdate) - new Date(a.feed_cdate));
			for (const comment in card.feedComments) {
				const date = this.getTimeString(card.feedComments[comment].feed_cdate);

				const commentMap = {
					// profileImage: require("@/img/Feed/" + card.feedComments[comment].member.profile_img),
					profileImage: require("@/img/Feed/no_profile.png"),
					uid: card.feedComments[comment].user_id.user_id,
					nickname: card.feedComments[comment].user_id.nickname,
					cDate: date,
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
			this.formData.append("feedUserId", this.card.uid);
			console.log(this.formData);
			axios.post(`http://${process.env.VUE_APP_BACK_END_URL}/addFeedComment`, this.formData).then(() => {
				console.log("addFeedComment");
				axios.post(`http://${process.env.VUE_APP_BACK_END_URL}/reLoadFeedComment`, this.formData).then((res) => {
					this.$emit("getFeedList");
					console.log("갱신된 댓글", res);
					this.comments = [];

					res.data.sort((a, b) => new Date(b.feed_cdate) - new Date(a.feed_cdate));

					console.log(res.data[res.data.length - 1]);

					for (const comment of res.data) {
						const date = this.getTimeString(comment.feed_cdate);

						const commentMap = {
							// profileImage: require("@/img/Feed/" + card.feedComments[comment].member.profile_img),
							profileImage: require("@/img/Feed/no_profile.png"),
							uid: comment.user_id.user_id,
							nickname: comment.user_id.nickname,
							cDate: date,
							comment: comment.feed_comment_content,
						};
						this.comments.push(commentMap);
						this.card.comments = res.data.length;
					}
					console.log("댓글 추가");
					document.getElementById("commentText").value = "";
				});
			});
		},
		async sendLike() {
			this.formData = new FormData();
			this.formData.append("feedNum", this.card.feedNum);
			this.formData.append("userId", this.userData.id);
			this.formData.append("feedUserId", this.card.uid);
			await axios.post(`http://${process.env.VUE_APP_BACK_END_URL}/modifyFeedLike`, this.formData).then((res) => {
				this.$emit("getFeedList");
				console.log(res);
				this.card.likes = res.data;
				this.cardLike = !this.cardLike;
				if (this.cardLike === true) {
					this.heartStyle = {
						filter: "opacity(0.4) drop-shadow(0 0 0 red)",
					};
				} else {
					this.heartStyle = {};
				}
				console.log(this.cardOrigin.myFeedLike);
				console.log(this.heartStyle);
			});
		},
		feedUpdate() {
			var formData = new FormData();
			formData.append("feedNum", this.card.feedNum);
			axios.post(`http://${process.env.VUE_APP_BACK_END_URL}/feedUpdateForm`, formData).then((res) => {
				const feedData = res.data;
				this.$router.push({
					name: "UpdateFeed",
					state: {
						feedData: feedData,
					},
				});
			});
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
		feedDelete() {
			if (confirm("정말 삭제하시겠습니까?") === true) {
				var formData = new FormData();
				formData.append("feedNum", this.card.feedNum);
				axios.post(`http://${process.env.VUE_APP_BACK_END_URL}/feedDelete`, formData).then(() => {
					alert("삭제되었습니다.");
					this.$emit("getFeedList");
					this.$emit("close-modal");
				});
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
				const options = { year: "numeric", month: "2-digit", day: "2-digit", hour: "2-digit", minute: "2-digit" };
				return targetTime.toLocaleString("ko-KR", options);
			}
		},
	},
	created() {
		this.card = this.cardOrigin;
		this.getComments();
		if (this.card.myFeedLike === false) {
			this.heartStyle = {};
			this.cardLike = false;
		} else {
			this.heartStyle = {
				filter: "opacity(0.4) drop-shadow(0 0 0 red)",
			};
			this.cardLike = true;
		}
	},
};
</script>

<style scoped>
@import "@/css/client/feed/feedDetail.css";
#hashTag {
	color: #928e8e;
}
</style>
