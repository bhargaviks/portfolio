
// This function generates a random quote to display, also making sure that the previously displayed random image is not the same as the current random image.
function randomizeQuote() {

  const range= 4;    // This constant variable keeps track of the number of quotes I have. 

  // ImageNumber has the Image index of the quotes. This generates a number between [1-4]
  var imageNumber = Math.floor(Math.random() * range) + 1;

  // To check if an image exists already. The length of the list returned must have some element in it. This means that this is not the first time the button was clicked after page reload. 
  if($('#generated-quote-id').length>0)
  {
    var displayedImageNumber = $('#generated-quote-id').attr('src').substr(13,1);    // This gets the number(index) of the image that was displayed right before the button was clicked. 

    // If the displayed picture and the currently generated image have the same index, 
    if(imageNumber+"" === displayedImageNumber)
    {
      // And if the Number is the last value in the set of images, simply make sure to print the first picture of the bunch.
      if(imageNumber === range) {
        imageNumber = 1;
      }

      // If it's not the highest number, then simply add 1 to the randomized index.
      else {
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
