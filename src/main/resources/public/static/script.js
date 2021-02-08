$(document).ready(function() {

 $('#postUser').click(postUser());
 $('#putFoodItemToUser').click(putFoodItemToUser());
 $('#deleteUser').click(deleteUser());

  function postUser() {
    return function() {
      var data = {};
      data.name = $('#newUser input[name=name]').val();
      data.id = $('#newUser input[name=id]').val();
      console.log(data);

      $.ajax({
         method: "POST",
         url: 'http://localhost:5000/api/v1/users/',
         data: JSON.stringify(data),
         headers: {"Accept": "application/json"},
      }).done(function(result) {
         console.log(result);
         console.log('La till användare');
      }).fail(function() {
         console.log('Falure');
      });
    }
  }

  function putFoodItemToUser() {
    return function() {
      var data = {};
      var data1 = {"name":"Carin", "id":1, "foodItems":["Hårt bröd fullkorn råg fibrer 15,5% typ Husman", "Lättmjölk fett 0,5% berikad", "Sill inlagd m. gräddfilssås"]};
      console.log(JSON.stringify(data1));
      //data.name = $('#newFooditem input[name=name]').val();
      data.id = $('#newFooditem input[name=id]').val();
      /*
      var item = $('#newFooditem input[name=fooditem]').val();
      JSONArray arr = new JSONArray();
      data.foodItems = arr.add(item);
      console.log(data);
       */

      $.ajax({
        method: "PUT",
        url: 'http://localhost:5000/api/v1/users/' + data.id,
        data: JSON.stringify(data1),
        headers: {"Accept": "application/json"},
      }).done(function(result) {
       console.log(result);
       console.log('La till mat');
      })
      $.ajax({
            statusCode: {
                404: function() {
                alert( "page not found" );
            }
      }
      })
      $.ajax({
            statusCode: {
              500: function() {
              alert( "Server Error" );
              }
            }
      });
    }
  }

  function deleteUser() {
    return function() {
    var data = {};
    data.id = $('#deleteU input[name=id]').val();
    console.log(data.id);
      $.ajax({
        method: "DELETE",
        url: 'http://localhost:5000/api/v1/users/' + data.id,
        })
        .done(function(result) {
         console.log('Raderade användare');
         // window.location.reload();
      });
    }
  }

    function fetchAndUpdateInfo(details) {
        return function() {
            $.ajax({
                url: details,
                headers: {"Accept": "application/json"}
            })
            .done(function (data) {
                $('#userName').text(data['name']);
                $('#userId').text(data['id']);

                $('#newUser input[name=id]').val(data['id']);
                $('#newUser input[name=name]').val(data['name']);
                /*
                $('#existingUser input[name=id]').val(data['id']);
                $('#existingUser input[name=name]').val(data['name']);
                  */
                $('#postUser').click(postUser('#newUser'));
                $('#putItem').click(putItem('#newItem'));
                $('#deleteUser').click(deleteUser(data['id']));
                });
            }
        }
  });

    $(document).ready(function () {
        $.ajax({
            method: "GET",
            crossDomain: true,
            url: 'http://localhost:5000/api/v1/users/',
            headers: {"Accept": "application/json"}
        }).done(function (data) {
            list = $('#users');
            for (i = 0; i < data.length; i++) {
                html = '<li id="user_' + i + '">' + data[i]['name'] + '</li>';
                list.append(html);
                console.log('HÄMTADE ANVÄNDARE:');
                console.log(JSON.stringify(data));
                $('#user_' + i).click(fetchAndUpdateInfo(data[i]['details']));
         //     $('#item_' + i).click(fetchAndUpdateInfo(data[i]['name']));
            }
             $('#postUser').click(postUser());
             $('#putFoodItemToUser').click(putFoodItemToUser());
          //   $('#addUser').click(hideFormsAndShowOne('#newUser'));
        }).done(function (data) {
                  list = $('#item_List');

                  for (i = 0; i < data.length; i++) {
                      html = '<li id="item_' + i + '">' + data[i]['name'] + '</li>';
                      list.append(html);
                      console.log('HÄMTADE LIVSMEDEL');
                      console.log(JSON.stringify(data))
                      $('#item_' + i).click(fetchAndUpdateInfo(data[i]['name']));
                  }
                });
                   $('#postUser').click(postUser());
                   $('#putFoodItemToUser').click(putFoodItemToUser());
              //   $('#addUser').click(hideFormsAndShowOne('#newUser'));
  });