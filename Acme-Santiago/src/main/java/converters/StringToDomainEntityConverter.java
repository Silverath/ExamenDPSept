package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.DomainEntityRepository;
import domain.DomainEntity;

@Component
@Transactional
public class StringToDomainEntityConverter implements Converter<String, DomainEntity>{
	
	@Autowired
	private DomainEntityRepository domainEntityRepository;
	
	@Override
	public DomainEntity convert(String text) {
		DomainEntity result;
		int domainEntityId;
		
		try{
			if(StringUtils.isEmpty(text)){
				result = null;
			} else{
				domainEntityId = Integer.valueOf(text);
				result = domainEntityRepository.findOne(domainEntityId);
			}
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		
		return result;
	}

}
