$(document).ready(function() {
 $('#postUser').click(postUser());
 $('#putFoodItemToUser').click(putFoodItemToUser());
 $('#deleteUser').click(deleteUser());
 updateUserList();

  function postUser() {
    return function() {
      var data = {};
      data.name = $('#newUser input[name=name]').val();
      data.id = $('#newUser input[name=id]').val();
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
      data.name = nameU;
      data.id = idU;
      var item = $('#newFooditem input[name=fooditem]').val();
      data.foodItems = [item];
      $.ajax({
        method: "PUT",
        url: "http://localhost:5000/api/v1/users/" + idU,
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
      $.ajax({
        method: "DELETE",
        url: 'http://localhost:5000/api/v1/users/' + data.id,
        })
        .done(function(result) {
         console.log('Raderat användare:' + data.id);
         updateUserList();
      });
    }
  }

  function updateUserList() {
        $.ajax({
            method: "GET",
            url: 'http://localhost:5000/api/v1/users/',
            headers: {"Accept": "application/json"},
        }).done(function (data) {
            list = $('#users');
            list.empty();
            for (i = 0; i < data.length; i++) {
                html = '<li id="user_' + i + '">' + data[i]['name'] + '</li>';
                list.append(html);
                console.log('Hämtat användare:');
               $('#user_' + i).click(fetchUsersFoodItems(data[i]['name'], data[i]['id']));
            }
        });
   }

   function fetchUsersFoodItems(userName, userId) {
        return function() {
        var amountB12;
            nameU = '"' + userName + '"';
            idU = userId
            $.ajax({
                method: "GET",
                url: 'http://localhost:5000/api/v1/users/' + userId,
                headers: {"Accept": "application/json"}
            }).done(function (data) {
                list = $('#item');
                list.empty();
                nameUser = $('#nameUser');
                nameUser.empty();
                html = '<h3 id="nameUser_">' + userName + ', id: ' + userId + '</h3>';
                nameUser.append(html);
                    for (i = 0; i < data.length; i++) {
                         html = '<li id="item_' + i + '">' + data[i]['foodItem'] + '</li>';
                         list.append(html);
                         console.log('Hämtat livsmedel:');
                         var b12 = (data[i]['b12']).replace(",", ".");
                         amountB12 += parseFloat(b12);
                    }
                        sumB12 = $('#B12');
                        sumB12.empty();
                        html = '<h3 id="B12_">' + "Tryck på knappen för att beräkna" + '</h3>';
                        sumB12.append(html);
                       $('#b12amount').click(b12amountFunction(amountB12));
            });
         amountB12 = 0;
        }
   }

   function b12amountFunction(amountB12) {
      return function() {
      text =$('#Text');
      text.empty();
      str = '<p id="Text_">' + "Beräknat värde i microgram: " + '</p>';
      text.append(str);
      sumB12 = $('#B12');
      sumB12.empty();
      html = '<h3 id="B12_">' + amountB12 + '</h3>';
      sumB12.append(html);
      }
   }
});