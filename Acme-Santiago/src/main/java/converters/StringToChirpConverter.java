package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.ChirpRepository;
import domain.Chirp;

@Component
@Transactional
public class StringToChirpConverter implements Converter<String, Chirp>{
	
	@Autowired
	private ChirpRepository chirpRepository;
	
	@Override
	public Chirp convert(String text) {
		Chirp result;
		int chirpId;
		
		try{
			if(StringUtils.isEmpty(text)){
				result = null;
			} else{
				chirpId = Integer.valueOf(text);
				result = chirpRepository.findOne(chirpId);
			}
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		
		return result;
	}

}
