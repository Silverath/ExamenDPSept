
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.Validator;

import repositories.InnkeeperRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Inn;
import domain.Innkeeper;

@Service
@Transactional
public class InnkeeperService {

	//---------------------------------------- Managed repository ----------------------------------------

	@Autowired
	private InnkeeperRepository	innkeeperRepository;

	//--------------------------------------- Supporting services ----------------------------------------

	//-------------------------------------------- Validator ---------------------------------------------

	@Autowired
	private Validator			validator;


	//---------------------------------------------- Create ----------------------------------------------

	public Innkeeper create() {
		Innkeeper result;
		result = new Innkeeper();

		result.setInns(new ArrayList<Inn>());

		return result;
	}

	public Innkeeper create(final String name, final String surname, final String email, final String phone, final String address, final String picture) {
		final Innkeeper res = new Innkeeper();
		res.setName(name);
		res.setSurname(surname);
		res.setEmail(email);
		res.setPhone(phone);
		res.setAddress(address);
		res.setPicture(picture);

		return res;
	}

	//------------------------------------------- Constructors -------------------------------------------

	public InnkeeperService() {
		super();
	}

	//------------------------------------------- CRUD methods -------------------------------------------

	public Collection<Innkeeper> findAll() {
		return this.innkeeperRepository.findAll();
	}

	public Innkeeper findOne(final int innkeeperId) {
		return this.innkeeperRepository.findOne(innkeeperId);
	}

	public Innkeeper save(final Innkeeper innkeeper) {
		Innkeeper res;
		Assert.notNull(innkeeper);
		res = this.innkeeperRepository.save(innkeeper);
		return res;
	}

	public void delete(final Innkeeper innkeeper) {
		Assert.notNull(innkeeper);
		this.innkeeperRepository.delete(innkeeper);
	}

	//-------------------------------------- Other Business methods --------------------------------------

	public Innkeeper findByPrincipal() {
		UserAccount userAccount;
		Innkeeper res;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);

		res = this.findByUserAccount(userAccount);
		Assert.notNull(res);

		return res;
	}

	public Innkeeper findByUserAccount(final UserAccount userAccount) {
		Innkeeper res;
		res = this.innkeeperRepository.findByUserAcconuntId(userAccount.getId());
		Assert.notNull(res);
		return res;
	}

	public Innkeeper registerInnkeeper(final UserAccount userAccount, final Innkeeper r) {
		final Authority au = new Authority();
		au.setAuthority("INNKEEPER");
		userAccount.addAuthority(au);
		r.setUserAccount(userAccount);
		//this.save(r);

		this.save(r);

		return r;
	}

}
