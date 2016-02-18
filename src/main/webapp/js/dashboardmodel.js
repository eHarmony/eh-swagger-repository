var DASHBOARD_API_PATH = "/api/1.0/dashboards";
var DASHBOARD_ID_API_PATH = "/api/1.0/dashboards/";


function DashboardCollectionViewModel(dashboardArray) {
    var self = this;
    this.allDashboards = ko.observableArray(dashboardArray);

    this.searchFilter = ko.observable();

    this.searchDashboards = ko.computed(function() {
        var filters = [];
        if(this.searchFilter()) {
            filters = this.searchFilter().toLowerCase().split(" ");
        }
        return ko.utils.arrayFilter(this.allDashboards(), function(doc) {
            var match = true;
            for(var i = 0; i < filters.length; i++) {
                match &= freeTextSearchDashboard(doc, filters[i]);
            }

            return match;
        }).slice(0,50);
    }, this);

}


/**
 * The base knockout js model for a dashboard object
 * @param jsonDoc
 * @constructor
 */
function Dashboard(jsonDoc) {
    this.name = jsonDoc.name;
    this.id = jsonDoc.id;
    this.description = jsonDoc.description;
    this.documentationIds = jsonDoc.documentationIds;
    this.dashboardLink = ko.computed(function() {
        return "/dashboardview.html?dashboardId=" + this.id;
    }, this);
    this.dashboardEditLink = ko.computed(function() {
        return "/dashboardedit.html?dashboardId=" + this.id;
    }, this);
}

/**
 * Searches for a match for the filter word within a documentation entry's service name, description
 * environment, category and host
 * @param doc
 * @param filter
 * @returns {boolean}
 */
function freeTextSearchDashboard(doc, filter) {
    return doc.name.toLowerCase().indexOf(filter) >= 0 ||
        doc.description.toLowerCase().indexOf(filter) >= 0;
}

/**
 * Loads all documentation from the documentation api and passes the json to the knockout js model
 */
function loadDashboards(loadedCallback) {
    $.getJSON(DASHBOARD_API_PATH, function(jsonDoc){
        var dashboardArray = [];
        for(var i = 0; i < jsonDoc.length; i++) {
            dashboardArray.push(new Dashboard(jsonDoc[i]));
        }
        loadedCallback(dashboardArray);
    });
}

/**
 * Loads all documentation from the documentation api and passes the json to the knockout js model
 */
function loadDashboard(dashboardId, loadedCallback) {
    $.getJSON(DASHBOARD_ID_API_PATH + dashboardId, function(jsonDoc){
        loadedCallback(new Dashboard(jsonDoc));
    });
}

function saveDashboard(dashboard, loadedCallback) {
    var jsonDashboard = ko.toJS(dashboard);
    delete jsonDashboard.dashboardLink;
    delete jsonDashboard.dashboardEditLink;
    $.ajax({
        type: 'PUT',
        dataType: 'json',
        url: DASHBOARD_ID_API_PATH + dashboard.id,
        data: ko.toJSON(jsonDashboard),
        headers: { "Content-Type" : "application/json"}
    }).done(function(data, status, thing) {
        loadedCallback();
    });
}

function createDashboardId(dashboardName) {
    return dashboardName.toLowerCase().replace(new RegExp(" ", 'g'), "_")
}