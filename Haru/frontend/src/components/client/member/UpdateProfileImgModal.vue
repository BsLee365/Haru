<template>
	<!-- 프로필 사진 변경 모달창 -->
	<div v-if="mStatus" class="FindMyAccount-modal" @click="$emit('modalClose')">
		<div class="findMyAccount-modal-content" @click.stop>
			<div class="feed-detail-modal-btn">
				<!-- X 버튼 -->
				<button id="common-modal-close" @click="$emit('modalClose')"></button>
			</div>

			<div class="findMyAccount-title-area">
				<h2>프로필 사진 수정</h2>
			</div>

			<div class="findModalContent">
				<form autocomplete="off" class="up-profile-modal-form">
					<div id="up-profile-area">
						<div class="up-profileImgBg cursor-p">
							<label class="up-profileImgBg cursor-p">
								<input type="file" style="display: none" @change="handleFileChange" />
							</label>
							<img src="@/img/FaceRegistration/camera.png" alt="" />
							<p>프로필 수정</p>
						</div>
						<img v-if="!UpdatedProfileImg" :src="this.profileImg" class="profileImg" />
						<img v-else :src="this.UpdatedProfileImg" class="profileImg" />
					</div>

					<div class="deleteMyInfo-btn-area">
						<button class="big-ctlbtn cancle-btn" id="deleteMyInfo-cancel" @click="$emit('modalClose')">뒤로가기</button>
						<button type="button" @click="updateProfileImg" class="big-ctlbtn update-btn" id="deleteMyInfo-submit">수정하기</button>
					</div>
				</form>
			</div>
		</div>
	</div>
</template>
<script>
import axios from "axios";
import { ref, onMounted } from "vue";
import { jwtDecode } from "jwt-decode";
export default {
	name: "FindByIdModal",
	data() {
		return {
			UpdatedProfileImg: null,
      selectedFile: null,
		};
	},
	props: {
		mStatus: Boolean,
		profileImg: String,
	},
	methods: {
		handleFileChange(event) {
			const selectedFile = event.target.files[0];
      console.log(selectedFile);
			if (selectedFile) {
				this.previewProfileImage(selectedFile);
			}
		},
		previewProfileImage(file) {
      this.selectedFile = file;
			const reader = new FileReader();
			reader.onload = (e) => {
				this.UpdatedProfileImg = e.target.result;
        console.log(this.UpdatedProfileImg);
			};
			reader.readAsDataURL(file);
		},
		updateProfileImg() {
			var formData = new FormData();
			var selectedFile = null;
			if (this.selectedFile != null) {
				selectedFile = this.selectedFile
			} else {
				alert("파일을 선택해주세요.");
				return;
			}
			formData.append("file", selectedFile);
			formData.append("userId", this.data.id);

			axios
				.post(`http://${process.env.VUE_APP_BACK_END_URL}/member/UpdateProfileImg`, formData, {
					headers: {
						"Content-Type": "multipart/form-data",
					},
				})
				.then((res) => {
					if (res.data == "success") {
						alert("프로필 사진이 변경되었습니다.");
						this.$emit("modalClose");
					} else {
						alert("프로필 사진 변경에 실패하였습니다.");
					}
				})
				.catch((err) => {
					console.log(err);
				});

			// 모달 닫기
			this.isModalOpen = false;
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
			axios.get(`http://${process.env.VUE_APP_BACK_END_URL}/api/auth/logout`).then((res) => {
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
.info-input-container {
	padding-bottom: 30px;
}
.btn-area {
	width: fit-content;
	margin: 0 auto;
}
.deleteMyInfo-btn-area {
	width: 93%;
}
</style>
