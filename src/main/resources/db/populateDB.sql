DELETE FROM user_roles;
DELETE FROM meals;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;
ALTER SEQUENCE meal_seq RESTART WITH 1;

INSERT INTO users (name, email, password) VALUES
  ('User', 'user@yandex.ru', 'password'),
  ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

INSERT INTO meals (user_id, id, datetime, description, calories) VALUES
(100000, nextval('meal_seq'::regclass), '2015-05-30 10:00', 'Завтрак', 500),
(100000, nextval('meal_seq'::regclass), '2015-05-30 13:00', 'Обед', 1000),
(100000, nextval('meal_seq'::regclass), '2015-05-30 20:00', 'Ужин', 500),
(100000, nextval('meal_seq'::regclass), '2015-05-31 10:00', 'Завтрак', 500),
(100000, nextval('meal_seq'::regclass), '2015-05-31 13:00', 'Обед', 1000),
(100000, nextval('meal_seq'::regclass), '2015-05-31 20:00', 'Ужин', 510),
(100001, nextval('meal_seq'::regclass), '2015-06-01 14:00', 'Админ ланч', 510),
(100001, nextval('meal_seq'::regclass), '2015-06-01 21:00', 'Админ ужин', 1500);