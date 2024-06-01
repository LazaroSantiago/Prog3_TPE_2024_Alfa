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

    public Greedy(List<Procesador> procesadores, List<Tarea> tareas) {
        this.procesadores = procesadores;
        this.tareas = tareas;
        this.solucion = new HashMap<>(procesadores.size());
        this.tiempos = new HashMap<>(procesadores.size());
    }

    public void greedy(int tiempo) {
        //validar solucion posible
        inicializarListas(tiempo);

        while (!tareas.isEmpty()) {
            //todo: se puede hacer mejor...
            //el comportamiento depende de si la tarea es critica o no.
            //si no es critica, es mucho mas facil
            //si es critica... ahi tengo que elegir un procesador viable
            Tarea t = tareas.removeFirst();

            Procesador p = procesadorMenosCargado();
            if (validarAgregar(t, p))
                agregarTarea(p, t);

           /* if (!t.esCritica()) {
//                Procesador p = procesadorMenosCargado();
                int indiceProcesador = indiceProcesadorMenosCargado();
                Procesador p = procesadores.get(indiceProcesador);

                if (p.estaRefrigerado()) {
                    agregarTarea(p, t);
                } else {
                    if (validarAgregar(t, p)) {
                        agregarTarea(p, t);
                    } else {
                        boolean dead = false;
                        for (int i = indiceProcesador; i < procesadores.size(); i++) {
                            if (validarAgregar(t, procesadores.get(i)))
                                agregarTarea(p, t);
                        }
                        for (int i = 0; i < indiceProcesador; i++) {
                            if (validarAgregar(t, procesadores.get(i)))
                                agregarTarea(p, t);
                            if (i == indiceProcesador -1)
                                dead = true;
                        }
                        if (dead)
                            System.out.println("dead");
                    }
                    //y si la validacion me da mal?
                }

            }

            //y si en vez del procesador paso la posicion y uso un arrayList?
//            if (validarAgregar(t, p))
//                agregarTarea(p, t);
//            else {

//            }
*/

            //todo: asignador de tareas, procesador con su lista de tareas
            //todo: version defensiva en caso de fallas
            //agregar tarea devuelve un booleano.
            //pruebo con los demas procesadores, si no puedo en ninguno, retorno
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

    private void agregarTarea(Procesador p, Tarea t) {
        solucion.get(p).add(t);

        Integer tiempoProcesador = this.tiempos.get(p);
        tiempoProcesador += t.getTiempoEjecucion();
        this.tiempos.put(p, tiempoProcesador);
    }

    private int indiceProcesadorMenosCargado() {
        int result = Integer.MAX_VALUE;

        for (Procesador p : procesadores) {
            if (tiempos.get(p) < result) {
                result = tiempos.get(p);
            }
        }

        return result;
    }

    private Procesador procesadorMenosCargado() {
        //se puede hacer mas inteligentemente sabiendo que:
        //si la tarea a asignar va a ser critica puedo ignorar el procesador refri
        //por cada procesador
        int tiempoMenor = Integer.MAX_VALUE;
        Procesador result = null;

        for (Procesador p : procesadores) {
            if (tiempos.get(p) < tiempoMenor) {
                tiempoMenor = tiempos.get(p);
                result = p;
            }
        }

        return result;
    }

    private boolean validarAgregar(Tarea t, Procesador p) {
        //procesador no refrigerado puede tener como limite tiempo ejecucion igual a tiempo
        if (!p.estaRefrigerado())
            if ((tiempos.get(p) + t.getTiempoEjecucion()) > tiempo)
                return false;

        //procesador puede tener solo 2 criticas
        if (t.esCritica()) {
            LinkedList<Tarea> criticas = solucion.get(p);
            int countCriticas = 0;

            for (Tarea critica : criticas)
                if (critica.esCritica())
                    countCriticas++;

            return countCriticas >= 2;
        }

        return true;
    }
}
