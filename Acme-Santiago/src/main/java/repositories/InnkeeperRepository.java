
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Innkeeper;

@Repository
public interface InnkeeperRepository extends JpaRepository<Innkeeper, Integer> {

	@Query("select i from Innkeeper i where i.userAccount.id = ?1")
	Innkeeper findByUserAcconuntId(int id);

}
