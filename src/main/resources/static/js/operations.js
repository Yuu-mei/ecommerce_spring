// Init vars
let from = 0;
let totalResults = 0;
let total_videogames_dev = 0;
let total_videogames_tag = 0;
let temp_dev_id = 0;
let temp_tag_id = 0;
let dev_from = 0;
let tag_from = 0;
const resultsPerPage = 12;

// AJAX Functions
function obtainAllGames(videogame_name = "") {
	$.post("../obtain-videogames-pagination", {videogame_name, from:from, resultsPerPage:resultsPerPage}).done((res) => {
		let videogames = res;
		// Set total amount of games
		$.get("../obtain-total-videogames", {videogame_name}).done((res) => {
			totalResults = res;
			
			// While it may seem it makes no sense, this is done to get the specific latest 3 games to appear on the featured area with the respective information
			$.ajax("../obtain-latest-games").done((res) => {
				let latest_videogames = JSON.parse(res);
				const render = Mustache.render(game_list_template, {videogames: videogames, latest_videogames: latest_videogames});
				
				
				$("#game_container").html(render);
				$("#search-form").submit(getVideogameByName);
				
				//Sanity checks to show or hide the button
				if((from+resultsPerPage) >= totalResults){
					$("#next-page").hide();
				}else{
					$("#next-page").show();
				}
				
				if((from-resultsPerPage) < 0){
					$("#previous-page").hide();
				}else{
					$("#previous-page").show();
				}
				
				$("#next-page").click(nextResults);
				$("#previous-page").click(previousResults);
				$(".get-videogame-details").click(getVideogameDetails);
				$(".get-dev-videogames").click(getVideogamesByDev);
			});
		});
	});		
}

function nextResults(){
	// I know this looks weird but I wanted to try it out
	(from+resultsPerPage < totalResults) ? ($(this).show(), from+= resultsPerPage, obtainAllGames()) : $(this).hide();
}

function previousResults(){
	(from-resultsPerPage >= 0) ? ($(this).show(), from-= resultsPerPage, obtainAllGames()) : $(this).hide();
}

function getVideogameByName(e){
	e.preventDefault();
	let search_val = $("#search-bar").val();
	$.post("../obtain-videogames-by-name", {videogame_name : search_val}).done((res) => {
		console.log(res);
		// Check if there is nothing like it and then return
		if(res == null || res == undefined || res.length == 0){
			Toastify({
			  text: "No search results!",
			  duration: 5000,
			  newWindow: true,
			  close: true,
			  gravity: "top",
			  position: "center",
			  stopOnFocus: false,
			  style: {
			    background: "rgb(220, 53, 69)",
			  },
			}).showToast();
			return;
		 }
		
		const render = Mustache.render(name_search_template, {videogames:res});
		$("#game_container").html(render);
		$(".get-videogame-details").click(getVideogameDetails);
	});
}

function getVideogameDetails(){
	let videogame_id = $(this).attr("videogame-id");
	$.getJSON("../obtain-videogame-detail", {id:videogame_id}).done(res => {
		let videogame = res;
		let tag_id = res.tag_id;
		$.getJSON("../obtain-similar-videogames", {tag_id:tag_id, videogame_id:videogame_id}).done(res => {
			let similar_games = res;
			const render = Mustache.render(game_detail_template, {videogame:videogame, similar_games:similar_games});
			$("#game_container").html(render);
			$("#add_game_to_cart").click(addGameToCart);
			isVideogameWishlisted();
			$("#wishlist").click(wishlistGame);
			$(".get-videogame-details").click(getVideogameDetails);
			$(".get-dev-videogames").click(getVideogamesByDev);
			$(".get-tag-videogames").click(getVideogamesByTag);
		});
	});
}

function isVideogameWishlisted(){
	let wishListIcon = $("#wishlist");
	let videogame_id = wishListIcon.attr("videogame-id");
	let isGameWishlisted = false;
	
	if(logged_username == ""){
		return;
	}
	
	$.get("../is-videogame-wishlisted", {videogame_id, username:logged_username}).done(res => {
		isGameWishlisted = res;
		
		if(!isGameWishlisted){
			wishListIcon.removeClass("bi-heart-fill");
			wishListIcon.removeClass("clicked");
			wishListIcon.addClass("bi-heart");
		}else{
			wishListIcon.removeClass("bi-heart");
			wishListIcon.addClass("bi-heart-fill");
			wishListIcon.addClass("clicked");
		}
	});
}

function wishlistGame(){
	let videogame_id = $(this).attr("videogame-id");
	let wishListIcon = $(this);
	let isGameWishlisted = false;
	
	// If not logged, don't allow them to wishlist
	if(logged_username == ""){
		Toastify({
		  text: "Not logged in!",
		  duration: 5000,
		  newWindow: true,
		  close: true,
		  gravity: "top",
		  position: "center",
		  stopOnFocus: false,
		  style: {
		    background: "rgb(220, 53, 69)",
		  },
		}).showToast();
		return;
	}
	
	// Wishlist logic
	$.get("../is-videogame-wishlisted", {videogame_id, username:logged_username}).done(res => {
		isGameWishlisted = res;
		
		if(isGameWishlisted){
				// Logic
				$.post("../remove-videogame-from-wishlist", {videogame_id, username:logged_username}).done(() => {
					console.log("Game removed");
				})
				
				// Visuals
				wishListIcon.removeClass("bi-heart-fill");
				wishListIcon.removeClass("clicked");
				wishListIcon.addClass("bi-heart");
			}else{
				// Logic
				$.post("../add-videogame-to-wishlist", {videogame_id, username:logged_username}).done(() => {
					console.log("Game added");
				})
				
				// Visuals
				wishListIcon.removeClass("bi-heart");
				wishListIcon.addClass("bi-heart-fill");
				wishListIcon.addClass("clicked");
			}
	});

}

function getVideogamesByDev(){
	let dev_id = $(this).attr("dev-id");
	//Sanity checks as the next and previous buttons don't have the dev id
	if(dev_id != undefined){
		temp_dev_id = dev_id;
	}
	if(dev_id == undefined){
		dev_id = temp_dev_id;
	}
	
	$.get("../obtain-total-videogames-dev", {dev_id}).done(res =>{
		total_videogames_dev = res;
		
		$.getJSON("../obtain-videogames-by-developer-with-pagination", {dev_id, from: dev_from, resultsPerPage}).done(res => {
			console.log(res);
			let render = Mustache.render(dev_search_template, res);
			
			//Hide and Show Visibility
			if((dev_from+resultsPerPage) >= total_videogames_dev){
				$("#next-page-dev").hide();
			}else{
				$("#next-page-dev").show();
			}

			if((dev_from-resultsPerPage) < 0){
				$("#previous-page-dev").hide();
			}else{
				$("#previous-page-dev").show();
			}
			
			$("#game_container").html(render);
			$("#previous-page-dev").click(previousResultsDev);
			$("#next-page-dev").click(nextResultsDev);
			$(".get-videogame-details").click(getVideogameDetails);
		});
	});
}

function nextResultsDev(){
	(dev_from+resultsPerPage < total_videogames_dev) ? ($(this).show(), dev_from+= resultsPerPage, getVideogamesByDev()) : $(this).hide();
}

function previousResultsDev(){
	(dev_from-resultsPerPage >= 0) ? ($(this).show(), dev_from-= resultsPerPage, getVideogamesByDev()) : $(this).hide();
}

function getVideogamesByTag(){
	let tag_id = $(this).attr("tag-id");
	
	//Sanity checks as the next and previous buttons don't have the dev id
	if(tag_id != undefined){
		temp_tag_id = tag_id;
	}
	if(tag_id == undefined){
		tag_id = temp_tag_id;
	}
	
	$.get("../obtain-total-videogames-tag", {tag_id}).done(res => {
		total_videogames_tag = res;
		
		$.getJSON("../obtain-videogames-by-tag-with-pagination", {tag_id, from: tag_from, resultsPerPage}).done(res => {
			console.log(res);
			let render = Mustache.render(tag_search_template, res);
			$("#game_container").html(render);
			
			//Hide and Show Visibility
			if((tag_from+resultsPerPage) >= total_videogames_tag){
				$("#next-page-tag").hide();
			}else{
				$("#next-page-tag").show();
			}

			if((tag_from-resultsPerPage) < 0){
				$("#previous-page-tag").hide();
			}else{
				$("#previous-page-tag").show();
			}
			
			$(".get-videogame-details").click(getVideogameDetails);
			$("#previous-page-tag").click(previousResultsTag);
			$("#next-page-tag").click(nextResultsTag);
		});
	});
}

function nextResultsTag(){
	(tag_from+resultsPerPage < total_videogames_tag) ? ($(this).show(), tag_from+= resultsPerPage, getVideogamesByTag()) : $(this).hide();
}

function previousResultsTag(){
	(tag_from-resultsPerPage >= 0) ? ($(this).show(), tag_from-= resultsPerPage, getVideogamesByTag()) : $(this).hide();
}

function addGameToCart(){
	if(logged_username == ""){
		Toastify({
		  text: "Not logged in!",
		  duration: 5000,
		  newWindow: true,
		  close: true,
		  gravity: "top",
		  position: "center",
		  stopOnFocus: false,
		  style: {
		    background: "rgb(220, 53, 69)",
		  },
		}).showToast();
		return;
	}
	
	let videogame_id = $(this).attr("videogame-id");
	console.log(`Added game ${videogame_id} to cart`);
	$.post("../add-game-to-cart", {
		id: videogame_id,
		quantity: 1
	}).done(res => {
		if(res == "OK"){
			Toastify({
			  text: "Added Product to Cart",
			  duration: 5000,
			  newWindow: true,
			  close: true,
			  gravity: "top",
			  position: "center",
			  stopOnFocus: false,
			  style: {
			    background: "rgb(25, 135, 84)",
			  },
			}).showToast();
		}
	});
}

function removeGameFromCart(videogame_id){
	$.post("../remove-game-from-cart", {id: videogame_id}).done(() => {
		$(`#${videogame_id}`).hide();
	});
}

function alterGameQuantity(videogame_id, quantity){
	$.post("../alter-game-quantity", {id: videogame_id, quantity});
}

function obtainCartProducts(){
	if(logged_username == ""){
		Toastify({
		  text: "Not logged in!",
		  duration: 5000,
		  newWindow: true,
		  close: true,
		  gravity: "top",
		  position: "center",
		  stopOnFocus: false,
		  style: {
		    background: "rgb(220, 53, 69)",
		  },
		}).showToast();
		showLoginForm();
		return;
	}
	
	$.getJSON("../obtain-cart-products").done((res) => {
		console.log(res);
		let render = Mustache.render(cart_template, res);
		// Fix the appearence or disappearence of the checkout button
		cart_products_count = res.length;
		console.log(cart_products_count);
		$("#game_container").html(render);
		checkIfCartIsEmpty();
		$(".get-videogame-details").click(getVideogameDetails);
		$("#confirm_order_processing").click(checkout_entry);
		let total_price = 0;
		for(index in res){
			total_price += res[index].price * res[index].quantity;
			let videogame_id = res[index].videogame_id;
		
			$(".remove-item").click((e) => {
				e.preventDefault();
				// We substract one from the total count, as the quantity is INSIDE the product and we don't care about that here
				cart_products_count -= 1;
				// After any operation we have to check if the count is greater than 0 to enable or disable the button
				checkIfCartIsEmpty();
				removeGameFromCart(videogame_id);
				total_price -= res[index].price * res[index].quantity;
			});
			
			$(".quantity-right-plus").click((e) => {
			    e.preventDefault();
			    let quantity_val = parseInt($("#quantity").val()) + 1;
				cart_products_count += 1;
			    $("#quantity").val(quantity_val);
				alterGameQuantity(videogame_id, quantity_val);
			});
			
			$(".quantity-left-minus").click((e) => {
			   e.preventDefault();
			   let quantity_val = parseInt($("#quantity").val());
			   if(quantity_val>1){
				   quantity_val -= 1;
				   cart_products_count -= 1;
				   checkIfCartIsEmpty();
			       $("#quantity").val(quantity_val);
				   alterGameQuantity(videogame_id, quantity_val);
			   }
			});
		}
		$("#total").html(total_price);
	});
}

function login(){
	$.post("../login-user", {
		email: $("#email").val(),
		pwd: $("#pwd").val()
	}).done(({response, message, username}) => {
		if(response == "OK"){
			if($("#remember-user").prop("checked")){
				Cookies.set("email", $("#email").val(), {expires: 100});
				Cookies.set("pwd", $("#pwd").val(), {expires: 100});
			}else{
				if(typeof(Cookies.get("email")) != "undefined"){
					Cookies.remove("email");
				}
				if(typeof(Cookies.get("pwd")) != "undefined"){
					Cookies.remove("pwd");
				}
			}
			logged_username = username;
			obtainAllGames();
			$("#login-item").hide();
			$("#signup-item").hide();
			$("#logout-item").show();
			$("#profile-item").show();
		}else{
			Toastify({
			  text: "Error login in, either password or email are incorrect",
			  duration: 5000,
			  newWindow: true,
			  close: true,
			  gravity: "top", 
			  position: "center", 
			  stopOnFocus: false,
			  style: {
			    background: "rgb(220, 53, 69)",
			  },
			}).showToast();
		}
	});
}

function obtainUserInfo(){
	if(logged_username == ""){
		Toastify({
		  text: "Not logged in!",
		  duration: 5000,
		  newWindow: true,
		  close: true,
		  gravity: "top", 
		  position: "center", 
		  stopOnFocus: false,
		  style: {
		    background: "rgb(220, 53, 69)",
		  },
		}).showToast();
		return;	
	}
	
	$.post("../obtain-user-info", {
		username: logged_username
	}).done((res) => {
		let render = Mustache.render(profile_template, res);
		$("#game_container").html(render);
		$(".get-videogame-details").click(getVideogameDetails);
	});
}

function logout(){
	$.post("../logout").done(() => {
		$("#login-item").show();
		$("#signup-item").show();
		$("#profile-item").hide();
		$("#logout-item").hide();
		obtainAllGames();
	});
}

function registerUser(){
	let username = $("#username").val();
	let email = $("#email").val();
	let pwd = $("#pwd").val();
	let country = $("#country").val();
	let phone = $("#phone").val();
	
	console.log(username);
	
	// Validations
	
	// Image
	if ($('#user_image_file').get(0).files.length == 0) {
		Toastify({
		  text: "You must upload an image",
		  duration: 5000,
		  newWindow: true,
		  close: true,
		  gravity: "top", 
		  position: "center", 
		  stopOnFocus: false,
		  style: {
		    background: "rgb(220, 53, 69)",
		  },
		}).showToast();
		return;
	}
	
	// Every single function has its own toast
	if(!validateUsername(username) || !validateEmail(email) || !validatePassword(pwd) || !validateCountry(country) || !validatePhone(phone)){
		return;
	}
	
	let formData = new FormData($("#user_form")[0]);
	// Turns out you need to pass it through ajax instead of post if you have files
	// Plus you need to turn off the contentType and processData to avoid issues with files too
	$.ajax({
		url: "../register-user",
		type: "POST",
		data: formData,
		contentType: false,
		processData: false,
		success: (res) => {
			Toastify({
					  text: "User created succesfully",
					  duration: 5000,
					  newWindow: true,
					  close: true,
					  gravity: "top",
					  position: "center",
					  stopOnFocus: false,
					  style: {
					    background: "rgb(220, 53, 69)",
					  },
			}).showToast();
			showLoginForm();
		},
		error: (xhr, status, error) => {
			console.log(error);
		}
	});
}

// Template Functions
function showSignUpForm() {
	$("#game_container").html(sign_up_form_template);
	$("#submitUser").click(registerUser);
}

function showLoginForm(){
	$("#game_container").html(login_form_template);
	
	if(typeof(Cookies.get("email")) != "undefined"){
		$("#email").val(Cookies.get("email"));
	}
	if(typeof(Cookies.get("pwd")) != "undefined"){
		$("#pwd").val(Cookies.get("pwd"));
	}
	
	$("#form_login").submit((e) => {
		e.preventDefault();
		login();
	});
}

//Helper functions
function formatDate(date){
	let date_obj = new Date(date);
	let year = date_obj.getFullYear();
	//Turns out months start at 0 in JS
	let month = date_obj.getMonth() + 1;
	return `${year}-${month}`;
}

function checkIfCartIsEmpty(){
	if(cart_products_count > 0){
		$("#confirm_order_processing").prop("disabled", false);
	}else{
		$("#confirm_order_processing").prop("disabled", true);
	}
}