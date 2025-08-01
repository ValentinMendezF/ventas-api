package com.valentinmendezf.ventas_api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClienteDTO {
    private Long idCliente;
    private String nombre;
    private String apellido;
    private String dni;
}
