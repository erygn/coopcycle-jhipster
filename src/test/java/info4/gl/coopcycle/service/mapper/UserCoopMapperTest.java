package info4.gl.coopcycle.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserCoopMapperTest {

    private UserCoopMapper userCoopMapper;

    @BeforeEach
    public void setUp() {
        userCoopMapper = new UserCoopMapperImpl();
    }
}
