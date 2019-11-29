var RightPanel = function() {
    this.ProductChart = new ProductChart(this);
    this.ResponseTime = new ResponseTime(this);
    this.LatestTable = new LatestTable(this);

    this.Startup = function(){
        this.ProductChart.Reload();
        this.ResponseTime.Reload();
        this.LatestTable.Reload();
    }
};