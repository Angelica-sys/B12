
$(document).ready(function() {
  //Lägger till ny användare
  function postUser() {
    return function() {
      var data = {};
      data.name = $('#newUser input[name=name]').val();
      data.id = $('#newUser input[name=id]').val();

      $.ajax({
        method: "POST",
        url: 'http://localhost:5000/api/v1/users/',
        data: JSON.stringify(data),
        headers: {"Accept": "application/json"}
      })
      .done(function(result) {
      console.log('La till användare');
      });
    }
  }

  function putFoodItemToUser(id) {
    return function() {
      var data = {};
      //data.name = $('#newItem input[name=name]').val();
      //data.id = $('#newItem input[name=id]').val();
      data.foodItem = $('#existingUser input[foodItem=foodItem]').val(); //ska det vara name=name ?


      $.ajax({
        method: "PUT",
        url: 'http://localhost:5000/api/v1/users/' + data.id,
        data: JSON.stringify(data),
        headers: {"Accept": "application/json"}
      })
      .done(function(result) {
      console.log('La till mat');
      });
    }
  }

  function deleteUser(user) {
    return function() {
      $.ajax({
        method: "DELETE",
        url: 'http://localhost:5000/api/v1/users/' + user
       //  })
       //           .done(function (data) {
         //             window.location.reload();
      });
    }
  }

    /*Visa info funktion*/
  function fetchAndUpdateInfo(id) {
      return function() {
          $.ajax({
              method: "GET",
                        crossDomain: true,
                        url: 'http://localhost:5000/api/v1/users/' + data.id,
              headers: {"Accept": "application/json"}
          })
          .done(function (data) {
              $('#userName').text(data['name']);
              $('#userId').text(data['id']);

              /*
               //Karls lösning
              $('#items input[name=name]').val(data['name']);
              $('#items input[name=id]').val(data['id']);
              $('#items input[name=fooditem]').val(data['fooditem']);
               */

              $('#existingUser input[name=id]').val(data['id']);
              $('#existingUser input[name=name]').val(data['name']);


              $('#postUser').click(postUser('#newUser'));
              $('#putItem').click(putItem('#newItem'));
              $('#deleteUser').click(deleteUser(data['id']));
              });
          }
      }

  $(document).ready(function () {
      $.ajax({
          method: "GET",
          crossDomain: true,
          url: 'http://localhost:5000/api/v1/users/',
          headers: {"Accept": "application/json"}
      })

      .done(function (data) {
          list = $('#users'); //eller user??

          for (i = 0; i < data.length; i++) {
              html = '';    // denna känns överflödig
              html = '<li id="item_' + i + '">' + data[i]['name'] + '</li>';
              list.append(html);
              console.log('HÄMTADE DATA:');
              console.log(JSON.stringify(data));
              $('#user_' + i).click(fetchAndUpdateInfo(data[i]['details']));
         //     $('#item_' + i).click(fetchAndUpdateInfo(data[i]['name']));
          }
        });
          $("h4").on("click", function() {
              $(this).nextAll("*").slideToggle();
          });
              /*Beräkna animering på sidan*/
              $("div.output_wrapper").hide();
              $('#postUser').click(postUser());
              $('#putFoodItemToUser').click(putFoodItemToUser());
          });
        });
        /*
           $('#postUser').click(postUser());
           $('#putFoodItemToUser').click(putFoodItemToUser());
           $('#addUser').click(hideFormsAndShowOne('#newUser'));
      });  */

