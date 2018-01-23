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

				<form action="EmailPasswort" method="post">
					<div class="form-group">
						<label class="sr-only" for="uninput">Email</label> <input
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


	<script>

	</script>

</body>
</html>