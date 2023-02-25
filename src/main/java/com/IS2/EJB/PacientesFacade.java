package com.IS2.EJB;

import com.IS2.model.Pacientes;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class PacientesFacade extends AbstractFacade<Pacientes> implements PacientesFacadeLocal {

    @PersistenceContext(unitName = "com.ULE_GestionHospital_war_1.0PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PacientesFacade() {
        super(Pacientes.class);
    }
    
    @Override
    public Pacientes buscarPacientesPorId(int idPaciente){
        Pacientes pacientes = null;
        //si hay que buscar algo concreto en la bbdd hay que hacer una consulta
        try{
            String consulta = "FROM Pacientes WHERE idPaciente = :param1"; //ojo la consulta es sobre la clase Personal.java del modelo no sobre la bbdd directamente
            Query query = em.createQuery(consulta);
            query.setParameter("param1",idPaciente);
            List<Pacientes> resultado = query.getResultList();
            
            if(!resultado.isEmpty()){
                pacientes = resultado.get(0);
            }
        }catch (Exception e){
            System.out.println("Error al buscar pacientes" + e.getMessage());
        }
        return pacientes;
 
    }
    @Override
    public void asignarMediamento(int idPaciente, int fk_idProducto, int UMedicamento) {
        Pacientes pacientes = null;
        //si hay que buscar algo concreto en la bbdd hay que hacer una consulta
        try{
            String consulta = "UPDATE Pacientes SET fk_idProducto= :param1, UMedicamento= :param2 WHERE idPaciente = :param3"; //ojo la consulta es sobre la clase Personal.java del modelo no sobre la bbdd directamente
            Query query = em.createQuery(consulta);
            query.setParameter("param1",fk_idProducto);
            query.setParameter("param2",UMedicamento);
            query.setParameter("param3",idPaciente);
            List<Pacientes> resultado = query.getResultList();
            
            if(!resultado.isEmpty()){
                pacientes = resultado.get(0);
            }
        }catch (Exception e){
            System.out.println("Error al buscar pacientes" + e.getMessage());
        }
    }
    
    
}
