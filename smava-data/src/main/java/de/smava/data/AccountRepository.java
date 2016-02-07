package de.smava.data;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Tomek Samcik
 *
 * Repository for the Account class
 */
public interface AccountRepository extends CrudRepository<Account, Long> {
	
	List<Account> findAll();
	
	List<Account> findAllBySessionId(String sessionId);
	
	Account findByIdAndSessionId(Long id, String sessionId);
	
	@Modifying
	@Query("delete from Account a where a.sessionId = ?1")
	void deleteAllBySessionId(String sessionId);

}
