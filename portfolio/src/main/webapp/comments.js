function displayComments(){
    fetch('/comments-data')
    .catch(error => console.log('failed to fetch comments data from server'))
    .then(response => response.json())
    .catch(error => console.log('failed to parse comments'))
    .then(response => addAllCommentsToDOM(response))
    .catch(error => console.log('failed to print comments to DOM'));
}

function addCommentToDOM(comment){
    console.log("displaying comment " + comment)
    //create container
    var card_holder = document.createElement("div");
    card_holder.setAttribute('class', 'card-holder-small');
    var card = document.createElement("div");
    card.setAttribute('class', 'card-small');
    card_holder.appendChild(card);
    //add comment to container
    var para = document.createElement("p"); 
    para.innerText = comment;
    card.appendChild(para);  
    //add container to main 
    var main = document.getElementById("main-comments");        
    main.appendChild(card_holder);  
}

function addAllCommentsToDOM(comments){
    console.log("started adding comments to DOM")
    for(var i=0; i<comments.length; i++){
        addCommentToDOM(comments[i]);
    }
}