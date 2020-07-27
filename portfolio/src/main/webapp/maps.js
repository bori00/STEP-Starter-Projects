function createMap() {
  const map = new google.maps.Map(
      document.getElementById('map'),{
        center: {lat: 46.7712, lng: 23.6236},
        zoom: 8,
        backgroundColor: "#8E8D8A"}); //Cluj-Napoca coordinates
}