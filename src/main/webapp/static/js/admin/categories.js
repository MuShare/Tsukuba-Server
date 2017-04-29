$(document).ready(function () {

    checkAdminSession(function () {
        CategoryManager.getAll(function (categories) {
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
    });

});