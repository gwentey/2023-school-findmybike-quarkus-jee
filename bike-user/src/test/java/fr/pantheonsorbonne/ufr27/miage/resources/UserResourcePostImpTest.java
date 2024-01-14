package fr.pantheonsorbonne.ufr27.miage.resources;

import fr.pantheonsorbonne.ufr27.miage.camel.BikeGatewayImpl;
import fr.pantheonsorbonne.ufr27.miage.dao.BikeDAO;
import fr.pantheonsorbonne.ufr27.miage.dao.BookingDAO;
import fr.pantheonsorbonne.ufr27.miage.dao.UserDAO;
import fr.pantheonsorbonne.ufr27.miage.exception.BikeAlreadyBookedException;
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
	void testBookABikeEndpoint() throws BikeAlreadyBookedException {
		User mockUser = new User();
		mockUser.setId(1L); //

		Booking mockBooking = new Booking();
		mockBooking.setBike(new Bike());
		mockBooking.setUser(mockUser);

		when(userDAO.findByUsername("anthony")).thenReturn(mockUser);
		when(userService.bookABike(anyLong(), anyInt())).thenReturn(mockBooking);

		int bikeId = 1;
		double userPosX = 2.3295;
		double userPosY = 48.7965;

		requestSpecification
				.queryParam("positionX", userPosX)
				.queryParam("positionY", userPosY)
				.when().post("/user/bike/" + bikeId + "/")
				.then()
				.statusCode(200)
				.body("booking.idBooking", equalTo(mockBooking.getIdBooking()));

		verify(userDAO).findByUsername("anthony");
		verify(userService).bookABike(anyLong(), eq(bikeId));
	}




}
