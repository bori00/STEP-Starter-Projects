class Place{
    constructor(positionLat, positionLng, title, label, markerColor){
        this.position = new google.maps.LatLng(positionLat, positionLng);
        this.title = title;
        this.marker = new google.maps.Marker({position: this.position, title: this.title, label: label});
        console.log(this.marker);
    }

    addMarkerToMap(map){
        console.log("Add " + this.title + "to map");
        this.marker.setMap(map);
    }

    get getPosition(){
        return this.position;
    }
}

function createMap() {
    var cluj = new Place(46.7712, 23.6236, "My hometown: Cluj-Napoca", "M", "green"); //Cluj-Napoca coordinates
    var myMapOptions = {
        zoom: 8,
        center: cluj.getPosition,
        mapTypeId: 'roadmap',
        backgroundColor: "#8E8D8A",
    };
    const map = new google.maps.Map(
        document.getElementById('map'), myMapOptions); 
    cluj.addMarkerToMap(map);
}

