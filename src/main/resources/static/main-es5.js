(window["webpackJsonp"] = window["webpackJsonp"] || []).push([["main"],{

/***/ "./$$_lazy_route_resource lazy recursive":
/*!******************************************************!*\
  !*** ./$$_lazy_route_resource lazy namespace object ***!
  \******************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

function webpackEmptyAsyncContext(req) {
	// Here Promise.resolve().then() is used instead of new Promise() to prevent
	// uncaught exception popping up in devtools
	return Promise.resolve().then(function() {
		var e = new Error("Cannot find module '" + req + "'");
		e.code = 'MODULE_NOT_FOUND';
		throw e;
	});
}
webpackEmptyAsyncContext.keys = function() { return []; };
webpackEmptyAsyncContext.resolve = webpackEmptyAsyncContext;
module.exports = webpackEmptyAsyncContext;
webpackEmptyAsyncContext.id = "./$$_lazy_route_resource lazy recursive";

/***/ }),

/***/ "./node_modules/raw-loader/index.js!./src/app/app.component.html":
/*!**************************************************************!*\
  !*** ./node_modules/raw-loader!./src/app/app.component.html ***!
  \**************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<!--The content below is only a placeholder and can be replaced.-->\n<div style=\"text-align:center\">\n  <h1>\n    {{ title }}\n  </h1>\n\n    <div *ngIf=\"isDataAvailable()\">\n      <div *ngFor=\"let tstat of thermostats\">\n        <app-output-graph [thermostat]=\"tstat\"\n                          [onTstatUpdate]=\"onTstatUpdate\"\n                          [onWeatherUpdate]=\"onWeatherUpdate\"></app-output-graph>\n      </div>\n    </div>\n\n</div>\n<router-outlet></router-outlet>\n"

/***/ }),

/***/ "./node_modules/raw-loader/index.js!./src/app/output-graph/output-graph.component.html":
/*!************************************************************************************!*\
  !*** ./node_modules/raw-loader!./src/app/output-graph/output-graph.component.html ***!
  \************************************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<div id=container{{thermostat.id}} style=\"float:left; width: 800px; height: 400px; margin: 0 auto\"></div>\n"

/***/ }),

/***/ "./src/app/app-routing.module.ts":
/*!***************************************!*\
  !*** ./src/app/app-routing.module.ts ***!
  \***************************************/
/*! exports provided: AppRoutingModule */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "AppRoutingModule", function() { return AppRoutingModule; });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/router */ "./node_modules/@angular/router/fesm5/router.js");



var routes = [];
var AppRoutingModule = /** @class */ (function () {
    function AppRoutingModule() {
    }
    AppRoutingModule = tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["NgModule"])({
            imports: [_angular_router__WEBPACK_IMPORTED_MODULE_2__["RouterModule"].forRoot(routes)],
            exports: [_angular_router__WEBPACK_IMPORTED_MODULE_2__["RouterModule"]]
        })
    ], AppRoutingModule);
    return AppRoutingModule;
}());



/***/ }),

/***/ "./src/app/app.component.css":
/*!***********************************!*\
  !*** ./src/app/app.component.css ***!
  \***********************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "\n/*# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6IiIsImZpbGUiOiJzcmMvYXBwL2FwcC5jb21wb25lbnQuY3NzIn0= */"

/***/ }),

/***/ "./src/app/app.component.ts":
/*!**********************************!*\
  !*** ./src/app/app.component.ts ***!
  \**********************************/
/*! exports provided: AppComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "AppComponent", function() { return AppComponent; });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_common_http__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/common/http */ "./node_modules/@angular/common/fesm5/http.js");
/* harmony import */ var stompjs__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! stompjs */ "./node_modules/stompjs/index.js");
/* harmony import */ var stompjs__WEBPACK_IMPORTED_MODULE_3___default = /*#__PURE__*/__webpack_require__.n(stompjs__WEBPACK_IMPORTED_MODULE_3__);
/* harmony import */ var sockjs_client__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! sockjs-client */ "./node_modules/sockjs-client/lib/entry.js");
/* harmony import */ var sockjs_client__WEBPACK_IMPORTED_MODULE_4___default = /*#__PURE__*/__webpack_require__.n(sockjs_client__WEBPACK_IMPORTED_MODULE_4__);





var AppComponent = /** @class */ (function () {
    function AppComponent(http) {
        this.http = http;
        this.title = 'Thermostat Dashboard';
        this.onTstatUpdate = new _angular_core__WEBPACK_IMPORTED_MODULE_1__["EventEmitter"]();
        this.onWeatherUpdate = new _angular_core__WEBPACK_IMPORTED_MODULE_1__["EventEmitter"]();
        this.thermostats = [];
    }
    AppComponent.prototype.ngOnInit = function () {
        var _this = this;
        var ws = new sockjs_client__WEBPACK_IMPORTED_MODULE_4__('/websocket');
        this.stompClient = stompjs__WEBPACK_IMPORTED_MODULE_3__["over"](ws);
        var that = this;
        this.stompClient.connect({}, function () {
            // subscribe to tstat updates:
            that.stompClient.subscribe('/topic/tstat-updates', function (update) {
                var updateBody = JSON.parse(update.body);
                var tstatUpdateEvent = {
                    thermostatId: updateBody.thermostatId,
                    tstate: updateBody.tstate,
                    currentTemp: updateBody.currentTemp,
                    targetTemp: updateBody.targetTemp,
                    time: updateBody.time
                };
                that.onTstatUpdate.emit(tstatUpdateEvent);
            });
            // subscribe to current outdoor temp updates:
            that.stompClient.subscribe('/topic/weather-updates', function (update) {
                console.log('received weather update: ' + update);
                var updateBody = JSON.parse(update.body);
                var weatherUpdateEvent = {
                    currentTemp: updateBody.currentTemp,
                    time: updateBody.time
                };
                that.onWeatherUpdate.emit(weatherUpdateEvent);
            });
        });
        // load all thermostats from server, render chart per tstat
        this.getApiResponse('/thermostats').then(function (tstats) {
            _this.thermostats = tstats;
        }, function (error) {
            console.log('An error occurred retrieving thermostat data from the server. ' + error);
        });
    };
    AppComponent.prototype.ngOnChanges = function () {
        console.log('change detected!');
    };
    AppComponent.prototype.isDataAvailable = function () {
        return this.thermostats.length > 0;
    };
    AppComponent.prototype.getApiResponse = function (url) {
        return this.http.get(url, {})
            .toPromise().then(function (res) {
            return res;
        });
    };
    AppComponent.ctorParameters = function () { return [
        { type: _angular_common_http__WEBPACK_IMPORTED_MODULE_2__["HttpClient"] }
    ]; };
    AppComponent = tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Component"])({
            selector: 'app-root',
            template: __webpack_require__(/*! raw-loader!./app.component.html */ "./node_modules/raw-loader/index.js!./src/app/app.component.html"),
            styles: [__webpack_require__(/*! ./app.component.css */ "./src/app/app.component.css")]
        })
    ], AppComponent);
    return AppComponent;
}());



/***/ }),

/***/ "./src/app/app.module.ts":
/*!*******************************!*\
  !*** ./src/app/app.module.ts ***!
  \*******************************/
/*! exports provided: AppModule */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "AppModule", function() { return AppModule; });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");
/* harmony import */ var _angular_platform_browser__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/platform-browser */ "./node_modules/@angular/platform-browser/fesm5/platform-browser.js");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_common_http__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! @angular/common/http */ "./node_modules/@angular/common/fesm5/http.js");
/* harmony import */ var _app_routing_module__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! ./app-routing.module */ "./src/app/app-routing.module.ts");
/* harmony import */ var _app_component__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! ./app.component */ "./src/app/app.component.ts");
/* harmony import */ var _output_graph_output_graph_component__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! ./output-graph/output-graph.component */ "./src/app/output-graph/output-graph.component.ts");







var AppModule = /** @class */ (function () {
    function AppModule() {
    }
    AppModule = tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_2__["NgModule"])({
            declarations: [
                _app_component__WEBPACK_IMPORTED_MODULE_5__["AppComponent"],
                _output_graph_output_graph_component__WEBPACK_IMPORTED_MODULE_6__["OutputGraphComponent"]
            ],
            imports: [
                _angular_platform_browser__WEBPACK_IMPORTED_MODULE_1__["BrowserModule"],
                _app_routing_module__WEBPACK_IMPORTED_MODULE_4__["AppRoutingModule"],
                _angular_common_http__WEBPACK_IMPORTED_MODULE_3__["HttpClientModule"]
            ],
            providers: [],
            bootstrap: [_app_component__WEBPACK_IMPORTED_MODULE_5__["AppComponent"]]
        })
    ], AppModule);
    return AppModule;
}());



/***/ }),

/***/ "./src/app/output-graph/output-graph.component.css":
/*!*********************************************************!*\
  !*** ./src/app/output-graph/output-graph.component.css ***!
  \*********************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "#heading{\n  background-color: cornflowerblue;\n}\n\n/*# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbInNyYy9hcHAvb3V0cHV0LWdyYXBoL291dHB1dC1ncmFwaC5jb21wb25lbnQuY3NzIl0sIm5hbWVzIjpbXSwibWFwcGluZ3MiOiJBQUFBO0VBQ0UsZ0NBQWdDO0FBQ2xDIiwiZmlsZSI6InNyYy9hcHAvb3V0cHV0LWdyYXBoL291dHB1dC1ncmFwaC5jb21wb25lbnQuY3NzIiwic291cmNlc0NvbnRlbnQiOlsiI2hlYWRpbmd7XG4gIGJhY2tncm91bmQtY29sb3I6IGNvcm5mbG93ZXJibHVlO1xufVxuIl19 */"

/***/ }),

/***/ "./src/app/output-graph/output-graph.component.ts":
/*!********************************************************!*\
  !*** ./src/app/output-graph/output-graph.component.ts ***!
  \********************************************************/
/*! exports provided: OutputGraphComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "OutputGraphComponent", function() { return OutputGraphComponent; });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var highcharts_highstock__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! highcharts/highstock */ "./node_modules/highcharts/highstock.js");
/* harmony import */ var highcharts_highstock__WEBPACK_IMPORTED_MODULE_2___default = /*#__PURE__*/__webpack_require__.n(highcharts_highstock__WEBPACK_IMPORTED_MODULE_2__);
/* harmony import */ var _angular_common_http__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! @angular/common/http */ "./node_modules/@angular/common/fesm5/http.js");




var Boost = __webpack_require__(/*! highcharts/modules/boost */ "./node_modules/highcharts/modules/boost.js");
var noData = __webpack_require__(/*! highcharts/modules/no-data-to-display */ "./node_modules/highcharts/modules/no-data-to-display.js");
var More = __webpack_require__(/*! highcharts/highcharts-more */ "./node_modules/highcharts/highcharts-more.js");
Boost(highcharts_highstock__WEBPACK_IMPORTED_MODULE_2__);
noData(highcharts_highstock__WEBPACK_IMPORTED_MODULE_2__);
More(highcharts_highstock__WEBPACK_IMPORTED_MODULE_2__);
noData(highcharts_highstock__WEBPACK_IMPORTED_MODULE_2__);
var OutputGraphComponent = /** @class */ (function () {
    function OutputGraphComponent(http) {
        this.http = http;
        this.options = {
            chart: {
                type: 'line',
                height: 400
            },
            title: {
                text: 'current temp chart'
            },
            credits: {
                enabled: false
            },
            tooltip: {
                formatter: function () {
                    return this.points.reduce(function (s, point) {
                        return point.series.name !== 'Runtime' ? s + '<br/>' + point.series.name + ': ' + point.y.toFixed(1) + '°F'
                            : s + '<br/>' + point.series.name + ': ' + point.y.toFixed(0) + 'm';
                        // TODO: figure out how to use non-utc time instead of subtracting 5 hours
                    }, '<b>' + highcharts_highstock__WEBPACK_IMPORTED_MODULE_2__["dateFormat"]('%e %b %y %H:%M:%S', this.x - (60 * 60 * 1000 * 5)) + '</b>');
                }
            },
            // TODO: figure out how to select and drag with cursor like original version does....onSetExtreemes() maybe?
            rangeSelector: {
                inputEnabled: true,
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
            time: {
                useUTC: false,
                timezoneOffset: 5 * 60
            },
            series: [
                {
                    name: 'Temperature',
                    data: [],
                    pointInterval: 60 * 1000,
                    zIndex: 2,
                    tooltip: {
                        valueDecimals: 1,
                        valueSuffix: '°F'
                    }
                },
                {
                    name: 'Target Temperature',
                    data: [],
                    pointInterval: 60 * 1000,
                    dashStyle: 'ShortDash',
                    zIndex: 1,
                    tooltip: {
                        valueDecimals: 1,
                        valueSuffix: '°F'
                    }
                },
                {
                    name: 'Outside Temperature',
                    data: [],
                    pointInterval: 60 * 1000,
                    zIndex: 0,
                    tooltip: {
                        valueDecimals: 1,
                        valueSuffix: '°F'
                    },
                },
                {
                    name: 'Runtime',
                    data: [],
                    type: 'column',
                    yAxis: 1
                }
            ]
        };
    }
    OutputGraphComponent.prototype.ngOnInit = function () {
        var _this = this;
        this.onTstatUpdate.subscribe(function (tstatUpdateEvent) {
            if (_this.thermostat.id === tstatUpdateEvent.thermostatId) {
                console.log('Received tstat update, update for thermostatId=' + tstatUpdateEvent.thermostatId);
                _this.stockChart.series[0].addPoint([tstatUpdateEvent.time, tstatUpdateEvent.currentTemp], true, true);
                _this.stockChart.series[1].addPoint([tstatUpdateEvent.time, tstatUpdateEvent.targetTemp], true, true);
                _this.stockChart.series[3].addPoint([tstatUpdateEvent.time, tstatUpdateEvent.tstate], true, true);
                // change status in chart title depending on if the event indicates running or not (tstate of 1 is running, 0 is idle)
                if (tstatUpdateEvent.tstate === 1) {
                    _this.stockChart.setTitle({ text: _this.thermostat.name + ' (Running)' });
                }
                else {
                    _this.stockChart.setTitle({ text: _this.thermostat.name + ' (Idle)' });
                }
            }
        });
        this.onWeatherUpdate.subscribe(function (weatherUpdateEvent) {
            console.log('Received weather update for time=' + weatherUpdateEvent.time + ', currentTemp=' + weatherUpdateEvent.currentTemp);
            _this.stockChart.series[2].addPoint([weatherUpdateEvent.time, weatherUpdateEvent.currentTemp], true, true);
        });
        var fromTime = new Date();
        fromTime.setDate(fromTime.getDate() - 7);
        var toTime = new Date();
        // TODO: find better way rather than loading weather data for every tstat
        this.getApiResponse('/thermostats/' + this.thermostat.id + '/samples?fromTime=' + fromTime.getTime() + '&toTime=' + toTime.getTime()).then(function (samples) {
            _this.getApiResponse('/weather/' + _this.thermostat.location.id + '/samples?fromTime=' + fromTime.getTime() + '&toTime=' + toTime.getTime()).then(function (weatherSamples) {
                _this.renderChart(samples, weatherSamples, _this.thermostat);
            }, function (error) {
                console.log('An error occurred retrieving weather data from the server. ' + error);
            });
        }, function (error) {
            console.log('An error occurred retrieving thermostat data from the server. ' + error);
        });
    };
    OutputGraphComponent.prototype.getApiResponse = function (url) {
        return this.http.get(url, {})
            .toPromise().then(function (res) {
            return res;
        });
    };
    OutputGraphComponent.prototype.renderChart = function (data, weatherSamples, tstat) {
        var currentTempData = [];
        var targetTempData = [];
        var runtimeData = [];
        for (var i = 0; i < data.length; i++) {
            var currentTempVal = [];
            currentTempVal[0] = data[i].time;
            currentTempVal[1] = data[i].currentTemp;
            currentTempData[i] = currentTempVal;
            var targetTempVal = [];
            targetTempVal[0] = data[i].time;
            targetTempVal[1] = data[i].targetTemp;
            targetTempData[i] = targetTempVal;
            var tstateVal = [];
            tstateVal[0] = data[i].time;
            tstateVal[1] = data[i].tstate;
            runtimeData[i] = tstateVal;
        }
        this.options.series[0].data = currentTempData;
        this.options.series[1].data = targetTempData;
        // load weather data into chart:
        var outsideTempSeries = [];
        for (var i = 0; i < weatherSamples.length; i++) {
            var outsideTempVal = [];
            outsideTempVal[0] = weatherSamples[i].time;
            outsideTempVal[1] = weatherSamples[i].currentTemp;
            outsideTempSeries[i] = outsideTempVal;
        }
        this.options.series[2].data = outsideTempSeries;
        this.options.series[3].data = runtimeData;
        // TODO: seems like server is always returning null for last runtime data point in array
        this.options.title.text = runtimeData[runtimeData.length - 1][1] === 1 ? tstat.name + ' (Running)' : tstat.name + ' (Idle)';
        this.stockChart = highcharts_highstock__WEBPACK_IMPORTED_MODULE_2__["stockChart"]('container' + tstat.id, this.options);
    };
    OutputGraphComponent.ctorParameters = function () { return [
        { type: _angular_common_http__WEBPACK_IMPORTED_MODULE_3__["HttpClient"] }
    ]; };
    tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Input"])()
    ], OutputGraphComponent.prototype, "thermostat", void 0);
    tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Input"])()
    ], OutputGraphComponent.prototype, "onTstatUpdate", void 0);
    tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Input"])()
    ], OutputGraphComponent.prototype, "onWeatherUpdate", void 0);
    OutputGraphComponent = tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Component"])({
            selector: 'app-output-graph',
            template: __webpack_require__(/*! raw-loader!./output-graph.component.html */ "./node_modules/raw-loader/index.js!./src/app/output-graph/output-graph.component.html"),
            styles: [__webpack_require__(/*! ./output-graph.component.css */ "./src/app/output-graph/output-graph.component.css")]
        })
    ], OutputGraphComponent);
    return OutputGraphComponent;
}());



/***/ }),

/***/ "./src/environments/environment.ts":
/*!*****************************************!*\
  !*** ./src/environments/environment.ts ***!
  \*****************************************/
/*! exports provided: environment */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "environment", function() { return environment; });
// This file can be replaced during build by using the `fileReplacements` array.
// `ng build --prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.
var environment = {
    production: false
};
/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/dist/zone-error';  // Included with Angular CLI.


/***/ }),

/***/ "./src/main.ts":
/*!*********************!*\
  !*** ./src/main.ts ***!
  \*********************/
/*! no exports provided */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_platform_browser_dynamic__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/platform-browser-dynamic */ "./node_modules/@angular/platform-browser-dynamic/fesm5/platform-browser-dynamic.js");
/* harmony import */ var _app_app_module__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ./app/app.module */ "./src/app/app.module.ts");
/* harmony import */ var _environments_environment__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ./environments/environment */ "./src/environments/environment.ts");




if (_environments_environment__WEBPACK_IMPORTED_MODULE_3__["environment"].production) {
    Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["enableProdMode"])();
}
Object(_angular_platform_browser_dynamic__WEBPACK_IMPORTED_MODULE_1__["platformBrowserDynamic"])().bootstrapModule(_app_app_module__WEBPACK_IMPORTED_MODULE_2__["AppModule"])
    .catch(function (err) { return console.error(err); });


/***/ }),

/***/ 0:
/*!***************************!*\
  !*** multi ./src/main.ts ***!
  \***************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

module.exports = __webpack_require__(/*! /Users/scottlaplante/workspace/thermonitor-ui/src/main.ts */"./src/main.ts");


/***/ })

},[[0,"runtime","vendor"]]]);
//# sourceMappingURL=main-es5.js.map