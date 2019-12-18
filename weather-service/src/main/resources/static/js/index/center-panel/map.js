var Map = function(){

    this.features = [];
    this.polygons = [];
    this.images = [];
    this.feature = null;
    this.sites = null;
    this.dialog = new Dialog();

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
    };

    this.draw = function (data, elementCode) {
        // if (elementCode === "Wind"){
        //     this.drawWind(data);
        // }else {
        //     this.drawPolygon(data)
        // }
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
                icon: L.icon({
                    iconUrl: this.getWindDirectionImageUrl(item.windDirection),
                    iconSize: [16, 16],
                    iconAnchor:[8, 20]

                }),
                title: item.windDirection
            }));

        }.bind(this));
        this.map.addLayer(this.feature);
    };

    this.drawSixSite = function () {
        this.sites = new L.FeatureGroup();
        $.getJSON("json/site.json", function (data){
            $(data).each(function (index, item) {
                this.sites.addLayer(L.circle(item.Loc,
                    {
                        radius: 500,
                        color: '#B71D18',
                        fillColor: '#000000',
                        fillOpacity: 1,
                        className: '{0}({1})'.format(item.Name, item.StationCode)
                    }));
            }.bind(this));
            this.map.addLayer(this.sites);
            this.sites.off('click').on('click', this.clickSite.bind(this));
        }.bind(this))
    }

    this.clickSite = function (e) {
        this.dialog.OpenDialog();
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
    }

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