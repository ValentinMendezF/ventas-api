package com.valentinmendezf.ventas_api.service;

import com.valentinmendezf.ventas_api.model.Cliente;
import com.valentinmendezf.ventas_api.repository.IClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

public class ClienteService implements IClienteService {

    @Autowired
    IClienteRepository iClienteRepository;

    @Override
    public void createCliente(String nombre, String apellido, String dni) {
        Cliente cliente = new Cliente();
        cliente.setNombre(nombre);
        cliente.setApellido(apellido);
        cliente.setDni(dni);
        iClienteRepository.save(cliente);
    }

    @Override
    public List<Cliente> getAllClientes() {
        return iClienteRepository.findAll();
    }

    @Override
    public Cliente getOneCliente(Long idCliente) {
        return iClienteRepository.findById(idCliente).orElseThrow();
    }

    @Override
    public void deleteCliente(Long idCliente) {
        iClienteRepository.delete(this.getOneCliente(idCliente));
    }

    @Override
    public void updateCliente(Long idCliente, String nuevoNombre, String nuevoApellido, String nuevoDni) {
        Cliente nuevoCliente = this.getOneCliente(idCliente);
        if (nuevoNombre != null) {
            nuevoCliente.setNombre(nuevoNombre);
        }
        if (nuevoApellido != null) {
            nuevoCliente.setApellido(nuevoApellido);
        }
        if (nuevoDni != null) {
            nuevoCliente.setDni(nuevoDni);
        }
        iClienteRepository.save(nuevoCliente);
    }
}

