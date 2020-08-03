function addMaxCommentsInputListener() {
    var maxCommentsInput = document.getElementById("max-comments");
    maxCommentsInput.addEventListener("change", displayComments);
}

function displayComments() {
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

function addAllCommentsToDOM(commentDatas) {
    console.log("add all comments to DOM: "+  commentDatas.length)
    for (var i = 0; i < commentDatas.length; i++) {
        console.log(commentDatas[i]);
        addCommentToDOM(commentDatas[i]);
    }
}

function addCommentToDOM(commentData) {
    console.log("displaying comment " + commentData)
    var cardHolder = createCardHolderElement();
    var card = createCardElement(commentData.sender.jobTitle!==undefined);
    var messageParagraph = createParagraphElement(commentData.comment.message);
    var senderNameHeading = createHeading3Element(commentData.sender.firstName + " " + commentData.sender.lastName + " says:");
    var emailHeading = createHeading5Element("contact: " + commentData.sender.email);
    card.appendChild(senderNameHeading);
    card.appendChild(messageParagraph);
    card.appendChild(emailHeading);
    if (commentData.comment.imgBlobKey !== undefined) {
        console.log("adding img to comment with message: " + commentData.comment.message + " and blobKey: + " + commentData.comment.imgBlobKey);
        var img  = createImgElement(commentData.comment.imgBlobKey);
        card.appendChild(img);
    }
    cardHolder.appendChild(card);
    var main = document.getElementById("main-comments");        
    main.appendChild(cardHolder);  
}

function deleteAllComments() {
    fetch('/delete-comments-data', {method: 'POST'})
    .catch(error => console.log('failed to fetch from delete-comments-data'))
    .then(response => displayComments());
}

function removeAllCommentsFromDOM() {
    document.getElementById("main-comments").innerHTML = "";
}

function getNoMaxComments() {
    return document.getElementById("max-comments").value;
}

function createCardHolderElement() {
    var cardHolder = document.createElement("div");
    cardHolder.setAttribute('class', 'card-holder-small');
    return cardHolder;
}

function createCardElement(highlight) {
    var card = document.createElement("div");
    card.setAttribute('class', 'card-small');
    if (highlight === true) {
        card.style.backgroundColor = "hsla(20, 100%, 70%, 60%)"; 
    }
    return card;
}

function createHeading3Element(string) {
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

function createParagraphElement(string) {
    var paragraph = document.createElement("p"); 
    paragraph.innerText = string;
    return paragraph;
}

function createImgElement(srcBlobKey) {
    var url = new URL("/serve-blob", document.URL);
    url.searchParams.append('blob-key', srcBlobKey)
    var img = document.createElement("img");
    img.src = url;
    return img;
}
