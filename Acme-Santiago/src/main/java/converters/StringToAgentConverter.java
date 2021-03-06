
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.AgentRepository;
import domain.Agent;

@Component
@Transactional
public class StringToAgentConverter implements Converter<String, Agent> {

	@Autowired
	private AgentRepository	agentRepository;


	@Override
	public Agent convert(final String text) {
		Agent result;
		int agentId;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				agentId = Integer.valueOf(text);
				result = this.agentRepository.findOne(agentId);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
