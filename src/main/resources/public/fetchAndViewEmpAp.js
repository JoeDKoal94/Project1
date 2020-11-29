window.onload = function () {
    //AJAX - Asynchronous JavaScript and XML
    //Initialize xhr object
    let xhr = new XMLHttpRequest();
    const url = "menu/dsDhV/viewEmpApp";
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
                    let emp = JSON.parse(xhr.responseText);
                    console.log(emp);
					addInfo(emp);
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
	let authority = document.getElementById("authority");
	let firstname = document.getElementById("firstname");
	let lastname = document.getElementById("lastname");
	let phoneNumber = document.getElementById("phoneNumber");
	let address = document.getElementById("address");
	let city = document.getElementById("city");
	let state = document.getElementById("state");
	let postalCode = document.getElementById("postalCode");
	let emailAdd = document.getElementById("emailAdd");
	let availableAmount = document.getElementById("availableAmount");
	
	
    employeeId.innerHTML = "Employee ID: " + myForm.employeeId;
    authority.innerHTML = "Authority: " + myForm.authority;
    firstname.innerHTML = "First Name: " + myForm.firstName;
	lastname.innerHTML = "Last Name: " + myForm.lastName;
	phoneNumber.innerHTML = "Phone Number: " + myForm.phoneNumber;
	address.innerHTML = "Address: " + myForm.address;
	city.innerHTML = "City: " + myForm.city;
	state.innerHTML = "State: " + myForm.state;
	postalCode.innerHTML = "postalCode:  " + myForm.postalCode;
	emailAdd.innerHTML = myForm.emailAdd;
	availableAmount.innerHTML = "Available Amount: "+ myForm.availableAmount;

}



   