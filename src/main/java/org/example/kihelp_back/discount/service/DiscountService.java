package org.example.kihelp_back.discount.service;

import org.example.kihelp_back.discount.model.Discount;
import org.example.kihelp_back.discount.model.DiscountType;
import org.example.kihelp_back.discount.repository.DiscountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class DiscountService {
    private final DiscountRepository discountRepository;

    public DiscountService(DiscountRepository discountRepository) {
        this.discountRepository = discountRepository;
    }

    @Transactional
    public void save(Discount discount) {
        Optional<Discount> existingDiscount;

        if(discount.getType() == DiscountType.LOCAL){
            existingDiscount = discountRepository.findFirstByUserIdAndTypeAndTaskId(
                    discount.getUser().getId(), DiscountType.LOCAL, discount.getTask().getId()
            );
        }else{
            existingDiscount = discountRepository.findFirstByTypeAndTaskId(
                    DiscountType.GLOBAL, discount.getTask().getId()
            );
        }

        if(existingDiscount.isPresent()){
            Discount discountToUpdate = existingDiscount.get();

            if(discount.getType() == DiscountType.GLOBAL &&
                discount.getDiscountValue().compareTo(BigDecimal.ZERO) == 0){
                delete(discountToUpdate.getId());
            }else{
                discountToUpdate.setDiscountValue(discount.getDiscountValue());
                discountToUpdate.setAvailableValue(discount.getAvailableValue());

                discountRepository.save(discountToUpdate);
            }
        }else{
            discountRepository.save(discount);
        }
    }

    @Transactional
    public void delete(Long id) {
        discountRepository.deleteById(id);
    }

    public Discount getDiscountByUserAndTask(Long userId, Long taskId) {
        return discountRepository.findFirstByUserIdAndTypeAndTaskId(userId, DiscountType.LOCAL, taskId)
                .filter(d -> d.getAvailableValue() != null && d.getAvailableValue() > 0)
                .orElseGet(() ->
                        discountRepository.findFirstByTypeAndTaskId(DiscountType.GLOBAL, taskId)
                                .orElse(null)
                );
    }

    @Transactional
    public BigDecimal updateDiscount(Long taskId, Long userId) {
        Discount discount = getDiscountByUserAndTask(taskId, userId);

        if (discount == null) {
            return BigDecimal.ZERO;
        }

        if (discount.getType() == DiscountType.LOCAL && discount.getAvailableValue() > 1) {
            discount.setAvailableValue(discount.getAvailableValue() - 1);
            discountRepository.save(discount);
        }else if(discount.getType() == DiscountType.LOCAL && discount.getAvailableValue() == 1) {
            delete(discount.getId());
        }

        return discount.getDiscountValue();
    }
}
