
package MT;
import java.util.*;
import java.io.*;
/**
 *
 * @author Hawknavi
 */
public class Transiciones {
    public Transiciones(String estadoActual, String letraActual, String estadoSiguiente, String letraSiguiente, String movimiento)
    {
        this.estadoActual = estadoActual; 
        this.letraActual = letraActual;
        this.estadoSiguiente = estadoSiguiente;
        this.letraSiguiente = letraSiguiente;
        this.movimiento = movimiento;
       

    }
    
    private String estadoActual;
    private String letraActual;
    private String estadoSiguiente;
    private String letraSiguiente;
    private String movimiento;

    public String getEstadoActual() {
        return estadoActual;
    }

    public void setEstadoActual(String estadoActual) {
        this.estadoActual = estadoActual;
    }

    public String getLetraActual() {
        return letraActual;
    }

    public void setLetraActual(String letraActual) {
        this.letraActual = letraActual;
    }

    public String getEstadoSiguiente() {
        return estadoSiguiente;
    }

    public void setEstadoSiguiente(String estadoSiguiente) {
        this.estadoSiguiente = estadoSiguiente;
    }

    public String getLetraSiguiente() {
        return letraSiguiente;
    }

    public void setLetraSiguiente(String letraSiguiente) {
        this.letraSiguiente = letraSiguiente;
    }

    public String getMovimiento() {
        return movimiento;
    }

    public void setMovimiento(String movimiento) {
        this.movimiento = movimiento;
    }

}
