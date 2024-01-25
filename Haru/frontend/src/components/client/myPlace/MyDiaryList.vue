<template>
  <!-- 내 장소 - 일기 리스트 -->
  <div class="">
    <MyDiaryDetail
      @close-modal="closeModal"
      @delete-diary="deleteDiary"
      @update-d-status="changeStatus"
      @update-all-list="$emit('updateAllList')"
      @format-cdate="formatCDate"
      v-if="modal_Check"
      :selectedDiary="selectedDiary"
      :rDate="sendSelectedDate"
      :dStatus="dStatus"
    /> <!-- dStatus : 수정, 읽기 인지 판별 -->

    <!-- 날짜 -->
<!--    <div class="reclist-date-text myplace-diary-date">-->
<!--      <p>{{ sendSelectedDate }}</p>-->
<!--    </div>-->
    <p class="reclist-date-text" style="margin-bottom: 20px;" v-show="diaryList.length > 0">
      {{ sendSelectedDate }}
    </p>


    <!-- 일기 하나씩 -->
    <div
      class="myplace-diary-area"
      v-for="(diary) in diaryList"
      :key="diary.diary_num"
    >

      <!-- 아이콘 -->
      <div class="myplace-diary-icon-bg">
        <img src="@/img/myPlace/diaryicon.png" alt="" />
      </div>

      <!-- 제목 -->
      <div class="myplace-diary-title-area">
        <p
          class="cursor-p"
          @click="changeStatus('read'); openModal(); openDiaryUpdate(diary);"
        >
          {{ diary.diary_title }}
        </p>
      </div>

      <!-- 작성 시간 -->
      <div class="myplace-diary-rtime-area">
        <p
            class="cursor-p"
            @click="changeStatus('read'); openModal(); openDiaryUpdate(diary);"
        >
          {{ this.formatCDate(diary.diary_cdate) }}
        </p>
      </div>

      <!-- 버튼 (수정, 삭제) -->
      <div class="myplace-diary-btn-area">
        <button
          @click="changeStatus('update'); openModal(); openDiaryUpdate(diary);"
          class="diary-update-btn"
        ></button>
        <button class="diary-delete-btn" @click="deleteDiary(diary.diary_num)"></button>
      </div>
    </div>
  </div>
</template>

<script>
import MyDiaryDetail from "@/components/client/myPlace/MyDiaryDetail.vue";
import axios from "axios";
import moment from "moment";

export default {
  data() {
    return {
      selectedDiary: null,
      modal_Check: false,
      dStatus: "",
      // sendSelectedDate: String,
    };
  },
  props: {
    diaryList: Object,
    sendSelectedDate: String,
  },
  components: { MyDiaryDetail },
  methods: {
    openModal() {
      this.modal_Check = true;
    },

    closeModal() {
      this.modal_Check = false;
    },

    openDiaryUpdate(diary) {
      this.selectedDiary = diary;
    },
    changeStatus(status) {
      this.dStatus = status;
    },

    // 날짜(시간) 포맷
    formatCDate(cdate) {
      return moment(cdate).format("HH:mm");
    },

    // 일기 삭제
    deleteDiary(diaryNum) {
      if (confirm('일기를 삭제할까요?')) {
        axios.post(`http://${process.env.VUE_APP_BACK_END_URL}/diary/deleteDiary/${diaryNum}`)
            .then(() => {
              alert("일기가 삭제되었습니다.");
              if (this.modal_Check==true) {this.closeModal();}
              this.$emit('updateAllList');
            })
            .catch(error => {
              console.log(error);
            })
      }
    },
  },
};
</script>
