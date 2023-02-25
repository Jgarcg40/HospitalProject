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
@Table(name = "estadisticas") //definimos la tabla con la que se relaciona
public class Estadisticas implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int IdEstadisticas;
    @Column (name = "medicinas") //indicamos las columnas igual escritas que en la base de datos
    private int medicinas;
    @Column (name = "altas")
    //@Temporal(TemporalType.DATE) //para fechas
    private int altas;
    @Column (name = "ingresos")
    //@Temporal(TemporalType.DATE) //para fechas
    private int ingreso;
    @Column (name = "fechaEstadistica")
    private String fecha;

    public int getIdEstadisticas() {
        return IdEstadisticas;
    }

    public void setIdEstadisticas(int IdEstadisticas) {
        this.IdEstadisticas = IdEstadisticas;
    }

    public int getMedicinas() {
        return medicinas;
    }

    public void setMedicinas(int medicinas) {
        this.medicinas = medicinas;
    }

    public int getAltas() {
        return altas;
    }

    public void setAltas(int altas) {
        this.altas = altas;
    }

    public int getIngreso() {
        return ingreso;
    }

    public void setIngreso(int ingreso) {
        this.ingreso = ingreso;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + this.IdEstadisticas;
        hash = 83 * hash + this.medicinas;
        hash = 83 * hash + Objects.hashCode(this.altas);
        hash = 83 * hash + Objects.hashCode(this.ingreso);
        hash = 83 * hash + Objects.hashCode(this.fecha);
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
        final Estadisticas other = (Estadisticas) obj;
        if (this.IdEstadisticas != other.IdEstadisticas) {
            return false;
        }
        if (this.medicinas != other.medicinas) {
            return false;
        }
        if (!Objects.equals(this.altas, other.altas)) {
            return false;
        }
        if (!Objects.equals(this.ingreso, other.ingreso)) {
            return false;
        }
        if (!Objects.equals(this.fecha, other.fecha)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Estadisticas{" + "IdEstadisticas=" + IdEstadisticas + ", medicinas=" + medicinas + ", altas=" + altas + ", ingreso=" + ingreso + ", fecha=" + fecha + '}';
    }
    
}
