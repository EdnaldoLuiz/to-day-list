async function loginUser() {
    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;

    const loginData = {
        login: email,
        password: password
    };

    try {
        const response = await fetch('http://localhost:8080/auth/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(loginData)
        });

        if (response.ok) {
            const data = await response.json();
            const token = data.token;

            localStorage.setItem('jwt_token', token);

            if (token) {
                window.location.href = './pages/main.html';
            } else {
                alert('Token JWT inválido');
            }
        } else {
            console.error('Erro ao fazer login:', response.statusText);
        }
    } catch (error) {
        console.error('Erro ao fazer login:', error);
    }
}

document.querySelector('button').addEventListener('click', loginUser);
