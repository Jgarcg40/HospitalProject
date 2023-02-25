package com.IS2.controller;

import com.IS2.EJB.EstadisticasFacadeLocal;
import com.IS2.model.Estadisticas;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;


@Named
@ApplicationScoped
public class EstadisticasController implements Serializable {
    
    @EJB
    private EstadisticasFacadeLocal EstadisticasEJB;
    
    private Estadisticas estadisticaDiaria;
    
    private List<Estadisticas> listaEstadisticas;
    
    @PostConstruct
    public void init(){
        estadisticaDiaria = new Estadisticas();
        listaEstadisticas = EstadisticasEJB.findAll();
        comprobarNuevoDia();
    }
    
    private void comprobarNuevoDia(){
        if(!listaEstadisticas.get(listaEstadisticas.size()-1).getFecha().substring(8,10).equals(LocalDateTime.now().toString().substring(8,10))){
            crearEstadisticaNuevoDia();
        }
        
    }
    
    private void crearEstadisticaNuevoDia(){
        try{
            estadisticaDiaria.setFecha(LocalDateTime.now().toString());
            EstadisticasEJB.create(estadisticaDiaria);
            
        }
        catch(Exception e){
            System.out.println("Error al crear estadísticas del nuevo día:" + e.getMessage());
        }
    }

    public EstadisticasFacadeLocal getEstadisticasEJB() {
        return EstadisticasEJB;
    }

    public void setEstadisticasEJB(EstadisticasFacadeLocal EstadisticasEJB) {
        this.EstadisticasEJB = EstadisticasEJB;
    }

    public Estadisticas getEstadisticaDiaria() {
        return estadisticaDiaria;
    }

    public void setEstadisticaDiaria(Estadisticas estadisticaDiaria) {
        this.estadisticaDiaria = estadisticaDiaria;
    }

    public List<Estadisticas> getListaEstadisticas() {
        return listaEstadisticas;
    }

    public void setListaEstadisticas(List<Estadisticas> listaEstadisticas) {
        this.listaEstadisticas = listaEstadisticas;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.EstadisticasEJB);
        hash = 29 * hash + Objects.hashCode(this.estadisticaDiaria);
        hash = 29 * hash + Objects.hashCode(this.listaEstadisticas);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final EstadisticasController other = (EstadisticasController) obj;
        if (!Objects.equals(this.EstadisticasEJB, other.EstadisticasEJB)) {
            return false;
        }
        if (!Objects.equals(this.estadisticaDiaria, other.estadisticaDiaria)) {
            return false;
        }
        if (!Objects.equals(this.listaEstadisticas, other.listaEstadisticas)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "EstadisticasController{" + "EstadisticasEJB=" + EstadisticasEJB + ", estadisticaDiaria=" + estadisticaDiaria + ", listaEstadisticas=" + listaEstadisticas + '}';
    }

}
