$(document).ready(function() {
    loadDocumentation(function(documentation) {
        ko.applyBindings(new DocumentationCollectionViewModel(documentation));
    });
});