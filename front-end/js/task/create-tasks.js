import { apiUrl } from "../environment/environment.js";
import { getUserEmailFromToken } from "../auth/user-token.js";
import { fetchTaskList } from "./get-tasks.js";

document.getElementById('createTask').addEventListener('click', createTask);

export async function createTask() {
    const authToken = localStorage.getItem('jwt_token');
    const userEmail = getUserEmailFromToken();

    if (checkToken()) {
        const title = document.getElementById('title').value;
        const description = document.getElementById('description').value;
        const priority = document.getElementById('priority').value;
        const startAt = document.getElementById('startAt').value;
        const endAt = document.getElementById('endAt').value;

        if (title && startAt && endAt) {
            const taskData = {
                title,
                description,
                priority,
                startAt,
                endAt,
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

            if (response.ok) {
                const jsonData = await response.json();
                document.getElementById('taskForm').reset();
                await fetchTaskList();
            }
        }
    }
}
