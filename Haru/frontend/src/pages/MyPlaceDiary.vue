<template>
  <!-- 내 장소 - [추천 리스트/ 일기] 화면 -->
  <div class="container1">
    <!-- <div class="page-title-area">
      <h1 class="page-upload-title">내 기록</h1>
    </div> -->

    <div class="myplace-content-area">
      <!-- 달력 -->
      <div class="myplace-calendar-area">
        <div class="myplace-calendar-container">
          <div class="myplace-calendar">
            <header>
              <h2>September</h2>

              <a class="btn-prev fontawesome-angle-left" href="#"></a>
              <a class="btn-next fontawesome-angle-right" href="#"></a>
            </header>

            <table class="myplace-calendar-table">
              <thead>
                <tr>
                  <td>Mo</td>
                  <td>Tu</td>
                  <td>We</td>
                  <td>Th</td>
                  <td>Fr</td>
                  <td>Sa</td>
                  <td>Su</td>
                </tr>
              </thead>

              <tbody>
                <tr>
                  <td class="prev-month">26</td>
                  <td class="prev-month">27</td>
                  <td class="prev-month">28</td>
                  <td class="prev-month">29</td>
                  <td class="prev-month">30</td>
                  <td class="prev-month">31</td>
                  <td>1</td>
                </tr>
                <tr>
                  <td>2</td>
                  <td>3</td>
                  <td>4</td>
                  <td>5</td>
                  <td>6</td>
                  <td>7</td>
                  <td>8</td>
                </tr>
                <tr>
                  <td>9</td>
                  <td class="calendar-event">10</td>
                  <td>11</td>
                  <td>12</td>
                  <td>13</td>
                  <td>14</td>
                  <td>15</td>
                </tr>
                <tr>
                  <td>16</td>
                  <td>17</td>
                  <td>18</td>
                  <td>19</td>
                  <td>20</td>
                  <td class="calendar-event">21</td>
                  <td>22</td>
                </tr>

                <tr>
                  <td class="calendar-current-day calendar-event">23</td>
                  <td>24</td>
                  <td>25</td>
                  <td>26</td>
                  <td>27</td>
                  <td>28</td>
                  <td>29</td>
                </tr>
                <tr>
                  <td>30</td>
                  <td class="next-month">1</td>
                  <td class="next-month">2</td>
                  <td class="next-month">3</td>
                  <td class="next-month">4</td>
                  <td class="next-month">5</td>
                  <td class="next-month">6</td>
                </tr>
              </tbody>
            </table>
          </div>
          <!-- end calendar -->
        </div>
        <!-- end container -->
      </div>
      <div class="rightarea">
        <!-- 탭 버튼 Area -->
        <div class="myplace-tab-btn-area">
          <div
            class="tab-btn-left cursor-p"
            :class="{ 'tab-btn-active': activeTab === 'recommend' }"
            @click="changeTab('recommend')"
          >
            추천 리스트
          </div>
          <div
            class="tab-btn-right cursor-p"
            :class="{ 'tab-btn-active': activeTab === 'diary' }"
            @click="changeTab('diary')"
          >
            일기
          </div>
        </div>

        <!-- 컴포넌트로 토글되는 영역 (추천리스트, 일기 리스트) -->
        <div class="tab-content-area">
          <FeedList :RecommendList="RecommendList" />
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import FeedList from "../components/RecommendList.vue";

export default {
  data() {
    return {
      isLoggedIn: false,
      AccessToken: "",
      activeTab: "recommend", // 기본값으로 추천 리스트를 활성화
      RecommendList: [
        {
          storeName: "신논현역 딸부자네 불백",
          stAddress: "서울시 강남구 꼬마빌딩 1층",
          hashtag: ["푸드", "맛집", "고기", "돼지고기", "갈매기살"],
          heartOnOff: "on",
        },
        {
          storeName: "신논현역 버거킹",
          stAddress: "서울시 강남구 꼬마빌딩 2층",
          hashtag: ["패스트푸드", "맛집", "와퍼"],
          heartOnOff: "off",
        },
        {
          storeName: "신논현역 와플대학",
          stAddress: "서울시 강남구 꼬마빌딩 3층",
          hashtag: ["카페", "맛집", "와플", "애플시나몬", "레몬에이드"],
          heartOnOff: "on",
        },
        {
          storeName: "신논현역 딸부자네 불백",
          stAddress: "서울시 강남구 꼬마빌딩 1층",
          hashtag: ["푸드", "맛집", "고기", "돼지고기", "갈매기살"],
          heartOnOff: "on",
        },
        {
          storeName: "신논현역 버거킹",
          stAddress: "서울시 강남구 꼬마빌딩 2층",
          hashtag: ["패스트푸드", "맛집", "와퍼"],
          heartOnOff: "off",
        },
      ],
    };
  },
  created() {
    this.getToken();
  },
  methods: {
    getToken() {
      this.AccessToken = localStorage.getItem("jwtToken");
      if (this.AccessToken != null) {
        this.isLoggedIn = true;
      } else {
        this.isLoggedIn = false;
        this.$router.push("/login");
      }
    },
    changeTab(tab) {
      this.activeTab = tab;
    },
  },
  components: {
    FeedList,
  },
};
</script>

<style scoped>
@import "../css/MyPlaceDiary.css";
@import "../css/calendar.css";
</style>
