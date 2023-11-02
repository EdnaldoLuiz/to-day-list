import { createTask } from "./task/create-tasks.js";
import { fetchTaskList } from "./task/get-tasks.js";
import { openUpdateModal } from "./task/update-task.js";

document.getElementById('createTask').addEventListener('click', createTask);
window.addEventListener('load', fetchTaskList);
