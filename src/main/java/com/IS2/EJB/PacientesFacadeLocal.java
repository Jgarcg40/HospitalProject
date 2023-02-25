package com.IS2.EJB;

import com.IS2.model.Pacientes;
import java.util.List;
import javax.ejb.Local;

@Local
public interface PacientesFacadeLocal {

    void create(Pacientes pacientes);

    void edit(Pacientes pacientes);

    void remove(Pacientes pacientes);

    Pacientes find(Object id);

    List<Pacientes> findAll();

    List<Pacientes> findRange(int[] range);
    
    public Pacientes buscarPacientesPorId(int idPaciente);

    int count();
    
    public void asignarMediamento(int idPaciente, int fk_idProducto, int UMedicamento);
    
}
