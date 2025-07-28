package com.valentinmendezf.ventas_api.service;

import com.valentinmendezf.ventas_api.dto.MayorVentaDto;
import com.valentinmendezf.ventas_api.dto.VentaDto;
import com.valentinmendezf.ventas_api.exceptions.NoHayStock;
import com.valentinmendezf.ventas_api.model.Cliente;
import com.valentinmendezf.ventas_api.model.Producto;
import com.valentinmendezf.ventas_api.model.Venta;
import com.valentinmendezf.ventas_api.repository.IVentaRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class VentaService implements IVentaService{
    @Autowired
    IClienteService iClienteService;
    @Autowired
    IVentaRepository iVentaRepository;
    @Autowired
    IProductoService iProductoService;
    @Override
    public void createVenta(Long idCliente, List<Producto> listaProductos) {
        Cliente cliente = iClienteService.getOneCliente(idCliente);
        LocalDate fechaActual = LocalDate.now();
        for (Producto producto : listaProductos){
            int cantidadProducto = this.obtenerCantidadProducto(listaProductos, producto.getCodigoProducto());
            boolean noHayStock = verificarStock(producto.getCodigoProducto(),cantidadProducto);
            if (noHayStock){
                throw new NoHayStock("No hay Stock del producto: "+ producto.getNombre());
            }
        }
        Double total = this.calcularTotal(listaProductos);
        Venta venta = new Venta();
        venta.setFechaVenta(fechaActual);
        venta.setCliente(cliente);
        venta.setTotal(total);
        venta.setListaProductos(listaProductos);
        iVentaRepository.save(venta);
        this.actualizarStock(listaProductos);
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
    public void editVenta(Long codigo, List<Producto> nuevaListaProductos) {
        Venta venta = iVentaRepository.findById(codigo).orElseThrow();
        venta.setListaProductos(nuevaListaProductos);
    }

    @Override
    public int obtenerCantidadProducto(List<Producto> listaProductos, Long codigoProducto) {
        int cantidad = 0;
        for (Producto p : listaProductos){
            if (codigoProducto.equals(p.getCodigoProducto())){
                cantidad +=1;
            }
        }
        return cantidad;
    }

    @Override
    public Double calcularTotal(List<Producto> listaProductos) {
        double total = 0.0;
        for (Producto producto : listaProductos){
            total = total + (producto.getCosto() * this.obtenerCantidadProducto(listaProductos, producto.getCodigoProducto()));
        }
        return total;
    }

    @Override
    public void actualizarStock(List<Producto> listaProductos) {
        for (Producto producto : listaProductos ){
            producto.setCantidadDisponible(producto.getCantidadDisponible() - this.obtenerCantidadProducto(listaProductos, producto.getCodigoProducto()));
        }
    }

    @Override
    public boolean verificarStock(Long codidoProducto, int cantidad) {
        Producto producto = iProductoService.getOneProducto(codidoProducto);
        return producto.getCantidadDisponible() < cantidad;
    }

    @Override
    public List<Producto> obtenerProductosPocoStock() {
        List<Producto> productosPocoStock = new ArrayList<>();
        List<Producto> listaProductos = iProductoService.getAllProductos();
        for (Producto producto : listaProductos){
            if (producto.getCantidadDisponible() < 5){
                productosPocoStock.add(producto);
            }
        }
        return productosPocoStock;
    }

    @Override
    public List<Producto> obtenerProductosVenta(Long codigoVenta) {
        return this.getOneVenta(codigoVenta).getListaProductos();
    }

    @Override
    public VentaDto obtenerCantidadVentasYMontoTotal(LocalDate fechaVenta) {
        List<Venta> listaVentas = this.getAllVentas();
        List<Venta> ventasDelDia = new ArrayList<>();
        int cantVentas = 0;
        for (Venta venta : listaVentas){
            if (venta.getFechaVenta().equals(fechaVenta)){
                ventasDelDia.add(venta);
                cantVentas+=1;
            }
        }
        double monto = 0.0;
        for (Venta venta : ventasDelDia){
            monto+=venta.getTotal();
        }
        return new VentaDto(monto,cantVentas);
    }

    @Override
    public MayorVentaDto obtenerMayorVenta() {
        List<Venta> listaVentas = this.getAllVentas();
        double mayorMonto=0.0;
        Long mayorVenta = 0L;
        for (Venta venta : listaVentas){
            if (venta.getTotal() > mayorMonto){
                mayorMonto = venta.getTotal();
                mayorVenta = venta.getCodigoVenta();
            }
        }
        Venta ventaMayor = this.getOneVenta(mayorVenta);
        return new MayorVentaDto(ventaMayor.getCodigoVenta(), ventaMayor.getTotal(),
                this.obtenerCantidadProducto(ventaMayor.getListaProductos()),
                ventaMayor.getCliente().getNombre(),
                ventaMayor.getCliente().getApellido());
    }

    @Override
    public int obtenerCantidadProducto(List<Producto> listaProductos) {
        int contador = 0;
        for (Producto producto : listaProductos){
            contador+=1;
        }
        return contador;
    }
}
