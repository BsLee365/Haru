<template>
	
    <div class="chatbot">
<!--        <button type="button" @click="toggleChatBot" id="chatBotToggle">-->
<!--            <i class="fa"-->
<!--        </button>-->
<!--		<iframe v-if="isChatBotOpen" allow="microphone;" width="400" height="520" src="https://console.dialogflow.com/api-client/demo/embedded/cfbe19c0-8dee-4ca6-ac06-95027bd77799"> </iframe>-->
	</div>
</template>
<script>
export default {
	name: "chatbot",
    data() {
        return {
            isChatBotOpen: false,
        };
    },
    methods: {
        toggleChatBot() {
            this.isChatBotOpen = !this.isChatBotOpen;
            console.log(this.isChatBotOpen);
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
    margin-right: auto;
}
</style>
