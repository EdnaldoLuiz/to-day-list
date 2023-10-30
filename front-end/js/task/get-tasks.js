import { apiUrl } from "../environment/environment.js";
import { getUserEmailFromToken } from "../auth/user-token.js";
import { deleteTask } from "./delete-tasks.js";

export async function fetchTaskList() {
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
            deleteIcon.addEventListener('click', async () => {
                await deleteTask(task.id);
                location.reload(); // Recarrega a página após a exclusão
            });

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