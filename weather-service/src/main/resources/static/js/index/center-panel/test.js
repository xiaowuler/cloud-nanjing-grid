var TimeLine = function(parent, line){
    this.Line = line;
    this.ScrollerWidth = 59;
    this.MarkerWidth = 59;
    this.MarginBetweenMarkers = 2;
    this.Times = this.Line.find('.times');
    this.PreButton = self.find('.arrow-prev');
    this.NextButton = self.find('.arrow-next');

    this.Startup = function (){
        this.Times.attr("mark-count", this.Times.find('li').length);
        this.Times.find('li').each(function (index, element) {
            $(element).attr('index', index);
        });

        this.PreButton.on('click', this.OnPrevButtonClick.bind(this));
    };

    this.GetActiveMarker = function () {
        var marker = this.Times.find('li.active:first');
        return marker.length > 0 ? marker : null;
    };

    this.SetActiveMarker = function (toActiveMarker) {
        this.Times.find('li.active').removeClass('active');
        toActiveMarker.addClass('active');

        // Make marker visible
        this.MakeMarkerVisible(toActiveMarker);
    };

    this.OnPrevButtonClick = function () {
        var marker = this.GetActiveMarker();
        if (marker == null)
            return;

        var count = parseInt(this.Times.attr('mark-count'));
        var index = parseInt(marker.attr('index'));
        var prev = (index === 0 ? count - 1 : index - 1);
        var li = this.Times.find('li').eq(prev);

        this.SetActiveMarker(li);
    };

    this.MakeMarkerVisible = function (marker) {
        var leftScrollRight = this.ScrollLeft.offset().left + this.ScrollerWidth;
        var rightScrollLeft = this.ScrollRight.offset().left;

        var offset = marker.offset();
        offset.right = offset.left + this.MarkerWidth;
        var moreScrollSize = (this.MarkerWidth + this.MarginBetweenMarkers) * 5;

        if (offset.left < leftScrollRight)
            this.RightScroll(leftScrollRight - offset.left + moreScrollSize);
        else if (offset.right > rightScrollLeft)
            this.LeftScroll(offset.right - rightScrollLeft + moreScrollSize);
    };
};