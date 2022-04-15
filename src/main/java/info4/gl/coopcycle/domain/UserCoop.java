package info4.gl.coopcycle.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A UserCoop.
 */
@Entity
@Table(name = "user_coop")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UserCoop implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "userCoop")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "basket", "payment", "userCoop" }, allowSetters = true)
    private Set<Grocery> groceries = new HashSet<>();

    @OneToMany(mappedBy = "userCoop")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "products", "userCoop" }, allowSetters = true)
    private Set<Restaurant> restaurants = new HashSet<>();

    @OneToMany(mappedBy = "userCoop")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "products", "grocery", "userCoop" }, allowSetters = true)
    private Set<Basket> baskets = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "rel_user_coop__cooperative",
        joinColumns = @JoinColumn(name = "user_coop_id"),
        inverseJoinColumns = @JoinColumn(name = "cooperative_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "userCoops" }, allowSetters = true)
    private Set<Cooperative> cooperatives = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public UserCoop id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public UserCoop name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Grocery> getGroceries() {
        return this.groceries;
    }

    public void setGroceries(Set<Grocery> groceries) {
        if (this.groceries != null) {
            this.groceries.forEach(i -> i.setUserCoop(null));
        }
        if (groceries != null) {
            groceries.forEach(i -> i.setUserCoop(this));
        }
        this.groceries = groceries;
    }

    public UserCoop groceries(Set<Grocery> groceries) {
        this.setGroceries(groceries);
        return this;
    }

    public UserCoop addGrocery(Grocery grocery) {
        this.groceries.add(grocery);
        grocery.setUserCoop(this);
        return this;
    }

    public UserCoop removeGrocery(Grocery grocery) {
        this.groceries.remove(grocery);
        grocery.setUserCoop(null);
        return this;
    }

    public Set<Restaurant> getRestaurants() {
        return this.restaurants;
    }

    public void setRestaurants(Set<Restaurant> restaurants) {
        if (this.restaurants != null) {
            this.restaurants.forEach(i -> i.setUserCoop(null));
        }
        if (restaurants != null) {
            restaurants.forEach(i -> i.setUserCoop(this));
        }
        this.restaurants = restaurants;
    }

    public UserCoop restaurants(Set<Restaurant> restaurants) {
        this.setRestaurants(restaurants);
        return this;
    }

    public UserCoop addRestaurant(Restaurant restaurant) {
        this.restaurants.add(restaurant);
        restaurant.setUserCoop(this);
        return this;
    }

    public UserCoop removeRestaurant(Restaurant restaurant) {
        this.restaurants.remove(restaurant);
        restaurant.setUserCoop(null);
        return this;
    }

    public Set<Basket> getBaskets() {
        return this.baskets;
    }

    public void setBaskets(Set<Basket> baskets) {
        if (this.baskets != null) {
            this.baskets.forEach(i -> i.setUserCoop(null));
        }
        if (baskets != null) {
            baskets.forEach(i -> i.setUserCoop(this));
        }
        this.baskets = baskets;
    }

    public UserCoop baskets(Set<Basket> baskets) {
        this.setBaskets(baskets);
        return this;
    }

    public UserCoop addBasket(Basket basket) {
        this.baskets.add(basket);
        basket.setUserCoop(this);
        return this;
    }

    public UserCoop removeBasket(Basket basket) {
        this.baskets.remove(basket);
        basket.setUserCoop(null);
        return this;
    }

    public Set<Cooperative> getCooperatives() {
        return this.cooperatives;
    }

    public void setCooperatives(Set<Cooperative> cooperatives) {
        this.cooperatives = cooperatives;
    }

    public UserCoop cooperatives(Set<Cooperative> cooperatives) {
        this.setCooperatives(cooperatives);
        return this;
    }

    public UserCoop addCooperative(Cooperative cooperative) {
        this.cooperatives.add(cooperative);
        cooperative.getUserCoops().add(this);
        return this;
    }

    public UserCoop removeCooperative(Cooperative cooperative) {
        this.cooperatives.remove(cooperative);
        cooperative.getUserCoops().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserCoop)) {
            return false;
        }
        return id != null && id.equals(((UserCoop) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserCoop{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
