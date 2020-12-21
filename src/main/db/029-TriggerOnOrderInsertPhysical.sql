create definer = root@localhost trigger TriggerOnOrderInsertPhysical
	after insert
	on physicalpurchasing
	for each row
	update `Order`
    set numberOfProduct = numberOfProduct + new.quantity,
        totalPrice = totalPrice + new.quantity*(
            select price from PhysicalProduct D where new.physicalProduct=D.id
        )
    where new.`order`=`Order`.id;

