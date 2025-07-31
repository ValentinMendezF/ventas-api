package com.valentinmendezf.ventas_api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class VentaProducto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "codigo_venta")
    private Venta venta;
    @ManyToOne
    @JoinColumn(name = "codigo_producto")
    private Producto producto;
    private int cantidad;
}


