import Entity.ProcesadorRedux;
import Entity.Tarea;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        //todo: testear mas casos
        //todo: agregar verificacion al final que compruebe que la cantidad de tareas asignadas sea igual a la cantidad de tareas al principio. Si no, retornar error.
        //todo: imitar la estructura de presentacion de datos que muestran en el TPE
        //todo: contar cantidad de estados y candidatos en ambas soluciones
        //todo: cambiar variable 'noHaySolucion' por 'haySolucion', porque confunde. Cambiar nombre de funcion por 'comprobarSolucion()'

        Servicios servicios = new Servicios("./datasets/Procesadores.csv", "./datasets/Tareas.csv");
        List<Tarea> tareas = servicios.getTareas();
        List<ProcesadorRedux> procesadores = servicios.getProcesadores();

        BacktrackingRedux b = new BacktrackingRedux(procesadores, tareas);
        GreedyRedux g = new GreedyRedux(procesadores, tareas);
//        b.backtracking(100000);
//        System.out.println(b.getCountEstados());
        g.greedy(10000);
    }
}
