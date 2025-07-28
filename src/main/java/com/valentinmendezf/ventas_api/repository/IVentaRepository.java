package com.valentinmendezf.ventas_api.repository;

import com.valentinmendezf.ventas_api.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IVentaRepository extends JpaRepository<Venta,Long> {
}
