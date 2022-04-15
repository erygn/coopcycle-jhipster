package info4.gl.coopcycle.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import info4.gl.coopcycle.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class UserCoopDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserCoopDTO.class);
        UserCoopDTO userCoopDTO1 = new UserCoopDTO();
        userCoopDTO1.setId(1L);
        UserCoopDTO userCoopDTO2 = new UserCoopDTO();
        assertThat(userCoopDTO1).isNotEqualTo(userCoopDTO2);
        userCoopDTO2.setId(userCoopDTO1.getId());
        assertThat(userCoopDTO1).isEqualTo(userCoopDTO2);
        userCoopDTO2.setId(2L);
        assertThat(userCoopDTO1).isNotEqualTo(userCoopDTO2);
        userCoopDTO1.setId(null);
        assertThat(userCoopDTO1).isNotEqualTo(userCoopDTO2);
    }
}
