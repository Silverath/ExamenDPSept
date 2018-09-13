package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.AdministratorRepository;
import domain.Administrator;

@Component
@Transactional
public class StringToAdministratorConverter implements Converter<String, Administrator>{
	
	@Autowired
	private AdministratorRepository administratorRepository;
	
	@Override
	public Administrator convert(String text) {
		Administrator result;
		int administratorId;
		
		try{
			if(StringUtils.isEmpty(text)){
				result = null;
			} else{
				administratorId = Integer.valueOf(text);
				result = administratorRepository.findOne(administratorId);
			}
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		
		return result;
	}

}