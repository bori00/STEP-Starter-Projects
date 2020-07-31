function handleUserLoginAndBlobstoreUrl() {
    console.log("trying to fetch user data from UserServlet");
    fetch('user-data')
    .catch(error => console.log("user-data: failed to fetch from user-data: " + error))
    .then(userData => userData.json())
    .catch(error => console.log("user-data: failed to convert to json: " + error))
    .then(userData => {
        if(userData.isLoggedIn == true) {
            console.log("user is logged in: " + userData);
            handleBlobstoreUrlAndForm(userData);
        }
        else {
            console.log("user is not logged in");
            displayLoginUrl(userData);
        }
    });
}

function handleBlobstoreUrlAndForm(userData){
    console.log("trying to fetch blobstore upload url");
    fetch('/blobstore-upload-url')
    .catch(error => console.log("upload-url: failed to fetch from blobstore-upload-url: " + error))
    .then(uploadUrl => uploadUrl.text())
    .catch(error => console.log('upload-url: failed to convert to text: ' + error))
    .then(uploadUrl => {
        console.log("the upload url is: " + uploadUrl);
        displayContactForm(uploadUrl);
        displayLogoutUrl(userData);
        if (userData.savedUser!=undefined) {
            disableSavedInputs(userData);
            console.log("user has saved data in database");
        }
        else {
            console.log("user has no saved data in database");
        }
    });
}

function displayContactForm(uploadUrl) {
    var contactForm = document.getElementById("contact-form");
    contactForm.action = uploadUrl;
    contactForm.style.display="block";
}

function displayLogoutUrl(userData) {
    var logoutUrl = userData.logoutUrl;
    console.log("logoutUrl = " + logoutUrl);
    document.getElementById("logout-url").innerHTML = "You can change account here<a class=\"link-light\" href=\"" + logoutUrl + "\"> here</a>";
}

function displayLoginUrl(userData) {
    var loginUrl = userData.loginUrl;
    console.log("loginUrl = " + loginUrl);
    document.getElementById("login-info").innerHTML = "Please login<a class=\"link-emphasise\" href=\"" + loginUrl + "\"> here</a>";
}

function disableSavedInputs(userData) {
    console.log("disable saved inputs");
    console.log(userData.savedUser.firstName);
    disableInputWithPresetText(document.getElementById("first-name-input"), userData.savedUser.firstName);
    disableInputWithPresetText(document.getElementById("last-name-input"), userData.savedUser.lastName);
    disableInputWithPresetText(document.getElementById("phone-input"), userData.savedUser.phone);
    disableCheck(document.getElementById("job-related-check"));
    disableInputWithPresetText(document.getElementById("job-title-dropdown"), userData.savedUser.jobTitle);
}

function disableInputWithPresetText(input, text) {
    input.disabled = true;
    input.value = text;
}

function disableCheck(check) {
    check.disabled = true;
}