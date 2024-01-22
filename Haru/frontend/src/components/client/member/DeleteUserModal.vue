<template>
  <!-- 탈퇴하기 모달 -->
  <div
    class="deleteUser-modal"
    v-if="deleteMyInfoModal"
    @click="$emit('modalClose')"
  >
    <div class="deleteUser-modal-content" @click.stop>
      <div class="feed-detail-modal-btn">
        <!-- X 버튼 -->
        <button id="common-modal-close" @click="$emit('modalClose')"></button>
      </div>

      <div class="deleteMyInfo-title-area">
        <h2>정말.... 탈퇴 하실건가요</h2>
      </div>

      <div class="deleteMyInfo-modal-body">
        <img src="@/img/deleteUserModal/image106.png" />
        <div class="delete-text-commend">
          <p>
            탈퇴 선택시, 계정은 삭제되며
            <span style="color: red">복구 되지 않습니다.</span>
          </p>
          <div class="delete-commend-input-box">
            <span>탈퇴하시기 전에 아이디를 입력해주세요.</span>
          </div>
          <div>
            <input
              class="input-text"
              type="text"
              id="delete-id"
              placeholder="아이디"
            />
          </div>
        </div>
      </div>
      <div class="deleteMyInfo-btn-area">
        <button
          class="big-ctlbtn cancle-btn"
          id="deleteMyInfo-cancel"
          @click="$emit('modalClose')"
        >
          뒤로가기
        </button>
        <button
          class="big-ctlbtn delete-btn"
          id="deleteMyInfo-submit"
          @click="deleteUser"
        >
          탈퇴하기
        </button>
      </div>
    </div>
  </div>
</template>
<script>
import axios from "axios";

export default {
  name: "DeleteMyInfoModal",
  data() {
    return {};
  },
  props: {
    deleteMyInfoModal: Boolean,
    userId: String,
  },
  methods: {
    // 탈퇴하기 메소드
    deleteUser() {
      const userId = document.getElementById("delete-id").value;
      if (userId == this.userId) {
        axios
          .delete(
            `http://${process.env.VUE_APP_BACK_END_URL}/member/delete/${userId}`
          )
          .then((res) => {
            console.log(res);
            if (res.data == 1) {
              localStorage.removeItem("jwtToken");
              this.$router.push("/login");
            }
          });
      } else {
        alert("아이디를 확인해주세요.");
      }
    },
  },
};
</script>
<style scoped>
.input-text {
  display: inline-block;
  margin-top: 10px;
}
</style>
