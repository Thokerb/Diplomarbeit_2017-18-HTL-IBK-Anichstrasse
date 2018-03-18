<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Easy PDF - Login</title>

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

<link rel="stylesheet" href="StyleHeroImage.css" />
<link rel="stylesheet" href="styleLogin.css" />
<script src="jquery-3.2.1.js"></script>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" />
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
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
					<a class="navbar-brand" href="Startseite.jsp"> <img src="Icons/favicon-32x32.png"></a>
				</div>
				<div class="collapse navbar-collapse" id="myNavbar">
					<ul class="nav navbar-nav">
						<li><a href="Startseite.jsp">Startseite</a></li>
					</ul>
										<ul class="nav navbar-nav">
						<li><a href="MeetTheTeam.jsp">Unser Team</a></li>
					</ul>
					<ul class="nav navbar-nav navbar-right">
						<li><a href="Register.jsp"><span class="glyphicon glyphicon-user"></span>
								Registrieren</a></li>

					</ul>
				</div>
			</div>
		</nav>
	<div class="hero-image1">
	
		<div class="container" id="c1">
			<div class="center-content">
				<h1 class="title"> <img src="Images/logov4.svg" class="imglogo" alt="LOGO"> </h1>

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

	<script>
		$(document).ready(function() {
							$("#unmaskbtn").on('click',function() {
												if ($("#pwinput").attr('type') == 'password') {
													console.log(1);
													var input = $("#pwinput");
													var pw = input.val();
													console.log(pw);
													input.replaceWith("<input type=\"text\" name=\"password\" id=\"pwinput\" placeholder=\"Passwort\" class=\"form-control\" value=\""+pw+"\" required>");
													$(this).html("<span class=\"glyphicon glyphicon-eye-close\"></span>")

												} else {
													console.log(2);
													var input = $("#pwinput");
													console.log(input);
													var pw = input.val();
													console.log(pw);
													input.replaceWith("<input type=\"password\" name=\"password\" id=\"pwinput\" class=\"form-control\" placeholder=\"Passwort\" value=\""+pw+"\" required>");
													$(this).html("<span class=\"glyphicon glyphicon-eye-open\"></span>")

												}

											});
						})
	</script>

</body>
</html>