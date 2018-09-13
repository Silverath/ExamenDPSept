package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.FollowerRepository;
import domain.Follower;

@Component
@Transactional
public class StringToFollowerConverter implements Converter<String, Follower>{
	
	@Autowired
	private FollowerRepository followerRepository;
	
	@Override
	public Follower convert(String text) {
		Follower result;
		int followerId;
		
		try{
			if(StringUtils.isEmpty(text)){
				result = null;
			} else{
				followerId = Integer.valueOf(text);
				result = followerRepository.findOne(followerId);
			}
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		
		return result;
	}

}
