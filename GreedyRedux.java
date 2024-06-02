import Entity.ProcesadorRedux;
import Entity.Tarea;

import java.util.ArrayList;
import java.util.List;

public class GreedyRedux implements SolucionadorAbstracto {
    //todo: pensar en nombre mejor que deathloop...

    List<ProcesadorRedux> procesadores;
    List<Tarea> tareas;
    private boolean sinSolucion;

    public GreedyRedux(List<ProcesadorRedux> procesadores, List<Tarea> tareas) {
        this.procesadores = procesadores;
        this.tareas = tareas;
        sinSolucion = noHaySolucion();
    }

    public boolean greedy(int tiempo) {
        if (sinSolucion)
            return false;

        ProcesadorRedux.setTiempo(tiempo);

        while (!tareas.isEmpty()) {
            Tarea t = tareas.removeFirst();

            int indiceProcesador = getIndiceProcesadorMenosCargado();
            ProcesadorRedux p = procesadores.get(indiceProcesador);

            if (!p.agregarTarea(t))
                sinSolucion = deathLoop(indiceProcesador, t);

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
        System.out.println(indiceProcesador);
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

    private boolean deathLoop(int indiceProcesador, Tarea t) {
        //itero por el resto de los procesadores
        int size = procesadores.size();
        for (int i = 0; i < size; i++) {
            int currentIndex = (indiceProcesador + i) % size;
            ProcesadorRedux procesadorActual = procesadores.get(currentIndex);

            if (procesadorActual.agregarTarea(t)) {
                return false;
            }
        }

        return true;
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
        return "Greedy" +
                procesadores +
                "\nTiempo Final = " + getTiempoFinal();
    }
}