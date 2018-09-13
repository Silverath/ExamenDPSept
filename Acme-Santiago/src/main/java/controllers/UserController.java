
package controllers;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.FollowerService;
import services.UserService;
import domain.Follower;
import domain.User;

@Controller
@RequestMapping("/user")
public class UserController extends AbstractController {

	//--------------------------------------- Supporting services ----------------------------------------

	@Autowired
	private UserService		userService;
	@Autowired
	private FollowerService	followerService;


	//------------------------------------------- Constructor --------------------------------------------

	public UserController() {
		super();
	}

	//----------------------------------------------- List -----------------------------------------------

	@RequestMapping(value = "/list")
	public ModelAndView list() {
		ModelAndView result;
		Collection<User> users;
		users = this.userService.findAll();

		result = new ModelAndView("user/list");
		result.addObject("users", users);
		result.addObject("requestURI", "user/list.do");
		try {
			result.addObject("loged", this.userService.findByPrincipal().getId());
			final Collection<User> followUser = new ArrayList<User>();
			users = this.userService.findAll();
			for (final User aux : users)
				if (this.followerService.existsFollower(this.userService.findByPrincipal(), aux) == true)
					followUser.add(aux);
			result.addObject("followUser", followUser);
		} catch (final Throwable oops) {

		}
		return result;
	}

	@RequestMapping(value = "/follow", method = RequestMethod.GET)
	public ModelAndView follow(@RequestParam final int userId) {
		ModelAndView result;
		final User a = this.userService.findByPrincipal();
		try {

			final User e = this.userService.findOne(userId);
			if (this.followerService.existsFollower(a, e)) {
				final Follower aux = this.followerService.findByFollowAndFollower(a, e);
				this.followerService.delete(aux);
				result = this.list();
			} else {
				final Follower aux = this.followerService.create();
				aux.setFollowed(e);
				this.followerService.save(aux);
				result = this.list();
			}
		} catch (final Throwable oops) {
			result = this.list();
			result.addObject("message", "user.errorFollow");

		}

		return result;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int userId) {
		Assert.notNull(userId);
		ModelAndView result;
		User r;
		r = this.userService.findOne(userId);
		result = new ModelAndView("user/display");
		result.addObject("user", r);
		return result;

	}

	//---------------------------------------------- Create ----------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		User user;

		user = this.userService.create();

		result = new ModelAndView("user/edit");
		result.addObject("user", user);

		return result;
	}

	//----------------------------------------------- Edit -----------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int userId) {
		ModelAndView result;
		User user;

		user = this.userService.findOne(userId);
		result = this.createEditModelAndView(user);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final User user, final BindingResult binding) {
		ModelAndView result;

		//user = userService.reconstruct(user, binding);
		if (binding.hasErrors())
			result = this.createEditModelAndView(user);
		else
			try {
				this.userService.save(user);
				result = new ModelAndView("redirect:/user/list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(user, "user.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final User user, final BindingResult binding) {
		ModelAndView result;

		try {
			this.userService.delete(user);
			result = new ModelAndView("redirect:/user/list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(user, "user.commit.error");
		}

		return result;
	}

	//------------------------------------------ Helper methods ------------------------------------------

	protected ModelAndView createEditModelAndView(final User user) {
		ModelAndView result;
		result = this.createEditModelAndView(user, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final User user, final String message) {
		ModelAndView result;

		result = new ModelAndView("user/edit");
		result.addObject("user", user);
		result.addObject("message", message);

		return result;
	}

}
