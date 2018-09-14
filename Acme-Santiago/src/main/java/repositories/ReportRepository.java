
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Report;

@Repository
public interface ReportRepository extends JpaRepository<Report, Integer> {

	@Query("select r from Report r where r.user.id = ?1")
	Collection<Report> findAllByUser(int userId);

	@Query("select r from Report r where r.route.id = ?1 and r.finalMode = TRUE and r.route != null")
	Collection<Report> findAllByRoute(int routeId);

}
