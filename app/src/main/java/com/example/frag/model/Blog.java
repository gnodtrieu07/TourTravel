package com.example.frag.model;

public class Blog {
    String titulo,descripcion,purl;

    public Blog() {
    }

    public Blog(String descripcion, String purl,  String titulo) {
        this.purl = purl;
        this.descripcion = descripcion;
        this.titulo = titulo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPurl() {
        return purl;
    }

    public void setPurl(String purl) {
        this.purl = purl;
    }
}

