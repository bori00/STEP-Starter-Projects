function addMaxCommentsInputListener(){
    var maxCommentsInput = document.getElementById("max-comments");
    maxCommentsInput.addEventListener("change", displayComments);
}

function displayComments(){
    removeAllCommentsFromDOM();
    var maxComments = getNoMaxComments();
    console.log(maxComments);
    var url = new URL("/comments-data", document.URL);
    url.searchParams.append('max-comments', maxComments)
    fetch(url) 
    .catch(error => console.log('failed to fetch comments data from server: '+ error))
    .then(response => response.json())
    .catch(error => console.log('failed to parse comments: ' + error))
    .then(response => addAllCommentsToDOM(response))
    .catch(error => console.log('failed to print comments to DOM: ' + error));
}

function deleteAllComments(){
    fetch('/delete-comments-data', {method: 'POST'})
    .catch(error => console.log('failed to fetch from delete-comments-data'))
    .then(respone => displayComments());
}

function getNoMaxComments(){
    return document.getElementById("max-comments").value;
}

function createCardHolderElement(){
    var cardHolder = document.createElement("div");
    cardHolder.setAttribute('class', 'card-holder-small');
    return cardHolder;
}

function createCardElement(highlight){
    var card = document.createElement("div");
    card.setAttribute('class', 'card-small');
    if(highlight===true){
        card.style.backgroundColor = "hsla(20, 100%, 70%, 60%)"; 
    }
    return card;
}

function createHeading3Element(string){
    var heading = document.createElement("h3"); 
    heading.innerText = string;
    return heading;
}

function createHeading5Element(string){
    var heading = document.createElement("h5");
    heading.setAttribute('class', 'bottom-heading'); 
    heading.innerText = string;
    return heading;
}

function createParagraphElement(string){
    var paragraph = document.createElement("p"); 
    paragraph.innerText = string;
    return paragraph;
}

function addCommentToDOM(comment){
    console.log("displaying comment " + comment)
    var cardHolder = createCardHolderElement();
    var card = createCardElement(false);
    var messageParagraph = createParagraphElement(comment.message);
    var senderNameHeading = createHeading3Element("unknown says:");
    var emailHeading = createHeading5Element("contact: " + "unknown mail");
    card.appendChild(senderNameHeading);
    card.appendChild(messageParagraph);
    card.appendChild(emailHeading);
    cardHolder.appendChild(card);
    var main = document.getElementById("main-comments");        
    main.appendChild(cardHolder);  
}

function addAllCommentsToDOM(comments){
    console.log("add all comments to DOM: "+  comments.length)
    for(var i=0; i<comments.length; i++){
        addCommentToDOM(comments[i]);
        console.log(comments[i]);
    }
}

function removeAllCommentsFromDOM(){
    document.getElementById("main-comments").innerHTML = "";
}