<!DOCTYPE html>
<html lang="en">
<head>
    <%@include file="header.jsp"%>
</head>

<body>

<%@include file="navigation.jsp"%>

<div class="container-fluid">
    <div class="row-fluid">
        <div class="col-md-12 well">
            <div class="container pull-left">
                <h3>Dashboards</h3>
                <span>A directory of dashboards of swagger services.</span>
            </div>
            <div class="pull-right">
                <div class="pull-right">
                    <a href="/dashboards/edit" data-toggle="tooltip" title="Create Dashboard" class="btn btn-primary"><span class="glyphicon glyphicon-plus-sign"></span></a>
                </div>
                <form action="" class="search-container">
                    <input data-bind="textInput: searchFilter" size="35" type="text" class="form-control" id="searchDashboards" placeholder="Search">
                </form>
            </div>
        </div>
        <div class="col-md-12 list-group" data-bind="foreach: searchDashboards">
            <a class="list-group-item" data-bind="attr: { href: dashboardLink }">
                <h4 class="list-group-item-heading" data-bind="text: name"></h4>
                <p class="list-group-item-text" data-bind="text: description"></p>
            </a>
        </div>
    </div><!--/row-->

</div><!--/.fluid-container-->

<%@include file="footer.jsp"%>
<script src="./static/js/dashboardmodel.js"></script>
<script src="./static/js/dashboards.js"></script>

</body>
</html>
