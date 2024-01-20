<template>
  <div class="container1">
    <div class="qna-container">
      <div class="faq-container">
        <h2 class="faq-title">나의 QnA 페이지</h2>
      </div>

      <div class="nav-bar">
        <router-link to="/QnA" class="nav-item" exact-active-class="active"
          >Q&A</router-link
        >
        <router-link to="/myQnA" class="nav-item" exact-active-class="active"
          >나의 질문 사항</router-link
        >
        <!-- ... 기타 링크 ... -->
      </div>
      <table class="qna-table">
        <thead>
          <tr>
            <th class="qna-tr">번호</th>
            <th class="qna-tr">카테고리</th>
            <th>제목</th>
            <th class="qna-tr">작성일</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="item in paginatedQnA" :key="item.no">
            <td class="qna-tr">{{ item.no }}</td>
            <td class="qna-tr">{{ item.category }}</td>
            <td>
              <a href="/DetailQnA">{{ item.title }}</a
              ><span
                class="qna-badge"
                :class="{
                  notAnswered: item.progress === '미답변',
                  answered: item.progress === '완료',
                }"
                >{{ item.progress }}</span
              >
            </td>
            <td class="qna-tr">{{ item.date }}</td>
          </tr>
        </tbody>
      </table>

      <div class="pagination">
        <button
          class="pagination-button"
          :class="{ disabled: currentPage === 1 }"
          @click="setCurrentPage(currentPage - 1)"
          v-if="currentPage > 1"
        >
          &lt;
        </button>

        <button
          v-for="page in pageCount"
          :key="page"
          :class="{ 'pagination-button': true, active: currentPage === page }"
          @click="setCurrentPage(page)"
        >
          {{ page }}
        </button>

        <button
          class="pagination-button"
          :class="{ disabled: currentPage === pageCount }"
          @click="setCurrentPage(currentPage + 1)"
          v-if="currentPage < pageCount"
        >
          &gt;
        </button>
      </div>
      <div class="qna-search-container">
        <form id="qna-search-form" @submit.prevent="onSearch">
          <div class="qna-search-area">
            <input
              class="qna-search-input"
              v-model="searchQuery"
              type="text"
              placeholder="Search"
            />
            <button type="button" class="qna-search-btn" @click="onSearch">
              <img src="@/img/Feed/search_btn.png" alt="" />
            </button>
          </div>
        </form>
        <div>
          <button
            type="button"
            class="big-ctlbtn insert-btn qna-Write"
            @click="onWrite"
          >
            글쓰기
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      currentPage: 1,
      pageSize: 10,

      MyQna: [
        {
          no: 13,
          category: "이용문의",
          title: "부적절한 블라블라 내용 삭제 요청",
          date: "2017-11-22",
          progress: "미답변",
        },
        {
          no: 12,
          category: "이용문의",
          title: "배송 문의 드립니다",
          date: "2020-11-12",
          progress: "완료",
        },
        {
          no: 11,
          category: "이용문의",
          title: "부적절한 블라블라 내용 삭제 요청",
          date: "2017-11-22",
          progress: "미답변",
        },
        {
          no: 10,
          category: "이용문의",
          title: "배송 문의 드립니다",
          date: "2020-11-12",
          progress: "완료",
        },
        {
          no: 9,
          category: "이용문의",
          title: "부적절한 블라블라 내용 삭제 요청",
          date: "2017-11-22",
          progress: "완료",
        },
        {
          no: 8,
          category: "이용문의",
          title: "배송 문의 드립니다",
          date: "2020-11-12",
          progress: "완료",
        },
        {
          no: 7,
          category: "이용문의",
          title: "부적절한 블라블라 내용 삭제 요청",
          date: "2017-11-22",
          progress: "완료",
        },
        {
          no: 6,
          category: "이용문의",
          title: "배송 문의 드립니다",
          date: "2020-11-12",
          progress: "완료",
        },
        {
          no: 5,
          category: "이용문의",
          title: "부적절한 블라블라 내용 삭제 요청",
          date: "2017-11-22",
          progress: "완료",
        },
        {
          no: 4,
          category: "이용문의",
          title: "배송 문의 드립니다",
          date: "2020-11-12",
          progress: "완료",
        },
        {
          no: 3,
          category: "이용문의",
          title: "부적절한 블라블라 내용 삭제 요청",
          date: "2017-11-22",
          progress: "완료",
        },
        {
          no: 2,
          category: "이용문의",
          title: "배송 문의 드립니다",
          date: "2020-11-12",
          progress: "완료",
        },
        {
          no: 1,
          category: "이용문의",
          title: "부적절한 블라블라 내용 삭제 요청",
          date: "2017-11-22",
          progress: "완료",
        },
      ],
    };
  },
  computed: {
    paginatedQnA() {
      const start = (this.currentPage - 1) * this.pageSize;
      const end = start + this.pageSize;
      return this.MyQna.slice(start, end);
    },
    pageCount() {
      return Math.ceil(this.MyQna.length / this.pageSize);
    },
  },
  created() {
    this.$emit("bgImage", "type3");
  },
  methods: {
    toggle(index) {
      this.faqs[index].open = !this.faqs[index].open;
    },
    setCurrentPage(page) {
      // 페이지 번호가 유효한 범위 내에 있는지 확인
      if (page >= 1 && page <= this.pageCount) {
        this.currentPage = page; // 현재 페이지 상태를 업데이트

        // 부모 컴포넌트에 'update:currentPage' 이벤트를 방출하여
        // v-model이나 .sync modifier를 사용하여 바인딩된 값을 업데이트
        this.$emit("update:currentPage", page);
      }
    },
    // '이전' 버튼을 클릭했을 때의 로직
    goToPreviousPage() {
      if (this.currentPage > 1) {
        this.setCurrentPage(this.currentPage - 1);
      }
    },
    // '다음' 버튼을 클릭했을 때의 로직
    goToNextPage() {
      if (this.currentPage < this.pageCount) {
        this.setCurrentPage(this.currentPage + 1);
      }
    },
    onSearch() {
      console.log("검색 내용:", this.searchQuery);
    },
    onWrite() {
      console.log("글쓰기 페이지로 이동");
      this.$router.push({ name: "WriteQnA" });
    },
  },
};
</script>

<style scoped>
@import url("@/css/client/qna/qnaBoard.css");
.qna-tr {
  text-align: center;
}
.active {
  font-weight: bold; /* 활성화된 링크의 폰트 두께 */
  color: #2eb4c9; /* 활성화된 링크의 색상 */
  border-bottom: 3px solid #2eb4c9; /* 활성화된 링크의 밑줄 */
}
</style>
