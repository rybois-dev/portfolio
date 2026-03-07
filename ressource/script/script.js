document.addEventListener('DOMContentLoaded', function() {
    // Dark mode toggle
    const darkModeToggle = document.getElementById('dark-mode-toggle');
    const body = document.body;

    // Check for saved user preference, if any, on load of the website
    const darkMode = localStorage.getItem('darkMode');

    if (darkMode === 'enabled') {
        body.classList.add('dark-mode');
    }

    darkModeToggle.addEventListener('click', function() {
        body.classList.toggle('dark-mode');

        // Save the current preference to localStorage
        if (body.classList.contains('dark-mode')) {
            localStorage.setItem('darkMode', 'enabled');
            darkModeToggle.textContent = '🌙';
        } else {
            localStorage.setItem('darkMode', 'disabled');
            darkModeToggle.textContent = '☀️';
        }
    });

    // Section animations
    const sections = document.querySelectorAll('section');
    const observer = new IntersectionObserver(entries => {
        entries.forEach(entry => {
            if (entry.isIntersecting) {
                entry.target.style.opacity = '1';
                entry.target.style.transform = 'translateY(0)';
                entry.target.style.transition = 'opacity 0.5s ease, transform 0.5s ease, box-shadow 0.5s ease';
                entry.target.style.boxShadow = '0 10px 20px rgba(0, 0, 0, 0.2)';
            }
        });
    }, { threshold: 0.1 });

    sections.forEach(section => {
        section.style.opacity = '0';
        section.style.transform = 'translateY(20px)';
        section.style.transition = 'opacity 0.5s ease, transform 0.5s ease, box-shadow 0.5s ease';
        observer.observe(section);
    });

    // Project card animations
    const projectCards = document.querySelectorAll('.carte-projet');
    projectCards.forEach(card => {
        card.style.opacity = '0';
        card.style.transform = 'scale(0.9)';
        card.style.transition = 'opacity 0.5s ease, transform 0.5s ease, box-shadow 0.5s ease';
    });

    const cardObserver = new IntersectionObserver(entries => {
        entries.forEach(entry => {
            if (entry.isIntersecting) {
                entry.target.style.opacity = '1';
                entry.target.style.transform = 'scale(1)';
                entry.target.style.boxShadow = '0 10px 20px rgba(0, 0, 0, 0.2)';
            }
        });
    }, { threshold: 0.1 });

    projectCards.forEach(card => {
        cardObserver.observe(card);
    });

    // Contact form animation
    const contactForm = document.querySelector('#contact');
    contactForm.style.opacity = '0';
    contactForm.style.transform = 'translateY(20px)';
    contactForm.style.transition = 'opacity 0.5s ease, transform 0.5s ease, box-shadow 0.5s ease';

    const formObserver = new IntersectionObserver(entries => {
        entries.forEach(entry => {
            if (entry.isIntersecting) {
                entry.target.style.opacity = '1';
                entry.target.style.transform = 'translateY(0)';
                entry.target.style.boxShadow = '0 10px 20px rgba(0, 0, 0, 0.2)';
            }
        });
    }, { threshold: 0.1 });

    formObserver.observe(contactForm);

    // Footer animation
    const footer = document.querySelector('footer');
    footer.style.opacity = '0';
    footer.style.transform = 'translateY(20px)';
    footer.style.transition = 'opacity 0.5s ease, transform 0.5s ease, box-shadow 0.5s ease';

    const footerObserver = new IntersectionObserver(entries => {
        entries.forEach(entry => {
            if (entry.isIntersecting) {
                entry.target.style.opacity = '1';
                entry.target.style.transform = 'translateY(0)';
                entry.target.style.boxShadow = '0 10px 20px rgba(0, 0, 0, 0.2)';
            }
        });
    }, { threshold: 0.1 });

    footerObserver.observe(footer);
});

function nextPublication() {
    const frames = document.querySelectorAll('iframe');
    const visibleFrames = document.querySelectorAll('iframe.visible');

    // Masquer les frames visibles actuelles
    visibleFrames.forEach(frame => {
        frame.classList.remove('visible');
        frame.classList.add('cache');
    });

    // Trouver la prochaine frame à afficher
    let nextIndex = 0;
    if (visibleFrames.length > 0) {
        const currentIndex = Array.from(frames).indexOf(visibleFrames[0]);
        nextIndex = (currentIndex + 1) % frames.length;
    }

    // Afficher la prochaine frame
    frames[nextIndex].classList.remove('cache');
    frames[nextIndex].classList.add('visible');
}

//let changemenActu = setInterval(nextPublication, 6000);

