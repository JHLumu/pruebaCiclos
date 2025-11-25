package com.formandera.domain.model;

public class PerfilUsuario {
	private final int edad;
	private final int cursosCompletados;
	private final String tematica;
	private final double puntuacionMinima;
	
	public PerfilUsuario(int edad, int cursosCompletados, String tematica, 
			double puntuacionMinima) {
        if (edad < 12) throw new IllegalArgumentException("La edad mínima es 12 años");
        if (puntuacionMinima < 1 || puntuacionMinima > 5)
            throw new IllegalArgumentException("La puntuación mínima debe estar entre 1 y 5");
        if (cursosCompletados < 0) throw new IllegalArgumentException("Número de cursos inválido");
        if (tematica == null || tematica.trim().isEmpty()) throw new IllegalArgumentException("Temática requerida");

        this.edad = edad;
        this.cursosCompletados = cursosCompletados;
        this.tematica = tematica.trim();
        this.puntuacionMinima = puntuacionMinima;
    }
	
	public int getEdad() { return edad; }
    public int getCursosCompletados() { return cursosCompletados; }
    public String getTematica() { return tematica; }
    public double getPuntuacionMinima() { return puntuacionMinima; }

}
