package info4.gl.coopcycle.service.mapper;

import info4.gl.coopcycle.domain.Basket;
import info4.gl.coopcycle.domain.Product;
import info4.gl.coopcycle.domain.Restaurant;
import info4.gl.coopcycle.service.dto.BasketDTO;
import info4.gl.coopcycle.service.dto.ProductDTO;
import info4.gl.coopcycle.service.dto.RestaurantDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Product} and its DTO {@link ProductDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProductMapper extends EntityMapper<ProductDTO, Product> {
    @Mapping(target = "basket", source = "basket", qualifiedByName = "basketId")
    @Mapping(target = "restaurant", source = "restaurant", qualifiedByName = "restaurantId")
    ProductDTO toDto(Product s);

    @Named("basketId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    BasketDTO toDtoBasketId(Basket basket);

    @Named("restaurantId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    RestaurantDTO toDtoRestaurantId(Restaurant restaurant);
}
