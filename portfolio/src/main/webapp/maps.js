class Place{
    constructor(positionLat, positionLng, title, label, iconName){
        this._position = new google.maps.LatLng(positionLat, positionLng);
        this._title = title;
        var iconBase = 'https://maps.google.com/mapfiles/kml/';
        this._marker = new google.maps.Marker({
            position: this._position, 
            title: this._title, 
            label: label, 
            icon: { 
                url: iconBase + iconName,
            }
        });
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
    var cluj = new Place(46.7712, 23.6236, "My hometown: Cluj-Napoca", "M", "paddle/grn-blank.png"); //Cluj-Napoca coordinates
    var myMapOptions = {
        zoom: 8,
        center: cluj.position,
        mapTypeId: 'roadmap',
        backgroundColor: "#8E8D8A",
    };
    const map = new google.maps.Map(
        document.getElementById('map'), myMapOptions); 
    cluj.addMarkerToMap(map);
    addWantToVisitPlacesToMap(map);
}


function addWantToVisitPlacesToMap(map){
    var wantToVisitIcon = "paddle/blu-blank.png";
    var wantToVisitPlaces = [
        new Place(61.3410, 28.0110, "Finnish Lakeland", "", wantToVisitIcon),
        new Place(49.3802, 10.1867, "Rothenburg ob der Tauber", "", wantToVisitIcon),
        new Place(44.5994, 2.3960, "Conques", "", wantToVisitIcon),
    ];
    wantToVisitPlaces.forEach(place => place.addMarkerToMap(map));
}

