//Librerias necesarias
import java.awt.*;
import java.io.*;
import java.nio.file.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.filechooser.*;

//Clase principal para ventana
public class FileExplorer extends  JFrame {
    //creacion de componente para ventanas emergentes
    private CardLayout cardLayout;
    //creacion de ventana principal
    private JPanel mainPanel;
    //creacion de variable para almacenar contador de palabras (se inserta dentro de panelStats)
    private JLabel etiquetaEstadistica;
    //booleano para verificar si el archivo esta modificado
    private boolean isModified;
    //variable para almacenar el archivo dentro del metodo
     private File currentFile;
    //creacion de la variable "areaTexto" dentro de la clase fileExplorer
    private JTextArea areaTexto;

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
        JButton botonGuardar= new JButton("Guardar");

        //action listener para abrir el archivo con los botones de windows
        botonGuardar.addActionListener(e -> guardarArchivo());
        botonAbrir.addActionListener(e -> abrirArchivo());
        //se agrega a la variable de botones
        panelBotones.add(botonAbrir);
        panelBotones.add(botonGuardar);

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
        //variable que registra una linea si y solo si hay un salto //n (nueva logica)
        int lines=0;
        //si en el espacio NO hay texto, no se cuentan lineas
        if (!texto.isEmpty()) {
            //agrupa TODAS las lineas vacias en un array, \n es el salto para detectar la linea vacia
            String[] lineasArray = texto.split("\n");
            //bucle que recorre el array en todas las lineas vacias
            for (String linea : lineasArray) {
                //trim verifica si no hay espacios entre palabras y isempty verifica si esta vacio despues del trim
                if (!linea.trim().isEmpty()) {
                    //si la linea no esta vacia, se cuenta y se agrega al array
                    lines++;
                }
            }
        }
       
        //metodo que actualizara la etiqueta con los datos obtenidos (%d)
        etiquetaEstadistica.setText(String.format(
            "Caracteres: %d | Palabras: %d | Lineas: %d", chars, words, lines));
        }

    //metodo para abrir el archivo
    private void abrirArchivo() {
        //variable tipo JFileChooser para abrir archivos (usa directorio del usuario)
        JFileChooser fileChooser = new JFileChooser();

        //Opcional: quitar slashes si se quiere activar verificacion automatica

        //Filtro para que solo se puedan abrir archivos.txt
        //FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos TXT", "txt");
        //se escoge de filtro el filtro anteriormente creado
        //fileChooser.setFileFilter(filter);
        //todos los demas extensiones de archivos son bloqueadas
        //fileChooser.setAcceptAllFileFilterUsed(false);

        //condicional que tiene de argumento fileExplorer y se cumple si es una opcion valida (setter)
        if (fileChooser.showOpenDialog(this)== JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            //condicional que verifica y obtiene el nombre del archivo, lo pone en minusculas, y verifica si termina en .txt (SI NO SE CUMPLE UNA NO ES VALIDO)
            if (!selectedFile.getName().toLowerCase().endsWith(".txt")) {
                //mensaje de error si no se cumple la condicion
                JOptionPane.showMessageDialog(this, "Solo se permiten archivos .txt", "Tipo de archivo invalido", JOptionPane.ERROR_MESSAGE);
                //regresa al inicio
                return;
            }
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

    //metodo para guardar el archivo
    private void guardarArchivo(){
        //variable para guardar el archivo
        JFileChooser fileChooser = new JFileChooser();
        //filtro que solo permite archivos .TXT
        fileChooser.setFileFilter(new FileNameExtensionFilter("Archivos TXT", "txt"));
        //el archivo selecionado sera el de la clase actual (FileExplorer) (setter)
        int result = fileChooser.showSaveDialog(this);
        //condicional de que si se cumple, el archivo se guarda en la variable
        //primer condicional
        if (result == JFileChooser.APPROVE_OPTION) {
            File archivo = fileChooser.getSelectedFile();
            //condicional que expresa si el archivo no es un .txt, retorna la ruta absoluta del archivo y le agrega .txt
            //segundo condicional
            if (!archivo.getName().toLowerCase().endsWith(".txt")) {
                archivo = new File(archivo.getAbsolutePath() + ".txt");
            }
            // confirmar sobreescritura si existe un archivo con el mismo nombre
            if (archivo.exists()) {
                int confirmacion = JOptionPane.showConfirmDialog(this,
                    "El archivo ya existe. ¿Desea sobreescribirlo?",
                    "Confirmar", 
                    // se le dara la opcion al usuario de escoger SI o NO
                    JOptionPane.YES_NO_OPTION
                    );
                    //si no es ninguna de las dos, cancelar
                    if (confirmacion != JOptionPane.YES_OPTION) return;
                    }
                    try (FileWriter writer = new FileWriter(archivo)) {
                        writer.write(areaTexto.getText());
                        currentFile = archivo;
                        isModified = false; 
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(this, "Error al guardar el archivo", "Error", JOptionPane.ERROR_MESSAGE);
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
