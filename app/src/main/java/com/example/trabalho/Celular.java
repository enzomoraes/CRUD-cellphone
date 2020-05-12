package com.example.trabalho;

import androidx.annotation.NonNull;

public class Celular {

    public Celular() {
        super();
    }

    private Marca marca;
    private int idCel;
    private String modelo;

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    public int getIdCel() {
        return idCel;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public void setIdCel(int idCel) {
        this.idCel = idCel;
    }

    @NonNull
    @Override
    public String toString() {
        return getModelo()+ " da marca "+ getMarca().toString();
    }
}
