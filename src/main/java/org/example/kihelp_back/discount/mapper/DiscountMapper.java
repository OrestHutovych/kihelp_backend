package org.example.kihelp_back.discount.mapper;

import org.example.kihelp_back.discount.dto.DiscountCreateDto;
import org.example.kihelp_back.discount.model.Discount;

public interface DiscountMapper {
    Discount toEntity(DiscountCreateDto createDto);
}
