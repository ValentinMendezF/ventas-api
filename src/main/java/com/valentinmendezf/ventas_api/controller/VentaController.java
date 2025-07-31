package com.valentinmendezf.ventas_api.controller;

import com.valentinmendezf.ventas_api.dto.*;
import com.valentinmendezf.ventas_api.service.IVentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
public class VentaController {
    @Autowired
    IVentaService iVentaService;
    @PostMapping("/ventas/create")
    public ResponseEntity<String> createVenta(@RequestParam Long id, @RequestBody List<ProductoEntradaDTO> listaProductoEntradaDTO){
        try {
            iVentaService.createVenta(id, listaProductoEntradaDTO);
            return ResponseEntity.ok("Venta creada correctamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al crear venta: " + e.getMessage());
        }
    }
    @GetMapping("/ventas")
    public ResponseEntity<List<VentaDTO>> getAllVentas(){
        return ResponseEntity.ok(iVentaService.getAllVentas());
    }
    @GetMapping("/ventas/{codigo}")
    public ResponseEntity<VentaDTO> getOneVenta(@PathVariable Long codigo){
        try {
            return ResponseEntity.ok(iVentaService.getOneVenta(codigo));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
    @DeleteMapping("/ventas/delete/{codigo}")
    public ResponseEntity<String> deleteVenta(@PathVariable Long codigo){
        try {
            iVentaService.deleteVenta(codigo);
            return ResponseEntity.ok("Venta eliminada con éxito");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al eliminar venta: " + e.getMessage());
        }
    }
    @PutMapping("/ventas/update/{codigo}")
    public ResponseEntity<String> updateVenta(@PathVariable Long codigo, @RequestBody List<ProductoEntradaDTO> listaProductosDTO){
        try {
            iVentaService.updateVenta(codigo, listaProductosDTO);
            return ResponseEntity.ok("Venta editada con éxito");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al editar venta: " + e.getMessage());
        }
    }
    @GetMapping("/ventas/falta_stock")
    public ResponseEntity<List<ProductoSalidaDTO>> getProductosPocoStock(){
        return ResponseEntity.ok(iVentaService.getProductosPocoStock());
    }
    @GetMapping("/ventas/productos/{codigo}")
    public ResponseEntity<List<ProductoEntradaDTO>> getProductosVenta(@PathVariable Long codigo){
        return ResponseEntity.ok(iVentaService.getProductosVenta(codigo));
    }
    @GetMapping("/ventas/dia/{fecha}")
    public ResponseEntity<VentaDiaDTO> getVentasDia(@PathVariable LocalDate fecha){
        return ResponseEntity.ok(iVentaService.getVentasDia(fecha));
    }
    @GetMapping("/ventas/mayor_venta")
    public ResponseEntity<VentaMayorDTO> getVentaMayor(){
        return ResponseEntity.ok(iVentaService.getVentaMayor());
    }
}
