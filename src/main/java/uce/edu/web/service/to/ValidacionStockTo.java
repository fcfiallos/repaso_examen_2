package uce.edu.web.service.to;

import uce.edu.web.repository.model.Producto;

public class ValidacionStockTo {
    private boolean stockDisponible;
    private String mensaje;
    private Producto producto;

    public ValidacionStockTo() {
    }

    public ValidacionStockTo(boolean stockDisponible, String mensaje, Producto producto) {
        this.stockDisponible = stockDisponible;
        this.mensaje = mensaje;
        this.producto = producto;
    }

    public boolean isStockDisponible() {
        return stockDisponible;
    }

    public void setStockDisponible(boolean stockDisponible) {
        this.stockDisponible = stockDisponible;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }
}
