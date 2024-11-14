function checkout_entry(){
	$("#game_container").html(checkout_general_template);
	$("#confirm_general_data").click(checkout_post_general_data);
}

function checkout_post_general_data(){
	let fullName = $("#fullName").val();
	let address = $("#address").val();
	let country = $("#country").find(":selected").val();
	let state = $("#state").val();
	let zip_code = $("#zip_code").val();
	let phone = $("#phone").val();
	
	const countries = [
	    "USA", "Canada", "Mexico", "Brazil", "Argentina", "Colombia", "Chile", "Peru",
	    "UK", "Germany", "France", "Italy", "Spain", "Netherlands", "Sweden", "Switzerland", "Norway",
	    "Australia", "New Zealand",
	    "Japan", "Korea"
	];
	
	// Default value for country or is not in the list
	if(country == undefined || country == "0" || !countries.includes(country)){
		alert("Select a country");
		return;
	}
	
	if(!validateFullName(fullName) || !validateAddress(address) || !validateState(state) || !validateZipCode(zip_code) || !validatePhone(phone)){
		alert("Incorrect Personal Information Data");
		return;
	}
	
	$.post("../process-general-user-data", {
		fullName,
		address,
		country,
		state,
		zip_code,
		phone
	}).done((res) => {
		if(res == "ok"){
			$("#game_container").html(checkout_card_template);
			$("#confirm_card_data").click(checkout_post_card_data);
		}else{
			alert(res);
		}
	});
}

function checkout_post_card_data(){
	let cardOwner = $("#cardOwner").val();
	let cardNumber = $("#cardNumber").val();
	let cardType = $("#cardType").find(":selected").val();
	let ccv = $("#ccv").val();
	let cardExpireDate = $("#cardExpireDate").val();
	
	// Card Type Validation
	const cards = ["visa", "mastercard", "americanexpress"];
	
	if(cardType == undefined || cardType == "0" || !cards.includes(cardType)){
		alert("Select a Card Type");
		return;
	}
	
	// Date Validation
	let selectedDate = new Date(cardExpireDate);
	let selectedYear = selectedDate.getFullYear();
	let selectedMonth = selectedDate.getMonth();
	
	let currentDate = new Date();
	let currentYear = currentDate.getFullYear();
	let currentMonth = currentDate.getMonth();
	
	if(selectedYear < currentYear || (selectedYear === currentYear && selectedMonth < currentMonth)){
		alert("Expired Card");
		return;
	}
	
	if(!validateFullName(cardOwner) || !validateCardNumber(cardNumber) || !validateCCV(ccv)){
		alert("Incorrect Card Data");
		return;
	}
	
	$.post("../process-card-data", {
		cardOwner,
		cardNumber,
		cardType,
		ccv,
		cardExpireDate
	}).done((res) => {
		if(res == "ok"){
			$("#game_container").html(checkout_shipping_template);
			$("#confirm-shipping-data").click(checkout_post_shipping_data);
		}else{
			alert(res);
		}
	});
}

function checkout_post_shipping_data(){
	let shippingDetails = $("#shippingDetails").val();
	let shippingType = $("#shippingType").find(":selected").val();
	
	const shipping_options = ["standard", "express"];
	
	if(shippingType == undefined || shippingType == "0" || !shipping_options.includes(shippingType)){
		alert("Select Shipping Type");
		return;
	}
	
	$.post("../process-shipping-details", {
		shippingDetails,
		shippingType
	}).done((res) => {
		// Date shenanigans
		res.cardExpireDate = formatDate(res.cardExpireDate);
		// Only return the last 4 numbers for the summary
		res.cardNumber = res.cardNumber.slice(-4);
		// Total price shenanigans
		let totalPrice = 0;
		res.videogames.forEach((v) => {
			totalPrice += v.price * v.quantity;
		});
		
		let html = Mustache.render(checkout_summary_template, res);
		console.log("Res");
		console.log(res);
		$("#game_container").html(html);
		$("#total-price").text(totalPrice);
		$("#checkout-btn").click(checkout);
	});
}

function checkout(){
	$.post("../checkout").done((res) => {
		if(res == "order-confirmed"){
			alert("Thanks for buying in our store");
			obtainAllGames();
		}
	});
}