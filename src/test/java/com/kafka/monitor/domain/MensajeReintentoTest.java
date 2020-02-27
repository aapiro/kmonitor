package com.kafka.monitor.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.kafka.monitor.web.rest.TestUtil;

public class MensajeReintentoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MensajeReintento.class);
        MensajeReintento mensajeReintento1 = new MensajeReintento();
        mensajeReintento1.setId(1L);
        MensajeReintento mensajeReintento2 = new MensajeReintento();
        mensajeReintento2.setId(mensajeReintento1.getId());
        assertThat(mensajeReintento1).isEqualTo(mensajeReintento2);
        mensajeReintento2.setId(2L);
        assertThat(mensajeReintento1).isNotEqualTo(mensajeReintento2);
        mensajeReintento1.setId(null);
        assertThat(mensajeReintento1).isNotEqualTo(mensajeReintento2);
    }
}
