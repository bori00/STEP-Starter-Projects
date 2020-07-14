var slideIndex = 1;
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