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
public class AFPD_Project {
    Scanner scanner = new Scanner(System.in);
    
    ArrayList<String> conjunto_estados = new ArrayList();
    String estado_inicial;
    ArrayList<String> estados_aceptacion = new ArrayList();       //VARIABLES QUE GUARDARAN LOS ATRIBUTOS DEL AUTOMATA AFPD
    ArrayList<String> alfa_cinta = new ArrayList();
    ArrayList<String> alfa_pila = new ArrayList();
    ArrayList<String> funcion_transicion = new ArrayList();
    String[][] matriz_transicion;
    
    ArrayList<String> conjunto_estados_AFD = new ArrayList();
    String estado_inicial_AFD;
    ArrayList<String> estados_aceptacion_AFD = new ArrayList();       //VARIABLES QUE GUARDARAN LOS ATRIBUTOS DEL AUTOMATA AFD
    ArrayList<String> funcion_transicion_AFD = new ArrayList();
    String[][] matriz_transicion_AFD;
    
    public static void main(String[] args) {
        AFPD_Project automata = new AFPD_Project();
        automata.inicializar();                                  //SE INICIALIZA EL PROGRAMA LLAMANDO EL MÉTODO "INICIALIZAR"
    }
    
    public void inicializar(){
        pruebas generar_pruebas = new pruebas();
        //generar_pruebas.prueba_1();
        //generar_pruebas.prueba_2();                           //ESTE MÉTODO LLAMA LA PRUEBA SELECCIONADA, SE DEBE SELECCIONAR UNA SOLA POR CADA EJECUCIÓN DEL PROGRAMA
        //generar_pruebas.prueba_3();                           //LAS PRUEBAS QUE NO SE UTILICEN DEBEN SER COMENTADAS
        //generar_pruebas.prueba_4();
        //generar_pruebas.prueba_5();
        generar_pruebas.prueba_6();
    }
    
    public void generar_matriz(){                                //MÉTODO QUE GENERA UNA MATRIZ DE TRANSICIONES DEL AFPD
            String instruccion;
            String[] partes;
            for(int i=0; i<funcion_transicion.size(); i++){
                 instruccion = funcion_transicion.get(i);       //SE SEPARAN LAS PARTES DE LA INSTRUCCIÓN DE TRANSICIÓN Y SE VAN GUARDANDO POR COLUMNAS.
                 partes = instruccion.split(":");               //CADA FILA ES UNA INSTRUCCIÓN DE TRANSICIÓN
                 matriz_transicion[0][i] = partes[0];           //COLUMNA 0 -> ESTADO ORIGEN
                 matriz_transicion[1][i] = partes[1];           //COLUMNA 1 -> LETRA DEL ALFABETO DE CINTA CON LA QUE FUNCIONA LA TRANSICIÓN
                 matriz_transicion[2][i] = partes[2];           //COLUMNA 2 -> ESTADO INICIAL PILA 
                 matriz_transicion[3][i] = partes[3];           //COLUMNA 3 -> ESTADO DESTINO
                 matriz_transicion[4][i] = partes[4];           //COLUMNA 4 -> ESTADO FINAL PILA
            }
        }
    
    public class ficheros{                       //CLASE QUE MANEJA TODO TIPO DE OPERACIÓN CON FICHEROS
        
        public String to_String(String dir){        //TOMA LA DIRECCIÓN DONDE SE ENCUENTRA EL AUTOMATA COMO PARAMETRO
            File fichero = new File(dir);
            String linea;
            String to_String = "";
            Scanner s = null;                   //ESTE MÉTODO REPRESENTA EL AUTOMANTA CON EL FORMATO DE ENTRADA ESPECIFICADO
            try{
                s = new Scanner(fichero);
                while(s.hasNextLine()){
                    linea = s.nextLine(); 
                    to_String = to_String.concat(linea)+";";     //LA IDEA CON ESTO ES GENERAR UN SPLIT CON ";" E IR MOSTRANDO LINEA POR LINEA EL AUTOMATA
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
            return to_String;           //SE RETORNA EL EQUIVALENTE EN STRING DEL AUTOMATA LEIDO
        }
        
        public boolean leer_archivo(String dir){            //ESTE MÉTODO LEE EL ARCHIVO DONDE SE ENCUENTRA EL AFPD
            File fichero = new File(dir);                   //SE PASA COMO PARAMETRO LA DIRECCIÓN DEL ARCHIVO
            String linea;
            int counter = 0;
            boolean etiqueta;
            boolean todo_correcto = true;                   //BOOLEANO QUE GUARDA SI LA LECTURA DEL AUTOMATA ES CORRECTA
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
                        case "#tapeAlphabet":            //CADA QUE SE LEE UNA ETIQUETA SE DA EL AVAL PARA LEER Y GUARDAR LOS ATRIBUTOS PRESENTES EN LA SIGUIENTE LINEA
                            counter = 4;                 //LA ETIQUETA SE PONE TRUE PARA INDICAR QUE LA SIGUIENTE LINEA YA SE PUEDE ALMACENAR COMO ATRIBUTO
                            etiqueta = true;             //EL CONTADOR ES PARA LLEVAR UN ORDEN EN CUANTO EN QUE SECCIÓN DEL AUTOMATA SE DEBE EMPEZAR A LEER
                            break;
                        case "#stackAlphabet":
                            counter = 5;
                            etiqueta = true;
                            break;
                        case "#transitions":
                            counter = 6;
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
                                if(alfa_pila.contains(linea)){    //SE VALIDA QUE NO HAYAN ELEMENTOS EN ALFABETO REPETIDOS
                                    System.out.println("Hay elementos del alfabeto de pila repetidos");
                                    todo_correcto = false;
                                }else{
                                   alfa_pila.add(linea); 
                                }
                                break;
                            case 6:
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
        
        public boolean leer_AFD(String dir){     //ESTE MÉTODO ES UTILIZADO PARA LEER EL AFD CON EL QUE POSTERIORMENTE SE HALLARA EL PRODUCTO CARTESIANO
            File fichero = new File(dir);        //SE TOMA LA DIRECCIÓN DEL ARCHIVO COMO PARÁMETRO
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
                            break;                          //EL FUNCIONAMIENTO ES SIMILAR AL MÉTODO ANTERIOR SOLO QUE ESTE AUTOMATA TIENE MENOS SECCIONES PARA LEER
                        case "#accepting":
                            counter = 3;
                            etiqueta = true;
                            break;
                        case "#transitions":
                            counter = 4;
                            etiqueta = true;
                            break;
                        default:
                            etiqueta = false;
                    }
                    if(etiqueta == false){      //SI LA LINEA LEIDA NO ES ETIQUETA ENTONCES SE GUARDAN LOS DATOS
                        switch(counter){
                            case 1:
                                if(conjunto_estados_AFD.contains(linea)){    //SE VÁLIDA QUE NO HAYAN ESTADOS REPETIDOS
                                    System.out.println("Hay estados repetidos en el conjunto de estados");
                                    todo_correcto = false;
                                }else{
                                    conjunto_estados_AFD.add(linea);
                                }
                                break;
                            case 2:
                                if(conjunto_estados_AFD.contains(linea)){     //SE VÁLIDA QUE EL ESTADO INICIAL TIENE QUE PERTENECER AL CONJUNTO DE ESTADOS
                                    estado_inicial_AFD = linea;
                                }else{
                                    todo_correcto = false;           
                                    System.out.println("El estado inicial tiene que pertenecer al conjunto de estados");
                                }
                                break;
                            case 3:
                                if(conjunto_estados_AFD.contains(linea)){
                                    if(estados_aceptacion_AFD.contains(linea)){   //SE VALIDA QUE NO HAYAN ESTADOS DE ACEPTACIÓN REPETIDOS
                                        System.out.println("Hay estados de aceptación repetidos");
                                        todo_correcto = false;
                                    }else{
                                        estados_aceptacion_AFD.add(linea); 
                                    }
                                }else{
                                    todo_correcto = false;     //SE VALIDA QUE LOS ESTADOS DE ACEPTACIÓN PERTENEZCAN AL CONJUNTO DE ESTADOS
                                    System.out.println("Los estados de aceptación tienen que pertenecer al conjunto de estados");
                                }
                                break;
                            case 4:
                                funcion_transicion_AFD.add(linea.replace('>', ':'));    //SE REEMPLAZA > POR : POR FACILIDAD DE DISEÑO Y LECTURA
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
        
        public void escribir_automata(String dir){   //MÉTODO UTILIZADO PARA ESCRIBIR UN AFPD EN UN ARCHIVO EN CASO DE NO TENER UN ARCHIVO CON UN AUTOMATA PREVIO
            FileWriter fichero = null;
            try{
                fichero = new FileWriter(dir);
                fichero.write("#states"+"\n");
                fichero.write("q0"+"\n");
                fichero.write("q1"+"\n");
                fichero.write("q2"+"\n");
                fichero.write("q3"+"\n");
                fichero.write("q4"+"\n");
                fichero.write("#initial"+"\n");
                fichero.write("q0"+"\n");
                fichero.write("#accepting"+"\n");
                fichero.write("q4"+"\n");
                fichero.write("#tapeAlphabet"+"\n");
                fichero.write("a"+"\n");
                fichero.write("b"+"\n");
                fichero.write("#stackAlphabet"+"\n");       //AQUÍ PUEDE INGRESAR UN AFPD MANUALMENTE SI NO CUENTA PREVIAMENTE CON UNO EN ARCHIVO.
                fichero.write("A"+"\n");                    //LA OPCIÓN PARA GENERAR EL ARCHIVO SE ENCUENTRA DISPONIBLE MAS ABAJO
                fichero.write("B"+"\n");
                fichero.write("#transitions"+"\n");
                fichero.write("q0:a:$>q1:A"+"\n");
                fichero.write("q1:a:$>q1:B"+"\n");
                fichero.write("q1:b:B>q2:$"+"\n");
                fichero.write("q1:b:A>q3:$"+"\n");
                fichero.write("q2:b:B>q2:$"+"\n");
                fichero.write("q2:b:A>q3:$"+"\n");
                fichero.write("q3:b:$>q4:$"+"\n");
                fichero.write("q4:b:$>q4:$"+"\n");
                fichero.close();
            }catch(Exception ex){
                System.out.println("Mensaje de la excepción: " + ex.getMessage());     //SE CAPTURAN LAS EXCEPCIONES
            }
        }
        
        public void escribir_AFD(String dir){        //MÉTODO UTILIZADO PARA ESCRIBIR UN AFD EN UN ARCHIVO EN CASO DE NO TENER UN ARCHIVO CON UN AUTOMATA PREVIO
            FileWriter fichero = null;
            try{
                fichero = new FileWriter(dir);
                fichero.write("#states"+"\n");
                fichero.write("q0"+"\n");
                fichero.write("q1"+"\n");
                fichero.write("q2"+"\n");
                fichero.write("#initial"+"\n");
                fichero.write("q0"+"\n");                     //AQUÍ PUEDE INGRESAR UN AFPD MANUALMENTE SI NO CUENTA PREVIAMENTE CON UNO EN ARCHIVO.
                fichero.write("#accepting"+"\n");             //LA OPCIÓN PARA GENERAR EL ARCHIVO SE ENCUENTRA DISPONIBLE MAS ABAJO
                fichero.write("q2"+"\n");      
                fichero.write("#transitions"+"\n");
                fichero.write("q0:a>q1"+"\n");
                fichero.write("q1:a>q2"+"\n");
                fichero.write("q2:a>q2"+"\n");
                fichero.write("q2:b>q2"+"\n");
                fichero.close();
            }catch(Exception ex){
                System.out.println("Mensaje de la excepción: " + ex.getMessage());    //SE CAPTURAN LOS ERRORES
            }
        }
        
        public void escribir_fichero_detalles(String cadena, String dir){     //MÉTODO UTILIZADO PARA ESCRIBIR LOS DETALLES DEL PROCESAMIENTO EN UN FICHERO
            String[] partes;                                                  //SE TOMA COMO PARÁMETRO LA CADENA Y LA DIRECCIÓN DEL ARCHIVO DESTINO
            partes = cadena.split(":");
            File log = new File(dir);
            try{
                if(!log.exists()){
                    log.createNewFile();                                      //SI EL ARCHIVO NO EXISTE ENTONCES SE CREA UNO
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
    
    public class pila{              //CLASE QUE MANEJA TODO LO RELACIONADO A LA PILA
        
        public boolean modificar_pila(Stack pila, int operacion, String parametro){     //METODO QUE MODIFICA LA PILA EN BASE A LA OPERACIÓN Y EL PARÁMETRO
            boolean op_valida = false;
            switch(operacion){
                case 1:
                    //EN ESTA OPCIÓN NO SE HACE NADA EN LA PILA
                    op_valida = true;
                    break;
                case 2:
                    //EN ESTA OPCIÓN SE APILA
                    pila.push(parametro);
                    op_valida = true;
                    break;
                case 3:
                    //EN ESTA OPCIÓN SE DESAPILA
                    if(!pila.empty()){
                        pila.pop();
                        op_valida = true;
                    }else{
                        //PILA VACÍA, NO SE PUEDE DESAPILAR MAS
                        op_valida = false;
                    }
                    break;
                case 4:
                    //EN ESTA OPCIÓN SE INTERCAMBIA EL TOPE
                    if(!pila.empty()){
                        pila.pop();
                        pila.push(parametro);
                        op_valida = true;
                    }else{
                        //PILA VACÍA, NO SE PUEDE DESAPILAR MAS
                        op_valida = false;
                    }
                    break;
            }
            return op_valida;     //SE RETORNA TRUE SI LA OPCIÓN DE PILA SE PUDO EJECUTAR, EN CASO DE ERROR RETORNA FALSE
        }
        
        public int operacion_pila(String estado_i_pila, String estado_f_pila){    //CON ESTE METODO SE SELECCIONA LA OPERACIÓN A REALIZAR EN BASE A LOS PARAMETROS DE LA TRANSICIÓN
            if(estado_i_pila.equals("$") && estado_f_pila.equals("$")){
                return 1;
                //NO HACER NADA
            }
            if(estado_i_pila.equals("$") && !estado_f_pila.equals("$")){
                return 2;
                //APILAR
            }
            if(!estado_i_pila.equals("$") && estado_f_pila.equals("$")){
                return 3;
                //DESAPILAR
            }
            if(!estado_i_pila.equals("$") && !estado_f_pila.equals("$")){
                return 4;
                //INTERCAMBIAR TOPE
            }
            return 1;
        }
    }
    
    public class procesamiento{     //CLASE ENCARGADA DEL PROCESAMIENTO
        
        public boolean procesar_cadena(String cadena){    //MÉTODO ENCARGADO DE PROCESAR LA CADENA
            String estado_actual;
            if(cadena.contains("$")){     //VERIFICA SI ES UNA CADENA QUE CONTIENE LAMBDA ($)
                cadena = "";
                System.out.println("Cadena: $ ->");
            }else{
                System.out.print("Cadena: "+cadena+" ->");
            }
            Stack pila = new Stack();                               
            pila opciones_pila = new pila();
            char letra;
            int op_pila;
            boolean cadena_valida = true;
            int instruccion_encontrada = 0;               //ESTA VARIABLE VA CONTANDO LAS INSTRUCCIONES ASOCIADAS ENCONTRADAS
            estado_actual = estado_inicial;               //LA VARIABLE ESTADO_ACTUAL SERVIRÁ COMO APUNTADOR, SE INICIALIZA CON EL ESTADO INICIAL
            for(int i=0; i<cadena.length(); i++){         //ESTE FOR LEE LETRA POR LETRA DE LA CADENA
                letra = cadena.charAt(i);
                if(instruccion_encontrada==i){            //PREGUNTA SI EL NÚMERO DE INSTRUCCIONES VA IGUAL A i, ESTO CON EL FIN DE SABER SI TODAS LAS INSTRUCCIONES SE ENCONTRARON O SI SE CAYÓ EN UN ESTADO LIMBO
                    for(int j=0; j<funcion_transicion.size(); j++){                //CON ESTE FOR SE VAN EVALUANDO CADA UNA DE LAS INSTRUCCIONES DE TRANSICION PARA ENCONTRAR LA ASOCIADA A LA LETRA EN ESE MOMENTO 
                        if(estado_actual.equals(matriz_transicion[0][j]) && matriz_transicion[1][j].equals(Character.toString(letra))){   //SE PREGUNTA SI EL ESTADO ACTUAL Y LA LETRA DE LA TRANSICIÓN COINCIDE
                            op_pila = opciones_pila.operacion_pila(matriz_transicion[2][j], matriz_transicion[4][j]);      //SE ENCONTRÓ UNA INSTRUCCIÓN DE TRANSICIÓN ASOCIADA, ENTONCES SE SOLICITA LA OPCIÓN DE PILA
                            if(op_pila==3 && !pila.empty()){       //SI LA OPCIÓN DE PILA ES DESAPILAR Y LA PILA NO ESTÁ VACÍA ENTONCES SE PREGUNTA CUAL ES EL TOPE EN ESE MOMENTO PARA SABER SI DESAPILAR FINALMENTE
                                if(matriz_transicion[2][j].equals(pila.peek())){
                                   if(opciones_pila.modificar_pila(pila, op_pila, matriz_transicion[4][j])){     //SI EL TOPE DE LA PILA COINCIDE CON LO QUE SE QUIERE DESAPILAR ENTONCES SE MANDA A DESAPILAR
                                        instruccion_encontrada += 1;                   //SE INDICA QUE SE ENCONTRÓ LA INSTRUCCIÓN
                                        estado_actual = matriz_transicion[3][j];       //SE MUEVE EL ESTADO ACTUAL AL SIGUIENTE ESTADO
                                        break;                                         //SE DETIENE EL CICLO YA QUE NO ES NECESARIO BUSCAR EN MAS INSTRUCCIONES
                                    }else{
                                        cadena_valida = false;                         //SI NO SE ENCUENTRA LA INSTRUCCIÓN ENTONCES SE CAYÓ EN UN ESTADO LIMBO Y LA CADENA SE RECHAZA
                                        break;                                         //SE DETIENE EL CICLO DEBIDO A LO ANTERIOR
                                    } 
                                }
                            }else{                                    //SI LA OPCIÓN DE PILA ES DIFERENTE A DESAPILAR ENTONCES SE OPERA CON NORMALIDAD
                                if(opciones_pila.modificar_pila(pila, op_pila, matriz_transicion[4][j])){       //SE MANDA A LA PILA LA SOLICITUD, SEA APILAR, NO HACER NADA O INTERCAMBIAR EL TOPE
                                    instruccion_encontrada += 1;            //SE INDICA QUE LA INSTRUCCIÓN SE ENCONTRÓ
                                    estado_actual = matriz_transicion[3][j];      //SE MUEVE EL ESTADO ACTUAL AL SIGUIENTE ESTADO
                                    break;                                        //SE DETIENE EL PROCESAMIENTO PORQUE YA SE ENCONTRÓ LA INSTRUCCIÓN
                                }else{
                                    cadena_valida = false;              //SI NO SE ENCONTRÓ ALGUNA INSTRUCCIÓN ENTONCES QUIERE DECIR QUE SE CAYÓ A UN ESTADO LIMBO
                                    break;   
                                }
                            }
                        }
                        if(estado_actual.equals(matriz_transicion[0][j]) && matriz_transicion[1][j].equals("$")){       //ESTE IF EVALUA LAS TRANSICIONES LAMBDA EXCLUSIVAMENTE
                            i--;                                 //COMO EN LAS TRANSICIONES LAMBDA LA LETRA NO ES CONSUMIDA ENTONCES POR ESTO SE RESTA 1 AL CICLO, ESTO PARA QUE NO PASE A LA SIGUIENTE LETRA EN LA SIGUIENTE ITERACIÓN
                            op_pila = opciones_pila.operacion_pila(matriz_transicion[2][j], matriz_transicion[4][j]);      //SE SOLICITA LA OPCIÓN DE PILA EN BASE A LA TRANSICIÓN LEIDA
                            if(op_pila==3 && !pila.empty()){                          //EL FUNCIONAMIENTO AQUÍ HACIA ABAJO ES EL MISMO QUE CUANDO SEA LEE UNA TRANSICIÓN QUE NO ES LAMBDA, EXCEPTUANDO QUE NO SE SUMARA A LA VARIABLE INSTRUCCIÓN ENCONTRADA.
                                if(matriz_transicion[2][j].equals(pila.peek())){
                                   if(opciones_pila.modificar_pila(pila, op_pila, matriz_transicion[4][j])){
                                        estado_actual = matriz_transicion[3][j];
                                        break;
                                    }else{
                                        cadena_valida = false;
                                        break;   
                                    } 
                                }
                            }else{
                                if(opciones_pila.modificar_pila(pila, op_pila, matriz_transicion[4][j])){
                                    estado_actual = matriz_transicion[3][j];
                                    break;
                                }else{
                                    cadena_valida = false;
                                    break;   
                                }
                            }
                        }
                    }
                }           //SI ALGUNA INSTRUCCIÓN NO SE ENCONTRÓ ENTONCES SALTA HASTA AQUI, ESTO QUIERE DECIR QUE SE CAYÓ EN UN ESTADO LIMBO
            }
            if(cadena_valida){        //ESTE IF DEFINIRÁ SI LA CADENA DEBE SER ACEPTADA O RECHAZADA
                if(estados_aceptacion.contains(estado_actual) && pila.empty() && instruccion_encontrada==cadena.length()){ 
                    cadena_valida = true;   //SI EL ESTADO ACTUAL CORRESPONDE CON UNO DE ACEPTACIÓN, LA PILA ES VACIA Y TODAS LAS INSTRUCCIONES FUERON ENCONTRADAS ENTONCES LA CADENA ES ACEPTADA
                }else{
                    cadena_valida = false;  //DE LO CONTRARIO SE RECHAZA
                }   
            }
            return cadena_valida;     //SE RETORNA EL VALOR DE VERDAD
        }
        
        public boolean procesar_cadena_detalles(String cadena, String nombre_fichero){       //ESTE MÉTODO PROCESA LA CADENA Y PROPORCIONA DETALLES DEL PROCESAMIENTO, TIENE EL MISMO FUNCIONAMIENTO LÓGICO QUE EL ANTERIOR MÉTODO
            String estado_actual, cadena_para_escribir;      //LA VARIABLE CADENA_PARA_ESCRIBIR VA ALMACENANDO LOS DETALLES PARA LUEGO SER ESCRITOS EN UN ARCHIVO
            if(cadena.contains("$")){
                cadena = "";
                System.out.print("("+estado_inicial+",$,$)->");
                cadena_para_escribir = "$:"+"("+estado_inicial+",$,$)->";            //SE PROPORCIONAN LOS DETALLES INICIALES
            }else{
                System.out.print("("+estado_inicial+","+cadena+",$)->");
                cadena_para_escribir = cadena+":"+"("+estado_inicial+","+cadena+",$)->";
            }
            Stack pila = new Stack();                                 
            pila opciones_pila = new pila();
            ficheros opciones_ficheros = new ficheros();
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
                            op_pila = opciones_pila.operacion_pila(matriz_transicion[2][j], matriz_transicion[4][j]);
                            if(op_pila==3 && !pila.empty()){
                                if(matriz_transicion[2][j].equals(pila.peek())){
                                   if(opciones_pila.modificar_pila(pila, op_pila, matriz_transicion[4][j])){
                                        instruccion_encontrada += 1;
                                        estado_actual = matriz_transicion[3][j];
                                        break;
                                    }else{
                                        cadena_valida = false;
                                        break;   
                                    } 
                                }
                            }else{
                                if(opciones_pila.modificar_pila(pila, op_pila, matriz_transicion[4][j])){
                                    instruccion_encontrada += 1;
                                    estado_actual = matriz_transicion[3][j];
                                    break;
                                }else{
                                    cadena_valida = false;
                                    break;   
                                }
                            }
                        }
                        if(estado_actual.equals(matriz_transicion[0][j]) && matriz_transicion[1][j].equals("$")){
                            i--;
                            op_pila = opciones_pila.operacion_pila(matriz_transicion[2][j], matriz_transicion[4][j]);
                            if(op_pila==3 && !pila.empty()){
                                if(matriz_transicion[2][j].equals(pila.peek())){
                                   if(opciones_pila.modificar_pila(pila, op_pila, matriz_transicion[4][j])){
                                        estado_actual = matriz_transicion[3][j];
                                        break;
                                    }else{
                                        cadena_valida = false;
                                        break;   
                                    } 
                                }
                            }else{
                                if(opciones_pila.modificar_pila(pila, op_pila, matriz_transicion[4][j])){
                                    estado_actual = matriz_transicion[3][j];
                                    break;
                                }else{
                                    cadena_valida = false;
                                    break;   
                                }
                            }
                        }
                    }
                }
                if(pila.empty()){
                    if(cadena.substring(i+1).equals("")){
                        System.out.print("("+estado_actual+",$,$)->");   
                        cadena_para_escribir = cadena_para_escribir.concat("("+estado_actual+",$,$)->");      //SE PROPORCIONAN LOS DETALLES
                    }else{
                        System.out.print("("+estado_actual+","+cadena.substring(i+1)+",$)->"); 
                        cadena_para_escribir = cadena_para_escribir.concat("("+estado_actual+","+cadena.substring(i+1)+",$)->");
                    } 
                }else{
                    if(cadena.substring(i+1).equals("")){
                        System.out.print("("+estado_actual+",$,"+pila.toString()+")->");
                        cadena_para_escribir = cadena_para_escribir.concat("("+estado_actual+",$,"+pila.toString()+")->");
                    }else{
                        System.out.print("("+estado_actual+","+cadena.substring(i+1)+","+pila.toString()+")->"); 
                        cadena_para_escribir = cadena_para_escribir.concat("("+estado_actual+","+cadena.substring(i+1)+","+pila.toString()+")->");
                    }
                }   
            }
            if(cadena_valida){      //SE DECIDE SI LA CADENA SERÁ ACEPTADA O RECHAZADA
                if(estados_aceptacion.contains(estado_actual) && pila.empty() && instruccion_encontrada==cadena.length()){
                    cadena_valida = true;
                }else{
                    cadena_valida = false;
                }   
            }
            if(cadena_valida){     //CON ESTE IF SIMPLEMENTE SE AGREGA "YES" O "NO"
                cadena_para_escribir = cadena_para_escribir.concat(":YES");
            }else{
                cadena_para_escribir = cadena_para_escribir.concat(":NO");
            }
            opciones_ficheros.escribir_fichero_detalles(cadena_para_escribir, nombre_fichero);    //SE LLAMA EL MÉTODO QUE ESCRIBE ESTOS DETALLES EN UN ARCHIVO, SE LE INDICA LA CADENA Y LA DIRECCIÓN DEL ARCHIVO
            return cadena_valida;    //SE RETORNA EL VALOR DE VERDAD
        }   
    }
    
    public class validaciones{         //CLASE DEDICADA A VALIDAR
        
        public boolean validacion_1(String cadena){         //ESTE MÉTODO VALIDA QUE TODAS LAS LETRAS DE LA CADENA A PROCESAR PERTENEZCAN AL ALFABETO DE LA CINTA
            boolean validar = true;
            if("$".contains(Character.toString(cadena.charAt(0)))){    //COMO "$" NO PERTENECE AL ALFABETO PERO REPRESENTA LAMBDA ENTONCES SE HACE UN TRATAMIENTO ESPECIAL
                
            }else{
                for(int i=0; i<cadena.length(); i++){         //SE RECORRE TODA LA CADENA LETRA POR LETRA PARA VERIFICAR LA PERTINENCIA
                    if(!(alfa_cinta.contains(Character.toString(cadena.charAt(i))))){    //SI SE DECTECTA UNA LETRA QUE NO ES DEL ALFABETO ENTONCES DE INFORMA EL FALLO, EXCEPTUANDO "$"
                        validar = false;
                    }
                }
            }
            return validar;
        }
    }
    
    public class cadenas{         //CLASE ENCARGADA DE MANEJAR TODO LO RELACIONADO A CADENAS
        validaciones opciones_validar = new validaciones();
        
        public void cargar_cadenas(boolean detalles){     //ESTE MÉTODO CARGA TODAS LAS CADENAS QUE SE DESEEN PROCESAR A UNA LISTA
            ArrayList<String> lista_cadenas = new ArrayList();
            Scanner scanner = new Scanner(System.in);
            int num_entradas;
            String cadena;
            String nombre_fichero = "C:\\Users\\Magnert\\Documents\\NetBeansProjects\\FinalProjectIntro\\fichero_salida.txt";    //AQUÍ SE DEBE PONER LA DIRECCIÓN DEL ARCHIVO DE SALIDA DE DETALLES
            File f = new File(nombre_fichero);
            f.delete();        //SE BORRA EL ARCHIVO PREVIAMENTE, ESTO CON EL FIN DE TENER EL ARCHIVO EN LIMPIO A LA HORA DE USARLO
            System.out.println("Ingrese la cantidad de cadenas que desea procesar:");     //SE SOLICITA LA CANTIDAD DE CADENAS A PROCESAR
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
            if(detalles){     //CON ESTE IF SE DECIDE CUAL DE LOS DOS MÉTODOS USAR PARA EL PROCESAMIENTO, PUEDE SER SALIDA CON DETALLES O SIN DETALLES
                procesarListaCadenas(lista_cadenas, nombre_fichero, true);
            }else{
                procesarListaCadenas(lista_cadenas, nombre_fichero, false);   
            }
        }
        
        public void procesarListaCadenas(ArrayList<String> lista_c, String nombre_fichero, Boolean imprimirDetalles){      //ESTE MÉTODO MANDA A PROCESAMIENTO CADA CADENA DE LA LISTA
            procesamiento proceso = new procesamiento();
            boolean respuesta;
            if(imprimirDetalles){     //CON ESTE IF SE DECIDE SI IMPRIMIR LOS DETALLES O NO
                for(int i=0; i<lista_c.size(); i++){
                    respuesta = proceso.procesar_cadena_detalles(lista_c.get(i), nombre_fichero);    //SE ENVIA LA CADENA A PROCESAMIENTO CON DETALLES
                    if(respuesta){
                        System.out.println("Accepted"); 
                    }else{
                        System.out.println("Rejected"); 
                    }
                }
            }else{ 
                for(int i=0; i<lista_c.size(); i++){
                    respuesta = proceso.procesar_cadena(lista_c.get(i));     //SE ENVIA LA CADENA A PROCESAMIENTO SIN DETALLES
                    if(respuesta){
                        System.out.println("Accepted"); 
                    }else{
                        System.out.println("Rejected"); 
                    }
                }
            }
        }
    }
    
    public class producto_cartesiano{
        
        ArrayList<String> conjunto_estados_producto = new ArrayList();
        String estado_inicial_producto = "";
        ArrayList<String> estados_aceptacion_producto = new ArrayList();       //VARIABLES QUE GUARDARAN LOS ATRIBUTOS DEL AUTOMATA PRODUCTO
        ArrayList<String> alfa_cinta_producto = alfa_cinta;
        ArrayList<String> alfa_pila_producto = alfa_pila;
        ArrayList<String> funcion_transicion_producto = new ArrayList();
        
        public void cargar_AFD(String dir){    //MÉTODO ENCARGADO DE CARGAR EL AFD Y DEJARLO DISPONIBLE PARA PROCESAR
            boolean lectura_correcta;
            ficheros opciones_ficheros = new ficheros();
            lectura_correcta = opciones_ficheros.leer_AFD(dir);     //SE MANDA A LEER EL ARCHIVO DONDE ESTÁ EL AFD
            if(lectura_correcta){
                matriz_transicion_AFD = new String[3][funcion_transicion_AFD.size()];  
                this.cargar_matriz_AFD();      //SE CARGA LA MATRIZ DE TRANSICIÓN CON LAS TRANSICIONES DEL AFD
                this.hallar_producto_cartesiano();     //POSTERIORMENTE SE HALLA EL PRODUCTO CARTESIANO
            }
        }
        public void cargar_matriz_AFD(){
            String instruccion;
            String[] partes;                 //SE CARGAN LOS ATRIBUTOS DE LAS TRASICIONES DEL AFD EN UNA MATRIZ
            for(int i=0; i<funcion_transicion_AFD.size(); i++){
                 instruccion = funcion_transicion_AFD.get(i);       //SE SEPARAN LAS PARTES DE LA INSTRUCCIÓN DE TRANSICIÓN Y SE VAN GUARDANDO POR COLUMNAS.
                 partes = instruccion.split(":");               //CADA FILA ES UNA INSTRUCCIÓN DE TRANSICIÓN
                 matriz_transicion_AFD[0][i] = partes[0];           //COLUMNA 0 -> ESTADO ORIGEN
                 matriz_transicion_AFD[1][i] = partes[1];           //COLUMNA 1 -> LETRA DEL ALFABETO DE CINTA CON LA QUE FUNCIONA LA TRANSICIÓN
                 matriz_transicion_AFD[2][i] = partes[2];           //COLUMNA 2 -> ESTADO FINAL 
            }
        }
        public void hallar_producto_cartesiano(){     //CON ESTE MÉTODO SE HALLA EL PRODUCTO CARTESIANO ENTRE LOS DOS AUTOMATAS
            for(int i=0; i<conjunto_estados.size(); i++){    //PRIMERO SE IDENTIFICAN LOS NUEVOS ESTADOS, ESO SE LOGRA RECORRIENDO UNO POR UNO Y ASOCIANDOLO CON LOS DEL OTRO AUTOMATA
                for(int j=0; j<conjunto_estados_AFD.size(); j++){
                    if(i==0 && j==0){    //EL PRIMER ESTADO GENERADO SERÁ EL DE INICIO
                        estado_inicial_producto = conjunto_estados.get(i)+conjunto_estados_AFD.get(j)+"'";
                    }
                    conjunto_estados_producto.add(conjunto_estados.get(i)+conjunto_estados_AFD.get(j)+"'");     //SE VAN AGREGANDO LOS NUEVOS ESTADOS AL CONJUNTO DE ESTADOS DEL AUTOMATA PRODUCTO
                    if(estados_aceptacion.contains(conjunto_estados.get(i)) && estados_aceptacion_AFD.contains(conjunto_estados_AFD.get(j))){   //SI AMBOS ESTADOS ASOCIADOS A LA CREACIÓN DE UN NUEVO ESTADO SON DE ACEPTACIÓN ENTONCES EL NUEVO ESTADO TAMBIEN LO SERÁ
                        estados_aceptacion_producto.add(conjunto_estados.get(i)+conjunto_estados_AFD.get(j)+"'");   //SE AGREGA ESE ESTADO AL CONJUNTO DE ESTADOS DE ACEPTACIÓN
                    }
                }
            }
            for(int i=0; i<funcion_transicion.size(); i++){     //DESPUES DE IDENTIFICAR LOS NUEVOS ESTADOS JUNTO CON LOS DE ACEPTACIÓN ENTONCES SE PASA A ANALIZAR LAS TRANSICIONES
                if(matriz_transicion[1][i].equals("$")){        //SI LA TRANSICION ES LAMBDA EN EL AFPD ENTONCES SE LE DA UN TRATAMIENTO ESPECIAL
                        for(int k=0; k<conjunto_estados_AFD.size(); k++){      //CON ESTE FOR SE DEJAN LOS ESTADOS DEL AFD FIJOS EN CADA INSTRUCCIÓN LUEGO SE PASA AL SIGUIENTE Y ASI
                           funcion_transicion_producto.add(matriz_transicion[0][i]+matriz_transicion_AFD[0][k]+"':$:"+matriz_transicion[2][i]+">"+matriz_transicion[3][i]+matriz_transicion_AFD[2][k]+"':"+matriz_transicion[4][i]); 
                        }
                    }
                for(int j=0; j<funcion_transicion_AFD.size(); j++){      //CON ESTE FOR SE ASOCIAN LAS DEMAS TRANSICIONES QUE NO SON LAMBDA
                    if(matriz_transicion[1][i].equals(matriz_transicion_AFD[1][j])){     //SE VAN AGREGANDO LAS NUEVAS TRANSICIONES A LA NUEVA FUNCIÓN DE TRANSICIÓN
                        funcion_transicion_producto.add(matriz_transicion[0][i]+matriz_transicion_AFD[0][j]+"':"+matriz_transicion[1][i]+":"+matriz_transicion[2][i]+">"+matriz_transicion[3][i]+matriz_transicion_AFD[2][j]+"':"+matriz_transicion[4][i]);
                    }             
                }
            }
            this.imprimir_producto_cartesiano();     //LUEGO DE TENER EL PRODUCTO CARTESIANO ENTONCES SE MANDA A IMPRIMIR
        }
        
        public void imprimir_producto_cartesiano(){    //ESTE MÉTODO IMPRIME EL PRODUCTO CARTESIANO
            System.out.println("--------AUTOMATA GENERADO--------");
            System.out.println("#states");
            for(int i=0; i<conjunto_estados_producto.size(); i++){
                System.out.println(conjunto_estados_producto.get(i));
            }
            System.out.println("#initial");
            System.out.println(estado_inicial_producto);
            System.out.println("#accepting");
            for(int i=0; i<estados_aceptacion_producto.size(); i++){
                System.out.println(estados_aceptacion_producto.get(i));
            }
            System.out.println("#tapeAlphabet");
            for(int i=0; i<alfa_cinta_producto.size(); i++){
                System.out.println(alfa_cinta_producto.get(i));
            }
            System.out.println("#stackAlphabet");
            for(int i=0; i<alfa_pila_producto.size(); i++){
                System.out.println(alfa_pila_producto.get(i));
            }
            System.out.println("#transition");
            for(int i=0; i<funcion_transicion_producto.size(); i++){
                System.out.println(funcion_transicion_producto.get(i));
            }
        }
    }
       
    public class pruebas{     //ESTA CLASE ES ENFOCADA A PRUEBAS
        
        public void iniciar_automata(String direccion_AFPD, String direccion_AFD, boolean manual, boolean detalles, boolean producto_cartesiano){
            ficheros ficheros = new ficheros();
            cadenas manejo_cadenas = new cadenas();                 //SE CREAN LOS OBJETOS 
            producto_cartesiano opciones_cartesiano = new producto_cartesiano();
            //ficheros.escribir_automata(direccion_AFPD);                           //SE ESCRIBE EL AUTOMATA QUE SE DESEA UTILIZAR EN UN ARCHIVO (OPCIONAL) SI YA SE TIENE UNO ENTONCES ESTA LINEA DE CODIGO SE PUEDE OMITIR
            //ficheros.escribir_AFD(direccion_AFD);
            boolean  lectura_correcta;                              
            lectura_correcta = ficheros.leer_archivo(direccion_AFPD);             //SE LEEN LOS ATRIBUTOS DEL AUTOMATA DEL ARCHIVO DADO Y SI TODO SE LEE CORRECTAMENTE ENTONCES SE RETORNA TRUE
            if(lectura_correcta){                                   //SI TODOS LOS DATOS LEIDOS SON CORRECTOS ENTONCES SE SIGUE CON EL PROCESO
                matriz_transicion = new String[5][funcion_transicion.size()];    //SE INICIALIZA LA MATRIZ DE TRANSICIÓN
                generar_matriz();                                                //SE GENERA UNA MATRIZ CON LAS TRANSICIONES DEL AUTOMATA
                manejo_cadenas.cargar_cadenas(detalles);                                 //SE CARGAN LAS CADENAS POR CONSOLA
                if(producto_cartesiano){                               //SI SE REQUIERE PRODUCTO CARTESIANO ENTONCES SE CALCULA, SINO EL PROGRAMA FINALIZA
                    opciones_cartesiano.cargar_AFD(direccion_AFD);
                }else{
                    System.out.println("PROGRAMA FINALIZADO, GRACIAS");
                }
            }
        }
        
        //AUTOMATAS EJEMPLO
        //AUTOMATA AFPD 1 -> (a)**n(b)**n
        //AUTOMATA AFPD 2 -> (a)**2n(b)**n
        //AUTOMATA AFPD 3 -> (a)**n(b)**2n
        //AUTOMATA AFPD 4 -> (a)**m(b)**n CON n>m>=1
        //AUTOMATA AFD 1 -> #par(a) y #par(b)
        //AUTOMATA AFD 2 -> Acepta solo cadenas que inician con 2 a's
        
        
        //MODIFICAR LAS DIRECCIONES EN BASE A LA DIRECCIÓN PROPORCIONADA POR EL COMPUTADOR DONDE SE EJECUTA EL PROGRAMA, ESTAS DIRECCIONES SON EN BASE A EL COMPUTADOR DE MAGNERT VALBUENA
        //LAS PRIMERAS 4 PRUEBAS SON AUTOMÁTICAS, SOLAMENTE ES NECESARIO INGRESAS LAS CADENAS A PROBAR
        //LA PRUEBA 5 ES MANUAL, ES A DISPOSICIÓN DEL USUARIO
        //LA PRUEBA 6 ES SOLAMENTE PARA EL MÉTODO TO_STRING, NO HACE NADA MAS QUE IMPRIMIR EL AUTOMATA PARA VISUALIZARLO MEJOR
        //NOTA: ESTÁS PRUEBAS SE HABILITAN O SE DESHABILITAN EN EL MÉTODO INICIALIZAR QUE ESTÁ AL INCICIO DEL CÓDIGO, SOLO SELECCIONAR UNA PRUEBA POR EJECUCIÓN DE PROGRAMA PARA UN CORRECTO FUNCIONAMIENTO
        public void prueba_1(){   //Primera prueba, procesamiento con detalles, y producto cartesiano
            String direccion_AFPD = "C:\\Users\\Magnert\\Documents\\NetBeansProjects\\FinalProjectIntro\\automata_AFPD_1.dpda";  //AUTOMATA AFPD 1
            String direccion_AFD = "C:\\Users\\Magnert\\Documents\\NetBeansProjects\\FinalProjectIntro\\automta_AFD_1.dfa";   //AUTOMATA AFPD 1
            this.iniciar_automata(direccion_AFPD, direccion_AFD, false, true, true);
        }
        
        public void prueba_2(){  //Segunda prueba, procesamiento sin detalles y producto cartesiano
            String direccion_AFPD = "C:\\Users\\Magnert\\Documents\\NetBeansProjects\\FinalProjectIntro\\automata_AFPD_3.dpda";   //AUTOMATA AFPD 1
            String direccion_AFD = "C:\\Users\\Magnert\\Documents\\NetBeansProjects\\FinalProjectIntro\\automta_AFD_2.dfa";   //AUTOMATA AFPD 1
            this.iniciar_automata(direccion_AFPD, direccion_AFD, false, false, true);
        }
        
        public void prueba_3(){  //Tercera prueba, procesamiento sin detalles y sin producto cartesiano
            String direccion_AFPD = "C:\\Users\\Magnert\\Documents\\NetBeansProjects\\FinalProjectIntro\\automata_AFPD_2.dpda";  //AUTOMATA AFPD 1
            String direccion_AFD = "C:\\Users\\Magnert\\Documents\\NetBeansProjects\\FinalProjectIntro\\automta_AFD_2.dfa";   //AUTOMATA AFPD 1
            this.iniciar_automata(direccion_AFPD, direccion_AFD, false, false, false);
        }
        
        public void prueba_4(){  //Cuarta prueba, procesamiento con detalles y sin producto cartesiano
            String direccion_AFPD = "C:\\Users\\Magnert\\Documents\\NetBeansProjects\\FinalProjectIntro\\automata_AFPD_4.dpda";  //AUTOMATA AFPD 1
            String direccion_AFD = "C:\\Users\\Magnert\\Documents\\NetBeansProjects\\FinalProjectIntro\\automta_AFD_2.dfa";   //AUTOMATA AFPD 1
            this.iniciar_automata(direccion_AFPD, direccion_AFD, false, true, false);
        }
        
        public void prueba_5(){  //Quinta prueba, procesamiento a elección
            boolean detalles = false;
            boolean producto_cartesiano = false;
            int op_cartesiano, op_detalles;
            String direccion_AFPD = "C:\\Users\\Magnert\\Documents\\NetBeansProjects\\FinalProjectIntro\\automata_AFPD_4.dpda";
            String direccion_AFD = "C:\\Users\\Magnert\\Documents\\NetBeansProjects\\FinalProjectIntro\\automta_AFD_2.dfa";
            System.out.println("Desea imprimir los detalles del siguiente procesamiento en pantalla? Digite un número -> 1-Si/2-No");
            op_detalles = scanner.nextInt();
            if(op_detalles==1){
                detalles = true;
            }else{
                detalles = false;
            }
            System.out.println("Desea obtener el producto cartesiano del AFPD con un AFD? Digite un número 1-Si/2-No");
            op_cartesiano = scanner.nextInt();
            if(op_cartesiano==1){
                producto_cartesiano = true;
            }else{
                producto_cartesiano = false;
            }
            this.iniciar_automata(direccion_AFPD, direccion_AFD, true, detalles, producto_cartesiano);
        }
        
        public void prueba_6(){   //Prueba del método to_string
            String cadena_larga;
            String[] partes;
            ficheros op_ficheros = new ficheros();
            cadena_larga = op_ficheros.to_String("C:\\Users\\Magnert\\Documents\\NetBeansProjects\\FinalProjectIntro\\automata_AFPD_1.dpda");
            partes = cadena_larga.split(";");
            for(int i=0; i<partes.length; i++){
                System.out.println(partes[i]);
            }
        }
    }
}
