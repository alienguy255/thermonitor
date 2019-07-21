<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Thermostat Dashboard</title>

    <link rel="stylesheet" type="text/css" href="css/home.css" />
    <link rel="stylesheet" type="text/css" href="css/jquery-ui-1.8.19.custom.css" />

    <script type="text/javascript" src="<%=request.getContextPath()%>/script/jquery-3.2.1.min.js"></script>
    
    <script type="text/javascript" src="<%=request.getContextPath()%>/script/highstock.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/script/modules/exporting.js"></script>

    <script type="text/javascript" src="<%=request.getContextPath()%>/script/component/ThermostatsView.js"></script>

    <script type="text/javascript" src="<%=request.getContextPath()%>/script/stomp.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/script/sockjs-0.3.min.js"></script>

    <script type="text/javascript">
        $(document).ready(function(){
            // build and render the Thermostats View
            $('#thermostatsContainer').ThermostatsView();
        });
    </script>

</head>
<body>

<h1>Thermostat Dashboard</h1>
<%@ page import="java.util.*" %>
<h2>Time on server: <%= new Date() %></h2>

<div id="thermostatsContainer" class="container">
    <div id="container1" style="float:left; width: 800px; height: 400px; margin: 0 auto"></div><div id="status1" style="float:left;"></div>
    <div id="container2" style="float:left; width: 800px; height: 400px; margin: 0 auto"></div><div id="status2" style="float:left;"></div>
    <div id="container3" style="float:left; width: 800px; height: 400px; margin: 0 auto"></div><div id="status3" style="float:left;"></div>
    <div id="container4" style="float:left; width: 800px; height: 400px; margin: 0 auto"></div><div id="status4" style="float:left;"></div>
    <div id="container5" style="float:left; width: 800px; height: 400px; margin: 0 auto"></div><div id="status5" style="float:left;"></div>
</div>

</body>
</html>
