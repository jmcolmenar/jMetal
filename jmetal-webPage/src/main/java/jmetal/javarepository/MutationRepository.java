package jmetal.javarepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import jmetal.javaclass.WebPageMutation;

/**
 * 
 * @author Harender
 *
 */
public interface MutationRepository extends JpaRepository<WebPageMutation, Long> {
	/*
	 * id
	 * mutation_name
	 * number_of_parameter
	 * solution_type
	 */
	@Query(value = "SELECT * FROM mutations WHERE solution_type in ('GENERIC', ?1) AND solution_type != 'null'", nativeQuery = true)
	List<WebPageMutation> findBySolutionType(String solutionType);
}
