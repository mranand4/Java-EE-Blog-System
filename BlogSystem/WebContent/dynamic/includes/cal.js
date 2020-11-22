function createCalendar(ele, year, month, date) {
var elem = document.getElementById(ele);

let mon = month - 1; // months in JS are 0..11, not 1..12
let d = new Date(year, mon);

let table = '<p style=\"text-align: center; margin: 0; padding: 0; margin-bottom: 8px;\">' + date + "/" + month + "/" + year + '</p>' + '<table><tr><th>M</th><th>T</th><th>W</th><th>T</th><th>F</th><th>S</th><th style=\"color: red;\"">S</th></tr><tr>';

// spaces for the first row
// from Monday till the first day of the month
// * * * 1234
for (let i = 0; i < getDay(d); i++) {
table += '<td></td>';
}

// <td> with actual dates
while (d.getMonth() == mon) {
//background color of current date is lime
if(d.getDate() == date)
	table += '<td style=\"background-color: rgba(191,255,0,0.4);\">' + d.getDate() + '</td>';
else
	table += '<td>' + d.getDate() + '</td>';

if (getDay(d) % 7 == 6) { // sunday, last day of week - newline
table += '</tr><tr>';
}

d.setDate(d.getDate() + 1);
}

// add spaces after last days of month for the last row
// 29 30 31 * * * *
if (getDay(d) != 0) {
for (let i = getDay(d); i < 7; i++) {
table += '<td></td>';
}
}

// close the table
table += '</tr></table>';

elem.innerHTML = table;
console.log("Done");
}

function getDay(date) { // get day number from 0 (monday) to 6 (sunday)
let day = date.getDay();
if (day == 0) day = 7; // make Sunday (0) the last day
return day - 1;
}
