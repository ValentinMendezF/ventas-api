package com.valentinmendezf.ventas_api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MayorVentaDto {
    private Long codidoVenta;
    private Double total;
    private int cantidadProductos;
    private String nombreCliente;
    private String apellido;
}
