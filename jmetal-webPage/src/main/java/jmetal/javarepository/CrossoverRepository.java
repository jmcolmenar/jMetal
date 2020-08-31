package jmetal.javarepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import jmetal.javaclass.WebPageCrossover;

/**
 * 
 * @author Harender
 *
 */
public interface CrossoverRepository extends JpaRepository<WebPageCrossover, Long> {

	/*
	 * id
	 * crossover_name
	 * number_of_parameter
	 * solution_type
	 */
	@Query(value = "SELECT * FROM crossovers WHERE solution_type in ('GENERIC', ?1) AND solution_type != 'null' AND crossover_name <> 'DifferentialEvolutionCrossover'", nativeQuery = true)
	List<WebPageCrossover> findBySolutionType(String solutionType);
	
	@Query(value = "SELECT * FROM crossovers WHERE crossover_name = ?1 AND solution_type != 'null'", nativeQuery = true)
	List<WebPageCrossover> findByName(String name);
}
