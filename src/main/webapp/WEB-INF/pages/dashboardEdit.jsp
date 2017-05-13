
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
            <div>
                <h2 data-bind="text: editText"></h2>
                <form action="#">
                    <div class="form-group">
                        <label for="dashboardName">Name</label>
                        <input id="dashboardName" class="form-control" type="text" data-bind="textInput: dashboard.name">
                    </div>
                    <div class="form-group">
                        <label for="dashboardDescription">Description</label>
                        <textarea class="form-control"  id="dashboardDescription" data-bind="textInput: dashboard.description"></textarea>
                    </div>
                </form>
                <h4>Services</h4>
                <ul class="list-group" data-bind="foreach: dashboardDocumentation">
                    <li class="list-group-item"><span data-bind="text: displayName"></span><a href="#" data-bind='click: $parent.removeDocumentation' class="pull-right glyphicon glyphicon-remove action negative-action" data-toggle='tooltip' title='Remove'></a></li>
                </ul>
            </div>
            <div class="pull-right">
                <button data-bind='click: saveDashboard' class="btn btn-primary glyphicon glyphicon-floppy-disk" data-toggle='tooltip' title='Save'></button>
            </div>
        </div>
    </div>
    <div class="row-fluid">
        <div class="col-md-12 well">
            <div class="pull-left">
                <h4>Add Services</h4>
            </div>
            <form action="#" class="pull-right form-inline">
                <div class="form-group">
                    <select class="form-control" data-bind="options: documentation.availableCategories, value: documentation.categoryFilter"></select>
                </div>
                <div class="form-group">
                    <select class="form-control" data-bind="options: documentation.availableEnvironments, value: documentation.environmentFilter"></select>
                </div>
                <input data-bind="textInput: documentation.searchFilter" size="35" type="text" class="form-control" id="searchServices" placeholder="Search">
            </form>
        </div>
    </div>
    <div class="row-fluid">
        <table class="table table-striped">
            <thead>
            <tr>
                <th>Service</th>
                <th>Description</th>
                <th>Environment</th>
                <th>Category</th>
                <th>URL</th>
                <th>Swagger Spec</th>
                <th>Swagger UI</th>
                <th>Add</th>
            </tr>
            </thead>
            <tbody data-bind="foreach: documentation.searchDocumentation">
            <tr>
                <td data-bind="text: serviceName">User Activity Service</td>
                <td data-bind="text: serviceDescription">Processes and retrieves aggregated and raw user activity data</td>
                <td data-bind="text: environment">local</td>
                <td data-bind="text: category">Matching</td>
                <td><a target="_blank" data-bind="text: displayHost, attr: {href: displayHost}"></a></td>
                <td><a target="_blank" class="glyphicon glyphicon-link" data-bind="attr: {href: displaySwaggerSpec}"></a></td>
                <td><a target="_blank" class="glyphicon glyphicon-link" data-bind="attr: {href: displaySwaggerUi}"></a></td>
                <td>
                    <!-- ko ifnot: $parent.isSaved($data) -->
                    <a href="#" data-bind="click: $parent.addDocumentation" class="glyphicon glyphicon-plus action positive-action" data-toggle='tooltip' title='Add'></a>
                    <!-- /ko -->
                </td>
            </tr>
            </tbody>
        </table>
    </div><!--/row-->

</div><!--/.fluid-container-->

<%@include file="footer.jsp"%>
<script src="/static/js/dashboardmodel.js"></script>
<script src="/static/js/documentationmodel.js"></script>
<script src="/static/js/documentationdashboardmodel.js"></script>
<script src="/static/js/editdashboard.js"></script>

</body>
</html>
