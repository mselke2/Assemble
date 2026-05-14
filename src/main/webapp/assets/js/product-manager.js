$(function () {
  $(".submit-btn").on("click", function (e) {
    let $target = $(e.target);
    let $row = $target.parent().parent();

    let resourceId = +$row.attr("resource-id");
    $.ajax(`Product/${resourceId}`, {
      method: "PUT",
      contentType: "application/json",
      data: JSON.stringify({
        id: resourceId,
        description: $row.find(".description").val(),
        duration: +$row.find(".duration").val(),
        personnelCount: +$row.find('.personnel-count').val()
      })
    });
  });

  $(".delete-btn").on("click", function (e) {
    let $target = $(e.target);
    let $row = $target.parent().parent();

    let resourceId = $row.attr("resource-id");
    $.ajax(`Product/${resourceId}`, {
      method: "DELETE"
    });

    $row.remove();
  });

})