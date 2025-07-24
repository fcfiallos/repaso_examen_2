package uce.edu.web.service.to;

public class ProductoVentaTo {
    private String codigoBarras;
    private Integer cantidad;

    public ProductoVentaTo() {
    }

    public ProductoVentaTo(String codigoBarras, Integer cantidad) {
        this.codigoBarras = codigoBarras;
        this.cantidad = cantidad;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
}
