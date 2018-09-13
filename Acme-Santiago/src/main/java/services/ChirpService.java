
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.Validator;

import repositories.ChirpRepository;
import domain.Administrator;
import domain.Chirp;
import domain.Follower;
import domain.User;

@Service
@Transactional
public class ChirpService {

	//---------------------------------------- Managed repository ----------------------------------------

	@Autowired
	private ChirpRepository			chirpRepository;

	//--------------------------------------- Supporting services ----------------------------------------
	@Autowired
	private FollowerService			followerService;

	@Autowired
	private UserService				userService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private ConfigurationService	configurationService;

	//-------------------------------------------- Validator ---------------------------------------------

	@Autowired
	private Validator				validator;


	//------------------------------------------- Constructors -------------------------------------------

	public ChirpService() {
		super();
	}

	//------------------------------------------- CRUD methods -------------------------------------------
	public Chirp create() {
		Chirp result;
		result = new Chirp();
		final User u = this.userService.findByPrincipal();
		result.setUser(u);
		final Date d = new Date();
		result.setMoment(d);

		return result;
	}

	public Collection<Chirp> findAll() {
		return this.chirpRepository.findAll();
	}

	public Chirp findOne(final int chirpId) {
		return this.chirpRepository.findOne(chirpId);
	}

	public Chirp save(final Chirp chirp) {
		Assert.notNull(chirp);
		final User u = this.userService.findByPrincipal();
		Assert.isTrue(u.equals(chirp.getUser()));
		final Chirp res = this.chirpRepository.save(chirp);
		u.getChirps().add(res);
		this.userService.save(u);
		return res;
	}

	public void delete(final Chirp chirp) {
		Assert.notNull(chirp);
		this.chirpRepository.delete(chirp);
	}

	public void deleteByAdmin(final int chirpId) {
		final Administrator a = this.administratorService.findByPrincipal();
		final Chirp c = this.chirpRepository.findOne(chirpId);
		Assert.notNull(a);

		final User u = c.getUser();
		u.getChirps().remove(c);
		this.userService.save(u);

		this.chirpRepository.delete(c);
	}

	//-------------------------------------- Other Business methods --------------------------------------

	public Collection<Chirp> prueba() {
		final Collection<Follower> follower = this.followerService.findByFollow(this.userService.findByPrincipal());
		final Collection<User> users = new ArrayList<User>();
		final Collection<Chirp> chirps = new HashSet<Chirp>();
		for (final Follower aux : follower)
			if (aux.getFollowed().getClass().equals(User.class)) {
				final User n = (User) aux.getFollowed();
				users.add(n);
			}
		for (final User u : users)
			chirps.addAll(u.getChirps());

		return chirps;
	}

	public Collection<Chirp> chirpsWithTabooWords(final Collection<Chirp> chirps) {
		final Collection<Chirp> res = new HashSet<Chirp>();
		final Collection<String> tabooWords = this.configurationService.findOne().getTaboo();
		for (final Chirp a : chirps)
			for (final String s : tabooWords)
				if (a.getDescription().contains(s) || a.getTitle().contains(s)) {
					res.add(a);
					break;
				}
		return res;
	}

	public void flush() {
		this.chirpRepository.flush();
	}

}
