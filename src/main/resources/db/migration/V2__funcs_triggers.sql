CREATE FUNCTION getCatalog(userId int, itemCount int) RETURNS items AS
    'SELECT items from items JOIN item_categories ON items.id = item_categories.item_id WHERE item_categories.category_name IN (SELECT item_categories.category_name FROM items JOIN item_categories ON item_categories.item_id = items.id WHERE items.id IN (SELECT items.id FROM items JOIN shop_items ON items.id = shop_items.item_id JOIN cart_items ON cart_items.shop_item_id = shop_items.id WHERE cart_items.cart_id = userId) OR items.id IN (SELECT item_id FROM favorite_items WHERE user_id = userId) OR items.id IN (SELECT items.id FROM items JOIN shop_items ON shop_items.item_id = items.id JOIN order_items ON order_items.shop_item_id = shop_items.id JOIN orders ON orders.id = order_items.order_id WHERE orders.user_id = userId)) ORDER BY RANDOM();'
LANGUAGE SQL;

CREATE FUNCTION getShopItems(shopId int) RETURNS items AS
    'SELECT items FROM items JOIN shop_items ON items.id = shop_items.item_id WHERE shop_items.shop_id = shopId;'
LANGUAGE SQL;

CREATE FUNCTION update_cart_item_count_inc() RETURNS trigger AS $update_cart_item_count_inc$
BEGIN
UPDATE carts SET item_count = item_count + 1 WHERE carts.user_id = NEW.cart_id;
RETURN NEW;
END; $update_cart_item_count_inc$ LANGUAGE plpgsql;
CREATE FUNCTION update_cart_item_count_dec() RETURNS trigger AS $update_cart_item_count_dec$
BEGIN
UPDATE carts SET item_count = item_count - 1 WHERE carts.user_id = NEW.cart_id;
RETURN NEW;
END; $update_cart_item_count_dec$ LANGUAGE plpgsql;
CREATE TRIGGER update_cart_item_count_inc AFTER INSERT ON cart_items FOR EACH ROW EXECUTE FUNCTION update_cart_item_count_inc();
CREATE TRIGGER update_cart_item_count_dec AFTER DELETE ON cart_items FOR EACH ROW EXECUTE FUNCTION update_cart_item_count_dec();
