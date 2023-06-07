// 중첩 클래스 종류 (Static or Non-Static(Inner, Local, Anonymous))
// Inner class -> Outer class 직접 참조 가능
// Static class -> Outer class 직접 참조 불가능 (권장)
class House(
    private val address: String,
    private val livingRoom: LivingRoom
) {
    class LivingRoom(
        private val area: Double
    )
//    inner class LivingRoom(
//        private val area: Double
//    ) {
//        val address: String
//            get() = this@House.address
//    }
}