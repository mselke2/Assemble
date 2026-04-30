let $jobForm;
let $jobIdDisplay;
let $productChoiceInput;
let $startTimeInput;
let $endTimeInput;
let $numMembersInput;
let $lineNumInput;

let originalStartTime;
let originalEndTime;
let originalLineNum;

let lastValidStartTime;
let lastValidEndTime;

let $activeJobEntry;

$(function() {
  $jobForm = $("#job-form");
  $jobIdDisplay = $("#job-id");
  $productChoiceInput = $("#product-choice");
  $startTimeInput = $("#start-time");
  $endTimeInput = $("#end-time");
  $numMembersInput = $("#num-members");
  $lineNumInput = $("#line-num");

  // onChange fires when the hour or minute fields are completed
  // individually, so if the user types in the hour and the browser
  // automatically proceeds to the minute field, onChange fires.
  // onBlur only fires when the input becomes unfocused.
  $startTimeInput.on("blur", function() {
    // start time cant be equal to or after end time
    if ($startTimeInput.val() >= $endTimeInput.val()) {
      $startTimeInput.val(lastValidStartTime);
    }

    lastValidStartTime = $startTimeInput.val();
    // set styles for the job entry element based on the updated time
    let [hours, minutes] = lastValidStartTime.split(':');
    $activeJobEntry.css("--start-time", +hours + minutes / 60);
  });

  $endTimeInput.on("blur", function() {
    // end time cant be equal to or before start time
    if ($startTimeInput.val() >= $endTimeInput.val()) {
      $endTimeInput.val(lastValidEndTime);
    }

    lastValidEndTime = $endTimeInput.val();
    // set styles for the job entry element based on the updated time
    let [hours, minutes] = lastValidEndTime.split(':');
    $activeJobEntry.css("--end-time", +hours + minutes / 60);
  });

  $numMembersInput.on("change", function() {
    if ($numMembersInput.val() < 1) {
      $numMembersInput.val(1);
    }
  });

  $lineNumInput.on("change", function() {
    if ($lineNumInput.val() < 1) {
      $lineNumInput.val(1);
    }

    $activeJobEntry.css("--lane", $lineNumInput.val())
  });

  $("#submit-btn").on("click", function() {
    // perform PUT request with info from form
    $.ajax(`Job/${$activeJobEntry.attr("job-id")}`, {
      method: "PUT",
      contentType: "application/json",
      data: JSON.stringify({
        productId: $productChoiceInput.val(),
        startTime: $startTimeInput.val(),
        projectedEndTime: $endTimeInput.val(),
        numMembers: $numMembersInput.val(),
        lineNum: $lineNumInput.val()
      })
    });
  });

  $("#cancel-btn").on("click", function() {
    // reset info to original states and use pre-existing event handlers to
    // update styles
    $startTimeInput.val(originalStartTime).trigger("blur");
    $endTimeInput.val(originalEndTime).trigger("blur");
    $lineNumInput.val(originalLineNum).trigger("change");

    // hide the form since the edit was canceled
    $jobForm.hide();
  })

  $(".job-entry").on("click", function(e) {
    // read the job id from the clicked element
    $activeJobEntry = $(e.currentTarget);
    let jobId = $activeJobEntry.attr("job-id");

    // perform a GET request for the job
    $.getJSON(`Job/${jobId}`, data => {
      // update GUI with data from GET
      $jobIdDisplay.text(jobId);

      $productChoiceInput.val(data["productId"]);

      lastValidStartTime = data["startTime"];
      originalStartTime = lastValidStartTime;
      $startTimeInput.val(lastValidStartTime);

      lastValidEndTime = data["projectedEndTime"];
      originalEndTime = lastValidEndTime;
      $endTimeInput.val(lastValidEndTime);

      $numMembersInput.val(data["numMembers"]);

      originalLineNum = data["lineNum"];
      $lineNumInput.val(originalLineNum);

      // show the job form since we are now editing the clicked job
      $jobForm.show();
    });
  });
});