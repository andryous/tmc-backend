-- Add 'archived' column to person table if it doesn't exist
ALTER TABLE person ADD COLUMN IF NOT EXISTS archived BOOLEAN DEFAULT FALSE;
