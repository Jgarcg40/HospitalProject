package com.IS2.controller;

import com.IS2.EJB.AlmacenFacadeLocal;
import com.IS2.EJB.EstadisticasFacadeLocal;
import com.IS2.EJB.EstanciasFacadeLocal;
import com.IS2.EJB.LoginFacadeLocal;
import com.IS2.EJB.PacientesFacadeLocal;
import com.IS2.EJB.PersonalFacadeLocal;
import com.IS2.Email.Generador;
import com.IS2.model.Almacen;
import com.IS2.model.Estadisticas;
import com.IS2.model.Estancias;
import com.IS2.model.Login;
import com.IS2.model.Pacientes;
import com.IS2.model.Personal;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import java.time.LocalDateTime; //para obtener la hora y fecha actual
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

@Named
@ViewScoped //ojo importar la clase de view
public class SecretarioController implements Serializable {
    
    @EJB
    private PersonalFacadeLocal PersonalEJB;
    @EJB
    private PacientesFacadeLocal PacientesEJB;
    @EJB
    private EstanciasFacadeLocal EstanciasEJB;
    @EJB
    private AlmacenFacadeLocal AlmacenEJB;
    @EJB
    private LoginFacadeLocal LoginEJB;
    @EJB
    private EstadisticasFacadeLocal EstadisticasEJB;
    
    private List<Pacientes> listaPacientes;
    private List<Personal> listaPersonal;
    private List<Estancias> listaEstancias;
    private List<Almacen> listaAlmacen;
    private List<Login> listaLogin;
    private List<Estadisticas> listaEstadisticas;
    
    Pacientes paciente;
    Personal personal;
    Almacen almacen;
    Estancias estancia;
    Login login;
    
    String busquedaPaciente;
    String busquedaPersonal;
    
    String nombreCompletoPaciente;//para que en la vista se pueda poner en una label o input text todo seguido
    String nombreCompletoPersonal;
    
    @PostConstruct
    public void init(){
        busquedaPaciente = "";
        busquedaPersonal = "";
        nombreCompletoPaciente = "";
        nombreCompletoPersonal =  "";
        
        paciente = new Pacientes();
        personal = new Personal();
        almacen = new Almacen();
        estancia = new Estancias(); 
        login = new Login();
        
        listaPersonal = PersonalEJB.findAll();
        listaPacientes = PacientesEJB.findAll();
        listaEstancias = EstanciasEJB.findAll();
        listaAlmacen = AlmacenEJB.findAll();
        listaAlmacen.remove(0);//eliminamos el "sin medicamento"
        listaLogin = LoginEJB.findAll();
        listaEstadisticas = EstadisticasEJB.findAll();
        
    }
    
    public void registrarPersonal(){
        try{
            listaLogin = LoginEJB.findAll();
            Login[] arrayLogin = new Login[listaLogin.size()];
            System.out.println(personal.getEmail()+ personal.getNombre()+ personal.getApellido1()+ personal.getApellido2());
            Generador generador = new Generador(personal.getEmail(), personal.getNombre(), personal.getApellido1(), personal.getApellido2(), listaLogin.toArray(arrayLogin));
            personal.setFechaAlta(LocalDateTime.now().toString());
            login.setPersonal(personal);
            login.setUsuario(generador.getUser());
            login.setContraseña(generador.getPassword());
            LoginEJB.create(login);
            FacesMessage mensajePersonalCreado = new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje", "Se ha creado el empleado y enviado el usuario y la contraseña al correo personal");
            FacesContext.getCurrentInstance().addMessage(null, mensajePersonalCreado);
            personal = null;
        }catch(Exception e){
            System.out.println("Error al registrar empleados:" + e.getMessage());
        }

    }
    
    public void registrarPacientes(){
        try{          
            personal = PersonalEJB.buscarPersonalPorId(personal.getIdTrabajador());
            paciente.setPersonal(personal);
            estancia.setIngreso(LocalDateTime.now().toString());
            paciente.setEstancia(estancia);
            paciente.setAlmacen(listaAlmacen.get(0));//asignamos una medicina por defecto
            PacientesEJB.create(paciente);
            FacesMessage mensajeCreadoPaciente = new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje", "Paciente añadido");
            FacesContext.getCurrentInstance().addMessage(null, mensajeCreadoPaciente);
            paciente = null;
        }catch(Exception e){
            System.out.println("Error al registrar pacientes:" + e.getMessage());
        }
    }
    
    public void buscarPaciente(){
        try{
            FacesMessage mensajePacienteNoEncontrado;
            if(busquedaPaciente.split(" ").length != 3){
                mensajePacienteNoEncontrado = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje", "Por favor introduce el nombre y los dos apellidos separados por espacios");
                FacesContext.getCurrentInstance().addMessage(null, mensajePacienteNoEncontrado);
                return;//si no ha metido el nombre completo no seguimos buscando
            }
            paciente = null;
            listaPacientes = PacientesEJB.findAll();
            for(int i = 0; i < listaPacientes.size(); i++){
                if(listaPacientes.get(i).getNombre().equals(busquedaPaciente.split(" ")[0]) && listaPacientes.get(i).getApellido1().equals(busquedaPaciente.split(" ")[1]) && listaPacientes.get(i).getApellido2().equals(busquedaPaciente.split(" ")[2])){
                    paciente = listaPacientes.get(i);
                    break;
                }
            }
            
            if(paciente == null){
                mensajePacienteNoEncontrado = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje", "Paciente no encontrado");
                FacesContext.getCurrentInstance().addMessage(null, mensajePacienteNoEncontrado);
            }else{
                nombreCompletoPaciente = paciente.getNombre() + " " + paciente.getApellido1() + " " + paciente.getApellido2();
            }
            
        }catch(Exception e){
            System.out.println("Error al buscar el paciente:" + e.getMessage());
        }
    }
    
    public void buscarPersonal(){
        try{
            FacesMessage mensajePersonalNoEncontrado;
            if(busquedaPersonal.split(" ").length != 3){
                mensajePersonalNoEncontrado = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje", "Por favor introduce el nombre y los dos apellidos separados por espacios");
                FacesContext.getCurrentInstance().addMessage(null, mensajePersonalNoEncontrado);
                return;
            }
            personal = null;
            listaPersonal = PersonalEJB.findAll();
            for(int i = 0; i < listaPersonal.size(); i++){
                if(listaPersonal.get(i).getNombre().equals(busquedaPersonal.split(" ")[0]) && listaPersonal.get(i).getApellido1().equals(busquedaPersonal.split(" ")[1]) && listaPersonal.get(i).getApellido2().equals(busquedaPersonal.split(" ")[2])){
                    personal = listaPersonal.get(i);
                    break;
                }
            }
            
            if(personal == null){
                mensajePersonalNoEncontrado = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje", "Personal no encontrado");
                FacesContext.getCurrentInstance().addMessage(null, mensajePersonalNoEncontrado);
            }else{
                nombreCompletoPersonal = personal.getNombre() + " " + personal.getApellido1() + " " + personal.getApellido2();
            }
            
        }catch(Exception e){
            System.out.println("Error al buscar el personal:" + e.getMessage());
        }
    }
    
    public void modificarPaciente(){
        try{
            almacen = AlmacenEJB.buscarAlmacenPorId(almacen.getIdProducto());
            //paciente.setAlmacen(almacen);
            personal = PersonalEJB.buscarPersonalPorId(personal.getIdTrabajador());
            paciente.setPersonal(personal);
            PacientesEJB.edit(paciente);
            paciente = null;
            FacesMessage mensajeModificadoPaciente = new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje", "Paciente modificado");
            FacesContext.getCurrentInstance().addMessage(null, mensajeModificadoPaciente);
        }catch(Exception e){
            System.out.println("Error al modificar paciente:" + e.getMessage());
        }
        
    }
    
    public void modificarTrabajador(){
        try{
            PersonalEJB.edit(personal);
            personal = null;
            FacesMessage mensajeModificadoPersonal = new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje", "Trabajador modificado");
            FacesContext.getCurrentInstance().addMessage(null, mensajeModificadoPersonal);
        }catch(Exception e){
            System.out.println("Error al modificar trabajador:" + e.getMessage());
        }
        
    }
    
    public void eliminarPersonal(){
        FacesMessage mensajeEliminadoPersonal;
        try{
            if(personal != null){
                listaLogin = LoginEJB.findAll();
                listaPacientes = PacientesEJB.findAll();
                
                for(int i = 0; i < listaLogin.size(); i++){
                    if(listaLogin.get(i).getPersonal().getIdTrabajador() == personal.getIdTrabajador()){
                        
                        for(int j = 0; j < listaPacientes.size(); j++){
                            if(listaPacientes.get(j).getPersonal().getIdTrabajador() == personal.getIdTrabajador()){//si el trabajador está asignado a un paciente no podemos borrarlo
                                mensajeEliminadoPersonal = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje", "No se puede eliminar el trabajador por que está asignado a uno o más pacientes");
                                FacesContext.getCurrentInstance().addMessage(null, mensajeEliminadoPersonal);
                                return;
                            }
                        }
                        LoginEJB.remove(listaLogin.get(i));//borramos el login y trabajador asignado
                        PersonalEJB.remove(personal);
                        personal = null;
                        mensajeEliminadoPersonal = new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje", "Trabajador eliminado");
                        FacesContext.getCurrentInstance().addMessage(null, mensajeEliminadoPersonal);
                        break;
                    }
                }
                
           }
        }
        
        catch(Exception e){
            System.out.println("Error al eliminar el trabajador:" + e.getMessage());
        }

    }
     
    public void eliminarPaciente(){
        FacesMessage mensajeEliminadoPaciente;
        try{
            if(paciente != null){
                PacientesEJB.remove(paciente);
                paciente = null;
                mensajeEliminadoPaciente = new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje", "Paciente eliminado");
                FacesContext.getCurrentInstance().addMessage(null, mensajeEliminadoPaciente);
           }
        }
        catch(Exception e){
            System.out.println("Error al eliminar el paciente:" + e.getMessage());
        }
    }
    
    public void ingresarPaciente(){
        try{
            if(paciente != null){
                paciente.getEstancia().setIngreso(LocalDateTime.now().toString());
                paciente.getEstancia().setAlta(null);
                EstanciasEJB.edit(paciente.getEstancia());
                
                listaEstadisticas.get(listaEstadisticas.size()-1).setIngreso(listaEstadisticas.get(listaEstadisticas.size()-1).getIngreso()+1);
                EstadisticasEJB.edit(listaEstadisticas.get(listaEstadisticas.size()-1));
                paciente = null;
                FacesMessage mensajeIngresadoPaciente = new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje", "Paciente ingresado");
                FacesContext.getCurrentInstance().addMessage(null, mensajeIngresadoPaciente);
           }
        }
        catch(Exception e){
            System.out.println("Error al ingresar paciente:" + e.getMessage());
        }
    }
    
    public void comprobarAlmacen(){
        listaAlmacen = AlmacenEJB.findAll();
        FacesMessage alertaAlmacen;
        for(int i = 0; i < listaAlmacen.size(); i++){
            if(listaAlmacen.get(i).getCantidad() == 0){
                alertaAlmacen = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Atención", "Revisar almacen, No quedan existencias de uno o más medicamentos");
                FacesContext.getCurrentInstance().addMessage(null, alertaAlmacen);
                return;
            }
        }
        
        alertaAlmacen = new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje", "El almacen tiene medicamentos suficientes");
        FacesContext.getCurrentInstance().addMessage(null, alertaAlmacen);
          
    }
    
    public void anadirMedicinas(){
        for(int i = 0; i < listaAlmacen.size(); i++){
            AlmacenEJB.edit(listaAlmacen.get(i));
        }
        FacesMessage mensajeMedicinasAnadidas = new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje", "Medicinas añadidas");
        FacesContext.getCurrentInstance().addMessage(null, mensajeMedicinasAnadidas);
    }

    public PersonalFacadeLocal getPersonalEJB() {
        return PersonalEJB;
    }

    public PacientesFacadeLocal getPacientesEJB() {
        return PacientesEJB;
    }

    public EstanciasFacadeLocal getEstanciasEJB() {
        return EstanciasEJB;
    }

    public AlmacenFacadeLocal getAlmacenEJB() {
        return AlmacenEJB;
    }

    public List<Pacientes> getListaPacientes() {
        return listaPacientes;
    }

    public List<Personal> getListaPersonal() {
        return listaPersonal;
    }

    public List<Estancias> getListaEstancias() {
        return listaEstancias;
    }

    public List<Almacen> getListaAlmacen() {
        return listaAlmacen;
    }

    public Pacientes getPaciente() {
        return paciente;
    }

    public Personal getPersonal() {
        return personal;
    }

    public String getNombreCompletoPaciente() {
        return nombreCompletoPaciente;
    }

    public void setNombreCompletoPaciente(String nombreCompletoPaciente) {
        this.nombreCompletoPaciente = nombreCompletoPaciente;
    }

    public String getNombreCompletoPersonal() {
        return nombreCompletoPersonal;
    }

    public void setNombreCompletoPersonal(String nombreCompletoPersonal) {
        this.nombreCompletoPersonal = nombreCompletoPersonal;
    }

    public Almacen getAlmacen() {
        return almacen;
    }

    public Estancias getEstancia() {
        return estancia;
    }

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

    public LoginFacadeLocal getLoginEJB() {
        return LoginEJB;
    }

    public void setLoginEJB(LoginFacadeLocal LoginEJB) {
        this.LoginEJB = LoginEJB;
    }

    public void setPersonalEJB(PersonalFacadeLocal PersonalEJB) {
        this.PersonalEJB = PersonalEJB;
    }

    public void setPacientesEJB(PacientesFacadeLocal PacientesEJB) {
        this.PacientesEJB = PacientesEJB;
    }

    public void setEstanciasEJB(EstanciasFacadeLocal EstanciasEJB) {
        this.EstanciasEJB = EstanciasEJB;
    }

    public void setAlmacenEJB(AlmacenFacadeLocal AlmacenEJB) {
        this.AlmacenEJB = AlmacenEJB;
    }

    public void setListaPacientes(List<Pacientes> listaPacientes) {
        this.listaPacientes = listaPacientes;
    }

    public void setListaPersonal(List<Personal> listaPersonal) {
        this.listaPersonal = listaPersonal;
    }

    public void setListaEstancias(List<Estancias> listaEstancias) {
        this.listaEstancias = listaEstancias;
    }

    public void setListaAlmacen(List<Almacen> listaAlmacen) {
        this.listaAlmacen = listaAlmacen;
    }

    public void setPaciente(Pacientes paciente) {
        this.paciente = paciente;
    }

    public void setPersonal(Personal personal) {
        this.personal = personal;
    }

    public void setAlmacen(Almacen almacen) {
        this.almacen = almacen;
    }

    public void setEstancia(Estancias estancia) {
        this.estancia = estancia;
    }

    public List<Login> getListaLogin() {
        return listaLogin;
    }

    public void setListaLogin(List<Login> listaLogin) {
        this.listaLogin = listaLogin;
    }

    public String getBusquedaPaciente() {
        return busquedaPaciente;
    }

    public void setBusquedaPaciente(String busquedaPaciente) {
        this.busquedaPaciente = busquedaPaciente;
    }

    public String getBusquedaPersonal() {
        return busquedaPersonal;
    }

    public void setBusquedaPersonal(String busquedaPersonal) {
        this.busquedaPersonal = busquedaPersonal;
    }

    public EstadisticasFacadeLocal getEstadisticasEJB() {
        return EstadisticasEJB;
    }

    public void setEstadisticasEJB(EstadisticasFacadeLocal EstadisticasEJB) {
        this.EstadisticasEJB = EstadisticasEJB;
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
        hash = 89 * hash + Objects.hashCode(this.PersonalEJB);
        hash = 89 * hash + Objects.hashCode(this.PacientesEJB);
        hash = 89 * hash + Objects.hashCode(this.EstanciasEJB);
        hash = 89 * hash + Objects.hashCode(this.AlmacenEJB);
        hash = 89 * hash + Objects.hashCode(this.LoginEJB);
        hash = 89 * hash + Objects.hashCode(this.EstadisticasEJB);
        hash = 89 * hash + Objects.hashCode(this.listaPacientes);
        hash = 89 * hash + Objects.hashCode(this.listaPersonal);
        hash = 89 * hash + Objects.hashCode(this.listaEstancias);
        hash = 89 * hash + Objects.hashCode(this.listaAlmacen);
        hash = 89 * hash + Objects.hashCode(this.listaLogin);
        hash = 89 * hash + Objects.hashCode(this.listaEstadisticas);
        hash = 89 * hash + Objects.hashCode(this.paciente);
        hash = 89 * hash + Objects.hashCode(this.personal);
        hash = 89 * hash + Objects.hashCode(this.almacen);
        hash = 89 * hash + Objects.hashCode(this.estancia);
        hash = 89 * hash + Objects.hashCode(this.login);
        hash = 89 * hash + Objects.hashCode(this.busquedaPaciente);
        hash = 89 * hash + Objects.hashCode(this.busquedaPersonal);
        hash = 89 * hash + Objects.hashCode(this.nombreCompletoPaciente);
        hash = 89 * hash + Objects.hashCode(this.nombreCompletoPersonal);
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
        final SecretarioController other = (SecretarioController) obj;
        if (!Objects.equals(this.busquedaPaciente, other.busquedaPaciente)) {
            return false;
        }
        if (!Objects.equals(this.busquedaPersonal, other.busquedaPersonal)) {
            return false;
        }
        if (!Objects.equals(this.nombreCompletoPaciente, other.nombreCompletoPaciente)) {
            return false;
        }
        if (!Objects.equals(this.nombreCompletoPersonal, other.nombreCompletoPersonal)) {
            return false;
        }
        if (!Objects.equals(this.PersonalEJB, other.PersonalEJB)) {
            return false;
        }
        if (!Objects.equals(this.PacientesEJB, other.PacientesEJB)) {
            return false;
        }
        if (!Objects.equals(this.EstanciasEJB, other.EstanciasEJB)) {
            return false;
        }
        if (!Objects.equals(this.AlmacenEJB, other.AlmacenEJB)) {
            return false;
        }
        if (!Objects.equals(this.LoginEJB, other.LoginEJB)) {
            return false;
        }
        if (!Objects.equals(this.EstadisticasEJB, other.EstadisticasEJB)) {
            return false;
        }
        if (!Objects.equals(this.listaPacientes, other.listaPacientes)) {
            return false;
        }
        if (!Objects.equals(this.listaPersonal, other.listaPersonal)) {
            return false;
        }
        if (!Objects.equals(this.listaEstancias, other.listaEstancias)) {
            return false;
        }
        if (!Objects.equals(this.listaAlmacen, other.listaAlmacen)) {
            return false;
        }
        if (!Objects.equals(this.listaLogin, other.listaLogin)) {
            return false;
        }
        if (!Objects.equals(this.listaEstadisticas, other.listaEstadisticas)) {
            return false;
        }
        if (!Objects.equals(this.paciente, other.paciente)) {
            return false;
        }
        if (!Objects.equals(this.personal, other.personal)) {
            return false;
        }
        if (!Objects.equals(this.almacen, other.almacen)) {
            return false;
        }
        if (!Objects.equals(this.estancia, other.estancia)) {
            return false;
        }
        if (!Objects.equals(this.login, other.login)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "SecretarioController{" + "PersonalEJB=" + PersonalEJB + ", PacientesEJB=" + PacientesEJB + ", EstanciasEJB=" + EstanciasEJB + ", AlmacenEJB=" + AlmacenEJB + ", LoginEJB=" + LoginEJB + ", EstadisticasEJB=" + EstadisticasEJB + ", listaPacientes=" + listaPacientes + ", listaPersonal=" + listaPersonal + ", listaEstancias=" + listaEstancias + ", listaAlmacen=" + listaAlmacen + ", listaLogin=" + listaLogin + ", listaEstadisticas=" + listaEstadisticas + ", paciente=" + paciente + ", personal=" + personal + ", almacen=" + almacen + ", estancia=" + estancia + ", login=" + login + ", busquedaPaciente=" + busquedaPaciente + ", busquedaPersonal=" + busquedaPersonal + ", nombreCompletoPaciente=" + nombreCompletoPaciente + ", nombreCompletoPersonal=" + nombreCompletoPersonal + '}';
    }

    
}