import Entity.Procesador;
import Entity.Tarea;
import Factory.ProcesadorFactory;
import Factory.TareaFactory;
import Helper.CSVHelper;

import java.util.ArrayList;
import java.util.List;

public class Servicios {
    List<Procesador> procesadores;
    List<Tarea> tareas;
    //y si cuando cargo las listas creo tambien los indices que necesite?
    //por ejemplo, cantidad de tareas criticas y proces no-refri
    //todo: solucion seguramente sea un hashmap de tamaño igual a la lista de proces, con una linked list de tareas
    //todo: explicar el trade-off de complejidad en casa servicio
    /*
     * Complejidad temporal:
     * O(n)
     */
    public Servicios(String pathProcesadores, String pathTareas) {
        CSVHelper<Procesador> helperProcesador = new CSVHelper<Procesador>(ProcesadorFactory.getInstance());
        CSVHelper<Tarea> helperTarea = new CSVHelper<Tarea>(TareaFactory.getInstance());

        this.procesadores = helperProcesador.crearLista(pathProcesadores);
        this.tareas = helperTarea.crearLista(pathTareas);
    }

    /*
     * Complejidad temporal:
     * O(n)
     */
    public Tarea servicio1(String ID) {
        //se puede cambiar la estructura a un hashset para que sea O(1)
        //todo: ver si combiene pasar a hashset

        for (Tarea t : tareas)
            if (t.getIdTarea().equals(ID))
                return t;

        return null;
    }

    /*
     * Complejidad temporal:
     * O(n)
     */
    public List<Tarea> servicio2(boolean esCritica) {
        List<Tarea> result = new ArrayList<>();

        for (Tarea t : tareas)
            if (t.esCritica() == esCritica)
                result.add(t);

        return result;
    }

    /*
     * Complejidad temporal del constructor:
     * O(n)
     */
    public List<Tarea> servicio3(int prioridadInferior, int
            prioridadSuperior) {
        List<Tarea> result = new ArrayList<>();

        for (Tarea t : tareas)
            if ((t.getNivelPrioridad() >= prioridadInferior) && (t.getNivelPrioridad() <= prioridadSuperior))
                result.add(t);

        return result;
    }

    public List<Procesador> getProcesadores() {
        return procesadores;
    }

    public List<Tarea> getTareas() {
        return tareas;
    }
}