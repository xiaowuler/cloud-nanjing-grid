var PortTable = function(){
    this.Timer = null;
    this.table = $('.port-table');
    this.StateGrid = $('#state-table');

    this.Reload = function(){
        this.InitTable();
        this.ReloadData();
        this.table.on('mouseover', this.RemoveSetInterval.bind(this));
        this.table.on('mouseout', this.AutoPlay.bind(this));
    };

    this.ReloadData = function () {
        this.StateGrid.datagrid({
            method: "POST",
            url: 'interface/findAll'
        });
    };

    this.InitTable = function () {
        this.StateGrid.datagrid({
            columns: [[
                { field: 'explain', title: '接口名称', align: 'left', width: 420, formatter: this.ShowDetailInfo.bind(this) },
                { field: 'enabled', title: '接口状态', align: 'right', formatter: this.SetState.bind(this)}
            ]],
            striped: true,
            singleSelect: true,
            fitColumns: true,
            fit: true,
            scrollbarSize: 0,
            onLoadSuccess: function () {
                this.SetTooltip();
                this.SetStateGridScroll(-1);
            }.bind(this)
        });
    };

    this.ShowDetailInfo = function (val) {
        return '<a href="javascript:;" title="' + val + '" class="note">' + val + '</a>';
    };

    this.SetState = function (val) {
        if (val === 1)
            return '<span class="success">正常</span>';
        else
            return '<span class="fail">异常</span>';
    };

    this.SetTooltip = function () {
        $(".note").tooltip({
            onShow: function () {
                $(this).tooltip('tip').css({
                    width: $('.note').width(),
                    color: '#72a1ba',
                    borderColor: '#a7c4cc',
                    boxShadow: '1px 1px 3px rgba(0,55,94,0.15)'
                });
            },
            onPosition: function () {
                $(this).tooltip('tip').css('left', $(this).offset().left);
                $(this).tooltip('arrow').css('left', 20);
            }
        });
    };

    this.SetStateGridScroll = function (index) {
        var rows = this.StateGrid.datagrid("getRows");
        if (rows.length * 28 > ($('.port-table').height() - 28)){
            this.Timer = setInterval(function () {
                index++;
                if (index >= rows.length)
                    index = 0;

                this.StateGrid.datagrid('scrollTo', index);
                this.StateGrid.datagrid('selectRow', index);
            }.bind(this), 3000);
        }
    };

    this.RemoveSetInterval = function (){
        if (this.Timer !== null)
            clearInterval(this.Timer);
    };

    this.AutoPlay = function () {
        var selected = this.StateGrid.datagrid('getSelected');
        this.SetStateGridScroll(selected.id - 1);
    };
};