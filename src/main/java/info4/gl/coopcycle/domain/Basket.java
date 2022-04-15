package info4.gl.coopcycle.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.domain.Persistable;

/**
 * A Basket.
 */
@Entity
@Table(name = "basket")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Basket implements Serializable, Persistable<String> {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Transient
    private boolean isPersisted;

    @OneToMany(mappedBy = "basket")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "basket", "restaurant" }, allowSetters = true)
    private Set<Product> products = new HashSet<>();

    @JsonIgnoreProperties(value = { "basket", "payment", "userCoop" }, allowSetters = true)
    @OneToOne(mappedBy = "basket")
    private Grocery grocery;

    @ManyToOne
    @JsonIgnoreProperties(value = { "groceries", "restaurants", "baskets", "cooperatives" }, allowSetters = true)
    private UserCoop userCoop;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Basket id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Transient
    @Override
    public boolean isNew() {
        return !this.isPersisted;
    }

    public Basket setIsPersisted() {
        this.isPersisted = true;
        return this;
    }

    @PostLoad
    @PostPersist
    public void updateEntityState() {
        this.setIsPersisted();
    }

    public Set<Product> getProducts() {
        return this.products;
    }

    public void setProducts(Set<Product> products) {
        if (this.products != null) {
            this.products.forEach(i -> i.setBasket(null));
        }
        if (products != null) {
            products.forEach(i -> i.setBasket(this));
        }
        this.products = products;
    }

    public Basket products(Set<Product> products) {
        this.setProducts(products);
        return this;
    }

    public Basket addProduct(Product product) {
        this.products.add(product);
        product.setBasket(this);
        return this;
    }

    public Basket removeProduct(Product product) {
        this.products.remove(product);
        product.setBasket(null);
        return this;
    }

    public Grocery getGrocery() {
        return this.grocery;
    }

    public void setGrocery(Grocery grocery) {
        if (this.grocery != null) {
            this.grocery.setBasket(null);
        }
        if (grocery != null) {
            grocery.setBasket(this);
        }
        this.grocery = grocery;
    }

    public Basket grocery(Grocery grocery) {
        this.setGrocery(grocery);
        return this;
    }

    public UserCoop getUserCoop() {
        return this.userCoop;
    }

    public void setUserCoop(UserCoop userCoop) {
        this.userCoop = userCoop;
    }

    public Basket userCoop(UserCoop userCoop) {
        this.setUserCoop(userCoop);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Basket)) {
            return false;
        }
        return id != null && id.equals(((Basket) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Basket{" +
            "id=" + getId() +
            "}";
    }
}
