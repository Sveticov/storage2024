package read_model
val listModel = mutableListOf<Model>(
    Model(1, "c1", 100, 150),
    Model(2, "c2", 140, 150),
    Model(3, "c3", 180, 150),
    Model(4, "c4", 240, 150),
    Model(5, "c5", 280, 150),
    Model(6, "c6", 340, 150),
    Model(7, "c7", 100, 250),
    Model(8, "c8", 160, 250),
)
class TestModel {


    fun findID(id: Int): Model {
        return listModel.find { it.id == id } ?: Model(0, "", 0, 0)
    }

    fun deleteId(id: Int): String {
        val model = listModel.find { it.id==id } ?: return "Model not find "
       val status = listModel.remove(model)
        println(listModel)
        return "Delete ID $id  status: $status"
    }
    fun allList():List<Model>{
        return listModel
    }
}

data class Model(
    val id: Int,
    val name: String,
    val xPos: Int,
    val yPos: Int,
)