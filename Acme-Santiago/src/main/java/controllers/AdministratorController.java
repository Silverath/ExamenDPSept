
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import services.AdvertisementService;
import services.ChirpService;
import services.CommentService;
import services.ConfigurationService;
import services.HikeService;
import services.RouteService;
import services.UserService;
import domain.Administrator;
import domain.Chirp;
import domain.Comment;
import domain.Configuration;
import domain.Route;
import domain.User;

@Controller
@RequestMapping("/administrator")
public class AdministratorController extends AbstractController {

	//--------------------------------------- Supporting services ----------------------------------------

	@Autowired
	private AdministratorService	administratorService;
	@Autowired
	private RouteService			routeService;
	@Autowired
	private HikeService				hikeService;
	@Autowired
	private UserService				userService;
	@Autowired
	private ChirpService			chirpService;
	@Autowired
	private CommentService			commentService;

	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private AdvertisementService	advertisementService;


	//------------------------------------------- Constructor --------------------------------------------
	public AdministratorController() {
		super();
	}

	//----------------------------------------------- List -----------------------------------------------

	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public ModelAndView dashboard1() {
		final ModelAndView result;
		final Double avgR = this.userService.avgRoutesPerUser();
		final Double stddevR = this.userService.stddevRoutesPerUser();
		final Double avgH = this.routeService.avgHikesPerRoute();
		final Double stddevH = this.routeService.stddevHikesPerRoute();
		final Double avgLengthR = this.routeService.avgLengthOfRoutes();
		final Double stddevLengthR = this.routeService.stddevLengthOfRoutes();
		final Double avgLengthH = this.hikeService.avgLengthOfHikes();
		final Double stddevLengthH = this.hikeService.stddevLengthOfHikes();
		final Double avgChirpsPerUser = this.userService.avgChirpsPerUser();
		final Double avgCommentsPerRoute = this.routeService.avgCommentsPerRoute();
		final Collection<Route> outlier = this.routeService.outlierRoutes();
		final Collection<User> userMoreChirps = this.userService.moreChirpsThanAverage();
		final Collection<User> userLessChirps = this.userService.lessChirpsThanAverage();
		final Double ratioAdvWithTaboo = this.advertisementService.ratioAdvertisementsWithTaboo();
		final Double ratioRoutesWithAdvVSNot = this.routeService.ratioRoutesWithAdvVSNot();
		result = new ModelAndView("administrator/dashboard");
		result.addObject("avgR", avgR);
		result.addObject("stddevR", stddevR);
		result.addObject("avgH", avgH);
		result.addObject("stddevH", stddevH);
		result.addObject("avgLengthR", avgLengthR);
		result.addObject("stddevLengthR", stddevLengthR);
		result.addObject("avgLengthH", avgLengthH);
		result.addObject("stddevLengthH", stddevLengthH);
		result.addObject("avgChirpsPerUser", avgChirpsPerUser);
		result.addObject("outlier", outlier);
		result.addObject("avgCommentsPerRoute", avgCommentsPerRoute);
		result.addObject("userMoreChirps", userMoreChirps);
		result.addObject("userLessChirps", userLessChirps);
		result.addObject("ratioAdvWithTaboo", ratioAdvWithTaboo);
		result.addObject("ratioRoutesWithAdvVSNot", ratioRoutesWithAdvVSNot);
		result.addObject("requestURI", "administrator/dashboard.do");
		return result;
	}
	//---------------------------------------------- Create ----------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Administrator administrator;

		administrator = this.administratorService.create();

		result = new ModelAndView("administrator/edit");
		result.addObject("administrator", administrator);

		return result;
	}

	//----------------------------------------------- Edit -----------------------------------------------

	@RequestMapping(value = "/deleteChirpsByAdmin", method = RequestMethod.GET)
	public ModelAndView deleteChirpsByAdmin(@RequestParam final int chirpId) {
		ModelAndView result;
		try {
			this.chirpService.deleteByAdmin(chirpId);
			result = new ModelAndView("redirect:listTabooChirps.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("administrator/listTabooChirps");
			result.addObject("chirp", chirpId);
			result.addObject("message", "chirp.commit.error.delete");
		}

		return result;
	}

	@RequestMapping(value = "/deleteCommentsByAdmin", method = RequestMethod.GET)
	public ModelAndView deleteCommentsByAdmin(@RequestParam final int commentId) {
		ModelAndView result;
		try {
			this.commentService.deleteByAdmin(commentId);
			result = new ModelAndView("redirect:listTabooComments.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("administrator/listTabooComments");
			result.addObject("comment", commentId);
			result.addObject("message", "comment.commit.error.delete");
		}
		final Collection<Comment> comments = this.commentService.findAll();
		result.addObject("comments", this.administratorService.commentsWithTabooWords(comments));

		return result;
	}

	@RequestMapping(value = "/listTabooWord", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		final Collection<String> tabooWords = this.configurationService.findOne().getTaboo();
		result = new ModelAndView("administrator/listTabooWord");
		result.addObject("tabooWords", tabooWords);
		result.addObject("requestURI", "administrator/listTabooWord.do");
		return result;
	}

	@RequestMapping(value = "/listTabooChirps", method = RequestMethod.GET)
	public ModelAndView listTabooChirps() {
		ModelAndView result;
		Collection<Chirp> chirps = this.chirpService.findAll();
		chirps = this.administratorService.chirpsWithTabooWords(chirps);
		result = new ModelAndView("chirp/list");
		result.addObject("chirps", chirps);
		result.addObject("requestURI", "chirp/list.do");
		return result;
	}

	@RequestMapping(value = "/listTabooComments", method = RequestMethod.GET)
	public ModelAndView listTabooComments() {
		ModelAndView result;
		Collection<Comment> comments = this.commentService.findAll();
		comments = this.administratorService.commentsWithTabooWords(comments);
		result = new ModelAndView("comment/list");
		result.addObject("comments", comments);
		result.addObject("requestURI", "comment/list.do");
		return result;
	}

	@RequestMapping(value = "/addTabooWord", method = RequestMethod.GET)
	public ModelAndView add() {
		final ModelAndView result = new ModelAndView("administrator/addTabooWord");
		final String s = "";
		result.addObject("tabooWord", s);
		return result;
	}

	@RequestMapping(value = "/addTabooWord", method = RequestMethod.POST, params = "save")
	public ModelAndView saveWord(@RequestParam("tabooWord") final String word) {
		ModelAndView result;
		if (word == "") {
			result = new ModelAndView("administrator/addTabooWord");
			result.addObject("message", "administrator.empty.taboo");
		} else
			try {
				final Configuration c = this.configurationService.findOne();
				c.getTaboo().add(word);
				this.configurationService.save(c);
				final Collection<String> tabooWords = this.configurationService.findOne().getTaboo();
				result = new ModelAndView("administrator/listTabooWord");
				result.addObject("tabooWords", tabooWords);
				result.addObject("requestURI", "administrator/listTabooWord.do");
			} catch (final Throwable oops) {
				result = new ModelAndView("administrator/addTabooWord");
				result.addObject("message", "configuration.commit.error");
			}
		return result;
	}

	//------------------------------------------ Helper methods ------------------------------------------

	protected ModelAndView createEditModelAndView(final Administrator administrator) {
		ModelAndView result;
		result = this.createEditModelAndView(administrator, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Administrator administrator, final String message) {
		ModelAndView result;

		result = new ModelAndView("administrator/edit");
		result.addObject("administrator", administrator);
		result.addObject("message", message);

		return result;
	}

}
