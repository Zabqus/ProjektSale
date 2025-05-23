CREATE TABLE rooms (
                       id BIGSERIAL PRIMARY KEY,
                       name VARCHAR(100) NOT NULL,
                       capacity INTEGER NOT NULL CHECK (capacity > 0),
                       location VARCHAR(200),
                       description TEXT,
                       is_available BOOLEAN NOT NULL DEFAULT TRUE,
                       created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                       updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_rooms_name ON rooms(name);
CREATE INDEX idx_rooms_capacity ON rooms(capacity);
CREATE INDEX idx_rooms_available ON rooms(is_available);