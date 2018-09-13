
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Follower;
import domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class FollowerServiceTest extends AbstractTest {

	//---------------------------------------- Service under test ----------------------------------------

	@Autowired
	private FollowerService		followerService;

	//--------------------------------------- Supporting services ----------------------------------------

	@Autowired
	public ConfigurationService	configurationService;

	@Autowired
	public UserService			userService;


	//----------------------------------------------- Test -----------------------------------------------

	/******************************** -Positive- ******************************/

	//	5.2 An actor who is authenticated as a user must be able to:
	//	Follow/unfollow other users.

	@Test
	public void testCreateFollowOrUnfollow() {
		super.authenticate("user1");
		final User a = this.userService.findOne(super.getEntityId("user1"));
		final User e = this.userService.findOne(super.getEntityId("user2"));
		if (this.followerService.existsFollower(a, e)) {
			final Follower aux = this.followerService.findByFollowAndFollower(a, e);
			this.followerService.delete(aux);
		} else {
			final Follower aux = this.followerService.create();
			aux.setFollowed(e);
			this.followerService.save(aux);
		}
		super.unauthenticate();
	}

	//	5.3  An actor who is authenticated as an user must be able to:
	//		List the users who he or she follows.

	@Test
	public void testListFollows() {
		final User u;
		int userId;
		userId = super.getEntityId("user1");
		u = this.userService.findOne(userId);
		super.authenticate("user1");

		u.getFollow();

		super.unauthenticate();
	}

	//	5.4  An actor who is authenticated as an user must be able to:
	//		List the users who follow him or her.

	@Test
	public void testListFollowers() {
		final User u;
		int userId;
		userId = super.getEntityId("user1");
		u = this.userService.findOne(userId);
		super.authenticate("user1");

		u.getFollower();

		super.unauthenticate();
	}

}
