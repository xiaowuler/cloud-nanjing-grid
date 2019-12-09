var InitAside = function (){
    this.menuValue = null;


    this.RightPanel = new RightPanel(this);

    this.Startup = function(){
        this.Reload();
        this.getCurrentLoginName();

        $('.first-menu h1').on('click', this.ShowSecondMenu.bind(this));
        $('.second-content li').on('click', this.OnSecondMenuClick.bind(this));
        $('.second-content li').eq(0).trigger('click');
        $('.first-menu').eq(0).find('ul').slideDown();
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
                    welcomeStr = '欢迎{0}登录！'.format(data);
                    $('#login').hide();
                    $('#logout').show();
                }

                $('.user').html(welcomeStr);
            }.bind(this)
        })
    }

    this.Reload = function () {
        $.ajax({
            type: "POST",
            dataType: 'json',
            async: false,
            url: 'interface/findInterfaceDetail',
            success: function (data) {
                this.menuValue = data;
                this.CreateMenu();
            }.bind(this)
        })
    };

    this.GetMenuValue = function () {
        var firstMenu = [];
        var secondMenu = [];
        this.menuValue.forEach(function (item) {
            firstMenu.push(item.name);
            secondMenu.push(item.interfaces);
        });
        return [firstMenu, secondMenu]
    };

    this.CreateMenu = function () {
        var value = this.GetMenuValue();
        var firstText = value[0];
        var secondText = value[1];
        for (var i = 0; i < firstText.length; i++) {
            var firstLevel = $("<li class='first-menu'></li>");
            firstLevel.appendTo($(".aside>ul"));
            $("<h1></h1>")
                .html(firstText[i])
                .appendTo(firstLevel);
            if(firstText.length > 1){
                var firstUl = $("<ul class='second-content'></ul>");
                for(var j = 0; j < secondText[i].length; j++){
                    firstUl.appendTo(firstLevel);
                    $("<li class='second-menu' index='{0}' name='{1}'></li>".format(secondText[i][j].id, secondText[i][j].name))
                        .appendTo(firstUl)
                        .append($("<a href='javascript:;'></a>").html(secondText[i][j].explain)
                        );
                }

                $('.aside-content .first-menu').eq(0).addClass('current');
                $('.aside-content .first-menu').eq(0).find('.second-menu').eq(0).addClass('active');
            }
        }
    };

    this.ShowSecondMenu = function (event) {
        $(event.target).next('ul').slideToggle();
        $(event.target).parent('li').toggleClass("current");
        $(event.target).parent('li').siblings().removeClass("current");
        $(event.target).parent('li').siblings().find('ul').slideUp();
    };

    this.OnSecondMenuClick = function (event) {
        $(event.target).parent('li').addClass('active');
        $(event.target).parent('li').siblings().removeClass('active');
        $(event.target).parents('.first-menu').siblings().find('li').removeClass('active');
        var text = $(event.target).parents('ul').prev('h1').text();
        if (text === undefined && text === null)
            text = $('.current').find('h1').text();
        else
            text = text;
        $('.position').text('{0}>>{1}'.format(text, $(event.target).text()));

        var index = null;
        var name = null;
        if ($(event.target).is('li')){
            index = $(event.target).attr('index');
            name = $(event.target).attr('name');
        } else {
            index = $(event.target).parent('li').attr('index');
            name = $(event.target).parent('li').attr('name');
        }
        this.RightPanel.Reload(parseInt(index), name);
        this.RightPanel.Startup(name);
        $('#result').empty();
        $('.port-param').empty();
    };
};