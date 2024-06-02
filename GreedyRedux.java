import Entity.ProcesadorRedux;
import Entity.Tarea;

import java.util.ArrayList;
import java.util.List;

public class GreedyRedux implements SolucionadorAbstracto {
    List<ProcesadorRedux> procesadores;
    List<Tarea> tareas;
    private boolean sinSolucion;
    private int countCandidatos;

    public GreedyRedux(List<ProcesadorRedux> procesadores, List<Tarea> tareas) {
        this.procesadores = procesadores;
        this.tareas = tareas;
    }

    private boolean agregarTarea(Tarea t, ProcesadorRedux p) {
        countCandidatos++;
        return p.agregarTarea(t);
    }

    //complejidad O(n*m) siendo n la cantidad de tareas y m la cantidad de procesadores
    public boolean greedy(int tiempo) {
        if (noHaySolucion())
            return false;

        countCandidatos = 0;
        ProcesadorRedux.setTiempo(tiempo);

        while (!tareas.isEmpty()) {
            Tarea t = tareas.removeFirst();

            int indiceProcesador = getIndiceProcesadorMenosCargado();
            ProcesadorRedux p = procesadores.get(indiceProcesador);

            if (!agregarTarea(t, p))
                sinSolucion = seleccionar(indiceProcesador, t);
        }

        return !sinSolucion;
    }

    private int getIndiceProcesadorMenosCargado() {
        int tiempoMenor = Integer.MAX_VALUE;
        int indiceProcesador = 0;

        for (int i = 0; i < procesadores.size(); i++) {
            if (procesadores.get(i).getTiempoEjecucionProcesador() < tiempoMenor) {
                tiempoMenor = procesadores.get(i).getTiempoEjecucionProcesador();
                indiceProcesador = i;
            }
        }

        return indiceProcesador;
    }

    private int getTiempoFinal() {
        int tiempoMayor = 0;

        for (ProcesadorRedux p : procesadores) {
            int tiempoProcesador = p.getTiempoEjecucionProcesador();

            if (tiempoProcesador > tiempoMayor)
                tiempoMayor = tiempoProcesador;
        }

        return tiempoMayor;
    }

    private boolean seleccionar(int indiceProcesador, Tarea t) {
        //iterar por el resto de los procesadores
            //si en ninguna instancia se pudo agregar,
                //retornar false
            //si se pudo
                //retornar true

        int size = procesadores.size();
        for (int i = 0; i < size; i++) {
            int currentIndex = (indiceProcesador + i) % size;
            ProcesadorRedux procesadorActual = procesadores.get(currentIndex);

            if (agregarTarea(t, procesadorActual))
                return false;
        }

        return true;
    }

    public int getCountCandidatos(){
        return this.countCandidatos;
    }

    @Override
    public boolean noHaySolucion() {
        return (Tarea.getCountCriticas() / 2) > procesadores.size();
    }

    public ArrayList<ProcesadorRedux> getSolucion() {
        return new ArrayList<>(procesadores);
    }

    @Override
    public String toString() {
        if (sinSolucion)
            return "No hay solucion";

        return "Greedy" +
                procesadores +
                "\nTiempo Final = " + getTiempoFinal();
    }
}