package com.IS2.EJB;

import com.IS2.model.Estancias;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class EstanciasFacade extends AbstractFacade<Estancias> implements EstanciasFacadeLocal {

    @PersistenceContext(unitName = "com.ULE_GestionHospital_war_1.0PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EstanciasFacade() {
        super(Estancias.class);
    }
    
}
