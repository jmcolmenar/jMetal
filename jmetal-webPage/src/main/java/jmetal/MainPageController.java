package jmetal;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jmetal.apicontroller.ExperiementCreateController;
import jmetal.javaclass.FinalResults;
import jmetal.javaclass.Parameter;
import jmetal.javaclass.Result;
import jmetal.javaclass.WebPageAlgorithm;
import jmetal.javaclass.WebPageCrossover;
import jmetal.javaclass.WebPageExperiment;
import jmetal.javaclass.WebPageMutation;
import jmetal.javaclass.WebPageProblem;
import jmetal.javaclass.WebPageSelection;
import jmetal.javarepository.AlgorithmRepository;
import jmetal.javarepository.CrossoverRepository;
import jmetal.javarepository.ExperimentRepository;
import jmetal.javarepository.FinalResultsRepository;
import jmetal.javarepository.MutationRepository;
import jmetal.javarepository.ParameterRepository;
import jmetal.javarepository.ProblemRepository;
import jmetal.javarepository.ResultRepository;
import jmetal.javarepository.SelectionRepository;

@Controller
public class MainPageController {
	
	@Autowired
	private ExperimentRepository experimentRepository; 
	@Autowired
	private ResultRepository resultRepository; 
	@Autowired
	private FinalResultsRepository finalresultsRepository; 
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
	
	private WebPageExperiment experiment;
	private static volatile Boolean pushed;
	private Boolean[] finished;
	private int count;
	
	@RequestMapping("/")
	public String mainPage(Model model) {
		return "index";
	}
	
	@RequestMapping("/chart")
	public String chart(Model model) {
		return "chart";
	}
	
	@RequestMapping("/404")
	public String errorPage(Model model) {
		return "404";
	}
	
	@RequestMapping("/experimentPage")
	public String experimentPage(Model model) {
		return "experimentPage";
	}
	
	@RequestMapping("/myExperiments")
	public String myExperiments(Model model) {
		return "myExperiments";
	}
	

	@RequestMapping(value = "/experimentResultPage")
	public void experimentResultPage(Model model, @RequestBody LinkedMultiValueMap<String, String> values) throws InterruptedException {
		List<String> parameters = new ArrayList<>();
	
	//-----------------------  set problem of the experiemnt------------------------------
		List<String> problemValue = values.get("problem");
		String[] problemsSplited = problemValue.get(0).split("-");
		Long l = Long.valueOf(problemsSplited[0]);
		List<Parameter> problemParams = parameterRepository.findParametersByProblemId(l);
		List<Parameter> problemParameterWithValue = new LinkedList<>();
		for (int i = 0; i < problemParams.size(); i++) {
			Parameter par = new Parameter(problemParams.get(i).getParameterType(), problemParams.get(i).getParameterName(),values.get(problemParams.get(i).getParameterName()).get(0));
			parameterRepository.save(par);
			problemParameterWithValue.add(par);
			parameters.add(par.getParameterName()+"->"+par.getParameterValue());
		}
		final WebPageProblem experimentProblem = new WebPageProblem();
		experimentProblem.setProblemName(problemsSplited[1]);
		experimentProblem.setParameters(problemParameterWithValue);
		experimentProblem.setNumberOfParameter(problemRepository.findOne(l).getNumberOfParameter());
		problemRepository.save(experimentProblem);
		experimentProblem.setSolutionType(problemsSplited[2]);
		
		
	//-----------------------  set algorithm of the experiemnt------------------------------
		List<String> algorithmValue = values.get("algorithm");
		WebPageAlgorithm experimentAlgorithm = null;
		WebPageMutation experimentMutation = null;
		WebPageCrossover experimentCrossover = null;
		WebPageSelection experimentSelection = null;
		if (algorithmValue == null) {
			System.err.println();;
		} else {
			String[] algorithmsSplited = algorithmValue.get(0).split("-");
			List<Parameter> algorithmParams = parameterRepository.findParametersByAlgorithmId(Long.valueOf(algorithmsSplited[0]));
			List<Parameter> algorithmParameterWithValue = new LinkedList<>();
	
			for (int i = 0; i < algorithmParams.size(); i++) {
				switch (algorithmParams.get(i).getParameterName()) {
				case "mutationOperator":
					List<String> mutationValue = values.get("mutation");
					String[] mutationSplited = mutationValue.get(0).split("-");
					List<Parameter> mutationParams = parameterRepository.findParametersByMutationId(Long.valueOf(mutationSplited[0]));
					List<Parameter> mutationParameterWithValue = new LinkedList<>();
					for (int j = 0; j < mutationParams.size(); j++) {
						Parameter par =  new Parameter(mutationParams.get(j).getParameterType(), mutationParams.get(j).getParameterName(),values.get(mutationParams.get(j).getParameterName()).get(0));
						parameterRepository.save(par);
						mutationParameterWithValue.add(par);
						parameters.add(par.getParameterName()+"->"+par.getParameterValue());
					}
					
					experimentMutation = new WebPageMutation();
					experimentMutation.setMutationName(mutationSplited[1]);
					experimentMutation.setParameters(mutationParameterWithValue);
					experimentMutation.setNumberOfParameter(mutationRepository.findOne(Long.valueOf(mutationSplited[0])).getNumberOfParameter());
					mutationRepository.save(experimentMutation);
					Parameter parM = new Parameter("MutationOperator", "mutationOperator");
					parameterRepository.save(parM);
					algorithmParameterWithValue.add(parM);
					break;
	
				case "crossoverOperator":
					List<String> crossoverValue = values.get("crossover");
					String[] crossoverSplited = crossoverValue.get(0).split("-");
					List<Parameter> crossoverParams = parameterRepository.findParametersByCrossoverId(Long.valueOf(crossoverSplited[0]));
					List<Parameter> crossoverParameterWithValue = new LinkedList<>();
					for (int j = 0; j < crossoverParams.size(); j++) {
						Parameter par = new Parameter(crossoverParams.get(j).getParameterType(), crossoverParams.get(j).getParameterName(),values.get(crossoverParams.get(j).getParameterName()).get(0));
						parameterRepository.save(par);
						crossoverParameterWithValue.add(par);
						parameters.add(par.getParameterName()+"->"+par.getParameterValue());
					}
					
					experimentCrossover = new WebPageCrossover();
					experimentCrossover.setCrossoverName(crossoverSplited[1]);
					experimentCrossover.setParameters(crossoverParameterWithValue);
					experimentCrossover.setNumberOfParameter(crossoverRepository.findOne(Long.valueOf(crossoverSplited[0])).getNumberOfParameter());
					crossoverRepository.save(experimentCrossover);
					Parameter parC = new Parameter("CrossoverOperator", "crossoverOperator");
					parameterRepository.save(parC);
					algorithmParameterWithValue.add(parC);
					break;
					
				case "selectionOperator":
					List<String> selectionValue = values.get("selectionOperator");
					String[] selectionSplited = selectionValue.get(0).split("-");
					List<Parameter> selectionParams = parameterRepository.findParametersBySelectionId(Long.valueOf(selectionSplited[0]));
					List<Parameter> selectionParameterWithValue = new LinkedList<>();
					for (int j = 0; j < selectionParams.size(); j++) {
						Parameter par = new Parameter(selectionParams.get(j).getParameterType(), selectionParams.get(j).getParameterName(),values.get(selectionParams.get(j).getParameterName()).get(0));
						parameterRepository.save(par);
						selectionParameterWithValue.add(par);
						parameters.add(par.getParameterName()+"->"+par.getParameterValue());
					}
					
					experimentSelection = new WebPageSelection();
					experimentSelection.setSelectionName(selectionSplited[1]);
					experimentSelection.setParameters(selectionParameterWithValue);
					experimentSelection.setNumberOfParameter(selectionRepository.findOne(Long.valueOf(selectionSplited[0])).getNumberOfParameter());
					selectionRepository.save(experimentSelection);
					Parameter parS = new Parameter("MelectionOperator", "selectionOperator");
					parameterRepository.save(parS);
					algorithmParameterWithValue.add(parS);
					break;
	
				default:
					Parameter par = new Parameter(algorithmParams.get(i).getParameterType(), algorithmParams.get(i).getParameterName(),values.get(algorithmParams.get(i).getParameterName()).get(0));
					parameterRepository.save(par);
					algorithmParameterWithValue.add(par);
					parameters.add(par.getParameterName()+"->"+par.getParameterValue());
					break;
				}
			}
			
			experimentAlgorithm = new WebPageAlgorithm();
			experimentAlgorithm.setAlgorithmName(algorithmsSplited[1]);
			experimentAlgorithm.setParameters(algorithmParameterWithValue);
			experimentAlgorithm.setNumberOfParameter(algorithmRepository.findOne(Long.valueOf(algorithmsSplited[0])).getNumberOfParameter());
			algorithmRepository.save(experimentAlgorithm);
			
		}
		
		model.addAttribute("selectedAlgorithm", experimentAlgorithm.getAlgorithmName());
		model.addAttribute("selectedProblem", experimentProblem.getProblemName());
		if (experimentCrossover != null) {
			model.addAttribute("selectedCrossover", experimentCrossover.getCrossoverName());
		}else {
			model.addAttribute("selectedCrossover", "");
		}
		if (experimentMutation != null) {
			model.addAttribute("selectedMutation", experimentMutation.getMutationName());
		}else {
			model.addAttribute("selectedMutation", "");
		}
		if (experimentSelection != null) {
			model.addAttribute("selectedSelectionOperator", experimentSelection.getSelectionName());
		}else {
			model.addAttribute("selectedSelectionOperator", "");
		}
		model.addAttribute("parameters", parameters);

		count = 0;
		pushed = false;
		String id =values.get("experimentName").get(0)+"0-";
		do {
			List<String> idCount = null;
			idCount = experimentRepository.getExperimentId(id); 
			if (idCount.size() == 0 ) {
				id = values.get("experimentName").get(0)+"0-";
			} else {
				String[] s = idCount.get(0).split("-");
				id = values.get("experimentName").get(0)+(Character.getNumericValue(s[0].charAt(s[0].length()-1))+1)+"-";
			}
			
		} while (experimentRepository.countExperimentsById(id) !=0);
		
		int runCount = Integer.parseInt(values.get("numberOfRepetition").get(0));
		model.addAttribute("experimentName", id);
		model.addAttribute("numberOfRepetition", runCount);
		Boolean[] finishedTh = new Boolean[runCount]; 
		finishedTh[runCount-1] = false;
		initiateFinished(runCount);
		
		final String finalId = id;
		final WebPageAlgorithm 	finalExperimentAlgorithm = experimentAlgorithm;
		final WebPageMutation finalExperimentMutation = experimentMutation;
		final WebPageCrossover finalExperimentCrossover = experimentCrossover;
		final WebPageSelection finalExperimentSelection = experimentSelection;
		new Thread(() -> {
			processInitiateExperiment(finalId, finalExperimentAlgorithm, experimentProblem, finalExperimentCrossover, finalExperimentMutation, finalExperimentSelection, runCount, finishedTh);
		}).start();
		
	}
	
	public void processInitiateExperiment(String Id, WebPageAlgorithm experimentAlgorithm, WebPageProblem experimentProblem, WebPageCrossover experimentCrossover, WebPageMutation experimentMutation,
			WebPageSelection experimentSelection, int runCount,Boolean[] finishedTh) {
		
		
		for (int j = 1; j <= runCount; j++) {
			
			finishedTh[j-1] = false;
			String idAux = Id+j;
			WebPageExperiment exp = new WebPageExperiment(idAux, experimentAlgorithm, experimentProblem, experimentCrossover, experimentMutation,
					experimentSelection, runCount);
			experimentRepository.save(exp);
			setExperiment(exp);
			
			ExperiementCreateController experiementCreateController = 
					new ExperiementCreateController(exp, experimentRepository, resultRepository, finalresultsRepository,
							problemRepository, algorithmRepository, crossoverRepository, mutationRepository,
							selectionRepository,parameterRepository);
				
			try {
				processRunExperiment(experiementCreateController,j,finishedTh);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (!finishedTh[0]) {
				processShowResultPage();
			}else {
				setFinished(finishedTh);
			}
			pushed = true;
			while (pushed) {
			}
		}
	}

	public String processShowResultPage() {
		return "experimentResultPage";
	} 
	
	public static void processRunExperiment(ExperiementCreateController experiementCreateController, int i, Boolean[] finishedTh) throws InterruptedException {
		new Thread(() -> {
			while (!pushed) {
			}
			experiementCreateController.createExperimentAndRun();
			finishedTh[i-1] = true;
			pushed = false;
		}).start();
	}

	@RequestMapping("/chartdata")
	@ResponseBody
	public String getDataFromDB(){
		WebPageExperiment exp = getExperiment();
		List<Result> dataList = resultRepository.findResultsByExperimentId(exp.getId());
		
		JSONArray jsonArrayId = new JSONArray();
		JSONArray jsonArrayValue = new JSONArray();
		JSONObject jsonObject = new JSONObject();

		int x;
		for(x = count; x < dataList.size(); x++) {
			jsonArrayId.put(x+1);
			jsonArrayValue.put(dataList.get(x).getValue());
		};
		count = dataList.size();
		jsonObject.putOpt("finish", finished);
		jsonObject.putOpt("categories", jsonArrayId);
		jsonObject.putOpt("series", jsonArrayValue);
		return jsonObject.toString();
	}
	
	@RequestMapping("/showAllResultChart")
	@ResponseBody
	public String showFullResultCharts(){
		WebPageExperiment exp = getExperiment();
		String [] expes = exp.getId().split("-");
		List<String> experimentsList = experimentRepository.getExperimentId(expes[0]);
		List<List<Result>> dataList = new ArrayList<>();
		
		
		for (String s : experimentsList) {
			dataList.add(resultRepository.findResultsByExperimentId(s));
		}
		
		JSONArray jsonArrayNames = new JSONArray();
		JSONArray jsonArrayId = new JSONArray();
		JSONArray jsonArrayValue = new JSONArray();
		JSONObject jsonObject = new JSONObject();

		for(int x = 0; x < dataList.size(); x++) {
			int[] cat = new int[dataList.get(x).size()];
			double[] se = new double[dataList.get(x).size()];
			for(int j=0; j<dataList.get(x).size(); j++) {
				cat[j] = j+1;
				se[j] = dataList.get(x).get(j).getValue();
			}
			jsonArrayNames.put(experimentsList.get(x));
			jsonArrayId.put(cat);
			jsonArrayValue.put(se);				
		};
		
		jsonObject.putOpt("names", jsonArrayNames);
		jsonObject.putOpt("categories", jsonArrayId);
		jsonObject.putOpt("series", jsonArrayValue);
		return jsonObject.toString();
	}
	
	@RequestMapping("/getFinalResult/{id}")
	@ResponseBody
	public String getFinalResult(@PathVariable int id){
		WebPageExperiment exp = getExperiment();
		String [] expes = exp.getId().split("-");
		FinalResults finalResultData = finalresultsRepository.findResultsByExperimentId(expes[0]+"-"+id);
		
		JSONObject jsonObject = new JSONObject();

		jsonObject.putOpt("name", expes[0]+"-"+id);
		jsonObject.putOpt("finalResult", finalResultData.getResultValue());
		jsonObject.putOpt("finalPopulation", finalResultData.getLastPopulation());
		return jsonObject.toString();
	}
	
	@RequestMapping("/playExperiment/{id}")
	public void playExperiment(Model model, @PathVariable String id) throws InterruptedException {
		List<WebPageExperiment> experimentList = experimentRepository.getAllExperimentById(id);
		WebPageProblem problem;
		WebPageAlgorithm algorithm;
		WebPageCrossover crossover;
		WebPageSelection selection;
		WebPageMutation mutation;
		
		
		problem = experimentList.get(0).getProblem();
		algorithm = experimentList.get(0).getAlgorithm();
		crossover = experimentList.get(0).getCrossover();
		selection = experimentList.get(0).getSelectionOperator();
		mutation = experimentList.get(0).getMutation();
		
		LinkedMultiValueMap<String, String> mapToPass = new LinkedMultiValueMap<>();
		
		mapToPass.put("experimentName", Arrays.asList(id));
		mapToPass.put("numberOfRepetition", Arrays.asList(String.valueOf(experimentList.get(0).getNumberOfRepetition())));
		mapToPass.put("problem", Arrays.asList(problem.toString()));
		mapToPass.putAll(setParameterMap(parameterRepository.findParametersByProblemId(problem.getId())));
		
		mapToPass.put("algorithm", Arrays.asList(algorithm.toString()));
		mapToPass.putAll(setParameterMap(parameterRepository.findParametersByAlgorithmId(algorithm.getId())));
		
		if (crossover != null) {
			mapToPass.put("crossover", Arrays.asList(crossover.toString()));
			mapToPass.putAll(setParameterMap(parameterRepository.findParametersByCrossoverId(crossover.getId())));
		}
		if (mutation != null) {
			mapToPass.put("mutation", Arrays.asList(mutation.toString()));
			mapToPass.putAll(setParameterMap(parameterRepository.findParametersByMutationId(mutation.getId())));
		}
		if (selection != null) {
			mapToPass.put("selectionOperator", Arrays.asList(selection.toString()));
			mapToPass.putAll(setParameterMap(parameterRepository.findParametersBySelectionId(selection.getId())));
		}
		
	/*	new Thread(() -> {
		try {
			experimentResultPage(model, mapToPass);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		});
		
		processShowResultPage();*/
	}
	
	@RequestMapping("/showExperiment")
	public String showExpe(Model model, @RequestBody LinkedMultiValueMap<String, String> value) {
		
		String id = value.get("experimentList").get(0);
		List<WebPageExperiment> experimentList = experimentRepository.getAllExperimentById(id);
		WebPageProblem problem;
		WebPageAlgorithm algorithm;
		WebPageCrossover crossover;
		WebPageSelection selection;
		WebPageMutation mutation;
		
		
		problem = experimentList.get(0).getProblem();
		algorithm = experimentList.get(0).getAlgorithm();
		crossover = experimentList.get(0).getCrossover();
		selection = experimentList.get(0).getSelectionOperator();
		mutation = experimentList.get(0).getMutation();
		
		List<String> parameters = new ArrayList<>();
		parameters.addAll(setParameterList(parameterRepository.findParametersByProblemId(problem.getId())));
		parameters.addAll(setParameterList(parameterRepository.findParametersByAlgorithmId(algorithm.getId())));
		
		model.addAttribute("experimentName", id);
		model.addAttribute("numberOfRepetition", experimentList.get(0).getNumberOfRepetition());
		model.addAttribute("selectedAlgorithm", algorithm.getAlgorithmName());
		model.addAttribute("selectedProblem", problem.getProblemName());
		if (crossover != null) {
			model.addAttribute("selectedCrossover", crossover.getCrossoverName());
			parameters.addAll(setParameterList(parameterRepository.findParametersByCrossoverId(crossover.getId())));
		}else {
			model.addAttribute("selectedCrossover", "");
		}
		if (mutation != null) {
			model.addAttribute("selectedMutation", mutation.getMutationName());
			parameters.addAll(setParameterList(parameterRepository.findParametersByMutationId(mutation.getId())));
		}else {
			model.addAttribute("selectedMutation", "");
		}
		if (selection != null) {
			model.addAttribute("selectedSelectionOperator", selection.getSelectionName());
			parameters.addAll(setParameterList(parameterRepository.findParametersBySelectionId(selection.getId())));
		}else {
			model.addAttribute("selectedSelectionOperator", "");
		}
		model.addAttribute("parameters", parameters);
		
		initiateFinished(experimentList.size());
		Boolean[] finishAux = new Boolean[experimentList.size()];
		for (int i = 0; i < finishAux.length; i++) {
			finishAux[i] = true;
		}
		setFinished(finishAux);
		setExperiment(experimentList.get(0));
		
		return "experimentResultPage";
		
		
		
	}
	
	
	public List<String> setParameterList(List<Parameter> params){
		List<String> parameters = new ArrayList<>();
		for (int i = 0; i < params.size(); i++) {
			parameters.add(params.get(i).getParameterName()+"->"+params.get(i).getParameterValue());
		}
		
		return parameters;
	}
	
	public LinkedMultiValueMap<String, String> setParameterMap(List<Parameter> params){
		LinkedMultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
		for (int i = 0; i < params.size(); i++) {
			parameters.put(params.get(i).getParameterName(),Arrays.asList(params.get(i).getParameterValue()));
		}
		
		return parameters;
	}
	
	public Boolean[] getFinished() {
		return finished;
	}

	
	public  void setFinished(Boolean[] finished) {
		this.finished = finished;
	}
	
	public void initiateFinished(int length) {
		this.finished = new Boolean[length];
		this.finished[length-1] = false;
	}
	public WebPageExperiment getExperiment() {
		return experiment;
	}

	public void setExperiment(WebPageExperiment exp) {
		this.experiment = exp;
	}

	public ExperimentRepository getExperimentRepository() {
		return experimentRepository;
	}

	public void setExperimentRepository(ExperimentRepository experimentRepository) {
		this.experimentRepository = experimentRepository;
	}

	public ResultRepository getResultRepository() {
		return resultRepository;
	}

	public void setResultRepository(ResultRepository resultRepository) {
		this.resultRepository = resultRepository;
	}

	public FinalResultsRepository getFinalresultsRepository() {
		return finalresultsRepository;
	}

	public void setFinalresultsRepository(FinalResultsRepository finalresultsRepository) {
		this.finalresultsRepository = finalresultsRepository;
	}

	public ProblemRepository getProblemRepository() {
		return problemRepository;
	}

	public void setProblemRepository(ProblemRepository problemRepository) {
		this.problemRepository = problemRepository;
	}

	public AlgorithmRepository getAlgorithmRepository() {
		return algorithmRepository;
	}

	public void setAlgorithmRepository(AlgorithmRepository algorithmRepository) {
		this.algorithmRepository = algorithmRepository;
	}

	public CrossoverRepository getCrossoverRepository() {
		return crossoverRepository;
	}

	public void setCrossoverRepository(CrossoverRepository crossoverRepository) {
		this.crossoverRepository = crossoverRepository;
	}

	public MutationRepository getMutationRepository() {
		return mutationRepository;
	}

	public void setMutationRepository(MutationRepository mutationRepository) {
		this.mutationRepository = mutationRepository;
	}

	public SelectionRepository getSelectionRepository() {
		return selectionRepository;
	}

	public void setSelectionRepository(SelectionRepository selectionRepository) {
		this.selectionRepository = selectionRepository;
	}

	public ParameterRepository getParameterRepository() {
		return parameterRepository;
	}

	public void setParameterRepository(ParameterRepository parameterRepository) {
		this.parameterRepository = parameterRepository;
	}
	
	
}
