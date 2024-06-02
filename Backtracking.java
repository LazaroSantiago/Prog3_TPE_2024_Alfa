import Entity.Procesador;
import Entity.Tarea;

import java.util.*;

public class Backtracking {
    private List<Procesador> procesadores;
    private List<Tarea> tareas;

    private HashMap<Procesador, LinkedList<Tarea>> solucionActual;
    private HashMap<Procesador, LinkedList<Tarea>> solucionFinal;
    private HashMap<Procesador, Integer> tiemposSolucionActual;

    private int tiempoActual;
    private int tiempoFinal;
    private int tiempo;


    public Backtracking(List<Procesador> procesadores, List<Tarea> tareas) {
        this.procesadores = procesadores;
        this.tareas = tareas;
        this.solucionFinal = new HashMap<>(procesadores.size());
        this.solucionActual = new HashMap<>(procesadores.size());
        this.tiemposSolucionActual = new HashMap<>(procesadores.size());
        this.tiempoFinal = Integer.MAX_VALUE;
        this.tiempoActual = 0;
    }

    public void backtracking(int tiempo) {
        if (noHaySolucion())
            return;

        this.tiempo = tiempo;
        backtracking();
        System.out.println(">>>>>>>>> Solucion: " + solucionFinal);
        System.out.println("tiempo final: " + tiempoFinal);
    }

    public void backtracking() {
        if (tareas.isEmpty()) {
            if (solucionActualEsMejor())
                this.deepCopy();

        } else {
            Tarea t = tareas.removeFirst();

            for (Procesador p : procesadores) {
                //chequeo que pueda agregar tarea al procesador
                if (validarAgregar(t, p)) {
                    //agregar
                    agregarSolucion(p, t);

                    //backtrack y poda
                    if (solucionActualEsMejor())
                        backtracking();

                    //remover solucion actual
                    removerSolucion(p, t);
                }
            }

            tareas.addFirst(t);
        }
    }

    private void removerSolucion(Procesador p, Tarea t) {
        this.solucionActual.get(p).removeLast();
        disminuirIndiceTiempo(p, t);
    }

    private void agregarSolucion(Procesador p, Tarea t) {
        this.solucionActual.get(p).add(t);
        aumentarIndiceTiempo(p, t);
    }

    private void disminuirIndiceTiempo(Procesador p, Tarea t) {
        Integer tiempoProcesador = this.tiemposSolucionActual.get(p);
        tiempoProcesador -= t.getTiempoEjecucion();
        this.tiemposSolucionActual.put(p, tiempoProcesador);
    }

    private void aumentarIndiceTiempo(Procesador p, Tarea t) {
        Integer tiempoProcesador = this.tiemposSolucionActual.get(p);
        tiempoProcesador += t.getTiempoEjecucion();
        this.tiemposSolucionActual.put(p, tiempoProcesador);
    }

    private boolean noHaySolucion() {
        return (Tarea.getCountCriticas() / 2) > procesadores.size();
    }

    private boolean validarAgregar(Tarea t, Procesador p) {
        validarHashMapsSoluciones(p);
        //procesador no refrigerado puede tener solo 2 criticas
        //procesador no refrigerado puede tener como limite tiempo ejecucion igual a tiempo

        //si esta refrigerado, retorno verdadero
        if (p.estaRefrigerado())
            return true;

        //si no lo está, chequeo que la tarea a agregar no desborde la capacidad
        if ((tiemposSolucionActual.get(p) + t.getTiempoEjecucion()) > tiempo)
            return false;

        //finalmente, como es una operacion mas cara, reviso que haya menos de 2 tareas criticas asignadas a ese procesador
        if (t.esCritica()) {
            LinkedList<Tarea> criticas = solucionActual.get(p);
            int countCriticas = 0;

            for (Tarea critica : criticas)
                if (critica.esCritica())
                    countCriticas++;

            return countCriticas < 2;
        }

        return true;
    }

    private void validarHashMapsSoluciones(Procesador p) {
        //se evitan nullPointerExceptions
        if (this.solucionActual.get(p) == null) {
            this.solucionActual.put(p, new LinkedList<>());
            this.tiemposSolucionActual.put(p, 0);
        }
    }

    private boolean solucionActualEsMejor() {
        //obtener tiempo mayor de mi solucion actual
        Collection<Integer> tiempos = tiemposSolucionActual.values();
        int tiempoMayor = 0;

        for (Integer i : tiempos)
            if (i > tiempoMayor)
                tiempoMayor = i;

        this.tiempoActual = tiempoMayor;

        return tiempoActual < tiempoFinal;
    }

    private void deepCopy() {
        this.solucionFinal.clear();

        HashMap<Procesador, LinkedList<Tarea>> copy = new HashMap<>();
        for (Map.Entry<Procesador, LinkedList<Tarea>> entry : solucionActual.entrySet())
            copy.put(entry.getKey(), new LinkedList<>(entry.getValue()));

        this.tiempoFinal = tiempoActual;

        this.solucionFinal = copy;
    }

}
