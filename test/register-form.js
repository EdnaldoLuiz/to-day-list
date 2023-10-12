export async function registerUser() {
    const name = document.getElementById('name').value;
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;

    const userData = {
        name: name,
        username: username,
        password: password
    };

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
    alert('User created successfully');
    console.log(data);
}