package com.formandera.domain.model;

public class DiscountResult {
    private final double porcentaje; // 0..100
    private final double precioFinal;

    public DiscountResult(double porcentaje, double precioFinal) {
        this.porcentaje = porcentaje;
        this.precioFinal = precioFinal;
    }

    public double getPorcentaje() { return porcentaje; }
    public double getPrecioFinal() { return precioFinal; }
}

