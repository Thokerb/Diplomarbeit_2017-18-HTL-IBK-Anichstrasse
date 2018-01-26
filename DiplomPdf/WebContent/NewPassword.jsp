<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"/>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/1000hz-bootstrap-validator/0.11.5/validator.min.js"></script>
<link rel="stylesheet" href="StyleHeroImage.css">
<link rel="stylesheet" href="styleLogin.css">
</head>

<%
if(session.getAttribute("hashcodeverified")!="yes"){
//response.sendRedirect("Errorpage.html"); 			//auskommentiert für design TODO: dekommentieren
}
%>

<body>
	<div class="heroimage">

		<div class="container">
			<div class="center-content">
			<h1>Gib dein neues Passwort ein</h1>

<form action="ResetPasswort" method="post" id="registerform" data-toggle="validator">
<div class="form-group">
<div class="input-group">
<input class="form-control" type = "password" data-minlength="8" id="pwinput" name="password" data-minlength-error="Passwort muss mindestens 8 Zeichen haben" required>
							<span class="input-group-btn">
								<button class="btn-link btnpw form-control" type="button" id="unmaskbtn">
									<span class="glyphicon glyphicon-eye-open"></span>
								</button>
							</span>
</div>
<div class="help-block with-errors"></div>

</div>
<div class="form-group">
<input class="form-control" type = "password" id="pwinput2" name="password" data-match="#pwinput" data-match-error="Passwörter stimmen nicht überein" required>
<div class="help-block with-errors"></div>
</div>
<div class="form-group">
<input type="submit" class="form-control btn btn-primary" value="Change Password"> 
</div>
</form>

		</div>
	</div>

	<div id="statusModal" class="modal fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Status</h4>
      </div>
      <div class="modal-body">
        <p id="statusmessage">${message}</p>
      </div>
      <div class="modal-footer">
      	        <button type="button" class="btn btn-default" onclick="location.href = 'Startseite.jsp';">Startseite</button>
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
      </div>
    </div>

  </div>
	</div>


<script>

$(document).ready(function() {
	var register = $("#registerform");

    $("#unmaskbtn").on('click',function(){
        if($("#pwinput").attr('type') == 'password'){
        	var input = $("#pwinput");
        	var pw = input.val();
        	input.replaceWith("<input type=\"text\" name=\"password\" id=\"pwinput\" placeholder=\"Passwort\" data-minlength=\"8\" class=\"form-control\" value=\""+pw+"\" required>");
            $(this).html("<span class=\"glyphicon glyphicon-eye-close\"></span>")

            
            var input2 = $("#pwinput2");
            var pw2 = input2.val();
        	input2.replaceWith("<input type=\"text\" name=\"password\" id=\"pwinput2\" placeholder=\"Passwort wiederholen\" class=\"form-control\" data-match=\"#pwinput\" data-match-error=\"Passwörter stimmen nicht überein\" value=\""+pw2+"\" required>");
			
        	register.validator('update');
        }
        else{
        	var input = $("#pwinput");
        	var pw = input.val();
        	input.replaceWith("<input type=\"password\" name=\"password\" id=\"pwinput\" class=\"form-control\" data-minlength=\"8\" placeholder=\"Passwort\" value=\""+pw+"\" required>");
                            $(this).html("<span class=\"glyphicon glyphicon-eye-open\"></span>");
            
            var input2 = $("#pwinput2");
            var pw2 = input2.val();
        	input2.replaceWith("<input type=\"password\" name=\"password\" id=\"pwinput2\" class=\"form-control\" placeholder=\"Passwort wiederholen\" data-match=\"#pwinput\" data-match-error=\"Passwörter stimmen nicht überein\" value=\""+pw2+"\" required>");


        	register.validator('update');
        }

          });
}) 

</script>


</body>
</html>