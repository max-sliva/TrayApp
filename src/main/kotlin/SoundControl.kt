import jssc.SerialPort
import jssc.SerialPortException
import jssc.SerialPortList
import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.event.ActionEvent
import javax.swing.JComboBox
import javax.swing.JFrame
import javax.swing.JSlider

var strVal = ""
fun main(){
    //nircmd.exe changesysvolume 2000
//    SoundVolumeView.exe /SetVolume "chrome.exe" 75
    runCMD("PWD")
    runCMD("dir")
    createGUI()
}

fun createGUI() {
    var serialPort: SerialPort? = null
    val mainForm = JFrame("SoundControl").apply{
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        setLocationRelativeTo(null)
        size = Dimension(200,100)
        isVisible = true
    }
    val volumeSlider = JSlider().apply {
        minimum = 0
        maximum = 100
        addChangeListener {
//            println("$value")
            val strCMD = "SoundVolumeView.exe /SetVolume \"chrome.exe\" $value"
//            println("cmd = $strCMD")
            runCMD(strCMD)
        }
    }
    mainForm.add(volumeSlider, BorderLayout.NORTH)
    val portNames = SerialPortList.getPortNames() // получаем список портов
    val comPorts = JComboBox(portNames) //создаем комбобокс с этим списком
    mainForm.add(comPorts, BorderLayout.SOUTH)
    comPorts.selectedIndex = -1
    mainForm.validate()

    comPorts.addActionListener { arg: ActionEvent? ->  //слушатель выбора порта в комбобоксе
// получаем название выбранного порта
        val choosenPort = comPorts.getItemAt(comPorts.selectedIndex)
//если serialPort еще не связана с портом или текущий порт не равен выбранному в комбо-боксе
        if (serialPort == null || !serialPort!!.getPortName().contains(choosenPort)) {
            serialPort = SerialPort(choosenPort) //задаем выбранный порт
            try { //тут секция с try...catch для работы с портом
                serialPort!!.openPort() //открываем порт
                //задаем параметры порта, 9600 - скорость, такую же нужно задать для Serial.begin в Arduino
                serialPort!!.setParams(9600, 8, 1, 0) //остальные параметры стандартные
                //слушатель порта для приема сообщений от ардуино
                serialPort!!.addEventListener { event ->
                    if (event.isRXCHAR()) { // если есть данные для приема
                        try { //тут секция с try...catch для работы с портом
//считываем данные из порта в строку
                            var str: String = serialPort!!.readString()
                            //убираем лишние символы (типа пробелов, которые могут быть в принятой строке)
                            str = str.trim { it <= ' ' }
                            //проверяем принятую строку, и либо ставим, либо убираем галочку в чек-боксе
                            println(str) //выводим принятую строку
                            var i = str.length-1
                            if (str.contains("Volume") && str.get(i) in '0'..'9') {
                                strVal = ""
                                while (str.get(i) in '0'..'9') {
                                    strVal += str.get(i)
                                    i--
                                }
                            }
                            val value = strVal.reversed().toByte();
                            println("strVal = $value")
                            val strCMD = "SoundVolumeView.exe /SetVolume \"chrome.exe\" $value"
//            println("cmd = $strCMD")
                            runCMD(strCMD)

                        } catch (ex: SerialPortException) { //для обработки возможных ошибок
                            println(ex)
                        }
                    }
                }
            } catch (e: SerialPortException) { //для обработки возможных ошибок
                e.printStackTrace()
            }
        } else println("Same port!!") //если выбрали в списке тот же порт, что и
    }


}

fun runCMD(strCMD: String){
    val cmd = strCMD
    val run = Runtime.getRuntime()
    val pr = run.exec(cmd)
    pr.waitFor()
//    val buf = BufferedReader(InputStreamReader(pr.inputStream))
//    var line: String? = ""
//    while (buf.readLine().also { line = it } != null) {
//        println(line)
//    }
}