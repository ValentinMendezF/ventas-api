package com.valentinmendezf.ventas_api.service;

import com.valentinmendezf.ventas_api.model.Producto;
import com.valentinmendezf.ventas_api.repository.IProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ProductoService implements IProductoService{
    @Autowired
    IProductoRepository iProductoRepository;

    @Override
    public void createProducto(String nombre, String marca, Double costo, int cantidadDisponible) {
        Producto producto = new Producto();
        producto.setNombre(nombre);
        producto.setMarca(marca);
        producto.setCosto(costo);
        producto.setCantidadDisponible(cantidadDisponible);
        iProductoRepository.save(producto);
    }

    @Override
    public List<Producto> getAllProductos() {
        return iProductoRepository.findAll();
    }

    @Override
    public Producto getOneProducto(Long codigo) {
        return iProductoRepository.findById(codigo).orElseThrow();
    }

    @Override
    public void deleteProducto(Long codigo) {
        iProductoRepository.delete(this.getOneProducto(codigo));
    }

    @Override
    public void updateProducto(Long codigo, String nuevoNombre, String nuevaMarca, Double nuevoCosto,
                               int nuevaCantidadDisponible) {
        Producto nuevoProducto = this.getOneProducto(codigo);
        if (nuevoNombre != null){
            nuevoProducto.setNombre(nuevoNombre);
        }
        if (nuevaMarca != null){
            nuevoProducto.setMarca(nuevaMarca);
        }
        if (nuevoCosto != null){
            nuevoProducto.setCosto(nuevoCosto);
        }
        if (nuevaCantidadDisponible != 0){
            nuevoProducto.setCantidadDisponible(nuevaCantidadDisponible);
        }
        iProductoRepository.save(nuevoProducto);
    }

}
