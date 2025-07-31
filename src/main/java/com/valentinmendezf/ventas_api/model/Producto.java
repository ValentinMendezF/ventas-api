package com.valentinmendezf.ventas_api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigoProducto;
    private String nombre;
    private String marca;
    private Double costo;
    private Integer cantidadDisponible;
    @OneToMany(mappedBy = "producto")
    private Set<VentaProducto> listaVentasProductos = new HashSet<>();
}
