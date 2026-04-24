const monthNames = ["January", "February", "March", "April", "May", "June",
  "July", "August", "September", "October", "November", "December"
];

let calendarDate = new Date();
calendarDate.setDate(1);

let $monthName;
let $dateBoxes;

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

  $dateBoxes.each(function() {
    let $dateBox = $(this);

    // set the date number text to the date number of the stored date object
    $dateBox.find(".day-number").text(date.getDate())
    // if the date object's month does not match the month we stored earlier,
    // fade it out
      .toggleClass("off-month", date.getMonth() !== monthIdx);

    // query for jobs scheduled for that day
    $.getJSON("GetTimeline", date.getTime().toString(), data => {
      // for each job scheduled that day...
      for (let job of data) {
        // add a div with the product name to the date box
        $dateBox.append($(`<div></div>`, {
          text: job["productName"],
          class: "job-entry"
        }));
      }
    });

    // advance the date object forward by one day.
    date.setDate(date.getDate() + 1);
  });
}

$(function() {
  $monthName = $("#month-name");
  $dateBoxes = $(".calendar-day");

  $("#prev-btn").on("click", () => {
    calendarDate.setMonth(calendarDate.getMonth() - 1);
    populateCalendar();
  });

  $("#next-btn").on("click", () => {
    calendarDate.setMonth(calendarDate.getMonth() + 1);
    populateCalendar();
  });

  populateCalendar();
});