$(document).ready(function() {
    loadDashboards(function(dashboards) {
        ko.applyBindings(new DashboardCollectionViewModel(dashboards));
    });
});