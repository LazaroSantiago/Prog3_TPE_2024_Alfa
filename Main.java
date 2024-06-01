import Entity.Procesador;
import Entity.Tarea;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        //todo: testear mas casos
        //todo: asignador de tareas, procesador con su lista de tareas
        //todo: considero casos en los que no hay solucion posible? si.
            //si mi cantidad de tareas es mas del doble de mis procesadores
                //no hay solucion.
        //todo: imprimir solamente en el main.
        //todo: pasar funcionamiento de chequeos al procesador...
        //todo: ningún procesador podrá ejecutar más de 2 tareas críticas.
        //tenias mal eso. aaaaaaaaaaaaaaa
        Servicios servicios = new Servicios("./datasets/Procesadores.csv", "./datasets/Tareas.csv");
        List<Tarea> tareas = servicios.getTareas();
        List<Procesador> procesadores = servicios.getProcesadores();

        Backtracking b = new Backtracking(procesadores, tareas);
        b.backtracking(20);

        Greedy g = new Greedy(procesadores, tareas);
        g.greedy(10000000);
    }
}
