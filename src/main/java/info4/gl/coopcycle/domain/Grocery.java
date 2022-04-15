package info4.gl.coopcycle.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.domain.Persistable;

/**
 * A Grocery.
 */
@Entity
@Table(name = "grocery")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Grocery implements Serializable, Persistable<String> {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @NotNull
    @Column(name = "adress", nullable = false)
    private String adress;

    @Transient
    private boolean isPersisted;

    @JsonIgnoreProperties(value = { "products", "grocery", "userCoop" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Basket basket;

    @JsonIgnoreProperties(value = { "grocery" }, allowSetters = true)
    @OneToOne(mappedBy = "grocery")
    private Payment payment;

    @ManyToOne
    @JsonIgnoreProperties(value = { "groceries", "restaurants", "baskets", "cooperatives" }, allowSetters = true)
    private UserCoop userCoop;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Grocery id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAdress() {
        return this.adress;
    }

    public Grocery adress(String adress) {
        this.setAdress(adress);
        return this;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    @Transient
    @Override
    public boolean isNew() {
        return !this.isPersisted;
    }

    public Grocery setIsPersisted() {
        this.isPersisted = true;
        return this;
    }

    @PostLoad
    @PostPersist
    public void updateEntityState() {
        this.setIsPersisted();
    }

    public Basket getBasket() {
        return this.basket;
    }

    public void setBasket(Basket basket) {
        this.basket = basket;
    }

    public Grocery basket(Basket basket) {
        this.setBasket(basket);
        return this;
    }

    public Payment getPayment() {
        return this.payment;
    }

    public void setPayment(Payment payment) {
        if (this.payment != null) {
            this.payment.setGrocery(null);
        }
        if (payment != null) {
            payment.setGrocery(this);
        }
        this.payment = payment;
    }

    public Grocery payment(Payment payment) {
        this.setPayment(payment);
        return this;
    }

    public UserCoop getUserCoop() {
        return this.userCoop;
    }

    public void setUserCoop(UserCoop userCoop) {
        this.userCoop = userCoop;
    }

    public Grocery userCoop(UserCoop userCoop) {
        this.setUserCoop(userCoop);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Grocery)) {
            return false;
        }
        return id != null && id.equals(((Grocery) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Grocery{" +
            "id=" + getId() +
            ", adress='" + getAdress() + "'" +
            "}";
    }
}
