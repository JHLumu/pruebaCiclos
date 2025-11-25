package com.formandera.domain.interfaces;

import com.formandera.domain.model.Curso;
import java.util.List;

public interface RepositorioCurso {
	List<Curso> findByTematica(String tematica);
}
