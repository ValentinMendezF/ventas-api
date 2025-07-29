package com.valentinmendezf.ventas_api.service;

import com.valentinmendezf.ventas_api.model.VentaProducto;

public interface IVentaProductoService {
    VentaProducto createVentaProducto(Long codigoProducto,int cantidad);
    void deleteVentaProducto(Long id);
}
