import { validateField } from "../validators/field-validator.js";
import { apiUrl } from "../environment/environment.js";

async function registerUser() {
    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;
    const username = document.getElementById("username").value;

    const registerData = {
        login: email,
        password: password,
        username: username
    };

    const response = await fetch(`${apiUrl}/auth/register`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(registerData)
    });

    if (response.ok) {
        window.location.href = '/pages/login.html'
    }
}

validateField("username", "Nome deve ter no mínimo 3 caracteres");
validateField("email", "Formato do email inválido");
validateField("password", "Senha deve ter no mínimo 8 caracteres");

document.querySelector('button').addEventListener('click', registerUser);