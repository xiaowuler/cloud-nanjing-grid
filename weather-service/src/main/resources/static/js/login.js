var App = function () {

    this.Startup = function () {
        $('.login-title li').on('click', this.OnUserRoleTab.bind(this));
        $(".login-tab .login-block").eq(0).show();
        $('.login-btn').on('click', this.OnLoginBtnClick.bind(this));
    };

    this.OnLoginBtnClick = function () {
        $.ajax({
            type: 'GET',
            data: {
                username: "anhui",
                password: "root"
            },
            async: false,
            url: '/action',
            success: function (data) {
                console.log(data);
            }.bind(this)
        })
    }

    this.OnUserRoleTab = function (event) {
        $('.login-title li').removeClass("active");
        $(event.target).addClass("active");

        var index = $(event.target).index();
        $(".login-tab .login-block").eq(index).css("display", "block").siblings().css("display", "none");
    };
};

$(document).ready(function () {
    var app = new App();
    app.Startup();
});