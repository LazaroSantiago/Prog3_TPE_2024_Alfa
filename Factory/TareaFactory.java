package Factory;

import Entity.Tarea;

public class TareaFactory implements Factory<Tarea>{
    private static TareaFactory instance;

    private TareaFactory(){};

    public static TareaFactory getInstance(){
        if (instance==null)
            instance = new TareaFactory();
        return instance;
    }

    @Override
    public Tarea create(String[] values) {
        return createTarea(values);
    }

    private Tarea createTarea(String[] values) {
        String idTarea = values[0];
        String nombreTarea = values[1];
        int tiempoEjecucion = Integer.parseInt(values[2]);
        boolean esCritica = Boolean.parseBoolean(values[3]);
        int nivelPrioridad = Integer.parseInt(values[4]);

        return new Tarea(idTarea, nombreTarea, tiempoEjecucion, esCritica, nivelPrioridad);
    }
}
