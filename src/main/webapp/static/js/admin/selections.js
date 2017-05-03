var cid = request("cid");

// Supported lanagues array.
var languages;

// Key-value to save names.
var names;

var modifyingSid;

$(document).ready(function () {

    checkAdminSession(function () {

        CategoryManager.get(cid, function (category) {
            $("#category-name").text(category.identifier);
        });

        loadSelections();

        // Load all supported lanagues from server.
        ConfigManager.languages(function (_languages) {
            languages = _languages;
        });

    });

    // Create new selection
    $("#create-selection-submit").click(function () {
        var identifier = $("#create-selection-identifier").val();
        var validate = true;
        if (identifier == "") {
            $("#create-selection-identifier").parent().parent().addClass("has-error");
            validate = false;
        } else {
            $("#create-selection-identifier").parent().parent().removeClass("has-error");
        }
        if (validate) {
            SelectionManager.create(identifier, cid, function (result) {
                if (result == Result.SessionError.name) {
                    location.href = "session.html";
                    return;
                }
                loadSelections();

                $("#create-selection-modal").modal("hide");
            });
        }

    });

    // Empty create selection form when modal is hidden.
    $("#create-selection-modal").on("hidden.bs.modal", function (e) {
        // Reset modal
        $("#create-selection-form .form-group input").val("");
        $("#create-selection-form .form-group").removeClass("has-error");
    });

    // Modify selection name.
    $("#modify-name-submit").click(function () {
        var name = {};
        for (var i in languages) {
            var lan = languages[i];
            name[lan] = $("#" + lan + " input").val();
        }
        SelectionManager.modifyName(modifyingSid, JSON.stringify(name), function (result) {
            if (result == Result.SessionError.name) {
                location.href = "session.html";
                return;
            }
            names[modifyingSid] = name;
            $("#modify-name-modal").modal("hide");
            $.messager.popup("Modify name successfully!");
        });
    });

});

/**
 * Reload selections.
 */
function loadSelections() {
    names = {};
    SelectionManager.getByCid(cid, function (selections) {
        // Clear all selections when selection is refreshed.
        $("#selection-list tbody").mengularClear();

        for (var i in selections) {
            var selection = selections[i];
            names[selection.sid] = selection.name;

            $("#selection-list tbody").mengular(".selection-list-template", {
                sid: selection.sid,
                createAt: selection.createAt.format(DATE_HOUR_MINUTE_SECOND_FORMAT),
                identifier: selection.identifier,
                rev: selection.rev,
                icon: selection.icon,
                active: selection.active ? "Actived" : "Not Active"
            });

            // Show and modify selection name.
            $("#" + selection.sid + " .selection-list-name i").click(function () {
                modifyingSid = $(this).mengularId();
                // Clear all lan-name form.
                $("#modify-name-form").mengularClear();
                for (var i in languages) {
                    var lan = languages[i];
                    var name = names[modifyingSid];
                    $("#modify-name-form").mengular(".modify-name-template", {
                        lan: lan,
                        name: name == null ? "" : name[lan]
                    });
                }
                $("#modify-name-modal").modal("show");
            });

            // Set enable state of a selection
            $("#" + selection.sid + " .selection-list-enable input").bootstrapSwitch({
                state: selection.enable
            }).on("switchChange.bootstrapSwitch", function (event, state) {
                var sid = $(this).mengularId();
                SelectionManager.enable(sid, state);
            });

            // Selection active related.
            $("#" + selection.sid + " .selection-list-active button")
                .addClass(selection.active ? "btn-success" : null)
                .click(function () {
                    var sid = $(this).mengularId();
                    var identifier = $("#" + sid + " .selection-list-identifier").text();
                    $.messager.confirm("Tip", "Are you sure to activie the selection " + identifier + "? Actived selection cannot turn back.", function () {
                        SelectionManager.active(sid, function (result) {
                            if (result == Result.SessionError.name) {
                                location.href = "session.html";
                                return;
                            }
                            SelectionManager.get(sid, function (selection) {
                                $("#" + sid + " .selection-list-rev").text(selection.rev);
                                $("#" + selection.sid + " .selection-list-active button")
                                    .text("Actived")
                                    .addClass("btn-success");
                            });
                        });
                    });
                });

            // Remove selection.
            $("#" + selection.sid + " .selection-list-remove i").click(function () {
                var sid = $(this).mengularId();
                var identifier = $("#" + sid + " .selection-list-identifier").text();
                $.messager.confirm("Warning", "Are you sure to remove the selection " + identifier + " ?", function () {
                    SelectionManager.remove(sid, function (result) {
                        if (result == Result.SessionError.name) {
                            location.href = "session.html";
                            return;
                        }
                        if (result == Result.SelectionRemoveNotAllow.name) {
                            $.messager.popup(Result.SelectionRemoveNotAllow.message);
                            return;
                        }
                        $.messager.popup("Remove " + identifier + " successfully!");
                        $("#" + sid).fadeOut().remove();
                    });
                });
            });
        }
    });
}