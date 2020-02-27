package com.kafka.monitor.service;

import com.kafka.monitor.domain.MensajeReintento;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link MensajeReintento}.
 */
public interface MensajeReintentoService {

    /**
     * Save a mensajeReintento.
     *
     * @param mensajeReintento the entity to save.
     * @return the persisted entity.
     */
    MensajeReintento save(MensajeReintento mensajeReintento);

    /**
     * Get all the mensajeReintentos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MensajeReintento> findAll(Pageable pageable);

    /**
     * Get the "id" mensajeReintento.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MensajeReintento> findOne(Long id);

    /**
     * Delete the "id" mensajeReintento.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
