package info4.gl.coopcycle.service.mapper;

import info4.gl.coopcycle.domain.Basket;
import info4.gl.coopcycle.domain.Grocery;
import info4.gl.coopcycle.domain.UserCoop;
import info4.gl.coopcycle.service.dto.BasketDTO;
import info4.gl.coopcycle.service.dto.GroceryDTO;
import info4.gl.coopcycle.service.dto.UserCoopDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Grocery} and its DTO {@link GroceryDTO}.
 */
@Mapper(componentModel = "spring")
public interface GroceryMapper extends EntityMapper<GroceryDTO, Grocery> {
    @Mapping(target = "basket", source = "basket", qualifiedByName = "basketId")
    @Mapping(target = "userCoop", source = "userCoop", qualifiedByName = "userCoopId")
    GroceryDTO toDto(Grocery s);

    @Named("basketId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    BasketDTO toDtoBasketId(Basket basket);

    @Named("userCoopId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserCoopDTO toDtoUserCoopId(UserCoop userCoop);
}
