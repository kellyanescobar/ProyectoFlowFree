/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectoflowfree;
import java.io.*;
import javax.swing.JOptionPane;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
/**
 *
 * @author laraj
 */
public class Login implements Serializable {
    private String usuario;
    private String password;
    private String nombreCompleto;
    private String fechaRegistro;
    private String ultimaSesion;
    private int puntos;
    private int tiempoJugado;
    private String historialPartidas;
    private String avatar;
    private Map<String, String> preferencias;
    
    private static final String USER_DIR = "usuarios/";
    public static Login usuarioLogueado;
    
    private String avatarRuta = "/imagenes/ElegirAvatar.png"; 
    private String nombreAvatar = "Desconocido"; 
    
    public Login(String usuario, String password, String nombreCompleto) {
        this.usuario = usuario;
        this.password = password;
        this.nombreCompleto = nombreCompleto;
        this.fechaRegistro = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
        this.ultimaSesion = this.fechaRegistro;
        this.puntos = 0;
        this.tiempoJugado = 0;
        this.historialPartidas = "";
        this.avatar = "default.png";
        this.preferencias = new HashMap<>();
        this.preferencias.put("idioma", "español");
        this.preferencias.put("volumen", "100");
        
        File userFolder = new File(USER_DIR + usuario);
        if (!userFolder.exists()) {
            userFolder.mkdirs();
        }
        guardarDatos();
    }

    public void guardarDatos() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(USER_DIR + usuario + "/datos.dat"))) {
            out.writeObject(this);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar los datos del usuario.");
        }
    }

    public static Login cargarDatos(String usuario) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(USER_DIR + usuario + "/datos.dat"))) {
            return (Login) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }

    public static boolean iniciarSesion(String usuario, String password) {
        Login user = cargarDatos(usuario);
        if (user != null && user.password.equals(password)) {
            usuarioLogueado = user;
            usuarioLogueado.ultimaSesion = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date());
            usuarioLogueado.guardarDatos();
            JOptionPane.showMessageDialog(null, "Bienvenido " + usuario);
            return true;
        }
        JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos.");
        return false;
    }
    
    public static boolean registrarUsuario(String usuario, String password, String nombreCompleto) {
        if (new File(USER_DIR + usuario).exists()) {
            JOptionPane.showMessageDialog(null, "El usuario ya existe.");
            return false;
        }
        usuarioLogueado = new Login(usuario, password, nombreCompleto);
        JOptionPane.showMessageDialog(null, "Usuario registrado exitosamente.");
        return true;
    }
    
    public static void cerrarSesion() {
        if (usuarioLogueado != null) {
            usuarioLogueado.guardarDatos();
            usuarioLogueado = null;
        }
    }
    
   public void cambiarNombre(String nuevoNombre) {
    if (nuevoNombre != null && !nuevoNombre.trim().isEmpty()) {
        this.nombreCompleto = nuevoNombre;
        guardarDatos(); 
    }
}

   public boolean cambiarUsuario(String nuevoUsuario) {
    if (nuevoUsuario != null && !nuevoUsuario.trim().isEmpty()) {
        File oldFolder = new File(USER_DIR + this.usuario);
        File newFolder = new File(USER_DIR + nuevoUsuario);

        if (newFolder.exists()) {
            JOptionPane.showMessageDialog(null, "El nombre de usuario ya está en uso.");
            return false;
        }

        if (oldFolder.renameTo(newFolder)) {
            this.usuario = nuevoUsuario;
            guardarDatos();
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "No se pudo cambiar el usuario.");
            return false;
        }
    }
    return false;
}

    
    public boolean cambiarContraseña(String nuevaContraseña) {
    if (nuevaContraseña.length() >= 5) {
        this.password = nuevaContraseña;
        guardarDatos();
        return true;
    } else {
        JOptionPane.showMessageDialog(null, "La contraseña debe tener al menos 5 caracteres.");
        return false;
    }
}

    
    public boolean eliminarCuenta() {
        if (usuarioLogueado == null) return false;
        File userFolder = new File(USER_DIR + usuario);
        for (File file : userFolder.listFiles()) {
            file.delete();
        }
        userFolder.delete();
        usuarioLogueado = null;
        JOptionPane.showMessageDialog(null, "Cuenta eliminada exitosamente.");
        return true;
    }
    
    public void sumarPuntos(int puntosGanados) {
        this.puntos += puntosGanados;
        guardarDatos();
    }
    
    public void registrarPartida(String resultado) {
        this.historialPartidas += new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date()) + " - " + resultado + "\n";
        guardarDatos();
    }
    
    public void cambiarAvatar(String nuevoAvatar) {
        this.avatar = nuevoAvatar;
        guardarDatos();
    }
    
    public void actualizarPreferencias(String clave, String valor) {
        this.preferencias.put(clave, valor);
        guardarDatos();
    }
    
    public String obtenerHistorial() {
        return historialPartidas.isEmpty() ? "No hay partidas registradas." : historialPartidas;
    }
    
    public String getUsuario() {
    return usuario;
}

    public String getNombreCompleto() {
    return nombreCompleto;
}

    public String getFechaRegistro() {
    return fechaRegistro;
}
 
    
 public void setAvatar(String avatarPath, String avatarName) {
    this.avatar = avatarPath;
    this.nombreAvatar = avatarName;  
    guardarDatos();
}


public String getAvatar() {
    return this.avatar;
}   

public String getAvatarNombre() {
    return nombreAvatar;
}

}
