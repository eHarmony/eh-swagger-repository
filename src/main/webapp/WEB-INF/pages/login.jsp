<!DOCTYPE html>
<html lang="en">
<head>
    <%@include file="header.jsp"%>
</head>

<body>

<div class="navbar navbar-inverse navbar-fixed-top">
    <nav class="navbar navbar-inverse navbar-fixed-top">
        <div>
            <div class="navbar-header"><a class="navbar-brand" href="/">API</a></div>
        </div>
    </nav>
</div>

<div class="container-fluid">
    <div class="center-form well">

        <div class="alert ${parameters.flashClass}">
            <strong>${parameters.flash}</strong>
        </div>

        <form class="form-signin" action="perform_login" method='POST'>
            <h2 class="form-signin-heading">Sign In</h2>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <label for="inputUsername" class="sr-only">Username</label>
            <input type="text" id="inputUsername" name="username" class="form-control" placeholder="Username" required autofocus>
            <label for="inputPassword" class="sr-only">Password</label>
            <input type="password" id="inputPassword" name="password" class="form-control" placeholder="Password" required>
            <div class="checkbox">
                <label>
                    <input type="checkbox" name="remember-me"> Remember me
                </label>
            </div>
            <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
        </form>
    </div>
</div><!--/container-->


</body>
</html>
