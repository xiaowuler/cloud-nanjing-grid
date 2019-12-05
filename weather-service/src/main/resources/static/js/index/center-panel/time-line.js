var TimeLine = function(parent, line, text, id){
    this.id = id;
    this.parent = parent;
    this.text = text;
    this.Line = line;
    this.Timer = null;
    this.ImageWidth = 86;
    this.Times = this.Line.find('.time-content');
    this.PrevButton = this.Line.find('.arrow-prev');
    this.NextButton = this.Line.find('.arrow-next');
    this.FirstButton = this.Line.find('.arrow-first');
    this.LastButton = this.Line.find('.arrow-last');
    this.PlayButton = $('#play');
    this.PauseButton = $('#pause');

    this.Startup = function (){
        this.Times.attr("image-count", this.Times.find('li').length);
        this.Times.find('li').each(function (index, element) {
            $(element).attr('index', index);
        });
        this.SetActiveMarker(this.Times.find('li').length - 1);
    };

    this.bindClickEvent = function (startTimeIndex, updateTimeIndex, forecastTimeIndex) {
        //this.Times = this.Line.find('.time-content');
        this.Times.attr("image-count", this.Times.find('li').length);
        this.Times.find('li').each(function (index, element) {
            $(element).attr('index', index);
        });

        this.Play();
        this.PrevButton.off('click').on('click', this.ActivePrevMarker.bind(this));
        this.NextButton.off('click').on('click', this.ActiveNextMarker.bind(this));
        this.FirstButton.off('click').on('click', this.ActiveFirstMarker.bind(this));
        this.LastButton.off('click').on('click', this.ActiveLastMarker.bind(this));
        this.PlayButton.off('click').on('click', this.OnPlayButtonClick.bind(this));
        this.PauseButton.off('click').on('click', this.OnPauseButtonClick.bind(this));
        this.Times.find('li span').off('click').on('click', this.OnThumbMarkerClick.bind(this));

        //this.SetActiveMarker(0);
        if ((startTimeIndex != null || startTimeIndex != undefined) && this.Line.attr('id') === 'start')
            this.SetActiveMarker(startTimeIndex);
        else if ((updateTimeIndex != null || updateTimeIndex != undefined) && this.Line.attr('id') === 'update')
            this.SetActiveMarker(updateTimeIndex);
        else if ((forecastTimeIndex != null || forecastTimeIndex != undefined) && this.Line.attr('id') === 'forecast')
            this.SetActiveMarker(forecastTimeIndex);
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

        if (imageOffset.right > listOffset.right)
            this.Times.find('ul').stop().animate({'left': left + (listOffset.right - imageOffset.right)}, 600);
    };

    this.ActivePrevMarker = function () {
        var imageIndex = parseInt(this.Times.attr("image-index"));
        var imageCount = parseInt(this.Times.attr("image-count"));
        var prevIndex =  (imageIndex > 0) ? imageIndex - 1 : imageCount - 1;
        this.SetActiveMarker(prevIndex);
        this.reLoadData(prevIndex);
    };

    this.ActiveNextMarker = function () {
        var imageIndex = parseInt(this.Times.attr("image-index"));
        var imageCount = parseInt(this.Times.attr("image-count"));
        var nextIndex =  (imageIndex < imageCount - 1) ? imageIndex + 1 : 0;
        this.SetActiveMarker(nextIndex);
        this.reLoadData(nextIndex);
    };

    this.ActiveFirstMarker = function () {
        this.SetActiveMarker(0);
        this.reLoadData(0);
    };

    this.ActiveLastMarker = function () {
        var imageCount = parseInt(this.Times.attr("image-count"));
        this.SetActiveMarker(imageCount - 1);
        this.reLoadData(imageCount - 1);
    };

    this.OnThumbMarkerClick = function(e) {
        var index = $(e.target).parent().attr("index");
        this.SetActiveMarker(index);
        this.reLoadData(index);
    };

    this.reLoadData = function (index) {
        if (this.text == 'update'){
            this.parent.setStartTime(this.parent.dateTimes[index].startTimes, 0);
        }else if(this.text == 'start'){
            this.parent.setForecastTime(this.parent.dateTimes[$('.update-liner li.active').attr("index")].startTimes[index], 0);
        }else {
            this.parent.loadMap();
        }
    };

    this.OnPlayButtonClick = function (e) {
        $(e.target).addClass('play-disable');
        $(e.target).next().removeClass('pause-disable');
        this.Play();
        // if (this.Line.attr('id') === 'forecast'){
        //     this.Timer = setInterval(function () {
        //         this.ActiveNextMarker();
        //     }.bind(this), 2000);
        // }
    };

    this.Play = function () {
        if (this.Timer !== null)
            clearInterval(this.Timer);

        if (this.Line.attr('id') === 'forecast'){
            this.Timer = setInterval(function () {
                this.ActiveNextMarker();
            }.bind(this), 5000);
        }
    };

    this.OnPauseButtonClick = function (e) {
        $(e.target).addClass('pause-disable');
        $(e.target).prev().removeClass('play-disable');
        if (this.Timer !== null)
            clearInterval(this.Timer);
    };
};