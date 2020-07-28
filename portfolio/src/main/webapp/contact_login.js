function handleUserLogin(){
    console.log("trying to fetch user data from UserServlet");
    fetch('user-data')
    .catch(error => "failed to fetch from user-data: " + error)
    .then(response => response.json())
    .catch(error => "failed to convert to json: " + error)
    .then(response => {
        if(response.isLoggedIn == true){
            console.log("user is logged in: " + response);
            displayContactForm();
            displayLogoutUrl(response);
            if(response.savedUser!=undefined){
                disableSavedInputs(response);
                console.log("user has saved data in database");
            }
            else{
                console.log("user has no saved data in database");
            }
        }
        else{
            console.log("user is not logged in");
            displayLoginUrl(response);
        }
    });
}

function displayContactForm(){
    document.getElementById("contact-form").style.display="block";
}

function displayLogoutUrl(response){
    var logoutUrl = response.logoutUrl;
    console.log("logoutUrl = " + logoutUrl);
    document.getElementById("logout-url").innerHTML = "You can change account here<a class=\"link-light\" href=\"" + logoutUrl + "\"> here</a>";
}

function displayLoginUrl(response){
    var loginUrl = response.loginUrl;
    console.log("loginUrl = " + loginUrl);
    document.getElementById("login-info").innerHTML = "Please login<a class=\"link-emphasise\" href=\"" + loginUrl + "\"> here</a>";
}

function disableSavedInputs(response){
    console.log("disable saved inputs");
    console.log(response.savedUser.firstName);
    disableInputWithPresetText(document.getElementById("first-name-input"), response.savedUser.firstName);
    disableInputWithPresetText(document.getElementById("last-name-input"), response.savedUser.lastName);
    disableInputWithPresetText(document.getElementById("phone-input"), response.savedUser.phone);
    disableCheck(document.getElementById("job-related-check"));
    disableInputWithPresetText(document.getElementById("job-title-dropdown"), response.savedUser.jobTitle);
}

function disableInputWithPresetText(input, text){
    input.disabled = true;
    input.value = text;
}

function disableCheck(check){
    check.disabled = true;
}