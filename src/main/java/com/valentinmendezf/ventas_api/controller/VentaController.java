package com.valentinmendezf.ventas_api.controller;


import com.valentinmendezf.ventas_api.dto.MayorVentaDto;
import com.valentinmendezf.ventas_api.dto.VentaDto;
import com.valentinmendezf.ventas_api.model.Producto;
import com.valentinmendezf.ventas_api.model.Venta;
import com.valentinmendezf.ventas_api.model.VentaProducto;
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
    public ResponseEntity<String> createVenta(@RequestParam Long idCliente, @RequestBody List<VentaProducto> listaProductos){
        try {
            iVentaService.createVenta(idCliente,listaProductos);
            return ResponseEntity.ok("Venta creada correctamente.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al crear venta: " + e.getMessage());
        }
    }

    @GetMapping("/ventas/get")
    public ResponseEntity<List<Venta>> getAllVentas(){
        return ResponseEntity.ok(iVentaService.getAllVentas());
    }

    @GetMapping("/ventas/get/{codigo}")
    public ResponseEntity<Venta> getOneVenta(@PathVariable Long codigo){
        try {
            return ResponseEntity.ok(iVentaService.getOneVenta(codigo));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/ventas/delete/{codigo}")
    public ResponseEntity<String> deleteVenta(@PathVariable Long codigo){
        try {
            iVentaService.deleteVenta(codigo);
            return ResponseEntity.ok("Venta eliminada correctamente.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al eliminar venta:" + e.getMessage());
        }
    }
    @PutMapping("/ventas/update/{codigo}")
    public ResponseEntity<String> updateVenta(@PathVariable Long codigo,
                                              @RequestParam(required = false, name = "nuevaFecha") LocalDate nuevaFechaVenta,
                                              @RequestParam(required = false, name = "nuevoCliente") Long nuevoCliente,
                                              @RequestBody(required = false) List<VentaProducto> nuevaListaProductos){
        try {
            iVentaService.editVenta(codigo,nuevaFechaVenta,nuevoCliente,nuevaListaProductos);
            return ResponseEntity.ok("Venta editada correctamente");
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Error al editar venta: " + e.getMessage());
        }
    }
    @GetMapping("/ventas/falta_stock")
    public ResponseEntity<List<Producto>> obtenerProductosPocoStock(){
        try {
            return ResponseEntity.ok(iVentaService.obtenerProductosPocoStock());
        }catch (Exception e){
            return ResponseEntity.badRequest().body(null);
        }

    }
    @GetMapping("/ventas/productos/{codigo}")
    public ResponseEntity<List<VentaProducto>> obtenerProductosVenta(@PathVariable Long codigo){
        try {
            return ResponseEntity.ok(iVentaService.obtenerProductosVenta(codigo));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
    @GetMapping("/ventas/{fecha}")
    public ResponseEntity<VentaDto> obtenerCantidadVentasYMontoTotal(@RequestParam LocalDate fechaVenta){
        try {
            return ResponseEntity.ok(iVentaService.obtenerCantidadVentasYMontoTotal(fechaVenta));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
    @GetMapping("/ventas/mayor_venta")
    public ResponseEntity<MayorVentaDto> obtenerMayorVenta(){
        try {
            return ResponseEntity.ok(iVentaService.obtenerMayorVenta());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
