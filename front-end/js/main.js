import { createTask } from "./task/create-tasks.js";
import { fetchTaskList } from "./task/get-tasks.js";

document.getElementById('createTask').addEventListener('click', createTask);
window.addEventListener('load', fetchTaskList);