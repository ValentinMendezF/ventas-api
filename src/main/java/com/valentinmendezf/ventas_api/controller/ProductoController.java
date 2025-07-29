package com.valentinmendezf.ventas_api.controller;

import com.valentinmendezf.ventas_api.model.Producto;
import com.valentinmendezf.ventas_api.service.IProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductoController {
    @Autowired
    IProductoService iProductoService;
    @PostMapping("/productos/create")
    public ResponseEntity<String> createProducto(@RequestParam String nombre,
                                                 @RequestParam String marca,
                                                 @RequestParam Double costo,
                                                 @RequestParam Integer cantidadDisponible ){
        try {
            iProductoService.createProducto(nombre,marca,costo,cantidadDisponible);
            return ResponseEntity.ok("Producto creado correctamente.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al crear producto: " + e.getMessage());
        }
    }
    @GetMapping("/productos")
    public ResponseEntity<List<Producto>> getAllProductos(){
        return ResponseEntity.ok(iProductoService.getAllProductos());
    }
    @GetMapping("/productos/{codigo}")
    public ResponseEntity<Producto> getOneProducto(@PathVariable Long codigo){
        try {
            return ResponseEntity.ok(iProductoService.getOneProducto(codigo));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
    @DeleteMapping("/productos/delete/{codigo}")
    public ResponseEntity<String> deleteProducto(@PathVariable Long codigo){
        try {
            iProductoService.deleteProducto(codigo);
            return ResponseEntity.ok("Producto eliminado correctamente.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al eliminar producto: " + e.getMessage());
        }
    }
    @PutMapping("/productos/update/{codigo}")
    public ResponseEntity<String> updateProducto(@PathVariable Long codigo,
                                                 @RequestParam(required = false, name = "nombre") String nuevoNombre,
                                                 @RequestParam(required = false, name = "marca") String nuevaMarca,
                                                 @RequestParam(required = false, name = "costo") Double nuevoCosto,
                                                 @RequestParam(required = false, name = "cantidadDisponible") Integer nuevaCantidadDisponible){
        try {
            iProductoService.updateProducto(codigo, nuevoNombre, nuevaMarca, nuevoCosto, nuevaCantidadDisponible);
            return ResponseEntity.ok("Producto editado correctamente.");
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Error al editar producto: " + e.getMessage());
        }
    }

}
