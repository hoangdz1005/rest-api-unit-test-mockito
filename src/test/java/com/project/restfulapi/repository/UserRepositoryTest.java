package com.project.restfulapi.repository;

import com.project.restfulapi.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.Optional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void testFindById() {
        User userToSave = new User("devfullstack", "12345678", "devfullstack@gmail.com");
        User savedUser = userRepository.save(userToSave);
        Optional<User> result = userRepository.findById(savedUser.getId());
        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(savedUser.getId());
        assertThat(result.get().getUsername()).isEqualTo("devfullstack");
        assertThat(result.get().getEmail()).isEqualTo("devfullstack@gmail.com");
    }

    @Test
    void testSave() {
        User userToSave = new User("devfullstack", "12345678", "devfullstack@gmail.com");
        User savedUser = userRepository.save(userToSave);
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getId()).isNotNull();
        assertThat(savedUser.getUsername()).isEqualTo("devfullstack");
        assertThat(savedUser.getPassword()).isEqualTo("12345678");
        assertThat(savedUser.getEmail()).isEqualTo("devfullstack@gmail.com");
    }

    @Test
    void testFindByUsername() {
        User userToSave = new User("devfullstack", "12345678", "devfullstack@gmail.com");
        User savedUser = userRepository.save(userToSave);
        Optional<User> result = userRepository.findByUsername("devfullstack");
        assertThat(result).isPresent();
        assertThat(result.get().getUsername()).isEqualTo("devfullstack");
        assertThat(result.get().getEmail()).isEqualTo("devfullstack@gmail.com");
    }
}