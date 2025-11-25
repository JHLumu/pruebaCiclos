package com.formandera.domain.model;

public class Curso {
	private final String nombre;
	private final String tematica;
	private final int edadRecomendada;
	private final double valoracionMedia;
	private final double precioBase;
	
	public Curso (String nombre, String tematica, int edadRecomendada,
			double valoracionMedia, double precioBase) {

	  if (edadRecomendada < 0) throw new IllegalArgumentException("Edad recomendada inválida");
	  if (valoracionMedia < 0 || valoracionMedia > 5) throw new IllegalArgumentException("Valoración inválida");
	  if (precioBase <= 0) throw new IllegalArgumentException("Precio inválido");
	
	  this.nombre = nombre;
	  this.tematica = tematica;
	  this.edadRecomendada = edadRecomendada;
	  this.valoracionMedia = valoracionMedia;
	  this.precioBase = precioBase;
	}
	
	public String getNombre() { return nombre; }
    public String getTematica() { return tematica; }
    public int getEdadRecomendada() { return edadRecomendada; }
    public double getValoracionMedia() { return valoracionMedia; }
    public double getPrecioBase() { return precioBase; }
	

}
