CREATE VIEW `order_view` AS
SELECT user.email, bottom.bottom_name, topping.topping_name, orders.status, orders.date
FROM orders
LEFT JOIN user 
ON orders.user_id = user.user_id
LEFT JOIN bottom
ON orders.bottom_id = bottom.bottom_id
LEFT JOIN topping
ON orders.topping_id = topping.topping_id;