class Item(
    val id: Long,
    val name: String,
    val factoryPrice: Long,
    val currentPrice: Long
) {
    val isSamePrice: Boolean
        get() = factoryPrice == currentPrice
}

fun function(items: List<Item>) {
    // Filter & Map
    val factoryPrices = items.filter { item -> item.name == "item" }.map { item -> item.factoryPrice }

    // Collection 처리
    val isAllItem = items.all { item -> item.name == "item" }
    val isAllNotItem = items.none { item -> item.name == "item" }
    val atLeastOneIsItem = items.any { item -> item.name == "item" }
    val numberOfItems = items.count()
    val sortedById = items.sortedBy { item -> item.id }
    val sortedByDescendingId = items.sortedByDescending { item -> item.id }
    val distinctItems = items.distinctBy { item -> item.name }.map { item -> item.name }
    val firstItem = items.first()
    val firstOrNullItem = items.firstOrNull()
    val lastItem = items.last()
    val lastOrNullItem = items.lastOrNull()

    // List to Map
    val groupByItemName: Map<String, List<Item>> = items.groupBy { item -> item.name }
    val itemNameAndPrices: Map<String, List<Long>> = items.groupBy({ it.name }, { it.factoryPrice })
    val itemMap: Map<Long, Item> = items.associateBy { item -> item.id }
    val itemMapIdToPrice: Map<Long, Long> = items.associateBy({ it.id }, { it.factoryPrice })

    // Nested Collection
    val itemsInList: List<List<Item>> = mutableListOf()
    val flattenItems = itemsInList.flatten()
}