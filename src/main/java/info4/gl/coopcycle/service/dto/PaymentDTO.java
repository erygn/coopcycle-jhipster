package info4.gl.coopcycle.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link info4.gl.coopcycle.domain.Payment} entity.
 */
public class PaymentDTO implements Serializable {

    @NotNull
    private String id;

    @NotNull
    @Min(value = 0)
    private Integer amount;

    private GroceryDTO grocery;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public GroceryDTO getGrocery() {
        return grocery;
    }

    public void setGrocery(GroceryDTO grocery) {
        this.grocery = grocery;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaymentDTO)) {
            return false;
        }

        PaymentDTO paymentDTO = (PaymentDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, paymentDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PaymentDTO{" +
            "id='" + getId() + "'" +
            ", amount=" + getAmount() +
            ", grocery=" + getGrocery() +
            "}";
    }
}
