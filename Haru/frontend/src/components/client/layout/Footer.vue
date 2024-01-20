<template>
  <div class="footer-container">
    <!-- <footer
      class="d-flex flex-wrap justify-content-between align-items-center py-3 my-4 border-top"
    > -->
    <footer class="footer-area">
      <!-- 로고 이미지 -->
      <div class="img-area">
        <img src="@/assets/logo/logo_white.png" alt="" />
      </div>

      <div class="info-area">
        <p>사업자 번호: 120910-30-230923</p>
        <p>주소: 서울시 강남구 테헤란로</p>
        <p>전화번호: 02-1234-1234</p>
      </div>

      <div class="coperation-info-area">
        <p>Haru Coperation</p>
        <p>Copyright © 하루의 여울 2024</p>
      </div>
    </footer>
  </div>
</template>
<script>
export default {
  name: "Footer",
};
</script>
<style scoped>
/* footer 전체 컨테이너 */
.footer-container {
  background-color: #333;
  margin-bottom: 0;
  padding: 40px 228px 40px;
}

.footer-area {
  display: flex;
  width: 700px;
  align-items: center;
  justify-content: space-between;
}

.info-area {
  height: 60px;
  display: flex;
  justify-content: space-between;
  flex-direction: column;
}

.coperation-info-area {
  top: -3px;
  height: 65px;
  display: flex;
  justify-content: space-between;
  flex-direction: column;
}

.coperation-info-area p:first-child {
  font-size: 24px;
}

/* 로고 이미지 */
.img-area > img {
  width: 130px;
}

/* 전체 내용 p태그 */
p {
  color: #bdbdbd;
  font-size: 13px;
  font-weight: 300;
}
</style>
