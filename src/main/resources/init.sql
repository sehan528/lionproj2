CREATE TABLE users (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       user_id VARCHAR(255) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       name VARCHAR(255) NOT NULL,
                       email VARCHAR(255) NOT NULL UNIQUE,
                       profile_img VARCHAR(255),
                       is_ban BOOLEAN DEFAULT FALSE,
                       registration_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE posts (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       title VARCHAR(255) NOT NULL,
                       context TEXT NOT NULL,
                       is_private BOOLEAN DEFAULT FALSE,
                       creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       update_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE about_me (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          user_id BIGINT NOT NULL UNIQUE,
                          context TEXT NOT NULL,
                          CONSTRAINT fk_about_me_user FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE roles (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       name VARCHAR(255) NOT NULL UNIQUE
);

-- 초기 데이터 삽입
INSERT INTO roles (name) VALUES ('ROLE_ADMIN'), ('ROLE_USER');

CREATE TABLE user_posts (
                            user_id BIGINT,
                            post_id BIGINT,
                            PRIMARY KEY (user_id, post_id),
                            CONSTRAINT fk_user_posts_user FOREIGN KEY (user_id) REFERENCES users(id),
                            CONSTRAINT fk_user_posts_post FOREIGN KEY (post_id) REFERENCES posts(id)
);

CREATE TABLE user_roles (
                            user_id BIGINT,
                            role_id BIGINT,
                            PRIMARY KEY (user_id, role_id),
                            CONSTRAINT fk_user_roles_user FOREIGN KEY (user_id) REFERENCES users(id),
                            CONSTRAINT fk_user_roles_role FOREIGN KEY (role_id) REFERENCES roles(id)
);

CREATE TABLE series (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE post_series (
                             post_id BIGINT,
                             series_id BIGINT,
                             PRIMARY KEY (post_id, series_id),
                             CONSTRAINT fk_post_series_post FOREIGN KEY (post_id) REFERENCES posts(id),
                             CONSTRAINT fk_post_series_series FOREIGN KEY (series_id) REFERENCES series(id)
);

CREATE TABLE images (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        url VARCHAR(255) NOT NULL
);

CREATE TABLE post_images (
                             post_id BIGINT,
                             image_id BIGINT,
                             PRIMARY KEY (post_id, image_id),
                             CONSTRAINT fk_post_images_post FOREIGN KEY (post_id) REFERENCES posts(id),
                             CONSTRAINT fk_post_images_image FOREIGN KEY (image_id) REFERENCES images(id)
);

CREATE TABLE tags (
                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                      name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE post_tags (
                           post_id BIGINT,
                           tag_id BIGINT,
                           PRIMARY KEY (post_id, tag_id),
                           CONSTRAINT fk_post_tags_post FOREIGN KEY (post_id) REFERENCES posts(id),
                           CONSTRAINT fk_post_tags_tag FOREIGN KEY (tag_id) REFERENCES tags(id)
);

CREATE TABLE likes (
                       user_id BIGINT,
                       post_id BIGINT,
                       PRIMARY KEY (user_id, post_id),
                       CONSTRAINT fk_likes_user FOREIGN KEY (user_id) REFERENCES users(id),
                       CONSTRAINT fk_likes_post FOREIGN KEY (post_id) REFERENCES posts(id)
);

CREATE TABLE recent_views (
                              user_id BIGINT,
                              post_id BIGINT,
                              view_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                              PRIMARY KEY (user_id, post_id),
                              CONSTRAINT fk_recent_views_user FOREIGN KEY (user_id) REFERENCES users(id),
                              CONSTRAINT fk_recent_views_post FOREIGN KEY (post_id) REFERENCES posts(id)
);
