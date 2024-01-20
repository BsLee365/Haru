<template>
  <!-- 내 장소 - [추천 리스트/ 일기] 화면 -->
  <div class="container1">
    <div class="myplace-content-area">
      <!-- 달력 -->
      <div class="myplace-calendar-area">
        <div class="myplace-calendar-container">
          <div class="myplace-calendar">
            <header>
              <button class="cal-btn-prev" @click="changeMonth(-1)"></button>
              <span>{{ calendarHeader }}</span>
              <button class="cal-btn-next" @click="changeMonth(1)"></button>
            </header>

            <table class="myplace-calendar-table">
              <thead>
                <tr>
                  <th v-for="index in week" :key="index">
                    {{ index }}
                  </th>
                </tr>
              </thead>

              <tbody>
                <tr v-for="(index, i) in days" :key="i">
                  <td
                    v-for="childIndex in index"
                    :key="childIndex"
                    :class="{
                      'cal-set-today': isToday(childIndex),
                      'selected-cal-day': isSelected(childIndex),
                      'calendar-event': isInRecList(childIndex),
                      'prev-next-monthDay': isPrevNextDay(i, childIndex),
                    }"
                  >
                    {{ childIndex }}
                  </td>
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
            :class="{ 'tab-btn-active': isTabRecList === true }"
          >
            추천 리스트
          </div>
          <div
            class="tab-btn-right cursor-p"
            :class="{ 'tab-btn-active': isTabRecList === false }"
            @click="changeTab('diary')"
          >
            일기
          </div>
        </div>

        <!-- 컴포넌트로 토글되는 영역 (추천리스트, 일기 리스트)myNum -->
        <div class="tab-content-area" @mouseover="recBtnOff">
          <RecommendList
            :RecommendList="RecommendList"
            :isBtnHeartNone="isBtnHeartNone"
            class="rlist-container"
            v-if="isTabRecList === true"
            ref="recList"
          />
          <MyDiaryList
            :diaryList="diaryList"
            class="rlist-container"
            v-if="isTabRecList === false"
          />
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import RecommendList from "@/components/client/myPlace/RecommendList.vue";
import MyDiaryList from "@/components/client/myPlace/MyDiaryList.vue";
import moment from "moment";

export default {
  data() {
    return {

      // 달력 외 관련

      activeTab: "recommend", // 기본값으로 추천 리스트를 활성화
      // rDate: "2024-01-05",
      isBtnHeartNone: false, // 하트버튼이 안보여야되는지
      isTabRecList: true, // 추천 리스트 탭 활성화
      RecommendList: [
        // 추천 받았던 리스트 (날짜별)
        {
          rdate: "2024-01-15",
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
          rdate: "2024-01-10",
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
          rdate: "2024-01-07",
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
      // 일기 리스트
      diaryList: {
        rdate: "2024년 01월 10일(수)",
        dList: [
          {
            diaryNum: 0,
            diaryName: "오늘 와룡산 갔다오고 나서(1)",
            dContents:
              "오늘은 크리스마스, 하얀 눈이 내리는 마법 같은 날이었다. 아침에 눈을 뜨자 창밖으로 내리쏟는 눈을 보며 설렘을 감추지 못했다. 따뜻한 코코아 한 잔을 손에 쥐고, 창가에 앉아 눈 내리는 거리를 바라봤다.오늘은 크리스마스, 하얀 눈이 내리는 마법 같은 날이었다. 아침에 눈을 뜨자 창밖으로 내리쏟는 눈을 보며 설렘을 감추지 못했다.",
          },
          {
            diaryNum: 1,
            diaryName: "오늘 와룡산 갔다오고 나서(2)",
            dContents:
              "따뜻한 코코아 한 잔을 손에 쥐고, 창가에 앉아 눈 내리는 거리를 바라봤다. 점심 식사, 우리가 나눈 웃음과 이야기들이 마음을 더욱 풍성하게 만들어주었다. 선물 교환은 언제나 즐거움의 정점을 찍는다. 서로의 마음을 담은 선물을 주고받으며, 그 속에 담긴 사랑과 감사의 마음을 느낄 수 있었다. 저녁이 되어, 가족들과 함께 크리스마스 영화를 보며 하루를 마무리했다.모든 순간이 따뜻하고, 평화롭고, 기쁨 가득했다. 오늘 하루가 주는 작은 기적들에 감사하며, 내일을 기대한다.",
          },
          {
            diaryNum: 2,
            diaryName: "강남에 맛집 탐방 후기",
            dContents: "강남에 맛집 닭도리탕타앝수육",
          },
        ],
      },
      // 달력 관련 데이터
      // today: new Date(),
      today: moment(), // moment 객체 생성
      week: ["일", "월", "화", "수", "목", "금", "토"],
      calendarHeader: "",
      myYear: 0,
      myMonth: 0,
      days: [],
      isSelectedtoday: false,
    };
  },
  mounted() {
    this.calendarImplementation();
  },
  created() {
    // 페이지가 로드될 때 초기 이미지 설정
    this.bgImage();
    this.getToken();
  },
  methods: {
    // 달력 만들기----------------------------------
    calendarImplementation: function () {
      this.days = [];
      this.myYear = this.today.year();
      this.myMonth = this.today.month();

      // 시작 요일 찾기
      const startDayOfTheMonth = moment([this.myYear, this.myMonth, 1]).day();
      // 마지막 날
      const endDayOfTheMonth = moment([this.myYear, this.myMonth])
        .endOf("month")
        .date();

      // 시작날부터 마지막 날까지 채우기
      const basicDays = Array.from(
        { length: endDayOfTheMonth },
        (v, i) => i + 1
      );

      // 전달의 마지막 날 가져오기
      var prevMonthofLastDate = moment([this.myYear, this.myMonth])
        .subtract(1, "months")
        .endOf("month")
        .date();

      // 시작 요일까지의 빈 날짜 채우기 - 전달 날짜로
      const emptyDays = Array.from(
        { length: startDayOfTheMonth },
        (_, i) => prevMonthofLastDate - i
      ).reverse();
      // 두 배열 합치기
      const combinedDays = [...emptyDays, ...basicDays];
      // 7일씩 나누고 넣기
      for (let i = 0; i < endDayOfTheMonth + startDayOfTheMonth; i += 7) {
        this.days.push(combinedDays.slice(i, i + 7));
      }
      this.calendarHeader = `${this.myYear}년 ${this.myMonth + 1} 월`;
      this.addLastWeekEmptyDays();
    },

    // 막주 날짜 채우기----------------------------------
    addLastWeekEmptyDays: function () {
      // 마지막 주에 몇개의 값이 있는지 확인
      const daysLastIndex = this.days.length - 1; // index 여서 -1
      const lastdayIdx = this.days[daysLastIndex].length;

      // 마지막 날 이후엔 1부터 채우기
      if (this.days[daysLastIndex] !== 7) {
        this.days[daysLastIndex].length = 7;
        for (var i = lastdayIdx; i < 7; i++) {
          this.days[daysLastIndex][i] = i - lastdayIdx + 1;
        }
      }
    },

    // 날짜 변경----------------------------------
    changeMonth: function (val) {
      // moment 적용한거로 변경
      this.today = moment(this.today).add(val, "months").startOf("month");
      this.calendarImplementation();
    },

    // 해당 화면 Background 이미지 설정----------------------------------
    bgImage() {
      var newImage = "type5";
      this.$emit("bgImage", newImage);
    },
    getToken() {
      this.AccessToken = localStorage.getItem("jwtToken");
      if (this.AccessToken != null) {
        this.isLoggedIn = true;
      } else {
        this.isLoggedIn = false;
        this.$router.push("/login");
      }
    },

    // 탭 전환하기----------------------------------
    changeTab(tab) {
      this.activeTab = tab;
      if (tab === "recommend") {
        this.isTabRecList = true;
      } else {
        this.isTabRecList = false;
      }
    },

    // 오늘 날짜면 달력에 동그라미 표시----------------------------------
    isToday(day) {
      // moment 생성 -> 날짜 라이브러리
      const moment = require("moment");
      // 오늘 날짜
      const todayy = moment();
      // 오늘 날짜에 해당하면 클래스 표시 위해 format
      var indexDay = moment([this.myYear, this.myMonth, day]).format(
        "YYYY-MM-DD"
      );

      return indexDay === todayy.format("YYYY-MM-DD");
    },

    isSelected(day) {
      // 화면 최초 진입 시 오늘 날짜면 표시
      // 한번 움직였으면 상태값 바꾸고 클릭한 곳에 동그라미 표시하고
      // 데이터 불러오기
      return this.isToday(day) && !this.isSelectedtoday;
    },

    // 받아온 추천 리스트에 rDate를 화면에 점으로 표시하기----------------------------------
    isInRecList(day) {
      const rdates = this.RecommendList.map((item) => item.rdate);
      var indexDay = moment([this.myYear, this.myMonth, day]).format(
        "YYYY-MM-DD"
      );
      return rdates.includes(indexDay);
    },

    // 이전달, 다음달 날짜면 회색 처리 / 선택 못하게 일단 막아두기
    isPrevNextDay(i, index) {
      return (
        (i == 0 && index > this.days[i][this.days[i].length - 1]) ||
        (i == this.days.length - 1 && index < 7)
      );
    },
  },
  components: {
    RecommendList,
    MyDiaryList,
  },
};
</script>

<style scoped>
@import "@/css/client/myPlace/myPlaceDiary.css";
@import "@/css/client/myPlace/calendar.css";

.prev-next-monthDay {
  color: #c7c7c7;
}

#calendarSection {
  text-align: center;
  text-align: -webkit-center;
  text-align: -moz-center;
}
table {
  border-spacing: 2px;
  border-collapse: separate;
}
td {
  width: 65px;
  height: 65px;
  text-align: center;
  vertical-align: baseline;
  border: 2px solid transparent;
  border-radius: 50%;
  line-height: 58px;
  cursor: pointer;
}

/* tr td:first-child,
tr th:first-child {
  color: red;
}
tr td:last-child,
tr th:last-child {
  color: blue;
} */
</style>
