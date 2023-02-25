package com.IS2.controller;

import com.IS2.EJB.LoginFacadeLocal;
import com.IS2.model.Login;
import java.io.IOException;
import java.io.Serializable;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;


@Named
@ViewScoped
public class LoginController implements Serializable {
    
    @EJB
    private LoginFacadeLocal loginEJB;
    private Login login;
    
    @PostConstruct
    public void init(){
        login = new Login();
    }
    
    //método para comprobar si el usuario existe, y si existe llevarte a la pantalla adecuada
    public String comprobarUsuario(){
        try{
            String resultado = loginEJB.comprobarUsuario(login);
               
            if(resultado.equals("sinPermisos")){
                FacesContext.getCurrentInstance().addMessage("sinPermisos", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuario y/o contraseña incorrectos.","Error"));
                return "/publico/index";
            }
            
            return "/private/" + resultado;
            
        }catch(Exception e){
            System.out.println("Error al comprobar usuario: " + e.getMessage());
        }
        return null;
    }
    //metodo para comprobar si el usuario ha iniciado sesión o si ha iniciado sesión con este rol
    public void verificarUsuario() throws IOException{
        String paginaActual = FacesContext.getCurrentInstance().getViewRoot().getViewId();

        if(paginaActual.equals("/private/medico.xhtml") && (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("medico") != null)){
            return;
        }
        if(paginaActual.equals("/private/secretario.xhtml") && (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("secretario") != null)){
            return;
        }
        if(paginaActual.equals("/private/enfermero.xhtml") && (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("enfermero") != null)){
            return;
        }
        FacesContext.getCurrentInstance().getExternalContext().redirect("./../publico/index.xhtml");
        
    }
    
    //metodo para desconectar al usuario actual
    public void destruirSesion() throws IOException{
        FacesContext.getCurrentInstance().getExternalContext().redirect("/PR-INSO2/faces/publico/index.xhtml");
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        
    }

    public LoginFacadeLocal getLoginEJB() {
        return loginEJB;
    }

    public void setLoginEJB(LoginFacadeLocal loginEJB) {
        this.loginEJB = loginEJB;
    }

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + Objects.hashCode(this.loginEJB);
        hash = 89 * hash + Objects.hashCode(this.login);
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
        final LoginController other = (LoginController) obj;
        if (!Objects.equals(this.loginEJB, other.loginEJB)) {
            return false;
        }
        if (!Objects.equals(this.login, other.login)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "LoginController{" + "loginEJB=" + loginEJB + ", login=" + login + '}';
    }
    
}
