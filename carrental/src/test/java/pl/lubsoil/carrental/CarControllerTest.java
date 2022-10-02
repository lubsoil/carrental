package pl.lubsoil.carrental;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import pl.lubsoil.carrental.model.Car;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(value = "/insert_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = "/clean_database.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class CarControllerTest {
    @LocalServerPort
    private int serverPort;

    @Autowired
    private TestRestTemplate restTemplate;

    private URI createServerAddress() throws URISyntaxException {
        return new URI("http://localhost:" + serverPort + "/cars");
    }

    //TESTY JEDNOSTKOWE
    @Test
    void carChangeBorrowedStatus() {
        Car car = new Car();
        car.changeBorrowed();
        assertEquals(true, car.isBorrowed());
    }

    @Test
    void carChangeCarCode() {
        Car car = new Car(0,"FALSE","",4);
        car.setCarCode("LOL1234");
        assertEquals("LOL1234", car.getCarCode());
    }

    //TESTY INTEGRACYJNE
    @Test
    void shouldReturnAllExistingCars() throws Exception {
        // when:
        RequestEntity<Void> request = RequestEntity.get(createServerAddress()).build();
        ResponseEntity<List<Car>> response = restTemplate.exchange(request, new ParameterizedTypeReference<List<Car>>(){});
        // then:
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void shouldRentNotBorrowedCar() throws Exception {
        // when:
        RequestEntity<Void> request = RequestEntity.get(new URI("http://localhost:" + serverPort + "/cars/rent/1")).build();
        ResponseEntity<?> response = restTemplate.exchange(request, new ParameterizedTypeReference<>(){});
        // then:
        assertTrue(response.getStatusCode().is2xxSuccessful());
    }

    @Test
    void shouldNotRentNotBorrowedCar() throws Exception {
        // when:
        RequestEntity<Void> request = RequestEntity.get(new URI("http://localhost:" + serverPort + "/cars/rent/2")).build();
        ResponseEntity<?> response = restTemplate.exchange(request, new ParameterizedTypeReference<>(){});
        // then:
        assertTrue(response.getStatusCode().is4xxClientError());
    }

    @Test
    void shouldNotUnRentNotBorrowedCar() throws Exception {
        // when:
        RequestEntity<Void> request = RequestEntity.get(new URI("http://localhost:" + serverPort + "/cars/unrent/1")).build();
        ResponseEntity<?> response = restTemplate.exchange(request, new ParameterizedTypeReference<>(){});
        // then:
        assertTrue(response.getStatusCode().is4xxClientError());
    }

    @Test
    void shouldUnRentBorrowedCar() throws Exception {
        // when:
        RequestEntity<Void> request = RequestEntity.get(new URI("http://localhost:" + serverPort + "/cars/unrent/2")).build();
        ResponseEntity<?> response = restTemplate.exchange(request, new ParameterizedTypeReference<>(){});
        // then:
        assertTrue(response.getStatusCode().is2xxSuccessful());
    }

}
