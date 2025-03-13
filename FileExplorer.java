//Librerias necesarias
import java.awt.*;
import java.io.*;
import java.nio.file.*;
import javax.swing.*;

//Clase principal para ventana
public class FileExplorer extends  JFrame {
    //creacion de componente para ventanas emergentes
    private CardLayout cardLayout;
    //creacion de ventana principal
    private JPanel mainPanel;
    //metodo para inicializar la ventana con parametros default
    public FileExplorer(){
        //titulo del programa
        super("Explorador de Archivos");
        //metodo para cerrar la ventana al presionar la x
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //metodo para ajustar la dimension de la ventana
        setSize(800, 600);
        //metodo para centrar la ventana
        setLocationRelativeTo(null);
        //metodo para hacer que la ventana siempre se muestre al inicio
        setVisible(true);
        //asignacion de variables layout y panel, con cardLayout se pueden manejar multiples 
        //ventanas con jpanel
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        //creacion de botones editor y calculadora para cambiar de ventana
        mainPanel.add(crearPanelEditor(), "EDITOR");
        mainPanel.add(new JPanel(), "CALCULADORA");
        //metodo para agregar el panel al menu principal
        add(mainPanel);
        //
        //Barra de menu basica para navegar
        //creacion de barra de menu
        JMenuBar menuBar = new JMenuBar();
        //creacion del boton para la barra de menu
        JMenu menu = new JMenu("Navegacion");
        //popup menu para la barra de menu
        JMenuItem itemEditor = new JMenuItem("Editor");
        //accion para activar ventana de editor
        itemEditor.addActionListener(e -> cardLayout.show(mainPanel, "EDITOR"));
        //se agrega itemEditor a menu y se ejecuta cuando se activa addActionListener
        menu.add(itemEditor);
        //se agrega barra a menuBar
        menuBar.add(menu);
        //agrega barra a ventana principal
        setJMenuBar(menuBar);
    }
    //creacion de la variable "areaTexto" dentro de la clase fileExplorer
    private JTextArea areaTexto;

    //metodo para invocar UI (escrita)
    private JPanel crearPanelEditor() { 
        //creacion de panel dentro del menu principal para edicion de texto
        JPanel panel = new JPanel(new BorderLayout());
        areaTexto = new JTextArea();
        //panel de botones
        //variable para panel de botones (parte superior)
        JPanel panelBotones = new JPanel();
        JButton botonAbrir= new JButton("Abrir");  
        //action listener para abrir el archivo con los botones de windows
        botonAbrir.addActionListener(e -> abrirArchivo());
        //se agrega a la variable de botones
        panelBotones.add(botonAbrir);
        //centralizar el area del texto
        //ahora los botones estan en la parte superior y el area de texto en el centro
        panel.add(panelBotones, BorderLayout.NORTH);
        panel.add(new JScrollPane(areaTexto), BorderLayout.CENTER);
        return panel;
    }

    //metodo para abrir el archivo
    private void abrirArchivo() {
        //variable tipo JFileChooser para abrir archivos
        JFileChooser fileChooser = new JFileChooser();
        //condicional que tiene de argumento fileExplorer y se cumple si es una opcion valida (setter)
        if (fileChooser.showOpenDialog(this)== JFileChooser.APPROVE_OPTION) {
           try {
               //variable para almacenar el contenido del archivo (usa el explorador de archivos para seleccionar el archivo)
               String contenido = new String(Files.readAllBytes(fileChooser.getSelectedFile().toPath()));
               //implementa el contenido del archivo en el area de texto
               areaTexto.setText(contenido);
           } catch (IOException ex) { 
            //Error si el archivo que se abre tiene un formato invalido
            JOptionPane.showMessageDialog(this, "Error al abrir el archivo", "Error", JOptionPane.ERROR_MESSAGE);
           }
            
        }
        
    }

    //ejecucion main
    public static void main(String[] args) {
        //execucion del metodo para creacion de ventana
        SwingUtilities.invokeLater(() -> {
            FileExplorer frame= new FileExplorer();
            frame.setVisible(true);
        });
    }
}

// //Botones principales para los botones
// class FileEditorPanel extends JPanel {
//     //variable para el area de texto
//     private JTextArea areaTexto;
//     //variable para los botones
//     private JButton botonAbrir, botonGuardar, botonCerrar;
//     //metodo para la aparicion de botones en la interfaz
//     public FileEditorPanel() {
//         //asignacion de layout
//         setLayout(new BorderLayout());
//         //panel de botones
//         JPanel panelBotones = new JPanel();
//         botonAbrir= new JButton("Abrir");
//         botonGuardar = new JButton("Guardar");
//         botonCerrar = new JButton("Cerrar");
//         panelBotones.add(botonAbrir);
//         panelBotones.add(botonGuardar);
//         panelBotones.add(botonCerrar);
//         //area de texto
//         //uso de area texto dentro del metodo fileeditorpanel
//         areaTexto = new JTextArea();
//         //creacion de variable para el scroll (barra de desplazamiento)
//         JScrollPane scrollPane= new JScrollPane(areaTexto);
//         //asignacion de botones en la parte superior del explorador
//         add(panelBotones, BorderLayout.NORTH);
//         //area de texto (ventana) en el centro del explorador
//         add(scrollPane, BorderLayout.CENTER);
//     }
// }