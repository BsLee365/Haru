<template>
	<div class="container1">
		<FeedDetail v-if="modal_Check" @close-modal="closeModal" @openModal="openModal" :cardList="cardList" :userData="userData" @getFeedList="getFeedList" :index="index" />
		<div class="feed-card-area" id="card-area">
			<!-- 해시태그 검색 -->
			<form action="" id="search-form">
				<div class="search-area">
					<input class="hashtag-search-input" type="text" placeholder="# 해시태그로 피드를 검색하세요" />
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
import axios from "axios";
import { ref, onMounted } from "vue";
import { jwtDecode } from "jwt-decode";
export default {
	name: "feedMain",
	data() {
		return {
			cardList: [
				// {
				// 	profileImage: require("@/img/Feed/no_profile.png"),
				// 	uid: "abc",
				// 	profileLink: "#",
				// 	nickname: "닉네임",
				// 	mainCategory: "액티비티",
				// 	images: [require("@/img/Feed/feed1.png"), require("@/img/Feed/feed2.jpg"), require("@/img/Feed/feed3.jpg")],
				// 	content: "오늘 정말 좋았던거 같은데... 뭐 별거 없었다d... 하지만 야경은 진짜 멋있었다",
				// 	likes: 22,
				// 	rDate: "2023-12-31",
				// 	comments: 5,
				// },
				// {
				// 	profileImage: require("@/img/Feed/no_profile.png"),
				// 	uid: "abcd",
				// 	profileLink: "#",
				// 	nickname: "닉네임",
				// 	mainCategory: "액티비티",
				// 	images: [require("@/img/Feed/feed3.jpg")],
				// 	content: "오늘 정말 좋았던거 같은데... 뭐 별거 없었다... 하지만 야경은 진짜 멋있었다 오늘 정말 좋았던거 같은데... 뭐 별거 없었다... 하지만 야경은 진짜 멋있었다 꼭 또가고 싶어졌다",
				// 	likes: 22,
				// 	rDate: "2023-12-30",
				// 	comments: 5,
				// },
				// {
				// 	profileImage: require("@/img/Feed/no_profile.png"),
				// 	uid: "abcd",
				// 	profileLink: "#",
				// 	nickname: "닉네임",
				// 	mainCategory: "액티비티",
				// 	images: [require("@/img/Feed/feed2.jpg"), require("@/img/Feed/feed3.jpg")],
				// 	content: "오늘 정말 좋았던거 같은데... 뭐 별거 없었다... 하지만 야경은 진짜 멋있었다",
				// 	likes: 22,
				// 	rDate: "2023-12-29",
				// 	comments: 5,
				// },
				// {
				// 	profileImage: require("@/img/Feed/no_profile.png"),
				// 	uid: "abc",
				// 	profileLink: "#",
				// 	nickname: "닉네임",
				// 	mainCategory: "액티비티",
				// 	images: [require("@/img/Feed/feed1.png"), require("@/img/Feed/feed3.jpg"), require("@/img/Feed/feed2.jpg")],
				// 	content: "오늘 정말 좋았던거 같은데... 뭐 별거 없었다... 하지만 야경은 진짜 멋있었다",
				// 	likes: 22,
				// 	rDate: "2023-12-28",
				// 	comments: 5,
				// },
				// {
				// 	profileImage: require("@/img/Feed/no_profile.png"),
				// 	uid: "abc",
				// 	profileLink: "#",
				// 	nickname: "닉네임",
				// 	mainCategory: "액티비티",
				// 	images: [require("@/img/Feed/feed1.png"), require("@/img/Feed/feed3.jpg"), require("@/img/Feed/feed2.jpg")],
				// 	content: "오늘 정말 좋았던거 같은데... 뭐 별거 없었다... 하지만 야경은 진짜 멋있었다",
				// 	likes: 22,
				// 	rDate: "2023-12-27",
				// 	comments: 5,
				// },
			],
			card: null,
			modal_Check: false,
			userData: null,
			index: 0,
		};
	},
	created() {
		// 페이지가 로드될 때 초기 이미지 설정
		this.bgImage();
		// 페이지 로드하면서 리스트 불러오기
		this.getFeedList();
	},
	methods: {
		// 해당 화면 Background 이미지 설정
		bgImage() {
			var newImage = "type4";
			this.$emit("bgImage", newImage);
		},
		async getFeedList() {
			this.cardList = [];
			await axios.get(`http://${process.env.VUE_APP_BACK_END_URL}/feedList`).then((res) => {
				const data = res.data;
				var index = 0;
				for (const feed in data.feed) {
					const feedNum = data.feed[feed].feed_num;
					const images = function (data) {
						const images = [];
						for (const img of data.feedImg) {
							if (img.feed_num.feed_num === feedNum) {
								images.push(require("@/img/Feed/" + img.feed_img));
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
					const cardList = {
						profileImage: require("@/img/Feed/no_profile.png"), //data.feed[index].member.profile_img,
						uid: data.feed[index].member.user_id,
						profileLink: data.feed[index].member.profile_link,
						nickname: data.feed[index].member.nickname,
						mainCategory: data.feed[index].place_num.sub_category.main_category.main_category,
						images: images(data),
						content: data.feed[index].feed_content,
						likes: data.feedLike[index],
						rDate: data.feed[index].feed_cdate.substring(0, 16),
						comments: comments.length,
						feedComments: comments,
						recommend: data.feed[index].place_num,
						feedNum: data.feed[index].feed_num,
					};
					this.cardList.push(cardList);
					index++;
				}
				console.log(data);
			});
		},
		openModal(card, idx) {
			this.modal_Check = !this.modal_Check;
			this.userData = this.data;
			this.index = idx;
		},
		closeModal() {
			this.modal_Check = false;
		},
	},
	components: {
		FeedDetail,
		FeedList,
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
};
</script>
<style scoped>
@import "@/css/client/feed/feed.css";
</style>
