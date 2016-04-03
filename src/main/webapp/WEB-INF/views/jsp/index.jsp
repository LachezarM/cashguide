<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Business Frontpage - Start Bootstrap Template</title>

    <!-- Bootstrap Core CSS -->
    <link type="text/css"  href="css/bootstrap.min.css" rel="stylesheet">
    <!-- Custom CSS -->
    <link type="text/css" href="css/business-frontpage.css" rel="stylesheet">
	<!-- Login and register CSS -->
	<link type="text/css" href="css/login_register.css" rel="stylesheet" />

	<style>
	.has-error-text{
		color: #a94442;
	  	background-color: #f2dede;
	  	border-color: #a94442;
	  	font-size:24px;
	  	margin-left:20px;
	  	margin-right:20px;
	  	display:inline;
	}
	/*START JQUERY VALIDATE STYLE*/
	/*the error message from jquery validate function will hava this styles*/
	.error{
		color: #a94442;
	}
	input.error{
		/*color: #a94442;*/
	  	background-color: #f2dede;
	  	border-color: #a94442;
	}
	/*END JQUERY VALIDATE STYLE*/
	</style>




	<!-- jQueryV2.2.2 -->
    <script src="js/jquery-2.2.2.min.js"></script>
    
    <!-- BootstrapV3.3.6 Core JavaScript -->
    <script src="js/bootstrap.min.js"></script>
    
	<!-- SmoothScroll -->
	<script src="js/smoothscroll.js"></script>
	
	<!-- JQuery Validation plugin -->
	<!-- Plugins for Form validation with jquery -->
	<script src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.9/jquery.validate.min.js"></script>
	<script src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.9/additional-methods.min.js"></script>
	
	<!--Login and register JavaScript
		NOT USED-->
	<script src="js/login_register.js"></script>
	
	<script src="js/index.js"></script>
	<script type="text/javascript">
	$(function(){
		//login_register->index.js
		login_register();
		//form_validation->index.js
		form_validation();
	})	
	</script>
</head>

<body>

    <!-- Navigation -->
    <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
        <div class="container">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
              <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand smoothScroll" onclick="scrollToTarget('1')">Logo</a>
            </div>
            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav">
					
                    <li>
                        <a onclick="scrollToTarget('#about')" >About</a>
                    </li>
                    <li>
                        <a onclick="scrollToTarget('#login')">Login</a>
                    </li>
                    <li>
                        <a onclick="scrollToTarget('2')">Contact</a>
                    </li>
                </ul>
            </div>
            <!-- /.navbar-collapse -->
        </div>
        <!-- /.container -->
    </nav>

    <!-- Image Background Page Header -->
    <!-- Note: The background image is set within the business-casual.css file. -->
    <header class="business-header" name="start">
        <div class="container">
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="tagline">Cashguide</h1>
                </div>
            </div>
        </div>
    </header>

    <!-- Page Content -->
    <div class="container">
        <hr>
		<!--About us-->
        <div class="row" name="about" id="about" style="margin-top:150px;">
            <div class="col-sm-12 text-center">
                <h2 style="margin-top:50px;" >What We Do</h2>
                <p>Introduce the visitor to the business using clear, informative text. Use well-targeted keywords within your sentences to make sure search engines can find the business.</p>
                <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Et molestiae similique eligendi reiciendis sunt distinctio odit? Quia, neque, ipsa, adipisci quisquam ullam deserunt accusantium illo iste exercitationem nemo voluptates asperiores.</p>
                <p>
                    <a class="btn btn-default btn-lg" href="#">Call to Action &raquo;</a>
                </p>
            </div>
        </div>
        <!-- /.row -->
		<hr>
		<!--LOGIN AND REGISTER-->
		<div class="row" name="login" id="login">
		    <div class="col-sm-12 text-center">
				<div class="col-md-6 col-md-offset-3">
				<div class="panel panel-login" style="margin-top:100px">
					<div class="panel-heading">
						<div class="row">
							<div class="col-xs-6">
								<a href="#" class="active" id="login-form-link">Login</a>
							</div>
							<div class="col-xs-6">
								<a href="#" id="register-form-link">Register</a>
							</div>
						</div>
						<hr>
					</div>
					<!-- PANEL BODY -->
					<div class="panel-body">
						<div class="row">
							<div class="col-lg-12">
							
							<!-- LOGIN FORM -->
								<form id="login-form" action="login" method="post" role="form" style="display: block;">
									<div class="form-group">
										<input type="text" name="username" id="username" tabindex="1" class="form-control" placeholder="Username" value="">
									</div>
									<div class="form-group">
										<input type="password" name="password" id="password" tabindex="2" class="form-control" placeholder="Password">
									</div>
									<!--<div class="form-group text-center">
										<input type="checkbox" tabindex="3" class="" name="remember" id="remember">
										<label for="remember"> Remember Me</label>
									</div>-->
									<div class="form-group">
										<div class="row">
											<div class="col-sm-6 col-sm-offset-3">
												<input type="submit" name="login-submit" id="login-submit" tabindex="4" class="form-control btn btn-login" value="Log In">
											</div>
										</div>
									</div>
									<div class="form-group">
										<div class="row">
											<div class="col-lg-12">
												<div class="text-center">
													<!--<a href="#" tabindex="5" class="forgot-password">Forgot Password?</a><br>-->
													<div class="has-error-text">${LoginErrorInfo}</div>
												</div>
											</div>
										</div>
									</div>
								</form>
								
							<!-- REGISTER FORM -->
								<form id="register-form" action="register" method="post" role="form" style="display: none;">
									<div class="form-group">
										<input type="text" name="username" id="username" tabindex="1" class="form-control" placeholder="Username" value="${fn:escapeXml(param.username)}">
									</div>
									<div class="form-group">
										<input type="email" name="email" id="email" tabindex="1" class="form-control" placeholder="Email Address" value="${fn:escapeXml(param.email)}">
									</div>
									<div class="form-group">
										<input type="password" name="password" id="password" tabindex="2" class="form-control" placeholder="Password" value="${fn:escapeXml(param.password)}"}">
									</div>
									<div class="form-group">
										<input type="password" name="confirm-password" id="confirm-password" tabindex="2" class="form-control" placeholder="Confirm Password" value="${fn:escapeXml(param.password)}">
									</div>
									<div class="form-group">
										<div class="row">
											<div class="col-sm-6 col-sm-offset-3">
												<input type="submit" name="register-submit" id="register-submit" tabindex="4" class="form-control btn btn-register" value="Register Now">
											</div>
										</div>
									</div>
									
									<div class="form-group">
										<div class="row">
											<div class="col-lg-12">
												<div class="text-center">
													<!--<a href="#" tabindex="5" class="forgot-password">Forgot Password?</a><br>-->
													<div class="has-error-text">${RegisterErrorInfo}</div>
												</div>
											</div>
										</div>
									</div>
								</form>
							</div>
						</div>
					</div>
					<!-- END PANEL BODY -->
				</div>
			</div>
		</div>
		</div>
		<!-- END LOGIN AND REGISTER -->
        <hr>
		<!--contact us-->
		<div class="row" name="contact">
		    <div class="col-sm-12 text-center">
                <h2 style="margin-top:50px;">Contact Us</h2>
                <address>
                    <strong>Start Bootstrap</strong>
                    <br>3481 Melrose Place
                    <br>Beverly Hills, CA 90210
                    <br>
                </address>
                <address>
                    <abbr title="Phone">P:</abbr>(123) 456-7890
                    <br>
                    <abbr title="Email">E:</abbr> <a href="mailto:#">name@example.com</a>
                </address>
            </div>
		</div>
				
     <!--   <div class="row">
            <div class="col-sm-4">
                <img class="img-circle img-responsive img-center" src="http://placehold.it/300x300" alt="">
                <h2>Marketing Box #1</h2>
                <p>These marketing boxes are a great place to put some information. These can contain summaries of what the company does, promotional information, or anything else that is relevant to the company. These will usually be below-the-fold.</p>
            </div>
            <div class="col-sm-4">
                <img class="img-circle img-responsive img-center" src="http://placehold.it/300x300" alt="">
                <h2>Marketing Box #2</h2>
                <p>The images are set to be circular and responsive. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh, ut fermentum massa justo sit amet risus. Etiam porta sem malesuada magna mollis euismod. Donec sed odio dui.</p>
            </div>
            <div class="col-sm-4">
                <img class="img-circle img-responsive img-center" src="http://placehold.it/300x300" alt="">
                <h2>Marketing Box #3</h2>
                <p>Donec id elit non mi porta gravida at eget metus. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh, ut fermentum massa justo sit amet risus. Etiam porta sem malesuada magna mollis euismod. Donec sed odio dui.</p>
            </div>
        </div>-->
        <!-- /.row -->

        <hr>

        <!-- Footer -->
        <footer>
            <div class="row">
                <div class="col-lg-12 text-center">
                    <p>Copyright &copy; Your Website 2014</p>
                </div>
            </div>
            <!-- /.row -->
        </footer>

    </div>
    <!-- /.container -->
</body>

</html>

