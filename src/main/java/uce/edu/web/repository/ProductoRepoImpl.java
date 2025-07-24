package uce.edu.web.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import uce.edu.web.repository.model.Producto;

@Transactional
@ApplicationScoped
public class ProductoRepoImpl implements IProductoRepo {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void insertar(Producto producto) {
        entityManager.persist(producto);
    }

    @Override
    public Producto seleccionarPorCodigo(String codigoBarras) {
        try {
            return entityManager
                .createQuery("SELECT p FROM Producto p WHERE p.codigoBarras = :codigoBarras", Producto.class)
                .setParameter("codigoBarras", codigoBarras)
                .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
        
    }

    @Override
    public Producto seleccionarPorStock(Integer stock) {
        return entityManager
                .createQuery("SELECT p FROM Producto p WHERE p.stock = :stock", Producto.class)
                .setParameter("stock", stock)
                .getSingleResult();
    }

    @Override
    public void actualizar(Producto producto) {
        this.entityManager.merge(producto);
    }

}
