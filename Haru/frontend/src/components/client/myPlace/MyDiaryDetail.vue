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
              ref="d_title"
              :value="selectedDiary.diary_title"
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
              :value="selectedDiary.diary_context"
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
          <button class="big-ctlbtn delete-btn" @click="deleteDiary">
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

export default {
  data() {
    return {
      formData: new FormData(),
    };
  },
  props: {
    selectedDiary: Object,
    rDate: String,
    dStatus: String,
  },
  methods: {
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
        diary_num: this.selectedDiary.diary_num,
        user_id: this.data.id,
        diary_title: this.$refs.d_title.value,
        diary_context: this.$refs.d_context.value,
        diary_cdate: this.selectedDiary.diary_cdate
      }

      axios.post(`http://${process.env.VUE_APP_BACK_END_URL}/diary/updateDiary`, newDiaryData)
          .then(() => {
            alert("일기가 수정되었습니다.");
            // 일기 수정하면 새로운 데이터로 가져와지게 수정해야 됨
            // this.$router.push("/MyPlaceDiary");
            this.updateDStatus('read');
          })
          .catch(error => {
            console.log(error);
          })
    },

    // 일기 삭제
    deleteDiary() {
      axios.post(`http://${process.env.VUE_APP_BACK_END_URL}/diary/deleteDiary/${this.selectedDiary.diary_num}`)
          .then(() => {
            alert("일기가 삭제되었습니다.");
            // 일기 수정하면 새로운 데이터로 가져와지게
            // this.$router.push("/MyPlaceDiary");
            this.updateDStatus('read');
          })
          .catch(error => {
            console.log(error);
          })
    }
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
