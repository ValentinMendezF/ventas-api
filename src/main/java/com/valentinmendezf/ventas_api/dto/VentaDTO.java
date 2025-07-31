package com.valentinmendezf.ventas_api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VentaDTO {
    private Long codigoVenta;
    private LocalDate fechaVenta;
    private Double total;
    private List<ProductoEntradaDTO> listaProductos;
    private Long idCliente;
    private String nombreCliente;
}
