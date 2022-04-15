package info4.gl.coopcycle.domain;

import static org.assertj.core.api.Assertions.assertThat;

import info4.gl.coopcycle.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GroceryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Grocery.class);
        Grocery grocery1 = new Grocery();
        grocery1.setId("id1");
        Grocery grocery2 = new Grocery();
        grocery2.setId(grocery1.getId());
        assertThat(grocery1).isEqualTo(grocery2);
        grocery2.setId("id2");
        assertThat(grocery1).isNotEqualTo(grocery2);
        grocery1.setId(null);
        assertThat(grocery1).isNotEqualTo(grocery2);
    }
}
