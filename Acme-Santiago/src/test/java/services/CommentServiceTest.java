
package services;

import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Comment;
import domain.Route;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class CommentServiceTest extends AbstractTest {

	//The SUT ----------------------------------------------------------
	@Autowired
	private CommentService	commentService;

	@Autowired
	private RouteService	routeService;


	//Tests ---------------------------------------------------------------

	//	Functional requirement 5.6: An actor who is authenticated as a user must be able to:
	//	Comment on the routees that he or she has RSVPd.

	@Test
	public void driverCreateComment() {

		final Date moment = new Date();
		final String picture = "https://exampleImagen.com";

		final Object testingData[][] = {
			{
				//Testing create a valid comment
				"user1", "route1", moment, "Example text", null
			}, {
				//Testing create a valid comment
				"user2", "route2", moment, "Example text", null
			}, {
				//Testing create a comment with an user that shoulnd´t can do it
				"manager1", "route1", moment, "Example text", IllegalArgumentException.class
			}, {
				//Testing create a comment with an user that shoulnd´t can do it
				"manager2", "route2", moment, "Example text", IllegalArgumentException.class
			}, {
				//Testing create a comment with an user unauthenticated
				null, "route1", moment, "Example text", IllegalArgumentException.class
			}, {
				//Testing create a comment with a blank text
				"user1", "route1", moment, "", javax.validation.ConstraintViolationException.class
			}, {
				//Testing create a comment with an user that doesn´t exist
				"user10", "route1", moment, "Example text", IllegalArgumentException.class
			},

		};
		for (int i = 0; i < testingData.length; i++)
			this.templateCreate((String) testingData[i][0], super.getEntityId((String) testingData[i][1]), (Date) testingData[i][2], (String) testingData[i][3], (Class<?>) testingData[i][4]);
	}

	// Ancillary methods ---------------------------------------------------------------------------------------

	protected void templateCreate(final String username, final int routeId, final Date moment, final String text, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		final Comment c;
		Route r;

		try {
			super.authenticate(username);
			r = this.routeService.findOne(routeId);
			c = this.commentService.create(routeId);
			c.setMoment(moment);
			c.setRoute(r);
			c.setDescription(text);
			c.setRating(1);

			this.commentService.save(c);
			this.commentService.flush();
			super.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}
