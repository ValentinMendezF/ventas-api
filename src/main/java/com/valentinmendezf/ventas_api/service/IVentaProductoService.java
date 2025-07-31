package com.valentinmendezf.ventas_api.service;

import com.valentinmendezf.ventas_api.model.Producto;
import com.valentinmendezf.ventas_api.model.Venta;
import com.valentinmendezf.ventas_api.model.VentaProducto;

public interface IVentaProductoService {
    VentaProducto createVentaProducto(Venta venta, Producto producto, int cantidad);

}
