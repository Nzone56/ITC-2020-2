/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package afpd_project;
import java.util.*;
import java.io.*;
/**
 *
 * @author Magnert
 */
public class AFD {
    
    ArrayList<String> conjunto_estados = new ArrayList();
    String estado_inicial;
    ArrayList<String> estados_aceptacion = new ArrayList();       //VARIABLES QUE GUARDARAN LOS ATRIBUTOS DEL AUTOMATA
    ArrayList<String> alfa_cinta = new ArrayList();
    ArrayList<String> funcion_transicion = new ArrayList();
    String[][] matriz_transicion;
    
    public static void main(String[] args) {
        AFD automata = new AFD();
        automata.inicializar();                                  //SE INICIALIZA EL PROGRAMA
    }
    
    public void inicializar(){
        Ficheros ficheros = new Ficheros();
        Cadenas manejo_cadenas = new Cadenas();                 //SE CREAN LOS OBJETOS 
        
        ficheros.escribir_automata("C:\\Diego\\Programación\\AFPD\\FinalProjectIntro\\AFPD_Project\\src\\afpd_project\\fichero.dfa");                           
        boolean  lectura_correcta;                              
        lectura_correcta = ficheros.leer_archivo("C:\\Diego\\Programación\\AFPD\\FinalProjectIntro\\AFPD_Project\\src\\afpd_project\\prueba1.dfa");   
        if(lectura_correcta){                                   //SI TODOS LOS DATOS LEIDOS SON CORRECTOS ENTONCES SE SIGUE CON EL PROCESO
            matriz_transicion = new String[4][funcion_transicion.size()];    //SE INICIALIZA LA MATRIZ DE TRANSICIÓN
            generar_matriz();                                                //SE GENERA UNA MATRIZ CON LAS TRANSICIONES DEL AUTOMATA
            manejo_cadenas.cargar_cadenas();                                 //SE CARGAN LAS CADENAS POR CONSOLA
        } else{
            System.out.println("No se pude leer el archivo");
        }
    }
    
    public void generar_matriz(){                                //MÉTODO QUE GENERA UNA MATRIZ DE TRANSICIONES
        String instruccion;
        String[] partes;
        for(int i = 0; i < funcion_transicion.size(); i++){
            instruccion = funcion_transicion.get(i);       
            partes = instruccion.split(":"); 
            matriz_transicion[0][i] = partes[0];     //COLUMNA 0 -> ESTADO ORIGEN
            matriz_transicion[1][i] = partes[1];    //COLUMNA 1 -> LETRA DEL ALFABETO DE CINTA CON LA QUE FUNCIONA LA TRANSICIÓN 
            matriz_transicion[2][i] = partes[2];   //COLUMNA 3 -> ESTADO DESTINO
        }
    }
    
    public class Ficheros{   //CLASE QUE MANEJA TODO TIPO DE OPERACIÓN CON FICHEROS        
        public String to_String(String dir){
            File fichero = new File(dir);
            String linea;
            String to_String = "";
            Scanner s = null;
            try{
                s = new Scanner(fichero);
                while(s.hasNextLine()){
                    linea = s.nextLine(); 
                    to_String = to_String.concat(linea)+"?"; 
                }
            }catch(Exception ex){
                System.out.println("Mensaje: " + ex.getMessage());
            }finally{
                try{
                    if (s != null)              //SE CAPTURAN LAS EXCEPCIONES DE LECTURA
                    s.close();
                }catch(Exception ex2){
                    System.out.println("Mensaje 2: " + ex2.getMessage());
                }
            }
            return to_String;
        }
        
        public boolean leer_archivo(String dir){
            File fichero = new File(dir);
            String linea;
            int counter = 0;
            boolean etiqueta;
            boolean todo_correcto = true;
            Scanner s = null;
            try{
                s = new Scanner(fichero);
                while(s.hasNextLine()){
                    linea = s.nextLine(); 
                    switch(linea){
                        case "#states":
                            counter = 1;
                            etiqueta = true;
                            break;
                        case "#initial":
                            counter = 2;
                            etiqueta = true;
                            break;
                        case "#accepting":
                            counter = 3;
                            etiqueta = true;
                            break;
                        case "#alphabet":            //CADA QUE SE LEE UNA ETIQUETA SE DA EL AVAL PARA LEER Y GUARDAR ATRIBUTOS EN LA SIGUIENTE LINEA
                            counter = 4;
                            etiqueta = true;
                            break;
                        case "#transitions":
                            counter = 5;
                            etiqueta = true;
                            break;
                        default:
                            etiqueta = false;
                    }
                    if(etiqueta == false){      //SI LA LINEA LEIDA NO ES ETIQUETA ENTONCES SE GUARDAN LOS DATOS
                        switch(counter){
                            case 1:
                                if(conjunto_estados.contains(linea)){    //SE VÁLIDA QUE NO HAYAN ESTADOS REPETIDOS
                                    System.out.println("Hay estados repetidos en el conjunto de estados");
                                    todo_correcto = false;
                                }else{
                                    conjunto_estados.add(linea);
                                }
                                break;
                            case 2:
                                if(conjunto_estados.contains(linea)){     //SE VÁLIDA QUE EL ESTADO INICIAL TIENE QUE PERTENECER AL CONJUNTO DE ESTADOS
                                    estado_inicial = linea;
                                }else{
                                    todo_correcto = false;           
                                    System.out.println("El estado inicial tiene que pertenecer al conjunto de estados");
                                }
                                break;
                            case 3:
                                if(conjunto_estados.contains(linea)){
                                    if(estados_aceptacion.contains(linea)){   //SE VALIDA QUE NO HAYAN ESTADOS DE ACEPTACIÓN REPETIDOS
                                        System.out.println("Hay estados de aceptación repetidos");
                                        todo_correcto = false;
                                    }else{
                                        estados_aceptacion.add(linea); 
                                    }
                                }else{
                                    todo_correcto = false;     //SE VALIDA QUE LOS ESTADOS DE ACEPTACIÓN PERTENEZCAN AL CONJUNTO DE ESTADOS
                                    System.out.println("Los estados de aceptación tienen que pertenecer al conjunto de estados");
                                }
                                break;
                            case 4:
                                if(alfa_cinta.contains(linea)){   //SE VALIDA QUE NO HAYAN ELEMENTOS EN ALFABETO REPETIDOS
                                    System.out.println("Hay elementos del alfabeto de cinta repetidos");
                                    todo_correcto = false;
                                }else{
                                   alfa_cinta.add(linea); 
                                }
                                break;
                            case 5:
                                funcion_transicion.add(linea.replace('>', ':'));    //SE REEMPLAZA > POR : POR FACILIDAD DE DISEÑO Y LECTURA
                                break;
                        }
                    }
                }
            }catch(Exception ex){
                System.out.println("Mensaje: " + ex.getMessage());
            }finally{
                try{
                    if (s != null)              //SE CAPTURAN LAS EXCEPCIONES DE LECTURA
                    s.close();
                }catch(Exception ex2){
                    System.out.println("Mensaje 2: " + ex2.getMessage());
                }
            }
            return todo_correcto;     //SI LOS ATRIBUTOS DEL AUTOMATA SON CORRECTOS SE RETORNA TRUE, EN OTRO CASO FALSE
        }
        
        public void escribir_automata(String dir){   //METODO UTILIZADO PARA ESCRIBIR UN AUTOMATA EN UN ARCHIVO EN CASO DE NO TENER UN ARCHIVO CON UN AUTOMATA PREVIO
            FileWriter fichero = null;
            try{
                fichero = new FileWriter(dir);
                fichero.write("#states"+"\n");
                fichero.write("q0"+"\n");
                fichero.write("q1"+"\n");
                fichero.write("#initial"+"\n");
                fichero.write("q0"+"\n");
                fichero.write("#accepting"+"\n");
                fichero.write("q0"+"\n");
                fichero.write("q1"+"\n");
                fichero.write("#alphabet"+"\n");          //INSERTAR AQUI EL AUTOMATA QUE DESEE PROBAR
                fichero.write("a"+"\n");
                fichero.write("b"+"\n");
                fichero.write("#transitions"+"\n");
                fichero.write("q0:a:$>q0"+"\n");
                fichero.write("q0:b:A>q1"+"\n");
                fichero.write("q1:b:A>q1"+"\n");
                fichero.close();
            }catch(Exception ex){
                System.out.println("Mensaje de la excepción: " + ex.getMessage());
            }
        }
        
        public void escribir_fichero_detalles(String cadena, String dir){     //MÉTODO UTILIZADO PARA ESCRIBIR LOS DETALLES DEL PROCESAMIENTO EN UN FICHERO
            String[] partes;
            partes = cadena.split(":");
            File log = new File(dir);
            try{
                if(!log.exists()){
                    log.createNewFile();
                }
                FileWriter fileWriter = new FileWriter(log, true);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                for(int i=0; i<partes.length; i++){
                    bufferedWriter.write(partes[i]+"\t");
                }
                bufferedWriter.write("\n");
                bufferedWriter.close();
            } catch(IOException e) {
                System.out.println("Error en la escritura de detalles en fichero");
            }
        }
    }
    
    
    public class procesamiento{     //CLASE ENCARGADA DEL PROCESAMIENTO
        
        public boolean procesar_cadena(String cadena){    //MÉTODO ENCARGADO DE PROCESAR LA CADENA
            String estado_actual;
            if(cadena.contains("$")){     //VERIFICA SI ES UNA CADENA QUE CONTIENE LAMBDA
                cadena = "";
                System.out.println("Cadena: $ ->");
            }else{
                System.out.print("Cadena: " + cadena + " ->");
            }
            char letra;
            boolean cadena_valida = true;
            int instruccion_encontrada = 0;
            estado_actual = estado_inicial;
            for(int i = 0; i < cadena.length(); i++){
                letra = cadena.charAt(i);
                if(instruccion_encontrada==i){
                    for(int j=0; j<funcion_transicion.size(); j++){    
                        if(estado_actual.equals(matriz_transicion[0][j]) && matriz_transicion[1][j].equals(Character.toString(letra))){
                            instruccion_encontrada++;
                            estado_actual = matriz_transicion[2][j];
                            break;
                        }
                        if(estado_actual.equals(matriz_transicion[0][j]) && matriz_transicion[1][j].equals("$")){
                            i--;
                        }
                    }
                }  
            }
            if(estados_aceptacion.contains(estado_actual) && instruccion_encontrada==cadena.length()){
                cadena_valida = true;
            }else{
                cadena_valida = false;
            }
            return cadena_valida;
        }
        
        public boolean procesar_cadena_detalles(String cadena, String nombre_fichero){       //ESTE MÉTODO PROCESA LA CADENA Y PROPORCIONA DETALLES DEL PROCESAMIENTO
            String estado_actual, cadena_para_escribir;
            if(cadena.contains("$")){
                cadena = "";
                System.out.print("(" + estado_inicial + ") -> ");
                cadena_para_escribir = "$: " + "(" + estado_inicial + ") -> ";
            }else{
                System.out.print("(" +estado_inicial + ", " + cadena + ") -> ");
                cadena_para_escribir = cadena + ": " + "(" + estado_inicial + " ," + cadena + ") -> ";
            }
            Ficheros opciones_ficheros = new Ficheros();
            char letra;
            int op_pila;
            boolean cadena_valida = true;
            int instruccion_encontrada = 0;
            estado_actual = estado_inicial;
            for(int i=0; i<cadena.length(); i++){
                letra = cadena.charAt(i);
                if(instruccion_encontrada==i){
                    for(int j=0; j<funcion_transicion.size(); j++){     
                        if(estado_actual.equals(matriz_transicion[0][j]) && matriz_transicion[1][j].equals(Character.toString(letra))){
                            instruccion_encontrada += 1;
                            estado_actual = matriz_transicion[2][j];
                            break;
                        }
                        if(estado_actual.equals(matriz_transicion[0][j]) && matriz_transicion[1][j].equals("$")){
                            i--;
                        }
                    }
                }
                if(cadena.substring(i+1).equals("")){
                    System.out.print("("+estado_actual+") -> ");   
                    cadena_para_escribir = cadena_para_escribir.concat("("+estado_actual+") -> ");
                }else{
                    System.out.print("("+estado_actual+","+cadena.substring(i+1)+") -> "); 
                    cadena_para_escribir = cadena_para_escribir.concat("("+estado_actual+","+cadena.substring(i+1)+") -> ");
                }  
            }
            if(estados_aceptacion.contains(estado_actual) && instruccion_encontrada==cadena.length()){
                cadena_valida = true;
            }else{
                cadena_valida = false;
            }   
            
            if(cadena_valida){
                cadena_para_escribir = cadena_para_escribir.concat(":YES");
            }else{
                cadena_para_escribir = cadena_para_escribir.concat(":NO");
            }
            opciones_ficheros.escribir_fichero_detalles(cadena_para_escribir, nombre_fichero);
            return cadena_valida;
        }   
    }
    
    public class Validaciones{         //CLASE DEDICADA A VALIDAR
        
        public boolean validacion_1(String cadena){         //ESTE MÉTODO VALIDA QUE TODAS LAS LETRAS DE LA CADENA A PROCESAS PERTENEZCAN AL ALFABETO DE LA CINTA
            boolean validar = true;
            if("$".contains(Character.toString(cadena.charAt(0)))){
                
            }else{
                for(int i=0; i<cadena.length(); i++){
                    if(!(alfa_cinta.contains(Character.toString(cadena.charAt(i))))){
                        validar = false;
                    }
                }
            }
            return validar;
        }
    }
    
    public class Cadenas{         //CLASE ENCARGADA DE MANEJAR TODO LO RELACIONADO A CADENAS
        Validaciones opciones_validar = new Validaciones();
        
        public void cargar_cadenas(){     //ESTE MÉTODO CARGA TODAS LAS CADENAS QUE SE DESEEN PROCESAR A UNA LISTA
            ArrayList<String> lista_cadenas = new ArrayList();
            Scanner scanner = new Scanner(System.in);
            int num_entradas, op_detalles;
            String cadena;
            String nombre_fichero = "C:\\Users\\Magnert\\Documents\\NetBeansProjects\\FinalProjectIntro\\fichero_salida.txt";
            File f = new File(nombre_fichero);
            f.delete();
            System.out.println("Ingrese la cantidad de cadenas que desea procesar:");
            num_entradas = Integer.parseInt(scanner.nextLine());
            if(num_entradas<=0){
                System.out.println("La cantidad de cadenas debe ser mayor a 0");        //SE CAPTURAN LAS CADENAS
            }else{
                for(int i=0; i<num_entradas; i++){
                    System.out.println("Ingrese la cadena "+(i+1));
                    cadena = scanner.nextLine();
                    if(opciones_validar.validacion_1(cadena)){         //SE COMPRUEBA QUE LAS CADENAS PROPORCIONADAS POR CONSOLA SEAN VÁLIDAS
                        lista_cadenas.add(cadena);                    
                    }else{
                        System.out.println("La cadena ingresada contiene letras que no hacen parte del alfabeto de la cinta");
                        break;
                    }
                } 
            }
            System.out.println("Desea imprimir los detalles del procesamiento en pantalla? Digite un número -> 1-Si/2-No");
            op_detalles = scanner.nextInt();
            if(op_detalles==1){
                procesarListaCadenas(lista_cadenas, nombre_fichero, true);
            }else{
                if(op_detalles==2){
                    procesarListaCadenas(lista_cadenas, nombre_fichero, false);
                }else{
                    System.out.println("Opción seleccionada no válida");
                }
            }
        }
        
        public void procesarListaCadenas(ArrayList<String> lista_c, String nombre_fichero, Boolean imprimirDetalles){      //ESTE MÉTODO MANDA A PROCESAMIENTO CADA CADENA DE LA LISTA
            procesamiento proceso = new procesamiento();
            boolean respuesta;
            if(imprimirDetalles){
                for(int i=0; i<lista_c.size(); i++){
                    respuesta = proceso.procesar_cadena_detalles(lista_c.get(i), nombre_fichero);
                    if(respuesta){
                        System.out.println("Accepted"); 
                    }else{
                        System.out.println("Rejected"); 
                    }
                }
            }else{ 
                for(int i=0; i<lista_c.size(); i++){
                    respuesta = proceso.procesar_cadena(lista_c.get(i));
                    if(respuesta){
                        System.out.println("Accepted"); 
                    }else{
                        System.out.println("Rejected"); 
                    }
                }
            }
        }
    }
    /*
    public class pruebas{
    }*/
}
