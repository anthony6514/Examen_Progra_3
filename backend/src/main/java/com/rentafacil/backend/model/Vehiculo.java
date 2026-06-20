package com.rentafacil.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

/**
 * Entidad que representa un vehículo en la flota de RentaFácil.
 */
@Entity
@Table(name = "vehiculos")
public class Vehiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Modelo del vehículo. Ejemplo: "Toyota Corolla 2024"
     * Obligatorio, entre 3 y 100 caracteres.
     */
    @NotBlank(message = "El modelo es obligatorio")
    @Size(min = 3, max = 100, message = "El modelo debe tener entre 3 y 100 caracteres")
    @Column(nullable = false, length = 100)
    private String modelo;

    /**
     * Categoría del vehículo. Ejemplo: "Sedán", "SUV", "Pickup"
     * Obligatorio, no puede estar vacío ni en blanco.
     */
    @NotBlank(message = "La categoría es obligatoria y no puede estar en blanco")
    @Column(nullable = false, length = 50)
    private String categoria;

    /**
     * Descripción opcional del vehículo.
     * Si se ingresa, máximo 255 caracteres.
     */
    @Size(max = 255, message = "La descripción no puede superar los 255 caracteres")
    @Column(length = 255)
    private String descripcion;

    /**
     * Precio por día de alquiler. Obligatorio, debe ser mayor a 0.
     */
    @NotNull(message = "El precio por día es obligatorio")
    @DecimalMin(value = "0.01", message = "El precio por día debe ser mayor a 0")
    @Column(nullable = false)
    private Double precioPorDia;

    /**
     * Unidades disponibles en flota. Obligatorio, mayor o igual a 0.
     */
    @NotNull(message = "Las unidades disponibles son obligatorias")
    @Min(value = 0, message = "Las unidades disponibles deben ser mayor o igual a 0")
    @Column(nullable = false)
    private Integer unidadesDisponibles;

    // ─── Constructores ───────────────────────────────────────────────────────

    public Vehiculo() {}

    public Vehiculo(String modelo, String categoria, String descripcion,
                    Double precioPorDia, Integer unidadesDisponibles) {
        this.modelo = modelo;
        this.categoria = categoria;
        this.descripcion = descripcion;
        this.precioPorDia = precioPorDia;
        this.unidadesDisponibles = unidadesDisponibles;
    }

    // ─── Getters y Setters ───────────────────────────────────────────────────

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Double getPrecioPorDia() { return precioPorDia; }
    public void setPrecioPorDia(Double precioPorDia) { this.precioPorDia = precioPorDia; }

    public Integer getUnidadesDisponibles() { return unidadesDisponibles; }
    public void setUnidadesDisponibles(Integer unidadesDisponibles) {
        this.unidadesDisponibles = unidadesDisponibles;
    }
}
