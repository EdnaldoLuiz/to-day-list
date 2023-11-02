-- Active: 1697340095581@@127.0.0.1@3306@todolist

SELECT tb_tasks.id, tb_tasks.title, tb_users.login
FROM tb_tasks
INNER JOIN tb_users ON tb_tasks.user_login = tb_users.login
WHERE tb_tasks.user_login = 'ednaldo@gmail.com';