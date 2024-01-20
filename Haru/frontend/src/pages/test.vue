<template>
  <div id="calendarSection">
    <div>
      <button type="button" @click="changeMonth(-1)">&lt;</button>
      {{ calendarHeader }}
      <button type="button" @click="changeMonth(1)">&gt;</button>
    </div>
    <table>
      <thead>
        <tr>
          <th v-for="index in week" :key="index">
            {{ index }}
          </th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(index, i) in days" :key="i">
          <td v-for="childIndex in index" :key="childIndex">
            {{ childIndex }}
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
export default {
  name: "DatePicker",
  data() {
    return {
      today: new Date(),
      week: ["일", "월", "화", "수", "목", "금", "토"],
      calendarHeader: "",
      days: [],
    };
  },
  mounted() {
    this.calendarImplementation();
  },
  methods: {
    calendarImplementation: function () {
      this.days = [];
      const year = this.today.getFullYear();
      const month = this.today.getMonth();
      const startDayOfTheMonth = new Date(year, month, 1).getDay();
      const endDayOfTheMonth = new Date(year, month + 1, 0).getDate();
      const basicDays = Array.from(
        { length: endDayOfTheMonth },
        (v, i) => i + 1
      );
      const emptyDays = Array(startDayOfTheMonth).fill(null);
      const combinedDays = [...emptyDays, ...basicDays];
      for (let i = 0; i < endDayOfTheMonth + startDayOfTheMonth; i += 7) {
        this.days.push(combinedDays.slice(i, i + 7));
      }
      this.calendarHeader = `${year}년 ${month + 1} 월`;
      this.addLastWeekEmptyDays();
    },
    addLastWeekEmptyDays: function () {
      const daysLastIndex = this.days.length - 1;
      if (this.days[daysLastIndex] !== 7) this.days[daysLastIndex].length = 7;
    },
    changeMonth: function (val) {
      this.today = new Date(
        this.today.setMonth(this.today.getMonth() + val, 1)
      );
      this.calendarImplementation();
    },
  },
};
</script>

<style>
#calendarSection {
  text-align: center;
  text-align: -webkit-center;
  text-align: -moz-center;
}
table {
  width: 100%;
}
td {
  width: 40px;
  height: 50px;
  text-align: center;
  vertical-align: baseline;
}
</style>
