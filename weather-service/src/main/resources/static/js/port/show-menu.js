var ShowMenu = function (){
    this.Startup = function(){
        $('.first-menu h1').on('click', this.ShowSecondMenu.bind(this));
        $('.second-content li').on('click', this.OnSecondMenuClick.bind(this));
        $('.first-menu').eq(0).find('ul').slideDown();
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