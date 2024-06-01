package Entity;

public class Procesador {
    private String idProcesador;
    private String codigoProcesador;
    private boolean estaRefrigerado;
    private int anioFuncionamiento;

    public Procesador(String idProcesador, String codigoProcesador, boolean estaRefrigerado, int anioFuncionamiento) {
        this.idProcesador = idProcesador;
        this.codigoProcesador = codigoProcesador;
        this.estaRefrigerado = estaRefrigerado;
        this.anioFuncionamiento = anioFuncionamiento;
    }

    public String getIdProcesador() {
        return idProcesador;
    }

    public String getCodigoProcesador() {
        return codigoProcesador;
    }

    public boolean estaRefrigerado() {
        return estaRefrigerado;
    }

    public int getAnioFuncionamiento() {
        return anioFuncionamiento;
    }

    @Override
    public String toString() {
        return "\nProcesador: " + idProcesador + " ";
    }
}
