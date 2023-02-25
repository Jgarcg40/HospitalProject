package com.IS2.model;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "login") //definimos la tabla con la que se relaciona
public class Login implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int IdLogin;
    @Column (name = "usuario")
    private String usuario;
    @Column (name = "contraseña")
    private String contraseña;
    @Column (name = "Rol")
    private String rol;
    @JoinColumn(name="fk_idTrabajador")
    @OneToOne(cascade=CascadeType.PERSIST)
    private  Personal personal;

    public int getIdLogin() {
        return IdLogin;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public String getRol() {
        return rol;
    }

    public Personal getPersonal() {
        return personal;
    }

    public void setIdLogin(int IdLogin) {
        this.IdLogin = IdLogin;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public void setPersonal(Personal personal) {
        this.personal = personal;
    }

    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + this.IdLogin;
        hash = 43 * hash + Objects.hashCode(this.usuario);
        hash = 43 * hash + Objects.hashCode(this.contraseña);
        hash = 43 * hash + Objects.hashCode(this.rol);
        hash = 43 * hash + Objects.hashCode(this.personal);
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
        final Login other = (Login) obj;
        if (this.IdLogin != other.IdLogin) {
            return false;
        }
        if (!Objects.equals(this.usuario, other.usuario)) {
            return false;
        }
        if (!Objects.equals(this.contraseña, other.contraseña)) {
            return false;
        }
        if (!Objects.equals(this.rol, other.rol)) {
            return false;
        }
        if (!Objects.equals(this.personal, other.personal)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Login{" + "IdLogin=" + IdLogin + ", usuario=" + usuario + ", contrasena=" + contraseña + ", rol=" + rol + ", personal=" + personal + '}';
    }    
    
}
