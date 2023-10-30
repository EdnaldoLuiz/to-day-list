import { validateField } from "../validators/field-validator.js";
import { apiUrl } from "../environment/environment.js";

async function loginUser() {
    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;

    const loginData = {
        login: email,
        password: password
    };

    const response = await fetch(`${apiUrl}/auth/login`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(loginData)
    });

    if (response.ok === true) {
        const data = await response.json();
        const token = data.token;

        localStorage.setItem('jwt_token', token);

        if (token) {
            window.location.href = '/pages/main.html';
        }
    }
}

validateField("email", "Formato do email inválido");
validateField("password", "Senha deve ter no mínimo 8 caracteres");

document.querySelector('button').addEventListener('click', loginUser);
