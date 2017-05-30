
<!DOCTYPE html>
<html lang="en">
<head>
    <%@include file="./header.jsp"%>
</head>

<body>

<%@include file="./navigation.jsp"%>

<div class="container-fluid">
    <div class="row-fluid">
        <div id="sidebar" class="col-md-3 well well-sm sidebar">
            <div id="sidebar-list">
                <h4>Your Services<a id="hide-link" href="#" onclick="toggleSidebar()" class="glyphicon glyphicon-arrow-left pull-right action" data-toggle="tooltip" title="Hide"></a></h4>
                <ul class="nav" data-bind="foreach: savedDocumentation">
                    <li class="col-md-10">
                        <a href="#" data-bind="click: $parent.setCurrentDocumentation, html: displayName"></a>
                    </li>
                    <li class="col-md-2">
                        <a class="glyphicon glyphicon-remove action negative-action"href="#" data-bind='click: $parent.removeDocumentation' data-toggle='tooltip' title='Remove'></a>
                    </li>
                </ul>
                <br/>
                <h4>Search Services</h4>
                <form action="#">
                    <input data-bind="textInput: searchFilter" class="form-control" placeholder="Search"type="text">
                </form>
                <ul class="nav nav-list" data-bind="foreach: searchDocumentation">
                    <li class="col-md-10" data-bind="css: {active: $parent.isActive}">
                        <a href="#" data-bind="click: $parent.setCurrentDocumentation, html: displayName"></a>
                    </li>
                    <li class="col-md-2">
                        <!-- ko ifnot: $parent.isSaved($data) -->
                        <a class="glyphicon glyphicon-plus action positive-action" href="#" data-bind='click: $parent.saveDocumentation' data-toggle='tooltip' title='Save'></a>
                        <!-- /ko -->
                    </li>
                </ul>
            </div>
        </div>
        <div id="sidebar-show" class="col-md-1 well well-sm sidebar arrow-show">
            <h4><a href="#" onclick="toggleSidebar()" class="glyphicon glyphicon-arrow-right action" data-toggle="tooltip" title="Show"></a></h4>
        </div>
        <div id="api-frame-container" class="col-md-9">
            <div id="api-link" class="pull-right">
                <h4 data-bind="text: currentDocumentation().environment.toUpperCase()"></h4>
                <div class="btn-group">
                    <a class="btn btn-info" target="_blank" data-bind="attr: {href: currentDocumentation().displaySwaggerSpec}, click: accessedSpec">Spec</a>
                </div>
                <div class="btn-group">
                    <a class="btn btn-info" target="_blank" data-bind="attr: {href: currentDocumentation().displaySwaggerUi}, click: accessedUI">UI</a>
                </div>
            </div>
            <iframe id="api-frame" frameBorder="0" width="100%" height="100%" data-bind="attr: {src: currentDocumentation().iframeUrl}"></iframe>
        </div><!--/span-->
    </div><!--/row-->
</div><!--/.fluid-container-->

<%@include file="./footer.jsp"%>
<script src="/static/js/documentationmodel.js"></script>
<script src="/static/js/common.js"></script>
<script src="/static/js/mydashboard.js"></script>

</body>
</html>
