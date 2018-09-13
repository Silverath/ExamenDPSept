
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Route;

@Repository
public interface RouteRepository extends JpaRepository<Route, Integer> {

	@Query("select u.routes from User u where u.id = ?1")
	Collection<Route> findByUser(int ownerId);

	@Query("select a from Route a where a.name like %?1% or a.description like %?1%")
	Collection<Route> searchRoute(String keyword);

	@Query("select a from Route a join a.hikes h where h.name like %?1% or h.description like %?1%")
	Collection<Route> searchRouteByHike(String keyword);

	@Query("select r from Route r where r.length >= ?1 and r.length<=?2")
	Collection<Route> searchRouteByLength(Double minLength, Double maxLength);

	@Query("select r from Route r where r.hikes.size >= ?1 and r.hikes.size<=?2")
	Collection<Route> searchRouteByHikes(Integer minHikes, Integer maxHikes);

	@Query("select stddev(r.hikes.size) from Route r")
	Double stddevHikesPerRoute();

	@Query("select avg(r.hikes.size) from Route r")
	Double avgHikesPerRoute();

	@Query("select avg(r.length) from Route r")
	Double avgLengthOfRoutes();

	@Query("select stddev(r.length) from Route r")
	Double stddevLengthOfRoutes();

	@Query("select r from Route r where r.length <= (select avg(r.length) from Route r) - (3*(select stddev(r.length) from Route r)) or r.length >= (select avg(r.length) from Route r) + (3*(select stddev(r.length) from Route r))")
	Collection<Route> outliersRoutes();

	@Query("select avg(r.comments.size+h.comments.size) from Route r join r.hikes h")
	Double avgCommentsPerRoute();

	@Query("select (select count(r) from Route r join r.hikes h where h.advertisements IS NOT EMPTY)/ count(a) from Route a join a.hikes z where z.advertisements IS EMPTY")
	Double ratioRoutesWithAdvVSNot();

}
