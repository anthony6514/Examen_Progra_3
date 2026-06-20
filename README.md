# RentaFácil — Sistema de Gestión de Flota

**Autor:** [Tu Nombre Aquí]  
**Asignatura:** Programación III

---

## Descripción

Backend REST desarrollado con **Spring Boot 3 + Java 21 + PostgreSQL** para gestionar la flota de vehículos de la empresa RentaFácil, junto con una interfaz web en **HTML, CSS y JavaScript puro** que consume la API mediante `fetch()`.

---

## Estructura del repositorio

```
Examen_Progra3/
├── backend/          → Proyecto Spring Boot (Maven)
└── frontend/         → Interfaz web estática (HTML + CSS + JS)
```

---

## Requisitos previos

- Java 21
- Maven 3.9+
- PostgreSQL (con base de datos `examen_parcial1` creada)
- Navegador web moderno

---

## Configuración de la base de datos

En DBeaver (o psql), ejecuta:

```sql
CREATE DATABASE examen_parcial1;
```

Luego ajusta las credenciales en `backend/src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/examen_parcial1
spring.datasource.username=postgres
spring.datasource.password=TU_CONTRASEÑA
```

---

## Ejecutar el backend

```bash
cd backend
mvn spring-boot:run
```

El servidor arranca en `http://localhost:8080`.

---

## Usar el frontend

Abre `frontend/index.html` directamente en el navegador (doble clic o con Live Server en VS Code).

---

## API REST — Endpoints

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| POST | `/api/vehiculos` | Crear vehículo |
| GET | `/api/vehiculos` | Listar todos |
| GET | `/api/vehiculos/{id}` | Obtener por ID |
| PUT | `/api/vehiculos/{id}` | Actualizar |
| DELETE | `/api/vehiculos/{id}` | Eliminar |
| GET | `/api/vehiculos/por-categoria?categoria=SUV&minUnidades=1` | Por categoría y unidades disponibles |
| GET | `/api/vehiculos/por-rango-precio?precioMin=30&precioMax=100` | Por rango de precio (ASC) |
| GET | `/api/vehiculos/buscar?modelo=toyota` | Búsqueda parcial por modelo |
