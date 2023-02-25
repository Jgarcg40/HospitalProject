package com.IS2.Email;
import com.IS2.model.Login;
import java.io.IOException;


public class Generador {

    private String email;
    private String nombre;
    private String apellido1;
    private String apellido2;
    private Login[] listaUsuarios;
    private String user;
    private String password;
    
    public Generador(String email, String nombre, String apellido1, String apellido2, Login[] listaUsuarios){
        
	this.email = email;
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.listaUsuarios = listaUsuarios;

	user = genUser(nombre, apellido1, apellido2, listaUsuarios);
	password = genPassword();
	
	String msn = "Saludos " + nombre + " " + apellido1 + " " + apellido2
			+ ", ha entrado a formar parte de la plantilla del hospital FernJon, le adjuntamos el usuario y contraseña\n\n"
			+ "Usuario: " + user + "\n" + "Contraseña: " + password;

	Email mail = new Email(email, "NO CONTESTAR A ESTE CORREO\n" + "ALTA HOSPITAL FERNJON", msn);
	try {
		mail.send();// Enviamos el email

	} catch (IOException e) {
		e.printStackTrace();

	}
    }    
	

    private static String genPassword() {

            String alphabet = "abcdefghijklmnopqrstuvwxyz1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ";
            StringBuilder password = new StringBuilder();

            int i = 0;
            while (i < 7) {
                    int rand = (int) ((Math.random() * ((61 - 0) + 1)));// Con el random generamos la contraseña sacando los caracteres del array alphabet

                    password.append(alphabet.charAt(rand));

                    i++;
            }

            return password.toString();
    }

    private static String genUser(String nombre, String apellido1, String apellido2, Login[] listaUsuarios) {
            
            StringBuilder sbName = new StringBuilder();// Formamos el nombre de usuario

            sbName.append(nombre.charAt(0));// Primera letra del nombre

            sbName.append(apellido1.charAt(0));
            sbName.append(apellido1.charAt(1));// Dos primeras letras del primer apellido
            sbName.append(apellido2.charAt(0));
            sbName.append(apellido2.charAt(1));// Dos primeras letras del segundo apellido

            int numberOfUser = 0;
            
            for (int i = 0; i < listaUsuarios.length; i++) {// Vamos comprobando nombre por nombre

                    char[] nameBuffer = listaUsuarios[i].getUsuario().toCharArray();
                    char[] secondBuffer = new char[5];

                    for (int j = 0; j < 5; j++) {

                            secondBuffer[j] = nameBuffer[j];// Quitamos los numero del nombre de usuario
                    }

                    if (String.valueOf(secondBuffer).equals(sbName.toString().toLowerCase())) {

                            numberOfUser++;// Contamos los ususarios con el mismo nombre y aniadimos un numero para que no
                                                            // se repita

                    }

            }

            sbName.append(numberOfUser);// Aniadimos el numero
            return sbName.toString().toLowerCase();

    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}

