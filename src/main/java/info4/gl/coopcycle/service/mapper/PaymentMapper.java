package info4.gl.coopcycle.service.mapper;

import info4.gl.coopcycle.domain.Grocery;
import info4.gl.coopcycle.domain.Payment;
import info4.gl.coopcycle.service.dto.GroceryDTO;
import info4.gl.coopcycle.service.dto.PaymentDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Payment} and its DTO {@link PaymentDTO}.
 */
@Mapper(componentModel = "spring")
public interface PaymentMapper extends EntityMapper<PaymentDTO, Payment> {
    @Mapping(target = "grocery", source = "grocery", qualifiedByName = "groceryId")
    PaymentDTO toDto(Payment s);

    @Named("groceryId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    GroceryDTO toDtoGroceryId(Grocery grocery);
}
