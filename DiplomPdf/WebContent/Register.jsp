<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Register</title>

<link rel="apple-touch-icon" sizes="57x57" href="Icons/apple-icon-57x57.png">
<link rel="apple-touch-icon" sizes="60x60" href="Icons/apple-icon-60x60.png">
<link rel="apple-touch-icon" sizes="72x72" href="Icons/apple-icon-72x72.png">
<link rel="apple-touch-icon" sizes="76x76" href="Icons/apple-icon-76x76.png">
<link rel="apple-touch-icon" sizes="114x114" href="Icons/apple-icon-114x114.png">
<link rel="apple-touch-icon" sizes="120x120" href="Icons/apple-icon-120x120.png">
<link rel="apple-touch-icon" sizes="144x144" href="Icons/apple-icon-144x144.png">
<link rel="apple-touch-icon" sizes="152x152" href="Icons/apple-icon-152x152.png">
<link rel="apple-touch-icon" sizes="180x180" href="Icons/apple-icon-180x180.png">
<link rel="icon" type="image/png" sizes="192x192"  href="Icons/android-icon-192x192.png">
<link rel="icon" type="image/png" sizes="32x32" href="Icons/favicon-32x32.png">
<link rel="icon" type="image/png" sizes="96x96" href="Icons/favicon-96x96.png">
<link rel="icon" type="image/png" sizes="16x16" href="Icons/favicon-16x16.png">
<link rel="manifest" href="Icons/manifest.json">
<meta name="msapplication-TileColor" content="#ffffff">
<meta name="msapplication-TileImage" content="Icons/ms-icon-144x144.png">
<meta name="theme-color" content="#ffffff">

<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="StyleHeroImage.css" />
<link rel="stylesheet" href="styleLogin.css" />
<script src="jquery-3.2.1.js"></script>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" />
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	
	<script src="https://cdnjs.cloudflare.com/ajax/libs/1000hz-bootstrap-validator/0.11.5/validator.min.js"></script>
	
		<!-- Implementation of cookie banner by https://cookieconsent.insites.com -->
<link rel="stylesheet" type="text/css" href="//cdnjs.cloudflare.com/ajax/libs/cookieconsent2/3.0.3/cookieconsent.min.css" />
<script src="//cdnjs.cloudflare.com/ajax/libs/cookieconsent2/3.0.3/cookieconsent.min.js"></script>
<script>
window.addEventListener("load", function(){
window.cookieconsent.initialise({
  "palette": {
    "popup": {
      "background": "#000"
    },
    "button": {
      "background": "#f1d600"
    }
  },
  "content": {
    "message": "Unsere Website verwendet Cookies um Ihnen die bestmöglichste Nutzererfahrung zu garantieren.",
    "dismiss": "Verstanden!",
    "link": "Mehr Informationen"
  }
})});
</script>
	
	
</head>
<body>
<div id="Start">
		<nav class="navbar navbar-inverse navbar-static-top"
			id="navbarStartseite">
			<div class="container-fluid">
				<div class="navbar-header">
					<button type="button" class="navbar-toggle" data-toggle="collapse"
						data-target="#myNavbar">
						<span class="icon-bar"></span> <span class="icon-bar"></span> <span
							class="icon-bar"></span>
					</button>
					<a class="navbar-brand" href="Startseite.jsp"> <img class="brandimg" src="Icons/iconneu2.png"></a>
				</div>
				<div class="collapse navbar-collapse" id="myNavbar">
					<ul class="nav navbar-nav">
						<li><a href="Startseite.jsp">Startseite</a></li>
					</ul>
										<ul class="nav navbar-nav">
						<li><a href="MeetTheTeam.jsp">Unser Team</a></li>
					</ul>
					<ul class="nav navbar-nav navbar-right">
						<li><a href="Login.jsp"><span class="glyphicon glyphicon-log-in"></span>
								Anmelden</a></li>

					</ul>
				</div>
			</div>
		</nav>
	<div class="hero-image1">

		<div class="container" id="c1">
			<div class="center-content">


				<h1 class="title">
					<img src="Icons/logoblack.png" class="imglogo" alt="LOGO">
				</h1>

				<form action="RegisterServlet" method="post" id="registerform" data-toggle="validator">
					<div class="form-group">
						<label class="sr-only" for="uninput">Benutzername</label> 
						<input type="text" class="form-control" name="username" id="uninput" placeholder="Benutzername" data-specialchars="NO" required>
											<div class="help-block with-errors"></div>
					</div>
					<div class="form-group">
						<label class="sr-only" for="emailinput">E-Mail</label> 
						<input type="email" data-error="Bitte geben Sie eine gültige Emailadresse ein" class="form-control"  name="email" id="emailinput" placeholder="E-Mail " required>
						<div class="help-block with-errors"></div>
					</div>
					
					<div class="form-group">
						<div class="input-group">
							<label class="sr-only" for="pwinput">Passwort</label> 
							<input type="password" class="form-control" name="password" id="pwinput" placeholder="Passwort" data-maxlength="16" data-containnumber="YES" data-maxlength-error="Das Passwort darf maximal 16 Zeichen lang sein"  data-minlength="8" data-minlength-error="Das Passwort muss mindestens 8 Zeichen lang sein" required>
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
				$(".modal-body").addClass("center-content");
				$("#statusmessage").text("");
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
    	var restrictedchars = ["!","#","$","%","&","'","(",")","*","+",",","-",".","/"]
    	var numbers = ["0","1","2","3","4","5","6","7","8","9"];
    	
    	register.validator({
        	
        			custom: {
        				maxlength: function($el){
        					console.log("called custom");
        				    var matchValue = $el.data("maxlength"); // bekommt die angegebene maxlänge
        				    console.log($el.val().length);
							if($el.val().length > matchValue){
								return "Das Passwort ist zu lang."
								register.validator('update');
							}
        					
        				},
        				specialchars: function($el){
        					var rule = $el.data("specialchars");
        					var str = $el.val();
        					for(var i = 0;i < str.length;i++){
        						var ch = str.substring(i,i+1);
        						var check = $.inArray(ch,restrictedchars);
        						if(check > 0){
        							console.log(ch);
        							break;
        						}
        					}


        					if(rule.localeCompare("YES") == 0){
								//Everything is fine
        					}
        					if(rule.localeCompare("NO") == 0){
        						if(check > 0){
        							return "Es dürfen keine Sonderzeichen verwendet werden.";
    								register.validator('update');
        						}
        					}

        				},
        				containnumber: function($el){
        					var rule = $el.data("containnumber");
        					if(rule.localeCompare("YES") == 0){
            					var str = $el.val();
            					var zahl;
            					for(var i = 0;i < str.length;i++){
            						var ch = str.substring(i,i+1);
            						var check = $.inArray(ch,numbers);
            						if(check > 0){
            							console.log(ch);
            							zahl = true;
            							break;
            						}
            					}
            					
            					if(zahl===true){
            						
            					}
            					else{
            						return "Das Passwort muss eine Zahl beinhalten.";
            					}

        					}
        				}
        			}
        	
    	});
    	

	    $("#unmaskbtn").on('click',function(){
	        if($("#pwinput").attr('type') == 'password'){
	        	var input = $("#pwinput");
	        	var pw = input.val();
	        	input.replaceWith("<input type=\"text\" name=\"password\" data-containnumber=\"YES\" id=\"pwinput\" data-maxlength=\"16\" placeholder=\"Passwort\" data-minlength=\"8\" data-maxlength-error=\"Das Passwort darf maximal 16 Zeichen lang sein\" data-minlength-error=\"Das Passwort muss mindestens 8 Zeichen lang sein\" class=\"form-control\" value=\""+pw+"\" required>");
                $(this).html("<span class=\"glyphicon glyphicon-eye-close\"></span>")

                
                var input2 = $("#pwinput2");
                var pw2 = input2.val();
	        	input2.replaceWith("<input type=\"text\" name=\"password\" id=\"pwinput2\" placeholder=\"Passwort wiederholen\" class=\"form-control\" data-match=\"#pwinput\" data-match-error=\"Passwörter stimmen nicht überein\" value=\""+pw2+"\" required>");
				
	        	register.validator('update');
	        }
	        else{
	        	var input = $("#pwinput");
	        	var pw = input.val();
	        	input.replaceWith("<input type=\"password\" name=\"password\" data-containnumber=\"YES\" id=\"pwinput\" class=\"form-control\" data-maxlength=\"16\" data-minlength=\"8\" data-maxlength-error=\"Das Passwort darf maximal 16 Zeichen lang sein\" data-minlength-error=\"Das Passwort muss mindestens 8 Zeichen lang sein\" placeholder=\"Passwort\" value=\""+pw+"\" required>");
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