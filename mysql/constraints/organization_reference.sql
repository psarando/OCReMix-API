ALTER TABLE organization_reference
    ADD CONSTRAINT organization_reference_organization_id_fkey
    FOREIGN KEY (organization_id)
    REFERENCES organizations(id);

