package info4.gl.coopcycle.service.mapper;

import info4.gl.coopcycle.domain.Basket;
import info4.gl.coopcycle.domain.UserCoop;
import info4.gl.coopcycle.service.dto.BasketDTO;
import info4.gl.coopcycle.service.dto.UserCoopDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Basket} and its DTO {@link BasketDTO}.
 */
@Mapper(componentModel = "spring")
public interface BasketMapper extends EntityMapper<BasketDTO, Basket> {
    @Mapping(target = "userCoop", source = "userCoop", qualifiedByName = "userCoopId")
    BasketDTO toDto(Basket s);

    @Named("userCoopId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserCoopDTO toDtoUserCoopId(UserCoop userCoop);
}
