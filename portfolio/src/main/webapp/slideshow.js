//sliding
var slideIndex = 1;
var slides = document.getElementsByClassName("slide");
var noSlides = slides.length;
showObject(slides[slideIndex-1]); //initial state

//hide/show functions
function showObject(object){
    object.style.display="block";
}

function hideObject(object){
    object.style.display="none";
}


//sliding
function incrementSlideIndex(n){
    slideIndex=slideIndex+n;
    if(slideIndex>noSlides || slideIndex<1 ){
        slideIndex=((slideIndex-1)%noSlides+noSlides)%noSlides+1;
    }
}

function hideAllSlides(){
    for(var i=0; i<noSlides; i++){
        hideObject(slides[i]);
    }
}

function plusSlides(n){
    incrementSlideIndex(n);
    hideAllSlides();
    showObject(slides[slideIndex-1]);
}

// hiding buttons, captions and slidenumbers + showing them when mouse is moved
var hidingObjects = document.querySelectorAll('.btn-prev, .btn-next, .caption, .slidenumber')
setHidingObjectsEventListener();

var showTimeout; //time period for showing the objects
function showObjects(){
    clearTimeout(showTimeout);
    console.log("show objects");
    for (var i = 0; i < hidingObjects.length; i++) {
        showObject(hidingObjects[i]);
    }
    //hide buttons after 2s, if the mouse is not moved again
    showTimeout = setTimeout(hideObjects, 3000); 
}

function hideObjects(){
    console.log("hide objects");
    for (var i = 0; i < hidingObjects.length; i++) {
        hideObject(hidingObjects[i]);
    }
}

function setHidingObjectsEventListener(){
    window.addEventListener('mousemove', showObjects);
}


