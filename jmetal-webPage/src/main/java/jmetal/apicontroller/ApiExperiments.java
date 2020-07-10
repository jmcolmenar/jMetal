package jmetal.apicontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import jmetal.javaropository.ExperimentRepository;
import jmetal.javaropository.ResultRepository;


@RestController
public class ApiExperiments {
	
	@Autowired
	private ExperimentRepository experimentRepository;
	@Autowired
	private ResultRepository resultRepository;
	
	
}
