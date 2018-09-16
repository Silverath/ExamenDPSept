
package services;

import java.util.Random;

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
import domain.Report;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class ReportServiceTest extends AbstractTest {

	//---------------------------------------- Service under test ----------------------------------------

	@Autowired
	private UserService			userService;

	@Autowired
	private ReportService		reportService;

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
				//Se acepta/deniega correctamente
				"admin", "report2", null

			}, {
				//Se acepta/deniega incorrectamente ya que el report no tiene asignada todavia una ruta
				"admin", "report1", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateCreateAndSave((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}
	private void templateCreateAndSave(final String username, final String reportBean, final Class<?> expected) {
		Class<?> caught;
		final Hike hike;
		caught = null;

		try {
			super.authenticate(username);
			final Report report = this.reportService.findOneToRejectOrApprove(super.getEntityId(reportBean));
			final Random random = new Random();
			if (random.nextInt(1) == 1)
				this.reportService.saveApprove(report);
			else
				this.reportService.saveReject(report);

			this.reportService.flush();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
			//Se borra la cache para que no salte siempre el error del primer objeto que ha fallado en el test
			this.entityManager.clear();
		}

		this.checkExceptions(expected, caught);

		super.unauthenticate();
	}
}
