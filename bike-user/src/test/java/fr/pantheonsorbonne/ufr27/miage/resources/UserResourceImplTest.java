package fr.pantheonsorbonne.ufr27.miage.resources;

import fr.pantheonsorbonne.ufr27.miage.camel.BikeGatewayImpl;
import fr.pantheonsorbonne.ufr27.miage.model.Bike;;
import io.quarkus.test.InjectMock;
import io.quarkus.test.Mock;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;

@QuarkusTest
@ExtendWith(MockitoExtension.class)
class UserResourceImplTest {

	@InjectMock
	BikeGatewayImpl bikeGateway;

	private RequestSpecification requestSpecification;

	@BeforeEach
	public void setup() {

		requestSpecification = given()
				.auth().basic("anthony", "anthonypass");

	}

	@Test
	void testBikeAvailableEndpoint() {

		Bike expectedBike = new Bike();
		expectedBike.setIdBike(1);
		expectedBike.setPositionX(2.29435);
		expectedBike.setPositionY(48.858844);
		expectedBike.setBatterie(100);
		expectedBike.setManagerId(1);

		String expectedItineraireUrl = "https://www.google.fr/maps/dir/48.858844,2.29435/48.858844,2.29435/data=!3m1!4b1!4m2!4m1!3e2?entry=ttu";

		lenient().when(bikeGateway.nextBikeAvailableByPosition(2.29435, 48.858844)).thenReturn(expectedBike);

		double positionX = 2.29435;
		double positionY = 48.858844;

		requestSpecification
				.queryParam("positionX", positionX)
				.queryParam("positionY", positionY)
				.when().get("/user/bike/available")
				.then()
				.statusCode(200)
				.body("bike.idBike", equalTo(1))
				.body("bike.positionX", equalTo(2.29435F))
				.body("bike.positionY", equalTo(48.858844F))
				.body("bike.batterie", equalTo(100))
				.body("bike.managerId", equalTo(1))
				.body("itineraireUrl", equalTo(expectedItineraireUrl));
	}


	@Test
	void testGetABikeByIdEndpoint() {
		Bike expectedBike = new Bike();
		expectedBike.setIdBike(1);
		expectedBike.setPositionX(2.29435);
		expectedBike.setPositionY(48.858844);
		expectedBike.setBatterie(100);
		expectedBike.setManagerId(1);

		String expectedMapsUrl = "https://www.google.fr/maps/dir/48.858844,2.29435";
		lenient().when(bikeGateway.getABikeById(1)).thenReturn(expectedBike);

		requestSpecification
				.when().get("/user/bike/1")
				.then()
				.statusCode(200)
				.body("bike.idBike", equalTo(1))
				.body("bike.positionX", equalTo(2.29435F))
				.body("bike.positionY", equalTo(48.858844F))
				.body("bike.batterie", equalTo(100))
				.body("bike.managerId", equalTo(1))
				.body("mapsUrl", equalTo(expectedMapsUrl));
	}

}
