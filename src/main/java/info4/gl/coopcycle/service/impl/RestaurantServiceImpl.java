package info4.gl.coopcycle.service.impl;

import info4.gl.coopcycle.domain.Restaurant;
import info4.gl.coopcycle.repository.RestaurantRepository;
import info4.gl.coopcycle.service.RestaurantService;
import info4.gl.coopcycle.service.dto.RestaurantDTO;
import info4.gl.coopcycle.service.mapper.RestaurantMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Restaurant}.
 */
@Service
@Transactional
public class RestaurantServiceImpl implements RestaurantService {

    private final Logger log = LoggerFactory.getLogger(RestaurantServiceImpl.class);

    private final RestaurantRepository restaurantRepository;

    private final RestaurantMapper restaurantMapper;

    public RestaurantServiceImpl(RestaurantRepository restaurantRepository, RestaurantMapper restaurantMapper) {
        this.restaurantRepository = restaurantRepository;
        this.restaurantMapper = restaurantMapper;
    }

    @Override
    public RestaurantDTO save(RestaurantDTO restaurantDTO) {
        log.debug("Request to save Restaurant : {}", restaurantDTO);
        Restaurant restaurant = restaurantMapper.toEntity(restaurantDTO);
        restaurant = restaurantRepository.save(restaurant);
        return restaurantMapper.toDto(restaurant);
    }

    @Override
    public RestaurantDTO update(RestaurantDTO restaurantDTO) {
        log.debug("Request to save Restaurant : {}", restaurantDTO);
        Restaurant restaurant = restaurantMapper.toEntity(restaurantDTO);
        restaurant = restaurantRepository.save(restaurant);
        return restaurantMapper.toDto(restaurant);
    }

    @Override
    public Optional<RestaurantDTO> partialUpdate(RestaurantDTO restaurantDTO) {
        log.debug("Request to partially update Restaurant : {}", restaurantDTO);

        return restaurantRepository
            .findById(restaurantDTO.getId())
            .map(existingRestaurant -> {
                restaurantMapper.partialUpdate(existingRestaurant, restaurantDTO);

                return existingRestaurant;
            })
            .map(restaurantRepository::save)
            .map(restaurantMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RestaurantDTO> findAll() {
        log.debug("Request to get all Restaurants");
        return restaurantRepository.findAll().stream().map(restaurantMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RestaurantDTO> findOne(Long id) {
        log.debug("Request to get Restaurant : {}", id);
        return restaurantRepository.findById(id).map(restaurantMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Restaurant : {}", id);
        restaurantRepository.deleteById(id);
    }
}
