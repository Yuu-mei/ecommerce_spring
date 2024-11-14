// User Sign Up - Validations
let username_regexp = /^[a-z_ -]{3,30}$/i;
let email_regexp = /^([a-z0-9_\.-]+)@([0-9a-z\.-]+)\.([a-z\.]+)$/i;
let pwd_regexp = /^[a-z0-9]{8,20}$/i;
let country_regexp = /^(AF|AX|AL|DZ|AS|AD|AO|AI|AQ|AG|AR|AM|AW|AU|AT|AZ|BS|BH|BD|BB|BY|BE|BZ|BJ|BM|BT|BO|BQ|BA|BW|BV|BR|IO|BN|BG|BF|BI|KH|CM|CA|CV|KY|CF|TD|CL|CN|CX|CC|CO|KM|CG|CD|CK|CR|CI|HR|CU|CW|CY|CZ|DK|DJ|DM|DO|EC|EG|SV|GQ|ER|EE|ET|FK|FO|FJ|FI|FR|GF|PF|TF|GA|GM|GE|DE|GH|GI|GR|GL|GD|GP|GU|GT|GG|GN|GW|GY|HT|HM|VA|HN|HK|HU|IS|IN|ID|IR|IQ|IE|IM|IL|IT|JM|JP|JE|JO|KZ|KE|KI|KP|KR|KW|KG|LA|LV|LB|LS|LR|LY|LI|LT|LU|MO|MK|MG|MW|MY|MV|ML|MT|MH|MQ|MR|MU|YT|MX|FM|MD|MC|MN|ME|MS|MA|MZ|MM|NA|NR|NP|NL|NC|NZ|NI|NE|NG|NU|NF|MP|NO|OM|PK|PW|PS|PA|PG|PY|PE|PH|PN|PL|PT|PR|QA|RE|RO|RU|RW|BL|SH|KN|LC|MF|PM|VC|WS|SM|ST|SA|SN|RS|SC|SL|SG|SX|SK|SI|SB|SO|ZA|GS|SS|ES|LK|SD|SR|SJ|SZ|SE|CH|SY|TW|TJ|TZ|TH|TL|TG|TK|TO|TT|TN|TR|TM|TC|TV|UG|UA|AE|GB|US|UM|UY|UZ|VU|VE|VN|VG|VI|WF|EH|YE|ZM|ZW|AFG|ALB|DZA|ASM|AND|AGO|AIA|ATA|ATG|ARG|ARM|ABW|AUS|AUT|AZE|BHS|BHR|BGD|BRB|BLR|BEL|BLZ|BEN|BMU|BTN|BOL|BIH|BWA|BVT|BRA|IOT|VGB|BRN|BGR|BFA|BDI|KHM|CMR|CAN|CPV|CYM|CAF|TCD|CHL|CHN|CXR|CCK|COL|COM|COD|COG|COK|CRI|CIV|CUB|CYP|CZE|DNK|DJI|DMA|DOM|ECU|EGY|SLV|GNQ|ERI|EST|ETH|FRO|FLK|FJI|FIN|FRA|GUF|PYF|ATF|GAB|GMB|GEO|DEU|GHA|GIB|GRC|GRL|GRD|GLP|GUM|GTM|GIN|GNB|GUY|HTI|HMD|VAT|HND|HKG|HRV|HUN|ISL|IND|IDN|IRN|IRQ|IRL|ISR|ITA|JAM|JPN|JOR|KAZ|KEN|KIR|PRK|KOR|KWT|KGZ|LAO|LVA|LBN|LSO|LBR|LBY|LIE|LTU|LUX|MAC|MKD|MDG|MWI|MYS|MDV|MLI|MLT|MHL|MTQ|MRT|MUS|MYT|MEX|FSM|MDA|MCO|MNG|MSR|MAR|MOZ|MMR|NAM|NRU|NPL|ANT|NLD|NCL|NZL|NIC|NER|NGA|NIU|NFK|MNP|NOR|OMN|PAK|PLW|PSE|PAN|PNG|PRY|PER|PHL|PCN|POL|PRT|PRI|QAT|REU|ROU|RUS|RWA|SHN|KNA|LCA|SPM|VCT|WSM|SMR|STP|SAU|SEN|SCG|SYC|SLE|SGP|SVK|SVN|SLB|SOM|ZAF|SGS|ESP|LKA|SDN|SUR|SJM|SWZ|SWE|CHE|SYR|TWN|TJK|TZA|THA|TLS|TGO|TKL|TON|TTO|TUN|TUR|TKM|TCA|TUV|VIR|UGA|UKR|ARE|GBR|UMI|USA|URY|UZB|VUT|VEN|VNM|WLF|ESH|YEM|ZMB|ZWE)$/
let phone_regexp = /^\s*(?:\+?(\d{1,3}))?([-. (]*(\d{3})[-. )]*)?((\d{3})[-. ]*(\d{2,4})(?:[-.x ]*(\d+))?)\s*$/;

function validateUsername(username){
	if(username_regexp.test(username)){
		return true;
	}else{
		Toastify({
		  text: "Incorrect username (only letters, _ - and spaces; 3 to 30 characters)",
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
}

function validateEmail(email){
	if(email_regexp.test(email)){
		return true;
	}else{
		Toastify({
		  text: "Incorrect email",
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
}

function validatePassword(pwd){
	if(pwd_regexp.test(pwd)){
		return true;
	}else{
		Toastify({
		  text: "Incorrect password (only letters and numbers, 8 to 20 characters)",
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
}

function validateCountry(country){
	if(country_regexp.test(country)){
		return true;
	}else{
		Toastify({
		  text: "Incorrect country code (either 2 or 3 letters version like ES or ESP)",
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
}

function validatePhone(phone){
	if(phone_regexp.test(phone)){
		return true;
	}else{
		Toastify({
		  text: "Incorrect phone number (7 chars long at least, allows + and -)",
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
}

// Order - Validations
let fullname_regexp = /^[a-z ]{4,}$/i;
let address_regexp = /^[a-z0-9 ]{10,}$/i;
let state_regexp = /^[a-z ]{4,}$/i;
let zipcode_regexp = /^[0-9]{5,}$/;
// Phone validation it's the same as before; Same goes for cardOwner
let cardnumber_regexp = /^(\d{4}[-. ]?){4}|\d{4}[-. ]?\d{6}[-. ]?\d{5}$/g;
let ccv_regexp = /^[0-9]{3}$/

function validateFullName(fullname){
	if(fullname_regexp.test(fullname)){
		return true;
	}else{
		Toastify({
		  text: "Incorrect name (only letters and spaces; 4 chars min)",
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
}

function validateAddress(address){
	if(address_regexp.test(address)){
		return true;
	}else{
		Toastify({
		  text: "Incorrect address (only letters, numbers and spaces; 10 chars min)",
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
}

function validateState(state){
	if(state_regexp.test(state)){
		return true;
	}else{
		Toastify({
		  text: "Incorrect state (only letters and spaces; 4 chars min)",
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
}

function validateZipCode(zipcode){
	if(zipcode_regexp.test(zipcode)){
		return true;
	}else{
		Toastify({
		  text: "Incorrect zipcode (only numbers; 5 min)",
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
}

function validateCardNumber(cardnumber){
	if(cardnumber_regexp.test(cardnumber)){
		return true;
	}else{
		Toastify({
		  text: "Incorrect card number (16 digits, allows spaces, dots and -)",
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
}

function validateCCV(ccv){
	if(ccv_regexp.test(ccv)){
		return true;
	}else{
		Toastify({
		  text: "Incorrect ccv (only 3 numbers)",
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
}