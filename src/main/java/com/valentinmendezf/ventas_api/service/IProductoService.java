package com.valentinmendezf.ventas_api.service;

import com.valentinmendezf.ventas_api.model.Producto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IProductoService {
    void createProducto(String nombre, String marca, Double costo, int cantidadDisponible);
    List<Producto> getAllProductos();
    Producto getOneProducto(Long codigo);
    void deleteProducto(Long codigo);
    void updateProducto(Long codigo, String nuevoNombre, String nuevaMarca, Double nuevoCosto,
                        int nuevaCantidadDisponible);
}
