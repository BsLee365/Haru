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
          <div
            class="hobby-img-box"
            v-for="(activity, index) in activity"
            :key="index"
            @click="selectActivity(index)"
          >
            <img :src="activity.image" />
            <img
              v-if="selectedActivity.includes(index)"
              src="@/img/selectHobbyImg/Group_208.png"
              class="hobby-check-img"
            />
            <span>{{ activity.name }}</span>
          </div>
        </div>
      </div>
      <div class="hobby-container">
        <div class="hobby-header-box">
          <h4>문화 예술</h4>
        </div>
        <div class="hobby-body">
          <div
            class="hobby-img-box"
            v-for="(culture, index) in culture"
            :key="index"
            @click="selectCulture(index)"
          >
            <img
              v-if="selectedCulture.includes(index)"
              src="@/img/selectHobbyImg/Group_208.png"
              class="hobby-check-img"
            />
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
          <div
            class="hobby-img-box"
            v-for="(travel, index) in travel"
            :key="index"
            @click="selectTravel(index)"
          >
            <img
              v-if="selectedTravel.includes(index)"
              src="@/img/selectHobbyImg/Group_208.png"
              class="hobby-check-img"
            />
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
          <div
            class="hobby-img-box"
            v-for="(hobby, index) in hobby"
            :key="index"
            @click="selectHobby(index)"
          >
            <img
              v-if="selectedHobby.includes(index)"
              src="@/img/selectHobbyImg/Group_208.png"
              class="hobby-check-img"
            />
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
          <div
            class="hobby-img-box"
            v-for="(food, index) in food"
            :key="index"
            @click="selectFood(index)"
          >
            <img
              v-if="selectedFood.includes(index)"
              src="@/img/selectHobbyImg/Group_208.png"
              class="hobby-check-img"
            />
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
          <div
            class="hobby-img-box"
            v-for="(selfDevelopment, index) in selfDevelopment"
            :key="index"
            @click="selectSelfDevelopment(index)"
          >
            <img
              v-if="selectedSelfDevelopment.includes(index)"
              src="@/img/selectHobbyImg/Group_208.png"
              class="hobby-check-img"
            />
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
          <div
            class="hobby-img-box"
            v-for="(shopping, index) in shopping"
            :key="index"
            @click="selectShopping(index)"
          >
            <img
              v-if="selectedShopping.includes(index)"
              src="@/img/selectHobbyImg/Group_208.png"
              class="hobby-check-img"
            />
            <img :src="shopping.image" />
            <span>{{ shopping.name }}</span>
          </div>
        </div>
      </div>
      <div class="hobby-setting-container">
        <button
          id="hobby-setting-btn"
          @click="hobbySet"
          class="big-ctlbtn select-btn"
        >
          설정 완료
        </button>
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
      formData: new FormData,
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
          image: require("@/img/selectHobbyImg/액티비티/골프.png"),
        },
        {
          name: "농구",
          image: require("@/img/selectHobbyImg/액티비티/농구.png"),
        },
        {
          name: "등산",
          image: require("@/img/selectHobbyImg/액티비티/등산.png"),
        },
        {
          name: "볼링",
          image: require("@/img/selectHobbyImg/액티비티/볼링.png"),
        },
        {
          name: "산책",
          image: require("@/img/selectHobbyImg/액티비티/산책.png"),
        },
        {
          name: "수영",
          image: require("@/img/selectHobbyImg/액티비티/수영.png"),
        },
        {
          name: "요가",
          image: require("@/img/selectHobbyImg/액티비티/요가.png"),
        },
        {
          name: "자전거",
          image: require("@/img/selectHobbyImg/액티비티/자전거.png"),
        },
        {
          name: "클라이밍",
          image: require("@/img/selectHobbyImg/액티비티/클라이밍.png"),
        },
        {
          name: "헬스",
          image: require("@/img/selectHobbyImg/액티비티/헬스.png"),
        },
      ],
      culture: [
        {
          name: "공연",
          image: require("@/img/selectHobbyImg/문화_예술/공연.png"),
        },
        {
          name: "뮤지컬",
          image: require("@/img/selectHobbyImg/문화_예술/뮤지컬.png"),
        },
        {
          name: "영화",
          image: require("@/img/selectHobbyImg/문화_예술/영화.png"),
        },
        {
          name: "전시",
          image: require("@/img/selectHobbyImg/문화_예술/전시.png"),
        },
        {
          name: "콘서트",
          image: require("@/img/selectHobbyImg/문화_예술/콘서트.png"),
        },
      ],
      travel: [
        { name: "바다", image: require("@/img/selectHobbyImg/여행/바다.png") },
        {
          name: "캠핑 글램핑",
          image: require("@/img/selectHobbyImg/여행/캠핑_글램핑.png"),
        },
        {
          name: "테마파크",
          image: require("@/img/selectHobbyImg/여행/테마파크.png"),
        },
      ],
      hobby: [
        { name: "게임", image: require("@/img/selectHobbyImg/취미/게임.png") },
        { name: "공예", image: require("@/img/selectHobbyImg/취미/공예.png") },
        { name: "댄스", image: require("@/img/selectHobbyImg/취미/댄스.png") },
        {
          name: "방탈출",
          image: require("@/img/selectHobbyImg/취미/방탈출.png"),
        },
        {
          name: "보드게임",
          image: require("@/img/selectHobbyImg/취미/보드게임.png"),
        },
        { name: "사진", image: require("@/img/selectHobbyImg/취미/사진.png") },
      ],
      food: [
        { name: "맛집", image: require("@/img/selectHobbyImg/푸드/맛집.png") },
        { name: "카페", image: require("@/img/selectHobbyImg/푸드/카페.png") },
      ],
      selfDevelopment: [
        {
          name: "독서 스터디",
          image: require("@/img/selectHobbyImg/자기계발/독서_스터디.png"),
        },
        {
          name: "외국어",
          image: require("@/img/selectHobbyImg/자기계발/외국어.png"),
        },
      ],
      shopping: [
        {
          name: "쇼핑",
          image: require("@/img/selectHobbyImg/쇼핑/쇼핑.png"),
        },
      ],
    };
  },
  created() {
    this.bgImage();
  },
  methods: {
    bgImage() {
      var newImage = "type1";
      this.$emit("bgImage", newImage);
    },
    selectActivity(index) {
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
    hobbySet() {
      this.formData.append("id", this.data.id);

      const selectedList = [];

      for (const i of this.selectedActivity) {
        selectedList.push(this.activity[i].name);
      }
      for (const i of this.selectedCulture) {
        selectedList.push(this.culture[i].name);
      }
      for (const i of this.selectedHobby) {
        selectedList.push(this.hobby[i].name);
      }
      for (const i of this.selectedTravel) {
        selectedList.push(this.travel[i].name);
      }
      for (const i of this.selectedFood) {
        selectedList.push(this.food[i].name);
      }
      for (const i of this.selectedSelfDevelopment) {
        selectedList.push(this.selfDevelopment[i].name);
      }
      for (const i of this.selectedShopping) {
        selectedList.push(this.shopping[i].name);
      }

      console.log(selectedList);
      const token = localStorage.getItem("jwtToken");
      const data = ref([]);
      
      this.formData.append("hobbyList", selectedList);
      axios
        .post(
          `http://${process.env.VUE_APP_BACK_END_URL}/setHobby`,
          this.formData,
          data.value,
          {
            headers: {
              Authorization: `Bearer ${token}`,
              "Content-Type": "application/json",
            },
          }
        )
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
};
</script>
<style scoped>
@import url("@/css/client/member/selectHobby.css");
</style>
