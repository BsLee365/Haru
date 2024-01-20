<template>
  <div class="container1">
    <div class="report-container">
      <div class="report-title">
        <span>{{ UserName }}의 스트레스 종합 보고서 😊</span>
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
            <button class="big-ctlbtn else-btn">검색</button>
          </div>
        </div>
        <div class="report-contents">
          <div v-if="SelectDate === 'day'">
            {{ Oneday }} 스트레스 수치
            <span class="badge rounded-pill normal-badge">정상</span>
          </div>
          <div v-if="SelectDate === 'days'">
            {{ Startdays }} ~ {{ Enddays }} 스트레스 수치
            <span class="badge rounded-pill warn-badge">위험</span>
          </div>
          <div v-if="SelectDate === 'month'">
            {{ Startmonth }}월 ~ {{ Endmonth }}월 스트레스 수치
            <span class="badge rounded-pill normal-badge">정상</span>
          </div>
          <div v-if="SelectDate === 'year'">
            {{ SelectYear }}년 스트레스 수치
            <span class="badge rounded-pill attention-badge">주의</span>
          </div>
        </div>
        <div class="report-chart-area">
          <Line
            ref="chart"
            :options="chartOptions"
            :data="chartData"
            style="width: 100%; height: 100%"
          ></Line>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
// import {
//   Chart as ChartJS,
//   CategoryScale,
//   LinearScale,
//   PointElement,
//   LineElement,
//   Title,
//   Tooltip,
//   Legend,
// } from "chart.js";
// import { Line } from "vue-chartjs";

// ChartJS.register(
//   CategoryScale,
//   LinearScale,
//   PointElement,
//   LineElement,
//   Title,
//   Tooltip,
//   Legend
// );

// // export default {
// // //   name: "Emotional_report",
// // //   components: {
// // //     Line,
// // //   },
// // //   data() {
// // //     return {
// // //       SelectDate: "day",
// // //       UserName: "이범석",

// // //       // 현재 날짜
// // //       nowDate: new Date().toISOString().slice(0, 10),

// // //       // 하루
// // //       Oneday: new Date().toISOString().slice(0, 10),

// // //       // 일간
// // //       Startdays: new Date().toISOString().slice(0, 10),
// // //       Enddays: new Date().toISOString().slice(0, 10),
// // //       minEndDays: "",
// // //       maxEndDays: "",

// // //       // 월간
// // //       Startmonth: new Date().toISOString().slice(0, 10),
// // //       Endmonth: new Date().toISOString().slice(0, 10),
// // //       minEndMonth: "",
// // //       maxEndMonth: "",

// // //       // 연도별로
// // //       SelectYear: new Date().toISOString().slice(0, 4),

// // //       //차트 영역
// // //       chartData: {
// // //         //바뀌어야 될 부분
// // //         labels: [
// // //           "1월",
// // //           "2월",
// // //           "3월",
// // //           "4월",
// // //           "5월",
// // //           "6월",
// // //           "7월",
// // //           "8월",
// // //           "9월",
// // //           "10월",
// // //           "11월",
// // //           "12월",
// // //         ],
// // //         datasets: [
// // //           {
// // //             label: this.UserName + "님 스트레스 수치",
// // //             backgroundColor: "#f87979",
// // //             // 바뀌어야 될 부분
// // //             data: [10, 7.8, 2.1, 3.4, 5.6, 7.8, 9.1, 8.7, 6.5, 4.3, 2.1, 1.2],
// // //             tension: 0.1,
// // //           },
// // //         ],
// // //       },
// // //       chartOptions: {
// // //         responsive: false,
// // //         pointStyle: "circle",
// // //         lineWidth: 5,
// // //         pointRadius: 8,
// // //         pointHoverRadius: 12,
// // //       },
// // //     };
// // //   },
// // //   created() {
// // //     this.bgImage();
// // //     this.chartData.datasets[0].label = this.UserName + "님 스트레스 수치";
// // //   },
// // //   methods: {
// // //     bgImage() {
// // //       var newImage = "type2";
// // //       this.$emit("bgImage", newImage);
// // //     },
// // //     // 일간 유효성 검사
// // //     updateEndDateRange() {
// // //       // 종료 날짜 초기화
// // //       this.Enddays = this.Startdays;
// // //       // 시작일이 변경되면 종료일의 범위를 다시 계산
// // //       const getMinDate = new Date(this.Startdays);
// // //       const getMaxDate = new Date(this.Startdays);

// // //       getMinDate.setDate(getMinDate.getDate() - 13);
// // //       this.minEndDays = getMinDate.toISOString().slice(0, 10);

// // //       getMaxDate.setDate(getMaxDate.getDate() + 13);
// // //       this.maxEndDays = getMaxDate.toISOString().slice(0, 10);

// // //       console.log(this.minEndDays, this.maxEndDays);
// // //     },
// // //     // 월간 유효성 검사
// // //     updateEndMonthRange() {
// // //       // 종료 날짜 초기화
// // //       this.Endmonth = this.Startmonth;
// // //       // 시작일이 변경되면 종료일의 범위를 다시 계산
// // //       const getMinDate = new Date(this.Startmonth);
// // //       const getMaxDate = new Date(this.Startmonth);

// // //       getMinDate.setMonth(getMinDate.getMonth() - 11);
// // //       this.minEndMonth = getMinDate.toISOString().slice(0, 7);

// // //       getMaxDate.setMonth(getMaxDate.getMonth() + 11);
// // //       this.maxEndMonth = getMaxDate.toISOString().slice(0, 7);

// // //       console.log(this.minEndMonth, this.maxEndMonth);
// // //     },
// // //   },
// // // };
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
