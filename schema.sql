-- Pomona Transit System Database Schema (SQLite)
-- Drop tables if they exist
DROP TABLE IF EXISTS ActualTripStopInfo;
DROP TABLE IF EXISTS TripStopInfo;
DROP TABLE IF EXISTS TripOffering;
DROP TABLE IF EXISTS Stop;
DROP TABLE IF EXISTS Driver;
DROP TABLE IF EXISTS Bus;
DROP TABLE IF EXISTS Trip;

-- Create tables
CREATE TABLE Trip (
    TripNumber INTEGER PRIMARY KEY,
    StartLocationName TEXT,
    DestinationName TEXT
);

CREATE TABLE Driver (
    DriverName TEXT PRIMARY KEY,
    DriverTelephoneNumber TEXT
);

CREATE TABLE Bus (
    BusID TEXT PRIMARY KEY,
    Model TEXT,
    Year TEXT
);

CREATE TABLE Stop (
    StopNumber INTEGER PRIMARY KEY,
    StopAddress TEXT
);

CREATE TABLE TripOffering (
    TripNumber INTEGER,
    Date TEXT,
    ScheduledStartTime TEXT,
    ScheduledArrivalTime TEXT,
    DriverName TEXT,
    BusID TEXT,
    PRIMARY KEY (TripNumber, Date, ScheduledStartTime),
    FOREIGN KEY (TripNumber) REFERENCES Trip(TripNumber),
    FOREIGN KEY (DriverName) REFERENCES Driver(DriverName),
    FOREIGN KEY (BusID) REFERENCES Bus(BusID)
);

CREATE TABLE TripStopInfo (
    TripNumber INTEGER,
    StopNumber INTEGER,
    SequenceNumber INTEGER,
    DrivingTime TEXT,
    PRIMARY KEY (TripNumber, StopNumber),
    FOREIGN KEY (TripNumber) REFERENCES Trip(TripNumber),
    FOREIGN KEY (StopNumber) REFERENCES Stop(StopNumber)
);

CREATE TABLE ActualTripStopInfo (
    TripNumber INTEGER,
    Date TEXT,
    ScheduledStartTime TEXT,
    StopNumber INTEGER,
    ScheduledArrivalTime TEXT,
    ActualStartTime TEXT,
    ActualArrivalTime TEXT,
    NumberOfPassengerIn INTEGER,
    NumberOfPassengerOut INTEGER,
    PRIMARY KEY (TripNumber, Date, ScheduledStartTime, StopNumber),
    FOREIGN KEY (TripNumber, Date, ScheduledStartTime)
        REFERENCES TripOffering(TripNumber, Date, ScheduledStartTime),
    FOREIGN KEY (StopNumber) REFERENCES Stop(StopNumber)
);

-- Insert test data
-- Trips
INSERT INTO Trip (TripNumber, StartLocationName, DestinationName)
VALUES (1, 'Pomona', 'Claremont');
INSERT INTO Trip (TripNumber, StartLocationName, DestinationName)
VALUES (2, 'Pasadena', 'Arcadia');
INSERT INTO Trip (TripNumber, StartLocationName, DestinationName)
VALUES (3, 'Riverside', 'Corona');

-- Drivers
INSERT INTO Driver (DriverName, DriverTelephoneNumber)
VALUES ('An', '626-555-0100');
INSERT INTO Driver (DriverName, DriverTelephoneNumber)
VALUES ('Viet', '626-555-0200');

-- Buses
INSERT INTO Bus (BusID, Model, Year) VALUES ('T01', 'Express', '2021');
INSERT INTO Bus (BusID, Model, Year) VALUES ('T02', 'Metro', '2022');
INSERT INTO Bus (BusID, Model, Year) VALUES ('T03', 'Rapid', '2023');
INSERT INTO Bus (BusID, Model, Year) VALUES ('T04', 'Local', '2023');
INSERT INTO Bus (BusID, Model, Year) VALUES ('T05', 'Shuttle', '2024');

-- Stops
INSERT INTO Stop (StopNumber, StopAddress)
VALUES (201, '450 Holt Ave, Pomona');
INSERT INTO Stop (StopNumber, StopAddress)
VALUES (202, '789 College Ave, Claremont');
INSERT INTO Stop (StopNumber, StopAddress)
VALUES (203, '125 Colorado Blvd, Pasadena');
INSERT INTO Stop (StopNumber, StopAddress)
VALUES (204, '567 Huntington Dr, Arcadia');
INSERT INTO Stop (StopNumber, StopAddress)
VALUES (205, '890 University Ave, Riverside');
INSERT INTO Stop (StopNumber, StopAddress)
VALUES (206, '234 Main St, Corona');

-- Trip Offerings
INSERT INTO TripOffering (TripNumber, Date, ScheduledStartTime, ScheduledArrivalTime, DriverName, BusID)
VALUES (1, '2025-11-22', '07:30am', '08:45am', 'An', 'T01');
INSERT INTO TripOffering (TripNumber, Date, ScheduledStartTime, ScheduledArrivalTime, DriverName, BusID)
VALUES (1, '2025-11-22', '10:00am', '11:15am', 'Viet', 'T03');
INSERT INTO TripOffering (TripNumber, Date, ScheduledStartTime, ScheduledArrivalTime, DriverName, BusID)
VALUES (1, '2025-11-25', '09:00am', '10:15am', 'An', 'T02');
INSERT INTO TripOffering (TripNumber, Date, ScheduledStartTime, ScheduledArrivalTime, DriverName, BusID)
VALUES (2, '2025-11-23', '01:00pm', '02:30pm', 'Viet', 'T04');
INSERT INTO TripOffering (TripNumber, Date, ScheduledStartTime, ScheduledArrivalTime, DriverName, BusID)
VALUES (2, '2025-11-26', '03:30pm', '05:00pm', 'An', 'T05');
INSERT INTO TripOffering (TripNumber, Date, ScheduledStartTime, ScheduledArrivalTime, DriverName, BusID)
VALUES (3, '2025-11-28', '11:30am', '01:00pm', 'Viet', 'T01');

-- Trip Stop Info
INSERT INTO TripStopInfo (TripNumber, StopNumber, SequenceNumber, DrivingTime)
VALUES (1, 201, 1, '50min');
INSERT INTO TripStopInfo (TripNumber, StopNumber, SequenceNumber, DrivingTime)
VALUES (1, 202, 2, '1h15min');
INSERT INTO TripStopInfo (TripNumber, StopNumber, SequenceNumber, DrivingTime)
VALUES (2, 203, 1, '1h05min');
INSERT INTO TripStopInfo (TripNumber, StopNumber, SequenceNumber, DrivingTime)
VALUES (2, 204, 2, '1h30min');
INSERT INTO TripStopInfo (TripNumber, StopNumber, SequenceNumber, DrivingTime)
VALUES (3, 205, 1, '1h20min');
INSERT INTO TripStopInfo (TripNumber, StopNumber, SequenceNumber, DrivingTime)
VALUES (3, 206, 2, '1h35min');

-- Actual Trip Stop Info
INSERT INTO ActualTripStopInfo (TripNumber, Date, ScheduledStartTime, StopNumber, ScheduledArrivalTime, ActualStartTime, ActualArrivalTime, NumberOfPassengerIn, NumberOfPassengerOut)
VALUES (1, '2025-11-22', '07:30am', 201, '08:45am', '07:35am', '08:50am', 12, 4);
INSERT INTO ActualTripStopInfo (TripNumber, Date, ScheduledStartTime, StopNumber, ScheduledArrivalTime, ActualStartTime, ActualArrivalTime, NumberOfPassengerIn, NumberOfPassengerOut)
VALUES (1, '2025-11-22', '07:30am', 202, '08:45am', '07:40am', '08:55am', 18, 6);
INSERT INTO ActualTripStopInfo (TripNumber, Date, ScheduledStartTime, StopNumber, ScheduledArrivalTime, ActualStartTime, ActualArrivalTime, NumberOfPassengerIn, NumberOfPassengerOut)
VALUES (1, '2025-11-22', '10:00am', 201, '11:15am', '10:05am', '11:20am', 15, 8);
INSERT INTO ActualTripStopInfo (TripNumber, Date, ScheduledStartTime, StopNumber, ScheduledArrivalTime, ActualStartTime, ActualArrivalTime, NumberOfPassengerIn, NumberOfPassengerOut)
VALUES (1, '2025-11-22', '10:00am', 202, '11:15am', '10:10am', '11:25am', 22, 10);
INSERT INTO ActualTripStopInfo (TripNumber, Date, ScheduledStartTime, StopNumber, ScheduledArrivalTime, ActualStartTime, ActualArrivalTime, NumberOfPassengerIn, NumberOfPassengerOut)
VALUES (1, '2025-11-25', '09:00am', 201, '10:15am', '09:08am', '10:20am', 14, 5);
INSERT INTO ActualTripStopInfo (TripNumber, Date, ScheduledStartTime, StopNumber, ScheduledArrivalTime, ActualStartTime, ActualArrivalTime, NumberOfPassengerIn, NumberOfPassengerOut)
VALUES (1, '2025-11-25', '09:00am', 202, '10:15am', '09:12am', '10:25am', 20, 9);
INSERT INTO ActualTripStopInfo (TripNumber, Date, ScheduledStartTime, StopNumber, ScheduledArrivalTime, ActualStartTime, ActualArrivalTime, NumberOfPassengerIn, NumberOfPassengerOut)
VALUES (2, '2025-11-23', '01:00pm', 203, '02:30pm', '01:05pm', '02:35pm', 10, 3);
INSERT INTO ActualTripStopInfo (TripNumber, Date, ScheduledStartTime, StopNumber, ScheduledArrivalTime, ActualStartTime, ActualArrivalTime, NumberOfPassengerIn, NumberOfPassengerOut)
VALUES (2, '2025-11-23', '01:00pm', 204, '02:30pm', '01:10pm', '02:40pm', 16, 7);
INSERT INTO ActualTripStopInfo (TripNumber, Date, ScheduledStartTime, StopNumber, ScheduledArrivalTime, ActualStartTime, ActualArrivalTime, NumberOfPassengerIn, NumberOfPassengerOut)
VALUES (2, '2025-11-26', '03:30pm', 203, '05:00pm', '03:35pm', '05:05pm', 13, 6);
INSERT INTO ActualTripStopInfo (TripNumber, Date, ScheduledStartTime, StopNumber, ScheduledArrivalTime, ActualStartTime, ActualArrivalTime, NumberOfPassengerIn, NumberOfPassengerOut)
VALUES (2, '2025-11-26', '03:30pm', 204, '05:00pm', '03:40pm', '05:10pm', 19, 8);
INSERT INTO ActualTripStopInfo (TripNumber, Date, ScheduledStartTime, StopNumber, ScheduledArrivalTime, ActualStartTime, ActualArrivalTime, NumberOfPassengerIn, NumberOfPassengerOut)
VALUES (3, '2025-11-28', '11:30am', 205, '01:00pm', '11:40am', '01:10pm', 25, 12);
INSERT INTO ActualTripStopInfo (TripNumber, Date, ScheduledStartTime, StopNumber, ScheduledArrivalTime, ActualStartTime, ActualArrivalTime, NumberOfPassengerIn, NumberOfPassengerOut)
VALUES (3, '2025-11-28', '11:30am', 206, '01:00pm', '11:45am', '01:15pm', 17, 9);
