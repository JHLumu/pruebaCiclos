package com.formandera.application.usecases;

import com.formandera.domain.model.DiscountResult;
import com.formandera.application.ports.outbound.DiscountCalculatorPort;

public class CalculateDiscountUseCase {
    private final DiscountCalculatorPort calculator;

    public CalculateDiscountUseCase(DiscountCalculatorPort calculator) {
        this.calculator = calculator;
    }

    public DiscountResult execute(int nCursosPrevios, boolean esPremium, double precioBase) {
        return calculator.calculateDiscount(nCursosPrevios, esPremium, precioBase);
    }
}

