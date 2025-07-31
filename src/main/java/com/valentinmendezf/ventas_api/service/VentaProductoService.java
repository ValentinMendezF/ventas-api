package com.valentinmendezf.ventas_api.service;

import com.valentinmendezf.ventas_api.model.Producto;
import com.valentinmendezf.ventas_api.model.Venta;
import com.valentinmendezf.ventas_api.model.VentaProducto;
import com.valentinmendezf.ventas_api.repository.IVentaProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VentaProductoService implements IVentaProductoService {
    @Autowired
    IVentaProductoRepository iVentaProductoRepository;

    @Override
    @Transactional
    public VentaProducto createVentaProducto(Venta venta, Producto producto, int cantidad) {
        VentaProducto ventaProducto = new VentaProducto();
        ventaProducto.setVenta(venta);
        ventaProducto.setProducto(producto);
        ventaProducto.setCantidad(cantidad);
        iVentaProductoRepository.save(ventaProducto);
        return ventaProducto;
    }

}
