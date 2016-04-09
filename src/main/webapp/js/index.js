$(document).ready(function() {
    adjustSize();
    loadDocumentation(function(documentation) {
        var viewModel = new DocumentationCollectionViewModel(documentation);

        if(viewModel.savedDocumentation().length > 0) {
            viewModel.currentDocumentation = ko.observable(viewModel.savedDocumentation()[0]);
        } else {
            viewModel.currentDocumentation = ko.observable(documentation[0]);
        }
        ko.applyBindings(viewModel);
    });
});
