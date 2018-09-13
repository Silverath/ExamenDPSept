
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Hike;

@Repository
public interface HikeRepository extends JpaRepository<Hike, Integer> {

	@Query("select avg(r.length) from Hike r")
	Double avgLengthOfHikes();

	@Query("select stddev(r.length) from Hike r")
	Double stddevLengthOfHikes();

	@Query("select n from Hike n where n.advertisements IS NOT EMPTY")
	Collection<Hike> allAdvertisements();

	@Query("select n from Hike n where n.advertisements IS EMPTY")
	Collection<Hike> notAdvertisements();

}
