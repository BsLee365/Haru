<template>
	<div class="container1">
		<div class="selectHobby-container">
			<div class="selectHobby-header-box">
				<h3>관심 카테고리를 선택해보세요!</h3>
			</div>
			<div class="hobby-container">
				<div class="hobby-header-box">
					<h4>엑티비티</h4>
				</div>
				<div class="hobby-body">
					<div class="hobby-img-box" v-for="(activity, index) in activity" :key="index" @click="selectActivity(index)">
						<img :src="activity.image" />
						<img v-if="selectedActivity.includes(index)" src="@/img/selectHobbyImg/Group_208.png" class="hobby-check-img" />
						<span>{{ activity.name }}</span>
					</div>
				</div>
			</div>
			<div class="hobby-container">
				<div class="hobby-header-box">
					<h4>문화 예술</h4>
				</div>
				<div class="hobby-body">
					<div class="hobby-img-box" v-for="(culture, index) in culture" :key="index" @click="selectCulture(index)">
						<img v-if="selectedCulture.includes(index)" src="@/img/selectHobbyImg/Group_208.png" class="hobby-check-img" />
						<img :src="culture.image" />
						<span>{{ culture.name }}</span>
					</div>
				</div>
			</div>
			<div class="hobby-container">
				<div class="hobby-header-box">
					<h4>여행</h4>
				</div>
				<div class="hobby-body">
					<div class="hobby-img-box" v-for="(travel, index) in travel" :key="index" @click="selectTravel(index)">
						<img v-if="selectedTravel.includes(index)" src="@/img/selectHobbyImg/Group_208.png" class="hobby-check-img" />
						<img :src="travel.image" />
						<span>{{ travel.name }}</span>
					</div>
				</div>
			</div>
			<div class="hobby-container">
				<div class="hobby-header-box">
					<h4>취미</h4>
				</div>
				<div class="hobby-body">
					<div class="hobby-img-box" v-for="(hobby, index) in hobby" :key="index" @click="selectHobby(index)">
						<img v-if="selectedHobby.includes(index)" src="@/img/selectHobbyImg/Group_208.png" class="hobby-check-img" />
						<img :src="hobby.image" />
						<span>{{ hobby.name }}</span>
					</div>
				</div>
			</div>
			<div class="hobby-container">
				<div class="hobby-header-box">
					<h4>푸드</h4>
				</div>
				<div class="hobby-body">
					<div class="hobby-img-box" v-for="(food, index) in food" :key="index" @click="selectFood(index)">
						<img v-if="selectedFood.includes(index)" src="@/img/selectHobbyImg/Group_208.png" class="hobby-check-img" />
						<img :src="food.image" />
						<span>{{ food.name }}</span>
					</div>
				</div>
			</div>
			<div class="hobby-container">
				<div class="hobby-header-box">
					<h4>자기계발</h4>
				</div>
				<div class="hobby-body">
					<div class="hobby-img-box" v-for="(selfDevelopment, index) in selfDevelopment" :key="index" @click="selectSelfDevelopment(index)">
						<img v-if="selectedSelfDevelopment.includes(index)" src="@/img/selectHobbyImg/Group_208.png" class="hobby-check-img" />
						<img :src="selfDevelopment.image" />
						<span>{{ selfDevelopment.name }}</span>
					</div>
				</div>
			</div>
			<div class="hobby-container">
				<div class="hobby-header-box">
					<h4>쇼핑</h4>
				</div>
				<div class="hobby-body">
					<div class="hobby-img-box" v-for="(shopping, index) in shopping" :key="index" @click="selectShopping(index)">
						<img v-if="selectedShopping.includes(index)" src="@/img/selectHobbyImg/Group_208.png" class="hobby-check-img" />
						<img :src="shopping.image" />
						<span>{{ shopping.name }}</span>
					</div>
				</div>
			</div>
			<div class="hobby-setting-container">
				<button id="hobby-setting-btn" @click="hobbySet" class="big-ctlbtn select-btn">설정 완료</button>
			</div>
		</div>
	</div>
</template>
<script>
import axios from "axios";
import { ref, onMounted } from "vue";
import { jwtDecode } from "jwt-decode";
export default {
	name: "SelectHobby",
	data() {
		return {
			formData: new FormData(),
			selectedActivity: [],
			selectedCulture: [],
			selectedHobby: [],
			selectedTravel: [],
			selectedFood: [],
			selectedSelfDevelopment: [],
			selectedShopping: [],
			index: null,
			activity: [
				{
					name: "골프",
					category: "골프",
					image: require("@/img/selectHobbyImg/액티비티/골프.png"),
				},
				{
					name: "농구",
					category: "농구장",
					image: require("@/img/selectHobbyImg/액티비티/농구.png"),
				},
				{
					name: "등산",
					category: "등산로",
					image: require("@/img/selectHobbyImg/액티비티/등산.png"),
				},
				{
					name: "배드민턴",
					category: "배드민턴장",
					image: require("@/img/selectHobbyImg/액티비티/농구.png"),
				},
				{
					name: "볼링",
					category: "볼링장",
					image: require("@/img/selectHobbyImg/액티비티/볼링.png"),
				},
				{
					name: "산책",
					category: "공원",
					image: require("@/img/selectHobbyImg/액티비티/산책.png"),
				},
				{
					name: "수영",
					category: "수영장",
					image: require("@/img/selectHobbyImg/액티비티/수영.png"),
				},
				{
					name: "스키",
					category: "스키장",
					image: require("@/img/selectHobbyImg/액티비티/스키.png"),
				},
				{
					name: "야구",
					category: "야구장",
					image: require("@/img/selectHobbyImg/액티비티/야구.png"),
				},
				{
					name: "요가",
					category: "요가",
					image: require("@/img/selectHobbyImg/액티비티/요가.png"),
				},
				{
					name: "자전거",
					category: "자전거",
					image: require("@/img/selectHobbyImg/액티비티/자전거.png"),
				},
				{
					name: "클라이밍",
					category: "클라이밍",
					image: require("@/img/selectHobbyImg/액티비티/클라이밍.png"),
				},
				{
					name: "테니스",
					category: "테니스",
					image: require("@/img/selectHobbyImg/액티비티/클라이밍.png"),
				},
				{
					name: "헬스",
					category: "헬스",
					image: require("@/img/selectHobbyImg/액티비티/헬스.png"),
				},
			],
			culture: [
				{
					name: "공연",
					category: "공연장",
					image: require("@/img/selectHobbyImg/문화_예술/공연.png"),
				},
				{
					name: "뮤지컬",
					category: "공연장",
					image: require("@/img/selectHobbyImg/문화_예술/뮤지컬.png"),
				},
				{
					name: "영화",
					category: "영화관",
					image: require("@/img/selectHobbyImg/문화_예술/영화.png"),
				},
				{
					name: "전시",
					category: "전시",
					image: require("@/img/selectHobbyImg/문화_예술/전시.png"),
				},
				{
					name: "콘서트",
					category: "공연장",
					image: require("@/img/selectHobbyImg/문화_예술/콘서트.png"),
				},
			],
			travel: [
				{
					name: "바다",
					category: "해수욕장",
					image: require("@/img/selectHobbyImg/여행/바다.png"),
				},
				{
					name: "캠핑 글램핑",
					category: "캠핑장",
					image: require("@/img/selectHobbyImg/여행/캠핑_글램핑.png"),
				},
				{
					name: "테마파크",
					category: "테마파크",
					image: require("@/img/selectHobbyImg/여행/테마파크.png"),
				},
			],
			hobby: [
				{
					name: "게임",
					category: "pc방",
					image: require("@/img/selectHobbyImg/취미/게임.png"),
				},
				{
					name: "노래",
					category: "노래방",
					image: require("@/img/selectHobbyImg/취미/노래방.png"),
				},
				{
					name: "공예",
					category: "공예",
					image: require("@/img/selectHobbyImg/취미/공예.png"),
				},
				{
					name: "댄스",
					category: "댄스",
					image: require("@/img/selectHobbyImg/취미/댄스.png"),
				},
				{
					name: "방탈출",
					category: "방탈출",
					image: require("@/img/selectHobbyImg/취미/방탈출.png"),
				},
				{
					name: "보드게임",
					category: "보드게임",
					image: require("@/img/selectHobbyImg/취미/보드게임.png"),
				},
				{
					name: "사진",
					category: "사진",
					image: require("@/img/selectHobbyImg/취미/사진.png"),
				},
			],
			food: [
				{
					name: "맛집",
					category: "식당",
					image: require("@/img/selectHobbyImg/푸드/맛집.png"),
				},
				{
					name: "카페",
					category: "식당",
					image: require("@/img/selectHobbyImg/푸드/카페.png"),
				},
			],
			selfDevelopment: [
				{
					name: "독서",
					category: "도서관",
					image: require("@/img/selectHobbyImg/자기계발/독서.png"),
				},
				{
					name: "스터디",
					category: "스터디",
					image: require("@/img/selectHobbyImg/자기계발/독서_스터디.png"),
				},
				{
					name: "외국어",
					category: "외국어학원",
					image: require("@/img/selectHobbyImg/자기계발/외국어.png"),
				},
			],
			shopping: [
				{
					name: "쇼핑",
					category: "쇼핑",
					image: require("@/img/selectHobbyImg/쇼핑/쇼핑.png"),
				},
			],
		};
	},
	created() {
		this.bgImage();
	},
	mounted() {
		this.getMyHobby();
	},
	methods: {
		bgImage() {
			var newImage = "type1";
			this.$emit("bgImage", newImage);
		},
		selectActivity(index) {
			// this.printSelect();
			const selectedIndex = this.selectedActivity.indexOf(index);
			if (selectedIndex > -1) {
				this.selectedActivity.splice(selectedIndex, 1);
			} else {
				this.selectedActivity.push(index);
			}
			console.log(this.selectedActivity);
		},
		selectCulture(index) {
			const selectedIndex = this.selectedCulture.indexOf(index);
			if (selectedIndex > -1) {
				this.selectedCulture.splice(selectedIndex, 1);
			} else {
				this.selectedCulture.push(index);
			}
		},
		selectHobby(index) {
			const selectedIndex = this.selectedHobby.indexOf(index);
			if (selectedIndex > -1) {
				this.selectedHobby.splice(selectedIndex, 1);
			} else {
				this.selectedHobby.push(index);
			}
		},
		selectTravel(index) {
			const selectedIndex = this.selectedTravel.indexOf(index);
			if (selectedIndex > -1) {
				this.selectedTravel.splice(selectedIndex, 1);
			} else {
				this.selectedTravel.push(index);
			}
		},
		selectFood(index) {
			const selectedIndex = this.selectedFood.indexOf(index);
			if (selectedIndex > -1) {
				this.selectedFood.splice(selectedIndex, 1);
			} else {
				this.selectedFood.push(index);
			}
		},
		selectSelfDevelopment(index) {
			const selectedIndex = this.selectedSelfDevelopment.indexOf(index);
			if (selectedIndex > -1) {
				this.selectedSelfDevelopment.splice(selectedIndex, 1);
			} else {
				this.selectedSelfDevelopment.push(index);
			}
		},
		selectShopping(index) {
			const selectedIndex = this.selectedShopping.indexOf(index);
			if (selectedIndex > -1) {
				this.selectedShopping.splice(selectedIndex, 1);
			} else {
				this.selectedShopping.push(index);
			}
		},
		getMyHobby() {
			var formData = new FormData();
			formData.append("userId", this.data.id);
			console.log(this.data.id);
			axios.post(`http://${process.env.VUE_APP_BACK_END_URL}/getMyHobby`, formData).then((res) => {
				console.log(res);
				for (const i of res.data) {
					if (i.main_category.main_category == "골프") {
						this.selectedActivity.push(0);
					} else if (i.main_category.main_category == "농구장") {
						this.selectedActivity.push(1);
					} else if (i.main_category.main_category == "등산로") {
						this.selectedActivity.push(2);
					} else if (i.main_category.main_category == "배드민턴장") {
						this.selectedActivity.push(3);
					} else if (i.main_category.main_category == "볼링장") {
						this.selectedActivity.push(4);
					} else if (i.main_category.main_category == "공원") {
						this.selectedActivity.push(5);
					} else if (i.main_category.main_category == "수영장") {
						this.selectedActivity.push(6);
					} else if (i.main_category.main_category == "스키장") {
						this.selectedActivity.push(7);
					} else if (i.main_category.main_category == "야구장") {
						this.selectedActivity.push(8);
					} else if (i.main_category.main_category == "요가") {
						this.selectedActivity.push(9);
					} else if (i.main_category.main_category == "자전거") {
						this.selectedActivity.push(10);
					} else if (i.main_category.main_category == "클라이밍") {
						this.selectedActivity.push(11);
					} else if (i.main_category.main_category == "테니스") {
						this.selectedActivity.push(12);
					} else if (i.main_category.main_category == "헬스") {
						this.selectedActivity.push(13);
					} else if (i.main_category.main_category == "공연장") {
						this.selectedCulture.push(0);
						this.selectedCulture.push(1);
						this.selectedCulture.push(4);
					} else if (i.main_category.main_category == "영화관") {
						this.selectedCulture.push(2);
					} else if (i.main_category.main_category == "전시") {
						this.selectedCulture.push(3);
					} else if (i.main_category.main_category == "해수욕장") {
						this.selectedTravel.push(0);
					} else if (i.main_category.main_category == "캠핑장") {
						this.selectedTravel.push(1);
					} else if (i.main_category.main_category == "테마파크") {
						this.selectedTravel.push(2);
					} else if (i.main_category.main_category == "pc방") {
						this.selectedHobby.push(0);
					} else if (i.main_category.main_category == "노래방") {
						this.selectedHobby.push(1);
					} else if (i.main_category.main_category == "공예") {
						this.selectedHobby.push(2);
					} else if (i.main_category.main_category == "댄스") {
						this.selectedHobby.push(3);
					} else if (i.main_category.main_category == "방탈출") {
						this.selectedHobby.push(4);
					} else if (i.main_category.main_category == "보드게임") {
						this.selectedHobby.push(5);
					} else if (i.main_category.main_category == "사진") {
						this.selectedHobby.push(6);
					} else if (i.main_category.main_category == "식당") {
						this.selectedFood.push(0);
						this.selectedFood.push(1);
					} else if (i.main_category.main_category == "도서관") {
						this.selectedSelfDevelopment.push(0);
					} else if (i.main_category.main_category == "스터디") {
						this.selectedSelfDevelopment.push(1);
					} else if (i.main_category.main_category == "외국어학원") {
						this.selectedSelfDevelopment.push(2);
					} else if (i.main_category.main_category == "쇼핑") {
						this.selectedShopping.push(0);
					}
				}
			});
		},
		hobbySet() {
			this.formData.append("id", this.data.id);

			var selectedList = [];

			for (const i of this.selectedActivity) {
				selectedList.push(this.activity[i].category);
			}
			for (const i of this.selectedCulture) {
				selectedList.push(this.culture[i].category);
			}
			for (const i of this.selectedHobby) {
				selectedList.push(this.hobby[i].category);
			}
			for (const i of this.selectedTravel) {
				selectedList.push(this.travel[i].category);
			}
			for (const i of this.selectedFood) {
				selectedList.push(this.food[i].category);
			}
			for (const i of this.selectedSelfDevelopment) {
				selectedList.push(this.selfDevelopment[i].category);
			}
			for (const i of this.selectedShopping) {
				selectedList.push(this.shopping[i].category);
			}

			selectedList = Array.from(new Set(selectedList));

			console.log(selectedList);
			const token = localStorage.getItem("jwtToken");
			const data = ref([]);

			this.formData.append("hobbyList", selectedList);
			axios
				.post(`http://${process.env.VUE_APP_BACK_END_URL}/setHobby`, this.formData, data.value, {
					headers: {
						Authorization: `Bearer ${token}`,
						"Content-Type": "application/json",
					},
				})
				.then((res) => {
					console.log(res);
					window.location.href = "/MyPage";
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
};
</script>
<style scoped>
@import url("@/css/client/member/selectHobby.css");
</style>
