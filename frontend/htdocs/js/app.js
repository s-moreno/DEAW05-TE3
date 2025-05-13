// app.js

import { loadCategories, showCategoryModal } from "./categories.js";
import { loadProducts, showProductModal} from "./products.js";

// Cambia de sección
function showSection(sectionId) {
  document
    .querySelectorAll(".section")
    .forEach((sec) => sec.classList.remove("active"));
  document.getElementById(sectionId).classList.add("active");
}

document.addEventListener("DOMContentLoaded", () => {
  loadCategories();
  loadProducts();

  // Eventos para los botones de navegación ( productos, categorías e info)
  document
    .getElementById("btnCategories")
    .addEventListener("click", () => showSection("categoriesSection"));
  document
    .getElementById("btnProducts")
    .addEventListener("click", () => showSection("productsSection"));

  document.getElementById("btnInfo").addEventListener("click", () => {
    const modal = new bootstrap.Modal(document.getElementById("infoModal"));
    modal.show();
  });

  // Evento para el botón de "Mostrar productos bajo stock"
  document
    .getElementById("lowStockCheck")
    .addEventListener("change", () => loadProducts());

  // Evento para mostrar el modal de Categoría (nuevo o editar)
  document
    .getElementById("btnCategoryModal")
    .addEventListener("click", () => showCategoryModal());

  // Evento para mostrar el modal de Producto (nuevo o editar)
  document
    .getElementById("btnProductModal")
    .addEventListener("click", () => showProductModal());
});
