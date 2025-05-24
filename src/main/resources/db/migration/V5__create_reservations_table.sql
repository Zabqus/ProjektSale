CREATE TABLE reservations (
                              id BIGSERIAL PRIMARY KEY,
                              user_id BIGINT NOT NULL,
                              room_id BIGINT NOT NULL,
                              start_time TIMESTAMP NOT NULL,
                              end_time TIMESTAMP NOT NULL,
                              status VARCHAR(20) NOT NULL DEFAULT 'PENDING' CHECK (status IN ('PENDING', 'CONFIRMED', 'CANCELLED', 'COMPLETED')),
                              purpose VARCHAR(500),
                              notes TEXT,
                              created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                              updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

                              CONSTRAINT fk_reservations_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
                              CONSTRAINT fk_reservations_room FOREIGN KEY (room_id) REFERENCES rooms(id) ON DELETE CASCADE,
                              CONSTRAINT chk_reservation_time CHECK (end_time > start_time)
);

CREATE INDEX idx_reservations_user ON reservations(user_id);
CREATE INDEX idx_reservations_room ON reservations(room_id);
CREATE INDEX idx_reservations_time ON reservations(start_time, end_time);
CREATE INDEX idx_reservations_status ON reservations(status);

--overlapping reservations
CREATE INDEX idx_reservations_overlap ON reservations(room_id, start_time, end_time) WHERE status IN ('CONFIRMED', 'PENDING');