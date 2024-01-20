<template>
  <div>
    <!-- --------------------- modal Start ------------------------->
    <div class="feed-modal-wrap" id="modal-wrap" @click="$emit('close-modal')">
      <div class="feed-modal-container feed-detail" @click.stop>
        <div class="feed-detail-modal-btn">
          <!-- X 버튼 -->
          <button
            id="common-modal-close"
            @click="$emit('close-modal')"
          ></button>
        </div>
        <div class="feed-card-content-area">
          <div class="feed-card-header">
            <div class="left-header">
              <!-- 프로필 사진, 닉네임 -->
              <div class="feed-profile-area">
                <div class="feed-profile-img">
                  <a href="#"
                    ><img :src="card.profileImage" alt="프로필 이미지"
                  /></a>
                </div>
                <a href="#"
                  ><span class="feed-nickname">{{ card.nickname }}</span></a
                >
              </div>

              <!-- 큰 카테고리 -->
              <div class="feed-main-category">{{ card.mainCategory }}</div>
            </div>
            <h3 class="rec-title">여기를 추천받았어요!</h3>
          </div>
        </div>

        <div class="feed-modal">
          <div class="modal-contents">
            <!-- 이미지 Area -->
            <div class="feed-card-body-image">
              <div
                id="myCarousel"
                class="carousel slide"
                data-bs-interval="false"
              >
                <!-- 인디케이터 -->
                <div class="carousel-indicators" v-if="card.images.length > 1">
                  <button
                    v-for="(indi, midx) in card.images"
                    :key="midx"
                    type="button"
                    data-bs-target="#myCarousel"
                    :data-bs-slide-to="midx"
                    :class="{ active: midx === 0 }"
                  ></button>
                </div>

                <!-- 이미지 -->
                <div class="carousel-inner carousel-inner-m">
                  <div
                    v-for="(image, imgIdx) in card.images"
                    :key="imgIdx"
                    class="carousel-item"
                    :class="{ active: imgIdx === 0 }"
                  >
                    <img :src="image" class="d-block w-100" alt="피드 이미지" />
                  </div>
                </div>

                <!-- 이전, 다음 버튼 -->
                <button
                  class="carousel-control-prev"
                  type="button"
                  data-bs-target="#myCarousel"
                  data-bs-slide="prev"
                  v-if="card.images.length > 1"
                >
                  <span
                    class="carousel-control-prev-icon"
                    aria-hidden="true"
                  ></span>
                  <span class="visually-hidden">Previous</span>
                </button>
                <button
                  class="carousel-control-next"
                  type="button"
                  data-bs-target="#myCarousel"
                  data-bs-slide="next"
                  v-if="card.images.length > 1"
                >
                  <span
                    class="carousel-control-next-icon"
                    aria-hidden="true"
                  ></span>
                  <span class="visually-hidden">Next</span>
                </button>
              </div>
            </div>

            <!-- 본문 contents -->
            <!-- 이미지 여러개면 margin-top 조금 올리기 -->
            <div class="feed-card-content">
              <p>{{ card.content }}</p>
            </div>

            <!-- 좋아요, 댓글 버튼 -->
            <div class="card-btn">
              <div>
                <div class="heart">
                  <img class="cursor-p" src="@/img/Feed/heart.png" id="heart" />
                  <span>{{ card.likes }}</span>
                </div>
                <div class="comment">
                  <img src="@/img/Feed/comment.png" id="comment" />
                  <span>{{ card.comments }}</span>
                </div>
              </div>

              <p class="rDate">{{ card.rDate }}</p>
            </div>
          </div>

          <!-- 우측 댓글 영역 -->
          <div class="modal-comment-area">
            <!-- 추천 받은 장소 -->
            <div class="feed-recommend-area">
              <div class="rec-detail-img-area">
                <a href="{{recommend.url}}"
                  ><img class="rec-detail-img" :src="recommend.img" alt=""
                /></a>
              </div>
              <div class="rec-detail">
                <a href="{{recommend.url}}">
                  <p class="rec-detail-title">{{ recommend.name }}</p>
                  <p>{{ recommend.address }}</p>
                </a>
                <p class="rec-hash-area">
                  <span
                    class="rec-hash cursor-p"
                    v-for="(hash, hidx) in recommend.hashtag"
                    :key="hidx"
                    >#{{ hash }}</span
                  >
                </p>
              </div>
            </div>

            <!-- 댓글 창 -->
            <div class="modal-comment-bg">
              <div class="comment-area1">
                <div
                  class="modal-comment"
                  v-for="(com, i) in comments"
                  :key="i"
                >
                  <div class="m-profile">
                    <a href="#"
                      ><img class="m-profile-img" :src="com.profileImage"
                    /></a>
                    <a href="#"
                      ><span class="m-nickname">{{ com.nickname }}</span></a
                    >
                    <span class="m-cDate">{{ com.cDate }}</span>
                  </div>

                  <div class="m-comment-area">
                    <div class="m-comment">
                      <p>{{ com.comment }}</p>
                    </div>
                    <img class="cursor-p" src="@/img/Feed/report.png" />
                  </div>
                </div>
              </div>

              <div class="comment-area2">
                <div id="comment-form">
                  <textarea id="commentText" cols="37" rows="2"></textarea>
                  <button class="send-comment">전송</button>
                </div>
              </div>
            </div>

            <!-- 수정하기, 삭제하기 버튼 => 본인 게시글일 경우 -->
            <div class="card-ctrl-btn-area">
              <button class="card-ctrl-btn update">수정</button>
              <button class="card-ctrl-btn delete">삭제</button>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!------------------------ modal End ------------------------->
  </div>
</template>

<script>
export default {
  name: "feedDetail",
  data() {
    return {
      card: {
        profileImage: require("@/img/Feed/no_profile.png"),
        uid: "abc",
        profileLink: "#",
        nickname: "닉네임",
        mainCategory: "액티비티",
        images: [
          require("@/img/Feed/feed1.png"),
          require("@/img/Feed/feed3.jpg"),
          require("@/img/Feed/feed2.jpg"),
        ],
        content:
          "오늘 정말 좋았던거 같은데... 뭐 별거 없었다... 하지만 야경은 진짜 멋있었다 오늘 정말 좋았던거 같은데... 뭐 별거 없었다... 하지만 야경은 진짜 멋있었다 꼭 또가고 싶어졌다",
        likes: 22,
        rDate: "2023-12-26",
        comments: 5,
      },
      comments: [
        {
          profileImage: require("@/img/Feed/no_profile.png"),
          uid: "abc",
          nickname: "이이이",
          cDate: "2023-12-30",
          comment: "저도 가고싶어요!",
        },
        {
          profileImage: require("@/img/Feed/no_profile.png"),
          uid: "abcd",
          nickname: "김기기김",
          cDate: "2023-12-30",
          comment:
            "와, 정말로 그렇죠! 여행은 새로운 경험이자 모험이기도 해서 놀라운 순간과 추억을 만들어주죠. 그 곳에서 새로운 문화와 사람들을 만나고 색다른 경험을 즐기며 여행하는 건 정말 특별한 일이에요. 혹시 어디를 생각하고 있나요? 어느 지역의 자연이나 도시의 매력에 빠져들었을까요? 솔직히 여행은 항상 기대되고 설레이는 순간이죠!",
        },
        {
          profileImage: require("@/img/Feed/no_profile.png"),
          uid: "abc",
          nickname: "아아앙",
          cDate: "2023-12-29",
          comment: "저는 좀1",
        },
        {
          profileImage: require("@/img/Feed/no_profile.png"),
          uid: "abcd",
          nickname: "아아앙",
          cDate: "2023-12-28",
          comment: "저는 좀2",
        },
        {
          profileImage: require("@/img/Feed/no_profile.png"),
          uid: "abc",
          nickname: "아아앙",
          cDate: "2023-12-27",
          comment: "저는 좀3",
        },
      ],
      recommend: {
        name: "신논현역 딸부자네 불백",
        img: require("@/img/Feed/bul.png"),
        hashtag: ["푸드", "맛집", "고기"],
        url: "https://www.naver.com/",
        address: "서울시 강남구 꼬마빌딩 1층",
      },
      modal_Check: false,
    };
  },
  methods: {},
};
</script>

<style scoped>
@import "@/css/client/feed/feedDetail.css";
</style>
