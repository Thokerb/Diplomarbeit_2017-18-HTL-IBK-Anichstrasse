<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Passwort Zurücksetzen</title>

<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" />
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/1000hz-bootstrap-validator/0.11.5/validator.min.js"></script>
<link rel="stylesheet" href="StyleHeroImage.css">
<link rel="stylesheet" href="styleLogin.css">
</head>

<%
	if (session.getAttribute("hashcodeverified") != "yes") {
		response.sendRedirect("ErrorPage.html");
	}
%>

<body>
	<div class="heroimage">

		<div class="container">
			<div class="center-content">
				<h1>Gib dein neues Passwort ein</h1>

				<form action="ResetPasswort" method="post" id="registerform"
					data-toggle="validator">
					<div class="form-group">
						<div class="input-group">
							<input class="form-control" type="password" data-minlength="8"
								data-maxlength="20" id="pwinput" name="password"
								data-minlength-error="Passwort muss mindestens 8 Zeichen haben"
								required> <span class="input-group-btn">
								<button class="btn-link btnpw form-control" type="button"
									id="unmaskbtn">
									<span class="glyphicon glyphicon-eye-open"></span>
								</button>
							</span>
						</div>
						<div class="help-block with-errors"></div>

					</div>
					<div class="form-group">
						<input class="form-control" type="password" id="pwinput2"
							name="password2" data-match="#pwinput"
							data-match-error="Passwörter stimmen nicht überein" required>
						<div class="help-block with-errors"></div>
					</div>
					<div class="form-group">
						<input type="submit" class="form-control btn btn-primary"
							value="Change Password">
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
						<button type="button" class="btn btn-default"
							onclick="location.href = 'Startseite.jsp';">Startseite</button>
						<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					</div>
				</div>

			</div>
		</div>


		<script>
			$(document)
					.ready(
							function() {

								var status = $("#statusmessage").text();
								console.log(status);
								if (status != "") {
									$(".modal-body").removeClass("loader");
									$(".modal-body").removeClass(
											"center-content");
									$("#statusModal").modal("show");
								}

								var register = $("#registerform");

								register
										.validator({

											custom : {
												maxlength : function($el) {
													console
															.log("called custom");
													var matchValue = $el
															.data("maxlength");
													console
															.log($el.val().length);
													if ($el.val().length > matchValue) {
														return "Das Passwort ist zu lang."
														register
																.validator('update');
													}

												}
											}

										});

								$("#registerform")
										.submit(
												function(event) {
													event.preventDefault();

													if (form_validate("registerform")) {
														$("#registerform")[0]
																.submit(); //only calls once, no infinite loop
														console.log("yeah");
														$(".modal-body")
																.addClass(
																		"loader");
														$(".modal-body")
																.addClass(
																		"center-content");
														$("#statusModal")
																.modal("show");
													} else {
														console.log("nope");

													}

												});

								function form_validate(attr_id) {
									var result = true;
									$('#' + attr_id).validator('validate');
									$('#' + attr_id + ' .form-group').each(
											function() {
												if ($(this).hasClass(
														'has-error')) {
													result = false;
													return false;
												}
											});
									return result;
								}

								$("#unmaskbtn")
										.on(
												'click',
												function() {
													if ($("#pwinput").attr(
															'type') == 'password') {
														var input = $("#pwinput");
														var pw = input.val();
														input
																.replaceWith("<input type=\"text\" name=\"password\" data-maxlength=\"20\" id=\"pwinput\" placeholder=\"Passwort\" data-minlength=\"8\" class=\"form-control\" value=\""+pw+"\" required>");
														$(this)
																.html(
																		"<span class=\"glyphicon glyphicon-eye-close\"></span>")

														var input2 = $("#pwinput2");
														var pw2 = input2.val();
														input2
																.replaceWith("<input type=\"text\" name=\"password\" id=\"pwinput2\" placeholder=\"Passwort wiederholen\" class=\"form-control\" data-match=\"#pwinput\" data-match-error=\"Passwörter stimmen nicht überein\" value=\""+pw2+"\" required>");

														register
																.validator('update');
													} else {
														var input = $("#pwinput");
														var pw = input.val();
														input
																.replaceWith("<input type=\"password\" name=\"password\" data-maxlength=\"20\" id=\"pwinput\" class=\"form-control\" data-minlength=\"8\" placeholder=\"Passwort\" value=\""+pw+"\" required>");
														$(this)
																.html(
																		"<span class=\"glyphicon glyphicon-eye-open\"></span>");

														var input2 = $("#pwinput2");
														var pw2 = input2.val();
														input2
																.replaceWith("<input type=\"password\" name=\"password\" id=\"pwinput2\" class=\"form-control\" placeholder=\"Passwort wiederholen\" data-match=\"#pwinput\" data-match-error=\"Passwörter stimmen nicht überein\" value=\""+pw2+"\" required>");

														register
																.validator('update');
													}

												});
							})
		</script>
</body>
</html>