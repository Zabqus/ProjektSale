CREATE TABLE equipment (
                           id BIGSERIAL PRIMARY KEY,
                           equipment_type VARCHAR(50) NOT NULL CHECK (equipment_type IN ('PROJECTOR', 'COMPUTER')),
                           name VARCHAR(255) NOT NULL,
                           description TEXT,
                           is_working BOOLEAN NOT NULL DEFAULT TRUE,
                           room_id BIGINT,

--ProjectorEquipment
                           resolution VARCHAR(50),
                           brightness INTEGER,

-- ComputerEquipment
                           operating_system VARCHAR(100),
                           processor VARCHAR(100),
                           ram_gb INTEGER,

                           CONSTRAINT fk_equipment_room FOREIGN KEY (room_id) REFERENCES rooms(id) ON DELETE CASCADE
);

CREATE INDEX idx_equipment_type ON equipment(equipment_type);
CREATE INDEX idx_equipment_room ON equipment(room_id);
CREATE INDEX idx_equipment_working ON equipment(is_working);