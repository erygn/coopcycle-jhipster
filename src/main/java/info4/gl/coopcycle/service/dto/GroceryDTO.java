package info4.gl.coopcycle.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link info4.gl.coopcycle.domain.Grocery} entity.
 */
public class GroceryDTO implements Serializable {

    @NotNull
    private String id;

    @NotNull
    private String adress;

    private BasketDTO basket;

    private UserCoopDTO userCoop;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public BasketDTO getBasket() {
        return basket;
    }

    public void setBasket(BasketDTO basket) {
        this.basket = basket;
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
        if (!(o instanceof GroceryDTO)) {
            return false;
        }

        GroceryDTO groceryDTO = (GroceryDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, groceryDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GroceryDTO{" +
            "id='" + getId() + "'" +
            ", adress='" + getAdress() + "'" +
            ", basket=" + getBasket() +
            ", userCoop=" + getUserCoop() +
            "}";
    }
}
