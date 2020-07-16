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