function isUserLoggedIn(){
    fetch('user-data')
    .catch(error => "failed to fetch from user-data: " + error)
    .then(response => response.json())
    .catch(error => "failed to convert to json: " + error)
    .then(response => {
        console.log("response= "+ response)
        if(response.isUserLoggedIn===true){
            document.getElementById("contact-form").display="block";
            console.log("user is logged in: " + response)
        }
        else{
            console.log("user is not logged in");
            var loginUrl = response.loginUrl;
            console.log("loginUrl = " + loginUrl);
            document.getElementById("login-info").innerHtml = "Please login <a href=\"" + loginUrl + "\">here</a>";
        }
    });
}