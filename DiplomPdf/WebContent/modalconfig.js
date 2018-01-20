function showModal(formdata){

	$("#saveModal").modal("show");
	$("#modalinput").val(formdata);
	$("#modaldatname").text(formdata);	//verwende .text anstatt .html da in diesem Fall die Formatierung des modals beibehalten wird

}

function addChecker(DokumentNamen){
	console.log("addkeypress");

	$("#modalinput").keyup(function(){				// statt keypress da ansonst 1 char delay
		console.log("keypressed!!!");
		console.log(DokumentNamen);
		var userinput = $("#modalinput").val();
		console.log(userinput);
		if($.inArray(userinput,DokumentNamen)==-1){
			console.log("nicht vorhandener name");
			if(userinput.match(".pdf$") ||userinput.match(".doc$") ||userinput.match(".docx$") ||userinput.match(".txt$")){
				console.log("Datei besitzt die richtige Endung");
				$("#modalinputbtn").prop("disabled",false); //nicht mit attr
				$("#modalinputbtn").attr("class","btn btn-primary active");
			}
			else{
				console.log("Die neue Datei ist keine PDF");
				$("#modalinputbtn").prop("disabled",true);	// nicht mit attr
				$("#modalinputbtn").attr("class","btn btn-primary disabled");
			}

		}
		else{
			console.log(" vorhandener name");
			$("#modalinputbtn").prop("disabled",true);	// nicht mit attr
			$("#modalinputbtn").attr("class","btn btn-primary disabled");
		}
	});
}

$(document).ready(function() {

	$("#saveModal").attr("data-backdrop","static");
	$("#saveModal").attr("data-keyboard","false");

	$("#bsbutton").click(function(){
		$("#saveModal").modal("show");
	});




	$("#saveModal").on("show.bs.modal",function(){
		var formdata = $("#text1").val();
		//alert(formdata);
		$("#modalinput").val(formdata);
		$("#modaldatname").text(formdata);	//verwende .text anstatt .html da in diesem Fall die Formatierung des modals beibehalten wird
	});

	$("#saveModal").on("hidden.bs.modal",function(){
		console.log("modal hide");
	})

	$("#saveModal").on("shown.bs.modal",function(){
		$("#modalinput").select();
	})
	

	$("#modalinput").on("click", function () {
		$(this).select();
	})

	$("#modalinputbutton").on("click",function(){
		//modal WebElement - config finished, next step intregrating into Hauptseite
	})

	console.log("finished loading modalconfig.js");
});