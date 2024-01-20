<template>
  <div class="container1">
    <div class="qna-container">
      <div class="faq-container">
        <h2 class="faq-title">Q&A 페이지</h2>
      </div>
      <div class="nav-bar">
        <!-- exact-active-class : Vue Router에서 사용되는 속성 -->
        <!-- router-link가 현재 활성화된(exact match) 상태일 때 적용할 클래스를 지정하는 데 사용 -->
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
            <th>작성자</th>
            <th class="qna-tr">작성일</th>
          </tr>
        </thead>
        <tbody>
          <tr
            v-for="item in paginatedQnA"
            :key="item.no"
            :class="{
              noticeTR: item.category === '공지사항',
            }"
          >
            <!-- category 가 공지사항인게 먼저 정렬된 데이터 받아야됨 -->
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
            <td class="qna-tr">{{ item.writer }}</td>
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
              placeholder="제목으로 검색하세요"
            />
            <button type="button" class="qna-search-btn" @click="onSearch">
              <img src="@/img/Feed/search_btn.png" alt="" />
            </button>
          </div>
        </form>
        <div>
          <button
            type="button"
            class="qna-write insert-btn big-ctlbtn"
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

      AllQna: [
        {
          no: 4,
          category: "공지사항",
          title: "배송 문의 드립니다",
          writer: "관리자",
          date: "2020-11-12",
        },
        {
          no: 3,
          category: "공지사항",
          title: "부적절한 블라블라 내용 삭제 요청",
          writer: "관리자",
          date: "2017-11-22",
        },
        {
          no: 2,
          category: "공지사항",
          title: "배송 문의 드립니다",
          writer: "관리자",
          date: "2020-11-12",
        },
        {
          no: 1,
          category: "공지사항",
          title: "부적절한 블라블라 내용 삭제 요청",
          writer: "관리자",
          date: "2017-11-22",
        },
        {
          no: 16,
          category: "이용문의",
          title: "배송 문의 드립니다",
          writer: "고구마",
          progress: "미답변",
          date: "2020-11-12",
        },
        {
          no: 15,
          category: "이용문의",
          title: "부적절한 블라블라 내용 삭제 요청",
          writer: "고구마",
          progress: "미답변",
          date: "2017-11-22",
        },
        {
          no: 14,
          category: "이용문의",
          title: "배송 문의 드립니다",
          writer: "고구마",
          progress: "미답변",
          date: "2020-11-12",
        },
        {
          no: 13,
          category: "이용문의",
          title: "부적절한 블라블라 내용 삭제 요청",
          writer: "고구마",
          progress: "미답변",
          date: "2017-11-22",
        },
        {
          no: 12,
          category: "이용문의",
          title: "배송 문의 드립니다",
          writer: "고구마",
          progress: "완료",
          date: "2020-11-12",
        },
        {
          no: 11,
          category: "이용문의",
          title: "부적절한 블라블라 내용 삭제 요청",
          writer: "고구마",
          progress: "완료",
          date: "2017-11-22",
        },
        {
          no: 10,
          category: "이용문의",
          title: "배송 문의 드립니다",
          writer: "고구마",
          progress: "완료",
          date: "2020-11-12",
        },
        {
          no: 9,
          category: "이용문의",
          title: "부적절한 블라블라 내용 삭제 요청",
          writer: "고구마",
          progress: "완료",
          date: "2017-11-22",
        },
        {
          no: 8,
          category: "이용문의",
          title: "배송 문의 드립니다",
          writer: "고구마",
          progress: "완료",
          date: "2020-11-12",
        },
        {
          no: 7,
          category: "이용문의",
          title: "부적절한 블라블라 내용 삭제 요청",
          writer: "고구마",
          progress: "완료",
          date: "2017-11-22",
        },
        {
          no: 6,
          category: "이용문의",
          title: "배송 문의 드립니다",
          writer: "고구마",
          progress: "완료",
          date: "2020-11-12",
        },
        {
          no: 5,
          category: "이용문의",
          title: "부적절한 블라블라 내용 삭제 요청",
          writer: "고구마",
          progress: "완료",
          date: "2017-11-22",
        },
        // ... more items
      ],
    };
  },
  computed: {
    paginatedQnA() {
      const start = (this.currentPage - 1) * this.pageSize;
      const end = start + this.pageSize;
      return this.AllQna.slice(start, end);
    },
    pageCount() {
      return Math.ceil(this.AllQna.length / this.pageSize);
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
/* TAB 활성화 표시 */
.active {
  font-weight: bold; /* 활성화된 링크의 폰트 두께 */
  color: #2eb4c9; /* 활성화된 링크의 색상 */
  border-bottom: 3px solid #2eb4c9; /* 활성화된 링크의 밑줄 */
}
</style>
