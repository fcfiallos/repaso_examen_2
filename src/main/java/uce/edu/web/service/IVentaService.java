package uce.edu.web.service;

import uce.edu.web.service.to.ValidacionStockTo;
import uce.edu.web.service.to.VentaProdTo;

public interface IVentaService {
    public void crearVenta(VentaProdTo ventaProdTo);
    public ValidacionStockTo validarStock(String codigoBarras, Integer cantidad);
}
