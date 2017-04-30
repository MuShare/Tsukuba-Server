$(document).ready(function () {

    checkAdminSession(function () {
        loadCategories();
    });
    
    $("#create-worker-submit").click(function () {
        var identifier = $("#create-worker-identifier").val();
        var validate = true;
        if (identifier == "") {
            $("#create-worker-identifier").parent().parent().addClass("has-error");
            validate = false;
        } else {
            $("#create-worker-identifier").parent().parent().removeClass("has-error");
        }
        if (validate) {
            CategoryManager.createCategory(identifier, function(success) {
                if (!success) {
                    location.href = "session.html";
                    return;
                }
                loadCategories();
                $("#create-category-modal").modal("hide");
            });
        }

    });

    $("#create-category-modal").on("hidden.bs.modal", function (e) {
        $("#create-category-form .form-group input").val("");
        $("#create-category-form .form-group").removeClass("has-error");
    });

});

function loadCategories() {
    CategoryManager.getAll(function (categories) {
        $("#category-list tbody").mengularClear();

        for (var i in categories) {
            var category = categories[i];
            $("#category-list tbody").mengular(".category-list-template", {
                cid: category.cid,
                createAt: category.createAt.format(DATE_HOUR_MINUTE_SECOND_FORMAT),
                identifier: category.identifier
            });

            $("#" + category.cid + " .category-list-enable input").bootstrapSwitch({
                state: category.enable
            }).on("switchChange.bootstrapSwitch", function (event, state) {
                var cid = $(this).mengularId();
                CategoryManager.enable(cid, state);
            });
        }
    });
}