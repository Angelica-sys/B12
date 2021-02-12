$(document).ready(function() {
 $('#postUser').click(postUser());
 $('#putFoodItemToUser').click(putFoodItemToUser());
 $('#deleteUser').click(deleteUser());
 $('#b12amount').click(b12amountFunction());
 updateUserList();

  function postUser() {
    return function() {
      var data = {};
      data.name = $('#newUser input[name=name]').val();
      name = data.name;
      data.id = $('#newUser input[name=id]').val();
      id = data.id;
      console.log(data);

      $.ajax({
         method: "POST",
         url: "http://localhost:5000/api/v1/users/",
         data: JSON.stringify(data),
         headers: {"Accept": "application/json"},
      }).done(function(result) {
         console.log(result);
         updateUserList();
      }).fail(function() {
         console.log('Failure');
      });
    }
  }

  function putFoodItemToUser() {
    return function() {
      var data = {};
      data.name = name;
      data.id = id;
      var item = $('#newFooditem input[name=fooditem]').val();
      data.foodItems = [item];
      console.log(data);

      $.ajax({
        method: "PUT",
        url: "http://localhost:5000/api/v1/users/" + id,
        data: JSON.stringify(data),
        headers: {"Accept": "application/json"},
      }).done(function(result) {
       console.log(result);
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
         updateUserList();
      });
    }
  }

    function updateUserList() {
        $.ajax({
            method: "GET",
           //crossDomain: true,
            url: 'http://localhost:5000/api/v1/users/',
            headers: {"Accept": "application/json"},
        }).done(function (data) {
            list = $('#users');
            list.empty();
            for (i = 0; i < data.length; i++) {
                html = '<li id="user_' + i + '">' + data[i]['name'] + '</li>';
                list.append(html);
                console.log('HÄMTADE ANVÄNDARE:');
                console.log(JSON.stringify(data));
                console.log(data[i]['name']);
                console.log(data[i]['id']);

                $('#user_' + i).click(fetchUsersFoodItems(data[i]['name'], data[i]['id']));
          //    $('#user_' + i).click(fetchAndUpdateInfo(data[i]['details']));
          //    $('#addUser').click(hideFormsAndShowOne('#newUser'));
            }
        });
    }

   function fetchUsersFoodItems(userName, userId) {
    amountB12 = 0;
        return function() {
            $.ajax({
                method: "GET",
                url: 'http://localhost:5000/api/v1/users/' + userId,
                headers: {"Accept": "application/json"}
            }).done(function (data) {
                list = $('#item');
                list.empty();
                nameUser = $('#nameUser');
                nameUser.empty();
                html = '<h2 id="nameUser_">' + userName + '</h2>';
                console.log(html);
                nameUser.append(html);

                    for (i = 0; i < data.length; i++) {
                         html = '<li id="item_' + i + '">' + data[i]['foodItem'] + '</li>';
                         list.append(html);
                         console.log(html);
                         console.log('HÄMTADE LIVSMEDEL');
                         var b12 = (data[i]['b12']).replace(",", ".");
                         amountB12 += parseFloat(b12);
                  //       $('#item_' + i).click(fetchAndUpdateInfo(data[i]['name']));
                    }
            });

            /*
            .done(function (data) {
                $('#userName').text(data['name']);
                $('#userId').text(data['id']);

                $('#newUser input[name=id]').val(data['id']);
                $('#newUser input[name=name]').val(data['name']);

                $('#existingUser input[name=id]').val(data['id']);
                $('#existingUser input[name=name]').val(data['name']);

                $('#postUser').click(postUser('#newUser'));
                $('#putItem').click(putItem('#newItem'));
                $('#deleteUser').click(deleteUser(data['id']));
                });
            */
        }
   }

    function b12amountFunction() {
      return function() {
      sumB12 = $('#B12');
      sumB12.empty();
      html = '<h3 id="B12_">' + amountB12 + '</h3>';
      console.log(html);
      sumB12.append(html);
      }
    }
                // Rader som jag rensat bort men som kanske behövs
                // window.location.reload();
                // $('#addUser').click(hideFormsAndShowOne('#newUser'));
});