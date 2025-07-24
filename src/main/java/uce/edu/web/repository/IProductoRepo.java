package uce.edu.web.repository;

import uce.edu.web.repository.model.Producto;

public interface IProductoRepo {
    public void insertar(Producto producto);
    public Producto seleccionarPorCodigo(String codigoBarras);
    public Producto seleccionarPorStock(Integer stock);
    public void actualizar(Producto producto);
}
