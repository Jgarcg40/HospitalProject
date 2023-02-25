package com.IS2.EJB;

import com.IS2.model.Almacen;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class AlmacenFacade extends AbstractFacade<Almacen> implements AlmacenFacadeLocal {

    @PersistenceContext(unitName = "com.ULE_GestionHospital_war_1.0PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AlmacenFacade() {
        super(Almacen.class);
    }

    @Override
    public Almacen buscarAlmacenPorId(int idProducto) {
        Almacen almacen = null;
        //si hay que buscar algo concreto en la bbdd hay que hacer una consulta directamente
        try{
            String consulta = "FROM Almacen c WHERE c.idProducto = :param1";
            Query query = em.createQuery(consulta);
            query.setParameter("param1",idProducto);
            List<Almacen> resultado = query.getResultList();
            
            if(!resultado.isEmpty()){ //porque como es una lista y solo deberia encontrar uno devolvemos el primer valor de la lista
                almacen = resultado.get(0);
            }
        }catch (Exception e){
            System.out.println("Error al buscar almacen:" + e.getMessage());
        }
        return almacen;
    }
    @Override
    public Almacen cantidadActual(int idProducto, int Cantidad, int Nombre) {
        Almacen almacen = null;
        //si hay que buscar algo concreto en la bbdd hay que hacer una consulta
        try{
            String consulta = "SELECT Cantidad FROM Almacen WHERE Nombre = :param1"; //ojo la consulta es sobre la clase Personal.java del modelo no sobre la bbdd directamente
            Query query = em.createQuery(consulta);
            query.setParameter("param1",Nombre);
            List<Almacen> resultado = query.getResultList();
            
            if(!resultado.isEmpty()){
                almacen = resultado.get(0);
            }
        }catch (Exception e){
            System.out.println("Error al buscar el producto" + e.getMessage());
        }
        return almacen;
    }
    
    @Override
    public void asignarMedicamentoAlmacen(int idProducto, int Cantidad, int Nombre) {
        Almacen almacen = null;
        //si hay que buscar algo concreto en la bbdd hay que hacer una consulta
        try{
            
            String consulta = "UPDATE Almacen SET Cantidad= :param1, Nombre= :param2 WHERE idProducto = :param3"; //ojo la consulta es sobre la clase Personal.java del modelo no sobre la bbdd directamente
            Query query = em.createQuery(consulta);
            query.setParameter("param1",idProducto);
            query.setParameter("param2",Cantidad);
            query.setParameter("param3",Nombre);
            List<Almacen> resultado = query.getResultList();
            
            if(!resultado.isEmpty()){
                almacen = resultado.get(0);
            }
        }catch (Exception e){
            System.out.println("Error al buscar el producto" + e.getMessage());
        }
    }
}
    

