<template>
	<div class="chatbot">
		<!-- <button type="button" @click="toggleChatBot" id="chatBotToggle" :class="{ chatBotOn: isChatBotOpen }"></button>
		<iframe v-if="isChatBotOpen" ref="chatBot" allow="microphone;" width="400" height="520" src="https://console.dialogflow.com/api-client/demo/embedded/cfbe19c0-8dee-4ca6-ac06-95027bd77799"> </iframe> -->
		<!-- <div class="chat-container">
			<div class="chat-messages" ref="chatMessages">
				<div v-for="(message, index) in chatMessages" :key="index" class="chat-message">
					{{ message }}
				</div>
			</div>
			<div class="user-input">
				<input v-model="userInput" @keyup.enter="sendMessage" placeholder="Type your message..." />
				<button @click="sendMessage">Send</button>
			</div>
		</div> -->
		<df-messenger 
            intent="" 
            chat-title="Haru" 
            agent-id="05b8509c-074f-43f2-873d-c21558194987" 
            language-code="ko" 
            chat-icon="https://lh3.google.com/u/0/d/1Dr_Rh5uQcnIY2ZnP4jsqMLdW5nKh_ija=w965-h914-iv1"
        ></df-messenger>
	</div>
</template>
<script>
export default {
	name: "chatbot",
	data() {
		return {
			isChatBotOpen: false,
			chatMessages: [],
			userInput: "",
		};
	},
	mounted() {
		// 페이지가 로드될 때마다 새로운 세션 ID 생성
		this.generateSessionId();
		const script = document.createElement("script");
		script.src = "https://www.gstatic.com/dialogflow-console/fast/messenger/bootstrap.js?v=1";
		document.head.appendChild(script);
	},
	methods: {
		toggleChatBot() {
			this.isChatBotOpen = !this.isChatBotOpen;
			console.log(this.isChatBotOpen);
		},
		generateSessionId() {
			// 세션 ID를 생성하는 로직
			this.sessionId = "unique-session-id-" + Date.now();
		},
	},
};
</script>
<style scoped>
.chatbot {
	display: flex;
	flex-direction: column;
	position: fixed;
	bottom: 145px;
	right: 0;
	z-index: 9999;
}

#chatBotToggle {
	width: 50px;
	height: 50px;
	background-color: #fff;
	border-radius: 50%;
	border: 1px solid #ddd;
	margin: 10px;
	cursor: pointer;
	background-image: url(https://i.imgur.com/6zeveJF);
	background-size: 50%;
	background-repeat: no-repeat;
	background-position: center;
	margin-right: 100%;
	right: 5px;
}

.chatBotOn {
	left: 335px;
	bottom: -75px;
	z-index: 1;
}

df-messenger {
    /* 열기버튼, 채팅 제목표시줄 색상 */
    --df-messenger-button-titlebar-color : #fbebe3;
    /* 채팅창 배경 색상 */
    --df-messenger-chat-background-color : #fff1e6;
    /* 사용자 메시지 배경 색상 */
    --df-messenger-user-message : #ffffff;
    /* df-messenger-button-titlebar-font-color 채팅창 제목표시줄 폰트 색상 */
    /* df-messenger-bot-message 봇 메시지 색상 */
    /* df-messenger-font-color 메시지 색상 */
    /* df-messenger-input-box-color 텍스트 입력 상자 배경 색상  */
    /* df-messenger-input-font-color 텍스트 입력 상자 글꼴 색상 */
    /* df-messenger-send-icon 텍스트 전송 아이콘 색상 */
    /* df-messenger-input-placeholder-color 텍스트 입력 상자 플레이스 홀더 색상 */
    /* df-messenger-minimized-chat-close-icon-color 닫기 아이콘 색상 */
}
</style>
