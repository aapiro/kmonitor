package com.kafka.monitor.service.impl;

import com.kafka.monitor.service.MensajeReintentoService;
import com.kafka.monitor.domain.MensajeReintento;
import com.kafka.monitor.repository.MensajeReintentoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link MensajeReintento}.
 */
@Service
@Transactional
public class MensajeReintentoServiceImpl implements MensajeReintentoService {

    private final Logger log = LoggerFactory.getLogger(MensajeReintentoServiceImpl.class);

    private final MensajeReintentoRepository mensajeReintentoRepository;

    public MensajeReintentoServiceImpl(MensajeReintentoRepository mensajeReintentoRepository) {
        this.mensajeReintentoRepository = mensajeReintentoRepository;
    }

    /**
     * Save a mensajeReintento.
     *
     * @param mensajeReintento the entity to save.
     * @return the persisted entity.
     */
    @Override
    public MensajeReintento save(MensajeReintento mensajeReintento) {
        log.debug("Request to save MensajeReintento : {}", mensajeReintento);
        return mensajeReintentoRepository.save(mensajeReintento);
    }

    /**
     * Get all the mensajeReintentos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MensajeReintento> findAll(Pageable pageable) {
        log.debug("Request to get all MensajeReintentos");
        return mensajeReintentoRepository.findAll(pageable);
    }

    /**
     * Get one mensajeReintento by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<MensajeReintento> findOne(Long id) {
        log.debug("Request to get MensajeReintento : {}", id);
        return mensajeReintentoRepository.findById(id);
    }

    /**
     * Delete the mensajeReintento by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MensajeReintento : {}", id);
        mensajeReintentoRepository.deleteById(id);
    }
}
