package info4.gl.coopcycle.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link info4.gl.coopcycle.domain.UserCoop} entity.
 */
public class UserCoopDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private Set<CooperativeDTO> cooperatives = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<CooperativeDTO> getCooperatives() {
        return cooperatives;
    }

    public void setCooperatives(Set<CooperativeDTO> cooperatives) {
        this.cooperatives = cooperatives;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserCoopDTO)) {
            return false;
        }

        UserCoopDTO userCoopDTO = (UserCoopDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, userCoopDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserCoopDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", cooperatives=" + getCooperatives() +
            "}";
    }
}
