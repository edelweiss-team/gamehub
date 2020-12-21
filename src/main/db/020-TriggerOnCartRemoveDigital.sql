create definer = root@localhost trigger TriggerOnCartRemoveDigital
	before delete
	on digitalcontaining
	for each row
	update Cart
    set numberOfProduct=numberOfProduct - old.quantity,
        totalPrice = totalPrice - old.quantity*(
            select price from DigitalProduct D where old.digitalProduct=D.id
        )
    where old.cart=Cart.user;

