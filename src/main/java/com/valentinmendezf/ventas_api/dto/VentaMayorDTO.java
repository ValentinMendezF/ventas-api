package com.valentinmendezf.ventas_api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VentaMayorDTO {
    private Long codigoVenta;
    private Double total;
    private int cantidadProductos;
    private String nombreCliente;
    private String apellidoCliente;
}
