const apiUrl = 'http://localhost:8080';

// Função para verificar a existência e validade do token
function checkToken() {
    const token = localStorage.getItem('jwt_token'); // Certifique-se de que esta chave corresponda à usada para armazenar o token
    if (token) {
        // Verifique se o token está presente e é válido (pode depender de sua lógica)
        return true;
    }
    return false;
}

// Função para criar uma tarefa
async function createTask() {
    if (checkToken()) {
        try {
            const taskForm = document.getElementById('taskForm');
            const formData = new FormData(taskForm);

            const taskData = {};
            formData.forEach((value, key) => {
                taskData[key] = value;
            });

            const response = await fetch(`${apiUrl}/tasks/create`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${localStorage.getItem('jwt_token')}`
                },
                body: JSON.stringify(taskData),
            });

            const data = await response.json();
            console.log('Task created:', data);

            await fetchTaskList();
        } catch (error) {
            console.error('Error creating task:', error);
        }
    } else {
        console.error('Token JWT não encontrado ou inválido. O usuário não está autenticado.');
        // Lidar com o redirecionamento para a página de login ou exibição de mensagem de erro
    }
}

async function fetchTaskList() {
    if (checkToken()) {
        try {
            const response = await fetch(`${apiUrl}/tasks/get`, {
                headers: {
                    'Authorization': `Bearer ${localStorage.getItem('jwt_token')}`
                }
            });

            const data = await response.json();

            const tasksList = document.getElementById('tasksList');
            tasksList.innerHTML = '';

            data.forEach(task => {
                const listItem = document.createElement('li');
                listItem.className = 'taskItem';

                const checkbox = document.createElement('input');
                checkbox.type = 'checkbox';
                checkbox.className = 'taskCheckbox';

                const detailsContainer = document.createElement('div');
                detailsContainer.className = 'taskDetails';

                const title = document.createElement('span');
                title.className = 'taskTitle';
                title.textContent = task.title;

                const description = document.createElement('span');
                description.className = 'taskDescription';
                description.textContent = task.description;

                if (task.priority === 'BAIXA') {
                    listItem.classList.add('taskPriorityLow');
                } else if (task.priority === 'MEDIA') {
                    listItem.classList.add('taskPriorityMedium');
                } else if (task.priority === 'ALTA') {
                    listItem.classList.add('taskPriorityHigh');
                }

                detailsContainer.appendChild(title);
                detailsContainer.appendChild(description);

                listItem.appendChild(checkbox);
                listItem.appendChild(detailsContainer);

                tasksList.appendChild(listItem);
            });
        } catch (error) {
            console.error('Error fetching task list:', error);
        }
    } else {
        console.error('Token JWT não encontrado ou inválido. O usuário não está autenticado.');
    }
}

window.addEventListener('load', fetchTaskList);
