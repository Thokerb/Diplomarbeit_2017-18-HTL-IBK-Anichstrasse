<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<?xml version="1.0" encoding="ISO-8859-1" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>EasyPDF</title>


<!-- jquery script  -->
<script src="jquery-3.2.1.js"></script>

<!-- bootstrap implementation -->
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" />


<link rel="stylesheet" href="stylesheet.css"></link>

<!-- jquery datatable stylesheet bootstrap -->
<link rel="stylesheet" type="text/css"
	href="https://cdn.datatables.net/1.10.16/css/dataTables.bootstrap.min.css"></link>
<!-- jquery datatable javascript -->
<script type="text/javascript"
	src="//cdn.datatables.net/1.10.16/js/jquery.dataTables.min.js"></script>


<script type="text/javascript">
	$(document)
			.ready(
					function() {

						//aktiviert Tooltip
						$('[data-toggle="tooltip"]').tooltip();

						var table = $('#datatable')
								.DataTable(
										{
											"processing" : true,
											"serverSide" : true,
											"language" : {

												"sEmptyTable" : "Keine Daten in der Tabelle vorhanden",
												"sInfo" : "_START_ bis _END_ von _TOTAL_ Einträgen",
												"sInfoEmpty" : "0 bis 0 von 0 Einträgen",
												"sInfoFiltered" : "(gefiltert von _MAX_ Einträgen)",
												"sInfoPostFix" : "",
												"sInfoThousands" : ".",
												"sLengthMenu" : "_MENU_ Einträge anzeigen",
												"sLoadingRecords" : "Wird geladen...",
												"sProcessing" : "Bitte warten...",
												"sSearch" : "<span class=\"glyphicon glyphicon-search\"></span>",
												"sZeroRecords" : "Keine Einträge vorhanden.",
												"oPaginate" : {
													"sFirst" : "Erste",
													"sPrevious" : "<span class=\"glyphicon glyphicon-circle-arrow-left arrowpagenav\" data-toggle=\"tooltip\" title =\"Vorherige Seite\"></span>",
													"sNext" : "<span class=\"glyphicon glyphicon-circle-arrow-right arrowpagenav\" data-toggle=\"tooltip\" title =\"Nächste Seite\"></span>",
													"sLast" : "Letzte"
												},
												"oAria" : {
													"sSortAscending" : ": aktivieren, um Spalte aufsteigend zu sortieren",
													"sSortDescending" : ": aktivieren, um Spalte absteigend zu sortieren"
												}

											},

											"ajax" : {
												"url" : '/DiplomPdf/DataTableServlet',
												"type" : 'POST',
												"dataSrc" : "data"
											},

											"columns" : [ {
												"data" : "Name"
											}, {
												"data" : "Autor"
											}, {
												"data" : "UploadDatum"
											}, {
												"data" : "DokumentDatum"
											}, {
												"data" : "Download"
											}, {
												"data" : "Delete"
											}

											],

											"columnDefs" : [
													{
														"targets" : -2,
														"data" : "null",
														"defaultContent" : "<button class=\"downloadbutton btn-link btn-datatable\" data-toggle=\"tooltip\" title =\"Hier klicken zum Downloaden\"><span class=\"glyphicon glyphicon-arrow-down\" ></span></button>"
													},
													{
														"targets" : -1,
														"data" : "null",
														"defaultContent" : "<button class=\"deletebutton btn-link btn-datatable\" data-toggle=\"tooltip\" title =\"Hier klicken zum Löschen\"><span class=\"glyphicon glyphicon-remove\" ></span></button>"
													} ],

											initComplete : function() { // wird aufgerufen, wenn der DataTable fertig geladen ist
												var input = $(
														'.dataTables_filter input')
														.off(), //Löscht alle existierenden Listener von der Inputbox
												self = this.api(), // referenziert den DataTable in eine Variable, damit er innerhalb der Suchen - Funktion aufgerufen werden kann
												$searchbutton = $(
														'<button class=\"btn-success dttopbtn\" data-toggle=\"tooltip\" title =\"Suchen\">')
														//erstellt ein Buttonobjekt
														.html(
																'<span class="glyphicon glyphicon-search"/>')
														// Button - Text: Suchen
														.click(
																function() { //Funktion welche bei drücken des Buttons aufgerufen wird
																	self
																			.search(
																					input
																							.val())
																			.draw(); //ruft die Search - Funktion des DataTables auf und aktualisiert
																}), $deletebutton = $(
														'<button class=\"btn-danger dttopbtn\"data-toggle=\"tooltip\" title =\"Löschen\">')
														//erstellt ein Buttonobjekt
														.html(
																'<span class="glyphicon glyphicon-remove"></span>')
														//Button - Text: Löschen
														.click(
																function() { //Funktion welche bei drücken des Buttons aufgerufen wird
																	input
																			.val(''); //Setzt den Input wieder leer
																	$searchbutton
																			.click(); //Betätigt die searchbutton - funktion, jetzt jedoch mit leerem Inhalt, zum Aktualisieren
																})
												$('.dataTables_filter').append(
														$searchbutton,
														$deletebutton); //Fügt beide Buttons zum DataTable hinzu
											},

										});

						$('#datatable tbody')
								.on(
										'click',
										'.downloadbutton',
										function() {
											console
													.log("downloadbutton geklickt");

											var position = $(this)
													.parents("td");
											console.log(position);
											var y = $("<div class='animate'>Download</div>");
											position.append(y);
											y.animate({
												left : "-100px",
												bottom : "-100px"
											});
											setTimeout(function() {
												y.remove();
											}, 5000);

											var data = table.row(
													$(this).parents("tr"))
													.data();
											var str = JSON.stringify(data);
											var xhttp = new XMLHttpRequest();

											xhttp.open("POST",
													"DownloadServlet", true);
											xhttp
													.setRequestHeader(
															"Content-type",
															"application/x-www-form-urlencoded");
											xhttp.send("download=" + str);

										});

						$("#datatable tbody")
								.on(
										"click",
										".deletebutton",
										function() {
											alert("gelöscht");
											var data = table.row(
													$(this).parents("tr"))
													.data();
											var str = JSON.stringify(data);
											var xhttp = new XMLHttpRequest();
											xhttp.open("POST", "DeleteServlet",
													true);
											xhttp
													.setRequestHeader(
															"Content-type",
															"application/x-www-form-urlencoded");
											xhttp.send("todelete=" + str);
											table.draw();
										});

						$("#datatable tbody").on('mouseenter',
								'.glyphicon-arrow-down', function() {
									console.log("enter");
									$(this).addClass("iconeffect");

								});

						$("#datatable tbody")
								.on(
										"webkitAnimationEnd mozAnimationEnd animationEnd",
										".glyphicon-arrow-down", function() {
											console.log("called");
											$(this).removeClass("iconeffect");
										});

						//#datatable > tbody > tr.odd > td:nth-child(5) > button > span

						console.log("finished  js init");
					});
</script>

</head>
<body>

<%
	if(session.getAttribute("username")== null){
		response.sendRedirect("Login.jsp");
	}
%>

	<div class="container">
		<div class="row">Hier haben Sie einen Überblick über Ihre Dateien</div>
		<div class="row">
			<div class="col-sm-2"></div>
			<div class="col-sm-8">
				<table id="datatable" class="table table-striped table-bordered"
					cellspacing="0" width="100%">
					<thead>
						<tr>
							<th>Name</th>
							<th>Autor</th>
							<th>UploadDatum</th>
							<th>DokumentDatum</th>
							<th>Download</th>
							<th>Loeschen</th>
						</tr>

					</thead>
					<tbody>

					</tbody>
				</table>
			</div>
			<div class="col-sm-2"></div>
		</div>

	</div>




</body>
</html>