// This function generates a random quote to display, also making sure that the previously displayed random image is not the same as the current random image.
function randomizeQuote() {

  const NUM_QUOTES = 4;    // This constant variable keeps track of the number of quotes (images) I have. 

  // ImageNumber has the Image index of the quotes. This generates a number between [1- NUM_QUOTES]
  var imageNumber = Math.floor(Math.random() * NUM_QUOTES) + 1;

  // To check if an image exists already. The length of the list returned must have some element in it. This means that this is not the first time the button was clicked after page reload. 
  if($('#generated-quote-id').length>0)
  {
    var displayedImageNumber = $('#generated-quote-id').attr('src').substr(13,1);    // This gets the number(index) of the image that was displayed right before the button was clicked. 

    
    if(imageNumber+"" === displayedImageNumber)
    { // If the displayed picture and the currently generated image have the same index
      
      imageNumber = Math.floor(Math.random() * (NUM_QUOTES-1)) + 1;         // Generate a number between [1- (NUM_QUOTES - 1)]

      if(imageNumber >= displayedImageNumber) {
        // If the newly generated number is greater than or equal to the previously generated number, then add 1 to it. Otherwise, keep the value.
        imageNumber = imageNumber + 1;
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

// Requests data (messages in this case) from the server.
function getMessages() {

  fetch('/data').then(response => response.json()).then( (messagesObj) => {
    const messageListElement = document.getElementById('comment-container');
    messageListElement.innerHTML = '';
    messagesObj.forEach( (comment) => {
      messageListElement.appendChild(
        createListElement(comment));
    })
  });

}

// Creates an <li> element containing text.
function createListElement(text) {
  
  const liElement = document.createElement('li');
  liElement.innerText = text;
  return liElement;

}
