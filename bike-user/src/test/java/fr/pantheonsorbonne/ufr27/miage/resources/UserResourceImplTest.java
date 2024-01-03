package fr.pantheonsorbonne.ufr27.miage.resources;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class UserResourceImplTest {

	private RequestSpecification requestSpecification;

	@BeforeEach
	void setup() {
		requestSpecification = given()
				.auth().basic("anthony", "anthonypass")
				.baseUri("http://localhost:8082/user");
	}

	@Test
	void testBikeAvailableEndpoint() {
		double positionX = 2.2932196427317164;
		double positionY = 48.85844443869412;

		requestSpecification
				.pathParam("positionX", positionX)
				.pathParam("positionY", positionY)
				.when()
				.get("/bike/available/{positionX}/{positionY}")
				.then()
				.statusCode(200)
				.body("idBike", equalTo(1))
				.body("positionX", equalTo(2.29435f))
				.body("positionY", equalTo(48.858844f))
				.body("batterie", equalTo(100))
				.body("managerId", equalTo(1));
	}

	@ParameterizedTest
	@CsvSource({"4, 2.295f, 48.8738f, 100, 2"})
	void getABikeByIdEndpoint(int bikeId, float expectedPosX, float expectedPosY, int expectedBattery, int expectedManagerId) {
		requestSpecification
				.pathParam("bikeId", bikeId)
				.when()
				.get("/bike/{bikeId}")
				.then()
				.statusCode(200)
				.body("idBike", equalTo(bikeId))
				.body("positionX", equalTo(expectedPosX))
				.body("positionY", equalTo(expectedPosY))
				.body("batterie", equalTo(expectedBattery))
				.body("managerId", equalTo(expectedManagerId));
	}
}
