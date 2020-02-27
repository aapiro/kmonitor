package com.kafka.monitor.web.rest;

import com.kafka.monitor.domain.MensajeReintento;
import com.kafka.monitor.service.MensajeReintentoService;
import com.kafka.monitor.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.kafka.monitor.domain.MensajeReintento}.
 */
@RestController
@RequestMapping("/api")
public class MensajeReintentoResource {

    private final Logger log = LoggerFactory.getLogger(MensajeReintentoResource.class);

    private static final String ENTITY_NAME = "mensajeReintento";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MensajeReintentoService mensajeReintentoService;

    public MensajeReintentoResource(MensajeReintentoService mensajeReintentoService) {
        this.mensajeReintentoService = mensajeReintentoService;
    }

    /**
     * {@code POST  /mensaje-reintentos} : Create a new mensajeReintento.
     *
     * @param mensajeReintento the mensajeReintento to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mensajeReintento, or with status {@code 400 (Bad Request)} if the mensajeReintento has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/mensaje-reintentos")
    public ResponseEntity<MensajeReintento> createMensajeReintento(@RequestBody MensajeReintento mensajeReintento) throws URISyntaxException {
        log.debug("REST request to save MensajeReintento : {}", mensajeReintento);
        if (mensajeReintento.getId() != null) {
            throw new BadRequestAlertException("A new mensajeReintento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MensajeReintento result = mensajeReintentoService.save(mensajeReintento);
        return ResponseEntity.created(new URI("/api/mensaje-reintentos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /mensaje-reintentos} : Updates an existing mensajeReintento.
     *
     * @param mensajeReintento the mensajeReintento to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mensajeReintento,
     * or with status {@code 400 (Bad Request)} if the mensajeReintento is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mensajeReintento couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/mensaje-reintentos")
    public ResponseEntity<MensajeReintento> updateMensajeReintento(@RequestBody MensajeReintento mensajeReintento) throws URISyntaxException {
        log.debug("REST request to update MensajeReintento : {}", mensajeReintento);
        if (mensajeReintento.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MensajeReintento result = mensajeReintentoService.save(mensajeReintento);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mensajeReintento.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /mensaje-reintentos} : get all the mensajeReintentos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mensajeReintentos in body.
     */
    @GetMapping("/mensaje-reintentos")
    public ResponseEntity<List<MensajeReintento>> getAllMensajeReintentos(Pageable pageable) {
        log.debug("REST request to get a page of MensajeReintentos");
        Page<MensajeReintento> page = mensajeReintentoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /mensaje-reintentos/:id} : get the "id" mensajeReintento.
     *
     * @param id the id of the mensajeReintento to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mensajeReintento, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/mensaje-reintentos/{id}")
    public ResponseEntity<MensajeReintento> getMensajeReintento(@PathVariable Long id) {
        log.debug("REST request to get MensajeReintento : {}", id);
        Optional<MensajeReintento> mensajeReintento = mensajeReintentoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mensajeReintento);
    }

    /**
     * {@code DELETE  /mensaje-reintentos/:id} : delete the "id" mensajeReintento.
     *
     * @param id the id of the mensajeReintento to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/mensaje-reintentos/{id}")
    public ResponseEntity<Void> deleteMensajeReintento(@PathVariable Long id) {
        log.debug("REST request to delete MensajeReintento : {}", id);
        mensajeReintentoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
