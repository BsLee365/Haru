<template>
	<!-- 피드 리스트 화면 - 카드 하나  -->
	<div>
		<div class="feed-card" v-for="(i, idx) in cardList" :key="idx">
			<div class="feed-card-header">
				<!-- 프로필 사진, 닉네임 -->
				<div class="feed-profile-area">
					<div class="feed-profile-img">
						<a href="#"><img :src="i.profileImage" /></a>
					</div>
					<a href="#"
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
						<span v-if="i.content.length > 100" class="more cursor-p" id="more" @click="$emit('open-modal')"> ... 더보기 </span>
					</p>
				</div>
				<!-- 좋아요, 댓글 버튼 -->
				<div class="card-btn">
					<div>
						<div class="heart">
							<img class="cursor-p" src="@/img/Feed/heart.png" id="heart" />
							<span>{{ i.likes }}</span>
						</div>
						<div class="comment">
							<img class="cursor-p" src="@/img/Feed/comment.png" id="comment" @click="$emit('open-modal', i, idx)" />
							<span @click="$emit('open-modal')" class="cursor-p">{{ i.comments }}</span>
						</div>
					</div>
					<p class="rDate">{{ i.rDate }}</p>
				</div>
			</div>
		</div>
	</div>
</template>

<script>
export default {
	name: "FeedList",
	data() {
		return {
			modal_Check: false,
		};
	},
	props: {
		cardList: Object, // 부모로부터 받은 데이터
	},
};
</script>
