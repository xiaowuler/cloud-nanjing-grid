var Map = function(){
    this.Startup = function(){
        this.CreateMap();
    };

    this.CreateMap = function (){
        this.map = L.map('map', {
            zoomControl: false,
            minZoom: 4,
            maxZoom: 11,
            zoomDelta: 0.05
        }).setView([31.843, 117.224], 7);

        $('.leaflet-control-attribution').hide();
    };
};