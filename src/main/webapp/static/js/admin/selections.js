var cid = request("cid");

// Supported lanagues array.
var languages;

// Key-value to save names.
var names;

var modifyingCid;

$(document).ready(function () {

    checkAdminSession(function () {
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

});

/**
 * Reload selections.
 */
function loadSelections() {
    names = {};
    SelectionManager.getAll(function (selections) {
        // Clear all selections when selection is refreshed.
        $("#selections-list tbody").mengularClear();

        for (var i in selections) {
            var selection = selections[i];
            console.log(selection);
            names[selection.sid] = selection.name;

            $("#selection-list tbody").mengular(".selection-list-template", {
                sid: selection.sid,
                createAt: selection.createAt.format(DATE_HOUR_MINUTE_SECOND_FORMAT),
                identifier: selection.identifier,
                rev: selection.rev,
                icon: selection.icon,
                active: selection.active ? "Actived" : "Not Active"
            });

        }
    });
}