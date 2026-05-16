const monthNames = ["January", "February", "March", "April", "May", "June",
  "July", "August", "September", "October", "November", "December"
];

let calendarDate = new Date();
calendarDate.setDate(1);

let $monthName;
let $dateBoxes;

let $prevMonthBtn;
let $nextMonthBtn;

let dateBoxXHRs = [];

function populateCalendar() {
  // create new date so we don't have to modify the original
  let date = new Date(calendarDate);
  // set the date to the first of the current month
  date.setDate(1);

  // save the month so we can identify dates that are outside of this month
  let monthIdx = date.getMonth();

  // set the month header text
  $monthName.text(monthNames[monthIdx]);

  // step backwards day-by-day until we get to a Sunday (start of calendar week)
  while (date.getDay() !== 0) { // Sunday
    date.setDate(date.getDate() - 1);
  }

  // clear existing job entries from calendar
  $dateBoxes.find(".job-entry").remove();

  // abort any requests that haven't been fulfilled yet.
  // can happen if you are clicking the prev/next buttons really fast.
  for (let request of dateBoxXHRs) {
    request.abort();
  }

  dateBoxXHRs = [];

  $dateBoxes.each(function () {
    let $dateBox = $(this);

    let dateString = date.getFullYear().toString() + (date.getMonth() + 1).toString().padStart(2, "0") + date.getDate().toString().padStart(2, "0");
    $dateBox.attr("href", "Timeline?d=" + dateString);

    // set the date number text to the date number of the stored date object
    $dateBox.find(".day-number").text(date.getDate())
      // if the date object's month does not match the month we stored earlier,
      // fade it out
      .toggleClass("off-month", date.getMonth() !== monthIdx);

    // query for jobs scheduled for that day, and add the requests to
    // the XHRs array
    dateBoxXHRs.push($.getJSON("Job", {
      d: dateString
    }, data => {
      // for each job scheduled that day...
      for (let job of data) {
        // add a div with the product name to the date box
        $dateBox.append($(`<div></div>`, {
          text: job["productName"],
          class: "job-entry",
          style: `background-color: ${calculateJobColorString(job.jobId)};`
        }));
      }
    }));

    // advance the date object forward by one day.
    date.setDate(date.getDate() + 1);
  });
}

$(function () {
  $monthName = $("#month-name");
  $dateBoxes = $(".calendar-day");

  $prevMonthBtn = $("#prev-btn").on("click", () => {
    calendarDate.setMonth(calendarDate.getMonth() - 1);

    $nextMonthBtn.prop("disabled", false);
    if (calendarDate.getFullYear() <= 2000) {
      $prevMonthBtn.prop("disabled", true);
    }

    populateCalendar();
  });

  $nextMonthBtn = $("#next-btn").on("click", () => {
    calendarDate.setMonth(calendarDate.getMonth() + 1);


    $prevMonthBtn.prop("disabled", false);
    if (calendarDate.getFullYear() >= 2500) {
      $nextMonthBtn.prop("disabled", true);
    }

    populateCalendar();
  });

  populateCalendar();
});