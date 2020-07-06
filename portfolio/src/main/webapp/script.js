
//this function generates a random quote to display, also making sure that the previously displayed random image is not the same as the current random image.
function randomizeQuote() {

  //ImageNumber has the Image index of the quotes. This generates a number between [1-4]
  var imageNumber = Math.floor(Math.random() * 4) + 1;

  //To check if an image exists already. The length of the list returned must have some element in it. This means that this is not the first time the button was clicked after page reload. 
  if($('#generated-quote-id').length>0)
  {
    var displayedImageIndex = $('#generated-quote-id').attr('src').substr(13,1);    //This gets the number(index) of the image that was displayed right before the button was clicked. 

    while(imageNumber+"" === displayedImageIndex){        //checks if the newly generated index is the same as the previously displayed image. as long as it is the same, it generates a different index
      imageNumber = Math.floor(Math.random() * 4) + 1;        
    }
    
    //here, the loop is broken and a different index has been stored in imageNumber than the previously generated quote.
  }

  //now, the page has to link the image and the division in quote.html
  const imgUrl = '/quotes/quote' + imageNumber + '.jpg';                         //generates image url
  const imgElement = document.createElement('img');                              //creates a stand alone image tag
  imgElement.id = "generated-quote-id";                                          //sets its ID
  imgElement.src = imgUrl;                                                       //assigned the above image url as the src of the image tag
  const imageContainer = document.getElementById('random-quote-container');      //gets the contained into which the image has to be stored
  // Remove the previous image.
  imageContainer.innerHTML = '';                                                 //clears up any image that already existed inside it
  imageContainer.appendChild(imgElement);                                        //appends the image into the div as a direct child
  $('#generated-quote-id').width(1000);                                          //specifies the width of the image
  $('#generated-quote-id').height(650);                                          // specifies the height of the image

}
