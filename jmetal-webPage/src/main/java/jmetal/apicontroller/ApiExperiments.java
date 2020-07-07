package jmetal.apicontroller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import jmetal.grafics.Grafics;
import jmetal.javaclass.Experiment;
import jmetal.javaclass.Result;
import jmetal.javaropository.ExperimentRepository;
import jmetal.javaropository.ResultRepository;


@RestController
public class ApiExperiments {
	
	@Autowired
	private ExperimentRepository experimentRepository;
	@Autowired
	private ResultRepository resultRepository;
	
	
}
