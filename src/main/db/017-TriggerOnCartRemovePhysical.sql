create definer = root@localhost trigger TriggerOnCartRemovePhysical
	before delete
	on physicalcontaining
	for each row
	update Cart
    set numberOfProduct=numberOfProduct - old.quantity,
        totalPrice = totalPrice - old.quantity*(
            select price from PhysicalProduct D where old.physicalProduct=D.id
        )
    where old.cart=Cart.user;

