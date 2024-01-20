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

<!--              'cal-set-today': isToday(childIndex),   ==>   오늘 날짜면 background-color 주기     -->
<!--              'selected-cal-day': isSelected(i, childIndex),   ==>   선택이 됐으면 border 색상 주기     -->
<!--              'calendar-event': isInRecList(i, childIndex),   ==>   추천된 기록이 있으면 작은 점으로 표시     -->
<!--              'prev-next-monthDay': isPrevNextDay(i, childIndex),   ==>   전달, 다음달 날짜는 흐리게 표시     -->
              <tbody>
                <tr v-for="(index, i) in days" :key="i">
                  <td
                    v-for="childIndex in index"
                    :key="childIndex"
                    @click="dayClick(i, childIndex)"
                    :class="{
                      'cal-set-today': isToday(childIndex),
                      'selected-cal-day': isSelected(i, childIndex),
                      'calendar-event': isInRecList(i, childIndex),
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
            @click="changeTab('recommend')"
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

        <!-- 컴포넌트로 토글되는 영역 (추천리스트, 일기 리스트) -->
        <div class="tab-content-area">
          <RecommendList
            :RecPlace="RecPlace"
            :sendSelectedDate="sendSelectedDate"
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
import {onMounted, ref} from "vue";
import axios from "axios";
import {jwtDecode} from "jwt-decode";

export default {
  data() {
    const today = moment(); // moment 객체 생성
    const sDate = [today.year(), today.month(), today.date(), 0]; // 선택한 날짜 정보 (연도, 월, 일, index)
    const myDate = []; // 현재 화면에 보여지는 날짜 정보

    return {
      // 달력 외 관련
      activeTab: "recommend", // 기본값으로 추천 리스트를 활성화
      isBtnHeartNone: false, // 하트버튼이 안보여야되는지
      isTabRecList: true, // 추천 리스트 탭 활성화
      RecommendList: [], // controller 에서 넘어온 월별 추천리스트 + 일기 data
      RecPlace: [], // 추천리스트로 넘길 데이터 담는 배열
      sendSelectedDate: "", // 추천리스트로 넘길 날짜
      // 일기 리스트
      diaryList: [],
      // 달력 관련 데이터
      today: today,
      sDate: sDate,
      myDate: myDate,
      week: ["일", "월", "화", "수", "목", "금", "토"],
      calendarHeader: "",
      days: [], // 해당 월의 일 담은 배열
      isSelectedtoday: false, // 한 번 이라도 선택된게 있는지
    };
  },
  mounted() {
    this.calendarImplementation();
    this.getMyRecPlace(this.sDate);
  },
  created() {
    // 페이지가 로드될 때 초기 이미지 설정
    this.bgImage();
    this.getToken();
  },
  setup() {
    const isLoggedIn = ref(false);
    const data = ref([]);

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
  methods: {
    // 추천리스트, 추천받은 날짜, 일기 ... 가져오기----------------------------------------------------------------
    getMyRecPlace() {
      // 달력의 첫번째 표시 날짜와 마지막 표시 날짜 가져오기
      var startSdate = moment([this.myDate[0], this.myDate[1], this.days[0][0]]);
      var endSdate = moment([this.myDate[0], this.myDate[1], this.days[this.days.length-1][6]]);

      // 전달, 다음달 처리
      if (this.days[0][0] > 1) {
        startSdate = startSdate.subtract(1,'months').format("YYYY-MM-DD");
      }
      if (this.days[this.days.length-1][6] < 7) {
        endSdate = endSdate.add(1,'months').format("YYYY-MM-DD");
      }
      // console.log(`${startSdate}, ${endSdate}`);

      axios.post(`http://${process.env.VUE_APP_BACK_END_URL}/getRecommendList`, {
        userid: this.data.id,
        startdate: startSdate,
        enddate: endSdate
      })
      .then(res => {
        console.log(res.data);
        this.RecommendList = res.data.rec_list;
        this.diaryList = res.data.diary_list;
      })
      .catch(error => {console.error('error! ' + error)});
    },

    // 달력 만들기----------------------------------
    calendarImplementation: function () {
      this.days = [];
      this.myDate[0] = this.today.year();
      this.myDate[1] = this.today.month();

      // 시작 요일 찾기
      const startDayOfTheMonth = moment([
        this.myDate[0],
        this.myDate[1],
        1,
      ]).day();
      // 마지막 날
      const endDayOfTheMonth = moment([this.myDate[0], this.myDate[1]])
        .endOf("month")
        .date();

      // 시작날부터 마지막 날까지 채우기
      const basicDays = Array.from(
        { length: endDayOfTheMonth },
        (v, i) => i + 1
      );

      // 전달의 마지막 날 가져오기
      var prevMonthofLastDate = moment([this.myDate[0], this.myDate[1]])
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
      this.calendarHeader = `${this.myDate[0]}년 ${this.myDate[1] + 1} 월`;
      this.addLastWeekEmptyDays();
      this.getMyRecPlace(); // 달력에 event 작은 점 표시 -> 데이터가 있으면 표시
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
      var today = moment();
      // 오늘 날짜에 해당하면 클래스 표시 위해 format
      var indexDay = moment([this.myDate[0], this.myDate[1], day]).format(
        "YYYY-MM-DD"
      );

      return indexDay == today.format("YYYY-MM-DD");
    },
    // 선택됐는지 확인해서 css 적용
    isSelected(i, day) {
      // 선택한 날짜
      var sdate = moment([this.sDate[0], this.sDate[1], this.sDate[2]]).format(
        "YYYY-MM-DD"
      );

      // 하루하루 각각
      var nowdate = moment([this.myDate[0], this.myDate[1], day]).format(
        "YYYY-MM-DD"
      );
      // console.log('sdate : ' + sdate + " / nowdate : " + nowdate);

      // 화면 최초 진입 시 오늘 날짜면 오늘 날짜에 표시
      if (this.isToday(day) && sdate && !this.isSelectedtoday) {
        return true;
      } else if (sdate == nowdate && i == this.sDate[3]) {
        return true;
      }
      return false;
    },

    // 날짜 클릭했을 때---------------------------------------------------------------------------------
    dayClick(i, day) {
      // 한 번 클릭된 이후엔 오늘 날짜 자동으로 선택 안되게
      if (this.isSelectedtoday == false) this.isSelectedtoday = !this.isSelectedtoday;

      if (i == 0 && day > 6) {
        // 이전 달 클릭 시
        this.changeMonth(-1);

        this.sDate = [
          this.myDate[0],
          this.myDate[1],
          day,
          this.days.length - 1,
        ];
      } else if (i == this.days.length - 1 && day < 7) {
        // 다음 달 클릭 시
        this.changeMonth(1);
        this.sDate = [this.myDate[0], this.myDate[1], day, 0];
      } else {
        this.sDate = [this.myDate[0], this.myDate[1], day, i];
      }

      // 선택한 일자에 해당하는 추천리스트 목록 담는 배열
      this.RecPlace = []; // 배열 초기화

      // 데이터 가져오기
      this.RecommendList.map(item => {
        // 가져온 데이터
        const placeCdate = moment(item.place_cdate).format('YYYY-MM-DD');
        // 선택한 날짜
        var selectedDate = moment([this.sDate[0], this.sDate[1], day]).format("YYYY-MM-DD");
        // console.log(placeCdate + " / selectedDate : " + selectedDate);

        // 날짜가 같은거만 리스트로 출력
        // if (placeCdate.includes(selectedDate)) {
        if (placeCdate == selectedDate) {
          // console.log(item.place);
          this.RecPlace.push(item.place);
        }
      });
      this.sendSelectedDate = moment([this.sDate[0], this.sDate[1], this.sDate[2]]).format("MM월 DD일");
      console.log(this.RecPlace);
    },

    // 받아온 추천 리스트에 rDate를 화면에 점으로 표시하기----------------------------------
    isInRecList(idx, day) {
      // console.log(idx + ' / ' + day);
      // 가져온 데이터
      const rdates = this.RecommendList.map(item => {
        // console.log('rDate!! : ' + moment(item.place_cdate).format("YYYY-MM-DD"));
        return moment(item.place_cdate).format("YYYY-MM-DD");
      });

      // 달력에 표시된 각각 날짜
      var indexDay = moment([this.myDate[0], this.myDate[1], day]);

      // 전달, 다음달 날짜 처리
      if (idx == 0 && day > 6) {
        indexDay = indexDay.subtract(1, 'months');
      } else if (idx == this.days.length-1 && day < 7) {
        indexDay = indexDay.add(1, 'months');
      }
      indexDay = indexDay.format("YYYY-MM-DD")
      // console.log('indexDay : ' + indexDay);
      return rdates.includes(indexDay);
    },

    // 이전달, 다음달 날짜면 회색 처리
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
