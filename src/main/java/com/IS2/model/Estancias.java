/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.IS2.model;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "estancias")
public class Estancias implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idEstancias;
    @Column (name = "ingreso")
    private String ingreso;
    @Column (name = "alta")
    private String alta;

    public int getIdEstancias() {
        return idEstancias;
    }

    public void setIdEstancias(int IdEstancias) {
        this.idEstancias = IdEstancias;
    }

    public String getIngreso() {
        return ingreso;
    }

    public void setIngreso(String ingreso) {
        this.ingreso = ingreso;
    }

    public String getAlta() {
        return alta;
    }

    public void setAlta(String alta) {
        this.alta = alta;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + this.idEstancias;
        hash = 17 * hash + Objects.hashCode(this.ingreso);
        hash = 17 * hash + Objects.hashCode(this.alta);
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
        final Estancias other = (Estancias) obj;
        if (this.idEstancias != other.idEstancias) {
            return false;
        }
        if (!Objects.equals(this.ingreso, other.ingreso)) {
            return false;
        }
        if (!Objects.equals(this.alta, other.alta)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Estancias{" + "idEstancias=" + idEstancias + ", ingreso=" + ingreso + ", alta=" + alta + '}';
    }
    
}
