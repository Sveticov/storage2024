package read_model

import java.io.File
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter


val listModelCarBoards = mutableListOf<ModelCarWithBoard>()
var listModelCarBoardsOptions = mutableListOf<ModelCarWithBoard>()
fun getFileBoards(): List<ModelCarWithBoard> {
    var countTokens = 0
    val name ="C:\\Users\\User\\Desktop\\connectionPLC_Car\\filename09042024.csv" //"filename09042024.csv"
    val file = File(name)
    file.forEachLine {
        if (countTokens > 0) {
            val tokens = it.split(",")
            val nameCar = tokens[1].replace("car", "").trim()
                .first().toString()


            listModelCarBoards.add(
                ModelCarWithBoard(
                    tokens[0].trim().toLong(),
                    nameCar,
                    tokens[2].trim().toLong(),
                    tokens[3].trim().toLong(),
                    tokens[4].replace("[Board: length=", "").trim().toLong(),
                    tokens[5].replace("width=", "").trim().toLong(),
                    tokens[6].replace("thickness=", "").trim().toLong(),
                    tokens[7].replace("asStack=", "").trim().toLong(),
                    tokens[8].replace("coverBoard = Cover Board: length=", "").trim().toLong(),
                    tokens[9].replace("width=", "").trim().toLong(),
                    tokens[10].replace("thickness=", "").trim().toLong(),
                  tokens[11].replace("asStack=", "").replace("]","").trim().toLong(),
                    tokens.last().trim(),
                    idModel = ""
                    //5969967830833,car 1 up 8:13,117158,107866,[Board: length=4166, width=2820, thickness=1270, asStack=290,
                // coverBoard = Cover Board: length=4166, width=2820, thickness=2820, asStack=1],2024-04-11T08:13:39.067381100
                )
            )
        }//if end

        countTokens++
    }
    val listSummary = listModelCarBoards
        .groupBy { it.id }
        .map { it.value.last() }.toMutableList()
    listModelCarBoardsOptions = listSummary

println(listSummary.groupBy { it.name }.toString().replace("ModelCarWithBoard","\n"))
    return listModelCarBoardsOptions
}

data class ModelCarWithBoard(
// coverBoard = Cover Board: length=4166, width=2820, thickness=2820, asStack=1],2024-04-11T08:13:39.067381100
    val id: Long,
    val name: String,
    val posX: Long,
    val posZ: Long,
    val length:Long,
    val width:Long,
    val thickness: Long,
    val asStack:Long,
    val coverBoardLength:Long,
    val coverBoardWidth:Long,
    val coverBoardThickness:Long,
    val coverBoardStack:Long,
    val date: String,
    val idModel: String? = null,
)

fun Long.makeDPX(): Int {
    val x = (((this - 100000) * 2) / 167) + 100//257.14f
    return x.toInt()
}

fun Long.makeDPY(): Int {
    val y = 750 - (((this - 100000) * 3.5) / 257.14f)
    return y.toInt()
}

fun String.fingerOutHours(): String {
    val dateTimeWithOutT = this.replace("T", " ")
    val dateTime = LocalDateTime.parse(dateTimeWithOutT, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.n"))
    return ((LocalDateTime.now()
        .toEpochSecond(ZoneOffset.UTC) - dateTime.toEpochSecond(ZoneOffset.UTC)) / 3600).toString()
}

fun findThickness(thickness: Int): List<ModelCarWithBoard> {

    return getFileBoards().filter { it.thickness==thickness.toLong() }//listThickness
}

fun deleteID(id:Long){
  val model =   listModelCarBoardsOptions.find { it.id==id }
    listModelCarBoardsOptions.remove(model)
}