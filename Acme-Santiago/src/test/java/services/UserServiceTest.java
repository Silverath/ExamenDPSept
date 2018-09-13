
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class UserServiceTest extends AbstractTest {

	//---------------------------------------- Service under test ----------------------------------------

	@Autowired
	private UserService			userService;

	//--------------------------------------- Supporting services ----------------------------------------

	@Autowired
	public ConfigurationService	configurationService;


	//----------------------------------------------- Test -----------------------------------------------

	/******************************** -Positive- ******************************/

	//	Requeriment 3.1 An actor who is not authenticated must be able to:
	//	Register to the system as a user.
	@Test
	public void testRegisterUser() {
		User u;

		u = this.userService.create();
		u.setName("Ignacio");
		u.setSurname("Guti�rrez");
		u.setEmail("igna@gmail.com");
		u.setPhone("654247955");
		u.setAddress("Calle monz�n");

		this.userService.save(u);
	}

	@Test
	public void testNotRegisteredListUsers() {
		super.authenticate(null);

		this.userService.findAll();
		this.userService.findOne(this.getEntityId("user1"));

		super.unauthenticate();

	}

	//Edit profile of the actor
	@Test
	public void testEditProfile() {
		super.authenticate("User1");

		User u;

		u = this.userService.findByPrincipal();
		u.setPhone("635658455");
		u.setEmail("emailedited@gmail.com");
		u.setAddress("Direcci�n editada");

		this.userService.save(u);

	}

	/************************* -Negative- ************************************/

	@Test(expected = javax.validation.ConstraintViolationException.class)
	public void testRegisterUserWithInvalidName() {
		User u;

		u = this.userService.create();
		u.setName("");
		u.setSurname("L�pez");
		u.setEmail("edu@gmail.com");
		u.setPhone("654455688");
		u.setAddress("Calle ancha");

		this.userService.save(u);
		this.userService.flush();
	}

	@Test(expected = javax.validation.ConstraintViolationException.class)
	public void testRegisterUserWithInvalidSurname() {
		User u;

		u = this.userService.create();
		u.setName("Antonio");
		u.setSurname("");
		u.setEmail("edu@gmail.com");
		u.setPhone("654455688");
		u.setAddress("Calle ancha");

		this.userService.save(u);
		this.userService.flush();
	}

	@Test(expected = javax.validation.ConstraintViolationException.class)
	public void testRegisterUserWithInvalidEmail() {
		User u;

		u = this.userService.create();
		u.setName("Antonio");
		u.setSurname("Luna");
		u.setEmail("edugmail.com");
		u.setPhone("654455688");
		u.setAddress("Calle ancha");

		this.userService.save(u);
		this.userService.flush();
	}

	//Edit other user perfil
	@Test
	public void testEditOtherProfile() {
		super.authenticate("user1");

		User user;
		Integer userId;

		userId = super.getEntityId("user2");
		user = this.userService.findOne(userId);

		user.setPhone("678123456");
		user.setEmail("newemail@gmail.com");
		user.setAddress("Calle maria de la o");

		this.userService.save(user);

		super.unauthenticate();

	}

	//Test cannot delete his user
	@Test(expected = org.springframework.dao.DataIntegrityViolationException.class)
	public void testDelete() {
		int userId;
		User user;

		userId = super.getEntityId("user1");

		super.authenticate("user1");
		user = this.userService.findOne(userId);

		this.userService.delete(user);
		this.userService.flush();

		super.unauthenticate();

	}

}
