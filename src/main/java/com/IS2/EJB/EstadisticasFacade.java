package com.IS2.EJB;

import com.IS2.model.Estadisticas;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless
public class EstadisticasFacade extends AbstractFacade<Estadisticas> implements EstadisticasFacadeLocal {

    @PersistenceContext(unitName = "com.ULE_GestionHospital_war_1.0PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EstadisticasFacade() {
        super(Estadisticas.class);
    }
    
}
