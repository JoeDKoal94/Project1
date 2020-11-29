/**
 * 
 */window.onload = function () {
    //AJAX - Asynchronous JavaScript and XML
    //Initialize xhr object
    let xhr = new XMLHttpRequest();
    const url = "menu/bencoV";
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
                    let form = JSON.parse(xhr.responseText);
                    console.log(form);
					addInfo(form);
                }
                break;

        }
    }

    //opens up the request
    xhr.open("GET", url, true);
    //sends request
    xhr.send();

}

let addInfo = function (myForm) {
	let employeeId = document.getElementById("employeeId");
	let eventDate = document.getElementById("eventDate");
	let eventTime = document.getElementById("eventTime");
	let eventLocation = document.getElementById("eventLocation");
	let description = document.getElementById("description");
	let gradingFormat = document.getElementById("gradingFormat");
	let typeEvent = document.getElementById("typeOfEvent");
	let attach = document.getElementById("attachments");
	let cost = document.getElementById("cost");
	let stats = document.getElementById("approvalStatus");
	let urgency = document.getElementById("isUrgent");
	let amount = document.getElementById("projectedAmount");
	
	
    employeeId.innerHTML = "Employee ID: " + myForm.employeeId;
    eventDate.innerHTML = "Date of Event: " + myForm.eventDate.year + "-" + myForm.eventDate.monthValue + "-" + myForm.eventDate.dayOfMonth;
    eventTime.innerHTML = "Time of Event: " + myForm.eventTime.hour + ":" + myForm.eventTime.minute;
	eventLocation.innerHTML = "Location of Event: " + myForm.eventLocation;
	description.innerHTML = myForm.description;
	gradingFormat.innerHTML = "Grading Format: " + myForm.gradingFormat;
	typeEvent.innerHTML = "Type of Event: " + myForm.typeOfEvent;
	attach.src = "data:image/jpg;base64," + myForm.attachments;
	cost.innerHTML = "Cost: " + myForm.cost;
	stats.innerHTML = "Approval Status:  " + myForm.approvalStatus;
	urgency.innerHTML = myForm.isUrgent;
	amount.innerHTML = "Projected Amount: "+ myForm.projectedAmount;
	
	employeeId.onclick = function(){
		window.location.href='viewEmployeeAp.html';
	}
}

function addressChange() {
	var reasonDecline = document.getElementById("reason");
  if(this.value == 'false'){
  	reasonDecline.disabled = false;
  }
  if(this.value == 'true'){
  reasonDecline.disabled = true;
  }
}

 document.getElementById('apApprove').addEventListener('change', addressChange);
 document.getElementById('apDecline').addEventListener('change', addressChange);

function selectAdd() {
	var needInfo = document.getElementById("target");
  if(this.checked == true){
  	needInfo.disabled = false;
  }
  if(this.checked == false){
  needInfo.disabled = true;
  }
}

document.getElementById("infoCheck").addEventListener('change', selectAdd);

function amountAdd() {
	var needMons = document.getElementById("exceedAmount");
	var whyMons = document.getElementById("reasonMon");
  if(this.checked == true){
  	needMons.disabled = false;
  	whyMons.disabled = false;
  }
  if(this.checked == false){
  needMons.disabled = true;
  whyMons.disabled = true;
  }
}

document.getElementById("infoAmount").addEventListener('change', amountAdd);


function checkFields() {
	if(document.getElementById("reason").disabled == true){
		document.getElementById("reason").value = null;
	}
	if(document.getElementById("target").disabled == true){
		document.getElementById("target").value = null;
	}
	if(document.getElementById("exceedAmount").disabled == true){
		document.getElementById("exceedAmount").value = null;
	}
	if(document.getElementById("reasonMon").disabled == true){
		document.getElementById("reasonMon").value = null;
	}
}

document.getElementById("sendingIt").addEventListener('click', checkFields());