console.log("Script file loaded");

function toggleProjectDetails(detailsId) {
  const details = document.getElementById(detailsId);
  details.classList.toggle("active");
}

document.addEventListener("DOMContentLoaded", function () {
  console.log("DOM loaded");

  const screenshots = document.querySelectorAll(".screenshots-grid img");
  console.log("Found screenshots:", screenshots.length);

  let currentImageIndex = 0;
  let screenshotUrls = [];

  // Create modal if it doesn't exist
  if (!document.querySelector(".modal")) {
    const modal = document.createElement("div");
    modal.className = "modal";
    modal.innerHTML = `
      <button class="prev-btn"><i class="fas fa-chevron-left"></i></button>
      <div class="modal-content">
        <img src="" alt="Screenshot">
      </div>
      <button class="next-btn"><i class="fas fa-chevron-right"></i></button>
      <button class="modal-close"><i class="fas fa-times"></i></button>
    `;
    document.body.appendChild(modal);
  }

  const modal = document.querySelector(".modal");
  const modalImg = modal.querySelector("img");
  const prevBtn = modal.querySelector(".prev-btn");
  const nextBtn = modal.querySelector(".next-btn");
  const closeBtn = modal.querySelector(".modal-close");

  // Add click event to all screenshots
  screenshots.forEach((screenshot, index) => {
    screenshotUrls.push(screenshot.src);

    screenshot.addEventListener("click", function () {
      currentImageIndex = index;
      updateModalImage();
      modal.classList.add("active");
    });
  });

  function updateModalImage() {
    modalImg.src = screenshotUrls[currentImageIndex];
    prevBtn.style.display = currentImageIndex > 0 ? "block" : "none";
    nextBtn.style.display =
      currentImageIndex < screenshotUrls.length - 1 ? "block" : "none";
  }

  prevBtn.addEventListener("click", function () {
    if (currentImageIndex > 0) {
      currentImageIndex--;
      updateModalImage();
    }
  });

  nextBtn.addEventListener("click", function () {
    if (currentImageIndex < screenshotUrls.length - 1) {
      currentImageIndex++;
      updateModalImage();
    }
  });

  closeBtn.addEventListener("click", () => modal.classList.remove("active"));

  modal.addEventListener("click", (e) => {
    if (e.target === modal) modal.classList.remove("active");
  });

  // Keyboard navigation
  document.addEventListener("keydown", (e) => {
    if (!modal.classList.contains("active")) return;

    switch (e.key) {
      case "ArrowLeft":
        if (currentImageIndex > 0) {
          currentImageIndex--;
          updateModalImage();
        }
        break;
      case "ArrowRight":
        if (currentImageIndex < screenshotUrls.length - 1) {
          currentImageIndex++;
          updateModalImage();
        }
        break;
      case "Escape":
        modal.classList.remove("active");
        break;
    }
  });
});

document.addEventListener("DOMContentLoaded", function () {
  console.log("DOM loaded - checking hamburger menu");

  const hamburger = document.querySelector(".hamburger-menu");
  const navbar = document.querySelector(".navbar");

  if (!hamburger || !navbar) {
    console.error("Hamburger or navbar not found!");
    return;
  }

  console.log("Found hamburger and navbar elements");

  // Toggle menu
  hamburger.addEventListener("click", function () {
    console.log("Hamburger clicked");
    navbar.classList.toggle("active");
    console.log("Navbar classes:", navbar.classList);
  });

  // Close menu when clicking a link
  const navLinks = document.querySelectorAll(".nav-link");
  navLinks.forEach((link) => {
    link.addEventListener("click", () => {
      navbar.classList.remove("active");
    });
  });

  // Close menu when clicking outside
  document.addEventListener("click", (e) => {
    if (!navbar.contains(e.target) && !hamburger.contains(e.target)) {
      navbar.classList.remove("active");
    }
  });
});

// Custom cursor
document.addEventListener("mousemove", (e) => {
  const cursor = document.querySelector(".custom-cursor");
  cursor.style.left = e.clientX + "px";
  cursor.style.top = e.clientY + "px";

  // Scale effect when hovering over interactive elements
  const hoverable = e.target.closest("a, button, .hobby-card");
  if (hoverable) {
    cursor.style.transform = "scale(1.5)";
  } else {
    cursor.style.transform = "scale(1)";
  }
});

// Scroll-triggered animations
const updatePlantGrowth = () => {
  const scrollPercent =
    (window.scrollY /
      (document.documentElement.scrollHeight - window.innerHeight)) *
    100;
  const plant = document.querySelector(".growing-plant");
  plant.style.setProperty("--scroll-height", `${scrollPercent}vh`);
};

window.addEventListener("scroll", updatePlantGrowth);

// Random floating elements movement
document.querySelectorAll(".floating-leaf").forEach((leaf, index) => {
  leaf.style.animation = `float ${15 + index * 2}s ease-in-out infinite`;
  leaf.style.animationDelay = `${index * -5}s`;
});

// Initialize AOS (Add this library for scroll animations)
AOS.init({
  duration: 800,
  easing: "ease-out",
  once: false,
});
