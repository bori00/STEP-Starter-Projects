function displayComments(){
    fetch('/comments-data')
    .catch(error => console.log('failed to fetch comments data from server: '+ error))
    .then(response => response.json())
    .catch(error => console.log('failed to parse comments: ' + error))
    .then(response => addAllCommentsToDOM(response))
    .catch(error => console.log('failed to print comments to DOM: ' + error));
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
        card.style.backgroundColor = "hsla(20, 100%, 70%, 60%)"; //"#ff9966";
    }
    return card;
}

function createHeadingElement(string){
    var heading = document.createElement("h3"); 
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
    var card = createCardElement(comment.jobTitle!==undefined);
    var messageParagraph = createParagraphElement(comment.message);
    var senderNameHeading = createHeadingElement(comment.firstName + " " + comment.lastName + " says:");
    card.appendChild(senderNameHeading);
    card.appendChild(messageParagraph);
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