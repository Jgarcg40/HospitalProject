package com.IS2.EJB;

import com.IS2.model.Login;
import java.util.List;
import javax.ejb.Local;

@Local
public interface LoginFacadeLocal {

    void create(Login login);

    void edit(Login login);

    void remove(Login login);

    Login find(Object id);

    List<Login> findAll();

    List<Login> findRange(int[] range);

    int count();

    public String comprobarUsuario(Login login);
    
}
