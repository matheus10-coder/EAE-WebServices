<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head><title>First JSP</title></head>
<form name="createForm" method="post" action="resources/eventQuery">
  <label for="system">System</label><br>
  <input type="text" id="system" name="system"><br>
  
  <label for="tenant">Tenant:</label><br>
  <input type="text" id="tenant" name="tenant"><br>
  
  <label for="type">Type:</label><br>
  <input type="text" id="type" name="type"><br>
  
  <label for="seq">Sequence:</label><br>
  <input type="number" id="seq" name="seq"><br>
  
  <label for="process">Process:</label><br>
  <input type="text" id="process" name="process"><br>
  
   <label for="Schema">Schema:</label><br>
  <input type="text" id="schema" name="schema"><br>
  
  <label for="query">Query:</label><br>
  <input type="text" id="query" name="query"><br>
  
   <label for="description">Description:</label><br>
  <input type="text" id="description" name="description"><br>
  
  <label for="parameter">Parameter:</label><br>
  <input type="text" id="parameter" name="parameter"><br>
  
   <label for="fileName">File Name:</label><br>
  <input type="text" id="fileName" name="fileName"><br>
  
   <label for="dbType">DB type:</label><br>
  <input type="text" id="dbType" name="dbType"><br>
  
   <label for="outputData">Output Data:</label><br>
  <input type="text" id="outputData" name="outputData"><br>
  
   <label for="outputType">Output Type:</label><br>
  <input type="text" id="outputType" name="outputType"><br>
  
   <label for="outputEndpoint">Output Endpoint:</label><br>
  <input type="text" id="outputEndpoint" name="outputEndpoint"><br>
  
  <label for="active">Active:</label><br>
  <input type="number" id="active" name="active"><br>
  
   
  <label for="createUserId">Current User Id:</label><br>
  <input type="text" id="createUserId" name="createUserId"><br>
  
   
  <label for="updateUserId">Update User Id</label><br>
  <input type="text" id="updateUserId" name="updateUserId"><br>
  
  <input type="submit" value="Submit">
</form>
</html>