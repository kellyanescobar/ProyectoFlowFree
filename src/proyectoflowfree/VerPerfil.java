/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectoflowfree;
import java.awt.*;
import javax.swing.*;
/**
 *
 * @author laraj
 */

public class VerPerfil extends JPanel {
    private JLabel avatar, nombreAvatar;
    private JLabel usuario, nombre, fecha; 
    private JButton configuracion, regresar;
    private Image fondoImagen;

    public VerPerfil() {
        setLayout(null);

        fondoImagen = new ImageIcon(getClass().getResource("/imagenes/Perfil.png")).getImage();

        ImageIcon imagenAvatar = new ImageIcon("imagenes/avatar.png"); 
        avatar = new JLabel(imagenAvatar);
        avatar.setBounds(25, 100, 200, 200);
        add(avatar);

        // Nombre del avatar 
        nombreAvatar = new JLabel("ROSADO", SwingConstants.CENTER);
        nombreAvatar.setFont(new Font("Pixel Font", Font.BOLD, 18));
        nombreAvatar.setForeground(Color.PINK);
        nombreAvatar.setBounds(25, 170, 200, 30);
        add(nombreAvatar);

        // Usuario
       usuario = crearEtiquetaNoEditable("USUARIO", new Color(247, 186, 186), new Color(255, 49, 49));
       usuario.setBounds(30, 425, 200, 50);
       add(usuario);

        // Nombre
        nombre = crearEtiquetaNoEditable("NOMBRE", new Color(250, 201, 222), new Color(230, 31, 147));
        nombre.setBounds(300, 150, 200, 40);
        add(nombre);

        // Fecha
        fecha = crearEtiquetaNoEditable("FECHA", new Color(200, 255, 247), new Color(0, 183, 231));
        fecha.setBounds(300, 220, 200, 40);
        add(fecha);


        // Boton Configuracion 
        configuracion = crearBotonEditable("CONFIGURACION",new Color(251, 210, 255), new Color(199, 0, 255));
        configuracion.setBounds(300, 290, 200, 40);
        configuracion.addActionListener(e -> abrirConfiguracion());
        add(configuracion);

        // Boton Regresar 
        regresar = crearBotonEditable("REGRESAR", new Color(246, 176, 164), new Color(234, 89, 35));
        regresar.setBounds(300, 360, 200, 40);
        regresar.addActionListener(e -> regresarMenu());
        add(regresar);
    }

    private JLabel crearEtiquetaNoEditable(String texto, Color bgColor, Color fgColor) {
    JLabel etiqueta = new JLabel(texto, SwingConstants.CENTER);
    etiqueta.setFont(new Font("Pixel Font", Font.BOLD, 14));
    etiqueta.setForeground(fgColor);  
    etiqueta.setOpaque(true);
    etiqueta.setBackground(bgColor);  
    etiqueta.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1)); 
    return etiqueta;
}


    private JButton crearBotonEditable(String texto, Color bgColor, Color fgColor) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Pixel Font", Font.BOLD, 14));
        boton.setForeground(fgColor);
        boton.setBackground(bgColor);
        boton.setBorderPainted(false);
        boton.setFocusPainted(false);
        boton.setContentAreaFilled(true);
        
        Color originalBg=bgColor;
        Color hoverBg=bgColor.darker();
        
        boton.addMouseListener(new java.awt.event.MouseAdapter() {
        @Override
        public void mouseEntered(java.awt.event.MouseEvent evt){
            boton.setBackground(hoverBg);
        }
        @Override
        public void mouseExited(java.awt.event.MouseEvent evt){
           boton.setBackground(originalBg); 
        }
        });
        
        return boton;
    }

    private void abrirConfiguracion() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        frame.dispose();
        new Configuracion().mostrarEnFrame();
    }

    private void regresarMenu() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        frame.dispose();
        new MenuPrincipal(Login.usuarioLogueado).mostrarEnFrame();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(fondoImagen, 0, 0, getWidth(), getHeight(), this);
    }

    public void mostrarEnFrame() {
        JFrame frame = new JFrame("Ver Perfil");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(this);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new VerPerfil().mostrarEnFrame();
        });
    }
}
