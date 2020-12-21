create definer = root@localhost trigger TriggerOnCartQuantityUpdateDigital
	after update
	on digitalcontaining
	for each row
	update Cart
    set numberOfProduct = numberOfProduct - old.quantity + new.quantity,
        totalPrice = totalPrice - old.quantity*(
            select price from DigitalProduct D where old.digitalProduct=D.id
        )  + new.quantity*(
            select price from DigitalProduct D where old.digitalProduct=D.id
        )
    where old.cart=Cart.user;

