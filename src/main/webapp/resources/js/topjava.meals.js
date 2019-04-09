$(function () {
    makeEditable({
            entity: "meal",
            ajaxUrl: "ajax/meals/",
            formColumns: ["dateTime", "description", "calories"],
            datatableApi: $("#datatable").DataTable({
                "paging": false,
                "info": true,
                "columns": [
                    {
                        "data": "dateTime"
                    },
                    {
                        "data": "description"
                    },
                    {
                        "data": "calories"
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
                        "desc"
                    ]
                ]
            })
        }
    );
    $("#filter").click(function () {
        getFiltered();
    });
    $("#clear").click(function () {
        clearForm();
    });
});

function getFiltered() {
    $.ajax({
        type: "GET",
        url: context.ajaxUrl + "filter",
        data: $("#filterParams").serialize(),
        success: function (data) {
            redrawTable(data);
        }
    });
}

function clearForm() {
    $("#filterParams").find(":input").val("");
    updateTable();
}

function updateTable() {
    $.get(context.ajaxUrl + "?startDate=" + $("input[name=startDate]").val() + "&endDate=" + $("input[name=endDate]").val()
        + "&startTime=" + $("input[name=startTime]").val() + "&endTime=" + $("input[name=endTime]").val(), function (data) {
        context.datatableApi.clear().rows.add(data).draw();
    });
}