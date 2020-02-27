package com.kafka.monitor.repository;

import com.kafka.monitor.domain.MensajeReintento;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MensajeReintento entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MensajeReintentoRepository extends JpaRepository<MensajeReintento, Long> {

}
