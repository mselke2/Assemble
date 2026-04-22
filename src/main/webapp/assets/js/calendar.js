const monthNames = ["January", "February", "March", "April", "May", "June",
  "July", "August", "September", "October", "November", "December"
];

window.onload = () => {
  // create a Date object initialized to the current date
  let date = new Date();
  // save the month so we can identify dates that are outside of this month
  let monthIdx = date.getMonth();

  // set the month header text
  let monthName = document.querySelector("#month-name");
  monthName.innerText = monthNames[monthIdx];

  // set the date to the first of the current month
  date.setDate(1);

  // step backwards day-by-day until we get to a Sunday (start of calendar week)
  while (date.getDay() !== 0) { // Sunday
    date.setDate(date.getDate() - 1);
  }

  // for each calendar date number...
  let dayNumbers = document.querySelectorAll(".day-number");
  for (let dayNumber of dayNumbers) {
    // set the date number text to the date number of the stored date object
    dayNumber.innerText = date.getDate();
    // if the date object's month does not match the month we stored earlier,
    // fade it out
    if (date.getMonth() !== monthIdx) {
      dayNumber.classList.add("off-month");
    }
    // advance the date object forward by one day.
    date.setDate(date.getDate() + 1);
  }
}
