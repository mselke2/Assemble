$(function () {
  let $password = $("#password")
  let $passwordRepeat = $("#password-repeat");
  let $message = $("#message");

  $password.add($passwordRepeat).on("change", function () {
    if ($passwordRepeat.val() === $password.val()) {
      $passwordRepeat[0].setCustomValidity("");
    } else {
      $passwordRepeat[0].setCustomValidity("Passwords do not match.");
    }
  })

  let $submitBtn = $("#submit");

  $("#create-user-form").on("submit", function (e) {
    e.preventDefault();
    $submitBtn.prop("disabled", true);

    if (this.reportValidity()) {
      let formData = $(this).serialize();
      $.post("User", formData).done(r => {
        $message.text(r);
        $message.css("color", "green");
      }).fail(r => {
        $message.text(r.responseText);
        $message.css("color", "red");
      }).always(() => {
        $submitBtn.prop("disabled", false);
      })
    }
  })
})