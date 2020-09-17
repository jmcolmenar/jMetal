package jmetal.javarepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import jmetal.javaclass.WebPageCrossover;
import jmetal.javaclass.WebPageSelection;

/**
 * 
 * @author Harender
 *
 */
public interface SelectionRepository extends JpaRepository<WebPageSelection, Long> {
	
	/*
	 * id
	 * crossover_name
	 * number_of_parameter
	 * solution_type
	 */
	@Query(value = "SELECT * FROM selections WHERE solution_type in ('GENERIC', ?1) AND solution_type != 'null'", nativeQuery = true)
	List<WebPageSelection> findByOnlySolutionType(String solutionType);
	
	@Query(value = "SELECT * FROM selections WHERE solution_type in ('GENERIC', ?1) AND solution_type != 'null' AND list_type = ?2", nativeQuery = true)
	List<WebPageSelection> findBySolutionAndListType(String solutionType, String listType);
	
	@Query(value = "SELECT * FROM selections WHERE solution_type in ('GENERIC', 'PermutationSolution', ?1) AND solution_type != 'null' AND list_type = ?2", nativeQuery = true)
	List<WebPageSelection> findBySolutionPermutationAndListType(String solutionType, String listType);
	
	@Query(value = "SELECT * FROM selections WHERE selection_name = ?1 AND solution_type != 'null'", nativeQuery = true)
	List<WebPageSelection> findByName(String name);
}
