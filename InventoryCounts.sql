SELECT
	pi.RequiredInventoryCount AS InventoryRequired,
    i.Count AS InventoryAvailable,
    (i.Count - pi.RequiredInventoryCount) AS Leftover
FROM 
	Product AS p
	INNER JOIN ProductInventory AS pi ON p.ID = pi.ProductID
    INNER JOIN InventoryType AS it ON pi.InventoryTypeID = it.ID
    INNER JOIN Inventory AS i ON it.ID = i.TypeID
WHERE 
	pi.InventoryTypeID = ?;