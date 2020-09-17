package jmetal.javarepository;


import org.springframework.data.jpa.repository.JpaRepository;

import jmetal.javaclass.FinalResults;

public interface FinalResultsRepository extends JpaRepository<FinalResults, Long> {

	FinalResults findResultsByExperimentId(String experiment);
}

