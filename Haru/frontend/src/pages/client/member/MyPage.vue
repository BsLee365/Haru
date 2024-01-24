<template>
  <div class="container1">
    <!-- 프로필 사진 업데이트 모달 창 -->
    <UpdateProfileImgModal
      :mStatus="profileModalStatus"
      @modalClose="closeModal"
    />

    <!-- 컨텐츠 -->
    <div class="mypage-box">
      <!-- title -->
      <div class="main-title">
        <h2>마이페이지</h2>
      </div>
      <div class="mypage-two-box">
        <div class="mypage-left-box">
          <ul>
            <li id="profileArea">
              <div class="profileImgBg cursor-p" @click="openModal">
                <img src="@/img/FaceRegistration/camera.png" alt="" />
                <p>프로필 수정</p>
              </div>
              <img src="@/assets/bgImage/type3.png" class="profileImg" />
            </li>
            <li>
              <div class="nickname-box">
                <span id="nickname-tag">{{ userNickname }}</span>
              </div>
              <div class="username-box">
                <span>별명을 가진</span
                ><span id="username-tag">{{ userName }} 님</span>
              </div>
            </li>
            <li class="user-activity-info">
              <div class="commentCnt-box">
                <ul>
                  <div>작성된 댓글 개수</div>
                  <div class="div-tag">
                    <span><img src="@/img/mypage/image 95.png" /></span>
                    <span>{{ commentCount }}</span>
                  </div>
                </ul>
              </div>

              <div class="commentCnt-box">
                <ul>
                  <div>받은 좋아요 개수</div>
                  <div class="div-tag">
                    <span><img src="@/img/mypage/image 94.png" /></span>
                    <span>{{ likeCount }}</span>
                  </div>
                </ul>
              </div>
            </li>
            <div class="update-mydata-box">
              <a
                class="big-ctlbtn update-btn"
                href="/userConfirmation"
                id="updateMyDataBtn"
                >내 정보 수정</a
              >
            </div>
          </ul>
        </div>
        <div class="mypage-right-box">
          <div class="alarm-area">
            <h3>알림</h3>
            <div class="alarm-container">
              <ul v-for="(alarm, i) in alarmList" :key="i">
                <li>
                  <div
                    v-if="alarm.like != null"
                    @click="
                      toMyFeed(data.nickname, alarm.like.feed_num.feed_num)
                    "
                  >
                    <img
                      class="alarm-img"
                      src="@/assets/icon/alarm/alarm_heart_icon.png"
                    />
                    <span id="comment-content"
                      >{{ alarm.like.feed_like_by.nickname }}님이 좋아요를
                      눌렀습니다.</span
                    >
                    <span class="alarm-time">
                      <img src="@/assets/icon/time_icon.png" alt="" />
                      {{ this.getTimeString(alarm.alarm_cdate) }}
                    </span>
                  </div>
                  <div
                    v-else-if="alarm.feed_comment != null"
                    @click="
                      toMyFeed(
                        data.nickname,
                        alarm.feed_comment.feed_num.feed_num
                      )
                    "
                  >
                    <img
                      class="alarm-img"
                      src="@/assets/icon/alarm/alarm_comment_icon.png"
                    />
                    <span id="comment-content"
                      >{{ alarm.feed_comment.user_id.nickname }}님이 게시물에
                      댓글을 작성하였습니다.</span
                    >
                    <span class="alarm-time">
                      <img src="@/assets/icon/time_icon.png" alt="" />
                      {{ this.getTimeString(alarm.alarm_cdate) }}
                    </span>
                  </div>
                  <div v-else>
                    <img
                      class="alarm-img"
                      src="@/assets/icon/alarm/alarm_basic_icon.png"
                    />
                    <span id="comment-content"
                      >{{ alarm.like.feed_like_by.nickname }}님이 Qna에 답변을
                      작성하였습니다.</span
                    >
                    <span class="alarm-time">
                      <img src="@/assets/icon/time_icon.png" alt="" />
                      {{ this.getTimeString(alarm.alarm_cdate) }}
                    </span>
                  </div>
                </li>
              </ul>
            </div>
          </div>
        </div>
      </div>
    </div>
    <ChatBot />
  </div>
</template>
<script>
import axios from "axios";
import { ref, onMounted } from "vue";
import { jwtDecode } from "jwt-decode";
import UpdateProfileImgModal from "@/components/client/member/UpdateProfileImgModal.vue";
import ChatBot from "@/components/client/layout/ChatBot.vue";

export default {
  name: "MyPage",
  data() {
    return {
      isLoggedIn: false,
      AccessToken: "",
      profileModalStatus: false,
      userId: "",
      userName: "",
      userNickname: "",
      alarmList: [],
      likeCount: 0,
      commentCount: 0,
    };
  },
  created() {
    // 페이지가 로드될 때 초기 이미지 설정
    this.bgImage();
  },
  mounted() {
    console.log(this.data.id);
    this.getMyPageData();
  },
  components: {
    UpdateProfileImgModal,
    ChatBot,
  },
  methods: {
    // 해당 화면 Background 이미지 설정
    bgImage() {
      var newImage = "type1";
      this.$emit("bgImage", newImage);
    },
    openModal() {
      this.profileModalStatus = true;
    },

    closeModal() {
      this.profileModalStatus = false;
    },
    getMyPageData() {
      var formData = new FormData();
      const userId = this.data.id;

      formData.append("userId", userId);
      axios
        .post(
          `http://${process.env.VUE_APP_BACK_END_URL}/getMyPageData`,
          formData
        )
        .then((res) => {
          console.log(res);
          this.userId = res.data.member.user_id;
          this.userName = res.data.member.name;
          this.userNickname = res.data.member.nickname;
          this.likeCount = res.data.likeCount;
          this.commentCount = res.data.commentCount;
          this.alarmList = res.data.alarmList;

          this.alarmList.sort((a, b) => {
            const dateA = new Date(a.alarm_cdate);
            const dateB = new Date(b.alarm_cdate);

            return dateB - dateA;
          });
        });
    },
    getTimeString(isoTimeString) {
      const currentTime = new Date();
      const targetTime = new Date(isoTimeString);
      const timeDifference = currentTime - targetTime;

      const minutes = Math.floor(timeDifference / (1000 * 60));

      if (minutes < 1) {
        return "방금 전";
      } else if (minutes < 60) {
        return `${minutes}분 전`;
      } else if (minutes < 24 * 60) {
        const hours = Math.floor(minutes / 60);
        return `${hours}시간 전`;
      } else {
        const options = {
          year: "numeric",
          month: "2-digit",
          day: "2-digit",
          hour: "2-digit",
          minute: "2-digit",
        };
        return targetTime.toLocaleString("ko-KR", options);
      }
    },
    toMyFeed(nickname, feedNum) {
      window.location.href =
        "/feed?nickname=" + nickname + "&feedNum=" + feedNum;
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
          if (res.data == "Logout") {
            localStorage.removeItem("jwtToken");
            window.location.href = "/login";
          }
        });
    };

    const decodeToken = (token) => {
      if (token == null) return false;
      const decoded = jwtDecode(token);
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
@import url("@/css/client/member/mypage.css");
</style>
