
package controllers;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.HikeService;
import services.RouteService;
import services.UserService;
import domain.Comment;
import domain.Hike;
import domain.Route;
import domain.User;
import forms.HikesSearchForm;
import forms.LengthSearchForm;
import forms.SearchForm;

@Controller
@RequestMapping("/route")
public class RouteController extends AbstractController {

	//--------------------------------------- Supporting services ----------------------------------------

	@Autowired
	private RouteService		routeService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private UserService			userService;

	@Autowired
	private HikeService			hikeService;

	private Collection<Hike>	aux;


	//------------------------------------------- Constructor --------------------------------------------

	public RouteController() {
		super();
	}

	//----------------------------------------------- List -----------------------------------------------

	@RequestMapping(value = "/list")
	public ModelAndView list() {
		ModelAndView result;
		Collection<Route> routes;

		routes = this.routeService.findAll();

		result = new ModelAndView("route/list");
		result.addObject("routes", routes);
		result.addObject("requestUri", "/route/list.do");

		return result;
	}

	@RequestMapping(value = "/myRoutes")
	public ModelAndView myRoutes() {
		ModelAndView result;
		final User u = (User) this.actorService.findByPrincipal();
		final Collection<Route> routes = this.routeService.findByUser(u.getId());
		result = new ModelAndView("route/myRoutes");
		result.addObject("routes", routes);
		result.addObject("requestUri", "/route/myRoutes.do");

		return result;
	}

	@RequestMapping(value = "/listComments", method = RequestMethod.GET)
	public ModelAndView listComments(@RequestParam final int routeId) {
		ModelAndView result;
		final Collection<Comment> comments;
		final Route r = this.routeService.findOne(routeId);
		comments = r.getComments();
		result = new ModelAndView("route/listComments");
		result.addObject("comments", comments);
		result.addObject("route", r);
		result.addObject("requestURI", "route/listCommentsRendez.do");
		return result;
	}

	@RequestMapping(value = "/listSearchKeyword", method = RequestMethod.GET)
	public ModelAndView listSearch(@RequestParam final String keyword) {
		ModelAndView result;
		final Collection<Route> routes = this.routeService.searchRoute(keyword);
		final Collection<Route> routesHike = this.routeService.searchRouteByHike(keyword);
		final Set<Route> res = new HashSet<Route>();
		res.addAll(routes);
		res.addAll(routesHike);
		result = new ModelAndView("route/list");
		result.addObject("requestURI", "route/listSearchKeyword.do");
		result.addObject("routes", res);
		return result;
	}

	@RequestMapping(value = "/listSearchLength", method = RequestMethod.GET)
	public ModelAndView listSearchLength(@RequestParam final Double minLength, final Double maxLength) {
		ModelAndView result;
		final Collection<Route> routes = this.routeService.searchRouteByLength(minLength, maxLength);
		result = new ModelAndView("route/list");
		result.addObject("requestURI", "route/listSearchLength.do");
		result.addObject("routes", routes);
		return result;
	}

	@RequestMapping(value = "/searchLength", method = RequestMethod.GET)
	public ModelAndView searchLength() {
		final LengthSearchForm searchForm = new LengthSearchForm();
		ModelAndView result;
		result = new ModelAndView("route/searchLength");
		result.addObject("searchForm", searchForm);

		return result;
	}

	@RequestMapping(value = "/searchLength", method = RequestMethod.POST, params = "search")
	public ModelAndView searchLength(@Valid final LengthSearchForm lengthSearchForm, final BindingResult binding) {
		Assert.isTrue(binding.hasErrors() == false);
		ModelAndView result;
		String min = "0";
		final Double n = Double.MAX_VALUE;
		String max = n.toString();
		if (lengthSearchForm.getMinLength() != null)
			min = lengthSearchForm.getMinLength().toString();
		if (lengthSearchForm.getMaxLength() != null)
			max = lengthSearchForm.getMaxLength().toString();
		result = new ModelAndView("redirect:listSearchLength.do?minLength=" + min + "&" + "maxLength=" + max);
		return result;
	}

	@RequestMapping(value = "/listSearchHikes", method = RequestMethod.GET)
	public ModelAndView listSearchHikes(@RequestParam final Integer minHikes, final Integer maxHikes) {
		ModelAndView result;
		final Collection<Route> routes = this.routeService.searchRouteByHikes(minHikes, maxHikes);
		result = new ModelAndView("route/list");
		result.addObject("requestURI", "route/listSearchHikes.do");
		result.addObject("routes", routes);
		return result;
	}

	@RequestMapping(value = "/searchHikes", method = RequestMethod.GET)
	public ModelAndView searchHikes() {
		final HikesSearchForm searchForm = new HikesSearchForm();

		ModelAndView result;
		result = new ModelAndView("route/searchNHikes");
		result.addObject("searchForm", searchForm);

		return result;
	}

	@RequestMapping(value = "/searchHikes", method = RequestMethod.POST, params = "search")
	public ModelAndView searchHikes(@Valid final HikesSearchForm hikesSearchForm, final BindingResult binding) {
		Assert.isTrue(binding.hasErrors() == false);
		ModelAndView result;
		String min = "0";
		final Integer n = Integer.MAX_VALUE;
		String max = n.toString();
		if (hikesSearchForm.getMinHikes() != null)
			min = hikesSearchForm.getMinHikes().toString();
		if (hikesSearchForm.getMaxHikes() != null)
			max = hikesSearchForm.getMaxHikes().toString();
		result = new ModelAndView("redirect:listSearchHikes.do?minHikes=" + min + "&" + "maxHikes=" + max);
		return result;
	}
	@RequestMapping(value = "/searchKeyword", method = RequestMethod.GET)
	public ModelAndView search() {
		final SearchForm searchForm = new SearchForm();
		ModelAndView result;
		result = new ModelAndView("route/searchKeyword");
		result.addObject("searchForm", searchForm);

		return result;
	}

	@RequestMapping(value = "/searchKeyword", method = RequestMethod.POST, params = "search")
	public ModelAndView search(@Valid final SearchForm searchForm) {
		ModelAndView result;
		result = new ModelAndView("redirect:listSearchKeyword.do?keyword=" + searchForm.getKeyword());

		return result;
	}

	//---------------------------------------------- Display ----------------------------------------------

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int routeId) {
		Assert.notNull(this.routeService.findOne(routeId));
		ModelAndView result;
		final Route route = this.routeService.findOne(routeId);
		result = new ModelAndView("route/display");
		result.addObject("route", route);
		return result;
	}

	//---------------------------------------------- Create ----------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Route route;

		route = this.routeService.create();
		final Collection<Hike> hikes = this.hikeService.findAll();

		result = new ModelAndView("route/edit");
		result.addObject("route", route);
		result.addObject("hikes", hikes);

		return result;
	}

	//----------------------------------------------- Edit -----------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int routeId) {
		ModelAndView result;
		Route route;

		final Collection<Hike> hikes = this.hikeService.findAll();
		route = this.routeService.findOne(routeId);

		final User u = this.userService.findByPrincipal();
		Assert.isTrue(u.equals(route.getUser()));
		Assert.notNull(u);

		result = this.createEditModelAndView(route);
		result.addObject("hikes", hikes);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@RequestParam("pictures") final String pictures, @Valid final Route route, final BindingResult binding) {
		ModelAndView result;

		//route = routeService.reconstruct(route, binding);
		if (binding.hasErrors()) {
			result = this.createEditModelAndView(route);
			result.addObject("hikes", this.hikeService.findAll());
		} else
			try {
				route.setPictures(this.routeService.collectStrings(pictures));
				this.routeService.save(route);
				result = new ModelAndView("redirect:/route/myRoutes.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(route, "route.commit.error");
				final Collection<Hike> hikesR = route.getHikes();
				final Collection<Hike> hikes = this.hikeService.findAll();
				if (hikesR != null)
					hikes.removeAll(hikesR);

				result.addObject("hikes", hikes);
			}

		return result;
	}

	// Deleting ---------------------------------------------------------------	
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int routeId) {
		ModelAndView result;
		final User u = (User) this.actorService.findByPrincipal();
		final Route route = this.routeService.findOne(routeId);
		Assert.isTrue(u.equals(route.getUser()));
		Assert.notNull(u);
		try {
			this.routeService.delete(routeId);
			result = new ModelAndView("redirect:myRoutes.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("route/myRoutes");
			result.addObject("route", routeId);
			result.addObject("message", "route.commit.error.delete");
		}
		result.addObject("routes", this.routeService.findByUser(u.getId()));

		return result;
	}

	@RequestMapping(value = "/deleteByAdmin", method = RequestMethod.GET)
	public ModelAndView deleteByAdmin(@RequestParam final int routeId) {
		ModelAndView result;
		try {
			this.routeService.deleteByAdmin(routeId);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("route/list");
			result.addObject("route", routeId);
			result.addObject("message", "route.commit.error.delete");
		}
		result.addObject("routes", this.routeService.findAll());

		return result;
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView add(@RequestParam final int routeId) {
		Assert.notNull(routeId);

		ModelAndView result;
		final Boolean option = true;
		this.aux = this.routeService.findOne(routeId).getHikes();
		final Collection<Hike> hikes = this.hikeService.findAll();
		final Route p = this.routeService.findOne(routeId);
		final User u = this.userService.findByPrincipal();
		Assert.isTrue(u.equals(p.getUser()));
		Assert.notNull(u);
		result = new ModelAndView("route/addOrRemove");
		result.addObject("route", p);
		result.addObject("option", option);
		result.addObject("hikes", hikes);
		return result;
	}

	@RequestMapping(value = "/remove", method = RequestMethod.GET)
	public ModelAndView remove(@RequestParam final int routeId) {
		Assert.notNull(routeId);
		ModelAndView result;
		final Boolean option = false;
		this.aux = this.routeService.findOne(routeId).getHikes();
		final Collection<Hike> hikes = this.routeService.findOne(routeId).getHikes();
		final Route p = this.routeService.findOne(routeId);
		final User u = this.userService.findByPrincipal();
		Assert.isTrue(u.equals(p.getUser()));
		Assert.notNull(u);
		result = new ModelAndView("route/addOrRemove");
		result.addObject("route", p);
		result.addObject("option", option);
		result.addObject("hikes", hikes);
		return result;
	}

	@RequestMapping(value = "/addHikes", method = RequestMethod.POST, params = "save")
	public ModelAndView addHikes(@Valid final Route route, final BindingResult binding) {
		ModelAndView result;
		final User actor = (User) this.actorService.findByPrincipal();
		final User user = this.userService.findUserByRoute(route.getId());
		final Collection<Hike> hikes = this.hikeService.findAll();
		if (binding.hasErrors())
			try {
				Assert.isTrue(actor.equals(user));
				result = new ModelAndView("route/addOrRemove");
				result.addObject(route);
			} catch (final Throwable oops) {
				result = new ModelAndView("route/addOrRemove");
				result.addObject("route", route);
				result.addObject("hikes", hikes);
				result.addObject("message", "route.commit.error");
			}
		else
			try {
				this.routeService.addHikes(route, this.aux);
				result = new ModelAndView("redirect:myRoutes.do");
			} catch (final Throwable oops) {
				result = new ModelAndView("route/addOrRemove");
				result.addObject("route", route);
				result.addObject("hikes", hikes);
				result.addObject("message", "route.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/removeHikes", method = RequestMethod.POST, params = "remove")
	public ModelAndView removeHikes(@Valid final Route route, final BindingResult binding) {
		ModelAndView result;
		final User user = this.userService.findUserByRoute(route.getId());
		;
		final User actor = (User) this.actorService.findByPrincipal();
		;
		final Collection<Hike> hikes = this.aux;
		if (binding.hasErrors())
			try {
				Assert.isTrue(actor.equals(user));
				result = new ModelAndView("route/addOrRemove");
				result.addObject(route);
			} catch (final Throwable oops) {
				result = new ModelAndView("route/addOrRemove");
				result.addObject("route", route);
				result.addObject("hikes", hikes);
				result.addObject("message", "route.commit.error");
			}
		else
			try {
				this.routeService.deleteHikes(route, this.aux);
				result = new ModelAndView("redirect:myRoutes.do");
			} catch (final Throwable oops) {
				result = new ModelAndView("route/addOrRemove");
				result.addObject("route", route);
				result.addObject("hikes", hikes);
				result.addObject("message", "route.commit.error");
			}

		return result;
	}

	//------------------------------------------ Helper methods ------------------------------------------

	protected ModelAndView createEditModelAndView(final Route route) {
		ModelAndView result;
		result = this.createEditModelAndView(route, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Route route, final String message) {
		ModelAndView result;

		result = new ModelAndView("route/edit");
		result.addObject("route", route);
		result.addObject("message", message);

		return result;
	}

}
