var InitAside = function (){
    this.menuValue = null;
    this.RightPanel = new RightPanel(this);

    this.Startup = function(){
        this.Reload();

        $('.first-menu h1').on('click', this.ShowSecondMenu.bind(this));
        $('.second-content li').on('click', this.OnSecondMenuClick.bind(this));
        $('.first-menu').eq(0).find('ul').slideDown();
        $('.second-content li').eq(0).trigger('click');
        this.RightPanel.Reload(1);
        this.RightPanel.ReloadInterfaceUrl('baseSearch/findNJGridsByArea');
    };

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
        console.log(value);
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

        this.RightPanel.Reload(parseInt($(event.target).attr('index')));
        this.RightPanel.ReloadInterfaceUrl($(event.target).attr('name'));
    };
};