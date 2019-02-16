$(function () {
    // create
    $("#create").ajaxForm(function (resp) {
        // reset form
        $("#create").trigger("reset");
        // create to-do
        createTodo(resp);
    });

    // search
    $("#search").on("input", function () {
        var done = $(this).closest("#search-w").find(".check");
        var isDone = done.hasClass("true");
        var searchVal = $(this).val();

        search(searchVal, done, isDone);
    });

    // change done flag
    $(".search-done").click(function () {
        var done = $(this).find(".check");
        done.toggleClass("true");
        var isDone = done.hasClass("true");
        var searchVal = $(this).closest("#search-w").find("#search").val();

        search(searchVal, done, isDone);
    });


    $(".to-do").not(".dummy").each(function(index, el) {
        init($(el));
    });
});

/**
 * Search function
 * @param search
 * @param done
 * @param isDone
 */
function search(search, done, isDone) {
    $.ajax({
        type: "POST",
        url: "search",
        data: {search: search, done: isDone},
        success: function (resp) {
            // remove all not matching
            $("#todo-list .to-do").not(".dummy").remove();
            $.each(resp.todos, function (index, el) {
                // insert all found
                createTodo(el);
            });
        },
        error: function () {
            // remove all
            $("#todo-list .to-do").not(".dummy").remove();
        }
    })
}

/**
 * Create the to-do
 * @param doto to-do
 */
function createTodo(doto) {
    // get the dummy element
    var dummy = $(".dummy");
    // create a clone
    dummy.clone().appendTo("#todo-list");

    // update the values for the new to-do
    dummy.removeClass("dummy");
    dummy.find(".title").text(doto.title);
    dummy.find(".dueDate").text(doto.dueDate);
    dummy.find(".done").addClass(doto.done)
    dummy.find(".id").val(doto.id);
    dummy.find(".description").val(doto.description);
    dummy.appendTo("#todo-list");

    init(dummy);
}

/**
 * register actions
 * @param el
 */
function init(el) {
    el.find(".edit").ajaxForm({
        error: function () {
            // error
        },
        success: function () {
            // success
        }
    });

    // update
    el.find(".update").click(function () {
        $(this).closest(".to-do").find(".edit").submit();
    });

    // delete
    el.find(".delete").click(function () {
        var el = $(this).closest(".to-do");
        var id = el.find(".id").val();
        $.post("delete", {id: id}, function () {
            el.remove();
        })
    });

    // done
    el.find(".done").click(function () {
        var el = $(this).closest(".to-do");
        var id = el.find(".id").val();
        var done = $("#search-w .search-done .check").hasClass("true");
        var isDone = el.find(".done").hasClass("true");
        if (!isDone) {
            $.post("do", {id: id}, function () {
                if (!done) {
                    el.remove();
                } else {
                    el.find(".done").toggleClass("true");
                }
            })
        }
    });
}