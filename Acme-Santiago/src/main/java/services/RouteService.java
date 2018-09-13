
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.RouteRepository;
import domain.Administrator;
import domain.Comment;
import domain.Hike;
import domain.Route;
import domain.User;
import forms.LengthSearchForm;

@Service
@Transactional
public class RouteService {

	//---------------------------------------- Managed repository ----------------------------------------

	@Autowired
	private RouteRepository			routeRepository;

	//--------------------------------------- Supporting services ----------------------------------------
	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private CommentService			commentService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private HikeService				hikeService;

	@Autowired
	private UserService				userService;
	//-------------------------------------------- Validator ---------------------------------------------

	@Autowired
	private Validator				validator;


	//---------------------------------------------- Create ----------------------------------------------

	public Route create() {
		Route result;
		result = new Route();
		final User u = (User) this.actorService.findByPrincipal();

		result.setUser(u);
		result.setPictures(new ArrayList<String>());
		result.setComments(new ArrayList<Comment>());
		result.setHikes(new ArrayList<Hike>());

		return result;
	}

	//------------------------------------------- Constructors -------------------------------------------

	public RouteService() {
		super();
	}

	//------------------------------------------- CRUD methods -------------------------------------------

	public Collection<Route> findAll() {
		return this.routeRepository.findAll();
	}

	public Route findOne(final int routeId) {
		return this.routeRepository.findOne(routeId);
	}

	public Route save(final Route route) {
		Route res;
		final User u = (User) this.actorService.findByPrincipal();
		Assert.notNull(route);
		res = this.routeRepository.save(route);
		if (!u.getRoutes().contains(res)) {
			u.getRoutes().add(res);
			this.userService.save(u);
		}

		return res;
	}

	public Route save2(final Route n) {
		Assert.notNull(n);
		final Route res = this.routeRepository.save(n);
		return res;
	}

	public void delete(final int routeId) {
		final User u = this.userService.findByPrincipal();
		Assert.notNull(u);
		final Route route = this.routeRepository.findOne(routeId);
		Assert.notNull(route);
		for (final Comment c : new HashSet<Comment>(route.getComments()))
			this.commentService.delete(c.getId());

		//		for (final Hike c : new HashSet<Hike>(route.getHikes()))
		//			this.hikeService.delete(c.getId());

		//		for (final Hike c : new HashSet<Hike>(route.getHikes())) {
		//			route.getHikes().remove(c);
		//			this.hikeService.save(c);
		//		}

		for (final Hike d : route.getHikes()) {
			d.getRoutes().remove(route);
			this.hikeService.save2(d);
		}

		final User user = route.getUser();
		user.getRoutes().remove(route);
		this.userService.save(user);

		this.routeRepository.delete(route);
	}

	public void deleteByAdmin(final int routeId) {
		final Administrator a = this.administratorService.findByPrincipal();
		final Route r = this.routeRepository.findOne(routeId);
		Assert.notNull(a);

		for (final Comment c : new HashSet<Comment>(r.getComments()))
			this.commentService.deleteByAdmin(c.getId());

		for (final Hike c : new HashSet<Hike>(r.getHikes()))
			this.hikeService.deleteByAdmin(c.getId());

		final User u = r.getUser();
		u.getRoutes().remove(r);
		this.userService.save(u);

		this.routeRepository.delete(r);
	}

	public LengthSearchForm reconstruct(final LengthSearchForm lengthSearchForm, final BindingResult binding) {
		LengthSearchForm result;

		result = lengthSearchForm;

		this.validator.validate(result, binding);

		return result;
	}

	public static boolean isNumeric(final String cadena) {

		boolean resultado;

		try {
			Integer.parseInt(cadena);
			resultado = true;
		} catch (final NumberFormatException excepcion) {
			resultado = false;
		}

		return resultado;
	}

	public void addHikes(final Route route, final Collection<Hike> aux) {
		Assert.notNull(route);
		final User o = this.userService.findByPrincipal();
		Assert.isTrue(o.equals(route.getUser()));
		aux.addAll(route.getHikes());
		for (final Hike d : route.getHikes()) {
			d.getRoutes().add(route);
			this.hikeService.save2(d);
		}
		route.setHikes(aux);
		this.routeRepository.save(route);
	}

	public Collection<String> collectStrings(final String names) {
		final Collection<String> res = new HashSet<String>();
		final String[] aux = names.split(",");
		for (final String s : aux)
			res.add(s.trim());
		return res;
	}

	public void deleteHikes(final Route route, final Collection<Hike> aux) {
		Assert.notNull(route);
		final User o = this.userService.findByPrincipal();
		Assert.isTrue(o.equals(route.getUser()), "You didn´t create this route.");
		aux.removeAll(route.getHikes());
		for (final Hike d : route.getHikes()) {
			d.getRoutes().remove(route);
			this.hikeService.save2(d);
		}
		route.setHikes(aux);
		this.routeRepository.save(route);

	}

	//-------------------------------------- Other Business methods --------------------------------------

	public Collection<Route> findByUser(final int userId) {
		return this.routeRepository.findByUser(userId);
	}

	public Collection<Route> searchRoute(final String keyword) {
		return this.routeRepository.searchRoute(keyword);
	}

	public Collection<Route> searchRouteByHike(final String keyword) {
		return this.routeRepository.searchRouteByHike(keyword);
	}

	public Collection<Route> searchRouteByLength(final Double minLength, final Double maxLength) {
		return this.routeRepository.searchRouteByLength(minLength, maxLength);
	}

	public Collection<Route> searchRouteByHikes(final Integer minHike, final Integer maxHike) {
		return this.routeRepository.searchRouteByHikes(minHike, maxHike);
	}

	public Double stddevHikesPerRoute() {
		return this.routeRepository.stddevHikesPerRoute();
	}

	public Double avgHikesPerRoute() {
		return this.routeRepository.avgHikesPerRoute();
	}

	public Double avgLengthOfRoutes() {
		return this.routeRepository.avgLengthOfRoutes();
	}

	public Double stddevLengthOfRoutes() {
		return this.routeRepository.stddevLengthOfRoutes();
	}

	public Collection<Route> outlierRoutes() {
		return this.routeRepository.outliersRoutes();
	}

	public Double avgCommentsPerRoute() {
		return this.routeRepository.avgCommentsPerRoute();
	}

	public Double ratioRoutesWithAdvVSNot() {
		return this.routeRepository.ratioRoutesWithAdvVSNot();
	}

	public void flush() {
		this.routeRepository.flush();
	}
}
