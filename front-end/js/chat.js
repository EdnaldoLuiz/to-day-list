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
        try {
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

                // Criar um novo elemento div para exibir a resposta com estilo semelhante ao código antigo
                const message = document.createElement('div');
                message.innerHTML = `<strong>Assistente: </strong>${responseData.content}`;
                message.classList.add('chat-message'); // Adicione uma classe de estilo

                responseContainer.appendChild(message);
            } else {
                console.error('Erro ao enviar pergunta:', response.statusText);
            }
        } catch (error) {
            console.error('Erro ao enviar pergunta:', error);
        }
    } else {
        console.error('Token JWT não encontrado ou inválido. O usuário não está autenticado.');
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
