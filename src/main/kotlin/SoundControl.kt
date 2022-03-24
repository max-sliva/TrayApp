import java.awt.BorderLayout
import java.awt.Dimension
import java.io.BufferedReader
import java.io.InputStreamReader
import javax.swing.JFrame
import javax.swing.JSlider


fun main(){
    //nircmd.exe changesysvolume 2000
//    SoundVolumeView.exe /SetVolume "chrome.exe" 75
    runCMD("PWD")
    runCMD("dir")
    createGUI()
}

fun createGUI() {
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
    mainForm.validate()

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