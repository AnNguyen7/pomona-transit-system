# Pomona Transit System

CS 4350 Database Systems - Lab 4

A JavaFX-based database management application for the Pomona Transit System.

## Team

- An Nguyen
- Viet

## Requirements

- Java 17 or higher
- Maven 3.6+

## Setup and Run

1. Install dependencies:
   ```bash
   mvn clean install
   ```

2. Run the application:
   ```bash
   mvn javafx:run
   ```

## Features

All 8 required transactions are implemented:

1. Display trip schedule by start location, destination, and date
2. Edit schedule and offerings (delete, add, change driver, change bus)
3. Display stops of a given trip
4. Display weekly schedule for a driver
5. Add a new driver
6. Add a new bus
7. Delete a bus
8. Record actual trip stop information

## Test Data

The database is pre-populated with the following test data:

**Trips:**
- Trip 1: Pomona to Claremont
- Trip 2: Pasadena to Arcadia
- Trip 3: Riverside to Corona

**Drivers:**
- An (626-555-0100)
- Viet (626-555-0200)

**Buses:**
- T01 (Express, 2021)
- T02 (Metro, 2022)
- T03 (Rapid, 2023)
- T04 (Local, 2023)
- T05 (Shuttle, 2024)

**Stops:**
- 201: 450 Holt Ave, Pomona
- 202: 789 College Ave, Claremont
- 203: 125 Colorado Blvd, Pasadena
- 204: 567 Huntington Dr, Arcadia
- 205: 890 University Ave, Riverside
- 206: 234 Main St, Corona

**Trip Offerings:**
6 scheduled offerings in November 2025

## Testing Examples

### Q1 - Display Trip Schedule
Search for trips between two locations on a specific date.

**Test Case:**
- Start Location: `Pomona`
- Destination: `Claremont`
- Date: `2025-11-22`
- **Expected:** 2 trip offerings:
  - 07:30am-08:45am, Driver: An, Bus: T01
  - 10:00am-11:15am, Driver: Viet, Bus: T03

### Q2 - Edit Schedule
Four operations to modify trip offerings:

**a) Delete Trip Offering:**
- Trip Number: `2`
- Date: `2025-11-23`
- Scheduled Start Time: `01:00pm`
- **Expected:** Removes trip offering (Pasadena→Arcadia, Viet, T04)

**b) Add Trip Offerings:**
- Opens table with existing 6 trip offerings
- Add new offerings with full validation
- **Expected:** Table updates with new entries

**c) Change Driver:**
- Trip Number: `1`, Date: `2025-11-25`, Time: `09:00am`
- Change driver from `An` to `Viet`
- **Expected:** Driver updated successfully

**d) Change Bus:**
- Trip Number: `2`, Date: `2025-11-26`, Time: `03:30pm`
- Change bus from `T05` to `T02`
- **Expected:** Bus assignment updated

### Q3 - Display Stops
Display all stops for a specific trip.

**Test Case:**
- Trip Number: `1`
- **Expected:** 2 stops displayed:
  - Stop 201: 450 Holt Ave, Pomona (Sequence 1, 50min)
  - Stop 202: 789 College Ave, Claremont (Sequence 2, 1h15min)

**Test Case 2:**
- Trip Number: `2`
- **Expected:** 2 stops:
  - Stop 203: 125 Colorado Blvd, Pasadena (Sequence 1, 1h05min)
  - Stop 204: 567 Huntington Dr, Arcadia (Sequence 2, 1h30min)

### Q4 - Weekly Driver Schedule
View all trips for a driver within a specified week.

**Test Case:**
- Driver Name: `An`
- Start Date: `2025-11-22`
- **Expected:** Shows An's trips for that week:
  - Trip 1: 2025-11-22 at 07:30am (Pomona→Claremont, Bus T01)
  - Trip 1: 2025-11-25 at 09:00am (Pomona→Claremont, Bus T02)
  - Trip 2: 2025-11-26 at 03:30pm (Pasadena→Arcadia, Bus T05)

**Test Case 2:**
- Driver Name: `Viet`
- Start Date: `2025-11-22`
- **Expected:** Shows Viet's trips:
  - Trip 1: 2025-11-22 at 10:00am (Pomona→Claremont, Bus T03)
  - Trip 2: 2025-11-23 at 01:00pm (Pasadena→Arcadia, Bus T04)
  - Trip 3: 2025-11-28 at 11:30am (Riverside→Corona, Bus T01)

### Q5 - Add Driver
Add a new driver to the system.

**Test Case:**
- Driver Name: `Mike`
- Phone Number: `626-555-0300`
- **Expected:** Driver added successfully, appears in driver list

### Q6 - Add Bus
Add a new bus to the fleet.

**Test Case:**
- Bus ID: `T99`
- Model: `SuperExpress`
- Year: `2025`
- **Expected:** Bus added successfully, available for assignment

### Q7 - Delete Bus
Remove a bus (only if not assigned to any trips).

**Test Case:**
- Bus ID: `T04`
- **Expected:** Cannot delete (currently assigned to Trip 2 on 2025-11-23)

**Test Case 2:**
- Add a new bus first (e.g., T10), then delete it
- **Expected:** Successfully deletes unassigned bus

### Q8 - Record Actual Trip Data
Record actual arrival/departure times and passenger counts.

**Test Case:**
- Trip Number: `1`
- Date: `2025-11-22`
- Scheduled Start Time: `07:30am`
- Stop Number: `201`
- Scheduled Arrival: `08:45am`
- Actual Start Time: `07:35am`
- Actual Arrival Time: `08:50am`
- Passengers In: `12`
- Passengers Out: `4`
- **Expected:** Actual trip data recorded successfully (matches existing data in ActualTripStopInfo table)

## Technology Stack

- Java 17
- JavaFX 17
- SQLite
- Maven

## Project Structure

```
PomonaTransitSystem/
├── pom.xml
├── schema.sql
├── src/main/java/pts/
│   ├── Main.java
│   ├── model/          (7 entity classes)
│   ├── database/       (DatabaseConnection)
│   └── controller/     (19 controllers)
└── src/main/resources/fxml/  (19 UI files)
```

## Database

The SQLite database (`pomona_transit.db`) is automatically created and initialized on first run from `schema.sql`.

To reset the database, delete `pomona_transit.db` and restart the application.
