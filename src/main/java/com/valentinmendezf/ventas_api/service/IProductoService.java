package com.valentinmendezf.ventas_api.service;

import com.valentinmendezf.ventas_api.model.Producto;

import java.util.List;

public interface IProductoService {
    void createProducto(String nombre, String marca, Double costo, Integer cantidadDisponible);
    List<Producto> getAllProductos();
    Producto getOneProducto(Long codigo);
    void deleteProducto(Long codigo);
    void updateProducto(Long codigo, String nuevoNombre, String nuevaMarca, Double nuevoCosto,
                        Integer nuevaCantidadDisponible);
}
