package com.valentinmendezf.ventas_api.service;

import com.valentinmendezf.ventas_api.model.VentaProducto;
import com.valentinmendezf.ventas_api.repository.IVentaProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VentaProductoService implements IVentaProductoService{
    @Autowired
    IVentaProductoRepository iVentaProductoRepository;
    @Autowired
    IProductoService iProductoService;
    @Override
    public VentaProducto createVentaProducto(Long codigoProducto, int cantidad) {
        VentaProducto ventaProducto = new VentaProducto();
        ventaProducto.setProducto(iProductoService.getOneProducto(codigoProducto));
        iVentaProductoRepository.save(ventaProducto);
        return ventaProducto;
    }

    @Override
    public void deleteVentaProducto(Long id) {
        iVentaProductoRepository.deleteById(id);
    }
}
