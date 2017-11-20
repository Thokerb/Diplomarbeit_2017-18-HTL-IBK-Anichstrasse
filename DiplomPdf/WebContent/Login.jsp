<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<title>Login</title>
<link rel="stylesheet" href="styleLogin.css" />
<script src="jquery-3.2.1.js"></script>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" />
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>

<body>

	<div class="heroimage">

		<div class="container">
			<div class="center-content">


				<h1 class="title">
					<img src="Images/logo_placeholder.png" class="imglogo" alt="LOGO">
				</h1>

				<form action="LoginServlet" method="POST">
					<div class="form-group">
						<label class="sr-only" for="uninput">Benutzername</label> 
						<input type="text" class="form-control" name="uname" id="uninput" placeholder="Benutzername" required>
					</div>
					
					<div class="form-group">
						<div class="input-group">
							<label class="sr-only" for="pwinput">Passwort</label> 
							<input type="password" class="form-control" name="pass" id="pwinput" placeholder="Passwort" required>
							<span class="input-group-btn">
								<button class="btn-link btnpw form-control" type="button" id="unmaskbtn">
									<span class="glyphicon glyphicon-eye-open"></span>
								</button>
							</span>
						</div>
					</div>
					<div class="form-group">
						<hr />
						<input type="submit" class="form-control btn btn-primary" value="Login">
					</div>
				</form>


			</div>
		</div>
	</div>


		<script>
	$(document).ready(function() {
	    $("#unmaskbtn").on('click',function(){
	        if($("#pwinput").attr('type') == 'password'){
	        	console.log(1);
	        	var input = $("#pwinput");
	        	var pw = input.val();
	        	console.log(pw);
	        	input.replaceWith("<input type=\"text\" name=\"pass\" id=\"pwinput\" placeholder=\"Passwort\" class=\"form-control\" value=\""+pw+"\" required>");
                $(this).html("<span class=\"glyphicon glyphicon-eye-close\"></span>")
	     
	        }
	        else{
	        	console.log(2);
	        	var input = $("#pwinput");
                console.log(input);
	        	var pw = input.val();
	        	console.log(pw);
	        	input.replaceWith("<input type=\"password\" name=\"pass\" id=\"pwinput\" class=\"form-control\" placeholder=\"Passwort\" value=\""+pw+"\" required>");
                                $(this).html("<span class=\"glyphicon glyphicon-eye-open\"></span>")

	     	       }

	          });
	})

        

        
    
    </script>

</body>
</html>