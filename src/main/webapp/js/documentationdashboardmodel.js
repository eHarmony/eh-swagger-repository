/**
 * The root knockout js model that controls the functionality for the page
 * @param jsonDoc the json returned from the api call
 * @constructor
 */
function DocumentationDashboardViewModel(documentation, dashboard) {
    var self = this;
    this.dashboardDocumentation = ko.observableArray(filterDocumentationByIds(dashboard.documentationIds, documentation));
    this.documentation = new DocumentationCollectionViewModel(documentation);
    this.dashboard = new Dashboard(dashboard);

    this.editText = ko.computed(function() {
        if(this.dashboard.id) {
            return "Edit Dashboard";
        } else {
            return "Create Dashboard";
        }
    }, this);

    this.isSaved = function(documentation) {
        return self.dashboardDocumentation.indexOf(documentation) > -1;
    };

    this.addDocumentation = function(documentation, event) {
        if (event && event.type == "click") {
            self.dashboardDocumentation.push(documentation);
            self.dashboard.documentationIds.push(documentation.id);
        }
    };

    this.removeDocumentation = function(documentation, event) {
        if (event && event.type == "click") {
            self.dashboardDocumentation.remove(documentation);

            var index = self.dashboard.documentationIds.indexOf(documentation.id);
            if(index > -1) {
                self.dashboard.documentationIds.splice(index, 1);
            }
        }
    };

    this.saveDashboard = function(dashboard, event) {
        if (event && event.type == "click") {
            self.dashboard.id = createDashboardId(self.dashboard.name);
            saveDashboard(self.dashboard, function() {
                window.location.href = "/dashboardview.html?dashboardId=" + self.dashboard.id;
            });
        }
    };
}