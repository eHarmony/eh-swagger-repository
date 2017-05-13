
<!DOCTYPE html>
<html lang="en">
<head>
    <%@include file="./header.jsp"%>
</head>

<body>

<%@include file="./navigation.jsp"%>

<div class="container-fluid">
    <div class="row-fluid">
        <div class="col-md-12 well">
            <div class="container pull-left">
                <h3>Search Swagger Enabled Services</h3>
                <span>A directory of services that have published their swagger specifications.</span>
            </div>
            <form action="#" class="pull-right form-inline search-container">
                <div class="form-group">
                    <select class="form-control" data-bind="options: availableCategories, value: categoryFilter"></select>
                </div>
                <div class="form-group">
                    <select class="form-control" data-bind="options: availableEnvironments, value: environmentFilter"></select>
                </div>
                <input data-bind="textInput: searchFilter" size="35" type="text" class="form-control" id="searchServices" placeholder="Search">
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
                    <th>Save</th>
                </tr>
            </thead>
            <tbody data-bind="foreach: searchDocumentation">
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
                        <a href="#" data-bind="click: $parent.saveDocumentation" class="glyphicon glyphicon-plus action positive-action" data-toggle='tooltip' title='Save'></a>
                        <!-- /ko -->
                    </td>
                </tr>
            </tbody>
        </table>
    </div><!--/row-->

</div><!--/.fluid-container-->

<%@include file="./footer.jsp"%>
<script src="/static/js/documentationmodel.js"></script>
<script src="/static/js/services.js"></script>

</body>
</html>
