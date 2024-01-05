package fr.pantheonsorbonne.ufr27.miage.resources;

import fr.pantheonsorbonne.ufr27.miage.camel.BikeGatewayImpl;
import fr.pantheonsorbonne.ufr27.miage.model.Bike;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.junit.jupiter.MockitoExtension;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.lenient;

@QuarkusTest
@ExtendWith(MockitoExtension.class)
class UserResourceImplTest {
	@InjectMock
	BikeGatewayImpl bikeGateway;
	@BeforeEach
	public void setup() {

		Bike expectedBike = new Bike();
		expectedBike.setIdBike(1);
		expectedBike.setPositionX(2.29435);
		expectedBike.setPositionY(48.858844);
		expectedBike.setBatterie(100);
		expectedBike.setManagerId(1);

		lenient().when(bikeGateway.nextBikeAvailableByPosition(2.29435, 48.858844)).thenReturn(expectedBike);

	}

	@Test
	void testBikeAvailableEndpoint() {
		given()
				.auth().basic("anthony", "anthonypass")
				.when().get("/user/bike/available/2.29435/48.858844")
				.then()
				.statusCode(200)
				.body("idBike", equalTo(1))
				.body("positionX", equalTo(2.29435F))
				.body("positionY", equalTo(48.858844F))
				.body("batterie", equalTo(100))
				.body("managerId", equalTo(1));
	}
}
