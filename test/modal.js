// modal.js
export function toggleModal() {
    const modal = document.querySelector(".modal");
    modal.classList.toggle("show-modal");
}

export function windowOnClick(event) {
    const modal = document.querySelector(".modal");
    if (event.target === modal) {
        toggleModal();
    }
}
