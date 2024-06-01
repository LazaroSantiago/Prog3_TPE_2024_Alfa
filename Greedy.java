import Entity.Procesador;
import Entity.Tarea;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Greedy {
    private List<Procesador> procesadores;
    private List<Tarea> tareas;
    private HashMap<Procesador, Integer> tiempos;
    private HashMap<Procesador, LinkedList<Tarea>> solucion;
    private int tiempo;
    private boolean sinSolucion;

    public Greedy(List<Procesador> procesadores, List<Tarea> tareas) {
        this.procesadores = procesadores;
        this.tareas = tareas;
        this.solucion = new HashMap<>(procesadores.size());
        this.tiempos = new HashMap<>(procesadores.size());
        sinSolucion = false;
    }

    public void greedy(int tiempo) {
        //validar solucion posible
        inicializarListas(tiempo);

        while (!tareas.isEmpty()) {
            Tarea t = tareas.removeFirst();

            int indiceProcesador = getIndiceProcesadorMenosCargado();
            Procesador p = procesadores.get(indiceProcesador);

            if (validarAgregar(t, p)){
                agregarTarea(t, p);
            } else {
                deathLoop(indiceProcesador, t);
            }

            if (sinSolucion) {
                System.out.println("No hay solucion");
                return;
            }

            //todo: asignador de tareas, procesador con su lista de tareas
        }

        System.out.println(">>>>>>>>> Solucion: " + solucion);
    }

    public void inicializarListas(int tiempo) {
        this.tiempo = tiempo;
        for (Procesador p : procesadores) {
            this.solucion.put(p, new LinkedList<>());
            this.tiempos.put(p, 0);
        }
    }

    private void agregarTarea(Tarea t, Procesador p) {
        solucion.get(p).add(t);

        Integer tiempoProcesador = this.tiempos.get(p);
        tiempoProcesador += t.getTiempoEjecucion();
        this.tiempos.put(p, tiempoProcesador);
    }

    private int getIndiceProcesadorMenosCargado() {
        int tiempoMenor = Integer.MAX_VALUE;
        int indiceProcesador = 0;

        for (int i = 0; i < procesadores.size(); i++){
            if (tiempos.get(procesadores.get(i)) < tiempoMenor){
                tiempoMenor = tiempos.get(procesadores.get(i));
                indiceProcesador = i;
            }
        }

        return indiceProcesador;
    }

//    private Procesador procesadorMenosCargado() {
//        //por cada procesador
//        int tiempoMenor = Integer.MAX_VALUE;
//        Procesador result = null;
//
//        for (Procesador p : procesadores) {
//            if (tiempos.get(p) < tiempoMenor) {
//                tiempoMenor = tiempos.get(p);
//                result = p;
//            }
//        }
//
//        return result;
//    }

    private boolean validarAgregar(Tarea t, Procesador p) {
        if (!t.esCritica() && p.estaRefrigerado())
            return true;

        if (!p.estaRefrigerado())
            if (!validarRefrigerado(t, p))
                return false;

        if (t.esCritica()) {
            return validarCritica(p);
        }

        return true;
    }

    private boolean validarCritica(Procesador p) {
        LinkedList<Tarea> criticas = solucion.get(p);
        int countCriticas = 0;

        for (Tarea critica : criticas)
            if (critica.esCritica())
                countCriticas++;

        return countCriticas < 2;
    }

    private boolean validarRefrigerado(Tarea t, Procesador p) {
        //reviso tener tiempo disponible para agregar
        return ((tiempos.get(p) + t.getTiempoEjecucion()) <= tiempo);
//        return ((tiempos.get(p) + t.getTiempoEjecucion()) > tiempo);
    }

    private void deathLoop(int indiceProcesador, Tarea t) {
        //itero por el resto de los procesadores
        int size = procesadores.size();
        for (int i = 0; i < size; i++) {
            int currentIndex = (indiceProcesador + i) % size;
            Procesador procesadorActual = procesadores.get(currentIndex);

            if (validarAgregar(t, procesadorActual)) {
                agregarTarea(t, procesadorActual);
                return;
            }
        }
        //si no pude agregar en ninguna instancia, corto toda la ejecucion
        sinSolucion = true;
    }

}
