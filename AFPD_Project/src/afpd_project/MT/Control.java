
package MT;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.util.*;
import java.io.*;
import MT.MT_Estandar;
import MT.Transiciones;
/**
 *
 * @author Hawknavi
 */

public class Control {
    
    public static void main(String[] args) throws IOException 
    { 
        MT_Estandar MT = cargarMT();
        ArrayList<Transiciones> tr = cargarTransiciones(MT);
        System.out.println("------------------------");
        System.out.println(MT.getEstados());
        System.out.println(MT.getEstadoInicial());
        System.out.println(MT.getEstadosAceptacion());
        System.out.println(MT.getAlfabetoEntrada());
        System.out.println(MT.getAlfabetoCinta());
        System.out.println(MT.getDelta());
        
        if(aceptarTransiciones(tr) && aceptarMaquina(MT) )
        {
            if(ejecutarMaquina(MT, tr))
            {
                System.out.println("Maquina apagada");
            }
            else
            {
                System.out.println("No se encontro una transicion, el lenguaje es rechazado");
            }
            
        }
    }
    public static MT_Estandar cargarMT () throws FileNotFoundException, IOException //CREA LA MAQUINA DE TURING 
    {
        ArrayList<String> estados = new ArrayList<>();  
        String estadoInicial = new String();  
        ArrayList<String> estadosAceptacion = new ArrayList<>(); 
        ArrayList<String> alfabetoEntrada= new ArrayList<>();  
        ArrayList<String> alfabetoCinta= new ArrayList<>();  
        ArrayList<String> delta= new ArrayList<>();
        String archivo= "D:/GUYAN JIJIJI/PROYECTO ITC 2020-2/ITC-2020-2/AFPD_Project/src/MT/Pruebas/Prueba2.mt"; 
        String cadena;
        FileReader f = new FileReader(archivo);
        BufferedReader b = new BufferedReader(f);
        
        while((cadena = b.readLine())!=null) 
        {
            switch(cadena)
            {   
                case "#states": {
                    System.out.println("ESTADOS: "); 
                    cadena = b.readLine();
                    while(cadena != null && cadena.equals("#endstates")== false )
                    {    
                        System.out.println(cadena); 
                        estados.add(cadena); 
                        cadena = b.readLine(); 
                    }
                    break; 
                }
                case "#initial": {
                    System.out.println("ESTADO INICIAL:"); 
                    cadena = b.readLine();
                    while(cadena != null && cadena.equals("#endinitial")== false )
                    {    
                        System.out.println(cadena); 
                        estadoInicial = cadena; 
                        cadena = b.readLine(); 
                    }
                    break; 
                }
                
                case "#accepting": {
                    System.out.println("ESTADOS DE ACEPTACIÓN: "); 
                    cadena = b.readLine();
                    while(cadena != null && cadena.equals("#endaccepting")== false)
                    {    
                        System.out.println(cadena); 
                        estadosAceptacion.add(cadena);
                        cadena = b.readLine(); 
                        
                    }
                    break; 
                }
                case "#inputAlphabet": {
                    System.out.println("ALFABETO DE ENTRADA: "); 
                    cadena = b.readLine();
                    while(cadena != null &&  cadena.equals("#endinputAlphabet")== false)
                    {    
                        System.out.println(cadena); 
                        alfabetoEntrada.add(cadena);
                        cadena = b.readLine();  
                    }
                    break; 
                 
                }
                case "#tapeAlphabet": {
                    System.out.println("ALFABETO DE CINTA: "); 
                    cadena = b.readLine();
                    while(cadena != null && cadena.equals("#endtapeAlphabet")== false)
                    {    
                        System.out.println(cadena); 
                        alfabetoCinta.add(cadena);
                        cadena = b.readLine(); 
                    }
                    break; 
                }
                case "#transitions": {
                    System.out.println("TRANSICIONES: ");
                    cadena = b.readLine();
                    while(cadena != null && cadena.equals("#endtransitions")== false)
                    {    
                        System.out.println(cadena); 
                        delta.add(cadena);
                        cadena = b.readLine();  
                    }
                    break; 
                }    
                
                default: {break;} 
            }
        }
        b.close();
        MT_Estandar MT= new MT_Estandar(estados ,estadoInicial,estadosAceptacion, alfabetoEntrada ,alfabetoCinta,delta );
        return MT; 
        
        
    }
    public static boolean aceptarMaquina (MT_Estandar MT) //MIRAR LOS PARAMETROS PARA ACEPTAR LA MAQUINA.
    {
        if(MT.getEstados().contains(MT.getEstadoInicial()))  //COMPROBAR SI EL ESTADO INICIAL HACE PARTE DEL CONJUNTO DE LOS ESTADOS 
        {
            System.out.println("El estado inicial si hace parte del conjunto de los estados");
           
        }
        else  
        {
            System.out.println("El estado inicial no hace parte del conjunto de los estados");
            return false; 
        }
        /*boolean encontrado = false; 
        for(int i=0; i<MT.getEstados().size(); i++)//COMPROBAR SI LOS ESTADOS DE ACEPTACIÓN HACEN PARTE DE LOS ESTADOS 
        {   System.out.println(i);
            System.out.println(MT.getEstados().size());
            for(int j=0; j<MT.getEstadosAceptacion().size(); j++)
            {   System.out.println(j);
                System.out.println(MT.getEstadosAceptacion().size());
                if(MT.getEstados().get(i).equals(MT.getEstadosAceptacion().get(j)))
                {
                    System.out.println(MT.getEstados().get(i));
                    System.out.println(MT.getEstadosAceptacion().get(j));
                    encontrado= true; 
                }
            }
            if(!encontrado)
            {
                System.out.println("Los estados de aceptación no hace parte del conjunto de los estados");
                return false; 
            }
            else
            {
                encontrado=false; 
            }
        }
        
        System.out.println("Los estados de aceptación si hace parte del conjunto de los estados");
                */
        return true; 
               
    }
    public static ArrayList<Transiciones> cargarTransiciones (MT_Estandar MT) //CREA EL ARRAY DE TRANSICIONES
    {       
         ArrayList<Transiciones> transiciones = new ArrayList<> (); 
         for(int i=0; i<MT.getDelta().size(); i++)
        {  
            
            String[] partes = MT.getDelta().get(i).split(":");
            String estadoActual= partes[0]; 
            String demas = partes[1]; 
            String letraSiguiente = partes[2];
            String movimiento = partes[3];
            partes = demas.split("=");
            String letraActual = partes[0];
            String estadoSiguiente = partes[1];
            
            Transiciones tr = new Transiciones(estadoActual, letraActual,estadoSiguiente, letraSiguiente, movimiento );
            transiciones.add(tr); 
            
        }
         
         return transiciones; 
         
    }
    public static boolean aceptarTransiciones (ArrayList<Transiciones> transiciones) //CONFIRMA QUE LAS TRANSICIONES SON EJECUTABLES 
    {
        return true; 
    }
    public static boolean ejecutarMaquina(MT_Estandar MT, ArrayList<Transiciones> tr) //PROCESO PRINCIPAL DE LA MÁQUINA.
    {   
        String estadoActual = MT.getEstadoInicial(); 
        ArrayList<String> cinta = generarCinta();
        int indexTape =2; 
        String letraActual = cinta.get(indexTape); 
        
        while(!estoyEstadoAceptacion(estadoActual,MT.getEstadosAceptacion() ))
        {   
            int indexDelta = getIndexDelta (estadoActual, letraActual, tr);
            imprimirCinta(estadoActual, indexTape, cinta);
            escribirFichero(estadoActual, indexTape, cinta); 
            if(indexDelta == -1) //NO ENCONTRO UNA TRANSICION 
            {   escribirResultado("LENGUAJE NO ACEPTADO"); 
               return false; 
            }
            else
            {   
                System.out.println(" "); 
                cinta.set(indexTape, tr.get(indexDelta).getLetraSiguiente());
                
                estadoActual = tr.get(indexDelta).getEstadoSiguiente();
                if(tr.get(indexDelta).getMovimiento().equals(">"))
                {
                    indexTape++;
                }
                else
                {
                    if(tr.get(indexDelta).getMovimiento().equals("<"))
                    {
                        indexTape--; 
                    }
                }
                letraActual = cinta.get(indexTape); 
            }
        }
        System.out.println(cinta);
        escribirResultado("LENGUAJE ACEPTADO");
        return true; 
        
    }
    public static boolean estoyEstadoAceptacion (String estadoActual, ArrayList<String> estadosAceptacion) //MIRAR SI ESTOY EN UN ESTADO DE ACEPTACION.
    {
        if(estadosAceptacion.indexOf(estadoActual)!= -1 )
         {
            return true; 
         }
        return false; 
    }
    public static ArrayList<String> generarCinta () //GENERA EL LENGUAJE EN LA CINTA. 
    {
        Scanner sc = new Scanner(System.in);
        ArrayList<String> cinta = new ArrayList<String>();
        cinta.add("!");
        cinta.add("!");
        String consola; 
        System.out.println("Ingrese la cadena con comas");
        consola = sc.nextLine();
        String str[] = consola.split(",");
        for(int i=0; i<str.length ; i++)
        {
            cinta.add(str[i]);
        }
        cinta.add("!");
        cinta.add("!");
        
        return cinta; 
    }
    public static boolean confirmarCinta (ArrayList<String> cinta) //CONFIRMA SI LA CINTA TIENE LENGUAJE DE ENTRADA. 
    {
        return true; 
    }
    public static int getIndexDelta (String estadoActual, String letra, ArrayList<Transiciones> transiciones ) //ENCUENTRA LA TRANSICION A APLICAR
    {   
        for(int i=0; i<transiciones.size(); i++)
        {
            if(estadoActual.equals(transiciones.get(i).getEstadoActual()) && letra.equals(transiciones.get(i).getLetraActual()))
            {
                return i; 
            }
        }
        return -1; //NO ENCONTRO UNA TRANSICION 
    }
    public static void imprimirCinta(String estadoActual, int indexTape, ArrayList<String> Cinta) //IMPRIME LA CINTA EN PANTALLA 
    {
        for(int i=0; i< Cinta.size(); i++)
        {   
            if(i==indexTape)
                {   
               System.out.print("("+estadoActual+")"); 
                }
            System.out.print(Cinta.get(i)); 
        }
        System.out.print(" ---->"); 
    }
    public static void escribirFichero(String estadoActual, int indexTape, ArrayList<String> Cinta) 
    {   
        try(FileWriter fw = new FileWriter("D:/GUYAN JIJIJI/PROYECTO ITC 2020-2/ITC-2020-2/AFPD_Project/src/MT/Pruebas/Resultados.mt", true);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter out = new PrintWriter(bw))
        {   
            
         for(int i=0; i< Cinta.size(); i++)
        {   
            if(i==indexTape)
                {   
               out.print("("+estadoActual+")"); 
                }
            out.print(Cinta.get(i)); 
        }
        out.print(" ---->"); 
        out.println(" "); 
      
        } catch (IOException e) {
       
            }
    }
    public static void escribirResultado(String mensaje) 
    {   
        try(FileWriter fw = new FileWriter("D:/GUYAN JIJIJI/PROYECTO ITC 2020-2/ITC-2020-2/AFPD_Project/src/MT/Pruebas/Resultados.mt", true);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter out = new PrintWriter(bw))
        {   
        out.println(" "); 
        out.println(mensaje); 
      
        } catch (IOException e) {
       
            }
    }
}
