
INSERT INTO users (username, email, password, role) VALUES
('admin', 'admin@test.com', '$2a$10$N.zmdr9k7uOCQb0bxVAGKe.OcVb/XbOhX.HS8.y.o9.0EY1OGP6a6', 'ADMIN'),
('john', 'john@test.com', '$2a$10$N.zmdr9k7uOCQb0bxVAGKe.OcVb/XbOhX.HS8.y.o9.0EY1OGP6a6', 'USER'),
('jane', 'jane@test.com', '$2a$10$N.zmdr9k7uOCQb0bxVAGKe.OcVb/XbOhX.HS8.y.o9.0EY1OGP6a6', 'USER');


INSERT INTO rooms (name, capacity, location, description) VALUES
('Room A', 10, 'Floor 1', 'Conference room'),
('Room B', 5, 'Floor 2', 'Meeting room'),
('Room C', 15, 'Floor 1', 'Training room');


INSERT INTO equipment (equipment_type, name, description, room_id, resolution, brightness) VALUES
('PROJECTOR', 'BenQ Projector 1', 'Main projector', 1, 'FULLHD', 3000),
('PROJECTOR', 'BenQ Projector 2', 'Training projector', 3, '4K', 4000);

INSERT INTO equipment (equipment_type, name, description, room_id, operating_system, processor, ram_gb) VALUES
('COMPUTER', 'PC 1', 'Training computer', 3, 'Windows 11', 'Intel i5', 8),
('COMPUTER', 'PC 2', 'Training computer', 3, 'Windows 11', 'Intel i5', 8);


INSERT INTO reservations (user_id, room_id, start_time, end_time, status, purpose) VALUES
(2, 1, '2025-05-25 09:00:00', '2025-05-25 11:00:00', 'CONFIRMED', 'Team meeting'),
(3, 2, '2025-05-25 14:00:00', '2025-05-25 15:30:00', 'PENDING', 'Presentation');