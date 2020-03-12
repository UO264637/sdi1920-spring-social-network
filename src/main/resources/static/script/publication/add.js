'use strict';

var singleUploadForm = document.querySelector('#singleUploadForm');
var singleFileUploadInput = document.querySelector('#singleFileUploadInput');
var titleInput = document.querySelector('#titleInput');
var textInput = document.querySelector('#textInput');

function uploadSingleFile(file, text, title) {
    var formData = new FormData();
    formData.append("file", file);
    formData.append("text", text);
    formData.append("title", title);

    var xhr = new XMLHttpRequest();
    xhr.open("POST", "/uploadFile");

    xhr.onload = function() {
        //console.log(xhr.responseText);
    }

    xhr.send(formData);
    console.log("DONE1");
}

singleUploadForm.addEventListener('submit', function(event){
    var files = singleFileUploadInput.files;
    var text = textInput.value;
    var title = titleInput.value;
    if(files.length === 0) {
    	uploadSingleFile(null, text, title);
    }
    else {
    	uploadSingleFile(files[0], text, title);
    	//event.preventDefault();
    	console.log("DONE2");
    }
    console.log("DONE3");
}, true);

