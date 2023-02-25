package com.IS2.model;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "pacientes") //definimos la tabla con la que se relaciona
public class Pacientes implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int IdPaciente;
    @Column (name = "Nombre")
    private String nombre;
    @Column (name = "Apellido1")
    private String Apellido1;
    @Column (name = "Apellido2")
    private String Apellido2;
    @Column (name = "NIFNIE")
    private String NIFNIE;
    @Column (name = "Enfermedad") 
    private String Enfermedad;
    @Column (name = "UMedicamento") 
    private int UMedicamento;
    
    @JoinColumn(name="idProducto")
    @ManyToOne(cascade=CascadeType.MERGE) //Merge hace que si existen en la base de datos los datos de la foreign key no hace una nueva, si no que usa la que hay
    private  Almacen almacen; 
    @JoinColumn(name="idEstancias")
    @OneToOne(cascade=CascadeType.PERSIST)
    private  Estancias estancia;
    @JoinColumn(name="idTrabajador")
    @ManyToOne(cascade=CascadeType.MERGE)//CUANDO ES ONE TO MANY O MANY TO MANY, TENEMOS QUE PONER UNA LISTA DE OBJETOS
    private  Personal personal;

    public int getIdPaciente() {
        return IdPaciente;
    }

    public void setIdPaciente(int IdPaciente) {
        this.IdPaciente = IdPaciente;
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

    public String getEnfermedad() {
        return Enfermedad;
    }

    public void setEnfermedad(String Enfermedad) {
        this.Enfermedad = Enfermedad;
    }

    public int getUMedicamento() {
        return UMedicamento;
    }

    public void setUMedicamento(int UMedicamento) {
        this.UMedicamento = UMedicamento;
    }

    public Almacen getAlmacen() {
        return almacen;
    }

    public void setAlmacen(Almacen almacen) {
        this.almacen = almacen;
    }

    public Estancias getEstancia() {
        return estancia;
    }

    public void setEstancia(Estancias estancia) {
        this.estancia = estancia;
    }

    public Personal getPersonal() {
        return personal;
    }

    public void setPersonal(Personal personal) {
        this.personal = personal;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + this.IdPaciente;
        hash = 79 * hash + Objects.hashCode(this.nombre);
        hash = 79 * hash + Objects.hashCode(this.Apellido1);
        hash = 79 * hash + Objects.hashCode(this.Apellido2);
        hash = 79 * hash + Objects.hashCode(this.NIFNIE);
        hash = 79 * hash + Objects.hashCode(this.Enfermedad);
        hash = 79 * hash + this.UMedicamento;
        hash = 79 * hash + Objects.hashCode(this.almacen);
        hash = 79 * hash + Objects.hashCode(this.estancia);
        hash = 79 * hash + Objects.hashCode(this.personal);
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
        
        final Pacientes other = (Pacientes) obj;
        if (this.IdPaciente != other.IdPaciente) {
            return false;
        }
        
        if (this.UMedicamento != other.UMedicamento) {
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
        if (!Objects.equals(this.Enfermedad, other.Enfermedad)) {
            return false;
        }
        if (!Objects.equals(this.almacen, other.almacen)) {
            return false;
        }
        if (!Objects.equals(this.estancia, other.estancia)) {
            return false;
        }
        if (!Objects.equals(this.personal, other.personal)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Pacientes{" + "IdPaciente=" + IdPaciente + ", nombre=" + nombre + ", Apellido1=" + Apellido1 + ", Apellido2=" + Apellido2 + ", NIFNIE=" + NIFNIE + ", Enfermedad=" + Enfermedad + ", UMedicamento=" + UMedicamento + ", almacen=" + almacen + ", estancia=" + estancia + ", personal=" + personal + '}';
    }
    
}
