$.ajax({
	url : 'experimentPage',
	success : function(result) {
		hideAll();
		$("#TSPsSelection").hide();
		$("#problemDescription").hide();
		getProblems();
	}
});

function hideAll(){
	$("#algorithms").hide();
	$("#algorithms").hide();
	$("#crossoverOperator").hide();
	$("#mutationOperator").hide();
	$("#selectionOperator").hide();
	$("#algorithmDescription").hide();
	
}

function getProblems() {
	$.get("/getProblemList", function(result) {
		var problem = (JSON.parse(result).problemName);
		
		var selectProblem = document.getElementById("selectProblem");
		for (pb in problem) {
			selectProblem.options[selectProblem.options.length] = new Option(problem[pb], problem[pb]);
		}
	});
}

function getAlgorithms() {
	var solutionType = document.getElementById('selectProblem').value;
	hideAll();
	$("#TSPsSelection").hide();
	$("#algorithms").show();
	$.get("/getAlgorithmsList/"+solutionType, function(result) {
		
		var algorithms = (JSON.parse(result).algorithmsName);
		var problemParams = (JSON.parse(result).problemParams);
		var problemDescription = (JSON.parse(result).problemDescription);
		$("#problemDescription").show();
		var text = document.getElementById('problemDescription');
		text.innerHTML = problemDescription;
		
		var algorithmView = document.getElementById('algorithms');
		algorithmView = true;

		var selectAlgorithm = document.getElementById("selectAlgorithm");
		var eleIn = selectAlgorithm.getElementsByTagName("OPTION");
	    while(eleIn.length >0){
	    	eleIn[0].remove();
	    }
		for (pb in algorithms) {
			selectAlgorithm.options[selectAlgorithm.options.length] = new Option(algorithms[pb], algorithms[pb]);
		}
		
		var typeParam = "problemParams";
		setParams(problemParams,typeParam);

	});
}

function setAlgorithmParams() {
	hideAll();
	$("#algorithms").show();
	
	var algorithm = document.getElementById('selectAlgorithm').value;
	$.get("/getAlgorithmParams/"+algorithm, function(result) {
		var algorithmParams = (JSON.parse(result).algorithmParams);
		var algorithmDescription = (JSON.parse(result).algorithmDescription);
		$("#algorithmDescription").show();
		var text = document.getElementById('algorithmDescription');
		text.innerHTML = algorithmDescription;
		var typeParam = "algorithmParams";
		setParams(algorithmParams,typeParam);
	});
	
}

function setParams(params,paramsTypes){
	var paramsDiv = document.getElementById(paramsTypes);
    var eleIn = paramsDiv.getElementsByTagName("INPUT");
    while(eleIn.length >0){
    	eleIn[0].remove();
    }
    var eleBr = paramsDiv.getElementsByTagName("BR");
    while(eleBr.length >0){
    	eleBr[0].remove();
    }
    
	for (pp of params) {
		var par = pp.split("-");
		if(par[1] == "MutationOperator"){
			getMutation();
		}
		if (par[1] == "CrossoverOperator" || par[1] == "DifferentialEvolutionCrossover"){
			getCrossover(par[1]);
		}
		if (par[1] == "DifferentialEvolutionSelection"){
			getSelection(par[1]);
		}else if(par[1] == "SelectionOperator"){
			getSelection(par[2]);
		}
		var y = document.createElement("INPUT");
		if (par[1] == "int" || par[1] == "double"){
			var y = document.createElement("INPUT");
			y.setAttribute("type", "number");
			if (par[1] == "double"){
				y.setAttribute("step", "0.01");
			}
			y.setAttribute("placeholder", par[0]);
			y.setAttribute("name", par[0]);
			y.setAttribute("class", "form-control");
			y.setAttribute("min", "0");
			y.setAttribute("required", "required");
		
			paramsDiv.appendChild(y);
			
		}else if(par[1] == "double[][]"){
			var y = document.createElement("INPUT");
			y.setAttribute("type", "text");
			y.setAttribute("placeholder",+par[1] + par[0]);
			y.setAttribute("name", par[0]);
			y.setAttribute("class", "form-control");
			
			paramsDiv.appendChild(y);
		}else if(par[1] == "List<Double>"){
			var y = document.createElement("INPUT");
			y.setAttribute("type", "text");
			y.setAttribute("placeholder",+"Ej: '1, 2'" + par[0]);
			y.setAttribute("name", par[0]);
			y.setAttribute("pattern", "[0-9]+([,\.][0-9]+){1,}");
			y.setAttribute("class", "form-control");
			
			paramsDiv.appendChild(y);
		}else if(par[0] == "distanceFile"){
			showTSPs();
		}else if (par[1] != "MutationOperator" && par[1] != "CrossoverOperator" && par[1] != "SelectionOperator" && par[1] != "DifferentialEvolutionCrossover" && par[1] != "DifferentialEvolutionSelection"){
			var y = document.createElement("INPUT");
			y.setAttribute("type", "text");
			y.setAttribute("placeholder","(Optional) " + par[0]);
			y.setAttribute("name", par[0]);
			y.setAttribute("class", "form-control");
		
			paramsDiv.appendChild(y);
		}
		
	}
	
}

function getCrossover(crossover) {
	var solutionType = document.getElementById('selectProblem').value;
	$("#crossoverOperator").show();
	$.get("/getCrossoverList/"+solutionType+"/"+crossover, function(result) {
		var problem = (JSON.parse(result).crossoverName);
			
		var selectCrossover = document.getElementById("selectCrossoverOperator");
		var eleOp = selectCrossover.getElementsByTagName("OPTION");
	    while(eleOp.length >0){
	    	eleOp[0].remove();
	    }
		for (pb in problem) {
			selectCrossover.options[selectCrossover.options.length] = new Option(problem[pb], problem[pb]);
		}
	});
}

function setCrossoverParams() {
	var crossover = document.getElementById('selectCrossoverOperator').value;
	$.get("/getCrossoverParams/"+crossover, function(result) {
		var crossoverParams = (JSON.parse(result).crossoverParams);
		
		var typeParam = "crossoverParams";
		setParams(crossoverParams,typeParam);
	});
	
}


function getMutation() {
	var solutionType = document.getElementById('selectProblem').value;
	$("#mutationOperator").show();
	$.get("/getMutationList/"+solutionType, function(result) {
		var problem = (JSON.parse(result).mutationName);
		
		var selectMutation = document.getElementById("selectMutationOperator");
		var eleOp = selectMutation.getElementsByTagName("OPTION");
	    while(eleOp.length >0){
	    	eleOp[0].remove();
	    }
		for (pb in problem) {
			selectMutation.options[selectMutation.options.length] = new Option(problem[pb], problem[pb]);
		}
	});
}

function setMutationParams() {
	var mutation = document.getElementById('selectMutationOperator').value;
	$.get("/getMutationParams/"+mutation, function(result) {
		var mutationParams = (JSON.parse(result).mutationParams);
		
		var typeParam = "mutationParams";
		setParams(mutationParams,typeParam);
	});
	
}


function getSelection(listType) {
	var solutionType = document.getElementById('selectProblem').value;
	$("#selectionOperator").show();
	$.get("/getSelectionList/"+solutionType+"/"+listType, function(result) {
		var problem = (JSON.parse(result).selectionName);
		
		var selectSelection = document.getElementById("selectSelectionOperator");
		var eleOp = selectSelection.getElementsByTagName("OPTION");
	    while(eleOp.length >0){
	    	eleOp[0].remove();
	    }
		for (pb in problem) {
			selectSelection.options[selectSelection.options.length] = new Option(problem[pb], problem[pb]);
		}
	});
}

function setSelectionParams() {
	var selection = document.getElementById('selectSelectionOperator').value;
	$.get("/getSelectionParams/"+selection, function(result) {
		var selectionParams = (JSON.parse(result).selectionParams);
		
		var typeParam = "selectionParams";
		setParams(selectionParams,typeParam);
	});
	
}

function showTSPs() {
	
	$.get("/getTSPs", function(result) {
		var TSPs = (JSON.parse(result).TSPsName);
		var tspSelection = document.getElementById("TSPs");
		for (var i = 0; i < TSPs.length; i++) {
			
			var TSPOp = tspSelection.getElementsByTagName("OPTION");
		    while(TSPOp.length >0){
		    	TSPOp[0].remove();
		    }
			for (x in TSPs) {
				tspSelection.options[tspSelection.options.length] = new Option(TSPs[x], TSPs[x]);
			}
		}
	});
	$("#TSPsSelection").show();
}