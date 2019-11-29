var TimeLine = function(parent, line){
    this.Line = line;
    this.Timer = null;
    this.ImageWidth = 69;
    this.Times = this.Line.find('.time-content');
    this.PrevButton = this.Line.find('.arrow-prev');
    this.NextButton = this.Line.find('.arrow-next');
    this.PlayButton = $('#play');
    this.PauseButton = $('#pause');

    this.Startup = function (){
        this.Times.attr("image-count", this.Times.find('li').length);
        this.Times.find('li').each(function (index, element) {
            $(element).attr('index', index);
        });

        this.PrevButton.on('click', this.ActivePrevMarker.bind(this));
        this.NextButton.on('click', this.ActiveNextMarker.bind(this));
        this.PlayButton.on('click', this.OnPlayButtonClick.bind(this));
        this.PauseButton.on('click', this.OnPauseButtonClick.bind(this));

        this.Times.find('li').on('click', this.OnThumbMarkerClick.bind(this));
        this.SetActiveMarker(0);
    };

    this.SetActiveMarker = function (imageIndex) {
        this.Times.find('li.active').removeClass("active");
        this.Times.find('li').eq(imageIndex).addClass("active");

        this.MakeVisible(imageIndex);
        this.Times.attr("image-index", imageIndex);
    };

    this.MakeVisible = function (index) {
        var imageOffset = this.Times.find('li').eq(index).offset();
        imageOffset.right = imageOffset.left + this.ImageWidth;

        var listOffset = this.Times.offset();
        listOffset.right = listOffset.left + this.Times.width();

        var left = parseInt(this.Times.find('ul').css("left").replace("px", ""));
        if (imageOffset.left < listOffset.left)
            this.Times.find('ul').stop().animate({'left': left + (listOffset.left - imageOffset.left)}, 600);
        else if (imageOffset.right > listOffset.right)
            this.Times.find('ul').stop().animate({'left': left + (listOffset.right - imageOffset.right)}, 600);
    };

    this.ActivePrevMarker = function () {
        var imageIndex = parseInt(this.Times.attr("image-index"));
        var imageCount = parseInt(this.Times.attr("image-count"));
        var prevIndex =  (imageIndex > 0) ? imageIndex - 1 : imageCount - 1;
        this.SetActiveMarker(prevIndex);
    };

    this.ActiveNextMarker = function () {
        var imageIndex = parseInt(this.Times.attr("image-index"));
        var imageCount = parseInt(this.Times.attr("image-count"));
        var nextIndex =  (imageIndex < imageCount - 1) ? imageIndex + 1 : 0;
        this.SetActiveMarker(nextIndex);
    };

    this.OnThumbMarkerClick = function(e) {
        var index = $(e.target).attr("index");
        this.SetActiveMarker(index);
    };

    this.OnPlayButtonClick = function (e) {
        $(e.target).addClass('play-disable');
        $(e.target).next().removeClass('pause-disable');

        if (this.Line.attr('id') === 'forecast'){
            this.Timer = setInterval(function () {
                this.ActiveNextMarker();
            }.bind(this), 2000);
        }


    };

    this.OnPauseButtonClick = function (e) {
        $(e.target).addClass('pause-disable');
        $(e.target).prev().removeClass('play-disable');
        if (this.Timer !== null)
            clearInterval(this.Timer);
    };
};