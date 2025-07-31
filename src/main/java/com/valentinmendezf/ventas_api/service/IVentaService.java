package com.valentinmendezf.ventas_api.service;

import com.valentinmendezf.ventas_api.dto.*;
import com.valentinmendezf.ventas_api.model.Producto;

import java.time.LocalDate;
import java.util.List;

public interface IVentaService {
    void createVenta(Long idCliente, List<ProductoEntradaDTO> listaProductosDTO);
    void actualizarStock(Producto producto, ProductoEntradaDTO productoEntradaDTO);
    boolean verificarStock(ProductoEntradaDTO productoEntradaDTO);
    Double calcularTotal(List<ProductoEntradaDTO> listaProductosDTO);
    List<VentaDTO> getAllVentas();
    VentaDTO getOneVenta(Long codigo);
    void deleteVenta(Long codigo);
    void updateVenta(Long codigo, List<ProductoEntradaDTO> listaProductosDTO);
    void devolverProductos(Producto producto, int cantidad);

    List<ProductoSalidaDTO> getProductosPocoStock();

    List<ProductoEntradaDTO> getProductosVenta(Long codigo);

    VentaDiaDTO getVentasDia(LocalDate fecha);

    VentaMayorDTO getVentaMayor();
}
