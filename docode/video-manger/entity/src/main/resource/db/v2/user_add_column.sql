ALTER TABLE user ADD COLUMN `invited`  bit DEFAULT b'0';
ALTER TABLE user ADD COLUMN `invitation_code` varchar(63);
ALTER TABLE user ADD COLUMN `invitation_user_id` bigint;