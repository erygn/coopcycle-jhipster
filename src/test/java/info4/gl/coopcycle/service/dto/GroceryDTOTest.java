package info4.gl.coopcycle.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import info4.gl.coopcycle.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GroceryDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GroceryDTO.class);
        GroceryDTO groceryDTO1 = new GroceryDTO();
        groceryDTO1.setId("id1");
        GroceryDTO groceryDTO2 = new GroceryDTO();
        assertThat(groceryDTO1).isNotEqualTo(groceryDTO2);
        groceryDTO2.setId(groceryDTO1.getId());
        assertThat(groceryDTO1).isEqualTo(groceryDTO2);
        groceryDTO2.setId("id2");
        assertThat(groceryDTO1).isNotEqualTo(groceryDTO2);
        groceryDTO1.setId(null);
        assertThat(groceryDTO1).isNotEqualTo(groceryDTO2);
    }
}
