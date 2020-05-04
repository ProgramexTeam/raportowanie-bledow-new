$(".plus-button").click(function () {
    var selects = $(".input-element select:not(:first-of-type)");
    $(selects).each(function (index) {
        if($(this).css("display") == "none") {
            $(this).css("display", "inline-block");
            return false;
        }
    });
});
$(".minus-button").click(function () {
    var selects = $(".input-element select:not(:first-of-type)").toArray().reverse();
    $(selects).each(function () {
        if($(this).css("display") == "inline-block") {
            $(this).css("display", "none");
            return false;
        }
    });
});