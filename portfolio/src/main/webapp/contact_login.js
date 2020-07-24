function isUserLoggedIn(){
    fetch('user-data')
    .catch(error => "failed to fetch from user-data: " + error)
    .then(respone => respone.json())
    .catch(error => "failed to convert to json: " + error)
    .then(respone => console.log("user is logged in: " + respone));
}