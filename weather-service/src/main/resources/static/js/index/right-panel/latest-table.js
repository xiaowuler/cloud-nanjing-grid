var LatestTable = function () {
    this.Timer = null;
    this.table = $('.data-table');
    this.DataGrid = $('#data-table');

    this.Reload = function(){
        this.InitTable();
        this.ReloadData();
        this.table.on('mouseover', this.RemoveSetInterval.bind(this));
        this.table.on('mouseout', this.AutoPlay.bind(this));
    };

    this.InitTable = function () {
        this.DataGrid.datagrid({
            columns: [[
                { field: 'name', title: '产品名称', align: 'left', width: 420 },
                { field: 'time', title: '数据更新时间', align: 'right'}
            ]],
            striped: true,
            singleSelect: true,
            fitColumns: true,
            fit: true,
            scrollbarSize: 0,
            onLoadSuccess: function () {
                this.SetStateGridScroll(-1);
            }.bind(this)
        });
    };

    this.ReloadData = function () {
        this.DataGrid.datagrid({
            method: "POST",
            url: 'other/findNewestTime'
        });
    };

    this.SetStateGridScroll = function (index) {
        var rows = this.DataGrid.datagrid("getRows");
        if (rows.length * 28 > ($('.data-table').height() - 28)){
            this.Timer = setInterval(function () {
                index++;
                if (index >= rows.length)
                    index = 0;

                this.DataGrid.datagrid('scrollTo', index);
                this.DataGrid.datagrid('selectRow', index);
            }.bind(this), 3000);
        }
    };

    this.RemoveSetInterval = function (){
        if (this.Timer !== null)
            clearInterval(this.Timer);
    };

    this.AutoPlay = function () {
        var selected = this.DataGrid.datagrid('getSelected');
        var index = this.DataGrid.datagrid('getRowIndex',selected);
        this.SetStateGridScroll(index);
    };
};