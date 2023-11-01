import { apiUrl } from "./environment/environment.js";
import { getUserEmailFromToken } from "./auth/user-token.js";

async function createTask() {
    const authToken = localStorage.getItem('jwt_token');
    const userEmail = getUserEmailFromToken();

    if (checkToken()) {
        const taskForm = document.getElementById('taskForm');
        const formData = new FormData(taskForm);

        const taskData = {
            description: formData.get('description'),
            title: formData.get('title'),
            priority: formData.get('priority'),
            startAt: formData.get('startAt'),
            endAt: formData.get('endAt'),
            userLogin: userEmail,
        };

        const response = await fetch(`${apiUrl}/tasks/create`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${authToken}`
            },
            body: JSON.stringify(taskData),
        });

        await response.json();
        await fetchTaskList();
    }
}
document.getElementById('createTask').addEventListener('click', createTask);

async function fetchTaskList() {
    const userEmail = getUserEmailFromToken();

    if (userEmail) {
        const response = await fetch(`${apiUrl}/tasks/list/${userEmail}`, {
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
            deleteIcon.className = 'fas fa-trash-alt';
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
    }
}

async function deleteTask(taskId) {
    if (checkToken()) {
        const response = await fetch(`${apiUrl}/tasks/${taskId}`, {
            method: 'DELETE',
            headers: {
                'Authorization': `Bearer ${localStorage.getItem('jwt_token')}`
            }
        });

        if (response.status === 200) {
            console.log('Task deleted successfully');
            await fetchTaskList();
        } else {
            console.error('Error deleting task');
        }
    }
}

window.addEventListener('load', fetchTaskList);