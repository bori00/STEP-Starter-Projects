function handleUserLogin(){
    console.log("trying to fetch user data from UserServlet");
    fetch('user-data')
    .catch(error => "failed to fetch from user-data: " + error)
    .then(response => response.json())
    .catch(error => "failed to convert to json: " + error)
    .then(response => {
        if(response.isLoggedIn==true){
            console.log("user is logged in: " + response);
            displayContactForm();
            displayLogoutUrl(response);
            if(response.savedUserEntity!=undefined){
                disableSavedInputs(response);
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
    disableInputWithPresetText(document.getElementById("first-name-input", response.savedUserEntity.propertyMap.firstName));
}

function disableInputWithPresetText(input, text){
    input.disabled = true;
    input.placeholder = text;
}