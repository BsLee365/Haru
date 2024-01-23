<template>
  <!-- 일기 수정 -->
  <div>
    <div class="feed-modal-wrap" id="modal-wrap" @click="$emit('close-modal')">
      <!-- 모달창 영역 -->
      <div class="mydiary-modal-container mydiary-rec-modal" @click.stop>
        <!-- 일기 날짜 -->
        <div class="diary-date-area">
          <h2 id="diary-date-h2">{{ formatCDate(myDiary.diary_cdate) }}</h2>
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
              ref="d_title"
              :value="myDiary.diary_title"
              :readonly="dStatus === 'read'"
            />
          </div>
          <div class="diary-content-box">
            <textarea
              name="diary-content"
              id="diary-content"
              placeholder="어떤 일이 있었나요?"
              maxlength="1000"
              ref="d_context"
              :value="myDiary.diary_context"
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
            @click="updateSumit()"
          >
            수정완료
          </button>
          <button class="big-ctlbtn delete-btn" @click="$emit('delete-diary', this.myDiary.diary_num)">
            삭제하기
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {onMounted, ref} from "vue";
import axios from "axios";
import {jwtDecode} from "jwt-decode";
import moment from "moment/moment";

export default {
  data() {
    return {
      formData: new FormData(),
      myDiary: {},
    };
  },
  props: {
    selectedDiary: Object,
    rDate: String,
    dStatus: String,
  },
  created() {
    // 초기 설정
    this.myDiary = this.selectedDiary;
  },
  methods: {
    // 날짜(시간) 포맷
    formatCDate(cdate) {
      return moment(cdate).format("MM월 DD일 (dddd) HH:mm");
    },

    // 수정하기 눌렀을 때
    updateDStatus(status) {
      this.$emit("update-d-status", status);
      if (status === "update") {
        this.$refs.d_title.focus();
      }
    },
    // 일기 수정하기
    updateSumit() {
      const newDiaryData = {
        diary_num: this.myDiary.diary_num,
        user_id: this.data.id,
        diary_title: this.$refs.d_title.value,
        diary_context: this.$refs.d_context.value,
        diary_cdate: this.myDiary.diary_cdate
      }

      axios.post(`http://${process.env.VUE_APP_BACK_END_URL}/diary/updateDiary`, newDiaryData)
          .then(() => {
            alert("일기가 수정되었습니다.");
            // this.$router.push("/MyPlaceDiary"); -> router에 사용하는 컴포넌트에서 등록이 되어있어야 사용 가능
            this.updateDStatus('read'); // 읽기 모드로 변경
            this.myDiary = newDiaryData; // 새 데이터로 변경
            // MyPlaceDiary 목록 업데이트
            this.$emit('update-all-list');
          })
          .catch(error => {
            console.log(error);
          })
    },
  },
  setup() {
    const isLoggedIn = ref(false); // Use ref to create reactive isLoggedIn
    const data = ref([]); // Use ref to create reactive data

    const getToken = () => {
      const token = localStorage.getItem("jwtToken");
      isLoggedIn.value = token ? true : false;
    };

    const logout = () => {
      axios
          .get(`http://${process.env.VUE_APP_BACK_END_URL}/api/auth/logout`)
          .then((res) => {
            if (res.data === "Logout") {
              localStorage.removeItem("jwtToken");
              window.location.href = "/login";
            }
          });
    };

    const decodeToken = (token) => {
      // console.log(token);
      if (token == null) return false;
      const decoded = jwtDecode(token);
      // console.log(JSON.stringify(decoded));
      data.value = decoded; // Use data.value to set the value of the ref
      return decoded;
    };

    onMounted(() => {
      getToken();
      const token = localStorage.getItem("jwtToken");
      decodeToken(token);
    });

    return { logout, data }; // Return data in the setup function
  },
};
</script>
<style scoped>
@import "@/css/client/myPlace/myDiary.css";
.big-ctlbtn {
  width: 140px;
}
</style>
