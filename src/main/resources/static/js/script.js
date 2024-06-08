var noRows = true

function disNoRows(){

    var table = document.getElementById("activityTable");

    var row = table.insertRow(1);
    var cell = row.insertCell(0);

    cell.innerHTML = "No rectangles found.";
}


if(noRows){
    disNoRows();
}