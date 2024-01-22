<template>
  <div class="container1">
    <div class="qna-write-container">
      <form class="qna-write-form" autocomplete="off">
        <h2>Q&A 수정</h2>
        <div class="info-input-container">
          <div class="qna-label-area">
            <label for="qna-title">카테고리 선택</label>
          </div>

          <!-- 카테고리 선택 -->
          <select class="input-select" v-model="selectCategory">
            <option value="usage">이용문의</option>
            <!-- [공지사항은 관리자만 입력할 수 있도록] => v-if="mid.slide(5) === 'admin'" -->
            <option value="notice">공지사항</option>
          </select>
        </div>

        <!-- 제목 -->
        <div class="info-input-container">
          <div class="qna-label-area">
            <label for="qna-title">제목</label>
          </div>
          <div class="input-area">
            <input
              class="input-text"
              type="text"
              name="qna-title"
              id="qna-title"
              placeholder="Q&A 제목을 입력해주세요."
              :value="myQnA.title"
            />
          </div>
        </div>

        <!-- 내용 -->
        <div class="info-input-container">
          <div class="qna-label-area">
            <label for="qna-content">내용</label>
          </div>
          <textarea
            class="input-text"
            id="qna-content"
            cols="88"
            rows="10"
            placeholder="Q&A 내용을 작성하세요."
            v-model="myQnA.contents"
          ></textarea>
        </div>

        <div class="qna-btn-group">
          <button class="big-ctlbtn cancle-btn" type="button" @click="cancel">
            취소
          </button>
          <button class="big-ctlbtn update-btn" type="submit">수정완료</button>
        </div>
      </form>
    </div>
  </div>
</template>
<script>
export default {
  name: "WriteQnA",
  data() {
    return {
      myQnA: {
        category: "usage",
        title: "어떻게 스트레스 분석이 이렇게 잘맞는건가요?!?!",
        contents: "너무 잘맞아요!",
      },
    };
  },
  methods: {
    cancel() {
      this.$router.go(-1); // 뒤로가기
    },
  },
  created() {
    this.$emit("bgImage", "type3");
    this.selectCategory = this.myQnA.category;
  },
};
</script>
<style scoped>
@import url("@/css/client/qna/qnaForm.css");
</style>
