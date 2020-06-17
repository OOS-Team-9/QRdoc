import os
import windowsPrinters
def _installPrinter(printerName = "Insert QR_CODE in your Document",ip = '127.0.0.1', port = 52124):
    """
    Install the printer to the ip address
    """
    #atexit.register(self.__del__)  # ensure that __del__ always gets called when the program exits
    if os.name == 'nt':
        osPrinterManager = windowsPrinters.WindowsPrinters()
        printerPortName = printerName + ' Port'
        makeDefault = False
        comment = 'Virtual printer created in Python'
        osPrinterManager.addPrinter(printerName, ip, port,
                                         printerPortName, makeDefault, comment)
    else:
        print 'WARN: Auto printer installation not implemented for ' + os.name


_installPrinter()