async function registerUser() {
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;
    const email = document.getElementById('email').value;

    const userData = {
        username: username,
        password: password,
        email: email
    };

    try {
        const response = await fetch('http://localhost:8080/users/register', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(userData)
        });

        if (!response.ok) {
            throw new Error('Ocorreu um erro');
        }

        const data = await response.json();

        setTimeout(() => {
            window.location.href = './pages/main.html';
        }, 3000);

        console.log(data);
    } catch (error) {
        console.error('Ocorreu um erro na operação fetch:', error);
    }
}

