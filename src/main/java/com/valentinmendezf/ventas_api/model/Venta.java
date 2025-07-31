package com.valentinmendezf.ventas_api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Venta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigoVenta;
    private LocalDate fechaVenta;
    private Double total;
    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<VentaProducto> listaVentasProductos = new HashSet<>();
    @ManyToOne
    @JoinColumn(name = "fk_cliente")
    private Cliente cliente;
}

