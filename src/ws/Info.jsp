<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- AstroLabNet Info -->
<!-- @author Julius.Hrivnac@cern.ch  -->

<!--%@ page errorPage="ExceptionHandler.jsp" %-->

<%@ page import="com.astrolabsoftware.AstroLabNet.Utils.Init" %>

<%
  Init.init();
  String element = request.getParameter("element");
  String key     = request.getParameter("key");
  out.println(element + " " + key);
  %>

