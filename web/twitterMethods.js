/**
 * Created by richardfenton on 4/10/16.
 */

function myFunction() {
    document.getElementById("myHeader").innerHTML = "Hello JavaScript!";
}


function createRequest() {
    var result = null;
    if (window.XMLHttpRequest) {
        // FireFox, Safari, etc.
        result = new XMLHttpRequest();
        if (typeof result.overrideMimeType != 'undefined') {
            result.overrideMimeType('text/xml'); // Or anything else
        }
    }
    else if (window.ActiveXObject) {
        // MSIE
        result = new ActiveXObject("Microsoft.XMLHTTP");
    }
    else {
        // No known mechanism -- consider aborting the application
    }
    return result;
}

//Search for term
function search(){
    var req = createRequest(); // defined above
    // Create the callback:
    req.onreadystatechange = function() {
        if (req.readyState != 4) return; // Not there yet
        if (req.status != 200) {
            // Handle request failure here...
            return;
        }
        // Request successful, read the response
        var resp = req.responseText;
        // ... and use it as needed by your app.
    }

    req.open("GET", "http://localhost:8080/api/hello/mood/search/"+document.getElementById("textfield").value, true);
    req.send();

}

//Open Twitter Search Terminal
function startMood(){
    var req = createRequest(); // defined above
    // Create the callback:
    req.onreadystatechange = function() {
        if (req.readyState != 4) return; // Not there yet
        if (req.status != 200) {
            // Handle request failure here...
            return;
        }
        // Request successful, read the response
        var resp = req.responseText;
        // ... and use it as needed by your app.
    }

    req.open("GET", "http://localhost:8080/api/hello/mood/s/", true);
    req.send();

}

//Get Current Mood
function getMood(){
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if (xhr.readyState == XMLHttpRequest.DONE) {
            document.getElementById("mood").innerHTML="Current Mood: "+xhr.responseText;
        }
    }

    xhr.open("GET", "http://localhost:8080/api/hello/mood", true);
    xhr.send(null);

    setTimeout(getMood, 5000);

}




//function getURLs(){
//
//    var xhr = new XMLHttpRequest();
//    xhr.onreadystatechange = function() {
//        if (xhr.readyState == XMLHttpRequest.DONE) {
//            xhr.responseText;
//        }
//    }
//
//    xhr.open("GET", "http://localhost:8080/api/hello/mood/url", true);
//    xhr.send(null);
//
//    setTimeout(getURLs, 5000);
//
//}

