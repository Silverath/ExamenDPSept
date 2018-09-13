
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.UserRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.User;
import forms.ActorAccountForm;

@Service
@Transactional
public class UserService {

	//---------------------------------------- Managed repository ----------------------------------------

	@Autowired
	private UserRepository	userRepository;

	//--------------------------------------- Supporting services ----------------------------------------

	//-------------------------------------------- Validator ---------------------------------------------

	@Autowired
	private Validator		validator;


	//---------------------------------------------- Create ----------------------------------------------

	public User create() {
		User result;
		result = new User();

		return result;
	}

	public User create(final String name, final String surname, final String email, final String phone, final String address, final String picture) {
		final User res = new User();
		res.setName(name);
		res.setSurname(surname);
		res.setEmail(email);
		res.setPhone(phone);
		res.setAddress(address);
		res.setPicture(picture);

		return res;
	}

	//------------------------------------------- Constructors -------------------------------------------

	public UserService() {
		super();
	}

	//------------------------------------------- CRUD methods -------------------------------------------

	public Collection<User> findAll() {
		return this.userRepository.findAll();
	}

	public User findOne(final int userId) {
		return this.userRepository.findOne(userId);
	}

	public User save(final User user) {
		User res;
		Assert.notNull(user);
		res = this.userRepository.save(user);
		return res;
	}

	public void delete(final User user) {
		Assert.notNull(user);
		this.userRepository.delete(user);
	}

	//-------------------------------------- Other Business methods --------------------------------------

	public User findByPrincipal() {
		UserAccount userAccount;
		User res;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);

		res = this.findByUserAccount(userAccount);
		Assert.notNull(res);

		return res;
	}

	public User findByUserAccount(final UserAccount userAccount) {
		User res;
		res = this.userRepository.findByUserAcconuntId(userAccount.getId());
		Assert.notNull(res);
		return res;
	}

	public User registerUser(final UserAccount userAccount, final User r) {
		final Authority au = new Authority();
		au.setAuthority("USER");
		userAccount.addAuthority(au);
		r.setUserAccount(userAccount);
		//this.save(r);

		this.save(r);

		return r;
	}

	public Actor reconstruct(final ActorAccountForm actorAccountForm, final BindingResult binding) {
		Actor result;

		if (actorAccountForm.getId() == 0) {

			result = this.create();
			result.getUserAccount().setUsername(actorAccountForm.getUsername());
			result.getUserAccount().setPassword(actorAccountForm.getPassword());
			result.setPhone(actorAccountForm.getPhone());
			result.setName(actorAccountForm.getName());
			result.setSurname(actorAccountForm.getSurname());

			result.setAddress(actorAccountForm.getAddress());
			result.setPicture(actorAccountForm.getPicture());

		} else {
			result = this.userRepository.findOne(actorAccountForm.getId());

			result.setName(actorAccountForm.getName());
			result.setSurname(actorAccountForm.getSurname());
			result.setAddress(actorAccountForm.getAddress());
			result.setPicture(actorAccountForm.getPicture());

			result.setPhone(actorAccountForm.getPhone());
			result.setEmail(actorAccountForm.getEmail());

		}

		this.validator.validate(result, binding);

		return result;
	}

	public Double stddevRoutesPerUser() {
		return this.userRepository.stddevRoutesPerUser();
	}

	public Double avgRoutesPerUser() {
		return this.userRepository.avgRoutesPerUser();
	}

	public Double avgChirpsPerUser() {
		return this.userRepository.avgChirpsPerUser();
	}

	public Collection<User> moreChirpsThanAverage() {
		return this.userRepository.moreChirpsThanAverage();
	}

	public Collection<User> lessChirpsThanAverage() {
		return this.userRepository.lessChirpsThanAverage();
	}

	public void flush() {
		this.userRepository.flush();
	}

	public User findUserByRoute(final int routeId) {
		User result;
		Assert.isTrue(routeId != 0);

		result = this.userRepository.findUserByRoute(routeId);

		return result;
	}
}
