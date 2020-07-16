var modal = document.getElementById("modal-quiz");
console.log(modal);

window.onload = function() {
    modal.style.display = "block";
}

var span = document.getElementById("close-quiz");
span.onclick = function() {
  modal.style.display = "none";
}

window.onclick = function(event) {
  if (event.target == modal) {
    modal.style.display = "none";
  }
}