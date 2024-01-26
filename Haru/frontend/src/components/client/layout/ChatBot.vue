<template>
	<div class="chatbot">
		<df-messenger 
            intent="" 
            chat-title="Haru" 
            agent-id="05b8509c-074f-43f2-873d-c21558194987" 
            language-code="ko" 
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
		const script = document.createElement("script");
		script.src = "https://www.gstatic.com/dialogflow-console/fast/messenger/bootstrap.js?v=1";
		document.head.appendChild(script);
	},
	methods: {
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
