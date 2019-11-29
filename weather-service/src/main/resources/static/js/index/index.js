var App = function () {
    this.CurrentTime = new CurrentTime(this);
    this.LeftPanel = new LeftPanel(this);
    this.CenterPanel = new CenterPanel(this);
    this.RightPanel = new RightPanel(this);

    this.Startup = function () {
        this.ReLayout();
        this.CurrentTime.InitTime();
        this.LeftPanel.Startup();
        this.CenterPanel.Startup();
        this.RightPanel.Startup();

        window.onresize = this.ReSize.bind(this);
    };

    this.ReLayout = function () {
        var width = $(window).width();
        var height = $(window).height();
        var centerWidth = $('.center-panel').width();
        $('.content').width((width - 20)).height(height - 94);
        $('.left-panel, .right-panel').width((width - centerWidth - 60) / 2);

        $('.port-table').height(height - 421);
        $('.data-table').height(height - 711)
    };

    this.ReSize = function () {
        this.ReLayout();
        this.InitChart();
    };

    this.InitChart = function () {
        this.LeftPanel.PortChart.SetChartSize();
        this.RightPanel.ProductChart.SetChartSize();
        this.RightPanel.ResponseTime.SetChartSize();
    };
};

$(document).ready(function () {
    var app = new App();
    app.Startup();
});