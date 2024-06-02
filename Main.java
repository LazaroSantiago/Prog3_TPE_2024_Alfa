import Entity.Procesador;
import Entity.ProcesadorRedux;
import Entity.Tarea;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        //todo: testear mas casos

        //todo: pasar funcionamiento de chequeos al procesador...
        //todo: revisar herencia de Backtracking y Greedy
        //todo: asignador de tareas, procesador con su lista de tareas
        //todo: imprimir solamente en el main.
        //todo: ningún procesador podrá ejecutar más de 2 tareas críticas.
        //tenias mal eso. aaaaaaaaaaaaaaa
        Servicios servicios = new Servicios("./datasets/Procesadores.csv", "./datasets/Tareas.csv");
        List<Tarea> tareas = servicios.getTareas();
        List<ProcesadorRedux> procesadores = servicios.getProcesadores();

        BacktrackingRedux b = new BacktrackingRedux(procesadores, tareas);
        b.backtracking(20);

//        Greedy g = new Greedy(procesadores, tareas);
//        g.greedy(10000000);
    }
}
