<template>
  <div class="container1">
    <div class="report-container">
      <div class="report-title">
        <span>{{ UserName }}님의 스트레스 추이 😊</span>
      </div>
      <div class="total_report_card">
        <div class="select-date-area">
          <!-- 날짜 선택 -->
          <select v-model="SelectDate" class="date-selector">
            <option value="day">하루</option>
            <option value="days">일간</option>
            <option value="month">월간</option>
            <option value="year">연간</option>
          </select>
          <!-- 하루 -->
          <div class="date-input-area">
            <input
              type="date"
              v-if="SelectDate === 'day'"
              v-model="Oneday"
              :max="nowDate"
              class="input-text"
            />
          </div>
          <!-- 일간 최대 14일 -->
          <div class="date-input-area" v-if="SelectDate === 'days'">
            <input
              type="date"
              v-model="Startdays"
              :max="nowDate"
              @change="updateEndDateRange"
              class="input-text"
            />
            ~
            <input
              type="date"
              v-model="Enddays"
              :min="minEndDays"
              :max="maxEndDays"
              class="input-text"
            />
          </div>
          <!-- 월간 최대 12개월 -->
          <div class="date-input-area" v-if="SelectDate === 'month'">
            <input
              type="month"
              v-model="Startmonth"
              :max="nowDate"
              @change="updateEndMonthRange"
              class="input-text"
            />
            ~
            <input
              type="month"
              v-model="Endmonth"
              :min="minEndMonth"
              :max="maxEndMonth"
              class="input-text"
            />
          </div>
          <!-- 연간 상관 없음-->
          <div class="date-input-area" v-if="SelectDate === 'year'">
            <select v-model="SelectYear" class="year-selector input-text">
              <option>2020</option>
              <option>2021</option>
              <option>2022</option>
              <option>2023</option>
              <option selected>2024</option>
            </select>
          </div>
          <div class="date-input-area">
            <button class="big-ctlbtn else-btn" @click="sendStressData">검색</button>
          </div>
        </div>
        <div class="report-contents">
          <div v-if="SelectDate === 'day'">
            {{ Oneday }} 스트레스 수치
          </div>
          <div v-if="SelectDate === 'days'">
            {{ Startdays }} ~ {{ Enddays }} 스트레스 수치
          </div>
          <div v-if="SelectDate === 'month'">
            {{ Startmonth }}월 ~ {{ Endmonth }}월 스트레스 수치
          </div>
          <div v-if="SelectDate === 'year'">
            {{ SelectYear }}년 스트레스 수치
          </div>
        </div>
        <div class="report-chart-area">
          <canvas ref="charts"></canvas>
        </div>
      </div>
    </div>
  </div>
</template>

<script>

import { onMounted, ref } from "vue";
import axios from "axios";
import { jwtDecode } from "jwt-decode";
import {Chart, registerables} from 'chart.js';
Chart.register(...registerables);
export default {
  name: "Emotional_report",
  components: {
  },
  data() {
    return {
      SelectDate: "day",
      UserName: "",
      imsichart: null,
      // 차트 시작
      chartData: {

        labels: [],

        datasets: [
          {
            data: [0, 0], // 초기 값으로 0을 설정
            backgroundColor: ['rgba(75, 192, 192, 0.5)', '#FFFFFF'],
            borderColor: 'rgba(75, 192, 192, 0.5)', // 파란색 계열 테두리
            borderWidth: 1,
          },
          {
            data: [0, 0], // 초기 값으로 0을 설정
            backgroundColor: ['rgba(75, 192, 192, 0.5)', '#FFFFFF'],
            borderColor: 'rgba(75, 192, 192, 0.5)', // 파란색 계열 테두리
            borderWidth: 1,
          },
          {
            data: [0, 0], // 초기 값으로 0을 설정
            backgroundColor: ['rgba(75, 192, 192, 0.5)', '#FFFFFF'],
            borderColor: 'rgba(75, 192, 192, 0.5)', // 파란색 계열 테두리
            borderWidth: 1,
          },
        ],
      },

      // 현재 클라이언트 주소
      ipAddress : window.location.host.split(':')[0],

      // 현재 날짜
      nowDate: new Date().toISOString().slice(0, 10),

      // 하루
      Oneday: new Date().toISOString().slice(0, 10),

      // 일간
      Startdays: new Date().toISOString().slice(0, 10),
      Enddays: new Date().toISOString().slice(0, 10),
      minEndDays: "",
      maxEndDays: "",

      // 월간

      Startmonth: new Date().toISOString().slice(0, 7),
      Endmonth: new Date().toISOString().slice(0, 7),
      minEndMonth: "",
      maxEndMonth: "",

      // 연도별로
      SelectYear: new Date().toISOString().slice(0, 4),

      // 차트에 뿌려질 데이터
      stressData : [],
      diaryScore : [],
      faceScore : [],
      stressLabel: [],

    };
  },
  created() {
    this.bgImage();
  },
  methods: {
    bgImage() {
      var newImage = "type2";
      this.$emit("bgImage", newImage);
    },
    // 일간 유효성 검사
    updateEndDateRange() {
      // 종료 날짜 초기화
      this.Enddays = this.Startdays;
      // 시작일이 변경되면 종료일의 범위를 다시 계산
      const getMinDate = new Date(this.Startdays);
      const getMaxDate = new Date(this.Startdays);

      getMinDate.setDate(getMinDate.getDate() - 13);
      this.minEndDays = getMinDate.toISOString().slice(0, 10);

      getMaxDate.setDate(getMaxDate.getDate() + 13);
      this.maxEndDays = getMaxDate.toISOString().slice(0, 10);

      console.log(this.minEndDays, this.maxEndDays);
    },
    // 월간 유효성 검사
    updateEndMonthRange() {
      // 종료 날짜 초기화
      this.Endmonth = this.Startmonth;
      // 시작일이 변경되면 종료일의 범위를 다시 계산
      const getMinDate = new Date(this.Startmonth);
      const getMaxDate = new Date(this.Startmonth);

      getMinDate.setMonth(getMinDate.getMonth() - 11);
      this.minEndMonth = getMinDate.toISOString().slice(0, 7);

      getMaxDate.setMonth(getMaxDate.getMonth() + 11);
      this.maxEndMonth = getMaxDate.toISOString().slice(0, 7);

      console.log(this.minEndMonth, this.maxEndMonth);
    },
    // 날짜 보내기
    sendStressData() {
      const sendDate = new FormData();
      sendDate.append("oneday", this.Oneday);
      sendDate.append("days", [this.Startdays, this.Enddays]);
      sendDate.append("month", [this.Startmonth, this.Endmonth]);
      sendDate.append("selectYear", this.SelectYear);
      sendDate.append("userId", this.data.id)
      sendDate.append("flag", this.SelectDate);

      axios.post(`http://${this.ipAddress}/Haru/report/oneday`, sendDate)
          .then((res)=> {

            // 스트레스 수치
            this.stressScore = res.data.map(item => item.stress_score);
            // 일기 수치
            this.diaryScore = res.data.map(item => item.diary_data)
            // 얼굴 수치
            this.faceScore = res.data.map(item => item.face_data);
            // 라벨
            this.stressLabel = res.data.map(item=> item.stress_cdate);

            // 차트 업데이트를 라이프사이클 훅으로 이동
            console.log(res);

            console.log("스트레스 점수 : " + this.stressScore);
            console.log("일기 점수 : " + this.diaryScore);
            console.log("얼굴 점수 : " + this.faceScore);
            console.log("라벨들 : " + this.stressLabel);

            // 차트 인스턴스를 다시 생성하여 업데이트된 데이터를 반영
            console.log("성공!");
            this.setupChart()
          })
    },
    setupChart() {
      const ctx = this.$refs.charts;
      if (this.imsichart) this.imsichart.destroy(); // 차트가 있다면
      this.imsichart = new Chart(ctx, {
        type : 'line',
        data: {
          labels: this.stressLabel,
          datasets: [
            {
              label: '일기',
              data: this.diaryScore,
              fill: false,
              borderColor: 'rgb(255,0,0)',
              tension: 0.3,
            },
            {
              label: '얼굴',
              data: this.faceScore,
              fill: false,
              borderColor: 'rgb(255,182,0)',
              tension: 0.3,
            },
            {
              label: '스트레스',
              data: this.stressScore,
              fill: false,
              borderColor: 'rgb(131,255,0)',
              tension: 0.3,
            }
          ]
        },
        options : {
          maintainAspectRatio: false,
        }
      })

    },
  },
  // 로그인 토큰
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
            if (res.data === "Logout") {
              localStorage.removeItem("jwtToken");
              window.location.href = "/login";
            }
          });
    };

    const decodeToken = (token) => {
      console.log(token);
      if (token == null) return false;
      const decoded = jwtDecode(token);
      console.log(JSON.stringify(decoded));
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
    //this.setupChart()
    this.sendStressData()
    this.UserName = this.data.username;


  }
};
</script>
<script setup></script>

<style scoped>
@import url("@/css/client/stress/Emotional_report.css");
/*날짜 입력 input 스타일*/
.date-input-area > input {
  border-radius: 20px;
  width: 150px;
  border: 2px solid #737373;
  min-height: 53px;
  padding: 10px 15px;
}
.input-text:hover {
  border-color: #f8c08d;
  box-shadow: 0 2px 4px rgba(3, 3, 3, 0.1);
}
</style>
