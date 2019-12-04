var InitAside = function (){
    this.menuValue = null;

    this.Startup = function(){
        this.Reload();
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

    this.InitMenu = function (data) {
        //this.CreateMenu();
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
        var secondText = value[0];
        for (var i = 0; i < firstText.length; i++) {
            var firstLevel = $("<li></li>");
            firstLevel.appendTo($(".aside>ul"));
            $("<h1></h1>")
                .html(firstText[i])
                .appendTo(firstLevel);
            if(firstText.length > 1){
                var firstUl = $("<ul></ul>");
                for(var j = 1; j < menuText[i].length; j++){
                    firstUl.appendTo(firstLevel);
                    $("<li></li>")
                        .appendTo(firstUl)
                        .append(
                            $("<a href='###'></a>")
                                .html(menuText[i][j])
                        );
                }
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