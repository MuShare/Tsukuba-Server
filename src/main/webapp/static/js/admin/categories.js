// Supported lanagues array.
var languages;

// Key-value to save names.
var names;

var modifyingCid;

$(document).ready(function () {

    checkAdminSession(function () {
        loadCategories();

        // Load all supported lanagues from server.
        ConfigManager.languages(function (_languages) {
            languages = _languages;
        });

    });

    // Create new category
    $("#create-category-submit").click(function () {
        var identifier = $("#create-worker-identifier").val();
        var validate = true;
        if (identifier == "") {
            $("#create-category-identifier").parent().parent().addClass("has-error");
            validate = false;
        } else {
            $("#create-category-identifier").parent().parent().removeClass("has-error");
        }
        if (validate) {
            CategoryManager.createCategory(identifier, function (result) {
                if (result == Result.SessionError.name) {
                    location.href = "session.html";
                    return;
                }
                loadCategories();

                $("#create-category-modal").modal("hide");
            });
        }

    });

    // Empty create category form when modal is hidden.
    $("#create-category-modal").on("hidden.bs.modal", function (e) {
        $("#create-category-form .form-group input").val("");
        $("#create-category-form .form-group").removeClass("has-error");
    });

    // Modify category name.
    $("#modify-name-submit").click(function () {
        var name = {};
        for (var i in languages) {
            var lan = languages[i];
            name[lan] = $("#" + lan + " input").val();
        }
        CategoryManager.modifyName(modifyingCid, JSON.stringify(name), function (result) {
            if (result == Result.SessionError.name) {
                location.href = "session.html";
                return;
            }
            $("#modify-name-modal").modal("hide");
            $.messager.popup("Modify name successfully!");
        });
    });

});

/**
 * Reload categories.
 */
function loadCategories() {
    names = {};
    CategoryManager.getAll(function (categories) {
        // Clear all category when category is refreshed.
        $("#category-list tbody").mengularClear();

        for (var i in categories) {
            var category = categories[i];
            names[category.cid] = category.name;

            $("#category-list tbody").mengular(".category-list-template", {
                cid: category.cid,
                createAt: category.createAt.format(DATE_HOUR_MINUTE_SECOND_FORMAT),
                identifier: category.identifier
            });

            // Show and modify category name.
            $("#" + category.cid + " .category-list-name i").click(function () {
                modifyingCid = $(this).mengularId();
                // Clear all lan-name form.
                $("#modify-name-form").mengularClear();
                for (var i in languages) {
                    var lan = languages[i];
                    var name = names[modifyingCid];
                    $("#modify-name-form").mengular(".modify-name-template", {
                        lan: lan,
                        name: name == null ? "" : name[lan]
                    });
                }
                $("#modify-name-modal").modal("show");
            });

            // Show category icon update modal.
            $("#" + category.cid + " .category-list-icon").click(function () {
                modifyingCid = $(this).mengularId();

                $("#upload-icon").fileupload({
                    autoUpload: true,
                    url: "/upload/icon/category?cid=" + modifyingCid,
                    dataType: "json",
                    acceptFileTypes: /^image\/(gif|jpeg|png)$/,
                    done: function (e, data) {
                        console.log(data.result);

                    },
                    progressall: function (e, data) {

                    }
                });

                $("#upload-icon-modal").modal("show");
            });

            // Set enable state of a category
            $("#" + category.cid + " .category-list-enable input").bootstrapSwitch({
                state: category.enable
            }).on("switchChange.bootstrapSwitch", function (event, state) {
                var cid = $(this).mengularId();
                CategoryManager.enable(cid, state);
            });

            // Remoce category.
            $("#" + category.cid + " .category-list-remove i").click(function () {
                var cid = $(this).mengularId();
                var identifier = $("#" + cid + " .category-list-identifier").text();
                $.messager.confirm("Warning", "Are you sure to remove this category " + identifier + " ?", function () {
                    CategoryManager.removeCategory(cid, function (result) {
                        if (result == Result.SessionError.name) {
                            location.href = "session.html";
                            return;
                        }
                        if (result == Result.CategoryRemoveNotAllow.name) {
                            $.messager.popup(Result.CategoryRemoveNotAllow.message);
                            return;
                        }
                        $.messager.popup("Remove " + identifier + " successfully!");
                        $("#" + cid).fadeOut().remove();
                    });
                });
            });
        }
    });
}