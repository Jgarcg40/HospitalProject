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
@Table(name = "personal") //definimos la tabla con la que se relaciona
public class Personal implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idTrabajador;
    @Column (name = "nombre")
    private String nombre;
    @Column (name = "Apellido1")
    private String Apellido1;
    @Column (name = "Apellido2")
    private String Apellido2;
    @Column (name = "NIFNIE")
    private String NIFNIE;
    @Column (name = "FechaAlta")
    //@Temporal(TemporalType.DATE) //para fechas
    private String FechaAlta;
    @Column (name = "CuentaBancaria")
    private String CuentaBancaria;
    @Column (name = "Puesto")
    private String Puesto;
    @Column (name = "Email")
    private String Email;

    public int getIdTrabajador() {
        return idTrabajador;
    }

    public void setIdTrabajador(int IdTrabajador) {
        this.idTrabajador = IdTrabajador;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido1() {
        return Apellido1;
    }

    public void setApellido1(String Apellido1) {
        this.Apellido1 = Apellido1;
    }

    public String getApellido2() {
        return Apellido2;
    }

    public void setApellido2(String Apellido2) {
        this.Apellido2 = Apellido2;
    }

    public String getNIFNIE() {
        return NIFNIE;
    }

    public void setNIFNIE(String NIFNIE) {
        this.NIFNIE = NIFNIE;
    }

    public String getFechaAlta() {
        return FechaAlta;
    }

    public void setFechaAlta(String FechaAlta) {
        this.FechaAlta = FechaAlta;
    }

    public String getCuentaBancaria() {
        return CuentaBancaria;
    }

    public void setCuentaBancaria(String CuentaBancaria) {
        this.CuentaBancaria = CuentaBancaria;
    }

    public String getPuesto() {
        return Puesto;
    }

    public void setPuesto(String Puesto) {
        this.Puesto = Puesto;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.idTrabajador;
        hash = 97 * hash + Objects.hashCode(this.nombre);
        hash = 97 * hash + Objects.hashCode(this.Apellido1);
        hash = 97 * hash + Objects.hashCode(this.Apellido2);
        hash = 97 * hash + Objects.hashCode(this.NIFNIE);
        hash = 97 * hash + Objects.hashCode(this.FechaAlta);
        hash = 97 * hash + Objects.hashCode(this.CuentaBancaria);
        hash = 97 * hash + Objects.hashCode(this.Puesto);
        hash = 97 * hash + Objects.hashCode(this.Email);
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
        final Personal other = (Personal) obj;
        if (this.idTrabajador != other.idTrabajador) {
            return false;
        }
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        if (!Objects.equals(this.Apellido1, other.Apellido1)) {
            return false;
        }
        if (!Objects.equals(this.Apellido2, other.Apellido2)) {
            return false;
        }
        if (!Objects.equals(this.NIFNIE, other.NIFNIE)) {
            return false;
        }
        if (!Objects.equals(this.CuentaBancaria, other.CuentaBancaria)) {
            return false;
        }
        if (!Objects.equals(this.Puesto, other.Puesto)) {
            return false;
        }
        if (!Objects.equals(this.Email, other.Email)) {
            return false;
        }
        if (!Objects.equals(this.FechaAlta, other.FechaAlta)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Personal{" + "IdTrabajador=" + idTrabajador + ", nombre=" + nombre + ", Apellido1=" + Apellido1 + ", Apellido2=" + Apellido2 + ", NIFNIE=" + NIFNIE + ", FechaAlta=" + FechaAlta + ", CuentaBancaria=" + CuentaBancaria + ", Puesto=" + Puesto + ", Email=" + Email + '}';
    }
}
