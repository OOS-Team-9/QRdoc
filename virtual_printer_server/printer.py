#!/usr/bin/env
# -*- coding: utf-8 -*-
"""
Simply send in the options you want in Printer.__init__
and then override printThis() to do what you want.
DONE!
Ready to run it with run()
"""
import subprocess
import os


# get the location of ghostscript
if os.name=='nt':
	GHOSTSCRIPT_APP=None
	for gsDir in [os.environ['ProgramFiles']+os.sep+'gs',os.environ['ProgramFiles(x86)']+os.sep+'gs']:
		if os.path.isdir(gsDir):
			# find the newest version
			bestVersion=0
			for f in os.listdir(gsDir):
				path=gsDir+os.sep+f
				if os.path.isdir(path) and f.startswith('gs'):
					try:
						val=float(f[2:])
					except:
						val=0
					if bestVersion<val:
						for appName in ['gswin64c.exe','gswin32c.exe','gswin.exe','gs.exe']:
							appName=gsDir+os.sep+f+os.sep+'bin'+os.sep+appName
							if os.path.isfile(appName):
								bestVersion=val
								GHOSTSCRIPT_APP='"'+appName+'"'
								break
	if GHOSTSCRIPT_APP is None:
		errString="""ERR: Ghostscript not found!
			You can get it from:
				http://www.ghostscript.com"""
		raise Exception(errString)
else: # assume we can find it
	GHOSTSCRIPT_APP='gs'
print 'GHOSTSCRIPT_APP='+GHOSTSCRIPT_APP


# find a good shell_escape routine
try:
	import shlex
	if hasattr(shlex,'quote'):
		shell_escape=shlex.quote
	else:
		import pipes
		shell_escape=pipes.quote
except ImportError:
	import pipes
	shell_escape=pipes.quote


class Printer(object):
	"""
	You can derive from this class to create your own printer!

	Simply send in the options you want in Printer.__init__
	and then override printThis() to do what you want.
	DONE!
	Ready to run it with run()
	"""

	def __init__(self,name='Insert QR_CODE in your Document',acceptsFormat='pdf',acceptsColors='rgba',autoInstallPrinter=False, host = '127.0.0.1', port = 52124):
		"""
		name - the name of the printer to be installed

		acceptsFormat - the format that the printThis() method accepts
		Available formats are "pdf", or "png" (default=png)

		acceptsColors - the color format that the printThis() method accepts
		(if relevent to acceptsFormat)
		Available colors are "grey", "rgb", or "rgba" (default=rgba)

		"""
		self.host = host
		self.port = port
		self.autoInstallPrinter = autoInstallPrinter
		self._server=None
		self.name=name
		self.acceptsFormat=acceptsFormat
		self.acceptsColors=acceptsColors
		self.bgColor='#ffffff' # not sure how necessary this is

	def printThis(self, doc, title=None, author=None, filename=None):
		"""
		Called whenever something is being printed.

		We'll save the input doc to a book or book format
		"""
		filename = 'test'
		print "Printing:"
		print "\tTitle:", title
		print "\tAuthor:", author
		print "\tFilename:", filename
		val = os.getcwd() + '\\' + 'test.pdf'
		if val is not None:
			print "Saving to:", val
			f = open(val, 'wb')
			f.write(doc)
			f.close()
			cmd = 'java --module-path javafx-sdk-11.0.2\lib --add-modules javafx.controls,javafx.fxml --add-exports javafx.graphics/com.sun.javafx.sg.prism=ALL-UNNAMED -jar -Dinp=' + '"' + val + '" ' + 'QRdoc_v.jar'
			stdout = subprocess.Popen(cmd, stdin=None, stderr=subprocess.STDOUT, stdout=subprocess.PIPE,
									  shell=True).communicate()
			print stdout
		else:
			print "No output filename.  So never mind then, I guess."

	def run(self):
		"""
		normally all the default values are exactly what you need!

		autoInstallPrinter is used to install the printer in the operating system
		(currently only supports Windows)
		startServer is required for this
		"""
		import printServer
		self._server=printServer.PrintServer(self.name,self.host,self.port,self.autoInstallPrinter,self.printPostscript)
		self._server.run()
		del self._server # delete it so it gets un-registered
		self._server=None

	def _postscriptToFormat(self,data,gsDev='pdfwrite',gsDevOptions=None,outputDebug=True):
		"""
		Converts postscript data (in a string) to pdf data (in a string)

		gsDev is a ghostscript format device

		For ghostscript command line, see also:
			http://www.ghostscript.com/doc/current/Devices.htm
			http://www.ghostscript.com/doc/current/Use.htm#Options
		"""
		if gsDevOptions is None:
			gsDevOptions=[]
		cmd=GHOSTSCRIPT_APP+' -q -sDEVICE='+gsDev+' '
		cmd=cmd+(' '.join(gsDevOptions))+' -sstdout=%stderr -sOutputFile=- -dBATCH -'
		if outputDebug:
			print cmd
		po=subprocess.Popen(cmd,stdin=subprocess.PIPE,stderr=subprocess.PIPE,
			stdout=subprocess.PIPE,shell=True)
		print(type(data))
		print(data[0:1000])
		data,gsStdoutStderr=po.communicate(input=data)
		if outputDebug:
			print gsStdoutStderr # note: stdout also goes to stderr because of the -sstdout=%stderr flag
		return data

	def printPostscript(self,datasource,datasourceIsFilename=False,
		title=None,author=None,filename=None):
		"""
		datasource is either:
			a filename
			None to get data from stdin
			a file-like object
			something else to convert using str() and then print
		Keep in mind that it MUST contain postscript data
		"""
		# -- open data source
		data=None
		if datasource is None:
			data=sys.stdin.read()
		elif isinstance(datasource,basestring):
			if datasourceIsFilename:
				f=open(datasource,'rb')
				data=f.read()
				f.close()
				if title is None:
					title=datasource.rsplit(os.sep,1)[-1].rsplit('.',1)[0]
			else:
				data=datasource
		elif hasattr(datasource,'read'):
			data=datasource.read()
		else:
			data=str(datasource)
		# -- convert the data to the required format
		print 'Converting data...'
		gsDevOptions=[]
		if self.acceptsFormat=='pdf':
			gsDev='pdfwrite'
		elif self.acceptsFormat=='png':
			gsDevOptions.append('-r600')
			gsDevOptions.append('-dDownScaleFactor=3')
			if self.acceptsColors=='grey':
				gsDev='pnggray'
			elif self.acceptsColors=='rgba':
				if self.bgColor is None: # I'm not sure how necessary background color is
					self.bgColor='#ffffff'
				gsDev='pngalpha'
				if self.bgColor!=None:
					if self.bgColor[0]!='#':
						self.bgColor='#'+self.bgColor
					gsDevOptions.append('-dBackgroundColor=16'+self.bgColor)
			#elif self.acceptsColors=='rgb': #TODO
			else:
				raise Exception('Unacceptable color format "'+self.acceptsColors+'"')
		else:
			raise Exception('Unacceptable data type format "'+self.acceptsFormat+'"')
		data=self._postscriptToFormat(data,gsDev,gsDevOptions)
		# -- send the data to the printThis function
		print 'Printing data...'
		self.printThis(data,title=title,author=author,filename=filename)

