/*JAVASCRIPT FOR INDEX PAGE*/
/* CONTENT
 * -Scroll
 * -form validation
 * =login/register form change content
 * */

// Scroll to target
function scrollToTarget(D)
{
	if(D == 1) // Top of page
	{
		D = 0;
	}
	else if(D == 2) // Bottom of page
	{
		D = $(document).height();
	}
	else // Specific Bloc
	{
		D = $(D).offset().top;
	}

	$('html,body').animate({scrollTop:D}, 'slow');
}

//Login register change content
function login_register(){
	$('#login-form-link').click(function(e) {
		$("#login-form").delay(100).fadeIn(100);
 		$("#register-form").fadeOut(100);
		$('#register-form-link').removeClass('active');
		$(this).addClass('active');
		e.preventDefault();
	});
	$('#register-form-link').click(function(e) {
		$("#register-form").delay(100).fadeIn(100);
 		$("#login-form").fadeOut(100);
		$('#login-form-link').removeClass('active');
		$(this).addClass('active');
		e.preventDefault();
	});
}

//login register form validation
//this function uses JQuery form validation
function form_validation(){
	$("#login-form").validate({
		// Specify the validation rules
        rules: {
            username: {
                required: true,
                minlength: 4
            },
            password: {
                required: true,
                minlength: 5
            }
        },
        
        // Specify the validation error messages
        messages: {
        	username: {
                required: "Please enter your username",
                minlength: "Your username must be at least 4 characters long"
            },
            password: {
                required: "Please provide a password",
                minlength: "Your password must be at least 5 characters long"
            }
        },
        
        //if error occurs the request won't be send
        submitHandler: function(form) {
            form.submit();
        }
	});
	
	
	$("#register-form").validate({
		// Specify the validation rules
        rules: {
            username: {
                required: true,
                minlength: 4
            },
            
            email:{
            	required: true,
            	email: true,
                minlength: 6
            },
            
            password: {
                required: true,
                minlength: 5
            },
            
            confirmpassword: {
 	                required: true,
 	                minlength: 5
            }
        },
        
        // Specify the validation error messages
        messages: {
        	username: {
                required: "Please enter your username",
                minlength: "Your username must be at least 4 characters long"
            },
            password: {
                required: "Please provide a password",
                minlength: "Your password must be at least 5 characters long"
            },
            email: {
                required: "Please enter your email",
                minlength: "Your username must be at least 6 characters long"
            },
            //confirm-password doesn't work
            //the id in confirm password input in register form should be changed to confirmpassowrd
            confirmpassword: {
                required: "Please provide a password",
                minlength: "Your password must be at least 5 characters long"
            }
        },
        
        //if error occurs the request won't be send
        submitHandler: function(form) {
            form.submit();
        }
	});
}