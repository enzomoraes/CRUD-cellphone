package com.example.trabalho;

import androidx.annotation.NonNull;

public class Marca {

    private int idMarca;
    private String marca;

    public int getIdMarca() {
        return idMarca;
    }

    public void setIdMarca(int idMarca) {
        this.idMarca = idMarca;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    @NonNull
    @Override
    public String toString() {
        return getMarca();
    }
}
