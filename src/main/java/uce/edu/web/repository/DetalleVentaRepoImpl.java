package uce.edu.web.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import uce.edu.web.repository.model.DetalleVenta;

@Transactional
@ApplicationScoped
public class DetalleVentaRepoImpl implements IDetalleVentaRepo {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void insertar(DetalleVenta detalleVenta) {
        entityManager.persist(detalleVenta);
    }

}
