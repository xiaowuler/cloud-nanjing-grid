var Map = function(){

    this.features = [];
    this.polygons = [];
    this.images = [];
    this.feature = null;
    this.weathers = null;
    this.sites = null;
    this.dialog = new Dialog(this);
    this.timeUtil = new TimeUtil();

    this.Startup = function(){
        this.createEasyMap();
        this.setProBorder();
        this.setRegionName();
    };

    this.createEasyMap = function () {
        this.map = L.map("map", {
            center: [32.00666, 118.85758],
            zoom: 9.2,
            zoomControl: false
        });
    }

    this.clearLayers = function(){
        $(this.features).each(function (index, feature) {
            this.map.removeLayer(feature);
        }.bind(this));
        $(this.polygons).each(function (index, polygon) {
            this.map.removeLayer(polygon);
        }.bind(this))
        $(this.images).each(function (index, image) {
            this.map.removeLayer(image);
        }.bind(this))

        if (this.feature != null){
            this.map.removeLayer(this.feature);
        }

        if (this.weathers != null){
            this.map.removeLayer(this.weathers);
        }

        if (this.sites != null){
            this.map.removeLayer(this.sites);
        }
    };

    this.draw = function (data, elementCode) {
        if (elementCode === "Wind"){
            this.drawWind(data);
        }else if(elementCode === "WTYPE" || elementCode === 'PTYPE'){
            this.drawWeather(data);
        }else {
            this.drawPolygon(data)
        }
        this.drawSixSite();
    };

    this.drawWind = function (data) {
        this.feature = new L.FeatureGroup();
        $(data.box).each(function (index, item) {
            var imageInfo = this.calcCenterPoint(item.startLat, item.endLat, item.startLon, item.endLon);
            this.feature.addLayer(L.marker(imageInfo.center, {
                icon: L.divIcon({
                    className: 'wind-box-label',
                    html: "{0}级".format(item.windSpeedLevel)
                })
            }));

            this.feature.addLayer(L.marker(imageInfo.center, {
                /*icon: L.icon({
                    iconUrl: this.getWindDirectionImageUrl(item.windDirection),
                    iconSize: [16, 16],
                    iconAnchor:[8, 20]

                }),*/
                icon: L.divIcon({
                    // language=HTML
                    className: 'image',
                    html: '<img src="{0}" style="width: 16px; height: 16px">'.format(this.getWindDirectionImageUrl(item.windDirection))
                }),
                title: item.windDirection
            }));

        }.bind(this));
        this.map.addLayer(this.feature.bringToFront());
    };

    this.drawWeather = function (data) {
        this.weathers = new L.FeatureGroup();
        $(data.box).each(function (index, item) {
            var imageInfo = this.calcCenterPoint(item.startLat, item.endLat, item.startLon, item.endLon);

            this.weathers.addLayer(L.marker(imageInfo.center, {
                icon: L.divIcon({
                    // language=HTML
                    className: 'image',
                    html: '<img src="{0}" style="width: 16px; height: 16px">'.format(this.getWeatherImageUrl(item.flag))
                }),
                title: item.flag
            }));

        }.bind(this));
        this.map.addLayer(this.weathers);
    };

    this.drawSixSite = function () {
        this.sites = new L.FeatureGroup();
        $.getJSON("json/site.json", function (data){
            $(data).each(function (index, item) {
                var icon = L.divIcon({className: 'base-station-point'});
                var circleMarker = L.marker(item.Loc, {icon: icon, tag: '{0}({1})'.format(item.Name, item.StationCode)}).off('click').on('click', this.clickSite.bind(this));
                this.sites.addLayer(circleMarker);
            }.bind(this));
            this.map.addLayer(this.sites);
        }.bind(this))
    }

    this.clickSite = function (e) {
        var startTime = this.timeUtil.parseStr($('.start-time li.active').text(), "YYMMDDHH").toDate();
        this.dialog.initial(moment($('.update-time li.active span').text(), 'YYMMDDHH').toDate(), startTime, $('#model').combobox('getValue'), $('#element').combobox('getValue'), e.latlng);

        startTime = moment(startTime).add(1, 'hours').toDate();
        var endTime = moment(startTime).add(1, 'days').toDate();
        this.dialog.InitDate(startTime, endTime);
        this.dialog.OpenDialog(e.target.options.tag);
    };

    this.getWeatherImageUrl = function (value) {
        if (value == "晴"){
            return "images/weather/1晴天.png";
        }else if (value == "多云"){
            return "images/weather/2多云.png";
        }else if (value == "阴"){
            return "images/weather/3阴天.png";
        }else if (value == "阵雨"){
            return "images/weather/4阵雨.png";
        }else if (value == "雷阵雨"){
            return "images/weather/5雷阵雨.png";
        }else if (value == "雷阵雨并伴有冰雹"){
            return "images/weather/6雷阵雨-并伴有冰雹.png";
        }else if (value == "雨夹雪"){
            return "images/weather/7雨夹雪.png";
        }else if (value == "小雨"){
            return "images/weather/8小雨.png";
        }else if (value == "中雨"){
            return "images/weather/9中雨.png";
        }else if (value == "大雨"){
            return "images/weather/10大雨.png";
        }else if (value == "暴雨"){
            return "images/weather/11暴雨.png";
        }else if (value == "大暴雨"){
            return "images/weather/12大暴雨.png";
        }else if (value == "特大暴雨"){
            return "images/weather/13特大暴雨.png";
        }else if (value == "阵雪"){
            return "images/weather/14阵雪.png";
        }else if (value == "小雪"){
            return "images/weather/15小雪.png";
        }else if (value == "中雪"){
            return "images/weather/16中雪.png";
        }else if (value == "大雪"){
            return "images/weather/17大雪.png";
        }else if (value == "暴雪"){
            return "images/weather/18暴雪.png";
        }else if (value == "雾"){
            return "images/weather/19雾.png";
        }else if (value == "冻雨"){
            return "images/weather/20冻雨.png";
        }else if (value == "沙尘暴"){
            return "images/weather/21沙尘暴.png";
        }else if (value == "小到中雨"){
            return "images/weather/22小到中雨.png";
        }else if (value == "中到大雨"){
            return "images/weather/23中到大雨.png";
        }else if (value == "大到暴雨"){
            return "images/weather/24大到暴雨.png";
        }else if (value == "暴雨到大暴雨"){
            return "images/weather/25暴雨到大暴雨.png";
        }else if (value == "大暴雨到特大暴雨"){
            return "images/weather/26大暴雨到特大暴雨.png";
        }else if (value == "小到中雪"){
            return "images/weather/27小到中雪.png";
        }else if (value == "中到大雪"){
            return "images/weather/28中到大雪.png";
        }else if (value == "大到暴雪"){
            return "images/weather/29大到暴雪.png";
        }else if (value == "霾"){
            return "images/weather/30霾.png";
        }else if (value == '雨'){
            return "images/weather/4阵雨.png";
        }else if (value == '雪'){
            return "images/weather/14阵雪.png";
        }else if (value == '雨夹雪'){
            return "images/weather/20冻雨.png";
        }
        return "";
    }

    this.getWindDirectionImageUrl = function (windDirection) {
        switch (windDirection) {
            case '东北风':
                return 'images/wind-direction/southwester.png';
                break;
            case '东风':
                return 'images/wind-direction/west.png';
                break;
            case '东南风':
                return 'images/wind-direction/northwest.png';
                break;
            case '南风':
                return 'images/wind-direction/north.png';
                break;
            case '西南风':
                return 'images/wind-direction/northeaster.png';
                break;
            case '西风':
                return 'images/wind-direction/east.png';
                break;
            case '西北风':
                return 'images/wind-direction/southeaster.png';
                break;
            default:
                return 'images/wind-direction/south.png';
        }
    };

    this.calcCenterPoint = function (startLat, endLat, startLon, endLon) {
        var lonHalf = (endLon - startLon) / 2;
        var latHalf = (endLat - startLat) / 2;

        return {
            center: L.latLng(startLat + latHalf, startLon + lonHalf)
        }
    }

    this.drawPolygon = function(data) {
        $(data.box).each(function (index, item) {
            var latlngs = [[item.startLat, item.startLon], [item.startLat, item.endLon], [item.endLat, item.endLon], [item.endLat, item.startLon]];
            var polygon = L.polygon(
                latlngs,
                {
                    weight: 0.5,
                    opacity: 0.5,
                    color: 'yellow',
                    fillColor: this.getColorByValue(parseFloat(item.value.toFixed(1)), data.legendLevels),
                    fillOpacity: 0.5
                }
            ).addTo(this.map);
            this.polygons.push(polygon);
            this.createLabelsLayer(polygon, item.flag == null ? item.value.toFixed(1) : item.flag, null)
        }.bind(this));
    };



    this.createLabelsLayer = function (polygon, label) {
        var feature = new L.FeatureGroup();

        feature.addLayer(L.marker(polygon.getBounds().getCenter(), {
            icon: L.divIcon({
                className: 'box-label',
                html: label
            })
        }));

        this.map.addLayer(feature);
        this.features.push(feature);
    };

    this.getColorByValue = function (value, legendLevels) {
        var color = null;
        $(legendLevels).each(function (index, item) {
            if (item.beginValue <= value && item.endValue > value){
                color = 'rgb(' + item.color + ')';
                return;
            }
        }.bind(this));

        if (color == null){
            return 'green';
        }
        return color;
    }

    this.setProBorder = function () {
        $.getJSON("json/nanjing-districts.json", function (data) {
            this.borders = L.geoJson(data, {
                style: {
                    weight: 1,
                    opacity: 1.0,
                    color: '#fbae2e',
                    fillColor: '#fbdb39',
                    fillOpacity: 0.1
                }
            })
            this.map.addLayer(this.borders);
        }.bind(this));
    }

    this.setRegionName = function () {
        $.getJSON("json/nanjingprovice.json", function (labels) {
            var feature = new L.FeatureGroup();
            $(labels).each(function (index, label) {
                feature.addLayer(L.marker([label.Latitude, label.Longitude], {
                    icon: L.divIcon({
                        className: 'district-name-label',
                        html: label.Name
                    })
                }));
            }.bind(this));
            this.map.addLayer(feature);
        }.bind(this));
    }
};