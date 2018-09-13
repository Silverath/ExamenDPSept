
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.Validator;

import repositories.ActorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Administrator;
import domain.Innkeeper;
import domain.User;

@Service
@Transactional
public class ActorService {

	//---------------------------------------- Managed repository ----------------------------------------

	@Autowired
	private ActorRepository			actorRepository;

	//--------------------------------------- Supporting services ----------------------------------------
	@Autowired
	private UserService				userService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private InnkeeperService		innkeeperService;
	//-------------------------------------------- Validator ---------------------------------------------

	@Autowired
	private Validator				validator;


	//---------------------------------------------- Create ----------------------------------------------

	public Actor create() {
		Actor result;
		result = new Actor();

		return result;
	}

	//------------------------------------------- Constructors -------------------------------------------

	public ActorService() {
		super();
	}

	//------------------------------------------- CRUD methods -------------------------------------------

	public Collection<Actor> findAll() {
		return this.actorRepository.findAll();
	}

	public Actor findOne(final int actorId) {
		return this.actorRepository.findOne(actorId);
	}

	public Actor save(final Actor actor) {
		Actor res;
		Assert.notNull(actor);
		res = this.actorRepository.save(actor);
		return res;
	}

	public void delete(final Actor actor) {
		Assert.notNull(actor);
		this.actorRepository.delete(actor);
	}

	//-------------------------------------- Other Business methods --------------------------------------

	public Actor findByPrincipal() {
		Actor result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = this.findByUserAccount(userAccount);
		Assert.notNull(result);

		return result;
	}
	public Actor findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);

		Actor result;

		result = this.actorRepository.findByUserAccountId(userAccount.getId());

		return result;
	}

	public void updateActor(final Actor a) {

		for (final Authority au : a.getUserAccount().getAuthorities()) {
			System.out.println(au.getAuthority());
			if (au.getAuthority().equals("USER"))
				this.userService.save((User) a);
			if (au.getAuthority().equals("INNKEEPER"))
				this.innkeeperService.save((Innkeeper) a);
			if (au.getAuthority().equals("ADMIN"))
				this.administratorService.save((Administrator) a);

		}

	}

}
