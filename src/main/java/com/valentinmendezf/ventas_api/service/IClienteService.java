package com.valentinmendezf.ventas_api.service;

import com.valentinmendezf.ventas_api.model.Cliente;

import java.util.List;

public interface IClienteService {
    void createCliente(String nombre, String apellido, String dni);

    List<Cliente> getAllClientes();

    Cliente getOneCliente(Long idCliente);

    void deleteCliente(Long idCliente);

    void updateCliente(Long idCliente, String nuevoNombre, String nuevoApellido, String nuevoDni);
}
