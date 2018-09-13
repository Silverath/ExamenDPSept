package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.Validator;

import domain.Inn;

import repositories.InnRepository;

@Service
@Transactional
public class InnService {

	//---------------------------------------- Managed repository ----------------------------------------
	
	@Autowired
	private InnRepository innRepository;
	

	//--------------------------------------- Supporting services ----------------------------------------



	//-------------------------------------------- Validator ---------------------------------------------
	
	@Autowired
	private Validator validator;
	

	//---------------------------------------------- Create ----------------------------------------------
	
	public Inn create() {
		Inn result;
		result = new Inn();


		return result;
	}
	

	//------------------------------------------- Constructors -------------------------------------------

	public InnService() {
		super();
	}
	
	
	//------------------------------------------- CRUD methods -------------------------------------------

	public Collection<Inn> findAll() {
		return innRepository.findAll();
	}

	public Inn findOne(int innId) {
		return innRepository.findOne(innId);
	}
	
	public Inn save(Inn inn) {
		Inn res;
		Assert.notNull(inn);
		res = innRepository.save(inn);
		return res;
	}
	
	public void delete(Inn inn) {
		Assert.notNull(inn);
		innRepository.delete(inn);
	}
	

	//-------------------------------------- Other Business methods --------------------------------------

	
}
