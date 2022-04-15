package info4.gl.coopcycle.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link info4.gl.coopcycle.domain.Basket} entity.
 */
public class BasketDTO implements Serializable {

    @NotNull
    private String id;

    private UserCoopDTO userCoop;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UserCoopDTO getUserCoop() {
        return userCoop;
    }

    public void setUserCoop(UserCoopDTO userCoop) {
        this.userCoop = userCoop;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BasketDTO)) {
            return false;
        }

        BasketDTO basketDTO = (BasketDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, basketDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BasketDTO{" +
            "id='" + getId() + "'" +
            ", userCoop=" + getUserCoop() +
            "}";
    }
}
