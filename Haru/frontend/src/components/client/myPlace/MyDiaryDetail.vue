<template>
  <!-- 일기 수정 -->
  <div>
    <div class="feed-modal-wrap" id="modal-wrap" @click="$emit('close-modal')">
      <!-- 모달창 영역 -->
      <div class="mydiary-modal-container mydiary-rec-modal" @click.stop>
        <!-- 일기 날짜 -->
        <div class="diary-date-area">
          <h2 id="diary-date-h2">{{ rDate }}</h2>
        </div>

        <!-- 일기 제목, 내용 -->
        <div class="input-diary">
          <div class="diary-title-box">
            <input
              type="text"
              name="diary-title"
              id="diary-title"
              placeholder="제목"
              maxlength="30"
              ref="diaryTitle"
              :value="selectedDiary.diaryName"
              :readonly="dStatus === 'read'"
            />
          </div>
          <div class="diary-content-box">
            <textarea
              name="diary-content"
              id="diary-content"
              placeholder="어떤 일이 있었나요?"
              maxlength="1000"
              :value="selectedDiary.dContents"
              :readonly="dStatus === 'read'"
            ></textarea>
          </div>
        </div>
        <!-- 일기 끝 -->

        <!-- 버튼 영역 -->
        <div class="mydiary-update-btn-area">
          <button @click="$emit('close-modal')" class="big-ctlbtn cancle-btn">
            취소
          </button>
          <button
            v-if="dStatus === 'read'"
            class="big-ctlbtn insert-btn"
            @click="updateDStatus('update')"
          >
            수정하기
          </button>
          <button
            v-if="dStatus === 'update'"
            class="big-ctlbtn select-btn"
            @click="updateDStatus('update')"
          >
            수정완료
          </button>
          <button class="big-ctlbtn delete-btn" @click="deleteDiary">
            삭제하기
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {};
  },
  props: {
    selectedDiary: Object,
    rDate: String,
    dStatus: String,
  },
  methods: {
    updateDStatus(status) {
      this.$emit("update-d-status", status);
      if (status === "update") {
        this.$refs.diaryTitle.focus();
      }
    },
  },
};
</script>
<style scoped>
@import "@/css/client/myPlace/myDiary.css";
.big-ctlbtn {
  width: 140px;
}
</style>
