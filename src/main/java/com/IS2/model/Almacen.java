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
@Table(name = "almacen") //definimos la tabla con la que se relaciona
public class Almacen implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idProducto;
    @Column (name = "Nombre") //indicamos las columnas igual escritas que en la base de datos
    private String Nombre;
    @Column (name = "Cantidad")
    private int Cantidad;
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + this.idProducto;
        hash = 83 * hash + Objects.hashCode(this.Nombre);
        hash = 83 * hash + this.Cantidad;
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
        final Almacen other = (Almacen) obj;
        if (this.idProducto != other.idProducto) {
            return false;
        }
        if (this.Cantidad != other.Cantidad) {
            return false;
        }
        if (!Objects.equals(this.Nombre, other.Nombre)) {
            return false;
        }
        return true;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int IdProducto) {
        this.idProducto = IdProducto;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public int getCantidad() {
        return Cantidad;
    }

    public void setCantidad(int Cantidad) {
        this.Cantidad = Cantidad;
    }

    @Override
    public String toString() {
        return "Almacen{" + "idProducto=" + idProducto + ", Nombre=" + Nombre + ", Cantidad=" + Cantidad + '}';
    }

}
