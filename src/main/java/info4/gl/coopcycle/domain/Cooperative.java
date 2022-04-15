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
 * A Cooperative.
 */
@Entity
@Table(name = "cooperative")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Cooperative implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @ManyToMany(mappedBy = "cooperatives")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "groceries", "restaurants", "baskets", "cooperatives" }, allowSetters = true)
    private Set<UserCoop> userCoops = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Cooperative id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Cooperative name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<UserCoop> getUserCoops() {
        return this.userCoops;
    }

    public void setUserCoops(Set<UserCoop> userCoops) {
        if (this.userCoops != null) {
            this.userCoops.forEach(i -> i.removeCooperative(this));
        }
        if (userCoops != null) {
            userCoops.forEach(i -> i.addCooperative(this));
        }
        this.userCoops = userCoops;
    }

    public Cooperative userCoops(Set<UserCoop> userCoops) {
        this.setUserCoops(userCoops);
        return this;
    }

    public Cooperative addUserCoop(UserCoop userCoop) {
        this.userCoops.add(userCoop);
        userCoop.getCooperatives().add(this);
        return this;
    }

    public Cooperative removeUserCoop(UserCoop userCoop) {
        this.userCoops.remove(userCoop);
        userCoop.getCooperatives().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Cooperative)) {
            return false;
        }
        return id != null && id.equals(((Cooperative) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Cooperative{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
