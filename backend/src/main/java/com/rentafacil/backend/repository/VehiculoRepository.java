package com.rentafacil.backend.repository;

import com.rentafacil.backend.model.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio JPA para la entidad Vehiculo.
 * Spring Data JPA genera el SQL automáticamente a partir del nombre de cada método.
 */
@Repository
public interface VehiculoRepository extends JpaRepository<Vehiculo, Long> {

    /**
     * Consulta a) — Vehículos de una categoría específica con unidades disponibles
     * mayores a un valor dado.
     *
     * SQL generado:
     *   SELECT * FROM vehiculos
     *   WHERE categoria = ?1 AND unidades_disponibles > ?2
     */
    List<Vehiculo> findByCategoriaAndUnidadesDisponiblesGreaterThan(
            String categoria, Integer minUnidades);

    /**
     * Consulta b) — Vehículos cuyo precio por día esté dentro de un rango,
     * ordenados de menor a mayor precio.
     *
     * SQL generado:
     *   SELECT * FROM vehiculos
     *   WHERE precio_por_dia BETWEEN ?1 AND ?2
     *   ORDER BY precio_por_dia ASC
     */
    List<Vehiculo> findByPrecioPorDiaBetweenOrderByPrecioPorDiaAsc(
            Double precioMin, Double precioMax);

    /**
     * Consulta c) — Búsqueda parcial de vehículos por modelo (insensible a mayúsculas).
     *
     * SQL generado:
     *   SELECT * FROM vehiculos
     *   WHERE LOWER(modelo) LIKE LOWER('%?1%')
     */
    List<Vehiculo> findByModeloContainingIgnoreCase(String modelo);
}
