//alert("Welcome!\nThis page contains some quiz questions. Hover your mouse over the question boc to find out the answer.\nGood luck!");
var modal = document.getElementById("modal-quiz");
console.log(modal);
window.onload = function() {
    modal.style.display = "block";
}
var span = document.getElementsByClassName("close")[0];
span.onclick = function() {
  modal.style.display = "none";
}
window.onclick = function(event) {
  if (event.target == modal) {
    modal.style.display = "none";
  }
}