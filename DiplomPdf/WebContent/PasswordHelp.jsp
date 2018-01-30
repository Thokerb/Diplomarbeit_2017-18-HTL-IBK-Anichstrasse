<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<title>Easy PDF - Login</title>
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
				<h1 class="title"> <img src="Images/logo_placeholder.png" class="imglogo" alt="LOGO"> </h1>

				<form action="EmailPasswort" id="pwresetform" method="post">
					<div class="form-group">
						<label  for="uninput">Gib hier die Emailadresse ein mit der du dich registriert hast. Wir schicken dir dann eine Email mit einem Link zum Zurücksetzen des Passworts.</label> <input
							type="text" class="form-control" name="email" id="uninput"
							placeholder="Emaildresse" required>
					</div>
					<div class="form-group">
						<hr />
						<input type="submit" class="form-control btn btn-primary"
							value="Send Email">
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
		
		
		
	});
	
	$("#pwresetform").submit(function(event){
		event.preventDefault();

		
					
			$("#pwresetform")[0].submit(); //only calls once, no infinite loop
			console.log("yeah");
			$(".modal-body").addClass("loader");
			$("#statusModal").modal("show");
		

		
	});

	</script>

</body>
</html>