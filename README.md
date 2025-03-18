Proyecto relacionado a la parte de laboratorio de la materia Programacion orientada a objetos. 

1. Descripcion

Explorador de archivos creado en java para la escritura, gestion y modificacion de archivos con extension .TXT 

2. Metodos disponibles
   
  * Panel de escritura: metodo principal del explorador de archivos, panel en blanco en el centro de la ventana donde se puede escribir caracteres, y dichos caracteres seran contados en la parte inferior de la
  UI; #lineas, #Palabras, #Caracteres.
 
  * Abrir archivo: metodo basico para abrir archivos con extension .TXT (si es otra extension el programa no lo permitira abrir)
    
  * Guardar archivo: metodo para guardar archivos, este metodo convertira el texto creado/modificado automaticamente en un archivo con extension .TXT , ademas se le preguntara
    a el usuario si se quiere guardar con o sin contraseña.
      *Formas de guardado:
        1. Sin contraseña: el archivo no necesita de una contraseña para abrirse, se ejecuta el metodo de manera normal.
        2. Con contraseña: el programa le preguntara a el usuario para introducir la contraseña, si es correcta se abre, si es incorrecta sale una ventana adicional con un mensaje de error.
    
  * Cerrar archivo: metodo para cerrar o eliminar el contenido del archivo, en caso de que se detecte una modificacion en el archivo a traves de una variable booleana, se invocara una ventana adicional
    preguntandole a el usuario si se quiere guardar el contenido escrito (SI_NO_CANCELAR)
    
  * Calculadora: metodo basico que simula el comportamiento de una calculadora basica de dos numeros, contiene; suma, resta, multiplicacion, division y potencia (en caso de que se quiera volver al menu de escritura,
    se le da click a el boton navegador->vista en la parte superior del programa)

3. Requisitos para ejecutar el codigo
   
   A. un IDE que soporte java (Eclipse, Vscode, Netbeans)
   B. jdk (Java Development Kit, adquirir de la pagina oficial de ORACLE™)
