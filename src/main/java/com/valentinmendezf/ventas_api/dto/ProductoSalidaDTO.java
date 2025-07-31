package com.valentinmendezf.ventas_api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductoSalidaDTO {
    private Long codigoProducto;
    private String nombre;
    private String marca;
    private Double costo;
    private int cantidadDisponible;
}
