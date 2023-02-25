package com.IS2.EJB;

import com.IS2.model.Almacen;
import java.util.List;
import javax.ejb.Local;

@Local
public interface AlmacenFacadeLocal {

    void create(Almacen almacen);

    void edit(Almacen almacen);

    void remove(Almacen almacen);

    Almacen find(Object id);

    List<Almacen> findAll();

    List<Almacen> findRange(int[] range);

    int count();
    
    public Almacen buscarAlmacenPorId(int nombre);
    
    public Almacen cantidadActual(int idProducto, int Cantidad, int Nombre);
    
    public void asignarMedicamentoAlmacen(int idProducto, int Cantidad, int Nombre);
}
