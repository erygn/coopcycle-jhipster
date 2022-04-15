package info4.gl.coopcycle.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import info4.gl.coopcycle.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BasketDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BasketDTO.class);
        BasketDTO basketDTO1 = new BasketDTO();
        basketDTO1.setId("id1");
        BasketDTO basketDTO2 = new BasketDTO();
        assertThat(basketDTO1).isNotEqualTo(basketDTO2);
        basketDTO2.setId(basketDTO1.getId());
        assertThat(basketDTO1).isEqualTo(basketDTO2);
        basketDTO2.setId("id2");
        assertThat(basketDTO1).isNotEqualTo(basketDTO2);
        basketDTO1.setId(null);
        assertThat(basketDTO1).isNotEqualTo(basketDTO2);
    }
}
