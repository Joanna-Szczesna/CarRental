CREATE TABLE CAR
(
    id     BIGINT AUTO_INCREMENT PRIMARY KEY,
    car_type VARCHAR(7) NOT NULL
);

CREATE TABLE RESERVATION
(
    id     BIGINT AUTO_INCREMENT PRIMARY KEY,
    start_date datetime NOT NULL,
    end_date datetime NOT NULL,
    car_id BIGINT NOT NULL
);

ALTER TABLE RESERVATION
    ADD CONSTRAINT reservation_car_id
        FOREIGN KEY (car_id) REFERENCES car(id)

--     type ENUM('SEDAN', 'SUV', 'VAN') NOT NULL