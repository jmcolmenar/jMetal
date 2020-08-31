package jmetal;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Semaphore;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
	private static volatile Boolean[] finished;
	static private Semaphore continuar;
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
	
	
	

	@RequestMapping(value = "/experimentResultPage")
	public void experimentResultPage(Model model, @RequestBody MultiValueMap<String, String> values) throws InterruptedException {
		continuar = new Semaphore(0);
		List<String> parameters = new ArrayList<>();
	
	//-----------------------  set problem of the experiemnt------------------------------
		List<String> problemValue = values.get("problem");
		String[] problemsSplited = problemValue.get(0).split("-");
		List<Parameter> problemParams = parameterRepository.findParametersByProblemId(Long.valueOf(problemsSplited[0]));
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
		experimentProblem.setNumberOfParameter(problemRepository.findOne(Long.valueOf(problemsSplited[0])).getNumberOfParameter());
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
		finished = new Boolean[runCount];
		finished[runCount-1] = false;
		
//		
		final String finalId = id;
		final WebPageAlgorithm 	finalExperimentAlgorithm = experimentAlgorithm;
		final WebPageMutation finalExperimentMutation = experimentMutation;
		final WebPageCrossover finalExperimentCrossover = experimentCrossover;
		final WebPageSelection finalExperimentSelection = experimentSelection;
		new Thread(() -> {
			processInitiateExperiment(finalId, finalExperimentAlgorithm, experimentProblem, finalExperimentCrossover, finalExperimentMutation, finalExperimentSelection, runCount);
		}).start();
		
	}
	
	public void processInitiateExperiment(String Id, WebPageAlgorithm experimentAlgorithm, WebPageProblem experimentProblem, WebPageCrossover experimentCrossover, WebPageMutation experimentMutation,
			WebPageSelection experimentSelection, int runCount) {
		
		
		for (int j = 1; j <= runCount; j++) {
			
			finished[j-1] = false;
			String idAux = Id+j;
			WebPageExperiment exp = new WebPageExperiment(idAux, experimentAlgorithm, experimentProblem, experimentCrossover, experimentMutation,
					experimentSelection, runCount);
			experimentRepository.save(exp);
			setExpeeriment(exp);
			
			ExperiementCreateController experiementCreateController = 
					new ExperiementCreateController(exp, experimentRepository, resultRepository, finalresultsRepository,
							problemRepository, algorithmRepository, crossoverRepository, mutationRepository,
							selectionRepository,parameterRepository);
				
			try {
				processRunExperiment(experiementCreateController,j);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (!finished[0]) {
				processShowResultPage();
			}
			pushed = true;
			while (pushed) {
			}
//			continuar.release();
//			continuar.acquire();
		}
	}

	public String processShowResultPage() {
		return "experimentResultPage";
	} 
	
	public static void processRunExperiment(ExperiementCreateController experiementCreateController, int i) throws InterruptedException {
		new Thread(() -> {
//			try {
//				continuar.acquire();
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
			while (!pushed) {
			}
			experiementCreateController.createExperimentAndRun();
			finished[i-1] = true;
			pushed = false;
		}).start();
//		continuar.release();
		
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
	
	public WebPageExperiment getExperiment() {
		return experiment;
	}

	public void setExpeeriment(WebPageExperiment exp) {
		this.experiment = exp;
	}
}
