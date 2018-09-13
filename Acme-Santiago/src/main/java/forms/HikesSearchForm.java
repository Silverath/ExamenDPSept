
package forms;

import javax.persistence.Access;
import javax.persistence.AccessType;

import domain.DomainEntity;

@Access(AccessType.PROPERTY)
public class HikesSearchForm extends DomainEntity {

	private Integer	minHikes;
	private Integer	maxHikes;


	public Integer getMinHikes() {
		return this.minHikes;
	}

	public void setMinHikes(final Integer minHikes) {
		this.minHikes = minHikes;
	}
	public Integer getMaxHikes() {
		return this.maxHikes;
	}

	public void setMaxHikes(final Integer maxHikes) {
		this.maxHikes = maxHikes;
	}

}
