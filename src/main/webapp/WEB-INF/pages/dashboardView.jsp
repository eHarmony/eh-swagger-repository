
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
                <h3 data-bind="text: dashboard.name"></h3>
                <span data-bind="text: dashboard.description"></span>
            </div>
            <div class="pull-right">
                <a data-bind="attr: { href: dashboard.dashboardEditLink }" class="btn btn-primary glyphicon glyphicon-edit" data-toggle='tooltip' title='Edit'></a>
            </div>
        </div>
        <div id="sidebar" class="col-md-3 well well-sm sidebar">
            <div id="sidebar-list">
                <h4>Services<a id="hide-link" href="#" onclick="toggleSidebar()" class="glyphicon glyphicon-arrow-left pull-right action" data-toggle="tooltip" title="Hide"></a></h4>
                <ul class="nav" data-bind="foreach: dashboardDocumentation">
                    <li class="col-md-10">
                        <a href="#" data-bind="click: $parent.documentation.setCurrentDocumentation, html: displayName"></a>
                    </li>
                </ul>
            </div>
        </div>
        <div id="sidebar-show" class="col-md-1 well well-sm sidebar arrow-show">
            <h4><a href="#" onclick="toggleSidebar()" class="glyphicon glyphicon-arrow-right action" data-toggle="tooltip" title="Show"></a></h4>
        </div>
        <div id="api-frame-container" class="col-md-9">
            <div id="api-link" class="pull-right">
                <h4 data-bind="text: documentation.currentDocumentation().environment.toUpperCase()"></h4>
                <div class="btn-group">
                    <a class="btn btn-info" target="_blank" data-bind="attr: {href: documentation.currentDocumentation().displaySwaggerSpec}, click: accessedSpec">Spec</a>
                </div>
                <div class="btn-group">
                    <a class="btn btn-info" target="_blank" data-bind="attr: {href: documentation.currentDocumentation().displaySwaggerUi}, click: accessedUI">UI</a>
                </div>
            </div>
            <iframe id="api-frame" frameBorder="0" width="100%" height="100%" data-bind="attr: {src: documentation.currentDocumentation().iframeUrl}"></iframe>
        </div><!--/span-->
    </div><!--/row-->
</div><!--/.fluid-container-->

<%@include file="footer.jsp"%>
<script src="/static/js/documentationmodel.js"></script>
<script src="/static/js/dashboardmodel.js"></script>
<script src="/static/js/documentationdashboardmodel.js"></script>
<script src="/static/js/common.js"></script>
<script src="/static/js/viewdashboard.js"></script>

</body>
</html>
