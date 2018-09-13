package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Follower;

@Component
@Transactional
public class FollowerToStringConverter implements Converter<Follower, String>{

	@Override
	public String convert(Follower follower) {
		String result;
		
		if(follower == null){
			result = null;
		}
		else{
			result = String.valueOf(follower.getId());
		}
		return result;
	}

}
