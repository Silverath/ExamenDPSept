
package services;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.Validator;

import repositories.AdministratorRepository;
import security.LoginService;
import security.UserAccount;
import domain.Administrator;
import domain.Chirp;
import domain.Comment;

@Service
@Transactional
public class AdministratorService {

	//---------------------------------------- Managed repository ----------------------------------------

	@Autowired
	private AdministratorRepository	administratorRepository;

	//--------------------------------------- Supporting services ----------------------------------------
	@Autowired
	private ConfigurationService	configurationService;
	//-------------------------------------------- Validator ---------------------------------------------

	@Autowired
	private Validator				validator;


	//---------------------------------------------- Create ----------------------------------------------

	public Administrator create() {
		Administrator result;
		result = new Administrator();

		return result;
	}

	//------------------------------------------- Constructors -------------------------------------------

	public AdministratorService() {
		super();
	}

	//------------------------------------------- CRUD methods -------------------------------------------

	public Collection<Administrator> findAll() {
		return this.administratorRepository.findAll();
	}

	public Administrator findOne(final int administratorId) {
		return this.administratorRepository.findOne(administratorId);
	}

	public Administrator save(final Administrator administrator) {
		Administrator res;
		Assert.notNull(administrator);
		res = this.administratorRepository.save(administrator);
		return res;
	}

	public void delete(final Administrator administrator) {
		Assert.notNull(administrator);
		this.administratorRepository.delete(administrator);
	}

	//-------------------------------------- Other Business methods --------------------------------------

	public Administrator findByPrincipal() {
		UserAccount userAccount;
		Administrator res;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);

		res = this.findByUserAccount(userAccount);
		Assert.notNull(res);

		return res;
	}

	public Administrator findByUserAccount(final UserAccount userAccount) {
		Administrator res;
		res = this.administratorRepository.findByUserAcconuntId(userAccount.getId());
		Assert.notNull(res);
		return res;
	}

	public Collection<Chirp> chirpsWithTabooWords(final Collection<Chirp> chirps) {
		final Administrator ad = this.findByPrincipal();
		Assert.notNull(ad);
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

	public Collection<Comment> commentsWithTabooWords(final Collection<Comment> comments) {
		final Administrator a = this.findByPrincipal();
		Assert.notNull(a);
		final Collection<Comment> res = new HashSet<Comment>();
		final Collection<String> tabooWords = this.configurationService.findOne().getTaboo();
		for (final Comment c : comments)
			for (final String s : tabooWords)
				if (c.getDescription().contains(s)) {
					res.add(c);
					break;
				}
		return res;
	}

	public void flush() {
		this.administratorRepository.flush();
	}

}
