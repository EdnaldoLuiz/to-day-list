import { apiUrl } from "../environment/environment.js";
import { fetchTaskList } from "./get-tasks.js";

const editTaskButtons = document.querySelectorAll('.icon-update');
const editTaskModal = document.getElementById('editTaskModal');
const closeEditTaskModal = document.getElementById('closeEditTaskModal');
const saveTaskChangesButton = document.getElementById('saveTaskChanges');
const editTaskForm = document.getElementById('editTaskForm');

export function openUpdateModal(task) {
    document.getElementById('editTaskId').value = task.id;
    document.getElementById('editTaskTitle').value = task.title;
    document.getElementById('editTaskDescription').value = task.description;
    document.getElementById('editTaskStartAt').value = task.startAt;
    document.getElementById('editTaskEndAt').value = task.endAt;
    document.getElementById('editTaskPriority').value = task.priority;
    editTaskModal.style.display = 'block';
}

editTaskButtons.forEach(button => {
    button.addEventListener('click', () => {
        const listItem = button.parentElement;
        const taskId = listItem.querySelector('.icon-update').dataset.taskId;
        const taskTitle = listItem.querySelector('.taskTitle').textContent;
        const taskDescription = listItem.querySelector('.taskDescription').textContent;
        const [taskStartAt, taskEndAt] = listItem.querySelector('.taskTime').textContent.split(' - ');
        const taskPriority = listItem.classList.contains('taskPriorityLow') ? 'BAIXA' :
            listItem.classList.contains('taskPriorityMedium') ? 'MEDIA' : 'ALTA';

        fillEditModal({
            id: taskId,
            title: taskTitle,
            description: taskDescription,
            startAt: taskStartAt,
            endAt: taskEndAt,
            priority: taskPriority
        });
    });
});

closeEditTaskModal.addEventListener('click', () => {
    editTaskModal.style.display = 'none';
});

saveTaskChangesButton.addEventListener('click', async () => {
    const formData = new FormData(editTaskForm);
    const editedTaskData = {};
    formData.forEach((value, key) => {
        editedTaskData[key] = value;
    });

    const response = await fetch(`${apiUrl}/tasks`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${localStorage.getItem('jwt_token')}`
        },
        body: JSON.stringify(editedTaskData)
    });

    if (response.ok) {
        editTaskModal.style.display = 'none';
        fetchTaskList();
    }
});
