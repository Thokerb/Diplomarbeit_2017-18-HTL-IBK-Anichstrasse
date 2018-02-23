<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<!-- jquery datatable stylesheet bootstrap -->
<link rel="stylesheet" type="text/css"
	href="https://cdn.datatables.net/1.10.16/css/dataTables.bootstrap.min.css"></link>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" />

			<!-- jquery script  -->
	<script src="jquery-3.2.1.js"></script>



	<!-- bootstrap javascript implementation -->
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

	<!-- jquery datatable javascript -->
	<script type="text/javascript"
		src="//cdn.datatables.net/1.10.16/js/jquery.dataTables.min.js"></script>

		


</head>
<%

System.out.println(session.getAttribute("user"));

if(session.getAttribute("user") == null){
	response.sendRedirect("Login.jsp");
}
%>
<body>
<h1>Gelöschte Dateien</h1>
				<table id="datatable" class="table table-striped table-bordered"
					cellspacing="0" width="100%">
					<thead>
						<tr>
							<th>ID</th>
							<th>DateiTyp</th>
							<th>Name</th>
							<th>Autor</th>
							<th>DeleteDatum</th>
							<th>UploadDatum</th>
							<th>DokumentDatum</th>
							<th>Wiederherstellen</th>
						</tr>
					</thead>
					<tbody>

					</tbody>
				</table>
				<script type="text/javascript">
				
				var table = $('#datatable').DataTable({
					
					
					"processing" : true,
					"serverSide" : true,
		            "ajax" : {
					"url" : '/DiplomPdf/DataTableServlet',
					"data" : function(act){
						act.user = '${user}';
						act.table = 'whtable';
					},
					"type" : 'POST',
						"dataSrc": "data"
					},
		            
		            
		            
					"language": {
						
							"sEmptyTable":   	"Keine Daten in der Tabelle vorhanden",
							"sInfo":         	"_START_ bis _END_ von _TOTAL_ Einträgen",
							"sInfoEmpty":    	"0 bis 0 von 0 Einträgen",
							"sInfoFiltered": 	"(gefiltert von _MAX_ Einträgen)",
							"sInfoPostFix":  	"",
							"sInfoThousands":  	".",
							"sLengthMenu":   	"_MENU_",
							"sLoadingRecords": 	"Wird geladen...",
							"sProcessing":   	"Bitte warten...",
							"sSearch":       	"<span class=\"glyphicon glyphicon-search\"></span>",
							"sZeroRecords":  	"Keine Einträge vorhanden.",
							"oPaginate": {
								"sFirst":    	"Erste",
								"sPrevious": 	"<span class=\"glyphicon glyphicon-circle-arrow-left arrowpagenav\" data-toggle=\"tooltip\" title =\"Vorherige Seite\"></span>",
								"sNext":     	"<span class=\"glyphicon glyphicon-circle-arrow-right arrowpagenav\" data-toggle=\"tooltip\" title =\"Nächste Seite\"></span>",
								"sLast":     	"Letzte"    
							},
							"oAria": {
								"sSortAscending":  ": aktivieren, um Spalte aufsteigend zu sortieren",
								"sSortDescending": ": aktivieren, um Spalte absteigend zu sortieren"
							}
						
					},


					
					

					"columns" : [ 
						{"data" : "ID"},
						{"data" : "DateiTyp"},
						{"data" : "Name"},
						{"data" : "Autor"},
						{"data" : "DeleteDatum"},
						{"data" : "UploadDatum"},
						{"data" : "DokumentDatum"},
						{"data" : "Wiederherstellen"}
					

					],
					

			        "columnDefs": [ 
			        	{
			        		"targets": 0,
			        		"visible": false,
			        		"searchable": false
			        	},
			        	{
			            "targets": -1,
			            "data": "null",
			            "defaultContent": "<button class=\"whbutton btn-link btn-datatable\" data-toggle=\"tooltip\" title =\"Stelle dein Dokument wieder her\"><span class=\"glyphicon glyphicon-level-up\" ></span></button>"
			        },
			        {
			        	"targets": 1,
			        	"render": function func(data){
			        		console.log(data);
			        		var text = "";
			        		switch(data){
			        		case "PDF":
			        			text = "<span class=\"fa fa-file-pdf-o icontype\" ></span>";
			        			break;
			        		case "TXT":
			        			text = "<span class=\"fa fa-file-text-o	\" ></span>";
			        			break;
			        		case "DOC":
			        			text = "<span class=\"fa fa-file-word-o	 icontype\" ></span>";
			        			break;
			        		case "DOCX":
			        			text = "<span class=\"fa fa-file-word-o	 icontype\" ></span>";
			        			break;
			        		default:
			        			text = "<span class=\"fa fa-question icontype\" ></span>";
			        			break;
			        		}
			        		return text;
			        	}
			        	
			        }
			        ],

				    initComplete : function() {		// wird aufgerufen, wenn der DataTable fertig geladen ist
				        var input = $('#datatable_wrapper .dataTables_filter input').off(), //Löscht alle existierenden Listener von der Inputbox
				           self = this.api(),			// referenziert den DataTable in eine Variable, damit er innerhalb der Suchen - Funktion aufgerufen werden kann
				            $searchbutton = $('<button class=\"btn-success dttopbtn\" data-toggle=\"tooltip\" title =\"Suchen\">')	//erstellt ein Buttonobjekt
				                       .html('<span class="glyphicon glyphicon-search"/>')		// Button - Text: Suchen
				                       .click(function() {	//Funktion welche bei drücken des Buttons aufgerufen wird
				                        self.search(input.val()).draw();	//ruft die Search - Funktion des DataTables auf und aktualisiert
				                       }),
				            $deletebutton = $('<button class=\"btn-danger dttopbtn\"data-toggle=\"tooltip\" title =\"Löschen\">')	//erstellt ein Buttonobjekt
				                       .html('<span class="glyphicon glyphicon-remove"></span>')		//Button - Text: Löschen
				                       .click(function() {	//Funktion welche bei drücken des Buttons aufgerufen wird
				                          input.val('');	//Setzt den Input wieder leer
				                          $searchbutton.click(); 	//Betätigt die searchbutton - funktion, jetzt jedoch mit leerem Inhalt, zum Aktualisieren
				                       }) 
				        $('#datatable_wrapper .dataTables_filter').append($searchbutton, $deletebutton);	//Fügt beide Buttons zum DataTable hinzu
				    },
				
				});
		    	
			  
				
			  
				
				</script>
</body>
</html>