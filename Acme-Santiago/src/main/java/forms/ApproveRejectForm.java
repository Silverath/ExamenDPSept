
package forms;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

import domain.Report;

public class ApproveRejectForm {

	private String	comment;
	private Report	report;


	public Report getReport() {
		return this.report;
	}

	public void setReport(final Report report) {
		this.report = report;
	}

	@NotBlank
	@Length(min = 0, max = 500)
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getComment() {
		return this.comment;
	}

	public void setComment(final String comment) {
		this.comment = comment;
	}
}
