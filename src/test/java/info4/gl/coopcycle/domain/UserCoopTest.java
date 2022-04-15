package info4.gl.coopcycle.domain;

import static org.assertj.core.api.Assertions.assertThat;

import info4.gl.coopcycle.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class UserCoopTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserCoop.class);
        UserCoop userCoop1 = new UserCoop();
        userCoop1.setId(1L);
        UserCoop userCoop2 = new UserCoop();
        userCoop2.setId(userCoop1.getId());
        assertThat(userCoop1).isEqualTo(userCoop2);
        userCoop2.setId(2L);
        assertThat(userCoop1).isNotEqualTo(userCoop2);
        userCoop1.setId(null);
        assertThat(userCoop1).isNotEqualTo(userCoop2);
    }
}
