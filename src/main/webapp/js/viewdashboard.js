$(document).ready(function() {
    adjustSize();
    loadDocumentation(function(documentation) {
        var dashboardId = window.location.search.match(/dashboardId=([^&]+)/)[1];
        loadDashboard(dashboardId, function(dashboard) {
            var viewModel = new DocumentationDashboardViewModel(documentation, dashboard);

            if(viewModel.dashboardDocumentation() && viewModel.dashboardDocumentation().length > 0) {
                viewModel.documentation.currentDocumentation(viewModel.dashboardDocumentation()[0]);
            }

            ko.applyBindings(viewModel);
        });
    });
});