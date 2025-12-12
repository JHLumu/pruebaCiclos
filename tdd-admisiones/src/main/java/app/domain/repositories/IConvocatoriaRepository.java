package app.domain.repositories;

import app.domain.model.IConvocatoria;
import app.domain.model.IInscripcion;
import java.util.List;

public interface IConvocatoriaRepository {
    List<IInscripcion> obtenerInscripciones(long idConvocatoria);
    IConvocatoria obtenerConvocatoria(long idConvocatoria);
}


