// Template Loading
let game_list_template = "";
let sign_up_form_template = "";
let login_form_template = "";
let game_detail_template = "";
let dev_search_template = "";
let tag_search_template = "";
let name_search_template = "";
let cart_template = "";
let checkout_general_template = "";
let checkout_card_template = "";
let checkout_shipping_template = "";
let checkout_summary_template = "";
let profile_template = "";

$.get(`../templates${language_suffix}/register_user.html`).done((res) => {
	sign_up_form_template = res;
});

$.get(`../templates${language_suffix}/game_list.html`).done((res) => {
	game_list_template = res;
});

$.get(`../templates${language_suffix}/login_form.html`).done((res) => {
	login_form_template = res;
});

$.get(`../templates${language_suffix}/videogame_detail.html`).done((res) => {
	game_detail_template = res;
});

$.get(`../templates${language_suffix}/dev_search.html`).done((res) => {
	dev_search_template = res;
});

$.get(`../templates${language_suffix}/tag_search.html`).done((res) => {
	tag_search_template = res;
});

$.get(`../templates${language_suffix}/name_search.html`).done((res) => {
	name_search_template = res;
});

$.get(`../templates${language_suffix}/cart.html`).done((res) => {
	cart_template = res;
});

$.get(`../templates${language_suffix}/checkout_general_form.html`).done((res) => {
	checkout_general_template = res;
});

$.get(`../templates${language_suffix}/checkout_card_form.html`).done((res) => {
	checkout_card_template = res;
});

$.get(`../templates${language_suffix}/checkout_shipping_form.html`).done((res) => {
	checkout_shipping_template = res;
});

$.get(`../templates${language_suffix}/checkout_order_summary.html`).done((res) => {
	checkout_summary_template = res;
});

$.get(`../templates${language_suffix}/profile.html`).done((res) => {
	profile_template = res;
});