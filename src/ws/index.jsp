<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- AstroLabNet JSP -->
<!-- @author Julius.Hrivnac@cern.ch  -->

<%@ page errorPage="ExceptionHandler.jsp" %>
<!DOCTYPE html>
<html>
  <head><title>AstroLabNet JSP</title></head>
  <frameset cols="40%,60%">
    <frame src="CommandCenter.jsp"  name="COMMAND">
    <frame src="Result.jsp"         name="RESULT">
    </frameset>
  </html>
