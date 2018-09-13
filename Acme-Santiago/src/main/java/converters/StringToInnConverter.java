package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.InnRepository;
import domain.Inn;

@Component
@Transactional
public class StringToInnConverter implements Converter<String, Inn>{
	
	@Autowired
	private InnRepository innRepository;
	
	@Override
	public Inn convert(String text) {
		Inn result;
		int innId;
		
		try{
			if(StringUtils.isEmpty(text)){
				result = null;
			} else{
				innId = Integer.valueOf(text);
				result = innRepository.findOne(innId);
			}
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		
		return result;
	}

}
