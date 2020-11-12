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
    
    Stack pila = new Stack();
    
    static ArrayList<Integer> conjunto_estados = new ArrayList();
    static int estado_inicial;
    static ArrayList<Integer> estados_aceptacion = new ArrayList();
    static ArrayList<String> alfa_cinta = new ArrayList();
    static ArrayList<String> alfa_pila = new ArrayList();
    static ArrayList<String> funcion_transicion = new ArrayList();
    
    public static void main(String[] args) {
        //escribir_archivo();
        inicializar inicio = new inicializar();
        inicio.leer_archivo();
    }
    
    public static class inicializar{
        
        public void inicializar(ArrayList<Integer> P_conjunto_estados, int P_estado_inicial, ArrayList<Integer> P_estados_aceptacion, ArrayList<String> P_alfa_cinta, ArrayList<String> P_alfa_pila, ArrayList<String> P_funcion_transicion){
            conjunto_estados = P_conjunto_estados;
            estado_inicial = P_estado_inicial;
            estados_aceptacion = P_estados_aceptacion;
            alfa_cinta = P_alfa_cinta;
            alfa_pila = P_alfa_pila;
            funcion_transicion = P_funcion_transicion;
        }
        
        public void leer_archivo(){
            File fichero = new File("C:\\Users\\Magnert\\Documents\\NetBeansProjects\\Proyecto---Aut-matas-Finitos-y-M-quina-de-Turing---Introducci-n-a-la-Teor-a-de-la-Computaci-n\\fichero.dpa");
            String linea;
            int counter = 0;
            boolean etiqueta;
            Scanner s = null;
            try{
                s = new Scanner(fichero);
                while(s.hasNextLine()){
                    linea = s.nextLine(); 
                    switch(linea){
                        case "#states":
                            counter += 1;
                            etiqueta = true;
                            break;
                        case "#initial":
                            counter += 1;
                            etiqueta = true;
                            break;
                        case "#accepting":
                            counter += 1;
                            etiqueta = true;
                            break;
                        case "#alphabet":
                            counter += 1;
                            etiqueta = true;
                            break;
                        case "#transitions":
                            counter += 1;
                            etiqueta = true;
                            break;
                        default:
                            etiqueta = false;
                    }
                    if(etiqueta == false){
                        switch(counter){
                            case 1:
                                conjunto_estados.add(Integer.parseInt(linea.substring(1)));
                                break;
                            case 2:
                                estado_inicial = Integer.parseInt(linea.substring(1));
                                break;
                            case 3:
                                estados_aceptacion.add(Integer.parseInt(linea.substring(1)));
                                break;
                            case 4:
                                alfa_cinta.add(linea);
                                break;
                            case 5:
                                alfa_pila.add(linea);
                                break;
                            case 6:
                                funcion_transicion.add(linea);
                        }
                    }
                }
            }catch(Exception ex){
                System.out.println("Mensaje: " + ex.getMessage());
            }finally{
                try{
                    if (s != null)
                    s.close();
                }catch(Exception ex2){
                    System.out.println("Mensaje 2: " + ex2.getMessage());
                }
            }
        }
    }
    
    public static class pila{
        public void modificar_pila(Stack pila, int operacion, String parametro){
            if(operacion==0){
                pila.pop();
            }else{
                pila.push(parametro);
            }
        }
    }
    
    public static class procesamiento{
        public boolean procesar_cadena(){
            return false;
        }
        public boolean procesar_cadena_detalles(){
            return false;
        }
    }
    
    public static void escribir_archivo(){
        FileWriter fichero = null;
        try{
            fichero = new FileWriter("C:\\Users\\Magnert\\Documents\\NetBeansProjects\\Proyecto---Aut-matas-Finitos-y-M-quina-de-Turing---Introducci-n-a-la-Teor-a-de-la-Computaci-n\\fichero.dpa");
            fichero.write("#states"+"\n");
            fichero.write("q0"+"\n");
            fichero.write("q1"+"\n");
            fichero.write("#initial"+"\n");
            fichero.write("q0"+"\n");
            fichero.write("#accepting"+"\n");
            fichero.write("q0"+"\n");
            fichero.write("q1"+"\n");
            fichero.write("#alphabet"+"\n");
            fichero.write("a"+"\n");
            fichero.write("b"+"\n");
            fichero.write("#alphabet"+"\n");
            fichero.write("A"+"\n");
            fichero.write("#transitions"+"\n");
            fichero.write("q0:a:$>q0:A"+"\n");
            fichero.write("q0:b:A>q1:$"+"\n");
            fichero.write("q1:b:A>q1:$"+"\n");
            fichero.close();
        }catch(Exception ex){
            System.out.println("Mensaje de la excepción: " + ex.getMessage());
	}
    }
    
}
