var theThings = document.querySelectorAll(".thing");

var transitionDurations = ["transitionDuration", "msTransitionDuration", "webkitTransitionDuration", "mozTransitionDuration", "oTransitionDuration"];
var transitionDurationProperty = getSupportedPropertyName(transitionDurations);

var transforms = ["transform", "msTransform", "webkitTransform", "mozTransform", "oTransform"];
var transformProperty = getSupportedPropertyName(transforms);
var xpos =400;


function setInitialProperties() {

    for (var i = 0; i < theThings.length; i++) {
        var theThing = theThings[i];

        var circleSize = Math.round(30 + Math.random() * 150);

        theThing.style.width = 40 + "px";
        theThing.style.height = 40 + "px";
        theThing.style.borderRadius = .5 * 40 + "px";
        theThing.style.opacity = .1 + Math.random() * .5;

        setTranslate3DTransform(theThing);
    }
    setTimeout(kickOffTransition, 1000);
}
setInitialProperties();


function kickOffTransition() {
    theThings = document.querySelectorAll(".thing");
    for (var i = 0; i < theThings.length; i++) {
        var theThing = theThings[i];

        theThing.addEventListener("transitionend", updatePosition, false);
        theThing.addEventListener("webkitTransitionEnd", updatePosition, false);
        theThing.addEventListener("mozTransitionEnd", updatePosition, false);
        theThing.addEventListener("msTransitionEnd", updatePosition, false);
        theThing.addEventListener("oTransitionEnd", updatePosition, false);

        setTranslate3DTransform(theThing);
        setTransitionDuration(theThing);
    }
}

function updatePosition(e) {
    var theThing = e.currentTarget;

    if (e.propertyName.indexOf("transform") != -1) {
        setTranslate3DTransform(theThing);
        setTransitionDuration(theThing);
    }
}

function getRandomXPosition() {
    return Math.round(-50 + Math.random() * 650);
}

function getRandomYPosition() {
    return Math.round(-50 + Math.random() * 400);
}

function getRandomDuration() {
    return (.5 + Math.random() * 3) + "s";
}

function getSupportedPropertyName(properties) {
    for (var i = 0; i < properties.length; i++) {
        if (typeof document.body.style[properties[i]] != "undefined") {
            return properties[i];
        }
    }
    return null;
}

function setTranslate3DTransform(element) {
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if (xhr.readyState == XMLHttpRequest.DONE) {
            xpos=Number(xpos)+Number(xhr.responseText)*5+Number(.1);

            console.log("xhr: ", xhr.responseText);
        }
    }

    xhr.open("GET", "http://localhost:8080/api/hello/mood/count", true);
    xhr.send(null);
    console.log("xpos: ",xpos);
    //xpos=xpos+50;
    element.style[transformProperty] = "translate3d(" + xpos + "px" + ", " + 150 + "px" + ", 0)";
}

function setTransitionDuration(element) {
    if (transitionDurationProperty) {
        element.style[transitionDurationProperty] = "4s";
    }
}
