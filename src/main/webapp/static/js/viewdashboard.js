$(document).ready(function() {
    adjustSize();
    loadDocumentation(function(documentation) {
        var dashboardId = window.location.search.match(/dashboardId=([^&]+)/)[1];
        loadDashboard(dashboardId, function(dashboard) {
            var viewModel = new DocumentationDashboardViewModel(documentation, dashboard);

            if(viewModel.dashboardDocumentation() && viewModel.dashboardDocumentation().length > 0) {
                viewModel.documentation.currentDocumentation(viewModel.dashboardDocumentation()[0]);
                $.get("/accessed?url=" + viewModel.documentation.currentDocumentation().displaySwaggerUi());
            }

            ko.applyBindings(viewModel);
        });
    });
});

function accessedUI(data, event) {
    if(event && event.type == "click") {
        $.get("/accessed?url=" + data.documentation.currentDocumentation().displaySwaggerUi());
    }
    return true;
}

function accessedSpec(data, event) {
    if(event && event.type == "click") {
        $.get("/accessed?url=" + data.documentation.currentDocumentation().displaySwaggerSpec());
    }
    return true;
}