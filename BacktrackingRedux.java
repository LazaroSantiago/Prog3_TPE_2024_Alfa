import Entity.Procesador;
import Entity.ProcesadorRedux;
import Entity.Tarea;

import java.util.*;

public class BacktrackingRedux implements SolucionadorAbstracto {
    private List<ProcesadorRedux> procesadores;
    private List<Tarea> tareas;

    private ArrayList<ProcesadorRedux> solucionFinal;
    private HashSet<ProcesadorRedux> solucionActual;

    private int tiempoActual;
    private int tiempoFinal;
    private boolean sinSolucion;
    private int countEstados;


    public BacktrackingRedux(List<ProcesadorRedux> procesadores, List<Tarea> tareas) {
        this.procesadores = procesadores;
        this.tareas = tareas;
        this.solucionFinal = new ArrayList<>(procesadores.size());
        this.solucionActual = new HashSet<>(procesadores.size());
        this.tiempoFinal = Integer.MAX_VALUE;
        this.tiempoActual = 0;
    }

    public boolean backtracking(int tiempo) {
        if (noHaySolucion())
            return false;

        int countTareas = this.tareas.size();
        int countEstados = 0;

        ProcesadorRedux.setTiempo(tiempo);
        backtracking();

        sinSolucion = !verificarSolucion(countTareas);
        return sinSolucion;
    }

    private boolean verificarSolucion(int countTareas){
        //si se llego a una solucion valida,
        //la cantidad de tareas en solucion final
        //deberia ser igual a la cantidad de tareas con las que se empezo
        int countTareasAsignadas = 0;

        for (ProcesadorRedux p : solucionFinal)
            countTareasAsignadas += p.getCountTareasAsignadas();

        return countTareasAsignadas == countTareas;
    }

    private void backtracking() {
        if (tareas.isEmpty()) {
            if (solucionActualEsMejor()) {
                this.deepCopy();
            }
        } else {
            Tarea t = tareas.removeFirst();

            for (ProcesadorRedux p : procesadores) {
                solucionActual.add(p);

                if (p.agregarTarea(t)) {
                    if (solucionActualEsMejor()){
                        countEstados++;
                        backtracking();
                    }
                    p.quitarTarea();
                }
            }
            tareas.addFirst(t);
        }
    }

    private boolean solucionActualEsMejor() {
        //obtener tiempo mayor de solucion actual
        int tiempoMayor = 0;

        for (ProcesadorRedux p : solucionActual) {
            int tiempoProcesador = p.getTiempoEjecucionProcesador();

            if (tiempoProcesador > tiempoMayor)
                tiempoMayor = tiempoProcesador;
        }

        this.tiempoActual = tiempoMayor;
        return tiempoActual < tiempoFinal;
    }

    private void deepCopy() {
        this.solucionFinal.clear();
        this.solucionFinal = new ArrayList<>(solucionActual.size());
        for (ProcesadorRedux p : solucionActual) {
            this.solucionFinal.add(new ProcesadorRedux(p));
        }

        this.tiempoFinal = this.tiempoActual;
    }

    @Override
    public boolean noHaySolucion() {
        return (Tarea.getCountCriticas() / 2) > procesadores.size();
    }

    public List<ProcesadorRedux> getSolucion(){
        return new ArrayList<>(solucionFinal);
    }

    public int getTiempoFinal(){
        return this.tiempoFinal;
    }

    public int getCountEstados(){
        return this.countEstados;
    }

    @Override
    public String toString() {
        if (sinSolucion)
            return "No hay solucion";

        return "Backtracking" +
                solucionFinal +
                "\nTiempo Final = " + tiempoFinal;
    }
}
