package jmetal.javarepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import jmetal.javaclass.WebPageAlgorithm;

/**
 * 
 * @author Harender
 *
 */
public interface AlgorithmRepository extends JpaRepository<WebPageAlgorithm, Long> {

	/*
	 * id
	 * algorithm_name
	 * number_of_parameter
	 * solution_type
	 */
	@Query(value = "SELECT * FROM algorithms WHERE solution_type in ('GENERIC', ?1) AND solution_type != 'null'", nativeQuery = true)
	List<WebPageAlgorithm> findBySolutionType(String solutionType);
	
	
	
}
