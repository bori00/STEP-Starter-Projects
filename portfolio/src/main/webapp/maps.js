class Place{
    constructor(positionLat, positionLng, title, label, markerColor){
        this._position = new google.maps.LatLng(positionLat, positionLng);
        this._title = title;
        this._marker = new google.maps.Marker({position: this._position, title: this._title, label: label});
        console.log(this._marker);
    }

    addMarkerToMap(map){
        console.log("Add " + this._title + "to map");
        this._marker.setMap(map);
    }

    get position(){
        return this._position;
    }
}

function createMap() {
    var cluj = new Place(46.7712, 23.6236, "My hometown: Cluj-Napoca", "M", "green"); //Cluj-Napoca coordinates
    var myMapOptions = {
        zoom: 8,
        center: cluj.position,
        mapTypeId: 'roadmap',
        backgroundColor: "#8E8D8A",
    };
    const map = new google.maps.Map(
        document.getElementById('map'), myMapOptions); 
    cluj.addMarkerToMap(map);
}

