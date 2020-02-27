package com.kafka.monitor.web.rest;

import com.kafka.monitor.KafkaMonitorApp;
import com.kafka.monitor.domain.MensajeReintento;
import com.kafka.monitor.repository.MensajeReintentoRepository;
import com.kafka.monitor.service.MensajeReintentoService;
import com.kafka.monitor.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.kafka.monitor.web.rest.TestUtil.sameInstant;
import static com.kafka.monitor.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link MensajeReintentoResource} REST controller.
 */
@SpringBootTest(classes = KafkaMonitorApp.class)
public class MensajeReintentoResourceIT {

    private static final String DEFAULT_NOMBRE_MICROSERVICIO = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_MICROSERVICIO = "BBBBBBBBBB";

    private static final String DEFAULT_ERROR = "AAAAAAAAAA";
    private static final String UPDATED_ERROR = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_FECHA_HORA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_FECHA_HORA = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_MENSAJE = "AAAAAAAAAA";
    private static final String UPDATED_MENSAJE = "BBBBBBBBBB";

    @Autowired
    private MensajeReintentoRepository mensajeReintentoRepository;

    @Autowired
    private MensajeReintentoService mensajeReintentoService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restMensajeReintentoMockMvc;

    private MensajeReintento mensajeReintento;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MensajeReintentoResource mensajeReintentoResource = new MensajeReintentoResource(mensajeReintentoService);
        this.restMensajeReintentoMockMvc = MockMvcBuilders.standaloneSetup(mensajeReintentoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MensajeReintento createEntity(EntityManager em) {
        MensajeReintento mensajeReintento = new MensajeReintento()
            .nombreMicroservicio(DEFAULT_NOMBRE_MICROSERVICIO)
            .error(DEFAULT_ERROR)
            .fechaHora(DEFAULT_FECHA_HORA)
            .mensaje(DEFAULT_MENSAJE);
        return mensajeReintento;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MensajeReintento createUpdatedEntity(EntityManager em) {
        MensajeReintento mensajeReintento = new MensajeReintento()
            .nombreMicroservicio(UPDATED_NOMBRE_MICROSERVICIO)
            .error(UPDATED_ERROR)
            .fechaHora(UPDATED_FECHA_HORA)
            .mensaje(UPDATED_MENSAJE);
        return mensajeReintento;
    }

    @BeforeEach
    public void initTest() {
        mensajeReintento = createEntity(em);
    }

    @Test
    @Transactional
    public void createMensajeReintento() throws Exception {
        int databaseSizeBeforeCreate = mensajeReintentoRepository.findAll().size();

        // Create the MensajeReintento
        restMensajeReintentoMockMvc.perform(post("/api/mensaje-reintentos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mensajeReintento)))
            .andExpect(status().isCreated());

        // Validate the MensajeReintento in the database
        List<MensajeReintento> mensajeReintentoList = mensajeReintentoRepository.findAll();
        assertThat(mensajeReintentoList).hasSize(databaseSizeBeforeCreate + 1);
        MensajeReintento testMensajeReintento = mensajeReintentoList.get(mensajeReintentoList.size() - 1);
        assertThat(testMensajeReintento.getNombreMicroservicio()).isEqualTo(DEFAULT_NOMBRE_MICROSERVICIO);
        assertThat(testMensajeReintento.getError()).isEqualTo(DEFAULT_ERROR);
        assertThat(testMensajeReintento.getFechaHora()).isEqualTo(DEFAULT_FECHA_HORA);
        assertThat(testMensajeReintento.getMensaje()).isEqualTo(DEFAULT_MENSAJE);
    }

    @Test
    @Transactional
    public void createMensajeReintentoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mensajeReintentoRepository.findAll().size();

        // Create the MensajeReintento with an existing ID
        mensajeReintento.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMensajeReintentoMockMvc.perform(post("/api/mensaje-reintentos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mensajeReintento)))
            .andExpect(status().isBadRequest());

        // Validate the MensajeReintento in the database
        List<MensajeReintento> mensajeReintentoList = mensajeReintentoRepository.findAll();
        assertThat(mensajeReintentoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllMensajeReintentos() throws Exception {
        // Initialize the database
        mensajeReintentoRepository.saveAndFlush(mensajeReintento);

        // Get all the mensajeReintentoList
        restMensajeReintentoMockMvc.perform(get("/api/mensaje-reintentos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mensajeReintento.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombreMicroservicio").value(hasItem(DEFAULT_NOMBRE_MICROSERVICIO)))
            .andExpect(jsonPath("$.[*].error").value(hasItem(DEFAULT_ERROR)))
            .andExpect(jsonPath("$.[*].fechaHora").value(hasItem(sameInstant(DEFAULT_FECHA_HORA))))
            .andExpect(jsonPath("$.[*].mensaje").value(hasItem(DEFAULT_MENSAJE)));
    }
    
    @Test
    @Transactional
    public void getMensajeReintento() throws Exception {
        // Initialize the database
        mensajeReintentoRepository.saveAndFlush(mensajeReintento);

        // Get the mensajeReintento
        restMensajeReintentoMockMvc.perform(get("/api/mensaje-reintentos/{id}", mensajeReintento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mensajeReintento.getId().intValue()))
            .andExpect(jsonPath("$.nombreMicroservicio").value(DEFAULT_NOMBRE_MICROSERVICIO))
            .andExpect(jsonPath("$.error").value(DEFAULT_ERROR))
            .andExpect(jsonPath("$.fechaHora").value(sameInstant(DEFAULT_FECHA_HORA)))
            .andExpect(jsonPath("$.mensaje").value(DEFAULT_MENSAJE));
    }

    @Test
    @Transactional
    public void getNonExistingMensajeReintento() throws Exception {
        // Get the mensajeReintento
        restMensajeReintentoMockMvc.perform(get("/api/mensaje-reintentos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMensajeReintento() throws Exception {
        // Initialize the database
        mensajeReintentoService.save(mensajeReintento);

        int databaseSizeBeforeUpdate = mensajeReintentoRepository.findAll().size();

        // Update the mensajeReintento
        MensajeReintento updatedMensajeReintento = mensajeReintentoRepository.findById(mensajeReintento.getId()).get();
        // Disconnect from session so that the updates on updatedMensajeReintento are not directly saved in db
        em.detach(updatedMensajeReintento);
        updatedMensajeReintento
            .nombreMicroservicio(UPDATED_NOMBRE_MICROSERVICIO)
            .error(UPDATED_ERROR)
            .fechaHora(UPDATED_FECHA_HORA)
            .mensaje(UPDATED_MENSAJE);

        restMensajeReintentoMockMvc.perform(put("/api/mensaje-reintentos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedMensajeReintento)))
            .andExpect(status().isOk());

        // Validate the MensajeReintento in the database
        List<MensajeReintento> mensajeReintentoList = mensajeReintentoRepository.findAll();
        assertThat(mensajeReintentoList).hasSize(databaseSizeBeforeUpdate);
        MensajeReintento testMensajeReintento = mensajeReintentoList.get(mensajeReintentoList.size() - 1);
        assertThat(testMensajeReintento.getNombreMicroservicio()).isEqualTo(UPDATED_NOMBRE_MICROSERVICIO);
        assertThat(testMensajeReintento.getError()).isEqualTo(UPDATED_ERROR);
        assertThat(testMensajeReintento.getFechaHora()).isEqualTo(UPDATED_FECHA_HORA);
        assertThat(testMensajeReintento.getMensaje()).isEqualTo(UPDATED_MENSAJE);
    }

    @Test
    @Transactional
    public void updateNonExistingMensajeReintento() throws Exception {
        int databaseSizeBeforeUpdate = mensajeReintentoRepository.findAll().size();

        // Create the MensajeReintento

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMensajeReintentoMockMvc.perform(put("/api/mensaje-reintentos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mensajeReintento)))
            .andExpect(status().isBadRequest());

        // Validate the MensajeReintento in the database
        List<MensajeReintento> mensajeReintentoList = mensajeReintentoRepository.findAll();
        assertThat(mensajeReintentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMensajeReintento() throws Exception {
        // Initialize the database
        mensajeReintentoService.save(mensajeReintento);

        int databaseSizeBeforeDelete = mensajeReintentoRepository.findAll().size();

        // Delete the mensajeReintento
        restMensajeReintentoMockMvc.perform(delete("/api/mensaje-reintentos/{id}", mensajeReintento.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MensajeReintento> mensajeReintentoList = mensajeReintentoRepository.findAll();
        assertThat(mensajeReintentoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
