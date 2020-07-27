function createMap() {
    var clujLatlng = new google.maps.LatLng(46.7712, 23.6236); //Cluj-Napoca coordinates
    var myMapOptions = {
        zoom: 8,
        center: clujLatlng,
        mapTypeId: 'roadmap',
        backgroundColor: "#8E8D8A"
    };
    const map = new google.maps.Map(
        document.getElementById('map'), myMapOptions); 
}