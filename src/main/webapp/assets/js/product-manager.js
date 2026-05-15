let $inventoryEditor;
let editedProductId = -1;

$(function() {
  initializeControls("Product");

  $inventoryEditor = $("#inventory-editor");
  $inventoryEditor.find(".editor-submit").on("click", function() {
    let $row = $(`[resource-id="${editedProductId}"]`);

    let inventoryIds = [];
    let inventoryCounts = [];

    $inventoryEditor.find("input").each(function() {
      let $this = $(this);
      if ($this.val() > 0) {
        inventoryIds.push(+$this.attr("inventory-type-id"));
        inventoryCounts.push(+$this.val());
      }
    });

    $row.find(".inventory-ids").val(`[${inventoryIds}]`);
    $row.find(".inventory-counts").val(`[${inventoryCounts}]`);

    $inventoryEditor.hide();
  });

  $(".inventory-btn").on("click", function() {
    let $row = $(this).parent().parent();
    let $inventoryIds = $row.find(".inventory-ids");
    let $inventoryCounts = $row.find(".inventory-counts");
    editedProductId = +$row.attr("resource-id");

    let inventoryIds = JSON.parse($inventoryIds.val());
    let inventoryCounts = JSON.parse($inventoryCounts.val());

    $inventoryEditor.find("input").val(0);

    for (let i = 0; i < inventoryIds.length; i++) {
      $inventoryEditor.find(`input[inventory-type-id="${inventoryIds[i]}"]`).val(inventoryCounts[i]);
    }

    $inventoryEditor.show();
  });
})