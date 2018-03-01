<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
		<meta name="viewport" content="width=device-width, initial-scale=1">

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script src="jquery-3.2.1.js"></script>
<link rel="stylesheet" href="loginmobilestyle.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" />
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
<div class="container" id="containerrule">
<div class="row">
<div class="col-sm-12">
<div class="centercontent">
				<h1 class="title"> <img src="Images/logo_placeholder.png" class="imglogo" alt="LOGO"> </h1>
				
				<form action="LoginServlet" method="post">
					<div class="form-group">
						<label class="sr-only" for="uninput">Benutzername</label> <input
							type="text" class="form-control" name="username" id="uninput"
							placeholder="Benutzername" required>
					</div>
					<div class="form-group">
						<div class="input-group">
							<label class="sr-only" for="pwinput">Passwort</label> <input
								type="password" class="form-control" name="password"
								id="pwinput" placeholder="Passwort" required><span
								class="input-group-btn">
								<button class="btn-link btnpw form-control" type="button"
									id="unmaskbtn">
									<span class="glyphicon glyphicon-eye-open"></span>
								</button>
							</span>
						</div>
					</div>
					<div class="form-group">
						<hr />
						<input type="submit" class="form-control btn btn-primary"
							value="Login">
					</div>
					<div class="form-group">
												<a href="PasswordHelp.jsp">Passwort vergessen ?</a>
					
					</div>
				</form>
				</div>
				</div>
				</div>
				</div>
</body>
</html>