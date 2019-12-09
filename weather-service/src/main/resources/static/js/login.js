var App = function () {

    this.manageUrl = "http://10.124.102.42:8090/";

    this.Startup = function () {
        $('.login-title li').on('click', this.OnUserRoleTab.bind(this));
        $(".login-tab .login-block").eq(0).show();
        this.getSecurityError();
        $('#admin-login').on('click', this.OnAdminLogin.bind(this));
    };

    this.OnAdminLogin = function () {
        $.ajax({
            type: "POST",
            dataType: 'json',
            data:{
                username: $("#admin-username").val(),
                password: $("#admin-password").val()
            },
            async: false,
            url: 'user/login',
            success: function (data) {
                if (data.responseText != null && data.responseText != undefined && data.responseText != ''){
                    $("#error-message").html('<div>' + data.responseText + '</div>');
                }
            }.bind(this),
            error: function (data) {
                if (data.responseText != null && data.responseText != undefined && data.responseText != ''){
                    if (data.responseText === 'login success'){
                        window.location.href= this.manageUrl;
                    }else {
                        $("#error-message").html('<div>' + data.responseText + '</div>');
                    }
                }
            }.bind(this)
        });
    }

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
        var index = $(event.target).index();
        $(".login-tab .login-block").eq(index).css("display", "block").siblings().css("display", "none");
    };
};

$(document).ready(function () {
    var app = new App();
    app.Startup();
});