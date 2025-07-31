package com.valentinmendezf.ventas_api.service;

import com.valentinmendezf.ventas_api.dto.ProductoSalidaDTO;

import java.util.List;

public interface IProductoService {
    void createProducto(String nombre, String marca, Double costo, Integer cantidadDisponible);
    List<ProductoSalidaDTO> getAllProductos();
    ProductoSalidaDTO getOneProducto(Long codigo);
    void deleteProducto(Long codigo);
    void updateProducto(Long codigo, String nuevoNombre, String nuevaMarca, Double nuevoCosto,
                        Integer nuevaCantidadDisponible);
}
