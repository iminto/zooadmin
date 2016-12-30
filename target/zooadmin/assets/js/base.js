$(function() {

    /**
     * 初始化侧栏链接和样式布局
     */
    var menuLink = $(".admin-parent").eq(0).find("ul").addClass("am-in").find("li a").eq(0).addClass("am-active").attr("href");
    $("#iframe_default").attr("src", menuLink);

    /**
     * 点击菜单，更换iframe地址和移除别的展开菜单
     */
    $(".menu-link").on("click", function() {
        $("#iframe_default").attr("src", $(this).attr("href"));
        $(".menu-link").removeClass("am-active")
        $(this).addClass("am-active");
        $(this).parent().parent().parent().siblings().find("ul").removeClass("am-in")
        return false;
    })
    $(".admin-parent").on("click", function() {
        $(this).siblings().find("ul").removeClass("am-in");
    })
    /* 点击菜单，更换iframe地址和移除别的展开菜单结束 */

    /**
     * 后台菜单功能JS
     */
    if ($("#menu-pid").val() != '-1' && $("#menu-pid").val() != '0') {
        $("#menu-url").show();
    } else {
        $("#menu-url").hide();
    }

    $("#menu-pid").on("change", function() {
        if ($("#menu-pid").val() != '-1' && $("#menu-pid").val() != '0') {
            $("#menu-url").show();
        } else {
            $("#menu-url").hide();
        }
    })
    /* 后台菜单功能JS结束 */
    $("#iframe_default").height($(window).height() - 59);

    $(window).resize(function() {
        $("#iframe_default").height($(window).height() - 59);
    });
})