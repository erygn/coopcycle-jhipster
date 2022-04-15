package info4.gl.coopcycle.service.mapper;

import info4.gl.coopcycle.domain.Cooperative;
import info4.gl.coopcycle.domain.UserCoop;
import info4.gl.coopcycle.service.dto.CooperativeDTO;
import info4.gl.coopcycle.service.dto.UserCoopDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link UserCoop} and its DTO {@link UserCoopDTO}.
 */
@Mapper(componentModel = "spring")
public interface UserCoopMapper extends EntityMapper<UserCoopDTO, UserCoop> {
    @Mapping(target = "cooperatives", source = "cooperatives", qualifiedByName = "cooperativeIdSet")
    UserCoopDTO toDto(UserCoop s);

    @Mapping(target = "removeCooperative", ignore = true)
    UserCoop toEntity(UserCoopDTO userCoopDTO);

    @Named("cooperativeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CooperativeDTO toDtoCooperativeId(Cooperative cooperative);

    @Named("cooperativeIdSet")
    default Set<CooperativeDTO> toDtoCooperativeIdSet(Set<Cooperative> cooperative) {
        return cooperative.stream().map(this::toDtoCooperativeId).collect(Collectors.toSet());
    }
}
