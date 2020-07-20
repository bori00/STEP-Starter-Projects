function getWelcomeText(){
    console.log("trying to get welcome text");
    fetch('/welcome-data')
    //.catch(console.log('failed to get resposne'))
    .then(response => response.text())
    //.catch(console.log('failed to convert to text'))
    .then(welcomeText => document.getElementById('welcome-data').innerText=welcomeText)
    //.catch(console.log('failed to print text to html element'));
}