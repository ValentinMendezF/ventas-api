package com.valentinmendezf.ventas_api.service;

import com.valentinmendezf.ventas_api.dto.MayorVentaDto;
import com.valentinmendezf.ventas_api.dto.VentaDto;
import com.valentinmendezf.ventas_api.exceptions.NoHayStock;
import com.valentinmendezf.ventas_api.model.Cliente;
import com.valentinmendezf.ventas_api.model.Producto;
import com.valentinmendezf.ventas_api.model.Venta;
import com.valentinmendezf.ventas_api.model.VentaProducto;
import com.valentinmendezf.ventas_api.repository.IVentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Service
public class VentaService implements IVentaService{
    @Autowired
    IClienteService iClienteService;
    @Autowired
    IVentaRepository iVentaRepository;
    @Autowired
    IProductoService iProductoService;
    @Autowired
    IVentaProductoService iVentaProductoService;

    @Override
    public void createVenta(Long idCliente, List<VentaProducto> listaVentaProductos) {
        List<VentaProducto> lista = new ArrayList<>();
        Venta venta = new Venta();
        for (VentaProducto ventaProducto : listaVentaProductos){
            Producto producto = iProductoService.getOneProducto(ventaProducto.getProducto().getCodigoProducto());
            boolean noHayStock = verificarStock(producto,ventaProducto.getCantidad());
            if (noHayStock){
                throw new NoHayStock("No hay suficiente stock del producto " + producto.getNombre());
            }
            VentaProducto venProducto = iVentaProductoService.createVentaProducto(ventaProducto.getProducto().getCodigoProducto(), ventaProducto.getCantidad());
            lista.add(venProducto);
            actualizarStock(producto, ventaProducto.getCantidad());
        }
        venta.setCliente(iClienteService.getOneCliente(idCliente));
        venta.setFechaVenta(LocalDate.now());
        venta.setTotal(calcularTotal(listaVentaProductos));
        venta.setListaProductos(lista);
        iVentaRepository.save(venta);
    }

    @Override
    public List<Venta> getAllVentas() {
        return iVentaRepository.findAll();
    }

    @Override
    public Venta getOneVenta(Long codigo) {
        return iVentaRepository.findById(codigo).orElseThrow();
    }

    @Override
    public void deleteVenta(Long codigo) {
        iVentaRepository.deleteById(codigo);
    }

    @Override
    public void editVenta(Long codigo, LocalDate nuevaFechaVenta, Long nuevoCliente, List<VentaProducto> nuevaListaProductos) {
        Venta venta = getOneVenta(codigo);
        if (nuevoCliente != null){
            venta.setCliente(iClienteService.getOneCliente(nuevoCliente));
        }
        if (nuevaFechaVenta != null){
            venta.setFechaVenta(nuevaFechaVenta);
        }
        if (nuevaListaProductos != null){
            devolverProductos(venta.getListaProductos());
            List<VentaProducto> lista = new ArrayList<>();
            for (VentaProducto ventaProducto : nuevaListaProductos){
                Producto producto = iProductoService.getOneProducto(ventaProducto.getProducto().getCodigoProducto());
                boolean noHayStock = verificarStock(producto,ventaProducto.getCantidad());
                if (noHayStock){
                    throw new NoHayStock("No hay suficiente stock del producto " + producto.getNombre());
                }
                VentaProducto venProducto = iVentaProductoService.createVentaProducto(ventaProducto.getProducto().getCodigoProducto(), ventaProducto.getCantidad());
                lista.add(venProducto);
                actualizarStock(producto, ventaProducto.getCantidad());
            }
            venta.setTotal(calcularTotal(nuevaListaProductos));
            venta.setListaProductos(lista);
        }
        iVentaRepository.save(venta);
    }

    public void devolverProductos(List<VentaProducto> listaProductos) {
        for (VentaProducto ventaProducto : listaProductos){
            Producto producto = iProductoService.getOneProducto(ventaProducto.getProducto().getCodigoProducto());
            producto.setCantidadDisponible(producto.getCantidadDisponible() + ventaProducto.getCantidad());
        }
    }

    @Override
    public double calcularTotal(List<VentaProducto> listaVentaProductos) {
        double total = 0.0;
        for (VentaProducto ventaProducto : listaVentaProductos){
            Producto producto = iProductoService.getOneProducto(ventaProducto.getProducto().getCodigoProducto());
            total += producto.getCosto() + ventaProducto.getCantidad();
        }
        return total;
    }

    @Override
    public void actualizarStock(Producto producto, int cantidadVendida) {
        producto.setCantidadDisponible(producto.getCantidadDisponible() - cantidadVendida);
    }

    @Override
    public boolean verificarStock(Producto producto, int cantidad) {
        return producto.getCantidadDisponible() < cantidad;
    }

    @Override
    public List<Producto> obtenerProductosPocoStock() {
        return List.of();
    }

    @Override
    public List<Producto> obtenerProductosVenta() {
        return List.of();
    }

    @Override
    public VentaDto obtenerCantidadVentasYMontoTotal() {
        return null;
    }

    @Override
    public MayorVentaDto obtenerMayorVenta() {
        return null;
    }
}
