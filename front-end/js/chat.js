const chatForm = document.getElementById('chatForm');
const promptInput = document.getElementById('promptInput');
const responseContainer = document.getElementById('responseContainer');

async function sendChat() {
    const prompt = promptInput.value;

    try {
        const response = await fetch('http://localhost:8080/chat', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ prompt }),
        });

        if (!response.ok) {
            throw new Error('Erro na solicitação');
        }

        const responseData = await response.json();

        const message = document.createElement('div');
        message.innerHTML = `<strong>Assistente: </strong>${responseData.content}`;
        responseContainer.appendChild(message);
    } catch (error) {
        console.error('Erro:', error.message);
    }
}

// Adicione um evento para prevenir o envio do formulário e chamar a função sendChat
chatForm.addEventListener('submit', function (event) {
    event.preventDefault();
    sendChat();
    promptInput.value = '';
});
