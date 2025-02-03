package org.example.kihelp_back.discount.controller;

import org.example.kihelp_back.discount.dto.DiscountCreateDto;
import org.example.kihelp_back.discount.usecase.DiscountCreateUseCase;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/discounts")
public class DiscountController {
    private final DiscountCreateUseCase discountCreateUseCase;

    public DiscountController(DiscountCreateUseCase discountCreateUseCase) {
        this.discountCreateUseCase = discountCreateUseCase;
    }

    @PostMapping("/discount")
    public void createDiscount(@RequestBody DiscountCreateDto discountCreateDto) {
        System.out.println(discountCreateDto);
        discountCreateUseCase.create(discountCreateDto);
    }
}
