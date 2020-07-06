function randomizeQuote() {

  var imageIndex = Math.floor(Math.random() * 4) + 1;

  //To check if an image exists already
  if($('#generated-quote-id').length>0)
  {
    //Image exists
    var currentImageIndex = $('#generated-quote-id').attr('src').substr(13,1)
    while(imageIndex+"" === currentImageIndex)
    {
      imageIndex = Math.floor(Math.random() * 4) + 1;
    }
    
  }

  const imgUrl = '/quotes/quote' + imageIndex + '.jpg';
  const imgElement = document.createElement('img');
  imgElement.id = "generated-quote-id";
  imgElement.src = imgUrl;
  const imageContainer = document.getElementById('random-quote-container');
  // Remove the previous image.
  imageContainer.innerHTML = '';
  imageContainer.appendChild(imgElement);
  $('#generated-quote-id').width(1000);
  $('#generated-quote-id').height(700);

}
