create definer = root@localhost trigger TriggerOnCartQuantityUpdatePhysical
	after update
	on physicalcontaining
	for each row
	update Cart
    set numberOfProduct = numberOfProduct - old.quantity + new.quantity,
        totalPrice = totalPrice - old.quantity*(
            select price from PhysicalProduct D where old.physicalProduct=D.id
        )  + new.quantity*(
            select price from PhysicalProduct D where old.physicalProduct=D.id
        )
    where old.cart=Cart.user;

