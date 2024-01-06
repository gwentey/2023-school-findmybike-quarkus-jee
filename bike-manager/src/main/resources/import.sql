-- Zone autour de la Tour Eiffel
INSERT INTO Zone (id, latitude_point_1, longitude_point_1, latitude_point_2, longitude_point_2, latitude_point_3, longitude_point_3, latitude_point_4, longitude_point_4)
VALUES (1, 48.8579, 2.2945, 48.8579, 2.29468, 48.85808, 2.29468, 48.85808, 2.2945);

-- Zone autour du Musée du Louvre
INSERT INTO Zone (id, latitude_point_1, longitude_point_1, latitude_point_2, longitude_point_2, latitude_point_3, longitude_point_3, latitude_point_4, longitude_point_4)
VALUES (2, 48.8606, 2.3376, 48.8606, 2.33778, 48.86078, 2.33778, 48.86078, 2.3376);

-- Zone autour de la Cathédrale Notre-Dame
INSERT INTO Zone (id, latitude_point_1, longitude_point_1, latitude_point_2, longitude_point_2, latitude_point_3, longitude_point_3, latitude_point_4, longitude_point_4)
VALUES (3, 48.8529, 2.3500, 48.8529, 2.35018, 48.85308, 2.35018, 48.85308, 2.3500);

-- Zone autour du Sacré-Cœur
INSERT INTO Zone (id, latitude_point_1, longitude_point_1, latitude_point_2, longitude_point_2, latitude_point_3, longitude_point_3, latitude_point_4, longitude_point_4)
VALUES (4, 48.8867, 2.3430, 48.8867, 2.34318, 48.88688, 2.34318, 48.88688, 2.3430);

-- Zone autour de l'Arc de Triomphe
INSERT INTO Zone (id, latitude_point_1, longitude_point_1, latitude_point_2, longitude_point_2, latitude_point_3, longitude_point_3, latitude_point_4, longitude_point_4)
VALUES (5, 48.8738, 2.2950, 48.8738, 2.29518, 48.87398, 2.29518, 48.87398, 2.2950);

-- Insertion des vélos dans les zones
REPLACE INTO Bike (idBike, positionY, positionX, batterie, managerId, zone_id) VALUES
(1, 48.85800, 2.29460, 100, 1, 1),
(2, 48.86069, 2.33769, 100, 2, 2),
(3, 48.85295, 2.35010, 100, 1, 3),
(4, 48.88675, 2.34310, 100, 2, 4),
(5, 48.87385, 2.29510, 100, 1, 5),
(6, 48.85800, 2.29460, 100, 2, 1),
(7, 48.86069, 2.33769, 100, 1, 2),
(8, 48.85295, 2.35010, 100, 2, 3),
(9, 48.88675, 2.34310, 100, 1, 4),
(10, 48.87385, 2.29510, 100, 2, 5),
(11, 48.85800, 2.29460, 100, 1, 1),
(12, 48.86069, 2.33769, 100, 2, 2),
(13, 48.85295, 2.35010, 100, 1, 3),
(14, 48.88675, 2.34310, 100, 2, 4),
(15, 48.87385, 2.29510, 100, 1, 5),
(16, 48.85800, 2.29460, 100, 2, 1),
(17, 48.86069, 2.33769, 100, 1, 2),
(18, 48.85295, 2.35010, 100, 2, 3),
(19, 48.88675, 2.34310, 100, 1, 4),
(20, 48.87385, 2.29510, 100, 2, 5),
(21, 48.85800, 2.29460, 100, 1, 1),
(22, 48.86069, 2.33769, 100, 2, 2),
(23, 48.85295, 2.35010, 100, 1, 3),
(24, 48.88675, 2.34310, 100, 2, 4),
(25, 48.87385, 2.29510, 100, 1, 5),
(26, 48.85800, 2.29460, 100, 2, 1),
(27, 48.86069, 2.33769, 100, 1, 2),
(28, 48.85295, 2.35010, 100, 2, 3),
(29, 48.88675, 2.34310, 100, 1, 4),
(30, 48.87385, 2.29510, 100, 2, 5);
