var CenterPanel = function () {
    this.UpdateTimeLine = new TimeLine(this, $('.update-liner'));
    this.NewspaperTimeLine = new TimeLine(this, $('.newspaper-liner'));
    this.ForecastTimeLine = new TimeLine(this, $('.forecast-liner'));

    this.Startup = function(){
        this.ResetComboBox();
        this.UpdateTimeLine.Startup();
        this.NewspaperTimeLine.Startup();
        this.ForecastTimeLine.Startup();
    };

    this.ResetComboBox = function () {
        $('#model').combobox({
            panelHeight: 'auto'
        });

        $('#element').combobox({
            panelHeight: 'auto'
        });
    };
};