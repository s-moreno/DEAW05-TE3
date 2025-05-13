import { API_BASE_CATEGORIES } from "./config.js";
let editingCategoryId = null;

// Carga las categorías desde la API y las muestra en tabla
export async function loadCategories() {
  try {
    const res = await fetch(`${API_BASE_CATEGORIES}`);
    if (!res.ok) throw new Error("Error al cargar categorías");
    const json = await res.json();

    const tbody = document.getElementById("categoriesTable");
    tbody.innerHTML = "";

    json.data.forEach((cat) => {
      const row = document.createElement("tr");

      row.innerHTML = `
        <td>${cat.id}</td>
        <td>${cat.nombre}</td>
        <td>${cat.descripcion}</td>
        <td></td>`;

      // Crear el botón "Editar"
      const btnEdit = document.createElement("button");
      btnEdit.className = "btn btn-sm btn-warning me-2";
      btnEdit.innerHTML = `<i class="bi bi-pencil-square"></i>`;
      // Añadir evento click para "Editar"
      btnEdit.addEventListener("click", () => editCategory(cat.id));

      // Crear el botón "Borrar"
      const btnDelete = document.createElement("button");
      btnDelete.className = "btn btn-sm btn-danger";
      btnDelete.innerHTML = `<i class="bi bi-trash3"></i>`;
      // Añadir evento click para "Borrar"
      btnDelete.addEventListener("click", () => deleteCategory(cat.id));

      // Colocar los botones dentro de la última celda (columna)
      const tdActions = row.querySelector("td:last-child");
      tdActions.appendChild(btnEdit);
      tdActions.appendChild(btnDelete);

      tbody.appendChild(row);
    });
  } catch (err) {
    console.error(err);
    alert("No se pudieron cargar las categorías");
  }
}

// Muestra el modal para crear una categoría
export function showCategoryModal(id = null) {
  editingCategoryId = id;
  const form = document.getElementById("categoryForm");
  form.reset();
  new bootstrap.Modal(document.getElementById("categoryModal")).show();
}

// Edita una categoría existente
async function editCategory(id) {
  try {
    const res = await fetch(`${API_BASE_CATEGORIES}/${id}`);
    if (!res.ok) throw new Error("Error al obtener la categoría");
    const json = await res.json();

    // Rellena el formulario con los datos existentes
    document.getElementById("categoryName").value = json.data.nombre;
    document.getElementById("categoryDescription").value =
      json.data.descripcion;
    editingCategoryId = id;

    new bootstrap.Modal(document.getElementById("categoryModal")).show();
  } catch (err) {
    console.error(err);
    alert("No se pudo cargar la categoría");
  }
}

// Elimina una categoría
async function deleteCategory(id) {
  const modalElement = document.getElementById("deleteModal");
  const modal = new bootstrap.Modal(modalElement);
  modal.show();

  // Clonamos el botón para eliminar cualquier listener anterior
  const oldBtn = document.getElementById("confirmDelete");
  const newBtn = oldBtn.cloneNode(true);
  oldBtn.replaceWith(newBtn);

  // Añadimos un único listener limpio
  newBtn.addEventListener("click", async () => {
    try {
      const res = await fetch(`${API_BASE_CATEGORIES}/${id}`, {
        method: "DELETE",
      });

      if (!res.ok) throw new Error("Error al eliminar la categoría");

      modal.hide();
      loadCategories();
    } catch (err) {
      console.error(err);
      alert("No se pudo eliminar la categoría");
    }
  });
}

// Evento de envío del formulario de categoría
document
  .getElementById("categoryForm")
  .addEventListener("submit", async (e) => {
    e.preventDefault();
    const nombre = document.getElementById("categoryName").value.trim();
    const descripcion = document
      .getElementById("categoryDescription")
      .value.trim();

    if (!nombre) return alert("El nombre es obligatorio.");

    const payload = { nombre, descripcion };
    const url = editingCategoryId
      ? `${API_BASE_CATEGORIES}/${editingCategoryId}`
      : `${API_BASE_CATEGORIES}`;
    const method = editingCategoryId ? "PUT" : "POST";

    try {
      const res = await fetch(url, {
        method,
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(payload),
      });

      if (!res.ok) throw new Error("Error al guardar la categoría");
      bootstrap.Modal.getInstance(
        document.getElementById("categoryModal")
      ).hide();
      loadCategories();
    } catch (err) {
      console.error(err);
      alert("No se pudo guardar la categoría");
    }
  });
