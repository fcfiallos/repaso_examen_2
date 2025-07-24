package uce.edu.web.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import uce.edu.web.repository.model.Venta;

@Transactional
@ApplicationScoped
public class VentaRepoImpl implements IVentaRepo {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void insertar(Venta venta) {
        entityManager.persist(venta);
    }

    

}
