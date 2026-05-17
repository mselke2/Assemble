let editedProductId = -1;

function initializeInventoryEditor() {
  let $inventoryEditor = $("#inventory-editor");

  let $inputs = $inventoryEditor.find("input").on("input", function() {
    let valid = true;
    $inputs.each(function() {
      valid &= this.checkValidity();
    })

    $inventoryEditor.find(".editor-submit").prop("disabled", !valid);
  });

  $inventoryEditor.find(".editor-submit").on("click", function () {
    let $row = $(`[resource-id="${editedProductId}"]`);

    let inventoryIds = [];
    let inventoryCounts = [];

    $inventoryEditor.find("input").each(function () {
      let $this = $(this);
      if ($this.val() > 0) {
        inventoryIds.push(+$this.attr("inventory-type-id"));
        inventoryCounts.push(+$this.val());
      }
    });

    $row.find(".inventory-ids").val(`[${inventoryIds}]`);
    $row.find(".inventory-counts").val(`[${inventoryCounts}]`);

    if ($row.find(".inventory-btn").text().trim() === "Edit") {
      $row.find("td:has(.inventory-btn)").addClass("edited");
    }

    $inventoryEditor.hide();
  });

  $inventoryEditor.find(".editor-cancel").on("click", () => $inventoryEditor.hide());

  $(".inventory-btn").on("click", function () {
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
}

function initializeEquipmentEditor() {
  let $equipmentEditor = $("#equipment-editor");

  let $inputs = $equipmentEditor.find("input").on("input", function() {
    let valid = true;
    $inputs.each(function() {
      valid &= this.checkValidity();
    })

    $equipmentEditor.find(".editor-submit").prop("disabled", !valid);
  });

  $equipmentEditor.find(".editor-submit").on("click", function () {
    let $row = $(`[resource-id="${editedProductId}"]`);

    let equipmentIds = [];
    let equipmentCounts = [];

    $equipmentEditor.find("input").each(function () {
      let $this = $(this);
      if ($this.val() > 0) {
        equipmentIds.push(+$this.attr("equipment-type-id"));
        equipmentCounts.push(+$this.val());
      }
    });

    $row.find(".equipment-ids").val(`[${equipmentIds}]`);
    $row.find(".equipment-counts").val(`[${equipmentCounts}]`);

    if ($row.find(".equipment-btn").text().trim() === "Edit") {
      $row.find("td:has(.equipment-btn)").addClass("edited");
    }

    $equipmentEditor.hide();
  });

  $equipmentEditor.find(".editor-cancel").on("click", () => $equipmentEditor.hide());

  $(".equipment-btn").on("click", function () {
    let $row = $(this).parent().parent();
    let $equipmentIds = $row.find(".equipment-ids");
    let $equipmentCounts = $row.find(".equipment-counts");
    editedProductId = +$row.attr("resource-id");

    let equipmentIds = JSON.parse($equipmentIds.val());
    let equipmentCounts = JSON.parse($equipmentCounts.val());

    $equipmentEditor.find("input").val(0);

    for (let i = 0; i < equipmentIds.length; i++) {
      $equipmentEditor.find(`input[equipment-type-id="${equipmentIds[i]}"]`).val(equipmentCounts[i]);
    }

    $equipmentEditor.show();
  });
}

function onSubmitClicked() {
  $(this).parent().parent().find(".edited").removeClass("edited");
}

$(function () {
  initializeControls("Product");
  $(".submit-btn").on("click", onSubmitClicked);
  initializeInventoryEditor();
  initializeEquipmentEditor();
})