package com.formandera.adapters.outbound;

import com.formandera.application.ports.outbound.DiscountCalculatorPort;
import com.formandera.domain.model.DiscountResult;
import org.springframework.stereotype.Service;

@Service
public class DiscountService implements DiscountCalculatorPort {

	public DiscountResult calculateDiscount(int nCursosPrevios, boolean esPremium, double precioBase) {
        double descuento = 0.0;

        if (esPremium)
        	descuento = descuentoPremium(nCursosPrevios);
        else
        	descuento = descuentoNoPremium(nCursosPrevios);

        double precioFinal = precioBase * (1 - descuento / 100);
        return new DiscountResult(descuento, precioFinal);
    }
	
	private double descuentoPremium(int nCursosPrevios) {
		
		if(nCursosPrevios>20)
			return 25.0;
		
		if(nCursosPrevios<20)
			return 10.0;
		
		return 5.0;
	}
	
	private double descuentoNoPremium(int nCursosPrevios) {
		
		if(nCursosPrevios>10)
			return 10.0;
		
		if(nCursosPrevios<10)
			return 0.0;
		
		return 5.0;
	}

}
