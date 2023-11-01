import { apiUrl } from "../environment/environment.js";
import { getUserEmailFromToken } from "../auth/user-token.js";
import { deleteTask } from "./delete-tasks.js";

export async function fetchTaskList() {
    const userEmail = getUserEmailFromToken();
    if (!userEmail) return;

    const response = await fetch(`${apiUrl}/tasks/list/${userEmail}`, {
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('jwt_token')}`
        }
    });

    const data = await response.json();
    const tasksList = document.getElementById('tasksList');
    tasksList.innerHTML = '';

    data.forEach(task => {
        const { title, description, startAt, endAt, priority, id } = task;

        const listItem = document.createElement('li');
        listItem.className = 'taskItem';

        listItem.innerHTML = `
            <i class="fas fa-trash-alt icon-remove icons" style="cursor: pointer;"></i>
            <i class="fa-solid fa-pen-to-square icon-update icons" style="cursor: pointer;"></i>
            <input type="checkbox" class="taskCheckbox">
            <div class="taskDetails">
                <span class="taskTitle">${title}</span>
                <span class="taskDescription">${description}</span>
            </div>
            <div class="taskDateContainer">
                <span class="taskTime">${startAt} - ${endAt}</span>
                <i class="fa-regular fa-clock time-icon"></i>
            </div>
        `;

        if (priority === 'BAIXA') {
            listItem.classList.add('taskPriorityLow');
        } else if (priority === 'MEDIA') {
            listItem.classList.add('taskPriorityMedium');
        } else if (priority === 'ALTA') {
            listItem.classList.add('taskPriorityHigh');
        }

        const deleteIcon = listItem.querySelector('.fa-trash-alt');
        const updateIcon = listItem.querySelector('.fa-pen-to-square');

        deleteIcon.addEventListener('click', async () => {
            await deleteTask(id);
            listItem.style.display = 'none';
        });

        tasksList.appendChild(listItem);
    });
}
