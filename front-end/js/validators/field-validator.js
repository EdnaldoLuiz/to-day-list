export function validateField(inputId, errorMessage) {
    const input = document.getElementById(inputId);
    const errorElement = input.nextElementSibling;

    input.addEventListener("blur", () => {
        if (!input.validity.valid) {
            errorElement.style.visibility = "visible";
            errorElement.style.opacity = 1;
            errorElement.textContent = errorMessage;
        }
    });

    input.addEventListener("input", () => {
        if (input.validity.valid) {
            errorElement.style.visibility = "hidden";
            errorElement.style.opacity = 0;
        }
    });
}
