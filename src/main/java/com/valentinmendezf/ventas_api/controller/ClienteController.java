package com.valentinmendezf.ventas_api.controller;

import com.valentinmendezf.ventas_api.model.Cliente;
import com.valentinmendezf.ventas_api.service.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ClienteController {

    @Autowired
    IClienteService iClienteService;

    @PostMapping("/clientes/create")
    public ResponseEntity<String> createCliente(@RequestParam String nombre,
                                                @RequestParam String apellido,
                                                @RequestParam String dni) {
        try {
            iClienteService.createCliente(nombre, apellido, dni);
            return ResponseEntity.ok("Cliente creado correctamente.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al crear cliente: " + e.getMessage());
        }
    }

    @GetMapping("/clientes")
    public ResponseEntity<List<Cliente>> getAllClientes() {
        return ResponseEntity.ok(iClienteService.getAllClientes());
    }

    @GetMapping("/clientes/{id}")
    public ResponseEntity<Cliente> getOneCliente(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(iClienteService.getOneCliente(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/clientes/delete/{id}")
    public ResponseEntity<String> deleteCliente(@PathVariable Long id) {
        try {
            iClienteService.deleteCliente(id);
            return ResponseEntity.ok("Cliente eliminado correctamente.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al eliminar cliente: " + e.getMessage());
        }
    }

    @PutMapping("/clientes/update/{id}")
    public ResponseEntity<String> updateCliente(@PathVariable Long id,
                                                @RequestParam(required = false, name = "nombre") String nuevoNombre,
                                                @RequestParam(required = false, name = "apellido") String nuevoApellido,
                                                @RequestParam(required = false, name = "dni") String nuevoDni) {
        try {
            iClienteService.updateCliente(id, nuevoNombre, nuevoApellido,nuevoDni);
            return ResponseEntity.ok("Cliente editado correctamente.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al editar cliente: " + e.getMessage());
        }
    }
}

