package org.example.kihelp_back.discount.usecase.impl;

import org.example.kihelp_back.discount.dto.DiscountCreateDto;
import org.example.kihelp_back.discount.mapper.DiscountMapper;
import org.example.kihelp_back.discount.model.Discount;
import org.example.kihelp_back.discount.service.DiscountService;
import org.example.kihelp_back.discount.usecase.DiscountCreateUseCase;
import org.springframework.stereotype.Component;

@Component
public class DiscountCreateUseCaseImpl implements DiscountCreateUseCase {
    private final DiscountService discountService;
    private final DiscountMapper discountMapper;

    public DiscountCreateUseCaseImpl(DiscountService discountService,
                                     DiscountMapper discountMapper) {
        this.discountService = discountService;
        this.discountMapper = discountMapper;
    }

    @Override
    public void create(DiscountCreateDto discountCreateDto) {
        Discount discount = discountMapper.toEntity(discountCreateDto);

        discountService.save(discount);
    }
}
