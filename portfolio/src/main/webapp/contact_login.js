function isUserLoggedIn(){
    fetch('user-data')
    .catch(error => "failed to fetch from user-data: " + error)
    .then(response => response.json())
    .catch(error => "failed to convert to json: " + error)
    .then(response => {
        if(response.isLoggedIn==true){
            document.getElementById("contact-form").style.display="block";
            console.log("user is logged in: " + response)
        }
        else{
            console.log("user is not logged in");
            var loginUrl = response.loginUrl;
            console.log("loginUrl = " + loginUrl);
            document.getElementById("login-info").innerHTML = "Please login<a class=\"link-emphasise\" href=\"" + loginUrl + "\"> here</a>";
        }
    });
}