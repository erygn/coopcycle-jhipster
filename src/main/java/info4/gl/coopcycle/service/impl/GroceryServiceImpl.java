package info4.gl.coopcycle.service.impl;

import info4.gl.coopcycle.domain.Grocery;
import info4.gl.coopcycle.repository.GroceryRepository;
import info4.gl.coopcycle.service.GroceryService;
import info4.gl.coopcycle.service.dto.GroceryDTO;
import info4.gl.coopcycle.service.mapper.GroceryMapper;
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
 * Service Implementation for managing {@link Grocery}.
 */
@Service
@Transactional
public class GroceryServiceImpl implements GroceryService {

    private final Logger log = LoggerFactory.getLogger(GroceryServiceImpl.class);

    private final GroceryRepository groceryRepository;

    private final GroceryMapper groceryMapper;

    public GroceryServiceImpl(GroceryRepository groceryRepository, GroceryMapper groceryMapper) {
        this.groceryRepository = groceryRepository;
        this.groceryMapper = groceryMapper;
    }

    @Override
    public GroceryDTO save(GroceryDTO groceryDTO) {
        log.debug("Request to save Grocery : {}", groceryDTO);
        Grocery grocery = groceryMapper.toEntity(groceryDTO);
        grocery = groceryRepository.save(grocery);
        return groceryMapper.toDto(grocery);
    }

    @Override
    public GroceryDTO update(GroceryDTO groceryDTO) {
        log.debug("Request to save Grocery : {}", groceryDTO);
        Grocery grocery = groceryMapper.toEntity(groceryDTO);
        grocery.setIsPersisted();
        grocery = groceryRepository.save(grocery);
        return groceryMapper.toDto(grocery);
    }

    @Override
    public Optional<GroceryDTO> partialUpdate(GroceryDTO groceryDTO) {
        log.debug("Request to partially update Grocery : {}", groceryDTO);

        return groceryRepository
            .findById(groceryDTO.getId())
            .map(existingGrocery -> {
                groceryMapper.partialUpdate(existingGrocery, groceryDTO);

                return existingGrocery;
            })
            .map(groceryRepository::save)
            .map(groceryMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GroceryDTO> findAll() {
        log.debug("Request to get all Groceries");
        return groceryRepository.findAll().stream().map(groceryMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the groceries where Payment is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<GroceryDTO> findAllWherePaymentIsNull() {
        log.debug("Request to get all groceries where Payment is null");
        return StreamSupport
            .stream(groceryRepository.findAll().spliterator(), false)
            .filter(grocery -> grocery.getPayment() == null)
            .map(groceryMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<GroceryDTO> findOne(String id) {
        log.debug("Request to get Grocery : {}", id);
        return groceryRepository.findById(id).map(groceryMapper::toDto);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Grocery : {}", id);
        groceryRepository.deleteById(id);
    }
}
