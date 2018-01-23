<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<title>Register</title>
<link rel="stylesheet" href="styleLogin.css" />
<script src="jquery-3.2.1.js"></script>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" />
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	
	<script src="https://cdnjs.cloudflare.com/ajax/libs/1000hz-bootstrap-validator/0.11.5/validator.min.js"></script>
	
</head>
<body>

	<div class="heroimage">

		<div class="container">
			<div class="center-content">


				<h1 class="title">
					<img src="Images/logo_placeholder.png" class="imglogo" alt="LOGO">
				</h1>

				<form action="LoginServlet" method="get" data-toggle="validator">
					<div class="form-group">
						<label class="sr-only" for="uninput">Benutzername</label> 
						<input type="text" class="form-control" name="username" id="uninput" placeholder="Benutzername" required>
					</div>
					<div class="form-group">
						<label class="sr-only" for="emailinput">E-Mail</label> 
						<input type="email" class="form-control" name="email" id="emailinput" placeholder="E-Mail " required>
					</div>
					
					<div class="form-group">
						<div class="input-group">
							<label class="sr-only" for="pwinput">Passwort</label> 
							<input type="password" class="form-control" name="password" id="pwinput" placeholder="Passwort" data-minlength="8" required>
							<span class="input-group-btn">
								<button class="btn-link btnpw form-control" type="button" id="unmaskbtn">
									<span class="glyphicon glyphicon-eye-open"></span>
								</button>
							</span>
						</div>
					</div>

					<div class="form-group">
						<label class="sr-only" for="pwinput2">Passwort wiederholen</label>
						<input type="password" class="form-control" name="passwordrepeat" id="pwinput2" placeholder="Passwort wiederholen" data-match="#pwinput" data-match-error="Passwörter stimmen nicht überein" required>
						<div class="help-block with-errors"></div>
					</div>
					<div class="form-group">
						<hr />
						<input type="submit" class="form-control btn btn-primary" value="Registrieren">
					</div>
				</form>
			</div>
		</div>
	</div>


	<script>
	$(document).ready(function() {
	    $("#unmaskbtn").on('click',function(){
	        if($("#pwinput").attr('type') == 'password'){
	        	var input = $("#pwinput");
	        	var pw = input.val();
	        	input.replaceWith("<input type=\"text\" name=\"password\" id=\"pwinput\" placeholder=\"Passwort\" data-minlength=\"8\" class=\"form-control\" value=\""+pw+"\" required>");
                $(this).html("<span class=\"glyphicon glyphicon-eye-close\"></span>")
                
                var input2 = $("#pwinput2");
                var pw2 = input2.val();
	        	input2.replaceWith("<input type=\"text\" name=\"password\" id=\"pwinput2\" placeholder=\"Passwort wiederholen\" class=\"form-control\" data-match=\"#pwinput\" data-match-error=\"Passwörter stimmen nicht überein\" value=\""+pw2+"\" required>");
     	     
	        }
	        else{
	        	var input = $("#pwinput");
	        	var pw = input.val();
	        	input.replaceWith("<input type=\"password\" name=\"password\" id=\"pwinput\" class=\"form-control\" data-minlength=\"8\" placeholder=\"Passwort\" value=\""+pw+"\" required>");
                                $(this).html("<span class=\"glyphicon glyphicon-eye-open\"></span>");
                
                var input2 = $("#pwinput2");
                var pw2 = input2.val();
	        	input2.replaceWith("<input type=\"password\" name=\"password\" id=\"pwinput2\" class=\"form-control\" placeholder=\"Passwort wiederholen\" data-match=\"#pwinput\" data-match-error=\"Passwörter stimmen nicht überein\" value=\""+pw2+"\" required>");
	     	       }

	          });
	})    
    </script>

</body>
</html>