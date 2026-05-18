function initializeControls(endpoint) {
  $(".submit-btn").on("click", function (e) {
    let $target = $(e.target);
    let $row = $target.parent().parent();

    let resourceId = +$row.attr("resource-id");
    let data = {
      id: resourceId
    };

    $row.find("input").add("select", $row).each(function () {
      data[$(this).attr("name")] = $(this).val();
    })

    $.ajax(`${endpoint}/${resourceId}`, {
      method: "PUT",
      contentType: "application/json",
      data: JSON.stringify(data)
    });
  });

  $(".delete-btn").on("click", function (e) {
    let $target = $(e.target);
    let $row = $target.parent().parent();

    let resourceId = $row.attr("resource-id");
    $.ajax(`${endpoint}/${resourceId}`, {
      method: "DELETE"
    });

    $row.remove();
  });

  $("table input").on("input", function () {
    let $row = $(this).parent().parent();

    let valid = true;
    $row.find("input").each(function () {
      valid &= this.checkValidity();
    })

    $row.find(".submit-btn").prop("disabled", !valid);
  });
}