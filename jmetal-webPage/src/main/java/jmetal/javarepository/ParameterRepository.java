package jmetal.javarepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import jmetal.javaclass.Parameter;

/**
 * 
 * @author Harender
 *
 */
public interface ParameterRepository extends JpaRepository<Parameter, Long> {

	/*
	 * parameters
	 * 
	 * id
	 * parameter_type
	 * parameter_name
	 */
	Parameter findByParameterName(String parameterName);
	Parameter findByParameterType(String parameterType);
	
	@Query(value = "SELECT p.* FROM parameters p, problems_parameters pp where p.id = pp.parameters_id and pp.web_page_problem_id = ?1", nativeQuery = true)
	List<Parameter> findParametersByProblemId(Long id);
	
	@Query(value = "SELECT p.* FROM parameters p, algorithms_parameters ap where p.id = ap.parameters_id and ap.web_page_algorithm_id = ?1", nativeQuery = true)
	List<Parameter> findParametersByAlgorithmId(Long id);
	
	@Query(value = "SELECT p.* FROM parameters p, crossovers_parameters cp where p.id = cp.parameters_id and cp.web_page_crossover_id = ?1", nativeQuery = true)
	List<Parameter> findParametersByCrossoverId(Long id);
	
	@Query(value = "SELECT p.* FROM parameters p, mutations_parameters mp where p.id = mp.parameters_id and mp.web_page_mutation_id = ?1", nativeQuery = true)
	List<Parameter> findParametersByMutationId(Long id);
	
	@Query(value = "SELECT p.* FROM parameters p, selections_parameters sp where p.id = sp.parameters_id and sp.web_page_selection_id = ?1", nativeQuery = true)
	List<Parameter> findParametersBySelectionId(Long id);
}
