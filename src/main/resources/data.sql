INSERT INTO bed_types (name) VALUES
                                 ('Односпальная'),
                                 ('Двуспальная'),
                                 ('Королевский размер'),
                                 ('Двухъярусная'),
                                 ('Диван-кровать'),
                                 ('Раскладушка');
INSERT INTO amenities (name) VALUES
                                 ('Wi-Fi'),
                                 ('Телевизор'),
                                 ('Кондиционер'),
                                 ('Холодильник'),
                                 ('Мини-бар'),
                                 ('Фен'),
                                 ('Балкон'),
                                 ('Вид на море'),
                                 ('Утюг'),
                                 ('Чайник');
INSERT INTO room_types (id, name, description, base_price, max_adults, max_children, square_meters) VALUES
                                                                                                        (1, 'Стандарт', 'Просторный комфортабельный номер', 3000, 2, 1, 25),
                                                                                                        (2, 'Полулюкс', 'Номер повышенной комфортности', 5000, 2, 1, 40),
                                                                                                        (3, 'Люкс', 'Просторный двухкомнатный номер', 8000, 2, 2, 60);
-- Стандарт: две односпальные + диван-кровать
INSERT INTO room_type_beds (room_type_id, bed_type_id, quantity) VALUES
                                                                     (1, 1, 2),   -- Односпальная x2
                                                                     (1, 5, 1);   -- Диван-кровать x1

-- Полулюкс: одна двуспальная + диван-кровать
INSERT INTO room_type_beds (room_type_id, bed_type_id, quantity) VALUES
                                                                     (2, 2, 1),   -- Двуспальная x1
                                                                     (2, 5, 1);   -- Диван-кровать x1

-- Люкс: одна двуспальная + раскладушка
INSERT INTO room_type_beds (room_type_id, bed_type_id, quantity) VALUES
                                                                     (3, 2, 1),   -- Двуспальная x1
                                                                     (3, 6, 1);   -- Раскладушка x1

-- Стандарт: Wi-Fi, Телевизор, Кондиционер
INSERT INTO room_type_amenities (room_type_id, amenity_id) VALUES
                                                               (1, 1),
                                                               (1, 2),
                                                               (1, 3);

-- Полулюкс: Wi-Fi, Телевизор, Кондиционер, Балкон
INSERT INTO room_type_amenities (room_type_id, amenity_id) VALUES
                                                               (2, 1),
                                                               (2, 2),
                                                               (2, 3),
                                                               (2, 7);

-- Люкс: Wi-Fi, Телевизор, Кондиционер, Балкон, Мини-бар
INSERT INTO room_type_amenities (room_type_id, amenity_id) VALUES
                                                               (3, 1),
                                                               (3, 2),
                                                               (3, 3),
                                                               (3, 5),
                                                               (3, 7);
