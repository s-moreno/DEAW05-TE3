import { API_BASE_PRODUCTS } from "./config.js";
import { API_BASE_CATEGORIES } from "./config.js";

let editingProductId = null;

// Carga productos desde la API
export async function loadProducts() {
  try {
    const lowStock = document.getElementById("lowStockCheck").checked;
    let url = `${API_BASE_PRODUCTS}`;
    if (lowStock) url += "/min";

    const res = await fetch(url);
    if (!res.ok) throw new Error("Error al cargar productos");
    const json = await res.json();

    const tbody = document.getElementById("productsTable");
    tbody.innerHTML = "";

    // json.data.forEach((prod) => {
    for (const prod of json.data) {
      const row = document.createElement("tr");
      if (prod.stock_actual < prod.stock_minimo) row.classList.add("low-stock");

      // buscar nombre de categoría según el id
      let nombreCat = "Sin categoría";
      try {
        const resCat = await fetch(
          `${API_BASE_CATEGORIES}/${prod.id_categoria}`
        );
        if (resCat.ok) {
          const jsonCat = await resCat.json();
          nombreCat = jsonCat?.data.nombre || "Sin categoría";
        }
      } catch (err) {
        console.error(err);
        nombreCat = "No disponible";
      }

      row.innerHTML = `
        <td>${prod.id}</td>
        <td>${prod.nombre}</td>
        <td>${prod.stock_actual}</td>
        <td>${prod.stock_minimo}</td>
        <td>${nombreCat}</td>
        <td></td>`;

      // Crear el botón "Editar"
      const btnEdit = document.createElement("button");
      btnEdit.className = "btn btn-sm btn-warning me-2";
      btnEdit.innerHTML = `<i class="bi bi-pencil-square"></i>`;
      // Añadir evento click para "Editar"
      btnEdit.addEventListener("click", () => editProduct(prod.id));

      // Crear el botón "Borrar"
      const btnDelete = document.createElement("button");
      btnDelete.className = "btn btn-sm btn-danger";
      btnDelete.innerHTML = `<i class="bi bi-trash3"></i>`;
      // Añadir evento click para "Borrar"
      btnDelete.addEventListener("click", () => deleteProduct(prod.id));
      // Colocar los botones dentro de la última celda (columna)
      const tdActions = row.querySelector("td:last-child");
      tdActions.appendChild(btnEdit);
      tdActions.appendChild(btnDelete);
      // Añadir la fila al tbody
      tbody.appendChild(row);
    }
  } catch (err) {
    console.error(err);
    alert("No se pudieron cargar los productos");
  }
}

// Muestra el modal para crear un producto
export async function showProductModal(id = null) {
  editingProductId = id;
  const form = document.getElementById("productForm");
  form.reset();

  await cargarCategorias();

  new bootstrap.Modal(document.getElementById("productModal")).show();
}

// Editar un producto existente
async function editProduct(id) {
  try {
    const res = await fetch(`${API_BASE_PRODUCTS}/${id}`);
    if (!res.ok) throw new Error("Error al obtener el producto");
    const json = await res.json();

    // Rellena el formulario con los datos existentes
    document.getElementById("productName").value = json.data.nombre;
    document.getElementById("stock").value = json.data.stock_actual;
    document.getElementById("stockMin").value = json.data.stock_minimo;
    
    const selectCategory = document.getElementById("productCategoryId");
    const idCategoria = json.data.id_categoria;
    
    await cargarCategorias();

    selectCategory.value = idCategoria;

    editingProductId = id;

    new bootstrap.Modal(document.getElementById("productModal")).show();
  } catch (err) {
    console.error(err);
    alert("No se pudo cargar el producto");
  }
}

// Elimina un producto
async function deleteProduct(id) {
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
      const res = await fetch(`${API_BASE_PRODUCTS}/${id}`, {
        method: "DELETE",
      });

      if (!res.ok) throw new Error("Error al eliminar el producto");

      modal.hide();
      loadProducts();
    } catch (err) {
      console.error(err);
      alert("No se pudo eliminar el producto");
    }
  });
}

// Llama a esta función al cargar el modal para poblar el select de categorías
export async function cargarCategorias() {
  
  const select = document.getElementById("productCategoryId");
  try {
    const res = await fetch(`${API_BASE_CATEGORIES}`);
    if (!res.ok) throw new Error("Error al obtener categorías");
    const json = await res.json();

    // Limpia opciones anteriores
    select.innerHTML = '<option value=""></option>';

    // Agrega cada categoría al select
    json.data.forEach(cat => {
      const option = document.createElement("option");
      option.value = cat.id;
      option.textContent = cat.nombre;
      select.appendChild(option);
    });
  } catch (error) {
    console.error("Error cargando categorías:", error);
  }
}

// Evento de envío del formulario de producto
document.getElementById("productForm").addEventListener("submit", async (e) => {
  e.preventDefault();

  const nombre = document.getElementById("productName").value.trim();
  const stockActual = document.getElementById("stock").value.trim();
  const stockMinimo = document.getElementById("stockMin").value.trim();
  const idCategoria = document.getElementById("productCategoryId").value.trim();

  // Función para mostrar modal de error
  const showErrorModal = (mensaje) => {
    document.getElementById("errorMessage").textContent = mensaje;
    const errorModal = new bootstrap.Modal(document.getElementById("errorModal"));
    errorModal.show();
  };

  // Validación básica
  if (!nombre || isNaN(stockActual) || isNaN(stockMinimo) || isNaN(idCategoria)) {
    showErrorModal("Por favor, completa todos los campos.");
    return;
  }

  const payload = {
    nombre,
    stock_actual: Number(stockActual),
    stock_minimo: Number(stockMinimo),
    id_categoria: Number(idCategoria),
  };

  const url = editingProductId
    ? `${API_BASE_PRODUCTS}/${editingProductId}`
    : API_BASE_PRODUCTS;
  const method = editingProductId ? "PUT" : "POST";

  try {
    const res = await fetch(url, {
      method,
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(payload),
    });

    if (!res.ok) {
      const errorData = await res.json();
      throw new Error(errorData.message || "Error al guardar el producto");
    }


    bootstrap.Modal.getInstance(document.getElementById("productModal")).hide();
    loadProducts();
  
  } catch (err) {
    console.error(err);
    showErrorModal(err.message || "Error al guardar el producto");
  }
});
