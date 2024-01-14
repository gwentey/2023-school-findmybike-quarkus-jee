package fr.pantheonsorbonne.ufr27.miage.resources;

import fr.pantheonsorbonne.ufr27.miage.dao.BikeDAO;
import fr.pantheonsorbonne.ufr27.miage.model.Bike;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.specification.RequestSpecification;
import jakarta.ws.rs.core.MediaType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static io.restassured.RestAssured.given;
import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.equalTo;

@QuarkusTest
@ExtendWith(MockitoExtension.class)
class ManagerResourceTest {

	@InjectMock
	BikeDAO bikeDAO;
	private RequestSpecification requestSpecification;

	@BeforeEach
	public void setup() {
		requestSpecification = given()
				.auth().basic("anthony", "anthonypass");


	}

	@Test
	void testGetABikeByIdEndpoint() {
		Bike expectedBike = new Bike();
		expectedBike.setIdBike(1);
		expectedBike.setPositionX(2.29435);
		expectedBike.setPositionY(48.858844);
		expectedBike.setBatterie(100);
		expectedBike.setManagerId(1);

		lenient().when(bikeDAO.findById(1)).thenReturn(expectedBike);

		requestSpecification
				.log().all()
				.when().get("/manager/bike/1")
				.then()
				.log().all()
				.statusCode(200)
				.body("idBike", equalTo(1))
				.body("positionX", equalTo(2.29435F))
				.body("positionY", equalTo(48.858844F))
				.body("batterie", equalTo(100))
				.body("managerId", equalTo(1));
	}

	@Test
	void testCreateBikeEndpoint() {
		Bike expectedBike = new Bike();
		expectedBike.setIdBike(1);
		expectedBike.setPositionX(2.29435);
		expectedBike.setPositionY(48.858844);
		expectedBike.setBatterie(100);
		expectedBike.setManagerId(1);

		lenient().when((bikeDAO.createBike(expectedBike))).thenReturn(expectedBike);

		requestSpecification
				.contentType(MediaType.APPLICATION_JSON)
				.body(expectedBike)
				.then()
				.statusCode(200)
				.body("idBike", equalTo(1));

	}

}
