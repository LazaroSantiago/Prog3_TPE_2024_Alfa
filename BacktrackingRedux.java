import Entity.Procesador;
import Entity.ProcesadorRedux;
import Entity.Tarea;

import java.util.*;

public class BacktrackingRedux {
    private List<ProcesadorRedux> procesadores;
    private List<Tarea> tareas;

    //todo: pasar esto al procesador
    //en vez de un hashmap de procesadores, tene un arreglo u otra cosa
    private ArrayList<ProcesadorRedux> solucionFinal;
    private HashSet<ProcesadorRedux> solucionActual;

    private int tiempoActual;
    private int tiempoFinal;

    public BacktrackingRedux(List<ProcesadorRedux> procesadores, List<Tarea> tareas) {
        this.procesadores = procesadores;
        this.tareas = tareas;
        this.solucionFinal = new ArrayList<>(procesadores.size());
        this.solucionActual = new HashSet<>();
        this.tiempoFinal = Integer.MAX_VALUE;
        this.tiempoActual = 0;
    }

    public void backtracking(int tiempo) {
        if (noHaySolucion())
            return;

        ProcesadorRedux.setTiempo(tiempo);
        backtracking();
        System.out.println(">>>>>>>>> Solucion: " + solucionFinal);
        System.out.println("tiempo final: " + tiempoFinal);
    }

    public void backtracking() {
        if (tareas.isEmpty()) {
            if (solucionActualEsMejor()) {
                this.deepCopy();
            }
        } else {
            Tarea t = tareas.removeFirst();

            for (ProcesadorRedux p : procesadores) {
                solucionActual.add(p);

                if (p.agregarTarea(t)) {
                    if (solucionActualEsMejor())
                        backtracking();

                    p.quitarTarea();
                }
            }
            tareas.addFirst(t);
        }
    }

    /*public void backtracking() {
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



    //todo: emprolijar validarAgregar
    //copialo del greedy
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
        //todo: estructura para reducir este costo, explicar que se puede hacer un indice para reducir el costo
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
*/

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

    private boolean noHaySolucion() {
        return (Tarea.getCountCriticas() / 2) > procesadores.size();
    }
}
