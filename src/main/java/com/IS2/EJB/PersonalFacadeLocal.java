package com.IS2.EJB;

import com.IS2.model.Personal;
import java.util.List;
import javax.ejb.Local;

@Local
public interface PersonalFacadeLocal {

    void create(Personal personal);

    void edit(Personal personal);

    void remove(Personal personal);

    Personal find(Object id);

    List<Personal> findAll();

    List<Personal> findRange(int[] range);
    
    public Personal buscarPersonalPorId(int idTrabajador);

    int count();

   
    
}
