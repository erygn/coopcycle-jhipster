package info4.gl.coopcycle.service.impl;

import info4.gl.coopcycle.domain.Basket;
import info4.gl.coopcycle.repository.BasketRepository;
import info4.gl.coopcycle.service.BasketService;
import info4.gl.coopcycle.service.dto.BasketDTO;
import info4.gl.coopcycle.service.mapper.BasketMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Basket}.
 */
@Service
@Transactional
public class BasketServiceImpl implements BasketService {

    private final Logger log = LoggerFactory.getLogger(BasketServiceImpl.class);

    private final BasketRepository basketRepository;

    private final BasketMapper basketMapper;

    public BasketServiceImpl(BasketRepository basketRepository, BasketMapper basketMapper) {
        this.basketRepository = basketRepository;
        this.basketMapper = basketMapper;
    }

    @Override
    public BasketDTO save(BasketDTO basketDTO) {
        log.debug("Request to save Basket : {}", basketDTO);
        Basket basket = basketMapper.toEntity(basketDTO);
        basket = basketRepository.save(basket);
        return basketMapper.toDto(basket);
    }

    @Override
    public BasketDTO update(BasketDTO basketDTO) {
        log.debug("Request to save Basket : {}", basketDTO);
        Basket basket = basketMapper.toEntity(basketDTO);
        basket.setIsPersisted();
        basket = basketRepository.save(basket);
        return basketMapper.toDto(basket);
    }

    @Override
    public Optional<BasketDTO> partialUpdate(BasketDTO basketDTO) {
        log.debug("Request to partially update Basket : {}", basketDTO);

        return basketRepository
            .findById(basketDTO.getId())
            .map(existingBasket -> {
                basketMapper.partialUpdate(existingBasket, basketDTO);

                return existingBasket;
            })
            .map(basketRepository::save)
            .map(basketMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BasketDTO> findAll() {
        log.debug("Request to get all Baskets");
        return basketRepository.findAll().stream().map(basketMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the baskets where Grocery is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<BasketDTO> findAllWhereGroceryIsNull() {
        log.debug("Request to get all baskets where Grocery is null");
        return StreamSupport
            .stream(basketRepository.findAll().spliterator(), false)
            .filter(basket -> basket.getGrocery() == null)
            .map(basketMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BasketDTO> findOne(String id) {
        log.debug("Request to get Basket : {}", id);
        return basketRepository.findById(id).map(basketMapper::toDto);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Basket : {}", id);
        basketRepository.deleteById(id);
    }
}
