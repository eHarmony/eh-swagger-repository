$(document).ready(function() {
    loadDocumentation(function(documentation) {
        var dashboardQuery = window.location.search.match(/dashboardId=([^&]+)/);
        if(dashboardQuery && dashboardQuery.length > 0) {
            loadDashboard(dashboardQuery[1], function(dashboard) {
                ko.applyBindings(new DocumentationDashboardViewModel(documentation, dashboard));
            });
        } else {
            ko.applyBindings(new DocumentationDashboardViewModel(documentation, {documentationIds : []}));
        }
    });
});