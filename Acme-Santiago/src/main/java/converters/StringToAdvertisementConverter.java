package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.AdvertisementRepository;
import domain.Advertisement;

@Component
@Transactional
public class StringToAdvertisementConverter implements Converter<String, Advertisement>{
	
	@Autowired
	private AdvertisementRepository advertisementRepository;
	
	@Override
	public Advertisement convert(String text) {
		Advertisement result;
		int advertisementId;
		
		try{
			if(StringUtils.isEmpty(text)){
				result = null;
			} else{
				advertisementId = Integer.valueOf(text);
				result = advertisementRepository.findOne(advertisementId);
			}
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		
		return result;
	}

}
