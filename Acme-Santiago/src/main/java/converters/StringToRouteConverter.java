
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.RouteRepository;
import domain.Route;

@Component
@Transactional
public class StringToRouteConverter implements Converter<String, Route> {

	@Autowired
	private RouteRepository	routeRepository;


	@Override
	public Route convert(final String text) {
		Route result;
		int routeId;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				routeId = Integer.valueOf(text);
				result = this.routeRepository.findOne(routeId);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
