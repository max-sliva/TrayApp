import java.awt.*
import javax.swing.ImageIcon

fun main(args: Array<String>) {
    println("Tray program!")
    if (!SystemTray.isSupported()) {
        println("SystemTray is not supported")
        return
    }
    println("SystemTray is supported")
    val popup = PopupMenu()
    val trayIcon = TrayIcon(ImageIcon("bulb.gif").image)
    val tray = SystemTray.getSystemTray()

    // Create a pop-up menu components
    val aboutItem = MenuItem("About")
    val cb1 = CheckboxMenuItem("Set auto size")
    val cb2 = CheckboxMenuItem("Set tooltip")
    val displayMenu = Menu("Display")
    val errorItem = MenuItem("Error")
    val warningItem = MenuItem("Warning")
    val infoItem = MenuItem("Info")
    val noneItem = MenuItem("None")
    val exitItem = MenuItem("Exit")

    //Add components to pop-up menu
    popup.add(aboutItem)
    popup.addSeparator()
    popup.add(cb1)
    popup.add(cb2)
    popup.addSeparator()
    popup.add(displayMenu)
    displayMenu.add(errorItem)
    displayMenu.add(warningItem)
    displayMenu.add(infoItem)
    displayMenu.add(noneItem)
    popup.add(exitItem)

    exitItem.addActionListener {
        tray.remove(trayIcon)
        System.exit(0)
    }

    trayIcon.popupMenu = popup

    try {
        tray.add(trayIcon)
    } catch (e: AWTException) {
        println("TrayIcon could not be added.")
    }
    println("Program arguments: ${args.joinToString()}")
}