<template>
  <!-- 내 장소 - 일기 리스트 -->
  <div class="">
    <MyDiaryDetail
      @close-modal="closeModal"
      @update-d-status="changeStatus"
      v-if="modal_Check"
      :selectedDiary="selectedDiary"
      :rDate="diaryList.rdate"
      :dStatus="dStatus"
    />
    <!-- 날짜 -->
    <div class="myplace-diary-date">
      <p>{{ diaryList.rdate }}</p>
    </div>

    <!-- 일기 하나씩 -->
    <div
      class="myplace-diary-area"
      v-for="(diary, idx) in diaryList.dList"
      :key="idx"
    >
      <!-- 아이콘 -->
      <div class="myplace-diary-icon-bg">
        <img src="@/img/myPlace/diaryicon.png" alt="" />
      </div>

      <!-- 제목 -->
      <div class="myplace-diary-title-area">
        <p
          class="cursor-p"
          @click="changeStatus('read'), openModal(), openDiaryUpdate(diary)"
        >
          {{ diary.diaryName }}
        </p>
      </div>

      <!-- 버튼 (수정, 삭제) -->
      <div class="myplace-diary-btn-area">
        <button
          @click="changeStatus('update'), openModal(), openDiaryUpdate(diary)"
          class="diary-update-btn"
        ></button>
        <button class="diary-delete-btn"></button>
      </div>
    </div>
  </div>
</template>

<script>
import MyDiaryDetail from "@/components/client/myPlace/MyDiaryDetail.vue";

export default {
  data() {
    return {
      selectedDiary: null,
      modal_Check: false,
      dStatus: "",
    };
  },
  props: {
    diaryList: Object,
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
  },
};
</script>
