package com.valentinmendezf.ventas_api.service;

import com.valentinmendezf.ventas_api.dto.ProductoEntradaDTO;
import com.valentinmendezf.ventas_api.dto.ProductoSalidaDTO;
import com.valentinmendezf.ventas_api.model.Producto;
import com.valentinmendezf.ventas_api.repository.IProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class ProductoService implements IProductoService{
    @Autowired
    IProductoRepository iProductoRepository;

    @Override
    public void createProducto(String nombre, String marca, Double costo, Integer cantidadDisponible) {
        Producto producto = new Producto();
        producto.setNombre(nombre);
        producto.setMarca(marca);
        producto.setCosto(costo);
        producto.setCantidadDisponible(cantidadDisponible);
        iProductoRepository.save(producto);
    }

    @Override
    public List<ProductoSalidaDTO> getAllProductos() {
        List<Producto> productos = iProductoRepository.findAll();
        List<ProductoSalidaDTO> productosSalidaDTO = new ArrayList<>();
        for (Producto producto : productos){
            productosSalidaDTO.add(new ProductoSalidaDTO(
                    producto.getCodigoProducto(),
                    producto.getNombre(),
                    producto.getMarca(),
                    producto.getCosto(),
                    producto.getCantidadDisponible()));
        }
        return productosSalidaDTO;
    }

    @Override
    public ProductoSalidaDTO getOneProducto(Long codigo) {
        Producto producto = iProductoRepository.findById(codigo).orElseThrow();
        return new ProductoSalidaDTO(
                producto.getCodigoProducto(),
                producto.getNombre(),
                producto.getMarca(),
                producto.getCosto(),
                producto.getCantidadDisponible());
    }

    @Override
    public void deleteProducto(Long codigo) {
        Producto producto = iProductoRepository.findById(codigo).orElseThrow();
        iProductoRepository.delete(producto);
    }

    @Override
    public void updateProducto(Long codigo, String nuevoNombre, String nuevaMarca, Double nuevoCosto,
                               Integer nuevaCantidadDisponible) {
        Producto nuevoProducto = iProductoRepository.findById(codigo).orElseThrow();
        if (nuevoNombre != null){
            nuevoProducto.setNombre(nuevoNombre);
        }
        if (nuevaMarca != null){
            nuevoProducto.setMarca(nuevaMarca);
        }
        if (nuevoCosto != null){
            nuevoProducto.setCosto(nuevoCosto);
        }
        if (nuevaCantidadDisponible != null){
            nuevoProducto.setCantidadDisponible(nuevaCantidadDisponible);
        }
        iProductoRepository.save(nuevoProducto);
    }

}
