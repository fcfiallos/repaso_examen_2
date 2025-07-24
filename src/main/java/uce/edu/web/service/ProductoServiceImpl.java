package uce.edu.web.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import uce.edu.web.repository.IProductoRepo;
import uce.edu.web.repository.model.Producto;
import uce.edu.web.service.to.ProductoTo;

@ApplicationScoped
public class ProductoServiceImpl implements IProductoService {
    @Inject
    private IProductoRepo productoRepo;

    @Override
    public void guadar(ProductoTo producto) {
        Producto prod = this.productoRepo.seleccionarPorCodigo(producto.getCodigoBarras());
        if (prod != null) {
            prod.setNombre(producto.getNombre());
            prod.setCategoria(producto.getCategoria());
            prod.setStock(producto.getStock() + prod.getStock());
            prod.setPrecio(producto.getPrecio());
            this.productoRepo.actualizar(prod);
        } else {
            prod = new Producto();
            prod.setCodigoBarras(producto.getCodigoBarras());
            prod.setNombre(producto.getNombre());
            prod.setCategoria(producto.getCategoria());
            prod.setStock(producto.getStock());
            prod.setPrecio(producto.getPrecio());
            this.productoRepo.insertar(prod);
        }
    }

}
