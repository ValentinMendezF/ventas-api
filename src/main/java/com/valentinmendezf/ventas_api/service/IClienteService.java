package com.valentinmendezf.ventas_api.service;

import com.valentinmendezf.ventas_api.model.Cliente;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface IClienteService {
    void createCliente(String nombre, String apellido, String dni);

    List<Cliente> getAllClientes();

    Cliente getOneCliente(Long idCliente);

    void deleteCliente(Long idCliente);

    void updateCliente(Long idCliente, String nuevoNombre, String nuevoApellido, String nuevoDni);
}
