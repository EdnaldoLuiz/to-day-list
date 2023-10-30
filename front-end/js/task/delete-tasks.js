import { apiUrl } from "../environment/environment.js";

export async function deleteTask(taskId) {
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