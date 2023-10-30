import { apiUrl } from "../environment/environment.js";
import { getUserEmailFromToken } from "../auth/user-token.js";
import { fetchTaskList } from "./get-tasks.js";

export async function createTask() {
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