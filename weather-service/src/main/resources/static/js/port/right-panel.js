var RightPanel = function () {
    this.baseUrl = 'http://10.124.102.43:8003/';
    this.data = null;
    this.interfaceName = null;

    this.Startup = function (name) {
        this.interfaceName = name;
        $('#submit-result').off('click').on('click', this.ReloadInterfaceUrl.bind(this));
        $('#delete-result').off('click').on('click', this.RemoveResult.bind(this));
    };

    this.Reload = function (id, name) {
        $.ajax({
            type: "POST",
            dataType: 'json',
            async: false,
            data:{
                id: parseInt(id)
            },
            url: 'interface/findInterfaceParameter',
            success: function (data) {
                this.ShowParamList(data);
                this.data = data;
                $('.btn-address').on('click', this.CreateInterfaceUrl.bind(this, data, name));
                $('#delete').on('click', this.RemoveInterfaceUrl.bind(this, data, name));
            }.bind(this)
        })
    };

    this.GetParameter = function () {

        var flag = true;

        var obj = {};
        var block = $('.param-block');
        this.data.forEach(function (item, index) {
            var cName = null;

            if (block.eq(index).find('#is-required').text() === '是') {
                if(block.eq(index).find('input').val() === ''){
                    flag = false;
                    block.eq(index).find('input').css('borderColor','#ff0000');
                } else {
                    cName = block.eq(index).find('input').val();
                    block.eq(index).find('input').css('borderColor','#125f95')
                }
            } else {
                cName = null;
            }

            var key = item.name;
            var value = cName;
            obj[key] = value;
        }.bind(this));

        if (flag == true)
            return obj;
        else
            return null;
    };

    this.ReloadInterfaceUrl = function () {
        var parameters = this.GetParameter();
        if (parameters === null)
            return;
        $.ajax({
            type: "POST",
            async: true,
            //url: this.baseUrl + this.interfaceName + '?' + parameters,
            url: this.baseUrl + this.interfaceName,
            data: parameters,
            success: function (data) {
                $('#result').text(JSON.stringify(data, null, 2));
            }.bind(this)
        })
    };

    this.ShowParamList = function (data) {
        var list = $('.param-list');
        list.find('.param-block').remove();

        data.forEach(function (item) {
            var name = item.name;
            var password = item.chineseName;
            var type = item.type;
            var describe = item.description;
            var isRequired = item.isRequired === '1' ? '是' : '否';
            var label = '<div class="param-block port-input clearfix"><label>{0}</label><input type="text" class="param-input" placeholder="{1}"><span class="param-type">参数类型：{2}</span><span class="param-describe">是否必填：<i id="is-required">{3}</i></span><span class="param-required">描述：{4}</span></div></div></div>';

            list.append(label.format(name, password, type, isRequired, describe));

            var block = $('.param-block');
            if (block.length <= 4)
                list.height(block.length * (block.height() + 10));
            else
                list.height(186);
            $('.port-result').height($('.content').height() - list.height() - 351);
        }.bind(this));
    };

    this.CreateInterfaceUrl = function (data, param) {
        var key = null;
        var port = $('.port-param');
        port.empty();
        data.forEach(function (item, index) {
            var name = item.name;
            var label = '{0}={1}&';

            if ($('.param-block').eq(index).find('input').val() === '')
                key = item.chineseName;
            else
                key = $('.param-block').eq(index).find('input').val();
            port.append(label.format(name, key));
        }.bind(this));

        var text = port.text();
        port.text('{0}?{1}'.format(param, text));

        port.text((port.text().slice(0,port.text().length-1)));
    };

    this.RemoveInterfaceUrl = function () {
        $('.port-param').empty();
    };

    this.RemoveResult = function () {
        $('#result').empty();
    };
};