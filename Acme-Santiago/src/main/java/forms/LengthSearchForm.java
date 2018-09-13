
package forms;

import javax.persistence.Access;
import javax.persistence.AccessType;

import org.hibernate.validator.constraints.Range;

import domain.DomainEntity;

@Access(AccessType.PROPERTY)
public class LengthSearchForm extends DomainEntity {

	private Double	minLength;
	private Double	maxLength;


	@Range(min = 0, max = 99999)
	public Double getMinLength() {
		return this.minLength;
	}

	public void setMinLength(final Double minLength) {
		this.minLength = minLength;
	}
	@Range(min = 0, max = 9999999)
	public Double getMaxLength() {
		return this.maxLength;
	}

	public void setMaxLength(final Double maxLength) {
		this.maxLength = maxLength;
	}

}
