package Factory;

import Entity.Procesador;
import Entity.ProcesadorRedux;

public class ProcesadorFactory implements Factory<ProcesadorRedux>{
    private static ProcesadorFactory instance;

    private ProcesadorFactory(){};

    public static ProcesadorFactory getInstance(){
        if (instance==null)
            instance = new ProcesadorFactory();
        return instance;
    }

    @Override
    public ProcesadorRedux create(String[] values) {
        return createProccesador(values);
    }

    private ProcesadorRedux createProccesador(String[] values) {
        String idProcesador = values[0];
        String codigoProcesador = values[1];
        boolean estaRefrigerado = Boolean.parseBoolean(values[2]);
        int anioFuncionamiento = Integer.parseInt(values[3]);

        return new ProcesadorRedux(idProcesador, codigoProcesador , estaRefrigerado, anioFuncionamiento);
    }

}
