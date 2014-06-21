ALTER TABLE system_reference
    ADD CONSTRAINT system_reference_system_id_fkey
    FOREIGN KEY (system_id)
    REFERENCES systems(id);

