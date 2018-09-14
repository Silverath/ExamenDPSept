
package controllers;

import java.util.ArrayList;
import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import services.ReportService;
import services.RouteService;
import services.UserService;
import domain.Report;
import domain.Route;
import domain.User;
import forms.ApproveRejectForm;

@Controller
@RequestMapping("/report")
public class ReportController extends AbstractController {

	@Autowired
	private ReportService			reportService;

	@Autowired
	private UserService				userService;

	@Autowired
	private AdministratorService	adminService;

	@Autowired
	private RouteService			routeService;


	public ReportController() {
		super();
	}

	@RequestMapping(value = "/listByRoute", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int routeId) {
		ModelAndView result;
		Collection<Report> reports = new ArrayList<Report>();
		reports = this.reportService.findAllByRoute(routeId);
		result = new ModelAndView("report/list");
		result.addObject("reports", reports);
		return result;
	}

	@RequestMapping(value = "/listNotApprovedOrRejected", method = RequestMethod.GET)
	public ModelAndView listNotApprovedRejected() {
		ModelAndView result;
		Collection<Report> reports = new ArrayList<Report>();

		reports = this.reportService.findAllNotApprovedRejected();
		result = new ModelAndView("report/list");
		result.addObject("reports", reports);
		return result;
	}

	@RequestMapping(value = "/listMine", method = RequestMethod.GET)
	public ModelAndView listMine() {
		ModelAndView result;
		Collection<Report> reports = new ArrayList<Report>();
		final User principal = this.userService.findByPrincipal();
		reports = this.reportService.findAllByUser(principal.getId());
		result = new ModelAndView("report/list");
		result.addObject("reports", reports);
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		final Report report = this.reportService.create();
		result = this.createEditModelAndView(report);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int reportId) {
		Assert.notNull(reportId);
		ModelAndView result;
		final Report report = this.reportService.findOneToEdit(reportId);
		result = this.createEditModelAndView(report);
		return result;
	}

	@RequestMapping(value = "/approve", method = RequestMethod.GET)
	public ModelAndView approve(@RequestParam final int reportId) {
		Assert.notNull(reportId);
		ModelAndView result;
		final Report report = this.reportService.findOneToRejectOrApprove(reportId);
		final ApproveRejectForm approveRejectForm = new ApproveRejectForm();
		approveRejectForm.setReport(report);
		result = this.approveModelAndView(approveRejectForm);
		return result;
	}

	@RequestMapping(value = "/approve", method = RequestMethod.POST, params = "save")
	public ModelAndView saveApprove(@Valid final ApproveRejectForm approveRejectForm, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors())
			result = this.approveModelAndView(approveRejectForm);
		else
			try {
				final Report report = approveRejectForm.getReport();
				report.setComment(approveRejectForm.getComment());
				this.reportService.saveApprove(report);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable oops) {
				result = this.approveModelAndView(approveRejectForm);
			}

		return result;
	}

	@RequestMapping(value = "/reject", method = RequestMethod.GET)
	public ModelAndView reject(@RequestParam final int reportId) {
		Assert.notNull(reportId);
		ModelAndView result;
		final Report report = this.reportService.findOneToRejectOrApprove(reportId);
		final ApproveRejectForm approveRejectForm = new ApproveRejectForm();
		approveRejectForm.setReport(report);
		result = this.rejectModelAndView(approveRejectForm);
		return result;
	}

	@RequestMapping(value = "/reject", method = RequestMethod.POST, params = "save")
	public ModelAndView saveReject(@Valid final ApproveRejectForm approveRejectForm, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors())
			result = this.rejectModelAndView(approveRejectForm);
		else
			try {
				final Report report = approveRejectForm.getReport();
				report.setComment(approveRejectForm.getComment());
				this.reportService.saveReject(report);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable oops) {
				result = this.rejectModelAndView(approveRejectForm);
			}

		return result;
	}
	// Saving ------------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Report report, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors())
			result = this.createEditModelAndView(report);
		else
			try {
				this.reportService.save(report);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable oops) {
				result = new ModelAndView("report/edit");
				result.addObject("report", report);
				result.addObject("reportId", report.getId());
				result.addObject("message", "report.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/selectRoute", method = RequestMethod.GET)
	public ModelAndView selectNewspaper(@RequestParam final int reportId) {
		Assert.notNull(reportId);
		ModelAndView result;
		final Report report = this.reportService.findOneToSelectRoute(reportId);
		Assert.isTrue(report.getFinalMode());
		result = this.selectNewspaperModelAndView(report);
		return result;
	}

	@RequestMapping(value = "/selectRoute", method = RequestMethod.POST, params = "save")
	public ModelAndView saveSelectNewspaper(@Valid final Report report, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors())
			result = this.selectNewspaperModelAndView(report);
		else
			try {
				this.reportService.onlySave(report);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable oops) {
				result = new ModelAndView("report/selectRoute");
				result.addObject("report", report);
				result.addObject("message", "report.commit.error");
			}

		return result;
	}
	// Saving ------------------------------------------------------------------

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int reportId) {
		ModelAndView result;
		try {
			this.reportService.delete(reportId);
			result = new ModelAndView("redirect:/welcome/index.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/welcome/index.do");

		}

		return result;
	}

	protected ModelAndView createEditModelAndView(final Report report) {
		ModelAndView result;

		result = this.createEditModelAndView(report, null);

		return result;
	}
	protected ModelAndView createEditModelAndView(final Report report, final String message) {
		ModelAndView result;

		result = new ModelAndView("report/edit");
		result.addObject("report", report);
		result.addObject("message", message);

		return result;
	}

	protected ModelAndView approveModelAndView(final ApproveRejectForm approveRejectForm) {
		ModelAndView result;

		result = this.approveModelAndView(approveRejectForm, null);

		return result;
	}
	protected ModelAndView approveModelAndView(final ApproveRejectForm approveRejectForm, final String message) {
		ModelAndView result;

		result = new ModelAndView("report/approveReject");
		result.addObject("approveRejectForm", approveRejectForm);
		result.addObject("message", message);
		result.addObject("requestURI", "report/approve.do");

		return result;
	}

	protected ModelAndView rejectModelAndView(final ApproveRejectForm approveRejectForm) {
		ModelAndView result;

		result = this.rejectModelAndView(approveRejectForm, null);

		return result;
	}
	protected ModelAndView rejectModelAndView(final ApproveRejectForm approveRejectForm, final String message) {
		ModelAndView result;

		result = new ModelAndView("report/approveReject");
		result.addObject("approveRejectForm", approveRejectForm);
		result.addObject("message", message);
		result.addObject("requestURI", "report/reject.do");

		return result;
	}

	protected ModelAndView selectNewspaperModelAndView(final Report report) {
		ModelAndView result;

		result = this.selectNewspaperModelAndView(report, null);

		return result;
	}
	protected ModelAndView selectNewspaperModelAndView(final Report report, final String message) {
		ModelAndView result;
		final User principal = this.userService.findByPrincipal();
		final Collection<Route> routes = this.routeService.findByUser(principal.getId());
		result = new ModelAndView("report/selectRoute");
		result.addObject("report", report);
		result.addObject("routes", routes);
		result.addObject("message", message);

		return result;
	}
}
