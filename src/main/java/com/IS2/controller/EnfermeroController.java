/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.IS2.controller;

import com.IS2.EJB.PacientesFacadeLocal;
import com.IS2.EJB.PersonalFacadeLocal;
import com.IS2.model.Pacientes;
import com.IS2.model.Personal;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author yoni8
 */

@Named
@ViewScoped //ojo importar la clase de view
public class EnfermeroController implements Serializable {
    
    
   @EJB
    private Personal personal;
    private List<Personal> listaPersonal;
    private PersonalFacadeLocal PersonalEJB;
    private Pacientes pacientes;
    private List<Pacientes> listaPacientes;
    private PacientesFacadeLocal PacientesEJB;
    
    
     @PostConstruct
    public void init(){
       
        pacientes = new Pacientes();
        listaPacientes = PacientesEJB.findAll();
    }
    
    
    public List<Pacientes> getListaPacientes() {
        return listaPacientes;
    }

    public void setListaPacientes(List<Pacientes> listaPacientes) {
        this.listaPacientes = listaPacientes;
    }

    public Pacientes getPacientes() {
        return pacientes;
    }

    public void setPacientes(Pacientes pacientes) {
        this.pacientes = pacientes;
    }

    
    
    
    
    
    
    
}