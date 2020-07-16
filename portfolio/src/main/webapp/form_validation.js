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


//email check
function validEmail(string){
    return /@/.test(string) && !(/^@/.test(string) || /@$/.test(string));
}
var email_input = document.getElementById("email-input");
email_input.addEventListener('change', function() {
    console.log("test email");
    if(this.value!="" && !validEmail(this.value)) {
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


//phone check
function isNumeric(string)
{
    return /^\d+$/.test(string);
}

function validPhone(string){
    return string.length===10 && isNumeric(string);
}

var phone_input = document.getElementById("phone-input");
phone_input.addEventListener('change', function() {
    console.log("test phone: ", this.value, this.value.length);
    if(this.value!="" && !validPhone(this.value)) {
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

function formValidation(){
    var inputs = document.getElementsByTagName("input");
    var valid = true;
    for(var i=0; i<inputs.length && valid; i++){
        if(!inputs[i].valid){
            valid = false;
        }
        else{
            console.log(inputs[i])
        }
    }
    if(valid){
        window.alert("Message succesfully sent!")
    }
}