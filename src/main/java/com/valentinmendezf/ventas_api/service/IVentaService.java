package com.valentinmendezf.ventas_api.service;

import com.valentinmendezf.ventas_api.dto.MayorVentaDto;
import com.valentinmendezf.ventas_api.dto.VentaDto;
import com.valentinmendezf.ventas_api.model.Producto;
import com.valentinmendezf.ventas_api.model.Venta;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public interface IVentaService {
    void createVenta(Long idCliente,List<Producto> listaProductos);
    List<Venta> getAllVentas();
    Venta getOneVenta(Long codigo);
    void deleteVenta(Long codigo);
    void editVenta(Long codigo, List<Producto> nuevaListaProductos);
    int obtenerCantidadProducto(List<Producto> listaProductos, Long codigoProducto);
    Double calcularTotal(List<Producto> listaProductos);
    void actualizarStock(List<Producto> listaProductos);
    boolean verificarStock(Long codidoProducto, int cantidad);
    List<Producto> obtenerProductosPocoStock();
    List<Producto> obtenerProductosVenta(Long codigoVenta);
    VentaDto obtenerCantidadVentasYMontoTotal(LocalDate fechaVenta);
    MayorVentaDto obtenerMayorVenta();
    int obtenerCantidadProducto(List<Producto> listaProductos);
}
