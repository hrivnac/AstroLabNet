<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- AstroLabNet JSP for site=LAL -->
<!-- @author Julius.Hrivnac@cern.ch  -->

<%@ page import="com.astrolabsoftware.AstroLabNet.Utils.Init" %>

<%@ page errorPage="ExceptionHandler.jsp" %>

<!DOCTYPE html>
<html>
  <head><title>AstroLabNet JSP</title></head>
  <%
    Init.init(new String[]{"-p", "LAL"}, false); 
    %>
  <frameset cols="40%,60%">
    <frame src="CommandCenter.jsp" name="COMMAND">
    <frame src="Result.jsp"        name="RESULT">
    </frameset>
  </html>
