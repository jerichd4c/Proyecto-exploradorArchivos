//Librerias necesarias
import java.awt.*;
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

    //metodo para invocar UI (escrita)
    private JPanel crearPanelEditor() { 
        //creacion de panel dentro del menu principal para edicion de texto
        JPanel panel = new JPanel(new BorderLayout());
        JTextArea areaTexto = new JTextArea();
        //centrarlizar el area del texto
        panel.add(new JScrollPane(areaTexto), BorderLayout.CENTER);
        return panel;
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