
package MT;
import java.util.*;
import java.io.*;
/**
 *
 * @author Hawknavi
 */
public class MT_Estandar {
    public MT_Estandar(ArrayList<String> estados, String estadoInicial, ArrayList<String> estadosAceptacion, ArrayList<String> alfabetoEntrada,ArrayList<String> alfabetoCinta, ArrayList<String> delta)
    {
        this.alfabetoEntrada = alfabetoEntrada; 
        this.alfabetoCinta = alfabetoCinta;
        this.estados = estados;
        this.estadoInicial = estadoInicial;
        this.estadosAceptacion = estadosAceptacion;
        this.delta = delta;

    }
    
    private ArrayList<String> alfabetoEntrada;
    private ArrayList<String> alfabetoCinta;
    private ArrayList<String> estados;
    private ArrayList<String> estadosAceptacion;
    private String estadoInicial;
    private ArrayList<String> delta;

    public ArrayList<String> getAlfabetoEntrada() {
        return alfabetoEntrada;
    }

    public void setAlfabetoEntrada(ArrayList<String> alfabetoEntrada) {
        this.alfabetoEntrada = alfabetoEntrada;
    }

    public ArrayList<String> getAlfabetoCinta() {
        return alfabetoCinta;
    }

    public void setAlfabetoCinta(ArrayList<String> alfabetoCinta) {
        this.alfabetoCinta = alfabetoCinta;
    }

    public ArrayList<String> getEstados() {
        return estados;
    }

    public void setEstados(ArrayList<String> estados) {
        this.estados = estados;
    }

    public ArrayList<String> getEstadosAceptacion() {
        return estadosAceptacion;
    }

    public void setEstadosAceptacion(ArrayList<String> estadosAceptacion) {
        this.estadosAceptacion = estadosAceptacion;
    }

    public String getEstadoInicial() {
        return estadoInicial;
    }

    public void setEstadoInicial(String estadoInicial) {
        this.estadoInicial = estadoInicial;
    }

    public ArrayList<String> getDelta() {
        return delta;
    }

    public void setDelta(ArrayList<String> delta) {
        this.delta = delta;
    }



    
    
    
}
