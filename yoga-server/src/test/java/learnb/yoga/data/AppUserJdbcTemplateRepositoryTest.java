package learnb.yoga.data;

import learnb.yoga.models.AppUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import static learnb.yoga.data.TestHelper.makeUser;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class AppUserJdbcTemplateRepositoryTest {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    AppUserJdbcTemplateRepository repository;

    @BeforeEach
    void setup() {jdbcTemplate.execute("call set_known_good_state();"); }

    @Test
    void shouldFindById_2() {

        AppUser expected = makeUser(2);

        AppUser actual = repository.findById(2);
        assertEquals(expected,actual);
    }

    @Test
    void findByEmail() {

       AppUser expected = makeUser(5);
AppUser actual = repository.findByEmail("fn.ln5@email.com");
assertEquals(expected,actual);
    }
//
//    @Test
//    void findByName() {
//    }
//
    @Test
    void shouldAdd() {

        assertNull(repository.findById(8));

        AppUser appUser = makeUser(8);
        appUser.setAppUserId(0);
        repository.add(appUser);

        assertEquals(makeUser(8), repository.findById(8));




    }
//
//    @Test
//    void update() {
//    }
//
//    @Test
//    void deleteById() {
//    }
}