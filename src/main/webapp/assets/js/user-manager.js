$(document).ready(function () {
  
  // Set the click action of usernames
  $(".username").on("click", function (event) {
  
    let value = $(event.target).text();
    
    $.ajax({
      type: "GET",
      url: "User",
      dataType: "json",
      data: {
        userToDisplay: value
      },
      
      success: function (data) {
        
        $("#formPanel").css("display", "block");
        
        $("#username").val(data["username"]);
        $("#fName").val(data["fName"]);
        $("#lName").val(data["lName"]);
        $("#type").val(data["permissionId"]);
        $("#userToEdit").val(data["username"]);
        
      }
    });
  
  
  });
  
});