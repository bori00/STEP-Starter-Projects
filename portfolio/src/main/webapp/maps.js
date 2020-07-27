function createMap() {
    var clujLatLng = new google.maps.LatLng(46.7712, 23.6236); //Cluj-Napoca coordinates
    var myMapOptions = {
        zoom: 8,
        center: clujLatLng,
        mapTypeId: 'roadmap',
        backgroundColor: "#8E8D8A"
    };
    const map = new google.maps.Map(
        document.getElementById('map'), myMapOptions); 
    addMarker(map, clujLatLng, "My hometown: Cluj");
}

function addMarker(map, markerPosition, markerTitle){
    var marker = new google.maps.Marker({position: markerPosition, map: map, title: markerTitle});
}