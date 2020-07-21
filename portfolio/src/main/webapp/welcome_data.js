function getWelcomeText(){
    console.log("trying to get welcome text");
    fetch('/welcome-data')
    .catch(error => console.log('failed to get resposne: ' + error))
    .then(response => response.text())
    .catch(error => console.log('failed to convert to text: ' + error))
    .then(welcomeText => document.getElementById('welcome-data').innerText=welcomeText)
    .catch(error => console.log('failed to print text to html element: ' + error));
}