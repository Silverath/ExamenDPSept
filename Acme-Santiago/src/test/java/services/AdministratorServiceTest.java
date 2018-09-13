
package services;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Administrator;
import domain.Chirp;
import domain.Comment;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class AdministratorServiceTest extends AbstractTest {

	//---------------------------------------- Service under test ----------------------------------------
	// Supporting services ----------------------------------------------------

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private RouteService			routeService;

	@Autowired
	private ChirpService			chirpService;

	@Autowired
	private CommentService			commentService;

	@Autowired
	private ConfigurationService	configurationService;

	@PersistenceContext
	EntityManager					entityManager;


	//Login  with admin
	@Test
	public void driveLoginAdmin() {

		final Object testingData[][] = {
			//Admin is registered
			{
				"admin", null
			},
			//Admin isn't registered
			{
				"adminNotCreated", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateLoginAdmin((String) testingData[i][0], (Class<?>) testingData[i][1]);

	}

	public void templateLoginAdmin(final String username, final Class<?> expected) {

		Class<?> caught;

		caught = null;

		try {
			this.authenticate(username);
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
			this.entityManager.clear();
		}

		this.checkExceptions(expected, caught);
	}

	//Test to edit all administrator attributes
	@Test
	public void driveEditNameAdministrator() {

		final Object testingData[][] = {
			//Try entering all the data of an admin with the null address and null telephone, positive case 
			{
				"admin", "admin", "adminPrueba", "surnamePrueba", null, null, "prueba@gmail.com", null
			},
			//Try entering a null phone number, this case positive
			{
				"admin", "admin", "adminPrueba", "surnamePrueba", "c/prueba", null, "prueba@gmail.com", null
			},
			//Try entering all the data of an admin, positive case
			{
				"admin", "admin", "adminPrueba", "surnamePrueba", "c/prueba", "+34657896576", "prueba@gmail.com", null
			},
			//Try entering a blank name, negative case
			{
				"admin", "admin", "", "surnamePrueba", null, null, "prueba@gmail.com", javax.validation.ConstraintViolationException.class
			},
			//Try entering a null name, negative case
			{
				"admin", "admin", null, "surnamePrueba", null, null, "prueba@gmail.com", javax.validation.ConstraintViolationException.class
			},
			//Try entering a blank surname, negative case
			{
				"admin", "admin", "adminPrueba", "", null, null, "prueba@gmail.com", javax.validation.ConstraintViolationException.class
			},
			//Try entering a null surname, negative case
			{
				"admin", "admin", "adminPrueba", null, null, null, "prueba@gmail.com", javax.validation.ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateEditAdministrator((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(Class<?>) testingData[i][7]);

	}

	public void templateEditAdministrator(final String entity, final String username, final String name, final String surname, final String address, final String phone, final String email, final Class<?> expected) {

		Class<?> caught;
		Administrator administrator;

		caught = null;
		administrator = this.administratorService.findOne(super.getEntityId(entity));

		try {
			super.authenticate(username);
			administrator.setName(name);
			administrator.setSurname(surname);
			administrator.setAddress(address);
			administrator.setPhone(phone);
			administrator.setEmail(email);
			this.administratorService.save(administrator);
			this.unauthenticate();
			this.administratorService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
			this.entityManager.clear();
		}

		this.checkExceptions(expected, caught);

	}

	//Use case 6.1. An admin removes a route that he or she thinks is inappropriate.
	@Test
	public void driveRemoveRouteAdmin() {

		final Object testingData[][] = {
			//Admin remove article, positive case
			{
				"admin", "route1", null
			},
			//User can't remove an article. Negative case
			{
				"user1", "route1", java.lang.IllegalArgumentException.class
			},
			//Customer can't remove an article. Negative case
			{
				"user21", "route2", java.lang.IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateRemoveRouteAdmin((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	public void templateRemoveRouteAdmin(final String username, final String routeEntity, final Class<?> expected) {

		Class<?> caught;

		caught = null;

		try {
			super.authenticate(username);
			this.routeService.deleteByAdmin(super.getEntityId(routeEntity));
			this.unauthenticate();
			this.routeService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
			this.entityManager.clear();
		}

		this.checkExceptions(expected, caught);

	}

	//Use case 16.3. An admin removes a chirp that he or she thinks is inappropriate.
	@Test
	public void driveRemoveChirpAdmin() {

		final Object testingData[][] = {
			//Admin remove chirp, positive case
			{
				"admin", "chirp2", null
			},
			//					
			//User can't remove a chirp. Negative case
			{
				"user1", "chirp1", java.lang.IllegalArgumentException.class
			},
			//Customer can't remove a chirp. Negative case
			{
				"customer1", "chirp1", java.lang.IllegalArgumentException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateRemoveChirpAdmin((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);

	}

	public void templateRemoveChirpAdmin(final String username, final String chirpEntity, final Class<?> expected) {

		Class<?> caught;

		caught = null;

		try {
			super.authenticate(username);
			this.chirpService.deleteByAdmin(super.getEntityId(chirpEntity));
			this.unauthenticate();
			this.chirpService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
			this.entityManager.clear();
		}

		this.checkExceptions(expected, caught);

	}

	//	Functional requirement16.5: An actor who is authenticated as an administrator must be able to:
	//  Remove a comment that he or she thinks is inappropriate.	
	@Test
	public void driverDeleteComment() {

		final Object testingData[][] = {
			{
				//Testing delete a comment correctly
				"admin", "comment1", null
			}, {
				//Testing delete a comment correctly
				"admin", "comment2", null
			}, {
				//Testing delete a comment with an user unauthenticated
				null, "comment1", IllegalArgumentException.class
			}, {
				//Testing delete a comment with an user that shouldn´t can do it
				"user1", "comment1", IllegalArgumentException.class
			}, {
				//Testing delete a comment with an user that shouldn´t can do it
				"user2", "comment2", IllegalArgumentException.class
			}, {
				//Testing delete a comment with an user that doesn´t exist
				"asdfhj", "comment1", IllegalArgumentException.class
			}, {
				//Testing delete a comment with an user that doesn´t exist
				"", "comment1", IllegalArgumentException.class
			},
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateDelete((String) testingData[i][0], super.getEntityId((String) testingData[i][1]), (Class<?>) testingData[i][2]);
	}

	protected void templateDelete(final String username, final int commentId, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		Comment c;
		try {
			super.authenticate(username);
			c = this.commentService.findOne(commentId);
			this.commentService.deleteByAdmin(commentId);
			this.commentService.flush();
			super.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	//16. An actor who is authenticated as an administrator must be able to:
	//1. Manage a list of taboo words.
	@Test
	public void testListTaboo() {

		super.authenticate("admin");
		this.configurationService.findOne().getTaboo();

		super.unauthenticate();

	}

	//16. An actor who is authenticated as an administrator must be able to:
	//2. List the chirps that contain taboo words.
	@Test
	public void testListChirpsTaboo() {

		super.authenticate("admin");
		Collection<Chirp> chirps = this.chirpService.findAll();
		chirps = this.administratorService.chirpsWithTabooWords(chirps);

		super.unauthenticate();

	}

	//16. An actor who is authenticated as an administrator must be able to:
	//4. List the comments that contain taboo words.
	@Test
	public void testListCommentsTaboo() {

		super.authenticate("admin");
		Collection<Comment> comments = this.commentService.findAll();
		comments = this.administratorService.commentsWithTabooWords(comments);

		super.unauthenticate();

	}

	//16. An actor who is authenticated as an administrator must be able to:
	//2. List the chirps that contain taboo words.
	@Test(expected = IllegalArgumentException.class)
	public void testListChirpsTabooNegative() {

		super.authenticate("user1");
		Collection<Chirp> chirps = this.chirpService.findAll();
		chirps = this.administratorService.chirpsWithTabooWords(chirps);

		super.unauthenticate();

	}

	//16. An actor who is authenticated as an administrator must be able to:
	//4. List the comments that contain taboo words.
	@Test(expected = IllegalArgumentException.class)
	public void testListCommentsTabooNegative() {

		super.authenticate("user2");
		Collection<Comment> comments = this.commentService.findAll();
		comments = this.administratorService.commentsWithTabooWords(comments);

		super.unauthenticate();

	}

}
