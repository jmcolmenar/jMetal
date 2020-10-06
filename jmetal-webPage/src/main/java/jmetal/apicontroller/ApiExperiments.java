package jmetal.apicontroller;

import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import jmetal.javaclass.WebPageAlgorithm;
import jmetal.javaclass.WebPageCrossover;
import jmetal.javaclass.WebPageMutation;
import jmetal.javaclass.Parameter;
import jmetal.javaclass.WebPageProblem;
import jmetal.javaclass.WebPageSelection;
import jmetal.javarepository.AlgorithmRepository;
import jmetal.javarepository.CrossoverRepository;
import jmetal.javarepository.MutationRepository;
import jmetal.javarepository.ParameterRepository;
import jmetal.javarepository.ProblemRepository;
import jmetal.javarepository.SelectionRepository;

/**
 * 
 * @author Harender
 *
 */

@RestController
public class ApiExperiments {
	
	@Autowired
	private ProblemRepository problemRepository;
	@Autowired
	private AlgorithmRepository algorithmRepository;
	@Autowired
	private CrossoverRepository crossoverRepository;
	@Autowired
	private MutationRepository mutationRepository;
	@Autowired
	private SelectionRepository selectionRepository;
	@Autowired
	private ParameterRepository parameterRepository;

	@RequestMapping("/getProblemList")
	@ResponseBody
	public String getProblemList(Model model) {
		List<WebPageProblem> problemList = problemRepository.findAllInitialProblems();
		
		JSONArray jsonProblemName = new JSONArray();
		JSONObject jsonObject = new JSONObject();
		for (WebPageProblem problem : problemList) {
			jsonProblemName.put(problem);
		}
		
		jsonObject.putOpt("problemName", jsonProblemName);
		return jsonObject.toString();
	}
	
	public List<String> getProblemParams(List<Parameter> parameters) {
		
		List<String> params = new LinkedList<>();
		for (Parameter parameter : parameters) {
			params.add(parameter.toString());							
		}
		
		return params;
	}
	
	@RequestMapping("/getAlgorithmsList/{resultType}")
	@ResponseBody
	public String getAlgorithmsList(@PathVariable String resultType) {
		String[] results = resultType.split("-");
		WebPageProblem problem = problemRepository.getOne(Long.valueOf(results[0]));
		List<Parameter> parameters = parameterRepository.findParametersByProblemId(Long.valueOf(results[0]));
		List<String> problemParams = getProblemParams(parameters);
		List<WebPageAlgorithm>	alrithmsList= algorithmRepository.findBySolutionType(results[results.length-1]);
		
		JSONArray jsonAlgorithmsName = new JSONArray();
		JSONArray jsonProblemParams = new JSONArray();
		JSONObject jsonObject = new JSONObject();
		
		for (WebPageAlgorithm algorithm : alrithmsList) {
			jsonAlgorithmsName.put(algorithm);
		}
		for (String param : problemParams) {
			jsonProblemParams.put(param);
		}
		
		jsonObject.putOpt("algorithmsName", jsonAlgorithmsName);
		jsonObject.putOpt("problemParams", jsonProblemParams);
		jsonObject.putOpt("problemDescription", problem.getDescription());
		return jsonObject.toString();
	}
	
	@RequestMapping("/getAlgorithmParams/{algorithm}")
	@ResponseBody
	public String getAlgorithmParams(@PathVariable String algorithm) {
		String[] algo = algorithm.split("-");
		
		List<Parameter> parameters = parameterRepository.findParametersByAlgorithmId(Long.valueOf(algo[0]));
		List<String> problemParams = getProblemParams(parameters);
		WebPageAlgorithm algorithmD = algorithmRepository.findOne(Long.valueOf(algo[0]));
		JSONArray jsonAlgorithmParams = new JSONArray();
		JSONObject jsonObject = new JSONObject();
		
		for (String param : problemParams) {
			jsonAlgorithmParams.put(param);
		}
	
		jsonObject.putOpt("algorithmParams", jsonAlgorithmParams);
		jsonObject.putOpt("algorithmDescription", algorithmD.getDescription());
		return jsonObject.toString();
	}
	
	@RequestMapping("/getCrossoverList/{resultType}/{crossover}")
	@ResponseBody
	public String getCrossoverList(@PathVariable String resultType, @PathVariable String crossover) {
		String[] results = resultType.split("-");
		System.out.println(results.length);
		System.out.println(results[results.length-2]+"-"+results[results.length-1]);
		List<WebPageCrossover>	crossoverList;
		if ((results.length >3) && (results[results.length-2].equals("PermutationSolution")) ){
			crossoverList= crossoverRepository.findBySolutionPermutation(results[results.length-2]+"-"+results[results.length-1]);
		}else {
			if (crossover.equals("DifferentialEvolutionCrossover")) {
				crossoverList= crossoverRepository.findByName(crossover);
			} else {
				crossoverList= crossoverRepository.findBySolutionType(results[results.length-1]);
			}
		}
		
		JSONArray jsonCrossoverName = new JSONArray();
		for (WebPageCrossover crossovers : crossoverList) {
			jsonCrossoverName.put(crossovers);
		}
		
		JSONObject jsonObject = new JSONObject();
		
		jsonObject.putOpt("crossoverName", jsonCrossoverName);
		return jsonObject.toString();
	}
	
	@RequestMapping("/getCrossoverParams/{crossover}")
	@ResponseBody
	public String getCrossoverParams(@PathVariable String crossover) {
		String[] cross = crossover.split("-");
		
		List<Parameter> parameters = parameterRepository.findParametersByCrossoverId(Long.valueOf(cross[0]));
		List<String> crossoverParams = getProblemParams(parameters);
		
		JSONArray jsonCrossoverParams = new JSONArray();
		JSONObject jsonObject = new JSONObject();
		
		for (String param : crossoverParams) {
			jsonCrossoverParams.put(param);
		}
	
		jsonObject.putOpt("crossoverParams", jsonCrossoverParams);
		return jsonObject.toString();
	}
	
	@RequestMapping("/getMutationList/{resultType}")
	@ResponseBody
	public String getMutationList(@PathVariable String resultType) {
		String[] results = resultType.split("-");
		List<WebPageMutation> mutationList;
		if ((results.length >3) && (results[results.length-2].equals("PermutationSolution")) ){
			mutationList = mutationRepository.findBySolutionPermutation(results[results.length-2]+"-"+results[results.length-1]);
		}else {
			mutationList = mutationRepository.findBySolutionType(results[results.length-1]);
		}
		JSONArray jsonMutationName = new JSONArray();
		for (WebPageMutation mutation : mutationList) {
			jsonMutationName.put(mutation);
		}
		
		JSONObject jsonObject = new JSONObject();
		
		jsonObject.putOpt("mutationName", jsonMutationName);
		return jsonObject.toString();
	}
	
	@RequestMapping("/getMutationParams/{mutation}")
	@ResponseBody
	public String getMutationParams(@PathVariable String mutation) {
		String[] muta = mutation.split("-");
		
		List<Parameter> parameters = parameterRepository.findParametersByMutationId(Long.valueOf(muta[0]));
		List<String> mutationParams = getProblemParams(parameters);
		
		JSONArray jsonMutationParams = new JSONArray();
		JSONObject jsonObject = new JSONObject();
		
		for (String param : mutationParams) {
			jsonMutationParams.put(param);
		}
	
		jsonObject.putOpt("mutationParams", jsonMutationParams);
		return jsonObject.toString();
	}
	
	@RequestMapping("/getSelectionList/{resultType}/{listType}")
	@ResponseBody
	public String getSelectionList(@PathVariable String resultType, @PathVariable String listType) {
		String[] results = resultType.split("-");
		System.out.println(results[results.length-1]);
		List<WebPageSelection>	selectionList;
		if ((results.length >3) && (results[results.length-2].equals("PermutationSolution")) ){
			selectionList = selectionRepository.findBySolutionPermutationAndListType(results[results.length-2]+"-"+results[results.length-1], listType);
		}else {
			if (listType.equals("DifferentialEvolutionSelection")) {
				selectionList= selectionRepository.findByName(listType);
			} else {
				selectionList = selectionRepository.findBySolutionAndListType(results[results.length-1], listType);
			}
		}
		JSONArray jsonSelectionName = new JSONArray();
		for (WebPageSelection selection : selectionList) {
			jsonSelectionName.put(selection);
		}
		
		JSONObject jsonObject = new JSONObject();
		
		jsonObject.putOpt("selectionName", jsonSelectionName);
		return jsonObject.toString();
	}
	
	@RequestMapping("/getSelectionParams/{selection}")
	@ResponseBody
	public String getSelectionParams(@PathVariable String selection) {
		String[] selec = selection.split("-");
		
		List<Parameter> parameters = parameterRepository.findParametersBySelectionId(Long.valueOf(selec[0]));
		List<String> selectionParams = getProblemParams(parameters);
		
		JSONArray jsonSelectionParams = new JSONArray();
		JSONObject jsonObject = new JSONObject();
		
		for (String param : selectionParams) {
			jsonSelectionParams.put(param);
		}
	
		jsonObject.putOpt("selectionParams", jsonSelectionParams);
		return jsonObject.toString();
	}
}
