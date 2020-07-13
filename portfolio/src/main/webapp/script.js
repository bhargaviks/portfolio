// This function generates a random quote to display, also making sure that the previously displayed random image is not the same as the current random image.
function randomizeQuote() {

  const NUM_QUOTES = 4;    // This constant variable keeps track of the number of quotes (images) I have. 
  var imageNumber = Math.floor(Math.random() * NUM_QUOTES) + 1;       // ImageNumber has the Image index of the quotes. This generates a number between [1- NUM_QUOTES]

  // To check if an image exists already. The length of the list returned must have some element in it. This means that this is not the first time the button was clicked after page reload. 
  if($('#generated-quote-id').length>0)
  {
    var displayedImageNumber = $('#generated-quote-id').attr('src').substr(13,1);    // This gets the number(index) of the image that was displayed right before the button was clicked. 

    if(imageNumber+"" === displayedImageNumber)
    { // If the displayed picture and the currently generated image have the same index
      
      imageNumber = Math.floor(Math.random() * (NUM_QUOTES-1)) + 1;         // Generate a number between [1- (NUM_QUOTES - 1)]
      if(imageNumber >= displayedImageNumber) {
        imageNumber = imageNumber + 1;          // If the newly generated number is greater than or equal to the previously generated number, then add 1 to it. Otherwise, keep the value.
      }

    }
    
  }
  
  // Now, the page has to link the image and the division in quote.html
  const imgUrl = '/quotes/quote' + imageNumber + '.jpg';                         // Generates image url
  const imgElement = document.createElement('img');                              // Creates a stand alone image tag
  imgElement.id = "generated-quote-id";                                          // Sets its ID
  imgElement.src = imgUrl;                                                       // Assigned the above image url as the src of the image tag
  const imageContainer = document.getElementById('random-quote-container');      // Gets the contained into which the image has to be stored
  // Remove the previous image.
  imageContainer.innerHTML = '';                                                 // Clears up any image that already existed inside it
  imageContainer.appendChild(imgElement);                                        // Appends the image into the div as a direct child
  $('#generated-quote-id').width(1000);                                          // Specifies the width of the image
  $('#generated-quote-id').height(650);                                          // Specifies the height of the image

}

/*
  This is a function I am writing to get a greeting in my portfolio page.
  By clicking the button on the page, this function is called and a request is
  sent to the server. This function also handles the request and delivers
  the required output. Fetch data and trigger servlet, take the response and convert it to 
  text, place the greeting in the greeting-container division
*/
function getGreeting() {

  fetch('/data').then(response => response.text()).then((greeting) => {
    document.getElementById('greeting-container').innerText = greeting;
  });

}

// Loads data (messages in this case) from datastore according to limit. The first time limit is null, so all comments are shown. 
function getMessages() {  

  var limit = new URLSearchParams(window.location.search).get('limit');

  fetch('/data?limit='+limit).then(response => response.json()).then( (comments) => {
    const commentListElement = document.getElementById('comment-list'); 
    comments.forEach( (comment) => {
      commentListElement.appendChild(
        createCommentElement(comment));
    })
  });

}

// The two functions that get called onload of the body of comments.html
function onPageLoad()
{
  getMessages();            // Gets the messages loaded to the page  
  populateLimitTextbox();   // Retain the limit if the user has entered previously, and display the required number of comments according to that.
}

/* 
  The purpose of this function is to keep the same value of the limit 
  entered by the user, so that when the user posts a new message, the
  same number (limit) is retained. 
*/
function populateLimitTextbox(){
  
  if(document.getElementById('reload-limit').value == "") {
    var limit = new URLSearchParams(window.location.search).get('limit');

    if(Number(limit)>0){
      document.getElementById('reload-limit').value = limit;
    }
  }
  
}

// reloads appropriately with limit
function reloadMessages() {  
  var limit = document.getElementById('reload-limit').value;
  
  fetch('/data?limit='+limit).then(response => response.json()).then( (comments) => {
    const commentListElement = document.getElementById('comment-list');
    commentListElement.innerHTML = '';
    comments.forEach( (comment) => {
      commentListElement.appendChild(
        createCommentElement(comment));
    })
  });

}

// Creates an <li> element containing text.
function createCommentElement(comment) {

  const commentElement = document.createElement('li');
  commentElement.className = 'comment';

  const textElement = document.createElement('span');
  textElement.innerText = comment.text;

  const deleteButtonElement = document.createElement('button');
  deleteButtonElement.innerText = 'Delete';
  deleteButtonElement.addEventListener('click', () => {
    deleteComment(comment);
    commentElement.remove();      // Removes the specified element fron DOM.
  });

  commentElement.appendChild(textElement);
  commentElement.appendChild(deleteButtonElement);
  return commentElement;

}

/** Tells the server to delete the comment. */
function deleteComment(comment) {
  const params = new URLSearchParams();
  params.append('id', comment.id);
  fetch('/delete-comment', {method: 'POST', body: params});
}


/** Tells the server to delete all comments. */
function deleteAll() {
  const params = new URLSearchParams();
  fetch('/delete-all', {method: 'POST', body: params}).then( (response) => {
    const commentListElement = document.getElementById('comment-list');
    commentListElement.innerHTML = '';
    reloadMessages();
  });
}

/* The belowe functions are used for Map related stuff --> 

/** Creates a map and adds it to the page. */
function createMap() {
  const map = new google.maps.Map(
      document.getElementById('map'),
      {center: {lat: 13.067439, lng: 80.237617}, zoom: 16});
}
