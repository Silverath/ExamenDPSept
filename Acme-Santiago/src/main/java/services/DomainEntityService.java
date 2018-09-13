package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.Validator;

import domain.DomainEntity;

import repositories.DomainEntityRepository;

@Service
@Transactional
public class DomainEntityService {

	//---------------------------------------- Managed repository ----------------------------------------
	
	@Autowired
	private DomainEntityRepository domainEntityRepository;
	

	//--------------------------------------- Supporting services ----------------------------------------



	//-------------------------------------------- Validator ---------------------------------------------
	
	@Autowired
	private Validator validator;
	

	//------------------------------------------- Constructors -------------------------------------------

	public DomainEntityService() {
		super();
	}
	
	
	//------------------------------------------- CRUD methods -------------------------------------------

	public Collection<DomainEntity> findAll() {
		return domainEntityRepository.findAll();
	}

	public DomainEntity findOne(int domainEntityId) {
		return domainEntityRepository.findOne(domainEntityId);
	}
	
	public DomainEntity save(DomainEntity domainEntity) {
		DomainEntity res;
		Assert.notNull(domainEntity);
		res = domainEntityRepository.save(domainEntity);
		return res;
	}
	
	public void delete(DomainEntity domainEntity) {
		Assert.notNull(domainEntity);
		domainEntityRepository.delete(domainEntity);
	}
	

	//-------------------------------------- Other Business methods --------------------------------------

	
}
