package com.formandera.application.ports.outbound;

import com.formandera.domain.model.DiscountResult;

public interface DiscountCalculatorPort {
	
	DiscountResult calculateDiscount(int nCursosPrevios, boolean esPremium, double precioBase);

}
