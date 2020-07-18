$.ajax({
	url : 'chartdata',
	success : function(result) {
		$("#showResult").hide();
		category = JSON.parse(result).categories;
		serie = JSON.parse(result).series;
		saveCheck = serie[0];
		drawLiveLineChart(category, serie);
	}
})

var updateInterval = 1500;
var category = [];
var serie = [];
var saveCheck;

function drawLiveLineChart(category, series) {
	chart = Highcharts.chart('resultsGrafics', {
		chart : {
			type : 'spline',
			marginRight : 10,
			scrollablePlotArea: {
                minWidth: 2000,
                scrollPositionX: 1
            }
		},
		credits : {
			enabled : false
		},

		legend : {
			enabled : false
		},

		title : {
			text : 'Result'
		},

		xAxis : {
			title : {
				text : 'Intent'
			},
			categories : category
		},

		yAxis : [ {
			title : {
				text : 'Result'
			},
			plotLines : [ {
				value : 0,
				width : 1,
				color : '#808080'
			} ]
		} ],

		tooltip : {
			formatter : function() {
				return '<strong>' + this.x + ': </strong>' + this.y;
			}
		},

		series : [ {
			data : series
		}, {
			events : {
				legendItemClick : function(e) {
					e.preventDefault()
				}
			}
		} ]
	});

}

var update = setInterval(function() {
	updateChart()
}, updateInterval);

function updateChart(count) {
	$.get("/chartdata", function(result) {
		var end = (JSON.parse(result).finish);
		
		var cat = (JSON.parse(result).categories);
		var se = (JSON.parse(result).series);
		var newCat = category.concat(cat);
		var newSe = serie.concat(se);
		if (se.find(checkToSave)) {
			category = newCat;
			serie = newSe;
			saveCheck = se.find(checkToSave);
		} 
		if (end == true) {
			clearInterval(update);
			showFinalResult();
		}
		drawLiveLineChart(newCat, newSe);
		
	});
}

function checkToSave(num) {
	  return num != saveCheck;
	}

function showFinalResult() {
	$.get("/getFinalResult", function(result) {
		var finalResult = (JSON.parse(result).finalResult);
		var finalPopulation = (JSON.parse(result).finalPopulation);
		$("#finalResult").text("The final result is:	"+finalResult);
		$("#finalPopulation").text(finalPopulation);
		$("#showResult").show();
	});
	
	
}

function drawFullChart() {
	$.get("/showAllResultChart", function(result) {
		
		var cat = (JSON.parse(result).categories);
		var se = (JSON.parse(result).series);
		
		drawChart(cat, se);
		
	});
}

function drawChart(category, series) {
	chart = Highcharts.chart('allResultsGrafics', {
		chart : {
			type : 'spline',
			marginRight : 10,
			scrollablePlotArea: {
                minWidth: 2000,
                scrollPositionX: 1
            }
		},
		credits : {
			enabled : false
		},

		legend : {
			enabled : false
		},

		title : {
			text : 'All result grafic'
		},

		xAxis : {
			title : {
				text : 'Intent'
			},
			categories : category
		},

		yAxis : [ {
			title : {
				text : 'Result'
			},
			plotLines : [ {
				value : 0,
				width : 1,
				color : '#808080'
			} ]
		} ],

		tooltip : {
			formatter : function() {
				return '<strong>' + this.x + ': </strong>' + this.y;
			}
		},

		series : [ {
			data : series
		}, {
			events : {
				legendItemClick : function(e) {
					e.preventDefault()
				}
			}
		} ]
	});

}