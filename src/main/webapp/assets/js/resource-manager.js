function initializeControls(endpoint) {
  $(".count-control.add").on("click", function(e) {
    let $target = $(e.target);
    let $row = $target.parent().parent();

    let resourceId = $row.attr("resource-id");
    $.ajax(`${endpoint}/${resourceId}`, {
      method: "PUT",
      data: {
        action: "add"
      }
    });

    let $resourceCount = $row.find(".resource-count");
    let count = +$resourceCount.text() + 1;
    $resourceCount.text(count);
  });

  $(".count-control.remove").on("click", function(e) {
    let $target = $(e.target);
    let $row = $target.parent().parent();

    let resourceId = $row.attr("resource-id");
    $.ajax(`${endpoint}/${resourceId}`, {
      method: "PUT",
      data: {
        action: "remove"
      }
    });

    let $resourceCount = $row.find(".resource-count");
    let count = $resourceCount.text() - 1;

    if (count === 0) {
      $row.remove();
    } else {
      $resourceCount.text(count);
    }
  });

  $(".delete-btn").on("click", function(e) {
    let $target = $(e.target);
    let $row = $target.parent().parent();

    let resourceId = $row.attr("resource-id");
    $.ajax(`${endpoint}/${resourceId}`, {
      method: "DELETE"
    });

    $row.remove();
  });
}
