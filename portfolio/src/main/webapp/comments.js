function writeCommentsToConsole(){
    fetch('/comments-data')
    .catch(console.log('failed to fetch comments data from server'))
    .then(response => response.json())
    .catch(console.log('failed to parse comments'))
    .then(response => {
        for(var i=0; i<response.length; i++){
            console.log(response[i]);
        }
    })
    .catch(console.log('failed to log comments to console'));
}