export function getUserEmailFromToken() {
    const token = localStorage.getItem('jwt_token');
    if (token) {
        const tokenParts = token.split('.');
        if (tokenParts.length === 3) {
            const payload = JSON.parse(atob(tokenParts[1]));
            return payload.sub; 
        }
    }
    return null;
}