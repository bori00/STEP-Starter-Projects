//sliding
var slideIndex = 1;
var timeout;
showSlide(slideIndex); //initial state

function showSlide(index){
    var slides = document.getElementsByClassName("slide");
    var noSlides = slides.length
    console.log(noSlides);
    if(index>noSlides || index<1 ){
        index=(index%noSlides+noSlides)%noSlides+1;
    }
    for(var i=0; i<noSlides; i++){
        slides[i].style.display="none";
    }
    slides[index-1].style.display="block";
    console.log("show slide: " + index);
}

function plusSlides(n){
    slideIndex=slideIndex+n;
    showSlide(slideIndex);
}

// fading buttons, captions and slidenumbers
var fadingObjects = document.querySelectorAll('.btn-prev, .btn-next, .caption, .slidenumber')
setFadingObjectsEventListener();

function showButtons(){
    clearTimeout(timeout);
    console.log("show buttons");
    for (var i = 0; i < fadingObjects.length; i++) {
        fadingObjects[i].style.display="block";
    }
    timeout = setTimeout(hideButtons, 3000);
}

function fadeObject(object){
    for(var i=10; i>=1; i--){
        object.style.opacity=i/10;
    }
}

function hideButtons(){
    console.log("hide buttons");
    for (var i = 0; i < fadingObjects.length; i++) {
        fadingObjects[i].style.display="none";
    }
}

function setFadingObjectsEventListener(){
    window.addEventListener('mousemove', showButtons);
}


