let $jobForm;
let $dateInput;
let $newJobLabel;
let $jobIdLabel;
let $jobIdDisplay;
let $productChoiceInput;
let $startTimeInput;
let $endTimeInput;
let $numMembersInput;
let $lineNumInput;
let $submitBtn;
let $deleteBtn;

let lastValidStartTime;
let lastValidEndTime;

let $timelineLanes;
let $activeJobEntry = null;

let hourTickSpace;
let laneWidth;

const subTickRatio = 1.0 / 6.0;

let timelineLanesHovered = true;

let $newJobGhost;
let newJobStartTime;
let newJobEndTime;
let newJobLane;
let addingJob = false;
let editingJob = false;
let validNewJobHover = false;

let $timelineWrapper;
let timelineMouseX = 0;
let timelineMouseY = 0;

let jobEntries = new Map();

function updateGhostPosition() {
  if (!timelineLanesHovered || addingJob) {
    return;
  }

  let scrolledX = timelineMouseX + $timelineWrapper.scrollLeft();
  let scrolledY = timelineMouseY + $timelineWrapper.scrollTop();

  newJobLane = Math.floor(scrolledX / laneWidth) + 1;
  let mouseHour = scrolledY / hourTickSpace;
  let newJobStartTimeFloat = Math.floor(mouseHour / subTickRatio) * subTickRatio;
  let newJobEndTimeFloat = Math.min(newJobStartTimeFloat + 1, 24);

  newJobStartTime = convertFloatToTime(newJobStartTimeFloat);
  newJobEndTime = convertFloatToTime(newJobEndTimeFloat);

  let inHoveredLane = jobEntries.values().filter(entry => entry.lineNumber === newJobLane).toArray();

  for (let job of inHoveredLane) {
    if (newJobStartTime >= job.startTime && newJobStartTime <= job.endTime) {
      newJobStartTimeFloat = convertTimeToFloat(job.endTime) + 1.0 / 60;
      newJobStartTime = convertFloatToTime(newJobStartTimeFloat);
    }

    if (newJobEndTime >= job.startTime && newJobEndTime <= job.endTime) {
      newJobEndTimeFloat = convertTimeToFloat(job.startTime) - 1.0 / 60;
      newJobEndTime = convertFloatToTime(newJobEndTimeFloat);
    }
  }

  validNewJobHover = newJobStartTime < newJobEndTime;

  updateGhostVisibility();
  $newJobGhost.css({
    "--start-time": convertTimeToFloat(newJobStartTime),
    "--end-time": convertTimeToFloat(newJobEndTime),
    "--lane": newJobLane
  });
}

function validateChanges() {
  if ($activeJobEntry == null) {
    return;
  }

  let isValid = true

  $(".invalid").removeClass("invalid");

  let startTime = $startTimeInput.val();
  let endTime = $endTimeInput.val();
  let line = +$lineNumInput.val();

  let inHoveredLane = jobEntries.entries().filter(entry => entry[1].lineNumber === line).toArray();

  for (let job of inHoveredLane) {
    let [jobId, jobData] = job;

    if (jobId === +$activeJobEntry.attr("job-id")
        || $activeJobEntry.attr("id") === "create-job-ghost" && !addingJob) {
      continue;
    }

    if (startTime >= jobData.startTime) {
      if (startTime <= jobData.endTime) {
        $(`[job-id="${jobId}"]`).addClass("invalid");
        $activeJobEntry.addClass("invalid");
        isValid = false;
      }
    } else if (endTime >= jobData.endTime) {
      $(`[job-id="${jobId}"]`).addClass("invalid");
      $activeJobEntry.addClass("invalid");
      isValid = false;
    }

    if (endTime >= jobData.startTime && endTime <= jobData.endTime) {
      $(`[job-id="${jobId}"]`).addClass("invalid");
      $activeJobEntry.addClass("invalid");
      isValid = false;
    }
  }

  $submitBtn.attr("disabled", !isValid);
}

function updateLocalJobData(id, startTime, endTime, lineNumber) {
  $(`.job-entry[job-id="${id}"]`).css("background-color", calculateJobColorString(id));

  jobEntries.set(id, {
    startTime: startTime,
    endTime: endTime,
    lineNumber: lineNumber
  });
}

function addJobEntry(id, startTime, endTime, lineNumber) {
  $activeJobEntry = $("<div></div>", {
    class: "job-entry",
    click: onJobEntryClicked,
    "job-id": id
  }).append($("<p></p>", {
    text: id
  })).appendTo($timelineLanes);

  updateLocalJobData(id, startTime, endTime, lineNumber);

  updateActiveJobStyles();
}

function showForm() {
  $jobIdLabel.toggle(!addingJob);
  $jobIdDisplay.toggle(!addingJob);
  $deleteBtn.toggle(!addingJob);

  $newJobLabel.toggle(addingJob);

  editingJob = true;

  $jobForm.show();
}

function updateActiveJobStyles() {
  // set styles for the job entry element based on the updated time
  $activeJobEntry.css("--start-time", convertTimeToFloat($startTimeInput.val()));

  // set styles for the job entry element based on the updated time
  $activeJobEntry.css("--end-time", convertTimeToFloat($endTimeInput.val()));

  $activeJobEntry.css("--lane", $lineNumInput.val())

  $activeJobEntry.css("z-index", Math.floor(convertTimeToFloat($startTimeInput.val()) * 60));
}

function convertFloatToTime(hoursFloat) {
  const hours = Math.floor(hoursFloat);
  const minutes = Math.round((hoursFloat - hours) * 60);

  // Format as HH:mm with leading zeros
  const displayHours = hours.toString().padStart(2, '0');
  const displayMinutes = minutes.toString().padStart(2, '0');

  return `${displayHours}:${displayMinutes}`;
}

function convertTimeToFloat(timeString) {
  let [hours, minutes] = timeString.split(':');
  return +hours + minutes / 60;
}

function updateGhostVisibility() {
  $newJobGhost.toggle(timelineLanesHovered && validNewJobHover && !editingJob || addingJob);
}

function cancelEdit() {
  if ($activeJobEntry !== null && !addingJob) {
    // reset info to original states and update styles

    let jobInfo = jobEntries.get(+$activeJobEntry.attr("job-id"));

    $startTimeInput.val(jobInfo.startTime);
    $endTimeInput.val(jobInfo.endTime);
    $lineNumInput.val(jobInfo.lineNumber);

    updateActiveJobStyles();
  }

  addingJob = false;
  editingJob = false
  validateChanges();
  $activeJobEntry = null;
  updateGhostVisibility();

  // hide the form since the edit was canceled
  $jobForm.hide();
}

function onSubmitClicked() {
  let data = {
    productId: +$productChoiceInput.val(),
    startTime: `${$dateInput.val()} ${$startTimeInput.val()}:00`,
    projectedEndTime: `${$dateInput.val()} ${$endTimeInput.val()}:00`,
    numMembers: +$numMembersInput.val(),
    lineNum: +$lineNumInput.val()
  };
  if (addingJob) {// perform PUT request with info from form
    $.post("Job", data).done(responseData => {
      addJobEntry(responseData["jobId"],
        $startTimeInput.val(),
        $endTimeInput.val(),
        data.lineNum);

      cancelEdit();
    });
  } else {
    // perform PUT request with info from form
    let id = +$jobIdDisplay.text();
    $.ajax(`Job/${id}`, {
      method: "PUT",
      contentType: "application/json",
      data: JSON.stringify(data)
    }).done(() => {
      updateLocalJobData(id,
        $startTimeInput.val(),
        $endTimeInput.val(),
        data.lineNum
      );

      cancelEdit();
    });
  }
}

function onJobEntryClicked(e) {
  let $clickedJob = $(e.currentTarget);
  // read the job id from the clicked element
  let jobId = +$clickedJob.attr("job-id");

  if (jobId === +$activeJobEntry?.attr("job-id")) {
    return;
  }

  cancelEdit();
  updateGhostVisibility();

  $activeJobEntry = $clickedJob;

  // perform a GET request for the job
  $.getJSON(`Job/${jobId}`, data => {
    // update GUI with data from GET
    $jobIdDisplay.text(jobId);

    $productChoiceInput.val(data["productId"]);

    lastValidStartTime = data["startTime"];
    $startTimeInput.val(lastValidStartTime);

    lastValidEndTime = data["projectedEndTime"];
    $endTimeInput.val(lastValidEndTime);

    let lineNum = data["lineNum"];
    $lineNumInput.val(lineNum);

    updateLocalJobData(jobId,
      lastValidStartTime,
      lastValidEndTime,
      lineNum);

    $numMembersInput.val(data["numMembers"]);

    updateActiveJobStyles();

    // show the job form since we are now editing the clicked job
    showForm();
  });
}

$(function() {
  $jobForm = $("#job-form");
  $dateInput = $("#date");
  $newJobLabel = $("#new-job-label");
  $jobIdLabel = $("#job-id-label");
  $jobIdDisplay = $("#job-id");
  $productChoiceInput = $("#product-choice");

  let $timeline = $(".timeline");
  hourTickSpace = parseInt($timeline.css("--hour-tick-space"));
  laneWidth = parseInt($timeline.css("--lane-width"));

  $newJobGhost = $("#create-job-ghost").hide();

  let dateText = `${$dateInput.val().substring(5, 7)}/${$dateInput.val().substring(8)}/${$dateInput.val().substring(0, 4)}`;
  $("#date-display").text(dateText)

  // onChange fires when the hour or minute fields are completed
  // individually, so if the user types in the hour and the browser
  // automatically proceeds to the minute field, onChange fires.
  // onBlur only fires when the input becomes unfocused.
  $startTimeInput = $("#start-time").on("blur", function() {
    // start time cant be equal to or after end time
    if ($startTimeInput.val() >= $endTimeInput.val()) {
      $startTimeInput.val(lastValidStartTime);
    }

    lastValidStartTime = $startTimeInput.val();

    updateActiveJobStyles();
    validateChanges();
  });

  $endTimeInput = $("#end-time").on("blur", function() {
    // end time cant be equal to or before start time
    if ($startTimeInput.val() >= $endTimeInput.val()) {
      $endTimeInput.val(lastValidEndTime);
    }

    lastValidEndTime = $endTimeInput.val();

    updateActiveJobStyles();
    validateChanges();
  });

  $numMembersInput = $("#num-members").on("change", function() {
    if ($numMembersInput.val() < 1) {
      $numMembersInput.val(1);
    }
  });

  $lineNumInput = $("#line-num").on("change", function() {
    if ($lineNumInput.val() < 1) {
      $lineNumInput.val(1);
    }

    updateActiveJobStyles();
    validateChanges();
  });

  $submitBtn = $("#submit-btn").on("click", onSubmitClicked);

  $("#cancel-btn").on("click", cancelEdit);

  $(".job-entry").on("click", onJobEntryClicked)
    .not("#create-job-ghost").each(function() {
      // on page load, add job existing job entries into the map
      let $this = $(this);
      let id = +$this.attr("job-id");

      updateLocalJobData(id,
        convertFloatToTime(+$this.css("--start-time")),
        convertFloatToTime(+$this.css("--end-time")),
        +$this.css("--lane"));
  })

  $timelineLanes = $("#timeline-lanes").on("mouseover", function(e) {
    if (e.target === this) {
      timelineLanesHovered = true;
      updateGhostVisibility();
    }
  }).on("mousemove", function(e) {
    timelineMouseX = e.pageX - $timelineLanes.position().left - $timelineWrapper.scrollLeft();
    timelineMouseY = e.pageY - $timelineLanes.position().top - $timelineWrapper.scrollTop();

    updateGhostPosition();
  }).on("mouseout", function(e) {
    if (e.target === this) {
      timelineLanesHovered = false;
      updateGhostVisibility()
    }
  }).on("click", function(e) {
    if (e.target === this && validNewJobHover) {
      cancelEdit();

      $activeJobEntry = $newJobGhost;
      addingJob = true;

      $productChoiceInput[0].selectedIndex = 0;

      lastValidStartTime = newJobStartTime;
      $startTimeInput.val(lastValidStartTime);

      lastValidEndTime = newJobEndTime;
      $endTimeInput.val(lastValidEndTime);

      $numMembersInput.val(1);

      $lineNumInput.val(newJobLane);

      // show the job form since we are now editing the clicked job
      showForm();
    }
  });

  $timelineWrapper = $(".timeline-wrapper").on("scroll", updateGhostPosition);

  $deleteBtn = $("#delete-btn").on("click", function() {
    let jobId = +$activeJobEntry.attr("job-id");
    $.ajax(`Job/${jobId}`, {
      method: "DELETE"
    });

    let $tmp = $activeJobEntry;

    cancelEdit();

    jobEntries.delete(jobId);
    $tmp.remove();
  })
});
