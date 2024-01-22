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
                <span id="nickname-tag">{{ data.nickname }}</span>
              </div>
              <div class="username-box">
                <span>별명을 가진</span
                ><span id="username-tag">{{ data.username }} 님</span>
              </div>
            </li>
            <li class="user-activity-info">
              <div class="commentCnt-box">
                <ul>
                  <div>누적 댓글 개수</div>
                  <div class="div-tag">
                    <span><img src="@/img/mypage/image 95.png" /></span>
                    <span>123</span>
                  </div>
                </ul>
              </div>

              <div class="commentCnt-box">
                <ul>
                  <div>누적 좋아요 개수</div>
                  <div class="div-tag">
                    <span><img src="@/img/mypage/image 94.png" /></span>
                    <span>12</span>
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
              <ul>
                <li>
                  <div>
                    <img
                      class="alarm-img"
                      src="@/assets/icon/alarm/alarm_heart_icon.png"
                    />
                    <span id="comment-content">댓글 내용</span>
                    <span class="alarm-time"
                      ><img
                        src="@/assets/icon/time_icon.png"
                        alt=""
                      />10분전</span
                    >
                  </div>
                </li>
                <li>
                  <div>
                    <img
                      class="alarm-img"
                      src="@/assets/icon/login/kakao_login_icon.png"
                    />
                    <span id="comment-content">댓글 내용</span>
                    <span class="alarm-time"
                      ><img
                        src="@/assets/icon/time_icon.png"
                        alt=""
                      />40분전</span
                    >
                  </div>
                </li>
                <li>
                  <div>
                    <img
                      class="alarm-img"
                      src="@/assets/icon/alarm/alarm_basic_icon.png"
                    />
                    <span id="comment-content">댓글 내용</span>
                    <span class="alarm-time">23.12.27 1:03</span>
                  </div>
                </li>
                <li>
                  <div>
                    <img
                      class="alarm-img"
                      src="@/assets/icon/alarm/alarm_heart_icon.png"
                    />
                    <span id="comment-content">댓글 내용</span>
                    <span class="alarm-time">23.12.26 1:03</span>
                  </div>
                </li>
                <li>
                  <div>
                    <img
                      class="alarm-img"
                      src="@/assets/icon/alarm/alarm_comment_icon.png"
                    />
                    <span id="comment-content">댓글 내용</span>
                    <span class="alarm-time">23.12.25 1:03</span>
                  </div>
                </li>
                <li>
                  <div>
                    <img
                      class="alarm-img"
                      src="@/assets/icon/alarm/alarm_comment_icon.png"
                    />
                    <span id="comment-content">댓글 내용</span>
                    <span class="alarm-time">23.12.24 1:03</span>
                  </div>
                </li>
                <li>
                  <div>
                    <img
                      class="alarm-img"
                      src="@/assets/icon/alarm/alarm_basic_icon.png"
                    />
                    <span id="comment-content">댓글 내용</span>
                    <span class="alarm-time">23.12.23 1:03</span>
                  </div>
                </li>
              </ul>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import axios from "axios";
import { ref, onMounted } from "vue";
import { jwtDecode } from "jwt-decode";
import UpdateProfileImgModal from "@/components/client/member/UpdateProfileImgModal.vue";

export default {
  name: "MyPage",
  data() {
    return {
      isLoggedIn: false,
      AccessToken: "",
      profileModalStatus: false,
    };
  },
  created() {
    // 페이지가 로드될 때 초기 이미지 설정
    this.bgImage();
  },
  components: {
    UpdateProfileImgModal,
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
  },
  setup() {
    const isLoggedIn = ref(false);
    const data = ref([]);
    const getToken = () => {
      const token = localStorage.getItem("jwtToken");
      isLoggedIn.value = token ? true : false;
    };

    // 로그아웃 메서드
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

    // 토큰 디코딩 메서드
    const decodeToken = (token) => {
      if (token == null) return false;
      const decoded = jwtDecode(token);
      data.value = decoded;
      return decoded;
    };

    // 페이지가 로드될 때 실행되는 함수
    onMounted(() => {
      getToken();
      const token = localStorage.getItem("jwtToken");
      decodeToken(token);
    });

    return { logout, data };
  },
};
</script>
<style scoped>
@import url("@/css/client/member/mypage.css");
</style>
