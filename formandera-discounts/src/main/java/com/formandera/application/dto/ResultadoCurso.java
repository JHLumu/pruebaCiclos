package com.formandera.application.dto;

public class ResultadoCurso {
	private final String nombre;
    private final double valoracionMedia;
    private final double precioBase;
    private final double precioConDescuento;

    public ResultadoCurso(String nombre, double valoracionMedia, double precioBase, double precioConDescuento) {
        this.nombre = nombre;
        this.valoracionMedia = valoracionMedia;
        this.precioBase = precioBase;
        this.precioConDescuento = precioConDescuento;
    }

    public String getNombre() { return nombre; }
    public double getValoracionMedia() { return valoracionMedia; }
    public double getPrecioBase() { return precioBase; }
    public double getPrecioConDescuento() { return precioConDescuento; }
}
