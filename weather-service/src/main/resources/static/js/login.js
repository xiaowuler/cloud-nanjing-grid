var App = function () {

    this.manageUrl = "http://10.124.102.42:8090/Call/RememberUser";

    this.Startup = function () {
        $('.login-title li').on('click', this.OnUserRoleTab.bind(this));
        $(".login-tab .login-block").eq(0).show();

        this.getSecurityError();

        $('#admin-login').on('click', this.checkAdminParameter.bind(this));
        $('#user-submit').on('click', this.OnSubmitClick.bind(this));
    };

    this.OnSubmitClick = function () {
        var errorMsg = '';
        if ($('#username').val() === ''){
            $('#username').css('borderColor','#ff0000');
            errorMsg += '用户名不能为空 ';
        } else {
            $('#username').css('borderColor','#125f95');
        }
        if ($('#password').val() === ''){
            $('#password').css('borderColor','#ff0000');
            errorMsg += '密码不能为空 ';
        } else {
            $('#password').css('borderColor','#125f95');
        }

        if (errorMsg === ''){
            $('#real-submit').trigger("click");
        }else {
            $('#error-message').text(errorMsg);
            $('#error-message').show();
        }
    };

    this.checkAdminParameter = function () {
        var errorMsg = '';
        if ($('#admin-username').val() === ''){
            $('#admin-username').css('borderColor','#ff0000');
            errorMsg += '用户名不能为空 ';
        } else {
            $('#admin-username').css('borderColor','#125f95');
        }
        if ($('#admin-password').val() === ''){
            $('#admin-password').css('borderColor','#ff0000');
            errorMsg += '密码不能为空';
        } else {
            $('#admin-password').css('borderColor','#125f95');
        }

        if (errorMsg === ''){
            this.OnAdminLogin();
        }else {
            $('#error-message').text(errorMsg);
            $('#error-message').show();
        }
    }

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
                    if (data.responseText.startsWith('login success')){
                        window.location.href= "{0}?username={1}".format(this.manageUrl, data.responseText.replace('login success', ''));
                        $("#error-message").hide();
                    }else {
                        $("#error-message").show();
                        $("#error-message").html('<div>' + data.responseText + '</div>');
                    }
                }
            }.bind(this)
        });
    };

    this.getSecurityError = function () {
        $.ajax({
            type: "POST",
            dataType: 'json',
            async: false,
            url: 'user/getError',
            success: function (data) {
                console.log(data);
                if (data.responseText != null && data.responseText != undefined && data.responseText != ''){
                    $("#error-message").html('<div>' + data.responseText + '</div>');
                }
            }.bind(this),
            error: function (data) {
                console.log(data);
                if (data.responseText != null && data.responseText != undefined && data.responseText != ''){
                    $("#error-message").show();
                    $("#error-message").html('<div>' + data.responseText + '</div>');
                }else {
                    $("#error-message").hide();
                }
            }.bind(this)
        });
    };

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