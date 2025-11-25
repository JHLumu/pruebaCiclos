package com.formandera.config;

import com.formandera.application.ports.inbound.PortBusquedaCurso;
import com.formandera.application.usecases.EncuentraCursosRecomendadosUseCase;
import com.formandera.domain.interfaces.RepositorioCurso;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    @Bean
    public PortBusquedaCurso courseSearchPort(RepositorioCurso repository) {
        return new EncuentraCursosRecomendadosUseCase(repository);
    }
}

