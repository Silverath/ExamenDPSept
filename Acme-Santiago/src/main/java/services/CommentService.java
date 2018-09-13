
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.Validator;

import repositories.CommentRepository;
import domain.Administrator;
import domain.Comment;
import domain.Route;
import domain.User;

@Service
@Transactional
public class CommentService {

	//---------------------------------------- Managed repository ----------------------------------------

	@Autowired
	private CommentRepository		commentRepository;

	//--------------------------------------- Supporting services ----------------------------------------
	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private UserService				userService;

	@Autowired
	private RouteService			routeService;

	@Autowired
	private HikeService				hikeService;
	//-------------------------------------------- Validator ---------------------------------------------

	@Autowired
	private Validator				validator;


	//---------------------------------------------- Create ----------------------------------------------

	public Comment create(final int routeId) {
		final Comment res = new Comment();
		res.setMoment(new Date());
		final User u = this.userService.findByPrincipal();
		res.setUser(u);
		final Route route = this.routeService.findOne(routeId);
		res.setRoute(route);
		return res;
	}
	//------------------------------------------- Constructors -------------------------------------------

	public CommentService() {
		super();
	}

	//------------------------------------------- CRUD methods -------------------------------------------

	public Collection<Comment> findAll() {
		return this.commentRepository.findAll();
	}

	public Comment findOne(final int commentId) {
		return this.commentRepository.findOne(commentId);
	}

	public Comment save(final Comment comment) {
		Comment res;
		Assert.notNull(comment);
		res = this.commentRepository.save(comment);
		return res;
	}

	public void deleteByAdmin(final int commentId) {
		final Administrator a = this.administratorService.findByPrincipal();
		final Comment c = this.commentRepository.findOne(commentId);
		Assert.notNull(a);

		final User u = c.getUser();
		u.getComments().remove(c);
		this.userService.save(u);

		//		final Route r = c.getRoute();
		//		r.getComments().remove(c);
		//		this.routeService.save(r);
		//
		//		final Hike h = c.getHike();
		//		h.getComments().remove(c);
		//		this.hikeService.save(h);

		this.commentRepository.delete(c);
	}

	public void delete(final int commentId) {
		Assert.notNull(commentId);
		final User a = this.userService.findByPrincipal();
		final Comment c = this.findOne(commentId);
		Assert.notNull(a);
		this.commentRepository.delete(c);
	}

	//-------------------------------------- Other Business methods --------------------------------------

	public void flush() {
		this.commentRepository.flush();
	}
}
