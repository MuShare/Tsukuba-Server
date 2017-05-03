var sid = request("sid");

// Supported lanagues array.
var languages;

// Key-value to save names.
var names;

var modifyingOid;

$(document).ready(function () {

    checkAdminSession(function () {

        SelectionManager.get(sid, function (selection) {
            $("#back").attr("href", "selections.html?cid=" + selection.cid);
            $("#selection-name").text(selection.identifier);
        });

        loadOptions();

        // Load all supported lanagues from server.
        ConfigManager.languages(function (_languages) {
            languages = _languages;
        });

    });

    // Create new option
    $("#create-option-submit").click(function () {
        var identifier = $("#create-option-identifier").val();
        var validate = true;
        if (identifier == "") {
            $("#create-option-identifier").parent().parent().addClass("has-error");
            validate = false;
        } else {
            $("#create-option-identifier").parent().parent().removeClass("has-error");
        }
        if (validate) {
            OptionManager.create(identifier, sid, function (result) {
                if (result == Result.SessionError.name) {
                    location.href = "session.html";
                    return;
                }
                loadOptions();

                $("#create-option-modal").modal("hide");
            });
        }

    });

    // Empty create option form when modal is hidden.
    $("#create-option-modal").on("hidden.bs.modal", function (e) {
        // Reset modal
        $("#create-option-form .form-group input").val("");
        $("#create-option-form .form-group").removeClass("has-error");
    });

    // Modify option name.
    $("#modify-name-submit").click(function () {
        var name = {};
        for (var i in languages) {
            var lan = languages[i];
            name[lan] = $("#" + lan + " input").val();
        }
        OptionManager.modifyName(modifyingOid, JSON.stringify(name), function (result) {
            if (result == Result.SessionError.name) {
                location.href = "session.html";
                return;
            }
            names[modifyingOid] = name;
            $("#modify-name-modal").modal("hide");
            $.messager.popup("Modify name successfully!");
        });
    });

});

/**
 * Reload options.
 */
function loadOptions() {
    names = {};
    OptionManager.getBySid(sid, function (options) {
        // Clear all options when option is refreshed.
        $("#option-list tbody").mengularClear();

        for (var i in options) {
            var option = options[i];
            names[option.oid] = option.name;

            $("#option-list tbody").mengular(".option-list-template", {
                oid: option.oid,
                createAt: option.createAt.format(DATE_HOUR_MINUTE_SECOND_FORMAT),
                identifier: option.identifier,
                rev: option.rev,
                icon: option.icon,
                active: option.active ? "Actived" : "Not Active"
            });

            // Show and modify option name.
            $("#" + option.oid + " .option-list-name i").click(function () {
                modifyingOid = $(this).mengularId();
                // Clear all lan-name form.
                $("#modify-name-form").mengularClear();
                for (var i in languages) {
                    var lan = languages[i];
                    var name = names[modifyingOid];
                    $("#modify-name-form").mengular(".modify-name-template", {
                        lan: lan,
                        name: name == null ? "" : name[lan]
                    });
                }
                $("#modify-name-modal").modal("show");
            });

            // Set enable state of a option
            $("#" + option.oid + " .option-list-enable input").bootstrapSwitch({
                state: option.enable
            }).on("switchChange.bootstrapSwitch", function (event, state) {
                var oid = $(this).mengularId();
                OptionManager.enable(oid, state);
            });

            // Option active related.
            $("#" + option.oid + " .option-list-active button")
                .addClass(option.active ? "btn-success" : null)
                .click(function () {
                    var oid = $(this).mengularId();
                    var identifier = $("#" + oid + " .option-list-identifier").text();
                    $.messager.confirm("Tip", "Are you sure to activie the option " + identifier + "? Actived option cannot turn back.", function () {
                        OptionManager.active(oid, function (result) {
                            if (result == Result.SessionError.name) {
                                location.href = "session.html";
                                return;
                            }
                            OptionManager.get(oid, function (option) {
                                $("#" + oid + " .option-list-rev").text(option.rev);
                                $("#" + option.oid + " .option-list-active button")
                                    .text("Actived")
                                    .addClass("btn-success");
                            });
                        });
                    });
                });

            // Remove option.
            $("#" + option.oid + " .option-list-remove i").click(function () {
                var oid = $(this).mengularId();
                var identifier = $("#" + oid + " .option-list-identifier").text();
                $.messager.confirm("Warning", "Are you sure to remove the option " + identifier + " ?", function () {
                    OptionManager.remove(oid, function (result) {
                        if (result == Result.SessionError.name) {
                            location.href = "session.html";
                            return;
                        }
                        if (result == Result.OptionRemoveNotAllow.name) {
                            $.messager.popup(Result.OptionRemoveNotAllow.message);
                            return;
                        }
                        $.messager.popup("Remove " + identifier + " successfully!");
                        $("#" + oid).fadeOut().remove();
                    });
                });
            });
        }
    });
}