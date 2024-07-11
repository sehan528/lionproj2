-- 기존 테이블들 (users, roles, posts, tags, series, images)은 그대로 유지

CREATE TABLE user_roles (
                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            user_id BIGINT,
                            role_id BIGINT,
                            UNIQUE KEY unique_user_role (user_id, role_id),
                            CONSTRAINT fk_user_roles_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
                            CONSTRAINT fk_user_roles_role FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
);

CREATE TABLE likes (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        user_id BIGINT,
                        post_id BIGINT,
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        UNIQUE KEY unique_user_post_like (user_id, post_id),
                        CONSTRAINT fk_likes_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
                        CONSTRAINT fk_likes_post FOREIGN KEY (post_id) REFERENCES posts(id) ON DELETE CASCADE
);

CREATE TABLE post_tags (
                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                           post_id BIGINT,
                           tag_id BIGINT,
                           UNIQUE KEY unique_post_tag (post_id, tag_id),
                           CONSTRAINT fk_post_tags_post FOREIGN KEY (post_id) REFERENCES posts(id) ON DELETE CASCADE,
                           CONSTRAINT fk_post_tags_tag FOREIGN KEY (tag_id) REFERENCES tags(id) ON DELETE CASCADE
);

CREATE TABLE post_series (
                             id BIGINT AUTO_INCREMENT PRIMARY KEY,
                             post_id BIGINT,
                             series_id BIGINT,
                             UNIQUE KEY unique_post_series (post_id, series_id),
                             CONSTRAINT fk_post_series_post FOREIGN KEY (post_id) REFERENCES posts(id) ON DELETE CASCADE,
                             CONSTRAINT fk_post_series_series FOREIGN KEY (series_id) REFERENCES series(id) ON DELETE CASCADE
);

CREATE TABLE post_images (
                             id BIGINT AUTO_INCREMENT PRIMARY KEY,
                             post_id BIGINT,
                             image_id BIGINT,
                             UNIQUE KEY unique_post_image (post_id, image_id),
                             CONSTRAINT fk_post_images_post FOREIGN KEY (post_id) REFERENCES posts(id) ON DELETE CASCADE,
                             CONSTRAINT fk_post_images_image FOREIGN KEY (image_id) REFERENCES images(id) ON DELETE CASCADE
);

CREATE TABLE recent_views (
                              id BIGINT AUTO_INCREMENT PRIMARY KEY,
                              user_id BIGINT,
                              post_id BIGINT,
                              view_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                              CONSTRAINT fk_recent_views_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
                              CONSTRAINT fk_recent_views_post FOREIGN KEY (post_id) REFERENCES posts(id) ON DELETE CASCADE
);