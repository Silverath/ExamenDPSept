package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.HikeRepository;
import domain.Hike;

@Component
@Transactional
public class StringToHikeConverter implements Converter<String, Hike>{
	
	@Autowired
	private HikeRepository hikeRepository;
	
	@Override
	public Hike convert(String text) {
		Hike result;
		int hikeId;
		
		try{
			if(StringUtils.isEmpty(text)){
				result = null;
			} else{
				hikeId = Integer.valueOf(text);
				result = hikeRepository.findOne(hikeId);
			}
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		
		return result;
	}

}
