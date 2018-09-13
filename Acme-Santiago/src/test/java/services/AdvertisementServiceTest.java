
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
import domain.Advertisement;
import domain.CreditCard;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class AdvertisementServiceTest extends AbstractTest {

	@Autowired
	public AdvertisementService	advertisementService;

	//---------------------------------------- Service under test ----------------------------------------
	@PersistenceContext
	public EntityManager		entityManager;


	@Test
	public void CreateTestPositive() {
		this.authenticate("agent1");
		final Advertisement a = this.advertisementService.create();
		a.setTitle("titulo test");
		a.setBanner("https://www.test.jpg");
		a.setTargetPage("https://www.targetTest.jpg");
		final CreditCard cred = new CreditCard();
		cred.setHolderName("Lidia");
		cred.setBrandName("VISA");
		cred.setNumber("4353102989904411");
		cred.setExpirationMonth(4);
		cred.setExpirationYear(2019);
		cred.setCVV(672);
		a.setCreditCard(cred);
		this.advertisementService.save(a);

	}

	@Test(expected = IllegalArgumentException.class)
	public void CreateTestNegative() {
		this.authenticate("user1");
		final Advertisement a = this.advertisementService.create();
		a.setTitle("titulo test");
		a.setBanner("https://www.test.jpg");
		a.setTargetPage("https://www.targetTest.jpg");
		final CreditCard cred = new CreditCard();
		cred.setHolderName("Lidia");
		cred.setBrandName("VISA");
		cred.setNumber("4353102989904411");
		cred.setExpirationMonth(4);
		cred.setExpirationYear(2019);
		cred.setCVV(672);
		a.setCreditCard(cred);
		this.advertisementService.save(a);

		this.advertisementService.flush();

	}

	@Test
	public void driverDelete() {
		final Object testingData[][] = {
			{
				//Se elimina el advertisement1 correctamente
				"admin", "advertisement1", null
			}, {
				//Se elimina el advertisement2 correctamente
				"admin", "advertisement2", null
			}, {
				//Se elimina el advertisement3 correctamente
				"admin", "advertisement3", null
			}, {
				//Se elimina el advertisement4 correctamente
				"admin", "advertisement4", null
			}, {
				//Se intenta eliminar con un usuario que no deber�a dejar
				"user1", "advertisement1", IllegalArgumentException.class
			}, {
				//Se intenta eliminar con un usuario que no deber�a dejar
				"agent1", "advertisement2", IllegalArgumentException.class
			}, {
				//Se intenta eliminar con un usuario que no deber�a dejar
				"customer1", "advertisement2", IllegalArgumentException.class
			}, {
				//Se intenta eliminar con un usuario que no deber�a dejar
				"", "advertisement1", IllegalArgumentException.class
			}, {
				//Se intenta eliminar con un usuario que no deber�a dejar
				null, "advertisement1", IllegalArgumentException.class
			},
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateDelete((String) testingData[i][0], super.getEntityId((String) testingData[i][1]), (Class<?>) testingData[i][2]);
	}
	private void templateDelete(final String username, final int advertisementId, final Class<?> expected) {
		Advertisement advertisement;
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);
			advertisement = this.advertisementService.findOne(advertisementId);
			this.advertisementService.deleteAdmin(advertisementId);

			this.advertisementService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
			//Se borra la cache para que no salte siempre el error del primer objeto que ha fallado en el test
			this.entityManager.clear();
		}

		this.checkExceptions(expected, caught);

		super.unauthenticate();
	}

}
