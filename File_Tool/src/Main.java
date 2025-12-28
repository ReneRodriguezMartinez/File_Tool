//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public static void main() {
    menu();
}


//metodo que retorna una ruta de un archivo aleatorio dentro de una carpeta
public static String archivoAleatorio(String ruta , String extension) {
    //creamos un archivo File con la ruta que nos llego por parametros
    File carpeta = new File(ruta);

    // Si no existe o no es carpeta, no hay archivos
    if (!carpeta.exists() || !carpeta.isDirectory()) {
        return null;
    }

    // Lista donde guardaremos todos los archivos encontrados
    List<File> archivosEncontrados = new ArrayList<>();

    //Usamos el metodo para crear una lsita de archivos
    buscarArchivos(carpeta, extension, archivosEncontrados);

    // Si no se encontró ningún archivo si la lista esta vacia vaya
    if (archivosEncontrados.isEmpty()) {
        return null;
    }

    // Elegimos uno aleatorio
    Random random = new Random();
    File archivoAleatorio = archivosEncontrados.get(random.nextInt(archivosEncontrados.size()));


    // Devolvemos su ruta completa
    return archivoAleatorio.getAbsolutePath();
}

//metodo que retornara un numero entero , este sera el total de archivos de la extensión expecificada  en la ruta también expecificada
public static int contarArchivos(String ruta, String extension){
    //creamos un archivo File con la ruta que nos llego por parametros
    File carpeta = new File(ruta);
    //variable que llevara la cuenta de archivos con la extensión expecificada dentro de esta carpeta
     int contador = 0;

    //Si no existe o no es carpeta, significa que no es archivos
    if (!carpeta.exists() || !carpeta.isDirectory()) {
        //por lo tanto devolvemos 0 por que no hay ningun archivo
        return 0;
    }
    //creamos un vector con los archivos de la carpeta
    File[] archivos = carpeta.listFiles();

    // comprobamos que la lsita no este vacia
    if (archivos != null) {
        //Entramos en bucle con total de veces de archivos que hay en la carpeta ( archivo sera el archivo de cada vuelta ) 
        for (File archivo : archivos) {
                if (archivo.isDirectory()) {
                    // si a su vez este archivo es una carpeta usamos este mismo metodo para contar también los archivos dentro de estas. 
                    contador += contarArchivos(archivo.getAbsolutePath(), extension);
                }else{
                    //si el archivo termina por la extensión 
                    if (archivo.getName().endsWith(extension)) {
                        contador++;
                    }
                }
        }
    }

    return  contador;
}

private static void buscarArchivos(File carpeta, String extension, List<File> lista) {
    //creamos un vector donde guardamos todos los archivos de la carpeta
    File[] archivos = carpeta.listFiles();
    // comprobamos que la lsita no este vacia
    if (archivos != null) {
        //Entramos en bucle con total de veces de archivos que hay en la carpeta ( archivo sera el archivo de cada vuelta )
         for (File archivo : archivos) {
             if (archivo.isDirectory()) {
                // si a su vez este archivo es una carpeta usamos este mismo metodo
                 buscarArchivos(archivo, extension, lista);
             }else{
                // Si tiene la extensión o añadimos a la lista
                if (archivo.getName().endsWith(extension)) {
                    lista.add(archivo);
                }
             }
         }
    }


}

public static void mostrarArbol(String ruta) {
    //creamos un archivo File con la ruta que nos llego por parametros
    File carpeta = new File(ruta);
    //Si no existe o no es carpeta, significa que no es archivos
    if (!carpeta.exists() || !carpeta.isDirectory()) {
        System.out.println("La ruta no es válida.");
        return;
    }

    // Mostramos la carpeta raíz 
    System.out.println(carpeta.getName());

    // Llamamos al metodo que dibujara todo le pasamos la carpeta 
    dibujarArbol(carpeta, "", true);
}

private static void dibujarArbol(File carpeta, String prefijo, boolean esUltimo) {
    //cramos un vector con el contenido de la carpeta
    File[] archivos = carpeta.listFiles();
    //si esta vacia terminamos
    if (archivos == null) return;

    //recorremos toda la carpeta
    for (int i = 0; i < archivos.length; i++) {
        //el archivo de esta vuelta
        File archivo = archivos[i];
        //si es el ultimo pq i = a la longitud de la lista 
        boolean ultimo = (i == archivos.length - 1);

        // Dibujar las ramas ramas si es ultimo dibujamos la rama diferente
        System.out.print(prefijo);
        if (ultimo) {
            System.out.print("└── ");
        } else {
            System.out.print("├── ");
        }
        //mostramos el archivo
        System.out.println(archivo.getName());

        // Si es carpeta, seguimos
        if (archivo.isDirectory()) {
            String nuevoPrefijo = prefijo + (ultimo ? "    " : "│   ");
            dibujarArbol(archivo, nuevoPrefijo, ultimo);
        }
    }
}




public static void menu(){
    int opc=-1;
    String extension = " ", ruta= " ";
    
    do { 
        System.out.println("Seleccione una opción.");
        System.out.println("1--> Contar archivos de una carpeta.");
        System.out.println("2--> Ruta de un archivo aleatorio de una carpeta.");
        System.out.println("3--> Mostrar carpeta en forma de arbol.");
        System.out.println("0--> Salir");
        opc = introducirUnNumero();
        switch (opc) {
            case 1:
                System.out.println("Introduzca el tipo de archivo que quiere contar. ( Ejemplo .png )");
                extension = introducirUnString();
                System.err.println("Ahora introduzca la ruta de la carpeta");
                ruta = introducirUnString();
                if (ruta != null){
                    System.out.println("Total de archivos con la extension "+ extension +" en la carpeta es :  "+contarArchivos(ruta, extension));
                }else{
                    System.out.println("La ruta no era valida.");
                }
               break;
            case 2:
                System.out.println("Introduzca el tipo de archivo que quiere. ( Ejemplo .png )");
                extension = introducirUnString();
                System.err.println("Ahora introduzca la ruta de la carpeta");
                ruta = introducirUnString();
                if (ruta != null){
                    System.out.println("La ruta aleatoria elegida es :" + archivoAleatorio(ruta, extension));
                }else{
                    System.out.println("La ruta no era valida.");
                }
                break;
            case 3:
                System.err.println("Ahora introduzca la ruta de la carpeta");
                ruta = introducirUnString();
                if (ruta != null){
                    System.out.println("Total de archivos con la extension "+ extension +" en la carpeta es :  "+contarArchivos(ruta, extension));
                }else{
                    System.out.println("La ruta no era valida.");
                }
                mostrarArbol(ruta);
        }
    } while (opc!=0);
}

//Metodo que admite al usuario meter un entero y este metodo lo retorna
public static int introducirUnNumero(){
    Scanner entrada = new Scanner(System.in);
    int numero = -1;
    try {
        numero=entrada.nextInt();
    } catch (Exception e) {
        System.out.println("eso no es un numero");
    }
    return numero;
}
//Metodo que admite al usuario meter un String y este metodo lo retorna
public static String introducirUnString(){
    Scanner entrada = new Scanner(System.in);
    String string = "null";
    try {
        string=entrada.nextLine();
    } catch (Exception e) {
        System.out.println("eso no es un numero");
    }
    return string;
}