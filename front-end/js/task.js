import { apiUrl } from "./environment/environment.js";

function getUserEmailFromToken() {
    const token = localStorage.getItem('jwt_token');
    if (token) {
        const tokenParts = token.split('.');
        if (tokenParts.length === 3) {
            const payload = JSON.parse(atob(tokenParts[1]));
            return payload.sub; 
        }
    }
    return null;
}
// ...

// ...

async function createTask() {
    const authToken = localStorage.getItem('jwt_token');
    const userEmail = getUserEmailFromToken();

    if (checkToken()) {
        try {
            const taskForm = document.getElementById('taskForm');
            const formData = new FormData(taskForm);

            const taskData = {
                description: formData.get('description'),
                title: formData.get('title'),
                priority: formData.get('priority'),
                startAt: formData.get('startAt'),
                endAt: formData.get('endAt'),
                userLogin: userEmail, // Adicione o email do usuário aqui
            };

            console.log('TaskData:', taskData); // Adicione esta linha para verificar o objeto taskData

            const response = await fetch(`${apiUrl}/tasks/create`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${authToken}`
                },
                body: JSON.stringify(taskData),
            });

            const data = await response.json();
            console.log('Response:', data); // Adicione esta linha para verificar a resposta do servidor
            console.log('Task created:', data);

            await fetchTaskList();
        } catch (error) {
            console.error('Error creating task:', error);
        }
    } else {
        console.error('Token JWT não encontrado ou inválido. O usuário não está autenticado.');
    }
}

document.getElementById('createTask').addEventListener('click', createTask);

async function fetchTaskList() {
    const userEmail = getUserEmailFromToken();

    if (userEmail) {
        try {
            const response = await fetch(`${apiUrl}/tasks/get/${userEmail}`, {
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
                
                 const deleteIcon = document.createElement('i');
    deleteIcon.className = 'fas fa-trash-alt'; // Classe do ícone de deletar
    deleteIcon.style.cursor = 'pointer';
    deleteIcon.addEventListener('click', () => deleteTask(task.id));

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

                listItem.appendChild(deleteIcon);
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

async function deleteTask(taskId) {
    if (checkToken()) {
        try {
            const response = await fetch(`${apiUrl}/tasks/${taskId}`, {
                method: 'DELETE',
                headers: {
                    'Authorization': `Bearer ${localStorage.getItem('jwt_token')}`
                }
            });

            if (response.status === 200) {
                console.log('Task deleted successfully');
                await fetchTaskList(); // Atualiza a lista de tarefas após a exclusão
            } else {
                console.error('Error deleting task');
            }
        } catch (error) {
            console.error('Error deleting task:', error);
        }
    } else {
        console.error('Token JWT não encontrado ou inválido. O usuário não está autenticado.');
    }
}
   
window.addEventListener('load', fetchTaskList);
