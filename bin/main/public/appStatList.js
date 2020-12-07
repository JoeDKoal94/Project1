
window.onload = function () {
    //AJAX - Asynchronous JavaScript and XML
    //Initialize xhr object
    let xhr = new XMLHttpRequest();
    const url = "http://localhost:9090/menu/employee/view/application-status";
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
               
                if (xhr.status === 200) {
                    console.log(xhr.responseText);
                    let app = JSON.parse(xhr.responseText);
                    console.log(app);
                    addInfo(app);
                }
                break;

        }
    }

    //opens up the request
    xhr.open("GET", url, true);
    //sends request
    xhr.send();

}

let addInfo = function (myApp) {
	let conform = document.createElement("form");
    let conBut = document.createElement("button");
    let yesLabel = document.createElement("label");
    let noLabel = document.createElement("label");
    let conyes = document.createElement("input");
    let conNo = document.createElement("input");
    let resBut = document.createElement("button");
    yesLabel.innerHTML = "Yes";
	noLabel.innerHTML = "No";
    conyes.type = "radio";
    conyes.name = "update";
    conyes.value = "true";
    conNo.type = "radio";
    conNo.name = "update";
    conNo.value = "false";
    yesLabel.appendChild(conyes);
    noLabel.appendChild(conNo);
    conBut.type = "submit";
    conform.appendChild(yesLabel);
    conform.appendChild(noLabel);
    conform.appendChild(conBut);
    
    let table = document.getElementById("wait-table");
    let tableRow = document.createElement("tr");
    let appIDCol = document.createElement("td");
    let formNumCol = document.createElement("td");
    let dsAppCol = document.createElement("td");
	let dhAppCol = document.createElement("td");
	let bencoAppCol = document.createElement("td");
	let appStatCol = document.createElement("td");
	let isUrgentCol = document.createElement("td");
	let willExCol = document.createElement("td");
	let amoExCol = document.createElement("td");
	let reaExCol = document.createElement("td");
	let conExCol = document.createElement("td");
	let resultCol = document.createElement("td");

	
	conExCol.appendChild(conform);
	resultCol.appendChild(resBut);
	
    tableRow.appendChild(appIDCol);
    tableRow.appendChild(formNumCol);
    tableRow.appendChild(dsAppCol);
    tableRow.appendChild(dhAppCol);
    tableRow.appendChild(bencoAppCol);
    tableRow.appendChild(appStatCol);
    tableRow.appendChild(isUrgentCol);
    tableRow.appendChild(willExCol);
    tableRow.appendChild(amoExCol);
    tableRow.appendChild(reaExCol);
    tableRow.appendChild(conExCol);
    tableRow.appendChild(resultCol);
    table.appendChild(tableRow);

	conBut.innerHTML = "Consent";
	
    appIDCol.innerHTML = myApp.approvalId;
    formNumCol.innerHTML = myApp.formNumber;
    dsAppCol.innerHTML = myApp.dsApproval;
    dhAppCol.innerHTML = myApp.dhApproval;
    bencoAppCol.innerHTML = myApp.bencoApproval;
    appStatCol.innerHTML = myApp.appStatus;
    isUrgentCol.innerHTML = myApp.isUrgent;
    willExCol.innerHTML = myApp.amExceed;
    amoExCol.innerHTML = myApp.amAwarded;
    reaExCol.innerHTML = myApp.reaExceed;
    if(myApp.conExceed == true){
    conExCol.innerHTML = "Consented";
    conBut.disabled = true;
	}
	else if(myApp.amExceed == false){
	conBut.disabled = true;
	}
	
	   if(myApp.appStatus != "Approved"){
    		resultCol.hidden = true;
    }
    appIDCol.className = "table-style";
    formNumCol.className = "table-style";
    dsAppCol.className = "table-style";
    dhAppCol.className = "table-style";
    bencoAppCol.className = "table-style";
    appStatCol.className = "table-style";
    isUrgentCol.className = "table-style";
    willExCol.className = "table-style";
    amoExCol.className = "table-style";
    reaExCol.className = "table-style";
    conExCol.className = "table-style";
 


 conform.setAttribute("action", "http://localhost:9090/menu/employee/view/application-status");
 conform.setAttribute("method", "POST");
 conBut.onclick = function(){
    	document.cookie = "numForm = " + myApp.formNumber;
    	window.location.href= 'viewForms.html';
    	}
    
}
	resBut.id = myApp.approvalId;
    resBut.onclick = function(){
    	document.cookie = "numApp = " + myWait.approvalId;
    	window.location.href='viewFormAfter.html';
    }