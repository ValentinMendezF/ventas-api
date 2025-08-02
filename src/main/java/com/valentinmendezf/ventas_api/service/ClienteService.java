package com.valentinmendezf.ventas_api.service;

import com.valentinmendezf.ventas_api.dto.ClienteDTO;
import com.valentinmendezf.ventas_api.model.Cliente;
import com.valentinmendezf.ventas_api.repository.IClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
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
    public List<ClienteDTO> getAllClientes() {
        List<Cliente> listaClientes = iClienteRepository.findAll();
        List<ClienteDTO> listaClientesDTO = new ArrayList<>();
        for (Cliente cliente : listaClientes){
            listaClientesDTO.add(new ClienteDTO(
                    cliente.getIdCliente(),
                    cliente.getNombre(),
                    cliente.getApellido(),
                    cliente.getDni()));
        }
        return listaClientesDTO;
    }

    @Override
    public ClienteDTO getOneCliente(Long idCliente) {
        Cliente cliente = iClienteRepository.findById(idCliente).orElseThrow();
        return new ClienteDTO(
                cliente.getIdCliente(),
                cliente.getNombre(),
                cliente.getApellido(),
                cliente.getDni());
    }

    @Override
    public void deleteCliente(Long idCliente) {
        Cliente cliente = iClienteRepository.findById(idCliente).orElseThrow();
        iClienteRepository.delete(cliente);
    }

    @Override
    public void updateCliente(Long idCliente, String nuevoNombre, String nuevoApellido, String nuevoDni) {
        Cliente nuevoCliente = iClienteRepository.findById(idCliente).orElseThrow();
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

