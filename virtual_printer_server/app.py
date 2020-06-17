#!/usr/bin/env
# -*- coding: utf-8 -*-
"""
A virtual printer device that creates a pdf as output
"""
import os
from printer import Printer
import subprocess

class MyPrinter(Printer):
	"""
	A virtual printer device that creates a pdf as output
	"""

	def __init__(self):
		Printer.__init__(self,'Insert QR_CODE in your Document',acceptsFormat='pdf')

	def doSaveAsDialog(self,originFilename = 'C:\\test\\test'):
		"""
		Quick ans simple way to toss up a "save as" dialog.

		:param originFilename: for convenience, we'll guess at an output filename
			based on this (foo.html -> foo.pdf)
		"""
		import tkFileDialog
		import Tkinter

		Tkinter.Tk().withdraw() # this trick prevents a blank application window from showing up
		filetypes=(("PDF Files",'*.pdf'),("all files","*.*"))
		originFilename=originFilename.rsplit(os.sep,1)
		initialfile=originFilename[-1].rsplit('.',1)[0]+'.pdf'
		print(originFilename)
		if len(originFilename)>1:
			initialdir=originFilename[0]
		else:
			initialdir=None
		print("1")
		originFilename = os.getcwd() + '\\' + 'test.pdf'
		val = originFilename
		"""
		val=tkFileDialog.asksaveasfilename(confirmoverwrite=True,
			title='Save As...',defaultextension='.pdf',filetypes=filetypes,
			initialfile=initialfile,initialdir=initialdir)
		"""
		print("2")
		if val is not None and val.strip()!='':
			print("3")
			if os.sep!='/':
				print("4")
				val=val.replace('/',os.sep)
			return val
		print("5")

		return None

	def printThis(self,doc,title=None,author=None,filename=None):
		"""
		Called whenever something is being printed.

		We'll save the input doc to a book or book format
		"""
		filename = 'test'
		print "Printing:"
		print "\tTitle:",title
		print "\tAuthor:",author
		print "\tFilename:",filename
		val=self.doSaveAsDialog(filename)
		if val is not None:
			print "Saving to:",val
			f=open(val,'wb')
			f.write(doc)
			f.close()
			cmd = 'java --module-path javafx-sdk-11.0.2\lib --add-modules javafx.controls,javafx.fxml --add-exports javafx.graphics/com.sun.javafx.sg.prism=ALL-UNNAMED -jar -Dinp=' +'"'+val +'" '+'QRdoc_v.jar'
			stdout = subprocess.Popen(cmd, stdin=None, stderr=subprocess.STDOUT, stdout=subprocess.PIPE,
									   shell=True).communicate()
			print stdout
		else:
			print "No output filename.  So never mind then, I guess."


if __name__=='__main__':
	# Simply run the printer
	p=Printer('Insert QR_CODE in your Document',acceptsFormat='pdf', autoInstallPrinter=False)
	print 'Starting printer... [CTRL+C to stop]'
	p.run()