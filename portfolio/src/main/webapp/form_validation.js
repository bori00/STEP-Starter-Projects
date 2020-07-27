//enable job title input if email is job related
var jobRelated_checkbox = document.getElementById("job-related-check");
var jobTitle_dropdown = document.getElementById("job-title-dropdown");
jobRelated_checkbox.addEventListener('change', function() {
    if(this.checked) {
        jobTitle_dropdown.disabled=false;
    } else {
        jobTitle_dropdown.disabled=true;
    }
});

//phone check: add red outline if invalid
function isNumeric(string)
{
    return /^\d+$/.test(string);
}

function validPhone(string){
    return string!=="" && string.length===10 && isNumeric(string);
}

var phone_input = document.getElementById("phone-input");
phone_input.addEventListener('change', function() {
    console.log("test phone: ", this.value, this.value.length);
    if(!validPhone(this.value)) {
        this.style.outlineColor="red";
        this.style.borderColor="red";
        this.style.borderWidth="3px";
    }
    else{
        this.style.outlineColor="#f2eeed";
        this.style.borderColor="#8E8D8A";
        this.style.borderWidth="0.5px";
    }
});

//end check: show alert box if message is successfully sent
function formValidation(){
    console.log("validation");
    var valid=true;
    var firstName = document.getElementById("first-name-input").value;
    var lastName = document.getElementById("last-name-input").value;
    var comment = document.getElementById("comment-textarea").value;
    if(validPhone(phone_input.value) 
     && firstName != ""
     && lastName != ""
     && comment != ""){ //form is valid, can be sent
        var message = 'Your comment has been succesfully sent! I will contact you as soon as possible';
        window.alert(message);
     }
     else{
         console.log("invalid");
     }
}