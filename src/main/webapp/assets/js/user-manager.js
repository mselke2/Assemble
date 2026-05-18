$(document).ready(function () {
  let requesterId = +$("#logged-in-user").attr("user-id");
  let selectedUser = -1;
  let $selectedUserRow;

  // Set the click action of usernames
  $(".username").on("click", function (event) {
    let value = $(event.target).text();
    $selectedUserRow = $(this).parent();

    $.ajax({
      type: "GET",
      url: "User",
      dataType: "json",
      data: {
        userToDisplay: value
      },

      success: function (data) {
        $("#formPanel").show();

        selectedUser = data["id"];
        $("#username").val(data["username"]);
        $("#fName").val(data["fName"]);
        $("#lName").val(data["lName"]);
        $("#type").val(data["permissionId"]);
        $("#userToEdit").val(data["username"]);
        $("#delete-user-btn").add("#permission-field").toggle(selectedUser !== requesterId);
      }
    });
  });

  $("#delete-user-btn").on("click", function () {
    $.ajax(`User/${selectedUser}`, {
      method: "DELETE"
    }).done(() => {
      $("#formPanel").hide();
      $selectedUserRow.remove();
    });
  });
});