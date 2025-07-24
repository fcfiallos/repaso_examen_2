package uce.edu.web.service;

import java.util.ArrayList;
import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import uce.edu.web.repository.IDetalleVentaRepo;
import uce.edu.web.repository.IProductoRepo;
import uce.edu.web.repository.IVentaRepo;
import uce.edu.web.repository.model.DetalleVenta;
import uce.edu.web.repository.model.Producto;
import uce.edu.web.repository.model.Venta;
import uce.edu.web.service.to.ProductoVentaTo;
import uce.edu.web.service.to.ValidacionStockTo;
import uce.edu.web.service.to.VentaProdTo;

@ApplicationScoped
public class VentaServiceImpl implements IVentaService{

    @Inject
    private IVentaRepo ventaRepo;
    @Inject
    private IDetalleVentaRepo detalleVentaRepo;
    @Inject
    private IProductoRepo productoRepo;

    @Override
    public ValidacionStockTo validarStock(String codigoBarras, Integer cantidad) {
        try {
            // Buscar el producto por código de barras
            Producto producto = productoRepo.seleccionarPorCodigo(codigoBarras);
            
            if (producto == null) {
                return new ValidacionStockTo(false, "Producto no encontrado", null);
            }
            
            // Validar si hay stock suficiente
            if (producto.getStock() < cantidad) {
                return new ValidacionStockTo(false, "Stock no disponible. Stock actual: " + producto.getStock(), producto);
            }
            
            return new ValidacionStockTo(true, "Stock disponible", producto);
            
        } catch (Exception e) {
            return new ValidacionStockTo(false, "Error al validar stock: " + e.getMessage(), null);
        }
    }

    @Override
    @Transactional
    public void crearVenta(VentaProdTo ventaProdTo) {
        try {
            // Crear la venta
            Venta venta = new Venta();
            venta.setNumeroVenta(ventaProdTo.getNumeroVenta());
            venta.setCedulaCliente(ventaProdTo.getCedulaCliente());
            
            double totalVenta = 0.0;
            List<DetalleVenta> detalleVentas = new ArrayList<>();
            
            // Procesar cada producto en la venta
            for (ProductoVentaTo productoVenta : ventaProdTo.getProductos()) {
                // Validar stock nuevamente antes de crear la venta
                ValidacionStockTo validacion = validarStock(productoVenta.getCodigoBarras(), productoVenta.getCantidad());
                
                if (!validacion.isStockDisponible()) {
                    throw new RuntimeException("Stock insuficiente para el producto: " + productoVenta.getCodigoBarras());
                }
                
                Producto producto = validacion.getProducto();
                
                // Calcular subtotal
                Double subtotal = producto.getPrecio() * productoVenta.getCantidad();
                
                // Crear detalle de venta
                DetalleVenta detalleVenta = new DetalleVenta();
                detalleVenta.setProducto(producto);
                detalleVenta.setCantidad(productoVenta.getCantidad());
                detalleVenta.setPrecioUnitario(producto.getPrecio());
                detalleVenta.setSubtotal(subtotal);
                detalleVenta.setVenta(venta);
                
                detalleVentas.add(detalleVenta);
                
                // Sumar al total
                totalVenta += subtotal;
                
                // Actualizar stock del producto
                producto.setStock(producto.getStock() - productoVenta.getCantidad());
                productoRepo.actualizar(producto);
            }
            
            // Establecer el total de la venta
            venta.setTotalVenta(totalVenta);
            venta.setDetalleVentas(detalleVentas);
            
            // Guardar la venta
            ventaRepo.insertar(venta);
            
            // Guardar los detalles de venta
            for (DetalleVenta detalle : detalleVentas) {
                detalleVentaRepo.insertar(detalle);
            }
            
        } catch (Exception e) {
            throw new RuntimeException("Error al crear la venta: " + e.getMessage());
        }
    }

}
