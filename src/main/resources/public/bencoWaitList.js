
window.onload = function () {
    //AJAX - Asynchronous JavaScript and XML
    //Initialize xhr object
    let xhr = new XMLHttpRequest();
    const url = "http://localhost:9090/menu/benco";
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
                    let waitList = JSON.parse(xhr.responseText);
                    console.log(waitList);
                    console.log(waitList[0]);
                    waitList.forEach(element => {
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
    let table = document.getElementById("wait-table");
    let tableRow = document.createElement("tr");
    let priorCol = document.createElement("td");
    let appIDCol = document.createElement("td");
    let appStatCol = document.createElement("td");
	let addInfoCol = document.createElement("td");
	let urgentCol = document.createElement("td");
	let targetCol = document.createElement("td");
	let willExceedCol = document.createElement("td");
	let amountAwardeds = document.createElement("td");
	let reasonAwarded = document.createElement("td");
	
    tableRow.appendChild(priorCol);
    tableRow.appendChild(appIDCol);
    tableRow.appendChild(appStatCol);
    tableRow.appendChild(addInfoCol);
    tableRow.appendChild(targetCol);
    tableRow.appendChild(urgentCol);
    tableRow.appendChild(willExceedCol);
    tableRow.appendChild(amountAwardeds);
    tableRow.appendChild(reasonAwarded);
    table.appendChild(tableRow);

     priorCol.innerHTML = myWait.priorityNumber;
    appIDCol.innerHTML = myWait.approvalId;
    appStatCol.innerHTML = myWait.dsApproval;
    addInfoCol.innerHTML = myWait.infoReq;
    urgentCol.innerHTML = myWait.isUrgent;
    willExceedCol.innerHTML = myWait.reasonExceed;
    amountAwardeds.innerHTML = myWait.amountAwarded;
    reasonAwarded.innerHTML = myWait.reasonExceed;
    

    priorCol.className = "table-style";
    appIDCol.className = "table-style";
    appStatCol.className = "table-style";
    addInfoCol.className = "table-style";
    urgentCol.className = "table-style";
    tableRow.className = "table-style";
    

    priorCol.setAttribute("id", myWait.approvalId);
    priorCol.onclick = function(){
    	document.cookie = "numApp = " + myWait.approvalId;
    	window.location.href='viewFormApBenco.html';
    }

 
    
}