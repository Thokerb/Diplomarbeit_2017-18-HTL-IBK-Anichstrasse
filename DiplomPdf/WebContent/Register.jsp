<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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

				<form action="RegisterServlet" method="post" id="registerform" data-toggle="validator">
					<div class="form-group">
						<label class="sr-only" for="uninput">Benutzername</label> 
						<input type="text" class="form-control" name="username" id="uninput" placeholder="Benutzername" required>
					</div>
					<div class="form-group">
						<label class="sr-only" for="emailinput">E-Mail</label> 
						<input type="email" data-error="Bitte geben Sie eine gültige Emailadresse ein" class="form-control"  name="email" id="emailinput" placeholder="E-Mail " required>
						<div class="help-block with-errors"></div>
					</div>
					
					<div class="form-group">
						<div class="input-group">
							<label class="sr-only" for="pwinput">Passwort</label> 
							<input type="password" class="form-control" name="password" id="pwinput" placeholder="Passwort" data-maxlength="16" data-minlength="8" required>
							<span class="input-group-btn">
								<button class="btn-link btnpw form-control" type="button" id="unmaskbtn">
									<span class="glyphicon glyphicon-eye-open"></span>
								</button>
							</span>
						</div>
												<div class="help-block with-errors"></div>
					
					</div>

					<div class="form-group">
						<label class="sr-only" for="pwinput2">Passwort wiederholen</label>
						<input type="password" class="form-control" name="passwordrepeat" id="pwinput2" placeholder="Passwort wiederholen" data-match="#pwinput" data-match-error="Passwörter stimmen nicht überein" required>
						<div class="help-block with-errors"></div>
					</div>
					<div class="form-group">
						<hr />
						<input type="submit" id="submitbtn" class="form-control btn btn-primary" value="Registrieren">
					</div>
				</form>
			</div>
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
      <div class="modal-body center-content">
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
		var status = $("#statusmessage").text();
		console.log(status);
		if(status!=""){
			$(".modal-body").removeClass("loader");
			$(".modal-body").removeClass("center-content");
			$("#statusModal").modal("show");
		}
		
		$("#registerform").submit(function(event){
			event.preventDefault();

			
			if(form_validate("registerform")){				
				$("#registerform")[0].submit(); //only calls once, no infinite loop
				console.log("yeah");
				$(".modal-body").addClass("loader");
				console.dir($("#statusmessage"));
				$("#statusModal").modal("show");
			}
			else{
				console.log("nope");

			
			}

			
		});
		
		function form_validate(attr_id){
		    var result = true;
		    $('#'+attr_id).validator('validate');
		    $('#'+attr_id+' .form-group').each(function(){
		        if($(this).hasClass('has-error')){
		            result = false;
		            return false;
		        }
		    });
		    return result;
		}
		
    	var register = $("#registerform");
    	
    	register.validator({
        	
        			custom: {
        				maxlength: function($el){
        					console.log("called custom");
        				    var matchValue = $el.data("maxlength") // bekommt die angegebene maxlänge
        				    console.log($el.val().length);
							if($el.val().length > matchValue){
								return "Das Passwort ist zu lang."
								register.validator('update');
							}
        					
        				}
        			}
        	
    	});
    	

	    $("#unmaskbtn").on('click',function(){
	        if($("#pwinput").attr('type') == 'password'){
	        	var input = $("#pwinput");
	        	var pw = input.val();
	        	input.replaceWith("<input type=\"text\" name=\"password\" id=\"pwinput\" data-maxlength=\"16\" placeholder=\"Passwort\" data-minlength=\"8\" class=\"form-control\" value=\""+pw+"\" required>");
                $(this).html("<span class=\"glyphicon glyphicon-eye-close\"></span>")

                
                var input2 = $("#pwinput2");
                var pw2 = input2.val();
	        	input2.replaceWith("<input type=\"text\" name=\"password\" id=\"pwinput2\" placeholder=\"Passwort wiederholen\" class=\"form-control\" data-match=\"#pwinput\" data-match-error=\"Passwörter stimmen nicht überein\" value=\""+pw2+"\" required>");
				
	        	register.validator('update');
	        }
	        else{
	        	var input = $("#pwinput");
	        	var pw = input.val();
	        	input.replaceWith("<input type=\"password\" name=\"password\" id=\"pwinput\" class=\"form-control\" data-maxlength=\"16\" data-minlength=\"8\" placeholder=\"Passwort\" value=\""+pw+"\" required>");
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