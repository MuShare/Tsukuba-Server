var config = null;

$(document).ready(function () {

    checkAdminSession(function () {
        ConfigManager.getConfigObject(function (_config) {
            config = _config;

            for (var itemName in config) {
                var item = config[itemName];

                $("#config-list").mengular(".config-list-template", {
                    itemName: itemName
                });

                for (var attributeName in item) {
                    $("#" + itemName).mengular(".attribute-list-template", {
                        attributeName: attributeName,
                        attributeValue: item[attributeName]
                    });
                }
            }
        });
    });

    // Refresh config file.
    $("#refresh-config").click(function () {
        ConfigManager.refreshConfig(function (success) {
            location.reload();
        });
    });

    // Save config.
    $("#save-config").click(function (event) {
        if (config == null) {
            return;
        }

        for (var itemName in config) {
            var item = config[itemName];
            for (var attributeName in item) {
                item[attributeName] = $("#" + attributeName + " input").val();
            }
        }

        ConfigManager.saveConfig(JSON.stringify(config), function (success) {
            if (success) {
                $.messager.popup("Saved!");
            } else {
                $.messager.popup("Save fail.");
            }
        });
    });

});