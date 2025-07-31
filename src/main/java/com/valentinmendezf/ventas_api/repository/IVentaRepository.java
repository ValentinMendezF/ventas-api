package com.valentinmendezf.ventas_api.repository;

import com.valentinmendezf.ventas_api.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IVentaRepository extends JpaRepository<Venta,Long> {
}
