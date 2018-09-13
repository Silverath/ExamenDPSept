
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.UserRepository;
import domain.User;

@Component
@Transactional
public class StringToUserConverter implements Converter<String, User> {

	@Autowired
	private UserRepository	userRepository;


	@Override
	public User convert(final String text) {
		User result;
		int userId;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				userId = Integer.valueOf(text);
				result = this.userRepository.findOne(userId);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
