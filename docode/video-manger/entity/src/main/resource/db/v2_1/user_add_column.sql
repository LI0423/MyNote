ALTER TABLE `user` ADD COLUMN max_invited_not_broken_continuous_sign_in_days bigint DEFAULT 0;
ALTER TABLE `user` ADD COLUMN invited_has_sign_in_broken bit DEFAULT b'0';