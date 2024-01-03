package fr.pantheonsorbonne.ufr27.miage.resources;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserRessourceImplTest {

	@BeforeEach
	public void setup() {

	}

	@Test
	public void testBikeAvailableEndpoint() {
		double positionX = 2.2932196427317164;
		double positionY = 48.85844443869412;

		given()
				.auth().basic("anthony", "anthonypass")
				.pathParam("positionX", positionX)
				.pathParam("positionY", positionY)
				.when()
				.get("http://localhost:8082/user/bike/available/{positionX}/{positionY}")
				.then()
				.statusCode(200)
				.body("idBike", equalTo(1))
				.body("positionX", equalTo(2.29435f))
				.body("positionY", equalTo(48.858844f))
				.body("batterie", equalTo(100))
				.body("managerId", equalTo(1));
	}


}