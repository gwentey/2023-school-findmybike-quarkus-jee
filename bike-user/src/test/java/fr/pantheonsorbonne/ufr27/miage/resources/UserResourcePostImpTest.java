package fr.pantheonsorbonne.ufr27.miage.resources;

import fr.pantheonsorbonne.ufr27.miage.camel.BikeGatewayImpl;
import fr.pantheonsorbonne.ufr27.miage.dao.BikeDAO;
import fr.pantheonsorbonne.ufr27.miage.dao.BookingDAO;
import fr.pantheonsorbonne.ufr27.miage.dao.UserDAO;
import fr.pantheonsorbonne.ufr27.miage.model.Bike;
import fr.pantheonsorbonne.ufr27.miage.model.Booking;
import fr.pantheonsorbonne.ufr27.miage.model.User;
import fr.pantheonsorbonne.ufr27.miage.service.UserService;
import fr.pantheonsorbonne.ufr27.miage.service.UserServiceImpl;
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
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@QuarkusTest
@ExtendWith(MockitoExtension.class)
class UserResourcePostImpTest {
	private RequestSpecification requestSpecification;

	@InjectMock
	UserService userService;

	@InjectMock
	UserDAO userDAO;

	@BeforeEach
	public void setup() {

		requestSpecification = given()
				.auth().basic("anthony", "anthonypass");

	}

	@Test
	void testBookABikeEndpoint() {
		// Simulez les données de retour pour userDAO et userService
		User mockUser = new User();
		mockUser.setId(1L); // Assurez-vous que l'ID correspond à celui utilisé dans le test
		// Configurez mockUser avec d'autres propriétés si nécessaire

		Booking mockBooking = new Booking();
		mockBooking.setBike(new Bike()); // Assurez-vous que le vélo est défini dans la réservation
		mockBooking.setUser(mockUser); // Associez l'utilisateur à la réservation

		when(userDAO.findByUsername("anthony")).thenReturn(mockUser);
		when(userService.bookABike(anyLong(), anyInt())).thenReturn(mockBooking);

		// Configurez le reste du test
		int bikeId = 1; // ou un autre identifiant, selon le scénario de test
		double userPosX = 2.3295;
		double userPosY = 48.7965;

		requestSpecification
				.queryParam("positionX", userPosX)
				.queryParam("positionY", userPosY)
				.when().post("/user/bike/" + bikeId + "/")
				.then()
				.statusCode(200)
				.body("booking.idBooking", equalTo(mockBooking.getIdBooking())); // Assurez-vous que cela correspond à votre réponse attendue
		// Autres assertions selon le besoin

		// Vérifiez que les méthodes simulées ont été appelées
		verify(userDAO).findByUsername("anthony");
		verify(userService).bookABike(anyLong(), eq(bikeId));
	}




}
