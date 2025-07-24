package uce.edu.web.service.to;

import java.util.List;

public class VentaProdTo {
    private String numeroVenta;
    private String cedulaCliente;
    private List<ProductoVentaTo> productos;

    public String getNumeroVenta() {
        return numeroVenta;
    }

    public void setNumeroVenta(String numeroVenta) {
        this.numeroVenta = numeroVenta;
    }

    public String getCedulaCliente() {
        return cedulaCliente;
    }

    public void setCedulaCliente(String cedulaCliente) {
        this.cedulaCliente = cedulaCliente;
    }

    public List<ProductoVentaTo> getProductos() {
        return productos;
    }

    public void setProductos(List<ProductoVentaTo> productos) {
        this.productos = productos;
    }
}
