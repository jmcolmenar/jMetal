package jmetal.javarepository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import jmetal.javaclass.WebPageProblem;

/**
 * 
 * @author Harender
 *
 */
public interface ProblemRepository extends JpaRepository<WebPageProblem, Long> {
	/*
	 * problems
	 * 
	 * id
	 * problem_name
	 * number_of_parameter
	 * solution_type
	 * initial_problem
	 * 
	 */
	
	@Query(value = "SELECT * FROM problems WHERE id = ?1", nativeQuery = true)
	WebPageProblem findByIdAndName(String id);
	
	@Query(value = "SELECT * FROM problems WHERE initial_problem = true", nativeQuery = true)
	List<WebPageProblem> findAllInitialProblems();
}
