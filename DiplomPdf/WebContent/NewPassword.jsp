<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>

<%
if(session.getAttribute("hashcodeverified")==null){
response.sendRedirect("Errorpage.html");
}
%>

<body>
<h1>Willkommen auf der RESET PASSWORT SEITE</h1>

<form action="ResetPasswort" method="post">
<input type = "text" name="password">
<input type = "text" name="password2">
<input type="submit" value="Change Password"> 

</form>

<!-- Modal -->
<div id="statusModal" class="modal fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Status</h4>
      </div>
      <div class="modal-body">
        <p id="statustext"></p>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
      </div>
    </div>

  </div>
</div>

<script>

$.ajax({
    type: 'POST',
    url: 'ResetPasswort',
    dataType: 'text/plain',
    complete: function(data){
       Console.log(data)
       
       switch(data){
       case"pwok":
    	   $("#statustext").text("Ihr Passwort wurde erfolgreich geändert!");
    	   $("#statusModal").modal();
    	   break;
       case"notsamesame":
    	   $("#statustext").text("Die Passwörter stimmen nicht überein!");
    	   $("#statusModal").modal();
    	   break;
       default:
    	   $("#statustext").text("Es gab einen Fehler mit Ihrer Anfrage.");
		   $("#statusModal").modal();
		   break;
    	   
       }
    }
 });

</script>


</body>
</html>