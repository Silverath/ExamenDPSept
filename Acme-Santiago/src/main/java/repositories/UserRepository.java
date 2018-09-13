
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	@Query("select u from User u where u.userAccount.id = ?1")
	User findByUserAcconuntId(int id);

	@Query("select stddev(u.routes.size) from User u")
	Double stddevRoutesPerUser();

	@Query("select avg(u.routes.size) from User u")
	Double avgRoutesPerUser();

	@Query("select avg(r.chirps.size) from User r")
	Double avgChirpsPerUser();

	@Query("select u from User u where u.chirps.size>(select avg(r.chirps.size) from User r)*0.75")
	Collection<User> moreChirpsThanAverage();

	@Query("select u from User u where u.chirps.size<(select avg(r.chirps.size) from User r)*0.25")
	Collection<User> lessChirpsThanAverage();

	@Query("select u from User u join u.routes p where p.id=?1")
	User findUserByRoute(int routeId);

}
