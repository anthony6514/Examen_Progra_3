package com.rentafacil.backend.controller;

import com.rentafacil.backend.model.Vehiculo;
import com.rentafacil.backend.repository.VehiculoRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controlador REST para la gestión de vehículos de RentaFácil.
 * Expone el CRUD completo y las consultas personalizadas en /api/vehiculos.
 *
 * @CrossOrigin habilita CORS sin restricciones para que la interfaz
 * web pueda consumir la API desde cualquier origen.
 */
@RestController
@RequestMapping("/api/vehiculos")
@CrossOrigin(origins = "*")
public class VehiculoController {

    private final VehiculoRepository vehiculoRepository;

    public VehiculoController(VehiculoRepository vehiculoRepository) {
        this.vehiculoRepository = vehiculoRepository;
    }

    // ─────────────────────────────────────────────────────────────────────────
    // CRUD básico
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * POST /api/vehiculos
     * Crear un nuevo vehículo.
     */
    @PostMapping
    public ResponseEntity<Vehiculo> crear(@Valid @RequestBody Vehiculo vehiculo) {
        Vehiculo guardado = vehiculoRepository.save(vehiculo);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
    }

    /**
     * GET /api/vehiculos
     * Listar todos los vehículos.
     */
    @GetMapping
    public ResponseEntity<List<Vehiculo>> listarTodos() {
        List<Vehiculo> vehiculos = vehiculoRepository.findAll();
        return ResponseEntity.ok(vehiculos);
    }

    /**
     * GET /api/vehiculos/{id}
     * Obtener un vehículo por su id.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Vehiculo> obtenerPorId(@PathVariable Long id) {
        Optional<Vehiculo> vehiculo = vehiculoRepository.findById(id);
        return vehiculo
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * PUT /api/vehiculos/{id}
     * Actualizar un vehículo existente.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Vehiculo> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody Vehiculo datosActualizados) {

        return vehiculoRepository.findById(id)
                .map(vehiculo -> {
                    vehiculo.setModelo(datosActualizados.getModelo());
                    vehiculo.setCategoria(datosActualizados.getCategoria());
                    vehiculo.setDescripcion(datosActualizados.getDescripcion());
                    vehiculo.setPrecioPorDia(datosActualizados.getPrecioPorDia());
                    vehiculo.setUnidadesDisponibles(datosActualizados.getUnidadesDisponibles());
                    Vehiculo actualizado = vehiculoRepository.save(vehiculo);
                    return ResponseEntity.ok(actualizado);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * DELETE /api/vehiculos/{id}
     * Eliminar un vehículo por su id.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (!vehiculoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        vehiculoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Consultas personalizadas
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * GET /api/vehiculos/por-categoria?categoria=SUV&minUnidades=2
     *
     * Consulta a) — Vehículos de una categoría específica
     * con unidades disponibles mayores a un valor dado.
     */
    @GetMapping("/por-categoria")
    public ResponseEntity<List<Vehiculo>> porCategoriaYDisponibles(
            @RequestParam String categoria,
            @RequestParam(defaultValue = "0") Integer minUnidades) {

        List<Vehiculo> resultado =
                vehiculoRepository.findByCategoriaAndUnidadesDisponiblesGreaterThan(
                        categoria, minUnidades);
        return ResponseEntity.ok(resultado);
    }

    /**
     * GET /api/vehiculos/por-rango-precio?precioMin=30&precioMax=100
     *
     * Consulta b) — Vehículos dentro de un rango de precio por día,
     * ordenados de menor a mayor.
     */
    @GetMapping("/por-rango-precio")
    public ResponseEntity<List<Vehiculo>> porRangoDePrecio(
            @RequestParam Double precioMin,
            @RequestParam Double precioMax) {

        List<Vehiculo> resultado =
                vehiculoRepository.findByPrecioPorDiaBetweenOrderByPrecioPorDiaAsc(
                        precioMin, precioMax);
        return ResponseEntity.ok(resultado);
    }

    /**
     * GET /api/vehiculos/buscar?modelo=toyota
     *
     * Consulta c) — Búsqueda parcial por modelo (insensible a mayúsculas).
     */
    @GetMapping("/buscar")
    public ResponseEntity<List<Vehiculo>> buscarPorModelo(
            @RequestParam String modelo) {

        List<Vehiculo> resultado =
                vehiculoRepository.findByModeloContainingIgnoreCase(modelo);
        return ResponseEntity.ok(resultado);
    }
}
