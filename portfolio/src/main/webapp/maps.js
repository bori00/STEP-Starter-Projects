var iconBase = 'https://maps.google.com/mapfiles/kml/';
const wantToVisitIcon = 'wantToVisit';
const favoriteIcon = 'favorite';
const importantIcon = 'important';
var icons = {
    wantToVisit: {
        icon: iconBase + 'paddle/blu-blank.png'
    },
    favorite: {
        icon: iconBase + "paddle/ylw-blank.png",
    },
    important: {
        icon: iconBase + 'paddle/orange-blank.png'
    }
};

class Place{
    constructor(positionLat, positionLng, title, label, iconType){
        this._position = new google.maps.LatLng(positionLat, positionLng);
        this._title = title;
        this._marker = new google.maps.Marker({
            position: this._position, 
            title: this._title, 
            label: label, 
            icon: { 
                url: icons[iconType].icon
            }
        });
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
    var cluj = new Place(46.7712, 23.6236, "My hometown: Cluj-Napoca", "M", importantIcon); //Cluj-Napoca coordinates
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
    addFavoritePlacesToMap(map);
}


function addWantToVisitPlacesToMap(map){
    var wantToVisitPlaces = [
        new Place(61.3410, 28.0110, "Finnish Lakeland", "1", wantToVisitIcon),
        new Place(49.3802, 10.1867, "Rothenburg ob der Tauber", "2", wantToVisitIcon),
        new Place(44.5994, 2.3960, "Conques", "3", wantToVisitIcon),
    ];
    wantToVisitPlaces.forEach(place => place.addMarkerToMap(map));
}

function addFavoritePlacesToMap(map){
    var favoritePlaces = [
        new Place(37.1761, -3.5881, "Granada", "1", favoriteIcon),
        new Place(46.7753, 23.5817, "Fellegvar, Cluj", "2", favoriteIcon),
        new Place(47.5576, 10.7498, "Neuschwanstein Castle", "3", favoriteIcon),
        new Place(52.4729, 4.8219, "Zaanse Schans", "4", favoriteIcon),
    ];
    favoritePlaces.forEach(place => place.addMarkerToMap(map));
}

