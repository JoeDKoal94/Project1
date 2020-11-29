
window.onload = function () {
    //AJAX - Asynchronous JavaScript and XML
    //Initialize xhr object
    let xhr = new XMLHttpRequest();
    const url = "http://localhost:9090/menu/employee";
    //sets up ready state handler
    xhr.onreadystatechange = function () {
        console.log(xhr.readyState);
        switch (xhr.readyState) {

            case 0:
                console.log("nothing, initalized not sent");
                break;
            case 1:
                console.log("connection established")
                break;
            case 2:
                console.log("request sent");
                break;
            case 3:
                console.log("waiting response");
                break;
            case 4:
                console.log("FINISHED!!!!!!!!!!!");
                //logic to add guest to table
                if (xhr.status === 200) {
                    console.log(xhr.responseText);
                    let myForms = JSON.parse(xhr.responseText);
                    console.log(myForms);
                    console.log(myForms[0]);
                    myForms.forEach(element => {
                        addRow(element);
                    });
                }
                break;

        }
    }

    //opens up the request
    xhr.open("GET", url, true);
    //sends request
    xhr.send();

}

let addRow = function (myWait) {
	let delform = document.createElement("form");
	let delBut = document.createElement("button");

	delBut.type = "submit";
	delform.appendChild(delBut);

    let table = document.getElementById("wait-table");
    let tableRow = document.createElement("tr");
    let formNumCol = document.createElement("td");
    let eventDateCol = document.createElement("td");
    let timePostedCol = document.createElement("td");
	let appStatusCol = document.createElement("td");
	let delCol = document.createElement("td");
	
	delCol.appendChild(delform);
	
    tableRow.appendChild(formNumCol);
    tableRow.appendChild(eventDateCol);
    tableRow.appendChild(timePostedCol);
    tableRow.appendChild(appStatusCol);
    tableRow.appendChild(delCol);
    table.appendChild(tableRow);


	delBut.innerHTML = "Delete";
    formNumCol.innerHTML = myWait.formNumber;
    eventDateCol.innerHTML = myWait.eventDate.year + "-" + myWait.eventDate.monthValue + "-" + myWait.eventDate.dayOfMonth;
    timePostedCol.innerHTML = myWait.timePosted.hour + ":" + myWait.timePosted.minute;
    appStatusCol.innerHTML = myWait.approvalStatus;
  

    formNumCol.className = "table-style";
    eventDateCol.className = "table-style";
    timePostedCol.className = "table-style";
    appStatusCol.className = "table-style";
    tableRow.className = "table-style";
    


	delBut.setAttribute("id", myWait.formNumber);
	delform.setAttribute("action", "http://localhost:9090/menu/employee");
	delform.setAttribute("method", "POST");
	delBut.onclick = function(){
    	document.cookie = "numForm = " + myWait.formNumber;
    	window.location.href= 'viewForms.html';
    	}
    formNumCol.setAttribute("id", myWait.formNumber);
    formNumCol.onclick = function(){
    	document.cookie = "numForm = " + myWait.formNumber;
    	window.location.href= 'viewAForm.html';
    }

 
    
}