document.addEventListener('DOMContentLoaded', () => {
    const nameInput = document.getElementById('nameInput');
    const colorInput = document.getElementById('colorInput');
    const widthInput = document.getElementById('widthInput');
    const heightInput = document.getElementById('heightInput');
    const rectangle = document.getElementById('rectangle');

    function updateRectangle() {
        rectangle.style.backgroundColor = colorInput.value;
        rectangle.style.width = widthInput.value + 'px';
        rectangle.style.height = heightInput.value + 'px';
        rectangle.innerHTML = nameInput.value;
    }
    
    nameInput.addEventListener('input', updateRectangle);
    colorInput.addEventListener('input', updateRectangle);
    widthInput.addEventListener('input', updateRectangle);
    heightInput.addEventListener('input', updateRectangle);
});