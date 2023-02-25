package com.IS2.EJB;

import com.IS2.model.Personal;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class PersonalFacade extends AbstractFacade<Personal> implements PersonalFacadeLocal {

    @PersistenceContext(unitName = "com.ULE_GestionHospital_war_1.0PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PersonalFacade() {
        super(Personal.class);
    }
    
    @Override
    public Personal buscarPersonalPorId(int idTrabajador){
        Personal personal = null;
        //si hay que buscar algo concreto en la bbdd hay que hacer una consulta
        try{
            String consulta = "FROM Personal c WHERE c.idTrabajador = :param1"; //ojo la consulta es sobre la clase Personal.java del modelo no sobre la bbdd directamente
            Query query = em.createQuery(consulta);
            query.setParameter("param1",idTrabajador);
            List<Personal> resultado = query.getResultList();
            
            if(!resultado.isEmpty()){
                personal = resultado.get(0);
            }
        }catch (Exception e){
            System.out.println("Error al buscar personal:" + e.getMessage());
        }
        return personal;
    }
    
}
