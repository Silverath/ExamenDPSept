
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.Validator;

import repositories.HikeRepository;
import domain.Administrator;
import domain.Advertisement;
import domain.Comment;
import domain.Hike;
import domain.User;

@Service
@Transactional
public class HikeService {

	//---------------------------------------- Managed repository ----------------------------------------

	@Autowired
	private HikeRepository			hikeRepository;

	//--------------------------------------- Supporting services ----------------------------------------

	@Autowired
	private UserService				userService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private CommentService			commentService;

	//-------------------------------------------- Validator ---------------------------------------------

	@Autowired
	private Validator				validator;


	//---------------------------------------------- Create ----------------------------------------------

	public Hike create() {
		Hike result;
		result = new Hike();

		final User u = (User) this.actorService.findByPrincipal();
		result.setPictures(new ArrayList<String>());
		result.setComments(new ArrayList<Comment>());
		result.setAdvertisements(new ArrayList<Advertisement>());

		return result;
	}

	//------------------------------------------- Constructors -------------------------------------------

	public HikeService() {
		super();
	}

	//------------------------------------------- CRUD methods -------------------------------------------

	public Collection<Hike> findAll() {
		return this.hikeRepository.findAll();
	}

	public Hike findOne(final int hikeId) {
		return this.hikeRepository.findOne(hikeId);
	}

	public Hike save(final Hike hike) {
		Hike res;
		final User u = (User) this.actorService.findByPrincipal();
		Assert.notNull(hike);
		Assert.notNull(u);
		res = this.hikeRepository.save(hike);
		return res;
	}

	public Hike save2(final Hike d) {
		Assert.notNull(d);
		final Hike res = this.hikeRepository.save(d);
		return res;
	}

	public void deleteByAdmin(final int hikeId) {
		Assert.notNull(hikeId);
		final Administrator a = this.administratorService.findByPrincipal();
		final Hike h = this.findOne(hikeId);
		Assert.notNull(a);
		for (final Comment c : new HashSet<Comment>(h.getComments()))
			this.commentService.deleteByAdmin(c.getId());

		this.hikeRepository.delete(h);
	}

	public void delete(final int hikeId) {
		Assert.notNull(hikeId);
		final User a = this.userService.findByPrincipal();
		final Hike h = this.findOne(hikeId);
		Assert.notNull(a);

		for (final Comment c : new HashSet<Comment>(h.getComments()))
			this.commentService.delete(c.getId());

		this.hikeRepository.delete(h);
	}

	public Collection<Hike> getHikesFromAdvertisements(final Collection<Advertisement> advertisements) {
		final Collection<Hike> res = new HashSet<Hike>();
		for (final Advertisement a : advertisements)
			res.add(a.getHike());
		return res;
	}

	//-------------------------------------- Other Business methods --------------------------------------

	public Double avgLengthOfHikes() {
		return this.hikeRepository.avgLengthOfHikes();
	}

	public Double stddevLengthOfHikes() {
		return this.hikeRepository.stddevLengthOfHikes();
	}
	public Collection<Hike> allAdvertisements() {
		final Collection<Hike> res = this.hikeRepository.allAdvertisements();
		return res;
	}
	public Collection<Hike> notAdvertisements() {
		final Collection<Hike> res = this.hikeRepository.notAdvertisements();
		return res;
	}

	public void flush() {
		this.hikeRepository.flush();
	}

}
