package com.kafka.monitor.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A MensajeReintento.
 */
@Entity
@Table(name = "mensaje_reintento")
public class MensajeReintento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "nombre_microservicio")
    private String nombreMicroservicio;

    @Column(name = "error")
    private String error;

    @Column(name = "fecha_hora")
    private ZonedDateTime fechaHora;

    @Column(name = "mensaje")
    private String mensaje;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreMicroservicio() {
        return nombreMicroservicio;
    }

    public MensajeReintento nombreMicroservicio(String nombreMicroservicio) {
        this.nombreMicroservicio = nombreMicroservicio;
        return this;
    }

    public void setNombreMicroservicio(String nombreMicroservicio) {
        this.nombreMicroservicio = nombreMicroservicio;
    }

    public String getError() {
        return error;
    }

    public MensajeReintento error(String error) {
        this.error = error;
        return this;
    }

    public void setError(String error) {
        this.error = error;
    }

    public ZonedDateTime getFechaHora() {
        return fechaHora;
    }

    public MensajeReintento fechaHora(ZonedDateTime fechaHora) {
        this.fechaHora = fechaHora;
        return this;
    }

    public void setFechaHora(ZonedDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getMensaje() {
        return mensaje;
    }

    public MensajeReintento mensaje(String mensaje) {
        this.mensaje = mensaje;
        return this;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MensajeReintento)) {
            return false;
        }
        return id != null && id.equals(((MensajeReintento) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MensajeReintento{" +
            "id=" + getId() +
            ", nombreMicroservicio='" + getNombreMicroservicio() + "'" +
            ", error='" + getError() + "'" +
            ", fechaHora='" + getFechaHora() + "'" +
            ", mensaje='" + getMensaje() + "'" +
            "}";
    }
}
