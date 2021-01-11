$(document).ready(function() {
  /*lägg till användare*/
  function postItem() {
      return function() {
          var data = {}; // Datan som Javascript-objekt
          data.name = $('#newItem input[name=name]').val();
          data.id = $('#newItem textarea[name=id]').val();

          /*Api anrop*/
          $.ajax({  
          method: "POST", //metod lägg till
          url: 'http://unicorns.idioti.se', //Adress till api
          data: JSON.stringify(data),
          headers: {"Accept": "application/json"}
          })

          .done(function(result) {

          /*Consol log för att se vad som skickas*/
          window.location.reload();
          console.log('Lade till följande data:');
          console.log(JSON.stringify(data));
          });
      }
  }
  /*läggtill i användarlista*/
  function putItem(setName) {
      return function() {
          var data = {}; // Datan som Javascript-objekt
          data.name = $('#items input[name=name]').val();
          data.id = $('#items input[name=id]').val(); 
          data.fooditem = $('#items textarea[name=fooditem]').val();

          /*Api anrop*/
          $.ajax({  
          method: "PUT", // metod ändra

          url: 'http://unicorns.idioti.se/' + data.id, //Adress till api
          data: JSON.stringify(data),
          headers: {"Accept": "application/json"}
          })
          .done(function(result) {

          /*Consol log för att se vad som skickas*/
          window.location.reload();
          console.log('Uppdaterade till följande data:');
          console.log(JSON.stringify(data));
          });
      }
  }

  function deleteItem(unicorn) {
      return function() {
        $.ajax({
          method: "DELETE",
          url: 'http://unicorns.idioti.se/' + unicorn
        })
          .done(function (data) {
              window.location.reload();
        });
      }
    }      



  function fetchAndUpdateInfo(details) {// Visa info funktion
      return function() {
          $.ajax({
              url: details,
              headers: {"Accept": "application/json"}
          })
          .done(function (data) {
              /* Mata in text med Jquery*/
              $('#itemName').text(data['name']);
              $('#itemId').text(data['id']);

              /* input lista*/
              $('#items input[name=name]').val(data['name']);
              $('#items input[name=id]').val(data['id']);
              $('#items input[name=fooditem]').val(data['fooditem']);

              /*Kopplar knappar till funktionerna*/  
              $('#postItem').click(postItem('#newItem'));
              $('#putItem').click(putItem('#items'));
              $('#deleteItem').click(deleteItem(data['id'])); //LISTA ELLER DATA?
              });
          }
      }   

  $(document).ready(function () {
      /*Hämta api lista*/
      $.ajax({
          method: "GET",
          crossDomain: true,
          url: 'http://localhost:5000/users/',        // Här finns listan.
          headers: {"Accept": "application/json"}  // Det här berättar för webservernatt vi vill ha JSON tillbaka.
      })

      .done(function (data) { 
          list = $('#item_List');// Här hämtar vi en referens till listan
          
          for (i = 0; i < data.length; i++) {//går igenom listan

          // Vi lägger till rå HTML till listan, så vi skapar en variabel för det.
              html = '';
              html = '<li id="item_' + i + '">' + data[i]['id'] + '</li>';
              list.append(html);
              console.log('HÄMTADE:');
              console.log(JSON.stringify(data));

              /*Klickbar lista funktion*/
              $('#item_' + i).click(fetchAndUpdateInfo(data[i]['treeValueCell']));
          }
      });

  $("h4").on("click", function() {
      $(this).nextAll("*").slideToggle();
  });
      /*Beräkna animering på sidan*/
      $("div.output_wrapper").hide();
      $('#postItem').click(postItem());
      $('#putItem').click(putItem());
  });
});