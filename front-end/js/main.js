import { sendChat } from "./chat.js";

document.getElementById('sendChatButton').addEventListener('click', () => {
    console.log('Botão clicado');
    sendChat();
});
