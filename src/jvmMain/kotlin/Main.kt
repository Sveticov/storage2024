import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.zIndex
import read_model.*


fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        StorageView()
    }
}

@Composable
fun StorageView(model: TestModel = TestModel()) {
    var infoModel by remember { mutableStateOf("info model") }
    var findThickness by remember { mutableStateOf("0") }
    var findDate by remember { mutableStateOf("0") }
    var findID by remember { mutableStateOf("0") }
    var listModel by remember { mutableStateOf(getFileBoards()) }
    var modelBoardInfo by remember {
        mutableStateOf(
            ModelCarWithBoard(
                0L, "", 0L, 0L, 0L, 0L, 0L, 0L,
                0L, 0L, 0L, 0L, ""
            )
        )
    }
    Box(modifier = Modifier.fillMaxSize().padding(10.dp).background(Color.Gray)) {

        Column(modifier = Modifier.padding(top = 8.dp, start = 10.dp)) {
            Text(infoModel)
            Spacer(modifier = Modifier.padding(top = 4.dp))
            Row {
                TextField(
                    value = findThickness,
                    onValueChange = { findThickness = it },
                    label = { Text("Find Thickness") })
                Spacer(modifier = Modifier.padding(start = 4.dp))
                TextField(value = findDate, onValueChange = { findDate = it }, label = { Text("Find Date") })
                Spacer(modifier = Modifier.padding(start = 4.dp))
                TextField(value = findID, onValueChange = { findID = it }, label = { Text("Find ID") })
            }
            Row(modifier = Modifier.padding(top = 4.dp)) {
                Button(onClick = {
                    listModel = findThickness(findThickness.toInt())
                }) { Text("Find Thickness") }
                Spacer(modifier = Modifier.padding(start = 2.dp))
                Button(onClick = {
                    listModel = getFileBoards()
                }) { Text("All Thickness") }
                Spacer(modifier = Modifier.padding(start = 2.dp))
                Button(onClick = {
                    listModel = listModelCarBoards
                }) { Text("All") }
                Spacer(modifier = Modifier.padding(start = 2.dp))
                Button(onClick = {
                    listModel = listModelCarBoards.filter { it.date.take(10) == findDate }
                }) { Text("Find Date") }
                Spacer(modifier = Modifier.padding(start = 2.dp))
                Button(onClick = {
                    listModel = listModelCarBoards.filter { it.id == findID.toLong() }
                }) { Text("Find ID") }
            }
        }
        PlaceStat()
        InfoBoard(modelBoardInfo)
        for (it in listModel)//model.allList())
        {
            Box(modifier = Modifier
                .offset(x = it.posZ.makeDPX().dp, y = it.posX.makeDPY().dp)//it.posZ.makeDPY().dp
                .size(width = 50.dp, height = 25.dp)
                .padding(start = 2.dp, end = 2.dp)
                .background(
                    if (it.thickness != 0L)
                        Color.Blue
                    else
                        Color.Magenta
                )
                .clickable {
                    infoModel = it.toString() + "x = ${it.posZ.makeDPX()}  y = ${it.posX.makeDPY()}"
                    findThickness = it.thickness.toString()
                    findDate = it.date.take(10)
                    findID = it.id.toString()
                    modelBoardInfo = it
                }
            ) {
                Column {
                    val lifeTime = it.date.fingerOutHours()
                    Row (modifier = Modifier.padding(start = 2.dp)){
                        if (it.thickness != 0L)
                            Text((it.thickness.toFloat() / 100.0).toString(), color = Color.White, fontSize = 9.sp)
                        else
                            Text(
                                (it.coverBoardThickness.toFloat() / 100.0).toString(),
                                color = Color.White,
                                fontSize = 11.sp
                            )
                        Spacer(modifier = Modifier.padding(start = 4.dp))
                        Text(lifeTime, color = Color.White, fontSize = 9.sp)
                        Column {
                            Box(
                                modifier = Modifier.padding(1.dp).size(2.dp)
                                    .background(if (it.coverBoardStack > 0) Color.Green else Color.White)
                            )
                            Box(
                                modifier = Modifier.padding(1.dp).size(2.dp)
                                    .background(if (it.name == "1") Color.Green else Color.Red)
                            )
                        }

                    }

                    Text(it.date.take(10), color = Color.White, fontSize = 8.sp)
                }
            }

        }


    }
}

@Composable
fun PlaceStat() {
    Box(
        modifier = Modifier
            .offset(x = 195.dp, y = 514.dp)//it.posZ.makeDPY().dp
            .size(width = 60.dp, height = 30.dp)
            .background(Color.DarkGray)
    )
    Box(
        modifier = Modifier
            .offset(x = 195.dp, y = 656.dp)//it.posZ.makeDPY().dp
            .size(width = 60.dp, height = 30.dp)
            .background(Color.DarkGray)
    )
    Box(
        modifier = Modifier
            .offset(x = 1045.dp, y = 198.dp)//it.posZ.makeDPY().dp
            .size(width = 60.dp, height = 30.dp)
            .background(Color.DarkGray)
    )
    Box(
        modifier = Modifier
            .offset(x = 1253.dp, y = 262.dp)//it.posZ.makeDPY().dp
            .size(width = 60.dp, height = 30.dp)
            .background(Color.DarkGray)
    )
    Box(
        modifier = Modifier
            .offset(x = 181.dp, y = 172.dp)//row
            .size(width = 762.dp, height = 30.dp)
            .background(Color.DarkGray)
    )
    Box(
        modifier = Modifier
            .offset(x = 181.dp, y = 220.dp)//it.posZ.makeDPY().dp
            .size(width = 762.dp, height = 30.dp)
            .background(Color.DarkGray)
    )
    Box(
        modifier = Modifier
            .offset(x = 181.dp, y = 268.dp)//it.posZ.makeDPY().dp
            .size(width = 762.dp, height = 30.dp)
            .background(Color.DarkGray)
    )
    Box(
        modifier = Modifier
            .offset(x = 181.dp, y = 315.dp)//it.posZ.makeDPY().dp
            .size(width = 762.dp, height = 30.dp)
            .background(Color.DarkGray)
    )
    Box(
        modifier = Modifier
            .offset(x = 181.dp, y = 363.dp)//it.posZ.makeDPY().dp
            .size(width = 762.dp, height = 30.dp)
            .background(Color.DarkGray)
    )
    Box(
        modifier = Modifier
            .offset(x = 181.dp, y = 411.dp)//it.posZ.makeDPY().dp
            .size(width = 762.dp, height = 30.dp)
            .background(Color.DarkGray)
    )
    Box(
        modifier = Modifier
            .offset(x = 204.dp, y = 724.dp)//it.posZ.makeDPY().dp
            .size(width = 60.dp, height = 30.dp)
            .background(Color.DarkGray)
    )

}

@Composable
fun InfoBoard(it: ModelCarWithBoard) {
    Box(
        modifier = Modifier
            .offset(x = 1400.dp, y = 100.dp)
            .size(width = 450.dp, height = 500.dp)
            .clip(shape = RoundedCornerShape(10.dp))

            .background(Color.White)
            .shadow(elevation = 4.dp)//, shape = RoundedCornerShape(10.dp),clip = true )

    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(" Board       ", fontSize = 16.sp, modifier = Modifier.padding(top = 8.dp))
            Text(" ID:           " + it.id.toString(), fontSize = 16.sp, modifier = Modifier.padding(top = 8.dp))
            Text(" Pos X:         " + it.posX.toString(), fontSize = 16.sp, modifier = Modifier.padding(top = 8.dp))
            Text(" Pos Z:         " + it.posZ.toString(), fontSize = 16.sp, modifier = Modifier.padding(top = 8.dp))
            Text(
                " Length:        " + it.length.toString(),
                fontSize = 16.sp,
                modifier = Modifier.padding(top = 8.dp)
            )
            Text(
                " Width:          " + it.width.toString(),
                fontSize = 16.sp,
                modifier = Modifier.padding(top = 8.dp)
            )
            Text(
                " Thickness:   " + (it.thickness.toFloat() / 100.0).toString(),
                fontSize = 16.sp,
                modifier = Modifier.padding(top = 8.dp)
            )
            Text(
                " Stack:          " + it.asStack.toString(),
                fontSize = 16.sp,
                modifier = Modifier.padding(top = 8.dp)
            )
            Text(
                " Cover Board Length:            " + it.coverBoardLength.toString(),
                fontSize = 16.sp,
                modifier = Modifier.padding(top = 8.dp)
            )
            Text(
                " Cover Board Width:              " + it.coverBoardWidth.toString(),
                fontSize = 16.sp,
                modifier = Modifier.padding(top = 8.dp)
            )
            Text(
                " Cover Board Thickness:       " + (it.coverBoardThickness.toFloat() / 100.0).toString(),
                fontSize = 16.sp,
                modifier = Modifier.padding(top = 8.dp)
            )
            Text(
                " Cover Board Stack:              " + it.coverBoardStack.toString(),
                fontSize = 16.sp,
                modifier = Modifier.padding(top = 8.dp)
            )
            Text(" Date:                " + it.date, fontSize = 16.sp, modifier = Modifier.padding(top = 8.dp))
            val boardM3 =
                (it.length.toFloat() / 1000.0) * (it.width.toFloat() / 1000.0) * ((it.thickness.toFloat() / 10.0) / 1000.0)
            val boardTotal =
                ((it.length.toFloat() / 1000.0) * (it.width.toFloat() / 1000.0) * ((it.thickness.toFloat() / 10.0) / 1000.0)) * it.asStack.toFloat()
            val boardM2 = (it.length.toFloat() / 1000.0) * (it.width.toFloat() / 1000.0)
            val boardHeight =(it.asStack*it.thickness/100.0)+it.coverBoardThickness/100.0
            Text(" Board m3:    $boardM3 m3", fontSize = 16.sp, modifier = Modifier.padding(top = 8.dp))
            Text(" Board m2:    $boardM2 m2", fontSize = 16.sp, modifier = Modifier.padding(top = 8.dp))
            Text(" Board total:   $boardTotal m3", fontSize = 16.sp, modifier = Modifier.padding(top = 8.dp))
            Text(" Board height:   $boardHeight mm", fontSize = 16.sp, modifier = Modifier.padding(top = 8.dp))

            OutlinedButton(onClick = { deleteID(it.id) }){ Text("Delete") }

        }
    }
}


