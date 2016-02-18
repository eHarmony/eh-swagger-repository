var SAVED_SERVICES_COOKIE = "saved-services";
var API_PATH = "/api/1.0/documentation";

/**
 * The root knockout js model that controls the functionality for the page
 * @param jsonDoc the json returned from the api call
 * @constructor
 */
function DocumentationCollectionViewModel(documentationArray) {
    var self = this;
    this.allDocumentation = ko.observableArray(documentationArray);

    this.searchFilter = ko.observable();
    this.categoryFilter = ko.observable();
    this.environmentFilter = ko.observable();

    this.availableCategories = ko.computed(function() {
        var categories = ["All Categories"];
        for(var i = 0; i < documentationArray.length; i++) {
            var category = documentationArray[i].category;
            if(!(categories.indexOf(category) >= 0)) {
                categories.push(category);
            }
        }
        return categories;
    }, this);

    this.availableEnvironments = ko.computed(function() {
        var environments = ["All Environments"];
        for(var i = 0; i < documentationArray.length; i++) {
            var environment = documentationArray[i].environment;
            if(!(environments.indexOf(environment) >= 0)) {
                environments.push(environment);
            }
        }
        return environments;
    }, this);

    this.searchDocumentation = ko.computed(function() {
        var filters = [];
        var category;
        var environment;
        if(this.searchFilter()) {
            filters = this.searchFilter().toLowerCase().split(" ");
        }
        if(this.categoryFilter() != "All Categories") {
            category = this.categoryFilter();
        }
        if(this.environmentFilter() != "All Environments") {
            environment = this.environmentFilter();
        }
        return ko.utils.arrayFilter(this.allDocumentation(), function(doc) {
            var match = true;
            for(var i = 0; i < filters.length; i++) {
                match &= freeTextSearch(doc, filters[i]);
            }
            if(category) {
                match &= (doc.category == category);
            }
            if(environment) {
                match &= (doc.environment == environment);
            }

            return match;
        }).slice(0,50);
    }, this);

    var saved = loadSavedDocumentation(documentationArray);

    this.savedDocumentation = ko.observableArray(saved);

    this.currentDocumentation = ko.observable();

    this.setCurrentDocumentation = function(data, event) {
        if(event && event.type == "click") {
            self.currentDocumentation(data);
        }
    };

    this.saveDocumentation = function(documentation, event) {
        if(event && event.type == "click") {
            if (self.savedDocumentation.indexOf(documentation) < 0) {
                var saveDoc = self.savedDocumentation();
                saveDoc.push(documentation);
                storeSavedDocumentation(saveDoc);
                self.savedDocumentation.valueHasMutated();

            }
        }
    };

    this.removeDocumentation = function(documentation, event) {
        if(event && event.type == "click") {
            if (self.savedDocumentation.indexOf(documentation) > -1) {
                self.savedDocumentation.remove(documentation);
                storeSavedDocumentation(self.savedDocumentation());
            }
        }
    };

    this.isSaved = function(documentation) {
        return self.savedDocumentation.indexOf(documentation) > -1;
    };

    this.isActive = function(doc) {
        return self.currentDocumentation().displayName() == doc.displayName();
    }
}

/**
 * The base knockout js model for a documentation object
 * @param jsonDoc
 * @constructor
 */
function Documentation(jsonDoc) {
    this.id = jsonDoc.id;
    this.serviceName = jsonDoc.serviceName;
    this.serviceDescription = jsonDoc.serviceDescription;
    this.serviceHost = jsonDoc.serviceHost;
    this.swaggerUiUrl = jsonDoc.swaggerUiUrl;
    this.swaggerSpecUrl = jsonDoc.swaggerSpecUrl;
    this.environment = jsonDoc.environment;
    this.category = jsonDoc.category;
    this.displayName = ko.computed(function() {
        return this.serviceName + " (" + this.environment + ")";
    }, this);
    this.displayHost = ko.computed(function() {
        var displayHost = this.serviceHost;
        if(displayHost.indexOf("http://") < 0) {
            displayHost = "http://" + displayHost;
        }
        return displayHost;
    }, this);
    this.displaySwaggerSpec = ko.computed(function() {
        return this.displayHost() + this.swaggerSpecUrl;
    }, this);
    this.displaySwaggerUi = ko.computed(function() {
        return this.displayHost() + this.swaggerUiUrl;
    }, this);
    this.iframeUrl = ko.computed(function() {
        var swaggerUI = this.displaySwaggerUi();
        if(swaggerUI.indexOf("?") > -1) {
            swaggerUI = swaggerUI + "&";
        } else {
            swaggerUI = swaggerUI + "?"
        }
        return swaggerUI +  "hideHeader=true";
    }, this);
}

/**
 * Takes the full list of saved documentation and pulls out just the service name and stores a comma separated list
 * to the 'savedServices' cookie.
 * @param savedDocumentation the fully qualified documentation model list
 */
function storeSavedDocumentation(savedDocumentation) {
    var serviceNames = [];
    for(var i = 0; i < savedDocumentation.length; i++) {
        serviceNames.push(savedDocumentation[i].displayName());
    }

    $.cookie(SAVED_SERVICES_COOKIE, encodeURIComponent(serviceNames.join(',')), {
        expires: 10000,
        path: "/"
    });
}

/**
 * Looks for the cookie for 'savedServices' that contains a comma separated list of service names. It searches through
 * the full list of services and compares the service names. If found, the documentation is added to the service list.
 * @param allDocumentation the complete list of fully qualified documentation models
 * @returns {Array} of saved fully qualified documentation models
 */
function loadSavedDocumentation(allDocumentation) {
    var savedServices = [];
    var savedServicesCookie = $.cookie(SAVED_SERVICES_COOKIE);
    if(savedServicesCookie) {
        var serviceNames = decodeURIComponent(savedServicesCookie).split(",");
        for(var i = 0; i < allDocumentation.length; i++) {
            if(serviceNames.indexOf(allDocumentation[i].displayName()) > -1) {
                savedServices.push(allDocumentation[i]);
            }
        }
    }
    return savedServices;
}

function filterDocumentationByIds(documentationIds, allDocumentation) {
    var filteredServices = [];
    for(var i = 0; i < allDocumentation.length; i++) {
        if(documentationIds.indexOf(allDocumentation[i].id) > -1) {
            filteredServices.push(allDocumentation[i]);
        }
    }
    return filteredServices;

}

/**
 * Searches for a match for the filter word within a documentation entry's service name, description
 * environment, category and host
 * @param doc
 * @param filter
 * @returns {boolean}
 */
function freeTextSearch(doc, filter) {
    return doc.serviceName.toLowerCase().indexOf(filter) >= 0 ||
        doc.serviceDescription.toLowerCase().indexOf(filter) >= 0 ||
        doc.environment.toLowerCase().indexOf(filter) >= 0 ||
        doc.category.toLowerCase().indexOf(filter) >= 0 ||
        doc.serviceHost.toLowerCase().indexOf(filter) >= 0;
}

/**
 * Loads all documentation from the documentation api and passes the json to the knockout js model
 */
function loadDocumentation(loadedCallback) {
    $.getJSON(API_PATH, function (jsonDoc) {
        var documentationArray = [];
        for (var i = 0; i < jsonDoc.length; i++) {
            documentationArray.push(new Documentation(jsonDoc[i]));
        }
        loadedCallback(documentationArray);
    });
}