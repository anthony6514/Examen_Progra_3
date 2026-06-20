// ============================================================
//  RentaFácil — Lógica de consumo de la API con fetch()
//  Base URL del backend Spring Boot
// ============================================================

const API_URL = 'http://localhost:8080/api/vehiculos';

// ─── Utilidades ──────────────────────────────────────────────

/**
 * Muestra la respuesta en el bloque <pre> de la página.
 * Acepta cualquier valor (objeto, string, error).
 */
function mostrarRespuesta(data) {
    const pre = document.getElementById('respuesta');
    if (typeof data === 'string') {
        pre.textContent = data;
    } else {
        pre.textContent = JSON.stringify(data, null, 2);
    }
}

function limpiarRespuesta() {
    document.getElementById('respuesta').textContent =
        'Aquí aparecerá la respuesta de la API...';
}

/**
 * Manejo centralizado de errores de fetch.
 */
async function manejarRespuesta(response) {
    if (response.status === 204) {
        return { mensaje: 'Operación exitosa (sin contenido)' };
    }
    const contentType = response.headers.get('Content-Type') || '';
    if (contentType.includes('application/json')) {
        const body = await response.json();
        if (!response.ok) {
            throw { status: response.status, body };
        }
        return body;
    } else {
        const text = await response.text();
        if (!response.ok) {
            throw { status: response.status, body: text };
        }
        return text || { mensaje: 'Operación exitosa' };
    }
}

// ─────────────────────────────────────────────────────────────
// CRUD COMPLETO
// ─────────────────────────────────────────────────────────────

// ── Crear vehículo (POST) ──────────────────────────────────
document.getElementById('formCrear').addEventListener('submit', async (e) => {
    e.preventDefault();

    const vehiculo = {
        modelo:              document.getElementById('modelo').value.trim(),
        categoria:           document.getElementById('categoria').value.trim(),
        descripcion:         document.getElementById('descripcion').value.trim() || null,
        precioPorDia:        parseFloat(document.getElementById('precioPorDia').value),
        unidadesDisponibles: parseInt(document.getElementById('unidadesDisponibles').value),
    };

    try {
        const response = await fetch(API_URL, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(vehiculo),
        });
        const data = await manejarRespuesta(response);
        mostrarRespuesta(data);
        e.target.reset();
    } catch (error) {
        mostrarRespuesta({ error: 'Error al crear vehículo', detalle: error });
    }
});

// ── Buscar por ID (GET /{id}) ──────────────────────────────
async function buscarPorId() {
    const id = document.getElementById('buscarId').value;
    if (!id) { mostrarRespuesta('⚠️ Ingresa un ID válido.'); return; }

    try {
        const response = await fetch(`${API_URL}/${id}`);
        const data = await manejarRespuesta(response);
        mostrarRespuesta(data);
    } catch (error) {
        mostrarRespuesta({ error: `Vehículo con ID ${id} no encontrado`, detalle: error });
    }
}

// ── Listar todos (GET /) ──────────────────────────────────
async function listarTodos() {
    try {
        const response = await fetch(API_URL);
        const data = await manejarRespuesta(response);
        mostrarRespuesta(data);
    } catch (error) {
        mostrarRespuesta({ error: 'Error al listar vehículos', detalle: error });
    }
}

// ── Actualizar vehículo (PUT /{id}) ───────────────────────
document.getElementById('formActualizar').addEventListener('submit', async (e) => {
    e.preventDefault();

    const id = document.getElementById('actualizarId').value;
    const vehiculo = {
        modelo:              document.getElementById('actualizarModelo').value.trim(),
        categoria:           document.getElementById('actualizarCategoria').value.trim(),
        descripcion:         document.getElementById('actualizarDescripcion').value.trim() || null,
        precioPorDia:        parseFloat(document.getElementById('actualizarPrecio').value),
        unidadesDisponibles: parseInt(document.getElementById('actualizarUnidades').value),
    };

    try {
        const response = await fetch(`${API_URL}/${id}`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(vehiculo),
        });
        const data = await manejarRespuesta(response);
        mostrarRespuesta(data);
    } catch (error) {
        mostrarRespuesta({ error: `Error al actualizar el vehículo ID ${id}`, detalle: error });
    }
});

// ── Eliminar vehículo (DELETE /{id}) ──────────────────────
async function eliminarVehiculo() {
    const id = document.getElementById('eliminarId').value;
    if (!id) { mostrarRespuesta('⚠️ Ingresa un ID válido para eliminar.'); return; }

    const confirmar = window.confirm(`¿Seguro que deseas eliminar el vehículo con ID ${id}?`);
    if (!confirmar) return;

    try {
        const response = await fetch(`${API_URL}/${id}`, { method: 'DELETE' });
        const data = await manejarRespuesta(response);
        mostrarRespuesta(data);
    } catch (error) {
        mostrarRespuesta({ error: `Error al eliminar el vehículo ID ${id}`, detalle: error });
    }
}

// ─────────────────────────────────────────────────────────────
// CONSULTAS PERSONALIZADAS
// ─────────────────────────────────────────────────────────────

// ── Consulta a) Por categoría y mínimo de unidades ────────
async function buscarPorCategoria() {
    const categoria   = document.getElementById('qCategoria').value.trim();
    const minUnidades = document.getElementById('qMinUnidades').value || 0;

    if (!categoria) { mostrarRespuesta('⚠️ Ingresa una categoría.'); return; }

    try {
        const url = `${API_URL}/por-categoria?categoria=${encodeURIComponent(categoria)}&minUnidades=${minUnidades}`;
        const response = await fetch(url);
        const data = await manejarRespuesta(response);
        mostrarRespuesta(data);
    } catch (error) {
        mostrarRespuesta({ error: 'Error en la consulta por categoría', detalle: error });
    }
}

// ── Consulta b) Por rango de precio ───────────────────────
async function buscarPorRangoPrecio() {
    const precioMin = document.getElementById('qPrecioMin').value;
    const precioMax = document.getElementById('qPrecioMax').value;

    if (!precioMin || !precioMax) {
        mostrarRespuesta('⚠️ Ingresa el precio mínimo y máximo.');
        return;
    }

    try {
        const url = `${API_URL}/por-rango-precio?precioMin=${precioMin}&precioMax=${precioMax}`;
        const response = await fetch(url);
        const data = await manejarRespuesta(response);
        mostrarRespuesta(data);
    } catch (error) {
        mostrarRespuesta({ error: 'Error en la consulta por rango de precio', detalle: error });
    }
}

// ── Consulta c) Búsqueda parcial por modelo ───────────────
async function buscarPorModelo() {
    const modelo = document.getElementById('qModelo').value.trim();
    if (!modelo) { mostrarRespuesta('⚠️ Ingresa parte del modelo a buscar.'); return; }

    try {
        const url = `${API_URL}/buscar?modelo=${encodeURIComponent(modelo)}`;
        const response = await fetch(url);
        const data = await manejarRespuesta(response);
        mostrarRespuesta(data);
    } catch (error) {
        mostrarRespuesta({ error: 'Error en la búsqueda por modelo', detalle: error });
    }
}
