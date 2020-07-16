window.addEventListener("load", showInstructionModal)

function showModal(modal, closeButton){
    modal.style.display = "block";
    closeButton.onclick = function(){
        modal.style.display="none";
    }
    window.onclick = function(event) {
        if (event.target == modal) {
          modal.style.display = "none";
        }
    }
}

function showRomaniaModal(){
    console.log("show romania modal");
    var modal = document.getElementById("modal-romania");
    var closBtn = document.getElementById("close-romania");
    showModal(modal, closBtn);
}

function showClujModal(){
    console.log("show cluj modal");
    var modal = document.getElementById("modal-cluj");
    var closeBtn = document.getElementById("close-cluj");
    showModal(modal, closeBtn);
}

function showInstructionModal(){
    console.log("show instructions modal");
    var modal = document.getElementById("modal-instructions");
    var closeBtn = document.getElementById("close-instructions");
    showModal(modal, closeBtn);
}

