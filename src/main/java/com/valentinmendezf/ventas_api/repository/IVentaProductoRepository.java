package com.valentinmendezf.ventas_api.repository;

import com.valentinmendezf.ventas_api.model.VentaProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IVentaProductoRepository extends JpaRepository<VentaProducto,Long> {
}
