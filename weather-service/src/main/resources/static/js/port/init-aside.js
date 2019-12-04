var InitAside = function (){
    this.menuValue = null;

    this.Startup = function(){
        this.Reload();
        this.aa();
        $('.first-menu h1').on('click', this.ShowSecondMenu.bind(this));
        $('.second-content li').on('click', this.OnSecondMenuClick.bind(this));
        $('.first-menu').eq(0).find('ul').slideDown();
    };

    this.Reload = function () {
        $.ajax({
            type: "POST",
            dataType: 'json',
            async: false,
            url: 'interface/findInterfaceDetail',
            success: function (data) {
                console.log(data);
                this.menuValue = data;
                this.InitMenu(data);
            }.bind(this)
        })
    };

    this.aa = function () {
        $.ajax({
            type: "POST",
            dataType: 'json',
            async: false,
            data:{
                id: 1
            },
            url: 'interface/findInterfaceParameter',
            success: function (data) {
                console.log(data);
            }.bind(this)
        })
    };

    this.InitMenu = function (data) {
        this.CreateMenu();
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
                    $("<li class='second-menu'></li>")
                        .appendTo(firstUl)
                        .append($("<a href='javascript:;' index='{0}'></a>".format(secondText[i][j].id)).html(secondText[i][j].explain)
                        );
                }

                $('.aside-content .first-menu').eq(0).find('.second-menu').eq(0).addClass('active');
            }
        }
    };

    this.ShowSecondMenu = function (event) {
        $(event.target).next('ul').slideToggle();
        $(event.target).parent('li').toggleClass("current");
        $(event.target).parent('li').siblings().find('ul').slideUp();
    };

    this.OnSecondMenuClick = function (event) {

        $(event.target).parent('li').addClass('active');
        $(event.target).parent('li').siblings().removeClass('active');
        $(event.target).parents('.first-menu').siblings().find('li').removeClass('active');
    };
};