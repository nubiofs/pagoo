$(document).ready(function() {

	// CREATING PARALLAX
    $('div.bgParallax').each(function(){
        var $obj = $(this);
        $(window).scroll(function() {
            var yPos = -($(window).scrollTop() / $obj.data('speed')); 
            var bgpos = '50% '+ yPos + 'px';
            $obj.css('background-position', bgpos );
        }); 
    });

    // CREATING ANIMATES
    var config = {
    	reset:  false,
     	mobile: true,
        vFactor: 0.30
    }

 	// MENU SCROLL
	$(window).scroll(function () {
		if ($(window).width() >= 751) {
			var y_scroll_pos = $(this).scrollTop();
	    	var scroll_pos_test = 100;
	    	if (y_scroll_pos > scroll_pos_test) {
	    		$("#nav-scroll").slideDown("fast");
	    	} else {
				$("#nav-scroll").slideUp("fast");
		  	}
		} else {
			$("#nav-scroll").slideUp("fast");
		}
	});

	// MENU SCROLL ONLY DESKTOP
	$(window).resize(function () {	 
		if ($(window).width() <= 751) {		    		
			$("#nav-scroll").slideUp("fast");
		}   	
	});

	// MENU MOBILE
	$('#nav-main i.bt-nav').on('click', function() {
		if ( $(this).hasClass('fa-bars') ) {
			$(this).removeClass('fa-bars');
			$(this).css('color', '#ffffff');
			$(this).addClass('fa-times');
			centerMenuMobile();
			setTimeout(function(){
				$('#nav-mobile').stop().animate({ right: "0" });
	    	},'200');
		} else {
			$(this).removeClass('fa-times');
			$(this).css('color', '#0060AE');
			$(this).addClass('fa-bars');
			centerMenuMobile();
			setTimeout(function(){
				$('#nav-mobile').stop().animate({ right: "-300" });
	    	},'200');
		}
	});

	// CALL SCROLL REVEAL
    window.sr = new scrollReveal(config);

    // CENTER MENU MOBILE
    function centerMenuMobile() {
		var windowUser    = $(window).height()/2;
		var centralizaBox = $('#nav-mobile ul').height()/2;
		var numTotal      = ($(window).height()/2) - ($('#nav-mobile ul ').height()/2);
		$('#nav-mobile ul').css({ marginTop: numTotal +'px' });	
		$('#nav-mobile ul').fadeIn('slow');	
	}

	// NAVIGATE
    $('#nav-main ul li a, #nav-scroll ul li a, #final ul li a, #presentation a.go').on('click',function(event){
		event.preventDefault();
		if ( $(this).attr('href') == "a.html" ) {
			window.location="a.html";			
		} else {
			var $anchor = $(this);
			$('html, body').stop().animate({
	            scrollTop: $($anchor.attr('href')).offset().top
	        }, 1500, 'easeInOutExpo');
		}
    });

 	// NAVIGATE MOBILE
    $('#nav-mobile ul li a').on('click',function(event){
		event.preventDefault();
		if ( $(this).attr('href') == "a.html" ) {
			window.location="a.html";
		} else {
	        var $anchor = $(this);
			$('html, body').stop().animate({
	            scrollTop: $($anchor.attr('href')).offset().top
	        }, 1500, 'easeInOutExpo', function() {
	        	$('#nav-mobile').animate({ right: "-300" });
	        	$('#nav-main i.bt-nav').removeClass('fa-times');
	        	$('#nav-main i.bt-nav').addClass('fa-bars');
				$('#nav-main i.bt-nav').css('color', '#0060AE');
	        });
		}
    });
    
    // NAVIGATE FOOTER
    $('#final ul li a, #copyright a').on('click',function(event){
		event.preventDefault();
		if ( $(this).attr('href') == "a.html" ) {
			window.location="a.html";
		} else {
	        var $anchor = $(this);
			$('html, body').stop().animate({
	            scrollTop: $($anchor.attr('href')).offset().top
	        }, 1500, 'easeInOutExpo');
		}
    });

});