package com.IS2.EJB;

import com.IS2.model.Estancias;
import java.util.List;
import javax.ejb.Local;

@Local
public interface EstanciasFacadeLocal {

    void create(Estancias estancias);

    void edit(Estancias estancias);

    void remove(Estancias estancias);

    Estancias find(Object id);

    List<Estancias> findAll();

    List<Estancias> findRange(int[] range);

    int count();
    
}
