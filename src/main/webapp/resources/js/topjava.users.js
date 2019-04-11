// $(document).ready(function () {
$(function () {
    makeEditable({
            ajaxUrl: "ajax/admin/users/",
            datatableApi: $("#datatable").DataTable({
                "paging": false,
                "info": true,
                "columns": [
                    {
                        "data": "name"
                    },
                    {
                        "data": "email"
                    },
                    {
                        "data": "roles"
                    },
                    {
                        "data": "enabled"
                    },
                    {
                        "data": "registered"
                    },
                    {
                        "defaultContent": "Edit",
                        "orderable": false
                    },
                    {
                        "defaultContent": "Delete",
                        "orderable": false
                    }
                ],
                "order": [
                    [
                        0,
                        "asc"
                    ]
                ]
            })
        }
    );
    $(":checkbox").change(function () {
        let checked = this.checked;
        let checkBox = $(this);
        $.ajax({
            url: context.ajaxUrl + "?id=" + checkBox.closest("tr").attr("id") + "&checked=" + checked,
            type: "PUT"
        }).done(function () {
            var s1 = checked ? "notEnabled" : "enabled";
            var s2 = checked ? "enabled" : "notEnabled";
            checkBox.closest("tr").removeClass(s1).addClass(s2);
            successNoty(checked ? "Enabled" : "Disabled");
        }).fail(function (jqXHR) {
            checkBox.prop('checked', !checked);
            failNoty(jqXHR);
        });

    });
});

function updateTable() {
    $.get(context.ajaxUrl, function (data) {
        redrawTable(data);
    });
}