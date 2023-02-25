package com.IS2.EJB;

import com.IS2.model.Estadisticas;
import java.util.List;
import javax.ejb.Local;

@Local
public interface EstadisticasFacadeLocal {

    void create(Estadisticas estadisticas);

    void edit(Estadisticas estadisticas);

    void remove(Estadisticas estadisticas);

    Estadisticas find(Object id);

    List<Estadisticas> findAll();

    List<Estadisticas> findRange(int[] range);

    int count();
    
}
