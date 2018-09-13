
package services;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Route;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class RouteServiceTest extends AbstractTest {

	//---------------------------------------- Service under test ----------------------------------------

	@Autowired
	private UserService			userService;

	@Autowired
	private RouteService		routeService;

	//--------------------------------------- Supporting services ----------------------------------------

	@Autowired
	public ConfigurationService	configurationService;

	@PersistenceContext
	EntityManager				entityManager;


	//----------------------------------------------- Test -----------------------------------------------

	/******************************** -Positive- ******************************/

	//	Functional requirement 5.1 An actor who is authenticated as a user must be able to:
	// Manage his or her routes, which includes creating, editing, deleting, and listing them.
	@Test
	public void driverCreateAndSave() {
		final Object testingData[][] = {
			{
				//Se crea un route correctamenre estando logeado como user1
				"user1", "name route test", 100.5, "desciption test", null

			}, {
				//Se crea un route incorrectamente ya que estamos logeados como admin
				"admin", "name route test", 500.0, "description test", ClassCastException.class
			}, {
				//Se crea un route incorrectamente ya que metemos el titulo vacio
				"user1", "", 600.2, "description test", javax.validation.ConstraintViolationException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateCreateAndSave((String) testingData[i][0], (String) testingData[i][1], (Double) testingData[i][2], (String) testingData[i][3], (Class<?>) testingData[i][4]);
	}
	private void templateCreateAndSave(final String username, final String name, final Double length, final String description, final Class<?> expected) {
		Class<?> caught;
		Route route;
		caught = null;

		try {
			super.authenticate(username);
			route = this.routeService.create();

			route.setName(name);
			route.setLength(length);
			route.setDescription(description);
			route = this.routeService.save(route);
			this.routeService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
			//Se borra la cache para que no salte siempre el error del primer objeto que ha fallado en el test
			this.entityManager.clear();
		}

		this.checkExceptions(expected, caught);

		super.unauthenticate();
	}

	//	Functional requirement 5.1 An actor who is authenticated as a user must be able to:
	// Manage his or her routes, which includes creating, editing, deleting, and listing them.
	@Test
	public void driveRemoveRouteUser() {

		final Object testingData[][] = {
			//user remove route, positive case
			{
				"user1", "route1", null
			},
			//admin can't remove an route. Negative case
			{
				"admin", "route1", IllegalArgumentException.class
			},
			//user doesn´t exist remove an route. Negative case
			{
				"sakjdsadj", "route2", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateRemoveRouteUser((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	public void templateRemoveRouteUser(final String username, final String routeEntity, final Class<?> expected) {

		Class<?> caught;

		caught = null;

		try {
			super.authenticate(username);
			this.routeService.delete(super.getEntityId(routeEntity));
			this.unauthenticate();
			this.routeService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
			this.entityManager.clear();
		}

		this.checkExceptions(expected, caught);

	}

}
