<template>
  <!-- 내 장소 - 일기 리스트 -->
  <div class="">
    <MyDiaryDetail
      @close-modal="closeModal"
      @delete-diary="deleteDiary"
      @update-d-status="changeStatus"
      v-if="modal_Check"
      :selectedDiary="selectedDiary"
      :rDate="sendSelectedDate"
      :dStatus="dStatus"
    /> <!-- dStatus : 수정, 읽기 인지 판별 -->

    <!-- 날짜 -->
    <div class="myplace-diary-date">
      <p>{{ sendSelectedDate }}</p>
    </div>

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

    // 일기 삭제
    deleteDiary(diaryNum) {
      axios.post(`http://${process.env.VUE_APP_BACK_END_URL}/diary/deleteDiary/${diaryNum}`)
          .then(() => {
            alert("일기가 삭제되었습니다.");
            // this.$emit('close-modal');
            if (this.modal_Check==true) {this.closeModal();}
          })
          .catch(error => {
            console.log(error);
          })
    }
  },
};
</script>
