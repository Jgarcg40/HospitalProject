package com.IS2.controller;

import com.IS2.EJB.AlmacenFacadeLocal;
import com.IS2.EJB.EstadisticasFacadeLocal;
import com.IS2.EJB.EstanciasFacadeLocal;
import com.IS2.EJB.LoginFacadeLocal;
import com.IS2.EJB.PacientesFacadeLocal;
import com.IS2.EJB.PersonalFacadeLocal;
import com.IS2.model.Almacen;
import com.IS2.model.Estadisticas;
import com.IS2.model.Estancias;
import com.IS2.model.Login;
import com.IS2.model.Pacientes;
import com.IS2.model.Personal;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
public class MedicoController implements Serializable {

    @EJB
    private EstanciasFacadeLocal EstanciasEJB;
    @EJB
    private PacientesFacadeLocal PacientesEJB;
    @EJB
    private LoginFacadeLocal LoginEJB;
    @EJB
    private AlmacenFacadeLocal AlmacenEJB;
    @EJB
    private EstadisticasFacadeLocal EstadisticasEJB;
    
    private Personal medico;
    private Pacientes paciente;
    private Login login;
    private Almacen almacen;
    
    private List<Estancias> listaEstancias;
    private List<Pacientes> listaPacientes;
    private List<Login> listaLogin;
    private List<Almacen> listaAlmacen;
    private List<Estadisticas> listaEstadisticas;
    
    private String busquedaPaciente;
    private String nombreCompletoPaciente;
    
    
    @PostConstruct
    public void init(){
        
        busquedaPaciente = "";
        nombreCompletoPaciente = "";
        
        medico = new Personal();
        paciente = new Pacientes();
        login = new Login();
        almacen = new Almacen();
        
        listaPacientes = PacientesEJB.findAll();
        listaEstancias = EstanciasEJB.findAll();
        listaLogin = LoginEJB.findAll();
        listaAlmacen = AlmacenEJB.findAll();
        listaEstadisticas = EstadisticasEJB.findAll();
        
    }
    
    
    public void buscarPaciente(){
        try{
            paciente = null;
            FacesMessage mensajePacienteNoEncontrado;
            if(busquedaPaciente.split(" ").length != 3){
                mensajePacienteNoEncontrado = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje", "Por favor introduce el nombre y los dos apellidos separados por espacios");
                FacesContext.getCurrentInstance().addMessage(null, mensajePacienteNoEncontrado);
                return;//si no ha metido el nombre completo no seguimos buscando
            }
            
            login = (Login) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("medico");
            listaPacientes = PacientesEJB.findAll();
            
            for(int i = 0; i < listaPacientes.size(); i++){
                if(listaPacientes.get(i).getNombre().equals(busquedaPaciente.split(" ")[0]) && listaPacientes.get(i).getApellido1().equals(busquedaPaciente.split(" ")[1]) && listaPacientes.get(i).getApellido2().equals(busquedaPaciente.split(" ")[2]) && listaPacientes.get(i).getPersonal().getIdTrabajador() ==  login.getPersonal().getIdTrabajador() && listaPacientes.get(i).getEstancia().getIngreso() != null){
                    
                    paciente = listaPacientes.get(i);
                    break;
                }
            }

            if(paciente == null){
                mensajePacienteNoEncontrado = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje", "Paciente no encontrado o no asignado a este médico");
                FacesContext.getCurrentInstance().addMessage(null, mensajePacienteNoEncontrado);
            }else{
                nombreCompletoPaciente = paciente.getNombre() + " " + paciente.getApellido1() + " " + paciente.getApellido2();
            }
            
        }catch(Exception e){
            System.out.println("Error al buscar el paciente:" + e.getMessage());
        }

    }
    
    public void darAlta(){
        try{
            if(paciente != null){
                paciente.getEstancia().setAlta(LocalDateTime.now().toString());
                paciente.getEstancia().setIngreso(null);
                paciente.setEnfermedad(null);
                paciente.setAlmacen(listaAlmacen.get(0));//cuando le damos el alta ponemos el almacen a "sin medicamento"
                EstanciasEJB.edit(paciente.getEstancia());
                PacientesEJB.edit(paciente);
                
                listaEstadisticas.get(listaEstadisticas.size()-1).setAltas(listaEstadisticas.get(listaEstadisticas.size()-1).getAltas()+1);
                EstadisticasEJB.edit(listaEstadisticas.get(listaEstadisticas.size()-1));
                paciente = null;
                FacesMessage mensajeAltaPaciente = new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje", "Paciente dado de alta");
                FacesContext.getCurrentInstance().addMessage(null, mensajeAltaPaciente);
           }
        }
        catch(Exception e){
            System.out.println("Error al dar de alta al paciente:" + e.getMessage());
        }
    }
  
    public void asignarMediamento(){
        try{
           FacesMessage mensajeMedicamentoPaciente;
           if(paciente != null){
                if(paciente.getAlmacen().getCantidad() - paciente.getUMedicamento() < 0){ //si la cantidad de medicamento es mayor que uno, restamos una unidad a la cantidad de medicamento
                    mensajeMedicamentoPaciente = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje", "No hay suficientes medicinas para asignar al paciente");
                    FacesContext.getCurrentInstance().addMessage(null, mensajeMedicamentoPaciente);
                    return;
               }
               almacen = AlmacenEJB.buscarAlmacenPorId(almacen.getIdProducto());
               paciente.setAlmacen(almacen);  
               paciente.getAlmacen().setCantidad(paciente.getAlmacen().getCantidad() - paciente.getUMedicamento());//restamos la cantidad de medicamento en el almacen
               AlmacenEJB.edit(paciente.getAlmacen());
               PacientesEJB.edit(paciente);
               
               listaEstadisticas.get(listaEstadisticas.size()-1).setMedicinas(listaEstadisticas.get(listaEstadisticas.size()-1).getMedicinas()+paciente.getUMedicamento());//cojemos la última estadística diaria y añadimos la cantidad de medicamentoc consumido
               EstadisticasEJB.edit(listaEstadisticas.get(listaEstadisticas.size()-1));
               paciente = null;
               mensajeMedicamentoPaciente = new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje", "Medicina asignada al paciente");
               FacesContext.getCurrentInstance().addMessage(null, mensajeMedicamentoPaciente);
           }
        }
        catch(Exception e){
            System.out.println("Error al asignar medicamento al paciente:" + e.getMessage());
        }
    }
     
    public Almacen getAlmacen() {
        return almacen;
    }

    public void setAlmacen(Almacen almacen) {
        this.almacen = almacen;
    }

    public EstanciasFacadeLocal getEstanciasEJB() {
        return EstanciasEJB;
    }

    public void setEstanciasEJB(EstanciasFacadeLocal EstanciasEJB) {
        this.EstanciasEJB = EstanciasEJB;
    }

    public PacientesFacadeLocal getPacientesEJB() {
        return PacientesEJB;
    }

    public void setPacientesEJB(PacientesFacadeLocal PacientesEJB) {
        this.PacientesEJB = PacientesEJB;
    }

    public Personal getMedico() {
        return medico;
    }

    public void setMedico(Personal medico) {
        this.medico = medico;
    }

    public Pacientes getPaciente() {
        return paciente;
    }

    public void setPaciente(Pacientes paciente) {
        this.paciente = paciente;
    }

    public List<Estancias> getListaEstancias() {
        return listaEstancias;
    }

    public void setListaEstancias(List<Estancias> listaEstancias) {
        this.listaEstancias = listaEstancias;
    }

    public List<Pacientes> getListaPacientes() {
        return listaPacientes;
    }

    public void setListaPacientes(List<Pacientes> listaPacientes) {
        this.listaPacientes = listaPacientes;
    }

    public String getBusquedaPaciente() {
        return busquedaPaciente;
    }

    public EstadisticasFacadeLocal getEstadisticasEJB() {
        return EstadisticasEJB;
    }

    public List<Estadisticas> getListaEstadisticas() {
        return listaEstadisticas;
    }

    public void setBusquedaPaciente(String busquedaPaciente) {
        this.busquedaPaciente = busquedaPaciente;
    }

    public String getNombreCompletoPaciente() {
        return nombreCompletoPaciente;
    }

    public void setNombreCompletoPaciente(String nombreCompletoPaciente) {
        this.nombreCompletoPaciente = nombreCompletoPaciente;
    }

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

    public void setLoginEJB(LoginFacadeLocal LoginEJB) {
        this.LoginEJB = LoginEJB;
    }

    public void setListaLogin(List<Login> listaLogin) {
        this.listaLogin = listaLogin;
    }

    public LoginFacadeLocal getLoginEJB() {
        return LoginEJB;
    }

    public List<Login> getListaLogin() {
        return listaLogin;
    }

    public AlmacenFacadeLocal getAlmacenEJB() {
        return AlmacenEJB;
    }

    public List<Almacen> getListaAlmacen() {
        return listaAlmacen;
    }

    public void setAlmacenEJB(AlmacenFacadeLocal AlmacenEJB) {
        this.AlmacenEJB = AlmacenEJB;
    }

    public void setListaAlmacen(List<Almacen> listaAlmacen) {
        this.listaAlmacen = listaAlmacen;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + Objects.hashCode(this.EstanciasEJB);
        hash = 79 * hash + Objects.hashCode(this.PacientesEJB);
        hash = 79 * hash + Objects.hashCode(this.LoginEJB);
        hash = 79 * hash + Objects.hashCode(this.AlmacenEJB);
        hash = 79 * hash + Objects.hashCode(this.EstadisticasEJB);
        hash = 79 * hash + Objects.hashCode(this.medico);
        hash = 79 * hash + Objects.hashCode(this.paciente);
        hash = 79 * hash + Objects.hashCode(this.login);
        hash = 79 * hash + Objects.hashCode(this.almacen);
        hash = 79 * hash + Objects.hashCode(this.listaEstancias);
        hash = 79 * hash + Objects.hashCode(this.listaPacientes);
        hash = 79 * hash + Objects.hashCode(this.listaLogin);
        hash = 79 * hash + Objects.hashCode(this.listaAlmacen);
        hash = 79 * hash + Objects.hashCode(this.listaEstadisticas);
        hash = 79 * hash + Objects.hashCode(this.busquedaPaciente);
        hash = 79 * hash + Objects.hashCode(this.nombreCompletoPaciente);
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
        final MedicoController other = (MedicoController) obj;
        if (!Objects.equals(this.busquedaPaciente, other.busquedaPaciente)) {
            return false;
        }
        if (!Objects.equals(this.nombreCompletoPaciente, other.nombreCompletoPaciente)) {
            return false;
        }
        if (!Objects.equals(this.EstanciasEJB, other.EstanciasEJB)) {
            return false;
        }
        if (!Objects.equals(this.PacientesEJB, other.PacientesEJB)) {
            return false;
        }
        if (!Objects.equals(this.LoginEJB, other.LoginEJB)) {
            return false;
        }
        if (!Objects.equals(this.AlmacenEJB, other.AlmacenEJB)) {
            return false;
        }
        if (!Objects.equals(this.EstadisticasEJB, other.EstadisticasEJB)) {
            return false;
        }
        if (!Objects.equals(this.medico, other.medico)) {
            return false;
        }
        if (!Objects.equals(this.paciente, other.paciente)) {
            return false;
        }
        if (!Objects.equals(this.login, other.login)) {
            return false;
        }
        if (!Objects.equals(this.almacen, other.almacen)) {
            return false;
        }
        if (!Objects.equals(this.listaEstancias, other.listaEstancias)) {
            return false;
        }
        if (!Objects.equals(this.listaPacientes, other.listaPacientes)) {
            return false;
        }
        if (!Objects.equals(this.listaLogin, other.listaLogin)) {
            return false;
        }
        if (!Objects.equals(this.listaAlmacen, other.listaAlmacen)) {
            return false;
        }
        if (!Objects.equals(this.listaEstadisticas, other.listaEstadisticas)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "MedicoController{" + "EstanciasEJB=" + EstanciasEJB + ", PacientesEJB=" + PacientesEJB + ", LoginEJB=" + LoginEJB + ", AlmacenEJB=" + AlmacenEJB + ", EstadisticasEJB=" + EstadisticasEJB + ", medico=" + medico + ", paciente=" + paciente + ", login=" + login + ", almacen=" + almacen + ", listaEstancias=" + listaEstancias + ", listaPacientes=" + listaPacientes + ", listaLogin=" + listaLogin + ", listaAlmacen=" + listaAlmacen + ", listaEstadisticas=" + listaEstadisticas + ", busquedaPaciente=" + busquedaPaciente + ", nombreCompletoPaciente=" + nombreCompletoPaciente + '}';
    }

}