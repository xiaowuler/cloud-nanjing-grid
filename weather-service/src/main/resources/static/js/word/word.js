var App = function () {
    this.CurrentTime = new CurrentTime(this);

    this.Startup = function () {
        this.ReLayout();
        this.getCurrentLoginName();
        this.CurrentTime.InitTime();
        $('.word-aside ul li').on('click', this.OnAsideSelect.bind(this));

        window.onresize = this.ReLayout.bind(this);
    };

    this.getCurrentLoginName = function () {
        $.ajax({
            type: 'POST',
            async: false,
            url: 'user/getCurrentLoginName',
            success: function (data) {
                var welcomeStr = '';
                if (data == null || data == undefined || data == ''){
                    welcomeStr = '你好，游客！';
                    $('#login').show();
                    $('#logout').hide();
                }else {
                    welcomeStr = '欢迎{0}登陆！'.format(data);
                    $('#login').hide();
                    $('#logout').show();
                }

                $('.user').html(welcomeStr);
            }.bind(this)
        })
    }

    this.ReLayout = function () {
        var width = $(window).width();
        var height = $(window).height();
        var content = $('.content');
        content.width((width - 20)).height(height - 94);
        $('.word-width').height(content.height() - 39);
    };

    this.OnAsideSelect = function (event) {
        $('.word-aside ul li').removeClass("current");
        $(event.target).addClass("current");
        var path = $(event.target).attr('path');

        $('.iframe').attr('src', 'develop/{0}.html'.format(path));
        $('#title').text($(event.target).text());
    };
};

$(document).ready(function () {
    var app = new App();
    app.Startup();
});