package com.valentinmendezf.ventas_api.service;

import com.valentinmendezf.ventas_api.dto.MayorVentaDto;
import com.valentinmendezf.ventas_api.dto.VentaDto;
import com.valentinmendezf.ventas_api.model.Producto;
import com.valentinmendezf.ventas_api.model.Venta;
import com.valentinmendezf.ventas_api.model.VentaProducto;

import java.time.LocalDate;
import java.util.List;

public interface IVentaService {
    void createVenta(Long idCliente, List<VentaProducto> listaVentaProductos);
    List<Venta> getAllVentas();
    Venta getOneVenta(Long codigo);
    void deleteVenta(Long codigo);
    void editVenta(Long codigo, LocalDate nuevaFechaVenta, Long nuevoCliente, List<VentaProducto> nuevaListaProductos);
    double calcularTotal(List<VentaProducto> listaVentaProductos);
    void actualizarStock(Producto producto, int cantidad);
    boolean verificarStock(Producto producto, int cantidad);
    List<Producto> obtenerProductosPocoStock();
    List<VentaProducto> obtenerProductosVenta(Long codigoVenta);
    VentaDto obtenerCantidadVentasYMontoTotal(LocalDate fechaVenta);
    MayorVentaDto obtenerMayorVenta();
    void devolverProductos(List<VentaProducto> listaProductos);
}
