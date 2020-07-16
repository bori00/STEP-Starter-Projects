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
    var romaniaModal = document.getElementById("modal-romania");
    var closeBtnRomania = document.getElementById("close-romania");
    showModal(romaniaModal, closeBtnRomania);
}

function showClujModal(){
    console.log("show cluj modal");
    var romaniaModal = document.getElementById("modal-cluj");
    var closeBtnRomania = document.getElementById("close-cluj");
    showModal(romaniaModal, closeBtnRomania);
}
