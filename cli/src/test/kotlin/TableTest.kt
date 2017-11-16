import com.googlecode.lanterna.TerminalSize
import com.googlecode.lanterna.gui2.*
import com.googlecode.lanterna.gui2.table.DefaultTableRenderer
import com.googlecode.lanterna.gui2.table.Table
import com.googlecode.lanterna.terminal.DefaultTerminalFactory

fun main(args: Array<String>) {

    val screen = DefaultTerminalFactory().createScreen()
    screen.startScreen()
    val textGUI = MultiWindowTextGUI(SeparateTextGUIThread.Factory(), screen);
    textGUI.isBlockingIO = false
    textGUI.isEOFWhenNoWindows = true

    try {
        init(textGUI)

        val guiThread = textGUI.guiThread as AsynchronousTextGUIThread
        guiThread.start()
        guiThread.waitForStop()
    } finally {
        screen.stopScreen()
    }
}

fun init(textGUI: WindowBasedTextGUI) {
    textGUI.addWindow(MainWindow())
}

class MainWindow : BasicWindow() {

    init {
        setHints(listOf(Window.Hint.FULL_SCREEN))

        val mainPanel = Panel(BorderLayout())

        val table = Table<String>("Column 1", "Columns 2")
        table.renderer = CustomTableRenderer()

        (0..50).forEach {
            table.tableModel.addRow("$it", "$it")
        }

        mainPanel.addComponent(table.withBorder(Borders.doubleLine()), BorderLayout.Location.CENTER)
        mainPanel.addComponent(Button("Ok"), BorderLayout.Location.BOTTOM)

        component = mainPanel
    }
}

class CustomTableRenderer : DefaultTableRenderer<String>() {

    override fun getPreferredSize(table: Table<String>): TerminalSize {
        table.visibleRows = table.parent.size.rows - 3

        return super.getPreferredSize(table)
    }
}