<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- AstroLabNet JSP -->
<!-- @author Julius.Hrivnac@cern.ch  -->

<%@ page errorPage="ExceptionHandler.jsp" %>
<!DOCTYPE html>
<html>
  <head><title>AstroLabNet JSP</title></head>
  <%
    String name  = request.getParameter("name");
    String spark = request.getParameter("spark");
    String livy  = request.getParameter("livy");
    String hbase = request.getParameter("hbase");
    String command = "CommandCenter.jsp";
    if (name != null) {
      command="CommandCenter.jsp?name=" + name + "&spark=" + spark + "&livy=" + livy + "&hbase=" + hbase;
      }
    %>
  <frameset cols="40%,60%">
    <frame src="<%=command%>" name="COMMAND">
    <frame src="Result.jsp"   name="RESULT">
    </frameset>
  </html>
