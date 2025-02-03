package org.example.kihelp_back.discount.repository;

import org.example.kihelp_back.discount.model.Discount;
import org.example.kihelp_back.discount.model.DiscountType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface DiscountRepository extends JpaRepository<Discount, Long> {
    @Transactional(readOnly = true)
    Optional<Discount> findFirstByUserIdAndTypeAndTaskId(Long userId, DiscountType type, Long taskId);
    @Transactional(readOnly = true)
    Optional<Discount> findFirstByTypeAndTaskId(DiscountType type, Long taskId);
}
