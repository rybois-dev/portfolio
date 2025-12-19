document.addEventListener('DOMContentLoaded', function() {
    const zoomableImages = document.querySelectorAll('.zoomable-image');
    const zoomOverlay = document.createElement('div');
    zoomOverlay.className = 'zoom-overlay';
    document.body.appendChild(zoomOverlay);

    zoomableImages.forEach(img => {
        img.addEventListener('click', function() {
            if (this.classList.contains('zoomed')) {
                this.classList.remove('zoomed');
                zoomOverlay.style.display = 'none';
            } else {
                this.classList.add('zoomed');
                zoomOverlay.style.display = 'block';
            }
        });
    });

    zoomOverlay.addEventListener('click', function() {
        const zoomedImage = document.querySelector('.zoomable-image.zoomed');
        if (zoomedImage) {
            zoomedImage.classList.remove('zoomed');
            this.style.display = 'none';
        }
    });
});
