CREATE TABLE inventory(
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    product_id VARCHAR(255) NOT NULL UNIQUE,
    quantity BIGINT NOT NULL CHECK (quantity >= 0),
    minimum_stock_level BIGINT NOT NULL CHECK (minimum_stock_level >= 0)
);