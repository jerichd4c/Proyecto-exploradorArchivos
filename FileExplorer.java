//Librerias necesarias
import java.awt.*;
import java.io.*;
import java.nio.file.*;
import javax.swing.*;
import javax.swing.event.*;

//Clase principal para ventana
public class FileExplorer extends  JFrame {
    //creacion de componente para ventanas emergentes
    private CardLayout cardLayout;
    //creacion de ventana principal
    private JPanel mainPanel;
    //creacion de variable para almacenar contador de palabras (se inserta dentro de panelStats)
    private JLabel etiquetaEstadistica;
    //agregar panel de calculadora como ventana secundaria (boton de popup menu)
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
        //creacion de botones editor y calculadora para cambiar de ventana (implementacion lista)
        mainPanel.add(crearPanelEditor(), "EDITOR");
        mainPanel.add(crearPanelCalculadora(), "CALCULADORA");

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

    //metodo para invocar UI (calculadora)
    private JPanel crearPanelCalculadora() {
        //creacion del popup con 3 filas y 2 columnas
        JPanel panel = new JPanel(new GridLayout(3,2));
        //se almacena primer valor (visualizacion)
        JTextField texto1 = new JTextField();
        //se almacena segundo valor (visualizacion)
        JTextField texto2 = new JTextField();
        //se almacena resultado (visualizacion)
        JLabel resultado = new JLabel("Resultado: ");

        panel.add(new JLabel("Primer valor: "));
        panel.add(texto1);
        panel.add(new JLabel("Segundo valor: "));
        panel.add(texto2);
        //se agrega boton para sumar, ejecutado por actionListener
        JButton botonSumar= new JButton("Sumar");
        botonSumar.addActionListener(e->{
            try {
                //se hace la operacion de sumar
                //convierte los numeros a enteros con parseInt
                int num1 = Integer.parseInt(texto1.getText());
                int num2 = Integer.parseInt(texto2.getText());
                //se muestra la operacion
                resultado.setText("Resultado: " + (num1 + num2));
            } catch (NumberFormatException ex) {
                //si hay un error se muestra un mensaje de error (entrada invalida)
                resultado.setText("Entrada invalida");
            }
        });
        //se agregan los botones al popup principal
        panel.add(botonSumar);
        panel.add(resultado);
        
        //se retorna el panel
        return panel;
    }

    //metodo para invocar UI (escritura de texto)
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

        //variable para el boton de calculadora
        JButton botonCalculadora= new JButton("Calculadora");
        //listener para hacer que la ventana de calculadora se muestre al presionar el boton
        botonCalculadora.addActionListener(e -> cardLayout.show(mainPanel, "CALCULADORA"));
        //se agrega a la barra principal
        panelBotones.add(botonCalculadora);

        //centralizar el area del texto
        //ahora los botones estan en la parte superior y el area de texto en el centro
        panel.add(panelBotones, BorderLayout.NORTH);
        panel.add(new JScrollPane(areaTexto), BorderLayout.CENTER);
        //variable para almacenar estadisticas
        JPanel panelEstadisticas = new JPanel();   
        //se inicializa la etiqueta con variables defaults (0)
        etiquetaEstadistica = new JLabel("Caracteres: 0 | Palabras: 0 | Lineas: 0");
        //etiquetaEstadistica ahora es el argumento de panelEstadisticas (variable)
        panelEstadisticas.add(etiquetaEstadistica);
        //las estadisticas se mostran en la parte inferior
        panel.add(panelEstadisticas, BorderLayout.SOUTH);
        //metodo listener para actualizar actualizar los datos (escucha y responde)
        //getDocument obtiene el area de texto y addDocumentListener escucha el area de texto (creacion)
        areaTexto.getDocument().addDocumentListener(new DocumentListener() {
            //si se inserta o remueve se llama a el metodo actualizarEstadisticas y se ejecuta
            public void insertUpdate(DocumentEvent e) { actualizarEstadisticas(); }
            public void removeUpdate(DocumentEvent e) { actualizarEstadisticas(); }
            public void changedUpdate(DocumentEvent e) {}
        });
        return panel;
    }

    //metodo para actualizar estadisticas
    private void actualizarEstadisticas() {
        //variable que almacena el texto leido
        String texto= areaTexto.getText();
        //variable que almacena el numero de caracteres
        int chars= texto.length();
        //variable cuya condicion es que registra palabra si y solo si hay un espacio entre los caracteres //s
        int words= texto.isEmpty() ? 0: texto.split("\\s+").length; 
        //variable que registra una linea si y solo si hay un salto //n
        int lines= texto.split("\n").length;
        //metodo que actualizara la etiqueta con los datos obtenidos (%d)
        etiquetaEstadistica.setText(String.format(
            "Caracteres: %d | Palabras: %d | Lineas: %d", chars, words, lines));
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