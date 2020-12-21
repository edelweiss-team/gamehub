create definer = root@localhost trigger TriggerOnCartInsertPhysical
	after insert
	on physicalcontaining
	for each row
	update Cart
    set numberOfProduct = numberOfProduct + new.quantity,
        totalPrice = totalPrice + new.quantity*(
            select price from PhysicalProduct D where new.physicalProduct=D.id
        )
    where new.cart=Cart.user;

