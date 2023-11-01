const apiUrls = 'http://localhost:8080';

function checkToken() {
    const token = localStorage.getItem('jwt_token');
    if (token) {
        return true;
    }
    return false;
}

async function sendChatQuestion(question) {
    if (checkToken()) {
        const requestData = {
            prompt: question
        };

        const response = await fetch(`${apiUrls}/chat`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${localStorage.getItem('jwt_token')}`
            },
            body: JSON.stringify(requestData),
        });

        if (response.ok) {
            const responseData = await response.json();
            const responseContainer = document.getElementById('responseContainer');

            const message = document.createElement('div');
            message.innerHTML = `<strong class="chat__helper"><img src="/assets/imgs/to-do-list-white.png"><br>Assistente: </strong>${responseData.content}`;
            message.classList.add('chat-message');

            responseContainer.appendChild(message);
        }
    }
}

const chatForm = document.getElementById('chatForm');
chatForm.addEventListener('submit', function (event) {
    event.preventDefault();
    const promptInput = document.getElementById('promptInput');
    const question = promptInput.value;
    sendChatQuestion(question);
    promptInput.value = '';
});
