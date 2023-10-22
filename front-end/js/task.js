const apiUrl = 'http://localhost:8080';

async function createTask() {
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
            },
            body: JSON.stringify(taskData),
        });

        const data = await response.json();
        console.log('Task created:', data);

        await fetchTaskList();
    } catch (error) {
        console.error('Error creating task:', error);
    }
}

async function fetchTaskList() {
    try {
        const response = await fetch(`${apiUrl}/tasks/get`);
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
}

fetchTaskList();
