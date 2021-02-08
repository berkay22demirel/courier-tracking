# courier-tracking

This project is a restful web application with Java and Spring Boot. In this project with couriers and stores, the location information of the couriers is stored.
This project calculates the total distance traveled by the couriers and can store the times the couriers are near stores.

**Database**
- src/main/resources/static/courier-geolocations.json
- src/main/resources/static/courier.json
- src/main/resources/static/stores.json
- src/main/resources/static/tracking-courier-in-store.json

**Postman Collection**
- src/main/resources/CourierTracking.postman_collection.json

**Controllers**
- Courier Controller
- Store Controller
- Courier Geolocation Controller

**Courier Controller**
- Add Couier
- Get Couier
- Update Couier
- Delete Couier
- Get Couriers

**Store Controller**
- Add Store
- Get Store
- Update Store
- Delete Store
- Get Stores

**Courier Geolocation Controller**
- Notify (Notify location information of the courier)
- Total Travel Distance (Calculates the total distance traveled by the courier)
- Get (Gets the location of the courier by id)
- GetAllByCourierId (Gets the locations of the courier by courier id)
- Delete (Deletes the location of the courier by id)
