package info4.gl.coopcycle.service.mapper;

import info4.gl.coopcycle.domain.Restaurant;
import info4.gl.coopcycle.domain.UserCoop;
import info4.gl.coopcycle.service.dto.RestaurantDTO;
import info4.gl.coopcycle.service.dto.UserCoopDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Restaurant} and its DTO {@link RestaurantDTO}.
 */
@Mapper(componentModel = "spring")
public interface RestaurantMapper extends EntityMapper<RestaurantDTO, Restaurant> {
    @Mapping(target = "userCoop", source = "userCoop", qualifiedByName = "userCoopId")
    RestaurantDTO toDto(Restaurant s);

    @Named("userCoopId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserCoopDTO toDtoUserCoopId(UserCoop userCoop);
}
