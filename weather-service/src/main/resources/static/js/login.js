var App = function () {

    this.Startup = function () {
        $('.login-title li').on('click', this.OnUserRoleTab.bind(this));
        $(".login-tab .login-block").eq(0).show();
        this.getSecurityError();
    };

    this.getSecurityError = function () {
        $.ajax({
            type: "POST",
            dataType: 'json',
            async: false,
            url: 'user/getError',
            success: function (data) {
                if (data.responseText != null && data.responseText != undefined && data.responseText != ''){
                    $("#error-message").html('<div>' + data.responseText + '</div>');
                }
            }.bind(this),
            error: function (data) {
                if (data.responseText != null && data.responseText != undefined && data.responseText != ''){
                    $("#error-message").html('<div>' + data.responseText + '</div>');
                }
            }.bind(this)
        });
    }

    this.OnUserRoleTab = function (event) {
        $('.login-title li').removeClass("active");
        $(event.target).addClass("active");

        $(event.target).text();
        /*var index = $(event.target).index();
        $(".login-tab .login-block").eq(index).css("display", "block").siblings().css("display", "none");*/
    };
};

$(document).ready(function () {
    var app = new App();
    app.Startup();
});