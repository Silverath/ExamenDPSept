
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
import domain.Hike;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class HikeServiceTest extends AbstractTest {

	//---------------------------------------- Service under test ----------------------------------------

	@Autowired
	private UserService			userService;

	@Autowired
	private HikeService			hikeService;

	//--------------------------------------- Supporting services ----------------------------------------

	@Autowired
	public ConfigurationService	configurationService;

	@PersistenceContext
	EntityManager				entityManager;


	//----------------------------------------------- Test -----------------------------------------------

	/******************************** -Positive- ******************************/

	@Test
	public void driverCreateAndSave() {
		final Object testingData[][] = {
			{
				//Se crea un hike correctamenre estando logeado como user1
				"user1", "name hike test", "description test", 100.5, "origin test", "destiny test", "EASY", null

			}, {
				//Se crea un hike incorrectamente ya que estamos logeados como admin
				"admin", "name hike test", "description test", 500.0, "origin test", "destiny test", "EASY", ClassCastException.class
			}, {
				//Se crea un hike incorrectamente ya que metemos el titulo vacio
				"user1", "", "description test", 600.2, "origin test", "destiny test", "EASY", javax.validation.ConstraintViolationException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateCreateAndSave((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Double) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(Class<?>) testingData[i][7]);
	}
	private void templateCreateAndSave(final String username, final String name, final String description, final Double length, final String originCity, final String destinyCity, final String difficulty, final Class<?> expected) {
		Class<?> caught;
		Hike hike;
		caught = null;

		try {
			super.authenticate(username);
			hike = this.hikeService.create();

			hike.setName(name);
			hike.setLength(length);
			hike.setDescription(description);
			hike.setOriginCity(originCity);
			hike.setDestinyCity(destinyCity);
			hike.setDifficulty(difficulty);
			hike = this.hikeService.save(hike);
			this.hikeService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
			//Se borra la cache para que no salte siempre el error del primer objeto que ha fallado en el test
			this.entityManager.clear();
		}

		this.checkExceptions(expected, caught);

		super.unauthenticate();
	}

}
