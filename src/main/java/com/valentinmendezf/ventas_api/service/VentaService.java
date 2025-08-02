package com.valentinmendezf.ventas_api.service;

import com.valentinmendezf.ventas_api.dto.*;
import com.valentinmendezf.ventas_api.exceptions.NoHayStock;
import com.valentinmendezf.ventas_api.model.Cliente;
import com.valentinmendezf.ventas_api.model.Producto;
import com.valentinmendezf.ventas_api.model.Venta;
import com.valentinmendezf.ventas_api.model.VentaProducto;
import com.valentinmendezf.ventas_api.repository.IClienteRepository;
import com.valentinmendezf.ventas_api.repository.IProductoRepository;
import com.valentinmendezf.ventas_api.repository.IVentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class VentaService implements IVentaService {
    @Autowired
    IVentaRepository iVentaRepository;
    @Autowired
    IClienteRepository iClienteRepository;
    @Autowired
    IVentaProductoService iVentaProductoService;
    @Autowired
    IProductoRepository iProductoRepository;
    @Autowired
    IProductoService iProductoService;
    @Autowired
    IClienteService iClienteService;

    @Override
    @Transactional
    public void createVenta(Long idCliente, List<ProductoEntradaDTO> listaProductosDTO) {
        Set<VentaProducto> listaVentasProductos = new HashSet<>();
        Venta venta = new Venta();
        for (ProductoEntradaDTO productoEntradaDTO : listaProductosDTO) {
            Producto producto = iProductoRepository.findById(productoEntradaDTO.getCodigoProducto()).orElseThrow();
            if (verificarStock(productoEntradaDTO)) {
                throw new NoHayStock("No hay stock del producto: " + producto.getNombre());
            }
            listaVentasProductos.add(iVentaProductoService.createVentaProducto(venta, producto,
                    productoEntradaDTO.getCantidadComprada()));
            actualizarStock(producto, productoEntradaDTO);
        }
        Cliente cliente = iClienteRepository.findById(idCliente).orElseThrow();
        venta.setCliente(cliente);
        venta.setFechaVenta(LocalDate.now());
        venta.setListaVentasProductos(listaVentasProductos);
        venta.setTotal(calcularTotal(listaProductosDTO));
        iVentaRepository.save(venta);
    }
    @Override
    @Transactional
    public void actualizarStock(Producto producto, ProductoEntradaDTO productoEntradaDTO) {
        iProductoService.updateProducto(producto.getCodigoProducto(), null,null,null,
                producto.getCantidadDisponible() - productoEntradaDTO.getCantidadComprada());
    }
    @Override
    @Transactional
    public boolean verificarStock(ProductoEntradaDTO productoEntradaDTO) {
        Producto producto = iProductoRepository.findById(productoEntradaDTO.getCodigoProducto()).orElseThrow();
        return producto.getCantidadDisponible() < productoEntradaDTO.getCantidadComprada();
    }
    @Override
    @Transactional
    public Double calcularTotal(List<ProductoEntradaDTO> listaProductosDTO) {
        double total = 0.0;
        for (ProductoEntradaDTO productoEntradaDTO : listaProductosDTO) {
            Producto producto = iProductoRepository.findById(productoEntradaDTO.getCodigoProducto()).orElseThrow();
            total += producto.getCosto() * productoEntradaDTO.getCantidadComprada();
        }
        return total;
    }

    @Override
    @Transactional
    public List<VentaDTO> getAllVentas() {
        List<Venta> listaVentas = iVentaRepository.findAll();
        List<VentaDTO> listaVentasDTO = new ArrayList<>();
        for (Venta venta : listaVentas) {
            List<ProductoEntradaDTO> listaProductos = new ArrayList<>();
            for (VentaProducto ventaProducto : venta.getListaVentasProductos()) {
                listaProductos.add(new ProductoEntradaDTO(
                        ventaProducto.getProducto().getCodigoProducto(),
                        ventaProducto.getProducto().getNombre(),
                        ventaProducto.getProducto().getMarca(),
                        ventaProducto.getProducto().getCosto(),
                        ventaProducto.getCantidad()));
            }
            listaVentasDTO.add(new VentaDTO(
                    venta.getCodigoVenta(),
                    venta.getFechaVenta(),
                    venta.getTotal(),
                    listaProductos,
                    venta.getCliente().getIdCliente(),
                    venta.getCliente().getNombre()));
        }
        return listaVentasDTO;
    }

    @Override
    @Transactional
    public VentaDTO getOneVenta(Long codigo) {
        Venta venta = iVentaRepository.findById(codigo).orElseThrow();
        List<ProductoEntradaDTO> listaProductos = new ArrayList<>();
        for (VentaProducto ventaProducto : venta.getListaVentasProductos()) {
            listaProductos.add(new ProductoEntradaDTO(
                    ventaProducto.getProducto().getCodigoProducto(),
                    ventaProducto.getProducto().getNombre(),
                    ventaProducto.getProducto().getMarca(),
                    ventaProducto.getProducto().getCosto(),
                    ventaProducto.getCantidad()));
        }
        return new VentaDTO(
                venta.getCodigoVenta(),
                venta.getFechaVenta(),
                venta.getTotal(),
                listaProductos,
                venta.getCliente().getIdCliente(),
                venta.getCliente().getNombre());
    }

    @Override
    @Transactional
    public void deleteVenta(Long codigo) {
        Venta venta = iVentaRepository.findById(codigo).orElseThrow();
        for (VentaProducto ventaProducto : venta.getListaVentasProductos()){
            devolverProductos(ventaProducto.getProducto(),ventaProducto.getCantidad());
        }
        iVentaRepository.deleteById(codigo);
    }


    @Override
    @Transactional
    public void updateVenta(Long codigo, List<ProductoEntradaDTO> listaProductosDTO) {
        Venta venta = iVentaRepository.findById(codigo).orElseThrow();
        for (VentaProducto ventaProducto : venta.getListaVentasProductos()){
            devolverProductos(ventaProducto.getProducto(),ventaProducto.getCantidad());
        }
        Cliente cliente = venta.getCliente();

        deleteVenta(codigo);
        createVenta(cliente.getIdCliente(), listaProductosDTO);
    }

    @Override
    @Transactional
    public void devolverProductos(Producto producto,int cantidad) {
        iProductoService.updateProducto(producto.getCodigoProducto(), null,null,null,
                producto.getCantidadDisponible() + cantidad);
    }

    @Override
    public List<ProductoSalidaDTO> getProductosPocoStock() {
        List<Producto> listaProductos = iProductoRepository.findAll();
        List<ProductoSalidaDTO> productosPocoStockSalidaDTO = new ArrayList<>();
        for (Producto producto : listaProductos){
            if (producto.getCantidadDisponible() < 5){
                productosPocoStockSalidaDTO.add(new ProductoSalidaDTO(producto.getCodigoProducto(), producto.getNombre(),
                        producto.getMarca(), producto.getCosto(), producto.getCantidadDisponible()));
            }
        }
        return productosPocoStockSalidaDTO;
    }

    @Override
    public List<ProductoEntradaDTO> getProductosVenta(Long codigo) {
        VentaDTO ventaDTO = getOneVenta(codigo);
        return ventaDTO.getListaProductos();
    }

    @Override
    public VentaDiaDTO getVentasDia(LocalDate fecha) {
        List<VentaDTO> listaVentasDTO = getAllVentas();
        List<VentaDTO> ventasDia = new ArrayList<>();
        double total = 0.0;
        int cantidad = 0;
        for (VentaDTO ventaDTO : listaVentasDTO){
            if (ventaDTO.getFechaVenta().equals(fecha)){
                ventasDia.add(ventaDTO);
            }
        }
        for (VentaDTO ventaDTO : ventasDia){
            total += ventaDTO.getTotal();
            cantidad += 1;
        }
        return new VentaDiaDTO(total,cantidad);
    }

    @Override
    public VentaMayorDTO getVentaMayor() {
        List<VentaDTO> listaVentasDTO = getAllVentas();
        VentaDTO mayorVentaDTO = listaVentasDTO.get(0);
        for (VentaDTO ventaDTO : listaVentasDTO){
            if (ventaDTO.getTotal() > mayorVentaDTO.getTotal()){
                mayorVentaDTO = ventaDTO;
            }
        }
        int cantidadProductos = 0;
        for (ProductoEntradaDTO productoEntradaDTO : mayorVentaDTO.getListaProductos()){
            cantidadProductos += productoEntradaDTO.getCantidadComprada();
        }
        ClienteDTO clienteDTO = iClienteService.getOneCliente(mayorVentaDTO.getIdCliente());
        return new VentaMayorDTO(mayorVentaDTO.getCodigoVenta(), mayorVentaDTO.getTotal(), cantidadProductos,
                clienteDTO.getNombre(), clienteDTO.getApellido());
    }

}
