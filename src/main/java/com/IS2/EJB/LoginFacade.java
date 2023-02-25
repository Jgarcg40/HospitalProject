package com.IS2.EJB;

import com.IS2.model.Login;
import java.util.List;
import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class LoginFacade extends AbstractFacade<Login> implements LoginFacadeLocal {

    @PersistenceContext(unitName = "com.ULE_GestionHospital_war_1.0PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public LoginFacade() {
        super(Login.class);
    }

    @Override
    public String comprobarUsuario(Login login) {
        try{
            String consulta = "FROM Login c WHERE c.usuario=:parametro1 and c.contraseña=:parametro2";
            Query query = em.createQuery(consulta);
            query.setParameter("parametro1", login.getUsuario());
            query.setParameter("parametro2", login.getContraseña());
            List<Login> resultado = query.getResultList();
            
            if(resultado.isEmpty()){
                return "sinPermisos";//?faces-redirect=true sirve para mostrar en el navegador el link de adonde te diriges
            }else{
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(resultado.get(0).getRol(),resultado.get(0));//con esto ponemos el objeto "login" con alias "login.rol" lo pondra en contexto, es decir almacenamos la sesion(digamos que es una variable global)
                return resultado.get(0).getRol();
            }
        }catch (Exception e){
            System.out.println("Error al buscar usuarios en la base de datos:" + e.getMessage());
        }
        return null;
    }
    
}
