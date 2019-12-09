var App = function () {

    this.Startup = function () {
        this.getSecurityError();
        $('.login-title li').on('click', this.OnUserRoleTab.bind(this));
        $(".login-tab .login-block").eq(0).show();
    };
    
    this.getSecurityError = function () {
        var ss = $.session.get('SPRING_SECURITY_LAST_EXCEPTION');
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