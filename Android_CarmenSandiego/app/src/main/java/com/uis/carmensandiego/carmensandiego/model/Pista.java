package com.uis.carmensandiego.carmensandiego.model;

public class Pista {

    private String pista;
    private String resultadoOrden;

    public Pista(String pista, String resultadoOrden){
        this.pista = pista;
        this.resultadoOrden = resultadoOrden;
    }

    public String getPista() {
        return pista;
    }
    public void setPista(String pista) {
        this.pista = pista;
    }

    public String getResultadoOrden() {
        return resultadoOrden;
    }
    public void setResultadoOrden(String resultadoOrden) {
        this.resultadoOrden = resultadoOrden;
    }
}
