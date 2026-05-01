$(function() {
  let $password = $("#password")
  let $passwordRepeat = $("#password-repeat");

  $password.add($passwordRepeat).on("change", function() {
    if ($passwordRepeat.val() === $password.val()) {
      $passwordRepeat[0].setCustomValidity("");
    } else {
      $passwordRepeat[0].setCustomValidity("Passwords do not match.");
    }
  })
})