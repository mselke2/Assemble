const monthNames = ["January", "February", "March", "April", "May", "June",
  "July", "August", "September", "October", "November", "December"
];

let calendarDate = new Date();
calendarDate.setDate(1);

let $monthName;
let $dateNumbers;

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

  $dateNumbers.each(function() {
    // set the date number text to the date number of the stored date object
    $(this).text(date.getDate())
    // if the date object's month does not match the month we stored earlier,
    // fade it out
      .toggleClass("off-month", date.getMonth() !== monthIdx);

    // advance the date object forward by one day.
    date.setDate(date.getDate() + 1);
  });
}

$(function() {
  $monthName = $("#month-name");
  $dateNumbers = $(".day-number");

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