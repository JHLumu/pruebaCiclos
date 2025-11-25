package com.formandera.application.ports.inbound;

import com.formandera.application.dto.ResultadoCurso;
import com.formandera.domain.model.PerfilUsuario;
import java.util.List;

public interface PortBusquedaCurso {
	
	 List<ResultadoCurso> findRecommendedCourses(PerfilUsuario user);

}
