// $(document).ready(function(){
//
//
//
//
// 	/*  Hamburger Menu & Icon  */
// 	$('.hamburger').on('click', function(e){
//
// 		e.preventDefault();
// 		$(this).toggleClass('opned');
// 		$('header nav').toggleClass('active');
//
// 	});
//
//
//
//
// 	/*  Advanced search form & Icon  */
// 	$('#advanced_search_btn').on("click", function(e){
// 		e.preventDefault();
//
// 		var ads_box =$('.advanced_search');
//
// 		if(!ads_box.hasClass('advanced_displayed')){
//
// 			$(this).addClass('active');
// 			ads_box.stop().fadeIn(200).addClass('advanced_displayed');
//
// 		}else{
//
// 			$(this).removeClass('active');
// 			ads_box.stop().fadeOut(200).removeClass('advanced_displayed');
//
// 		}
//
// 	});
//
//
//
//
// });

function changeLanguage() {
	var l = $('#lang').text();

	console.log(l);
	window.history.replaceState({}, "", window.location.protocol + "//" + window.location.host + window.location.pathname + "?lang=" + l);
}