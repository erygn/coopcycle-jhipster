package info4.gl.coopcycle.service.impl;

import info4.gl.coopcycle.domain.UserCoop;
import info4.gl.coopcycle.repository.UserCoopRepository;
import info4.gl.coopcycle.service.UserCoopService;
import info4.gl.coopcycle.service.dto.UserCoopDTO;
import info4.gl.coopcycle.service.mapper.UserCoopMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link UserCoop}.
 */
@Service
@Transactional
public class UserCoopServiceImpl implements UserCoopService {

    private final Logger log = LoggerFactory.getLogger(UserCoopServiceImpl.class);

    private final UserCoopRepository userCoopRepository;

    private final UserCoopMapper userCoopMapper;

    public UserCoopServiceImpl(UserCoopRepository userCoopRepository, UserCoopMapper userCoopMapper) {
        this.userCoopRepository = userCoopRepository;
        this.userCoopMapper = userCoopMapper;
    }

    @Override
    public UserCoopDTO save(UserCoopDTO userCoopDTO) {
        log.debug("Request to save UserCoop : {}", userCoopDTO);
        UserCoop userCoop = userCoopMapper.toEntity(userCoopDTO);
        userCoop = userCoopRepository.save(userCoop);
        return userCoopMapper.toDto(userCoop);
    }

    @Override
    public UserCoopDTO update(UserCoopDTO userCoopDTO) {
        log.debug("Request to save UserCoop : {}", userCoopDTO);
        UserCoop userCoop = userCoopMapper.toEntity(userCoopDTO);
        userCoop = userCoopRepository.save(userCoop);
        return userCoopMapper.toDto(userCoop);
    }

    @Override
    public Optional<UserCoopDTO> partialUpdate(UserCoopDTO userCoopDTO) {
        log.debug("Request to partially update UserCoop : {}", userCoopDTO);

        return userCoopRepository
            .findById(userCoopDTO.getId())
            .map(existingUserCoop -> {
                userCoopMapper.partialUpdate(existingUserCoop, userCoopDTO);

                return existingUserCoop;
            })
            .map(userCoopRepository::save)
            .map(userCoopMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserCoopDTO> findAll() {
        log.debug("Request to get all UserCoops");
        return userCoopRepository
            .findAllWithEagerRelationships()
            .stream()
            .map(userCoopMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    public Page<UserCoopDTO> findAllWithEagerRelationships(Pageable pageable) {
        return userCoopRepository.findAllWithEagerRelationships(pageable).map(userCoopMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserCoopDTO> findOne(Long id) {
        log.debug("Request to get UserCoop : {}", id);
        return userCoopRepository.findOneWithEagerRelationships(id).map(userCoopMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserCoop : {}", id);
        userCoopRepository.deleteById(id);
    }
}
