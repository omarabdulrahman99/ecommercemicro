-- V1__init_database.sql

-- Create the schema if it doesn't exist
CREATE SCHEMA IF NOT EXISTS item;

-- Create the categories table
CREATE TABLE IF NOT EXISTS item.categories (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255)
);

-- Create the items table
CREATE TABLE IF NOT EXISTS item.items (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    details TEXT,
    stock_quantity DOUBLE PRECISION NOT NULL,
    cost DECIMAL(10, 2) NOT NULL,
    category_id INT,
    sku VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_active BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (category_id) REFERENCES item.categories(id)
);

-- Insert categories
INSERT INTO item.categories (name, description) VALUES ('Candles', 'Scented and decorative candles');
INSERT INTO item.categories (name, description) VALUES ('Perfumes', 'Luxury and everyday perfumes');

-- Insert items for the 'Candles' category
INSERT INTO item.items (title, details, stock_quantity, cost, sku, category_id)
VALUES
    ('Vanilla Candle', 'Scented candle with rich vanilla aroma', 50, 19.99, 'CANDLE-VANILLA-01', (SELECT id FROM item.categories WHERE name = 'Candles' LIMIT 1)),
    ('Lavender Candle', 'Relaxing lavender scented candle', 30, 24.99, 'CANDLE-LAVENDER-01', (SELECT id FROM item.categories WHERE name = 'Candles' LIMIT 1)),
    ('Citrus Candle', 'Refreshing citrus blend candle', 20, 22.99, 'CANDLE-CITRUS-01', (SELECT id FROM item.categories WHERE name = 'Candles' LIMIT 1)),
    ('Ocean Breeze Candle', 'Candle with ocean breeze fragrance', 25, 27.99, 'CANDLE-OCEAN-01', (SELECT id FROM item.categories WHERE name = 'Candles' LIMIT 1)),
    ('Holiday Spice Candle', 'Seasonal candle with warm spices', 15, 29.99, 'CANDLE-HOLIDAY-01', (SELECT id FROM item.categories WHERE name = 'Candles' LIMIT 1));

-- Insert items for the 'Perfumes' category
INSERT INTO item.items (title, details, stock_quantity, cost, sku, category_id)
VALUES
    ('Eau de Parfum', 'Luxury perfume with floral notes', 40, 89.99, 'PERFUME-FLORAL-01', (SELECT id FROM item.categories WHERE name = 'Perfumes' LIMIT 1)),
    ('Citrus Splash', 'Refreshing perfume with citrus essence', 35, 69.99, 'PERFUME-CITRUS-01', (SELECT id FROM item.categories WHERE name = 'Perfumes' LIMIT 1)),
    ('Spicy Amber', 'Rich perfume with amber and spice', 20, 79.99, 'PERFUME-AMBER-01', (SELECT id FROM item.categories WHERE name = 'Perfumes' LIMIT 1)),
    ('Ocean Mist', 'Light and fresh perfume reminiscent of the sea', 30, 74.99, 'PERFUME-OCEAN-01', (SELECT id FROM item.categories WHERE name = 'Perfumes' LIMIT 1)),
    ('Woodsy Cedar', 'Earthy and woodsy perfume with cedar notes', 25, 84.99, 'PERFUME-CEDAR-01', (SELECT id FROM item.categories WHERE name = 'Perfumes' LIMIT 1));
