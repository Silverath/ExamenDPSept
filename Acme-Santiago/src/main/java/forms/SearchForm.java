
package forms;

import org.hibernate.validator.constraints.SafeHtml;

public class SearchForm {

	private String	keyword;


	@SafeHtml
	public String getKeyword() {
		return this.keyword;
	}
	public void setKeyword(final String keyword) {
		this.keyword = keyword;
	}
}
