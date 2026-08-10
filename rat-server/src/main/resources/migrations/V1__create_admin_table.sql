CREATE TABLE IF NOT EXISTS admin (
	id INTEGER NOT NULL,
	username VARCHAR(50),
	password_hash VARCHAR(255),
	updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	first_run BOOLEAN,
	CONSTRAINT single_user_check CHECK (id = 1)
);
