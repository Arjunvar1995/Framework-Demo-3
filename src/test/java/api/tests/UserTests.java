package api.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;
import io.restassured.response.Response;
import api.endpoints.UserEndPoints;
import api.payloads.User;

public class UserTests {
	
	Faker faker;
	User userPayload;
	
	@BeforeClass
	public void prepareData() {
		
		faker=new Faker();
		userPayload=new User();
		
		userPayload.setId(faker.idNumber().hashCode());
		userPayload.setUsername(faker.name().username());
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		userPayload.setPassword(faker.internet().password());
		userPayload.setPhone(faker.phoneNumber().cellPhone());
		
	}
	
	@Test(priority=0)
	public void testCreateUser() {
		
		Response response=UserEndPoints.createUserEP(userPayload);
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(), 200);
		
	}
	
	@Test(priority=1)
	public void testRetrieveUser() {
		
		Response response=UserEndPoints.retrieveUserEP(userPayload.getUsername());
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(), 200);
		
	}
	
	@Test(priority=2)
	public void testUpdateUser() {
		
		userPayload.setEmail(faker.internet().safeEmailAddress());
		
		Response response1=UserEndPoints.updateUserEP(userPayload.getUsername(), userPayload);
		response1.then().log().all();
		Assert.assertEquals(response1.getStatusCode(), 200);
		
		Response response2=UserEndPoints.retrieveUserEP(userPayload.getUsername());
		response2.then().log().all();
	}
	
	
	@Test(priority=3)
	public void testDeleteUser() {
		
		Response response1=UserEndPoints.deleteUserEP(userPayload.getUsername());
		response1.then().log().all();
		Assert.assertEquals(response1.getStatusCode(), 200);
		
		Response response2=UserEndPoints.retrieveUserEP(userPayload.getUsername());
		response2.then().log().all();
		
	}
	
	

}
