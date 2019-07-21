(function($j) {

	// chartIdToNameMap<Long, String> (thermostat id to name)
    var chartIdToNameMap = new Object();
	
	var methods = {
			init: function(startTime, endTime){
				// set global highcharts variable to disable UTC and default to browsers timezone when displaying time
				Highcharts.setOptions({
				    global: {
				        useUTC: false
				    }
				});
				
				// declare global variables to initialize
				var element = this;
				//$j('<h2>Thermostats:</h2>').appendTo(element);
				
				buildThermostatsView();
				
				function buildThermostatsView(){
					var socket = new SockJS("/websocket");
                    var stompClient = Stomp.over(socket);

                    // callback function to be called when stomp client is connected to server (see Note 2)
                    var connectCallback = function() {
                        console.log("connected!");
                        stompClient.subscribe('/topic/tstat-updates', function(update){
                            var newPoint = JSON.parse(update.body);
							var chart = $('#container' + parseInt(newPoint.thermostatId)).highcharts();
							chart.series[0].addPoint([newPoint.time, newPoint.currentTemp], true, true);
							chart.series[1].addPoint([newPoint.time, newPoint.targetTemp], true, true);
							chart.series[3].addPoint([newPoint.time, newPoint.tstate], true, true);

							var status = $('#status' + parseInt(newPoint.thermostatId));
							if (newPoint.tstate == 1) {
                                status.html('Running');
                            } else {
								status.html('Idle');
							}
                        });

                        stompClient.subscribe('/topic/weather-updates', function(update) {
                            var newPoint = JSON.parse(update.body);
                            for (i = 0;i < $(Highcharts.charts).length;i++) {
                                $(Highcharts.charts)[i].series[2].addPoint([newPoint.time, newPoint.currentTemp], true, true);
							}
						});
                    };

                    // callback function to be called when stomp client could not connect to server (see Note 3)
                    var errorCallback = function(error) {
                        console.log(error);
                    };

                    stompClient.connect("guest", "guest", connectCallback, errorCallback);

                    $.ajax({
		        		url:"/thermostats",
		        		cache: false, 
		        		//data:{}, 
		        		success: function(data, textStatus, jqXHR){
		        			for(var index=0;index < data.length;index++){
		        			    var thermostatId = data[index].id;
		        			    var locationId = data[index].location.id;
		        			    
		        			    chartIdToNameMap[thermostatId] = data[index].name;
		        			    
		        			    var fromTime = new Date();
		        			    fromTime.setDate(fromTime.getDate() - 7);
		        			    var toTime = new Date();
		        				$.ajax({
		                            url:"/thermostats/" + thermostatId + "/samples",
		                            type:'GET',
		                            cache: false, 
		                            data:{fromTime:fromTime.getTime(), toTime:toTime.getTime()},
		                            dataType:'json', 
		                            success: function(tstatData, textStatus, jqXHR){
		                            	
		                            	$.ajax({
		                            		url:"/weather/" + locationId + "/samples",
		                            		type:'GET',
		                            		cache: false,
		                            		data:{fromTime:fromTime.getTime(), toTime:toTime.getTime()},
		                            		dataType:'json',
		                            		success: function(weatherData, textStatus, jqXHR){
		                            			//var htmlString = '<div id="container' + tstatData[0].thermostatId + '" style="width: 800px; height: 400px; margin: 0 auto"></div>';
				                            	
				                            	//$j(htmlString).appendTo(element);
				                                
				                            	renderThermostatChart(tstatData, weatherData, 'container' + tstatData[0].thermostatId, tstatData[0].thermostatId);	
		                            		},
		                            		error: function(jqXHR, textStatus, errorThrown){
				                                alert('Error retrieving weather data from the server. ' + errorThrown);
				                            }
		                            	});
		                            }, 
		                            error: function(jqXHR, textStatus, errorThrown){
		                                alert('Error retrieving thermostat data from the server. ' + errorThrown);
		                            }
		                        }); 
		        			}
		        		},
		        		error: function(jqXHR, textStatus, errorThrown){
		        			alert('Error retrieving configuration data from the server. ' + errorThrown);
		        		}
		        	});
				}
				
				/**
				 * Load new data depending on the selected min and max
				 */
				function afterSetExtremes(e) {

					var currentExtremes = this.getExtremes(),
						range = e.max - e.min,
						chart = $('#container3').highcharts();
						
					//chart.showLoading('Loading data from server...');
					//$.getJSON('http://www.highcharts.com/samples/data/from-sql.php?start='+ Math.round(e.min) +
					//		'&end='+ Math.round(e.max) +'&callback=?', function(data) {
					//$.getJSON('http://www.highcharts.com/samples/data/from-sql.php?start='+ Math.round(e.min) +
					//		'&end='+ Math.round(e.max) +'&callback=?', function(data) {
					//	chart.series[0].setData(data);
					//	chart.hideLoading();
					//});
					var defaultSeries = new Array();
				}
				
				function renderThermostatChart(data, weatherData, renderTo, thermostatId) {
					var defaultSeries = new Array();
					var targetTempSeries = new Array();
					var tempRangeSeries = new Array();
					var tstateSeries = new Array();
					for(var i=0;i < data.length;i++){
						
						var currentTempVal = new Array();
						currentTempVal[0] = data[i].time;
						currentTempVal[1] = data[i].currentTemp;
 						defaultSeries[i] = currentTempVal;
 						
 						var targetTempVal = new Array();
 						targetTempVal[0] = data[i].time;
 						targetTempVal[1] = data[i].targetTemp;
 						targetTempSeries[i] = targetTempVal;
 						
 						var tempRangeVal = new Array();
 						tempRangeVal[0] = data[i].time;
 						tempRangeVal[1] = 70.0;
 						tempRangeVal[2] = 60.0;
 						tempRangeSeries[i] = tempRangeVal;

						var tstateVal = new Array();
						tstateVal[0] = data[i].time;
						tstateVal[1] = data[i].tstate;
					    tstateSeries[i] = tstateVal;	
	                }
					
					var outsideTempSeries = new Array();
					for(var i=0;i < weatherData.length;i++){
						var outsideTempVal = new Array();
						outsideTempVal[0] = weatherData[i].time;
						outsideTempVal[1] = weatherData[i].currentTemp;
						outsideTempSeries[i] = outsideTempVal;
					}

					// Create the chart
					Highcharts.StockChart({
					//$('#' + renderTo).highcharts('StockChart', {
					    chart: {
					    	renderTo: renderTo,
							events: {
								load: function(chart) {

								},
								selection: function(event) {
									var runTimeData = this.options.series[3].data;

									var sum = 0;
									for(var i=0;i<runTimeData.length;i++) {
										var runTimeValue = runTimeData[i];
										if(runTimeValue[0] > event.xAxis[0].min && runTimeValue[0] < event.xAxis[0].max) {
										    sum += runTimeValue[1];
										}
									}

									console.log('sum=' + sum);
								}
							},
							zoomType: 'x'
					    },

					    rangeSelector: {
							inputEnabled: $('#' + renderTo).width() > 480,
					        buttons: [{
					            type: 'hour',
					            count: 3,
					            text: '3h'
					        }, {
					            type: 'day',
					            count: 1,
					            text: '1d'
					        }, {
								type: 'all',
								text: 'All'
							}
					        ],
					        selected: 1
					    },

						yAxis: [{
							title: {
								text: 'Temperature (°F)'
							},
							height: '60%'
						}, {
							title: {
							    text: 'Runtime'
							},
							top: '65%',
							height: '35%'
						}],

					    title: {
							text: chartIdToNameMap[thermostatId]
						},

						series: [{
					        name: 'Temperature',
					        data: defaultSeries,
					        pointInterval: 60 * 1000,
					        zIndex: 2,
					        tooltip: {
					        	valueDecimals: 1,
					        	valueSuffix: '°F'
					        }
					    }, {
					    	name: 'Target Temperature',
					    	data: targetTempSeries,
					    	pointInterval: 60 * 1000,
					    	zIndex: 1,
					    	dashStyle: 'ShortDash',
					        tooltip: {
					        	valueDecimals: 1,
					        	valueSuffix: '°F'
					        }
					    }, {
					    	name: 'Outside Temperature',
					    	data: outsideTempSeries,
					    	pointInterval: 60 * 1000,
					    	zIndex: 0,
					        tooltip: {
					        	valueDecimals: 1,
					        	valueSuffix: '°F'
					        }
				  	    }, {
						   name: 'Runtime',
						   data: tstateSeries,
						   type: 'column',
						   yAxis: 1
					      }
					]

					});
				}
			}// end init
    };

    $j.fn.ThermostatsView = function(method) {
		if(methods[method]) {
			return methods[method].apply(this, Array.prototype.slice.call(arguments, 1));
		} else if(typeof method == 'object' || !method){
			return methods.init.apply(this, arguments);
		} else {
			$j.error('Method ' + method + ' does not exist on jQuery.ThermostatsView!');
		}
	};
	
})(jQuery);
