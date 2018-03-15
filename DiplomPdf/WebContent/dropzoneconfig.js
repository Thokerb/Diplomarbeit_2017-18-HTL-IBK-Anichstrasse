/**
 * 
 */

Dropzone.myDropzone = false;
		var size = 1;
		var name;
		Dropzone.options.myDropzone = {

			init : function() {
				var dropzone = this;
				var filetogive;
				var givename;
				var tochange;
				var overwrite = false;

				function getDokumentNamen(namedatei) {

					$.getJSON("DateienListServlet", function(responseText) { // Execute Ajax GET request on URL of "someservlet" and execute the following function with Ajax response text...
						var DokumentNamen = responseText;

						console.log(DokumentNamen);
						console.log(namedatei);
						var vorhanden = $.inArray(namedatei, DokumentNamen);
						console.log(vorhanden);

						if (vorhanden === -1) {
							console.log("nicht vorhanden");
							sendfile();
						} else {
							console.log("vorhanden");
							addChecker(DokumentNamen);
							showModal(namedatei);
							console.log(DokumentNamen);

						}
					});

				}

				this.on("addedfile", function(file) {
					tochange = file.previewElement
							.querySelector("[data-dz-name]");
					givename = file.name;
					console.log(givename);
					getDokumentNamen(givename);
					filetogive = file;
					console.log(file);
				});

				$("#overwritebtn").on("click", function() {
					overwrite = true;
					sendfile();
					$("#saveModal").modal("hide");
				})

				$("#modalinputbtn").on("click", function() {
					console.log("filetogive");
					givename = $("#modalinput").val();
					tochange.innerHTML = givename;
					dropzone.processFile(filetogive);
					$("#saveModal").modal("hide");
				});

				function sendfile() {
					console.log("sendingstatus");
					console.log(filetogive.status);
					if (filetogive.status != "error") {
						dropzone.processFile(filetogive);
					}

				}

				this.on("renameFile", function(file) {
					alert("called renameFile");
				});

				this.on("sending", function(file, xhr, formData) {
					console.log("sending called");
					formData.append("dateiname", givename);
					formData.append("overwrite", overwrite);
					overwrite = false;
				});
				
				this.on("success",function(file){
					console.log("success");
				});
				this.on("complete",function(file){
					console.log("complete");
				});
				this.on("uploadprogress",function(file,progress,bytesSent){
					console.log("progress: "+progress+" | "+bytesSent);
				});

				console.log("finished init");
			},
			maxFilesize : size,
			paramName : "pdffile",
			url : "UploadServlet",
			acceptedFiles : "application/pdf,application/msword,application/vnd.openxmlformats-officedocument.wordprocessingml.document,text/plain",
			parallelUploads : 1,
			autoQueue : false,
			autoProcessQueue : false,
			dictDefaultMessage : "Ziehe Dateien hierhin zum Hochladen",
			dictFallbackMessage : "Dieser Browser wird leider nicht unterstützt",
			dictFileTooBig : "Die Datei ist leider zu groß. Erlaubtes Maximum sind "
					+ size + " MB",
			dictInvalidFileType : "Dies ist leider der falsche Dateityp. Es werden nur PDF-Dateien unterstützt"

		}

		console.log("finished dropzone config javascript")