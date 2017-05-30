$(document).ready(function() {
    adjustSize();
    loadDocumentation(function(documentation) {
        var viewModel = new DocumentationCollectionViewModel(documentation);

        if(viewModel.savedDocumentation().length > 0) {
            viewModel.currentDocumentation = ko.observable(viewModel.savedDocumentation()[0]);
        } else {
            viewModel.currentDocumentation = ko.observable(documentation[0]);
        }

        $.get("/accessed?url=" + viewModel.currentDocumentation().displaySwaggerUi());
        ko.applyBindings(viewModel);
    });
});

function accessedUI(data, event) {
    if(event && event.type == "click") {
        $.get("/accessed?url=" + data.currentDocumentation().displaySwaggerUi());
    }
    return true;
}

function accessedSpec(data, event) {
    if(event && event.type == "click") {
        $.get("/accessed?url=" + data.currentDocumentation().displaySwaggerSpec());
    }
    return true;
}
