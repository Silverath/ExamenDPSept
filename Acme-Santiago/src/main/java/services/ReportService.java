
package services;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ReportRepository;
import domain.Administrator;
import domain.Report;
import domain.User;

@Service
@Transactional
public class ReportService {

	@Autowired
	private ReportRepository		reportRepository;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private UserService				userService;


	public ReportService() {
		super();
	}

	public Report create() {
		Report result;
		result = new Report();
		final User principal = this.userService.findByPrincipal();
		result.setUser(principal);
		result.setFinalMode(false);
		result.setTicker(this.createTicker());
		result.setApproved(false);
		result.setRejected(false);
		final Date now = new Date(System.currentTimeMillis());
		result.setMoment(now);

		return result;
	}

	public String createTicker() {

		String result;
		final String datePattern = "yyMMdd";
		final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(datePattern);
		final String moment = simpleDateFormat.format(new Date());
		String code = "";
		code += "-" + this.randomLetter() + this.randomLetter() + this.randomLetter() + this.randomLetter();
		result = moment + code;
		return result;
	}

	public char randomLetter() {
		char result;
		final String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		final Random random = new Random();
		result = alphabet.charAt(random.nextInt(52));
		return result;
	}

	public Collection<Report> findAll() {
		Collection<Report> res;
		res = this.reportRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public Report findOne(final Integer reportId) {
		final Report res = this.reportRepository.findOne(reportId);
		Assert.notNull(res);

		return res;
	}

	public Report findOneToSelectRoute(final Integer reportId) {
		final Report res = this.reportRepository.findOne(reportId);
		final User principal = this.userService.findByPrincipal();
		Assert.isTrue(res.getUser().equals(principal));
		Assert.notNull(res);
		Assert.isTrue(res.getFinalMode());
		Assert.isTrue(!res.getApproved() && !res.getRejected());
		Assert.isTrue(res.getRoute() == null);

		return res;
	}

	public Report findOneToRejectOrApprove(final Integer reportId) {
		final Report res = this.reportRepository.findOne(reportId);
		Assert.notNull(res);
		Assert.isTrue(res.getFinalMode());
		Assert.isTrue(!res.getApproved() && !res.getRejected());
		Assert.isTrue(res.getRoute() != null);
		Assert.isTrue(res.getAdministrator() == null);

		return res;
	}

	public Report save(final Report report) {
		if (report.getFinalMode() == null)
			report.setFinalMode(false);
		final Report res = this.reportRepository.save(report);
		return res;
	}

	public Report onlySave(final Report report) {
		Assert.isTrue(report.getFinalMode());
		final Report res = this.reportRepository.save(report);

		return res;
	}

	public Report saveApprove(final Report report) {
		report.setApproved(true);
		final Administrator princial = this.administratorService.findByPrincipal();
		report.setAdministrator(princial);
		final Report res = this.reportRepository.save(report);

		return res;
	}

	public Report saveReject(final Report report) {
		report.setRejected(true);
		final Administrator princial = this.administratorService.findByPrincipal();
		report.setAdministrator(princial);
		final Report res = this.reportRepository.save(report);

		return res;
	}

	public Report findOneToEdit(final Integer reportId) {
		final Report res = this.reportRepository.findOne(reportId);
		final User principal = this.userService.findByPrincipal();
		Assert.isTrue(res.getUser().equals(principal));
		Assert.notNull(res);
		Assert.isTrue(!res.getApproved() && !res.getRejected());
		Assert.isTrue(res.getRoute() == null);
		return res;
	}
	public void delete(final int reportId) {
		final Report r = this.findOne(reportId);
		final User principal = this.userService.findByPrincipal();
		Assert.isTrue(r.getUser().equals(principal));
		Assert.isTrue(r.getRoute() == null);
		Assert.notNull(r);
		this.reportRepository.delete(r);
	}

	public Collection<Report> findAllByUser(final int userId) {
		return this.reportRepository.findAllByUser(userId);
	}

	public Collection<Report> findAllByRoute(final int routeId) {
		return this.reportRepository.findAllByRoute(routeId);
	}

	public void flush() {
		this.reportRepository.flush();
	}
}
